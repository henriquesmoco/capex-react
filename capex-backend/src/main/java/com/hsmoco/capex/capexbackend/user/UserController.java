package com.hsmoco.capex.capexbackend.user;

import com.hsmoco.capex.capexbackend.user.dto.UserDto;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final ConversionService conversionService;
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository, ConversionService conversionService) {
        this.conversionService = conversionService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> conversionService.convert(user, UserDto.class))
                .toList();
    }
}
