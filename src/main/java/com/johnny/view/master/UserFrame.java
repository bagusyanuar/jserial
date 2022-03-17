/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.johnny.view.master;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.johnny.dialog.ResetPassword;
import com.johnny.entity.Receipt;
import com.johnny.entity.User;
import com.johnny.repository.UserRepository;
import com.johnny.util.HibernateUtils;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author johnny
 */
public class UserFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form UserFrame
     */
    public UserFrame() {
        initComponents();
        setTableProperties();
        clear();
    }

    private Session session;
    DefaultTableModel model;
    DefaultTableModel model2;
    private String role;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private void create() {
        Session ses = factory.openSession();
        try {
            if (factory != null) {
                String username = txt_username.getText();
                String plain_password = txt_password.getText();
                int roleIndex = comboRole.getSelectedIndex();
                String role = "admin";
                switch (roleIndex) {
                    case 0:
                        role = "admin";
                        break;
                    case 1:
                        role = "super";
                        break;
                    default:
                        break;
                }
                String password = BCrypt.withDefaults().hashToString(13, plain_password.toCharArray());
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setRole(role);
                user.setIsActive(true);
                ses.save(user);
                clear();
                getData(ses);
                JOptionPane.showMessageDialog(null, "Success create new user");
            } else {
                JOptionPane.showMessageDialog(null, "Error Null Session");
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "error insert " + e.getMessage());
        } finally {
            ses.close();
        }

    }

    String[] headerTitle = {"No.", "Username", "Role", "ID Pengguna"};
    int[] headerWidth = {40, 180, 120, 70};
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

        //set inactive tabel model
        model2 = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tb_data_inactive.setModel(model2);
        tb_data_inactive.setModel(model2);
        for (String hTitle : headerTitle) {
            model2.addColumn(hTitle);
        }
        model2.getDataVector().removeAllElements();
        model2.fireTableDataChanged();
        for (int i = 0; i < headerWidth.length; i++) {
            tb_data_inactive.getColumnModel().getColumn(i).setPreferredWidth(headerWidth[i]);
        }

        for (int i = 0; i < headerAlignment.length; i++) {
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(headerAlignment[i]);
            tb_data_inactive.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
        tb_data_inactive.getTableHeader().setReorderingAllowed(false);
    }

    private void getData(Session ses) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        model2.getDataVector().removeAllElements();
        model2.fireTableDataChanged();
        try {
            List<User> data = new ArrayList<User>();
            data = ses.createQuery("FROM User WHERE isActive = 1").list();
            int index = 0;
            for (User user : data) {
                Object[] obj = new Object[4];
                obj[0] = index + 1;
                obj[1] = user.getUsername();
                obj[2] = user.getRole();
                obj[3] = user.getId();
                model.addRow(obj);
                index++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed To Get Active User " + e.getMessage());
            System.err.println(e.getMessage());
        }

        try {
            List<User> data = new ArrayList<User>();
            data = ses.createQuery("FROM User WHERE isActive = 0").list();
            int index = 0;
            for (User user : data) {
                Object[] obj = new Object[4];
                obj[0] = index + 1;
                obj[1] = user.getUsername();
                obj[2] = user.getRole();
                obj[3] = user.getId();
                model2.addRow(obj);
                index++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed To Get Inactive User " + e.getMessage());
            System.err.println(e.getMessage());
        }

    }

    private void clear() {
        txt_username.setText("");
        txt_password.setText("");
        comboRole.setSelectedIndex(0);
    }

    public List<Map<String, ?>> findAll() {
        List<Map<String, ?>> result = new ArrayList<Map<String, ?>>();
        Session ses = factory.openSession();
        try {
            List<Receipt> data = new ArrayList<Receipt>();
            data = ses.createQuery("FROM Receipt r WHERE r.supplier.id = 2").list();
            for (Receipt receipt : data) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("id", receipt.getId());
                m.put("driver", receipt.getDriver());
                m.put("supplier", receipt.getSupplier());
                result.add(m);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ses.close();
        }
        return result;
    }

    private void cetak() {
        try {
            JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(findAll());
            String source = "src/main/java/com/johnny/report/report2.jrxml";
            JasperReport report = JasperCompileManager.compileReport(source);
            JasperPrint filledReport = JasperFillManager.fillReport(report, null, jrDataSource);
            JasperViewer.viewReport(filledReport, false);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private boolean isValidate() {
        boolean result = true;
        if ("".equals(txt_username.getText()) || "".equals(txt_password.getText())) {
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
        popReset = new javax.swing.JMenuItem();
        popSetInactive = new javax.swing.JMenuItem();
        pop2 = new javax.swing.JPopupMenu();
        popSetActive = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        btn_reset = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        comboRole = new javax.swing.JComboBox<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_data = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_data_inactive = new javax.swing.JTable();

        popReset.setText("Reset Password");
        popReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popResetActionPerformed(evt);
            }
        });
        pop.add(popReset);

        popSetInactive.setText("Set Non Aktif");
        popSetInactive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popSetInactiveActionPerformed(evt);
            }
        });
        pop.add(popSetInactive);

        popSetActive.setText("Set Aktif");
        popSetActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popSetActiveActionPerformed(evt);
            }
        });
        pop2.add(popSetActive);

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Form Pengguna");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jLabel1.setText("Username :");

        jLabel2.setText("Password :");

        btn_reset.setText("Reset");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });

        btn_save.setText("Simpan");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        jLabel3.setText("Akses :");

        comboRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Super" }));

        tb_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "No.", "Username"
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("User Aktif", jPanel3);

        tb_data_inactive.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_data_inactive.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tb_data_inactive.setRowHeight(24);
        tb_data_inactive.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_data_inactiveMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb_data_inactive);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("User Non Aktif", jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(btn_reset)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_save))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_username)
                                    .addComponent(txt_password)
                                    .addComponent(comboRole, 0, 136, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_reset)
                    .addComponent(btn_save))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        // TODO add your handling code here:

        if (isValidate() == true) {
            create();
        } else {
            JOptionPane.showMessageDialog(null, "Harap Isi Semua Form...");
        }
    }//GEN-LAST:event_btn_saveActionPerformed

    private void checkRole() {
        if ("super".equals(getRole())) {
            popSetInactive.setEnabled(true);
            popSetInactive.setEnabled(true);
        } else {
            popSetInactive.setEnabled(false);
            popSetInactive.setEnabled(false);
        }
    }
    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        clear();
        checkRole();
        Session ses = factory.openSession();
        getData(ses);
        ses.close();
    }//GEN-LAST:event_formComponentShown

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btn_resetActionPerformed

    private void tb_dataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_dataMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            pop.show(tb_data, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tb_dataMouseClicked

    private void popResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popResetActionPerformed
        // TODO add your handling code here:
        int row = tb_data.getSelectedRow();
        long userId = Long.parseLong(tb_data.getModel().getValueAt(row, 3).toString());
        ResetPassword reset = new ResetPassword(null, true);
        reset.setFactory(factory);
        reset.setUserId(userId);
        reset.setLocationRelativeTo(null);
        reset.setVisible(true);
    }//GEN-LAST:event_popResetActionPerformed

    private void tb_data_inactiveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_data_inactiveMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            pop2.show(tb_data_inactive, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tb_data_inactiveMouseClicked

    private void setActive(long userId, boolean active) {
        Session ses = factory.openSession();
        try {
            ses.getTransaction().begin();
            User user = UserRepository.findById(ses, userId);
            if (user != null) {
                user.setIsActive(active);
                ses.update(user);
                JOptionPane.showMessageDialog(null, "Success Update");
                getData(ses);
                ses.getTransaction().commit();
            } else {
                JOptionPane.showMessageDialog(null, "User not found!");
            }
        } catch (HeadlessException e) {
            ses.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "Failed To Set Non Aktif User");
        } finally {
            ses.close();
        }
    }
    private void popSetInactiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popSetInactiveActionPerformed
        // TODO add your handling code here:
        int row = tb_data.getSelectedRow();
        long userId = Long.parseLong(tb_data.getModel().getValueAt(row, 3).toString());
        setActive(userId, false);
    }//GEN-LAST:event_popSetInactiveActionPerformed

    
    private void popSetActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popSetActiveActionPerformed
        // TODO add your handling code here:
        int row = tb_data_inactive.getSelectedRow();
        long userId = Long.parseLong(tb_data_inactive.getModel().getValueAt(row, 3).toString());
        setActive(userId, true);
    }//GEN-LAST:event_popSetActiveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_save;
    private javax.swing.JComboBox<String> comboRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPopupMenu pop;
    private javax.swing.JPopupMenu pop2;
    private javax.swing.JMenuItem popReset;
    private javax.swing.JMenuItem popSetActive;
    private javax.swing.JMenuItem popSetInactive;
    private javax.swing.JTable tb_data;
    private javax.swing.JTable tb_data_inactive;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
