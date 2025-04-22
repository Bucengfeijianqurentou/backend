package com.gb.backend.chain.service;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gb.backend.chain.constants.ChainConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeBASEService {

    private final ChainConstants chainConstants;

    @Autowired
    public WeBASEService(ChainConstants chainConstants) {
        this.chainConstants = chainConstants;
    }

    // 如果有任何方法使用ChainConstants中的常量，请替换静态引用为实例变量
    // 例如：将 ADMIN_ADDRESS 改为 chainConstants.ADMIN_ADDRESS

    public String registerChainCount(String username) {
        String url = chainConstants.PRIVATE_KEY_URL + username;
        String result = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        String address = jsonObject.getStr("address");
        return address;
    }

}
