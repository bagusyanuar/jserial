/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.johnny.repository;

import com.johnny.entity.User;
import javax.swing.JOptionPane;
import org.hibernate.Session;

/**
 *
 * @author johnny
 */
public class UserRepository {
    
    public static User findById(Session session, long id) {
        User user = null;
        try {
            user = (User) session.get(User.class, id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return user;
    }    
    
    public static User findByUsername(Session session, String username) {
        User user = null;
        try {
            user = (User) session.createQuery("FROM User WHERE username = '" + username + "' AND is_active = 1")
                    .getResultList()
                    .stream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return user;
    }
}
