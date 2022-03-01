package com.johnny;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.johnny.entity.User;
import com.johnny.util.HibernateUtils;
import com.johnny.view.MainFrame;
import com.johnny.view.Splash;
import java.awt.Frame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            SessionFactory fs = HibernateUtils.getSessionFactory();
            Splash splash = new Splash();
            splash.setLocationRelativeTo(null);
            splash.setVisible(true);
//            MainFrame m = new MainFrame();
//            m.prepareComponent(fs);
//            m.setExtendedState(Frame.MAXIMIZED_BOTH);
//            m.setVisible(true);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "error class not found " + e.getMessage());
        } catch (IllegalAccessException e) {
            JOptionPane.showMessageDialog(null, "error Illegal " + e.getMessage());
        } //        System.out.println(hash);
        catch (InstantiationException e) {
            JOptionPane.showMessageDialog(null, "instantion " + e.getMessage());
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, "look and feel" + e.getMessage());
        }
//        System.out.println(hash);

    }
}
