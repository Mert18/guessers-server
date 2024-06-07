package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.response.UserBalanceResponse;
import dev.m2t.unlucky.model.User;
import dev.m2t.unlucky.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public BaseResponse getUserBalance(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found.");
        }
        UserBalanceResponse userBalanceResponse = new UserBalanceResponse();
        userBalanceResponse.setBalance(user.getBalance());
        userBalanceResponse.setUsername(user.getUsername());
        return new BaseResponse<>("User balance retrieved successfully", true, false, userBalanceResponse);
    }
}
