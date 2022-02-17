package com.johnny;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.johnny.view.MainFrame;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String password = "bgst";
        String hash = BCrypt.withDefaults().hashToString(13, password.toCharArray());
        System.out.println(hash);
        MainFrame m = new MainFrame();
        m.setVisible(true);
    }
}
