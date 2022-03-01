/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.johnny.view.master;

import com.johnny.entity.Receipt;
import com.johnny.entity.Supplier;
import com.johnny.entity.User;
import com.johnny.repository.SupplierRepository;
import com.johnny.repository.UserRepository;
import com.johnny.view.MainFrame;
import java.awt.HeadlessException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
public class ReceiptFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form ReceiptFrame
     */
    private SessionFactory factory;
    DefaultTableModel model;
    MainFrame mf;
    private long supplierId;

    String[] headerTitle = {"No.", "Tanggal Penerimaan", "Nama Supplier", "Nama Driver", "No. Kendaraan", "Qty", "Operator", "ID Penerimaan"};
    int[] headerWidth = {40, 120, 180, 150, 70, 70, 150, 120};
    int[] headerAlignment = {JLabel.CENTER, JLabel.CENTER, JLabel.LEFT, JLabel.LEFT, JLabel.CENTER, JLabel.CENTER, JLabel.LEFT, JLabel.CENTER};

    public ReceiptFrame() {
        initComponents();
        setTabelProperties();
        Date date = new Date();
        txt_tanggal.setDate(date);

    }

    public MainFrame getMf() {
        return mf;
    }

    public void setMf(MainFrame mf) {
        this.mf = mf;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    private void setTabelProperties() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
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

    public SessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    private void create() {
        Session session = factory.openSession();
        try {
            if (session != null) {
                String vehicle = txt_vehicle.getText();
                String driver = txt_driver_name.getText();
                long suppId = getSupplierId();
                int qty = Integer.parseInt(txt_qty.getText());
                long operatorId = mf.getOperatorId();
                Date receiptDate = txt_tanggal.getDate();
                User user = UserRepository.findById(session, operatorId);
                Supplier supplier = SupplierRepository.findById(session, suppId);
                Receipt receipt = new Receipt();
                receipt.setDate(receiptDate);
                receipt.setSupplier(supplier);
                receipt.setVehicle(vehicle);
                receipt.setDriver(driver);
                receipt.setQty(qty);
                receipt.setOperator(user);
                session.save(receipt);
                JOptionPane.showMessageDialog(null, "Penerimaan Berhasil Di Simpan");
                getData(session);
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "error insert " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "error insert " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error insert " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void getData(Session session) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date now = txt_tanggal.getDate();
            String start = sdf.format(now) + " 00:00:00";
            String end = sdf.format(now) + " 23:59:59";
            List<Receipt> data = new ArrayList<Receipt>();
            data = session.createQuery("FROM Receipt WHERE date BETWEEN '" + start + "' AND '" + end + "'").list();
            int index = 0;
            for (Receipt receipt : data) {
                Object[] obj = new Object[7];
                obj[0] = index + 1;
                obj[1] = df.format(receipt.getDate());
                obj[2] = receipt.getSupplier().getName();
                obj[3] = receipt.getDriver();
                obj[4] = receipt.getVehicle();
                obj[5] = receipt.getQty();
                obj[6] = receipt.getOperator().getUsername();
                model.addRow(obj);
                index++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.err.println(e.getMessage());
        }
    }

    public void setSupplierProperties(String name, long id) {
        txt_supplier_name.setText(name);
        setSupplierId(id);
    }

    private boolean isValidRequest() {
        boolean result = true;
        if (getSupplierId() <= 0
                || "".equals(txt_supplier_name.getText())
                || "".equals(txt_driver_name.getText())
                || "".equals(txt_vehicle.getText())
                || "".equals(txt_qty.getText())) {
            result = false;
        }
        return result;
    }

    private void clear() {
        txt_driver_name.setText("");
        txt_vehicle.setText("");
        txt_qty.setText("0");
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
        popCetak = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_supplier_name = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_driver_name = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_vehicle = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_qty = new javax.swing.JTextField();
        txt_save = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_data = new javax.swing.JTable();
        txt_tanggal = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        popCetak.setText("Cetak");
        pop.add(popCetak);

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Form Penerimaan");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jLabel1.setText("Supplier :");

        txt_supplier_name.setEditable(false);

        jLabel2.setText("Driver :");

        jLabel3.setText("No. Kendaraan :");

        jLabel4.setText("Jumlah :");

        txt_qty.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_qty.setText("0");

        txt_save.setText("Simpan");
        txt_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_saveActionPerformed(evt);
            }
        });

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

        txt_tanggal.setDateFormatString("dd MMM yyyy");
        txt_tanggal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txt_tanggalPropertyChange(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Search 16.png"))); // NOI18N
        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Tanggal Penerimaan :");

        jButton2.setText("Generate");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_save)
                            .addComponent(txt_driver_name, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addComponent(txt_supplier_name)
                            .addComponent(txt_qty)
                            .addComponent(txt_vehicle, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txt_supplier_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1)
                        .addComponent(jLabel5))
                    .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_driver_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_vehicle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_save)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
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

    private void txt_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_saveActionPerformed
        // TODO add your handling code here:
        if (isValidRequest()) {
            create();
        }else {
            JOptionPane.showMessageDialog(null, "Harap Isi Semua Form...");
        }
        
    }//GEN-LAST:event_txt_saveActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        Session ses = factory.openSession();
        getData(ses);
        ses.close();
    }//GEN-LAST:event_formComponentShown

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        com.johnny.dialog.Supplier d_supplier = new com.johnny.dialog.Supplier(null, true);
        d_supplier.setFactory(factory);
        d_supplier.setReceiptFrame(this);
        d_supplier.setLocationRelativeTo(null);
        d_supplier.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_tanggalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txt_tanggalPropertyChange
        // TODO add your handling code here:
        if (factory != null) {
            System.out.println("Property Changed");
            Session ses = factory.openSession();
            getData(ses);
            ses.close();
        }
    }//GEN-LAST:event_txt_tanggalPropertyChange

    private void tb_dataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_dataMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            pop.show(tb_data, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tb_dataMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu pop;
    private javax.swing.JMenuItem popCetak;
    private javax.swing.JTable tb_data;
    private javax.swing.JTextField txt_driver_name;
    private javax.swing.JTextField txt_qty;
    private javax.swing.JButton txt_save;
    private javax.swing.JTextField txt_supplier_name;
    private com.toedter.calendar.JDateChooser txt_tanggal;
    private javax.swing.JTextField txt_vehicle;
    // End of variables declaration//GEN-END:variables
}
