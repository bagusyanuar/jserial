package com.johnny;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.johnny.entity.User;
import com.johnny.util.HibernateUtils;
import com.johnny.view.MainFrame;
import java.awt.Frame;
import javax.swing.JOptionPane;
import org.hibernate.Session;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
//        System.out.println("Hello World!");
//        String password = "bgst";
//        String hash = BCrypt.withDefaults().hashToString(13, password.toCharArray());
        try {
//            User user = new User();
//            user.setUsername("bagus");
//            user.setPassword(hash);
//            session.save(user);
            Session session = HibernateUtils.getSessionFactory().openSession();
            MainFrame m = new MainFrame();
//            m.setSession(session);
            m.prepareComponent(session);
            m.setExtendedState(Frame.MAXIMIZED_BOTH);
            m.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
//        System.out.println(hash);

    }
}
