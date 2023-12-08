package dev.m2t.model.converter;

import dev.m2t.dto.UserDto;
import dev.m2t.model.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserDtoConverter {

    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setIdentityNumber(user.getIdentityNumber());
        userDto.setBalance(user.getBalance());
        userDto.setLuckPercentage(user.getLuckPercentage());
        userDto.setWantedDollars(user.getWantedDollars());
        userDto.setWantedName(user.getWantedName());
        return userDto;
    }

    public User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setIdentityNumber(userDto.getIdentityNumber());
        user.setBalance(userDto.getBalance());
        user.setLuckPercentage(userDto.getLuckPercentage());
        user.setWantedDollars(userDto.getWantedDollars());
        user.setWantedName(userDto.getWantedName());
        return user;
    }
}
