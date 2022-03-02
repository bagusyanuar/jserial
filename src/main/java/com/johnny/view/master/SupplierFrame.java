/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.johnny.view.master;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.johnny.entity.Supplier;
import com.johnny.entity.User;
import com.johnny.repository.SupplierRepository;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author johnny
 */
public class SupplierFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form SupplierFrame
     */
    public SupplierFrame() {
        initComponents();
        setTableProperties();
    }

    private Session session;
    private boolean isSave = true;
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private SessionFactory factory;

    public SessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    private void create() {
        Session ses = factory.openSession();
        try {
            if (ses != null) {
                String name = txt_name.getText();
                String phone = txt_phone.getText();
                Supplier supplier = new Supplier();
                supplier.setName(name);
                supplier.setPhone(phone);
                ses.save(supplier);
                clear();
                getData(ses);
                JOptionPane.showMessageDialog(null, "Success create new Supplier");
            } else {
                JOptionPane.showMessageDialog(null, "Error Null Session");
            }

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "error insert " + e.getMessage());
        } finally {
            ses.close();
        }
    }

    private void update() {
        Session ses = factory.openSession();
        try {
            if (ses != null) {
                ses.getTransaction().begin();
                String name = txt_name.getText();
                String phone = txt_phone.getText();

                Supplier supplier = SupplierRepository.findById(ses, userId);
                if (supplier != null) {

                    supplier.setName(name);
                    supplier.setPhone(phone);
                    ses.update(supplier);
                    ses.getTransaction().commit();
                    clear();
                    getData(ses);
                    isSave = true;
                    JOptionPane.showMessageDialog(null, "Success Update Supplier");
                } else {
                    JOptionPane.showMessageDialog(null, "user not found!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error Null Session");
            }

        } catch (HeadlessException e) {
            if (ses != null) {
                ses.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(null, "error insert " + e.getMessage());
        } finally {
            if (ses != null) {
                ses.close();
            }

        }
    }

    DefaultTableModel model;

    String[] headerTitle = {"No.", "Nama Supplier", "No. Hp", "ID Pengguna"};
    int[] headerWidth = {40, 220, 120, 70};
    int[] headerAlignment = {JLabel.CENTER, JLabel.LEFT, JLabel.CENTER, JLabel.CENTER};

    private void setTableProperties() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tb_data.setModel(model);
        tb_data.setModel(model);
        for (String hTitle : headerTitle) {
            model.addColumn(hTitle);
        }
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        for (int i = 0; i < headerWidth.length; i++) {
            tb_data.getColumnModel().getColumn(i).setPreferredWidth(headerWidth[i]);
        }

        for (int i = 0; i < headerAlignment.length; i++) {
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(headerAlignment[i]);
            tb_data.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
        tb_data.getTableHeader().setReorderingAllowed(false);
    }

    private void getData(Session session) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {

            List<Supplier> data = new ArrayList<Supplier>();
            data = session.createQuery("FROM Supplier").list();
            int index = 0;
            for (Supplier supplier : data) {
                Object[] obj = new Object[4];
                obj[0] = index + 1;
                obj[1] = supplier.getName();
                obj[2] = supplier.getPhone();
                obj[3] = supplier.getId();
                model.addRow(obj);
                index++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.err.println(e.getMessage());
        }
    }

    private void clear() {
        isSave = true;
        btn_save.setText("Simpan");
        setUserId(0);
        txt_name.setText("");
        txt_phone.setText("");
    }

    private boolean isValidate() {
        boolean result = true;
        if ("".equals(txt_name.getText()) || "".equals(txt_phone.getText())) {
            result = false;
        }
        return result;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pop = new javax.swing.JPopupMenu();
        popUpdate = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_name = new javax.swing.JTextField();
        btn_save = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txt_phone = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_data = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        popUpdate.setText("Edit");
        popUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popUpdateActionPerformed(evt);
            }
        });
        pop.add(popUpdate);

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Form Data Supplier");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jLabel1.setText("Nama Supplier :");

        btn_save.setText("Simpan");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        jLabel2.setText("No. Hp :");

        tb_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_data.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tb_data.setRowHeight(24);
        tb_data.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_dataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_data);

        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_name)
                            .addComponent(txt_phone)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_save)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txt_phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_save)
                    .addComponent(jButton1))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        // TODO add your handling code here:
        if (isValidate() == true) {
            if (isSave == true) {
                create();
            } else {
                update();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Harap Isi Semua Form...");
        }


    }//GEN-LAST:event_btn_saveActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        clear();
        Session ses = factory.openSession();
        getData(ses);
        ses.close();
    }//GEN-LAST:event_formComponentShown

    private void tb_dataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_dataMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            pop.show(tb_data, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tb_dataMouseClicked

    private void popUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popUpdateActionPerformed
        // TODO add your handling code here:
        isSave = false;
        btn_save.setText("Edit");
        int row = tb_data.getSelectedRow();
        long userId = Long.parseLong(tb_data.getModel().getValueAt(row, 3).toString());
        txt_name.setText(tb_data.getModel().getValueAt(row, 1).toString());
        txt_phone.setText(tb_data.getModel().getValueAt(row, 2).toString());
        setUserId(userId);
    }//GEN-LAST:event_popUpdateActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_save;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu pop;
    private javax.swing.JMenuItem popUpdate;
    private javax.swing.JTable tb_data;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_phone;
    // End of variables declaration//GEN-END:variables
}
