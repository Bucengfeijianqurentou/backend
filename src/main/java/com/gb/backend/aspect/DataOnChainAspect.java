package com.gb.backend.aspect;

import cn.hutool.core.lang.Dict;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gb.backend.chain.constants.ChainConstants;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * AOP 切面，拦截标有 @DataOnChain 注解的方法，在前置通知中执行区块链相关操作。
 */
@Order(2)
@Component
@Aspect
@RequiredArgsConstructor
public class DataOnChainAspect {

    private final ChainConstants chainConstants;

    /**
     * 前置通知：在标有 @DataOnChain 的方法执行前触发。
     * @param joinPoint 提供对方法调用的上下文信息
     */
    @Before("@annotation(com.gb.backend.annotation.DataOnChain)")
    public void beforeDataOnChain(JoinPoint joinPoint) {
        List funcParam = new ArrayList();
        funcParam.add("f"+ UUID.randomUUID());
        Dict dict = commonReq(chainConstants.CANTEEN_SUPERVISION_ABI, chainConstants.ADMIN_ADDRESS, "HelloWorld", "setName", funcParam, chainConstants.CONTRACT_ADDRESS);
        System.out.println(dict);
    }


    private Dict commonReq(String ABI , String userAddress, String contractName, String funcName, List funcParam, String contractAddress) {
        JSONArray abiJSON = JSONUtil.parseArray(ABI);
        JSONObject data = JSONUtil.createObj();
        data.set("groupId", "1");
        data.set("user", userAddress);
        data.set("contractName", contractName);
        data.set("version", "");
        data.set("funcName", funcName);
        data.set("funcParam", funcParam);
        data.set("contractAddress", contractAddress);
        data.set("contractAbi", abiJSON);
        data.set("useAes", false);
        data.set("useCns", false);
        data.set("cnsName", "");
        String dataString = JSONUtil.toJsonStr(data);
        String responseBody = HttpRequest.post(chainConstants.CONTRACT_URL)
                .header(Header.CONTENT_TYPE, "application/json").body(dataString).execute().body();
        Dict retDict = new Dict();
        retDict.set("result", responseBody);
        return retDict;
    }

}
