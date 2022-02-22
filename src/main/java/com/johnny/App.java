package com.johnny;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.johnny.entity.User;
import com.johnny.util.HibernateUtils;
import com.johnny.view.MainFrame;
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
            SessionFactory fs = HibernateUtils.getSessionFactory();
            MainFrame m = new MainFrame();
            m.prepareComponent(fs);
            m.setExtendedState(Frame.MAXIMIZED_BOTH);
            m.setVisible(true);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (IllegalAccessException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
//        System.out.println(hash);
        catch (InstantiationException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
//        System.out.println(hash);

    }
}
