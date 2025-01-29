package ru.test.api.model.mapper;


import ru.test.api.model.dto.respomse.UserResponse;
import org.mapstruct.Mapper;
import ru.test.api.model.entity.User;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);
}
