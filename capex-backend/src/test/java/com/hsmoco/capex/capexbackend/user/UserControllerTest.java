package com.hsmoco.capex.capexbackend.user;

import com.hsmoco.capex.capexbackend.user.dto.UserDto;
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
        List<UserDto> users = userController.findAll();

        assertThat(users).isNotNull();
        UserDto user = users.getFirst();
        assertThat(user.name()).isNotEmpty();
        assertThat(user.username()).isNotEmpty();
    }
}
