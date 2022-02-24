/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.johnny.repository;

import com.johnny.entity.Supplier;
import com.johnny.entity.User;
import javax.swing.JOptionPane;
import org.hibernate.Session;

/**
 *
 * @author johnny
 */
public class SupplierRepository {

    public static Supplier findById(Session session, long id) {
        Supplier supplier = null;
        try {
            supplier = (Supplier) session.get(Supplier.class, id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return supplier;
    }
}
