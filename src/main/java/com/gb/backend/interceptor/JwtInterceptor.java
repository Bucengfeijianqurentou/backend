package com.gb.backend.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gb.backend.common.Result;
import com.gb.backend.config.JwtConfig;
import com.gb.backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT拦截器
 * 用于验证请求中的token
 *
 * @author Claude
 * @since 2024-04-08
 */
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final JwtConfig jwtConfig;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取token
        String header = request.getHeader(jwtConfig.getHeader());
        String token = jwtUtil.extractToken(header);
        
        // 验证token
        if (token != null && jwtUtil.validateToken(token)) {
            // 将用户信息存入request，方便后续使用
            request.setAttribute("username", jwtUtil.getUsernameFromToken(token));
            request.setAttribute("role", jwtUtil.getRoleFromToken(token));
            return true;
        }
        
        // token无效，返回友好的错误提示
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        
        Result<?> errorResult = Result.error(HttpStatus.UNAUTHORIZED.value(), 
            token == null ? "请先登录" : "登录已过期，请重新登录");
            
        objectMapper.writeValue(response.getWriter(), errorResult);
        return false;
    }
} 