/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.johnny.util;

import java.beans.PropertyVetoException;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author johnny
 */
public class SingleDisplayInterface {

    private boolean maximizeView = false;
    public SingleDisplayInterface() {
        
    }

    public boolean isMaximizeView() {
        return maximizeView;
    }

    public void setMaximizeView(boolean maximizeView) {
        this.maximizeView = maximizeView;
    }
    
    
    public void prepareComponent(JInternalFrame[] comp, JDesktopPane dPane){
        for (JInternalFrame comp1 : comp) {
            dPane.add(comp1);
        }
    }
    
    public void showCompt (JInternalFrame compt, JDesktopPane dPane){
        JInternalFrame[] comp = dPane.getAllFrames();
        int a = comp.length;
        for (int i =0; i < a; i++){
            comp[i].setVisible(false);
        }
        if (compt!=null) {
            compt.setVisible(true);
            if (isMaximizeView() == true) {
                try {
                    compt.setMaximum(true);
                } catch (PropertyVetoException e) {
                    JOptionPane.showMessageDialog(null, "Error Show Component Single Display Interface!", "ERROR!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    public void hideCompt(JDesktopPane dPane){
        JInternalFrame[] comp = dPane.getAllFrames();
        int a = comp.length;
        for (int i = 0; i < a; i++) {
            comp[i].setVisible(false);
        }
    }
    
    public void setCenterLocation(JInternalFrame compt, JDesktopPane dPane){
        int xP = dPane.getWidth()/2;
        int yP = dPane.getHeight()/2;
        int xc = compt.getWidth()/2;
        int yc = compt.getHeight()/2;
        int x = xP - xc;
        int y = yP - yc;
        compt.setLocation(0, 0);
        if ((x > 0) && (y > 0)) {
            compt.setLocation(x, y);
        }
    }
    
    
}
