package com.example.demo.dao;

import com.example.demo.securité.User;

public interface DaoUser {
    void insertUser(User user);
    void updateUser(User user);
    User findUserByID(Integer id);
    void deleteUserById(Integer id);

}
