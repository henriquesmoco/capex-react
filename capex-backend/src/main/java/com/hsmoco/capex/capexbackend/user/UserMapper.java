package com.hsmoco.capex.capexbackend.user;

import com.hsmoco.capex.capexbackend.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface UserMapper extends Converter<User, UserDto> {

}
