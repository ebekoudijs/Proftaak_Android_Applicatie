package com.ebekoudijs.proftaakandroidapplicatie.services;

import com.ebekoudijs.proftaakandroidapplicatie.User;

import java.net.HttpURLConnection;

public interface IUserService {
    User createUser(User user);
    User getUser(String userName, String password);
}
