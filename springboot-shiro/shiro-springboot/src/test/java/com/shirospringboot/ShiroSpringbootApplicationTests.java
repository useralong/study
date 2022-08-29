package com.shirospringboot;

import com.demo.service.UserService;
import com.demo.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroSpringbootApplicationTests {

    /*@Autowired
    private UserService userService;*/

    @Test
    void contextLoads() {
        System.out.println(111);
        /*System.out.println(userService.queryUserByName("xiaoo"));*/
    }

}
