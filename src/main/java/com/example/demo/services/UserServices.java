package com.example.demo.services;

import com.example.demo.daoImp.UserDaoImp;
import com.example.demo.securité.User;

public class UserServices {
    private UserDaoImp userDaoImp = new UserDaoImp();
    public void save(User user){userDaoImp.insertUser(user);}
    public Boolean login(String login, String password){
       return userDaoImp.Login( login,  password);
    }
}
