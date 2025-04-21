package com.gb.backend.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gb.backend.annotation.PassToken;
import com.gb.backend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求日志切面
 * 记录每个API请求的详细信息：用户、方法、参数、时间等
 */
@Aspect
@Component
public class RequestLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingAspect.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 定义切入点，匹配所有Controller类中的方法
     */
    @Pointcut("execution(* com.gb.backend.*.controller.*.*(..))")
    public void controllerMethods() {}

    /**
     * 环绕通知，记录请求日志
     * @param joinPoint 连接点
     * @return 方法执行结果
     * @throws Throwable 执行异常
     */
    @Around("controllerMethods()")
    public Object logRequestInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取当前时间
        LocalDateTime requestTime = LocalDateTime.now();
        String formattedTime = requestTime.format(formatter);
        
        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        
        // 获取调用的方法信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        
        // 获取请求参数
        String params = getParameters(joinPoint);
        
        // 检查是否有PassToken注解
        boolean isPassToken = hasPassTokenAnnotation(method, method.getDeclaringClass());
        
        // 获取用户信息
        String userInfo = isPassToken ? "无需认证接口" : getUserInfo(request);

        // 获取请求URI和方法
        String uri = request != null ? request.getRequestURI() : "Unknown URI";
        String httpMethod = request != null ? request.getMethod() : "Unknown Method";
        
        // 构建并打印日志
        StringBuilder logMessage = new StringBuilder("\n========== 请求日志 ==========\n");
        logMessage.append("时间: ").append(formattedTime).append("\n");
        logMessage.append("用户: ").append(userInfo).append("\n");
        logMessage.append("URI: ").append(uri).append("\n");
        logMessage.append("HTTP方法: ").append(httpMethod).append("\n");
        logMessage.append("控制器: ").append(className).append("\n");
        logMessage.append("方法: ").append(methodName).append("\n");
        logMessage.append("参数: ").append(params).append("\n");
        logMessage.append("是否免Token: ").append(isPassToken).append("\n");
        logMessage.append("============================");
        
        logger.info(logMessage.toString());
        
        // 执行目标方法
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        
        // 记录执行耗时
        logger.info("方法 {}.{} 执行耗时: {}ms", className, methodName, (endTime - startTime));
        
        return result;
    }
    
    /**
     * 检查方法或类是否有PassToken注解
     * @param method 方法
     * @param targetClass 目标类
     * @return 是否有PassToken注解
     */
    private boolean hasPassTokenAnnotation(Method method, Class<?> targetClass) {
        // 方法上的注解优先级高于类上的注解
        PassToken methodAnnotation = method.getAnnotation(PassToken.class);
        if (methodAnnotation != null) {
            return methodAnnotation.required();
        }
        
        // 检查类上是否有注解
        PassToken classAnnotation = targetClass.getAnnotation(PassToken.class);
        return classAnnotation != null && classAnnotation.required();
    }
    
    /**
     * 获取方法参数，过滤文件上传等特殊参数
     * @param joinPoint 连接点
     * @return 参数字符串
     */
    private String getParameters(ProceedingJoinPoint joinPoint) {
        try {
            // 获取参数名和值
            String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
            Object[] paramValues = joinPoint.getArgs();
            
            Map<String, Object> params = new HashMap<>();
            
            if (paramNames != null) {
                for (int i = 0; i < paramNames.length; i++) {
                    Object value = paramValues[i];
                    
                    // 过滤文件上传参数
                    if (value instanceof MultipartFile) {
                        MultipartFile file = (MultipartFile) value;
                        params.put(paramNames[i], "文件: " + file.getOriginalFilename() + ", 大小: " + file.getSize() + " bytes");
                    } else if (value instanceof MultipartFile[]) {
                        MultipartFile[] files = (MultipartFile[]) value;
                        params.put(paramNames[i], "多文件上传, 数量: " + files.length);
                    } else if (value instanceof HttpServletRequest) {
                        params.put(paramNames[i], "HttpServletRequest");
                    } else {
                        params.put(paramNames[i], value);
                    }
                }
            }
            
            return objectMapper.writeValueAsString(params);
        } catch (Exception e) {
            logger.warn("无法获取方法参数", e);
            return "无法解析的参数";
        }
    }
    
    /**
     * 从请求中获取用户信息
     * @param request HTTP请求
     * @return 用户信息字符串
     */
    private String getUserInfo(HttpServletRequest request) {
        if (request == null) {
            return "未知用户";
        }
        
        // 从请求头中获取token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return "未登录用户";
        }
        
        try {
            // 解析token
            String token = authHeader.substring(7);
            Claims claims = jwtUtil.getClaimsFromToken(token);
            
            if (claims != null) {
                Long userId = claims.get("userId", Long.class);
                String username = claims.get("username", String.class);
                String role = claims.get("role", String.class);
                
                return String.format("ID:%s, 用户名:%s, 角色:%s", userId, username, role);
            }
        } catch (Exception e) {
            logger.warn("无法解析用户token", e);
        }
        
        return "Token无效";
    }
} 