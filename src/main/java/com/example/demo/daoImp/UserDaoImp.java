package com.example.demo.daoImp;

import com.example.demo.dao.DaoUser;
import com.example.demo.securité.User;

import java.sql.*;

public class UserDaoImp implements DaoUser {
    private Connection conn= DB.getConnection();
    @Override
    public void insertUser(User user) {
            PreparedStatement ps = null;
            if(conn==null)
                System.out.println("yes");

            try {
                ps = conn.prepareStatement("INSERT INTO user (nom,prenom,login,password)"
                        + " VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,user.getNom());
                ps.setString(2,user.getPrenom());
                ps.setString(3,user.getLogin());
                ps.setString(4,user.getPassword());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        user.setId(id);
                    }
                    DB.closeResultSet(rs);
                } else {
                    System.out.println("Aucune ligne renvoyée");
                }
            } catch (SQLException e) {
                System.err.println("problème d'insertion d'un utilisateur!!!");;
            } finally {
                DB.closeStatement(ps);
            }
    }
    @Override
    public void updateUser(User user) {

    }

    @Override
    public User findUserByID(Integer id) {
        return null;
    }

    @Override
    public void deleteUserById(Integer id) {

    }
    public Boolean Login(String login, String password) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM user WHERE login = ? AND  password = ?");
            ps.setString(1, login);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            else{    return  false;}
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver un vol");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }

    }
}
