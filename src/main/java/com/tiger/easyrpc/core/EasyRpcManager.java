package com.tiger.easyrpc.core;

import com.tiger.easyrpc.core.config.ApplicationConfig;
import com.tiger.easyrpc.core.config.ConsumerConfig;
import com.tiger.easyrpc.core.config.ProviderConfig;
import com.tiger.easyrpc.core.config.RegistryConfig;
import com.tiger.easyrpc.remote.netty4.NettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 管理easyrpc的各个组件
 */
public class EasyRpcManager {
    private String serviceScanPath;
    private String easyrpcRootPath="com.tiger.easyrpc";
    private Logger logger = LoggerFactory.getLogger(EasyRpcManager.class);
    private static EasyRpcManager manager = new EasyRpcManager();
    private EasyRpcManager(){}
    private NettyServer nettyServer;
    private ProviderConfig providerConfig;
    private ConsumerConfig consumerConfig;
    private RegistryConfig registryConfig;
    private ApplicationConfig applicationConfig;

    private boolean isServer;


    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public RegistryConfig getRegistryConfig() {
        return registryConfig;
    }

    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }

    public boolean isServer() {
        return providerConfig !=null && providerConfig.isEnable();
    }

    public void setServer(boolean server) {
        isServer = server;
    }

    public static EasyRpcManager getInstance(){
        return manager;
    }

    public ConsumerConfig getConsumerConfig() {
        return consumerConfig;
    }

    public String getServiceScanPath() {
        return serviceScanPath;
    }

    public void setServiceScanPath(String serviceScanPath) {
        this.serviceScanPath = serviceScanPath;
    }

    public String getEasyrpcRootPath() {
        return easyrpcRootPath;
    }

    public void setEasyrpcRootPath(String easyrpcRootPath) {
        this.easyrpcRootPath = easyrpcRootPath;
    }

    public void setProviderConfig(ProviderConfig providerConfig){
        this.providerConfig = providerConfig;
    }

    public ProviderConfig getProviderConfig() {
        return providerConfig;
    }

    public void setConsumerConfig(ConsumerConfig consumerConfig){
        this.consumerConfig = consumerConfig;
    }

    /**
     * 暴露服务
     */
    public void exportService(){
        //未设置服务端端口，不暴露服务
        if(providerConfig == null || providerConfig.getPort() == null){
            return;
        }
        if(nettyServer == null){
            nettyServer = new NettyServer(this.providerConfig.getPort());
        }
        try {
            nettyServer.run();
        } catch (Exception e) {
            logger.error("暴露服务发生异常！",e);
        }
    }

    public boolean isEnableRegistry(){
        return registryConfig == null ? false: true;
    }

}
