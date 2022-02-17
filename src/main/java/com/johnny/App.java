package com.johnny;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.johnny.entity.User;
import com.johnny.util.HibernateUtils;
import com.johnny.view.MainFrame;
import org.hibernate.Session;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        String password = "bgst";
        String hash = BCrypt.withDefaults().hashToString(13, password.toCharArray());
        try ( Session session = HibernateUtils.getSessionFactory().openSession()) {
            User user = new User();
            user.setUsername("bagus");
            user.setPassword(hash);
            session.save(user);
        } catch (Exception e) {
            System.out.println("Failed Insert " + e.getMessage());
        }
        System.out.println(hash);
//        MainFrame m = new MainFrame();
//        m.setVisible(true);
    }
}
