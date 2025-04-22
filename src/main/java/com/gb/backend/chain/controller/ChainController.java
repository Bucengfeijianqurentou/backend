package com.gb.backend.chain.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gb.backend.annotation.PassToken;
import com.gb.backend.chain.constants.ChainConstants;
import com.gb.backend.chain.service.WeBASEService;
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
    private  WeBASEService weBASEService;

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
        return weBASEService.registerChainCount(username);
    }


    /**
     * 获取交易总数
     * @return
     */
    @GetMapping("/getTransactionTotal")
    public Integer getTransactionTotal(){
        String res = HttpUtil.get(chainConstants.CHAIN_NUMBER_URL);
        JSONObject obj = JSONUtil.parseObj(res);
        return obj.getInt("txSum");
    }


    /**
     * 获取区块高度
     * @return
     */
    @GetMapping("/getBlockNumber")
    public Integer getBlockNumber(){
        String res = HttpUtil.get(chainConstants.BLOCK_NUMBER);
        return Integer.valueOf(res);
    }
}
