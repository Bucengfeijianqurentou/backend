package com.gb.backend.chain.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gb.backend.annotation.PassToken;
import com.gb.backend.chain.constants.ChainConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/chain")
@PassToken
public class ChainController {

    private final ChainConstants chainConstants;

    @Autowired
    public ChainController(ChainConstants chainConstants) {
        this.chainConstants = chainConstants;
    }

    /**
     * 注册区块链地址
     * @param username 用户名
     * @return address 账户地址
     */
    @GetMapping("/register")
    public String registerChainCount(String username) {
        String url = chainConstants.PRIVATE_KEY_URL + username;
        String result = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        String address = jsonObject.getStr("address");
        return address;
    }

    @GetMapping("/getTransactionTotal")
    public Integer getTransactionTotal(){
        String res = HttpUtil.get(chainConstants.CHAIN_NUMBER_URL);
        JSONObject obj = JSONUtil.parseObj(res);
        return obj.getInt("txSum");
    }

    @GetMapping("/getBlockNumber")
    public Integer getBlockNumber(){
        String res = HttpUtil.get(chainConstants.BLOCK_NUMBER);
        return Integer.valueOf(res);
    }
}
