package com.textquo.dreamcode.server.services;

import com.textquo.dreamcode.server.domain.User;

public interface UserService {
    public User login(String username, String password);
    public void logout();
    public User getLoggedInUser();
}
