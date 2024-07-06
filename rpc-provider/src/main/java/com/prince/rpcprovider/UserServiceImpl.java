package com.prince.rpcprovider;

import com.prince.model.User;
import com.prince.princerpcstarter.annotation.RpcService;
import com.prince.service.UserService;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
@RpcService
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) throws IOException {
        return user;
    }

    @Override
    public int queryUsername(User user) {
        return user.getName().length();
    }
}
