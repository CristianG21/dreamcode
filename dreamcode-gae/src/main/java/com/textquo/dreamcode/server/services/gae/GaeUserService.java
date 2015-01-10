package com.textquo.dreamcode.server.services.gae;

import com.textquo.dreamcode.server.domain.User;
import com.textquo.dreamcode.server.services.UserService;

// Stub User Service
public class GaeUserService implements UserService {

    @Override
    public User login(String username, String password) {
        return null;
    }

    @Override
    public void logout() {

    }

    @Override
    public User getLoggedInUser() {
        return null;
    }
}
