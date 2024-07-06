package com.prince.easyprovider;

import com.prince.RpcApplication;
import com.prince.bootstrap.ProviderBootstrap;
import com.prince.config.RegistryConfig;
import com.prince.config.RpcConfig;
import com.prince.constant.RpcConstant;
import com.prince.model.ServiceMetaInfo;
import com.prince.model.ServiceRegisterInfo;
import com.prince.registry.LocalRegistry;
import com.prince.registry.Registry;
import com.prince.registry.RegistryFactory;
import com.prince.server.VertxHttpServer;
import com.prince.server.tcp.VertxTcpServer;
import com.prince.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 注册中心启动
 */
public class AdvanceProvider {

    public static void main(String[] args) {
        List<ServiceRegisterInfo<?>> registerInfoList = new ArrayList<>();
        ServiceRegisterInfo<UserService> registerInfo =
                new ServiceRegisterInfo<>(UserService.class.getName(), UserService.class);
        registerInfoList.add(registerInfo);

        ProviderBootstrap.init(registerInfoList);
    }
}
