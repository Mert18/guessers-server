package dev.m2t.service;


import dev.m2t.dto.request.UserBalanceRequest;
import dev.m2t.dto.response.UserBalanceResponse;
import dev.m2t.model.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserService {

    public UserBalanceResponse getUserBalance(UserBalanceRequest userBalanceRequest) {
        User user = User.find("name = ?1", userBalanceRequest.getName()).firstResult();
        if (user == null) {
            return new UserBalanceResponse("User not found", null);
        }
        return new UserBalanceResponse(user.getName(), user.getBalance());
    }
}
