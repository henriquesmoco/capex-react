package com.hsmoco.capex.capexbackend.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    void findAllUsers_ReturnUsersProperly() {
        List<User> users = userController.findAll();

        assertThat(users).isNotNull();
        User user = users.getFirst();
        assertThat(user.getName()).isNotEmpty();
        assertThat(user.getUsername()).isNotEmpty();
    }
}
