package com.prince.rpcconsumer;

import com.prince.model.User;
import com.prince.princerpcstarter.annotation.RpcReference;
import com.prince.service.UserService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TestImpl {

    @RpcReference
    UserService userService;

    public void test() throws IOException {
        User user = new User();
        user.setName("prince");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }

}
