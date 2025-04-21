package com.gb.backend.chain.constants;

import com.gb.backend.chain.config.ChainProperties;
import org.springframework.stereotype.Component;

/**
 * 区块链常量类
 * 不再硬编码常量，而是从配置文件加载
 */
@Component
public class ChainConstants {
    // 基础URL
    public final String URL;
    
    // 管理员区块链地址
    public final String ADMIN_ADDRESS;
    
    // 合约地址
    public final String ASSET_MANAGEMENT_CONTRACT_ADDRESS;
    
    // 交易API
    public final String CONTRACT_URL;
    
    // 获取交易总数
    public final String CHAIN_NUMBER_URL;
    
    // 获取群组内的共识状态信息
    public final String NODE_MANAGE_URL;
    
    // 获取私钥
    public final String PRIVATE_KEY_URL;
    
    // 合约ABI
    public final String CANTEEN_SUPERVISION_ABI;
    
    // 获取块高接口
    public final String BLOCK_NUMBER;
    
    /**
     * 构造函数，从属性配置类中加载配置
     * @param chainProperties 区块链属性配置
     */
    public ChainConstants(ChainProperties chainProperties) {
        this.URL = chainProperties.getBaseUrl();
        this.ADMIN_ADDRESS = chainProperties.getAddress().getAdmin();
        this.ASSET_MANAGEMENT_CONTRACT_ADDRESS = chainProperties.getAddress().getContract();
        this.CONTRACT_URL = chainProperties.getApi().getContract();
        this.CHAIN_NUMBER_URL = chainProperties.getApi().getChainNumber();
        this.NODE_MANAGE_URL = chainProperties.getApi().getNodeManage();
        this.PRIVATE_KEY_URL = chainProperties.getApi().getPrivateKey();
        this.CANTEEN_SUPERVISION_ABI = chainProperties.getContract().getCanteenSupervisionAbi();
        this.BLOCK_NUMBER = chainProperties.getApi().getBlockNumber();
    }
}
