/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.johnny.view.master;

import com.johnny.entity.Receipt;
import com.johnny.view.MainFrame;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author johnny
 */
public class ReceiptReport extends javax.swing.JInternalFrame {

    /**
     * Creates new form ReceiptReport
     */
    private SessionFactory factory;
    DefaultTableModel model;
    MainFrame mf;
    String[] headerTitle = {"No.", "Tanggal Penerimaan", "Nama Supplier", "Nama Driver", "No. Kendaraan", "Qty", "Operator", "ID Penerimaan"};
    int[] headerWidth = {40, 120, 180, 150, 70, 70, 150, 120};
    int[] headerAlignment = {JLabel.CENTER, JLabel.CENTER, JLabel.LEFT, JLabel.LEFT, JLabel.CENTER, JLabel.CENTER, JLabel.LEFT, JLabel.CENTER};

    public ReceiptReport() {
        initComponents();
        setTabelProperties();
        Date date = new Date();
        txt_start.setDate(date);
        txt_end.setDate(date);
    }

    public SessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    public MainFrame getMf() {
        return mf;
    }

    public void setMf(MainFrame mf) {
        this.mf = mf;
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

    private void getData(Session session) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateStart = txt_start.getDate();
            Date dateEnd = txt_end.getDate();
            String start = sdf.format(dateStart) + " 00:00:00";
            String end = sdf.format(dateEnd) + " 23:59:59";
            int filterIndex = comboFilter.getSelectedIndex();
            String filter = txt_filter.getText();
            String query = "FROM Receipt WHERE date BETWEEN '" + start + "' AND '" + end + "'";
            switch (filterIndex) {
                case 0:
                    query = "FROM Receipt r WHERE r.date BETWEEN '" + start + "' AND '" + end + "' AND r.supplier.name LIKE '%" + filter + "%'";
                    break;
                case 1:
                    query = "FROM Receipt WHERE date BETWEEN '" + start + "' AND '" + end + "' AND driver LIKE '%" + filter + "%'";
                    break;
                case 2:
                    query = "FROM Receipt WHERE date BETWEEN '" + start + "' AND '" + end + "' AND vehicle LIKE '%" + filter + "%'";
                    break;
                default:
                    break;
            }
            List<Receipt> data = new ArrayList<Receipt>();
            data = session.createQuery(query).list();
            int index = 0;
            for (Receipt receipt : data) {
                Object[] obj = new Object[8];
                obj[0] = index + 1;
                obj[1] = df.format(receipt.getDate());
                obj[2] = receipt.getSupplier().getName();
                obj[3] = receipt.getDriver();
                obj[4] = receipt.getVehicle();
                obj[5] = receipt.getQty();
                obj[6] = receipt.getOperator().getUsername();
                obj[7] = receipt.getId();
                model.addRow(obj);
                index++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
    }
    
    public List<Map<String, ?>> findAll() {
        List<Map<String, ?>> result = new ArrayList<Map<String, ?>>();
        Session ses = factory.openSession();
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateStart = txt_start.getDate();
            Date dateEnd = txt_end.getDate();
            String start = sdf.format(dateStart) + " 00:00:00";
            String end = sdf.format(dateEnd) + " 23:59:59";
            int filterIndex = comboFilter.getSelectedIndex();
            String filter = txt_filter.getText();
            String query = "FROM Receipt WHERE date BETWEEN '" + start + "' AND '" + end + "'";
            switch (filterIndex) {
                case 0:
                    query = "FROM Receipt r WHERE r.date BETWEEN '" + start + "' AND '" + end + "' AND r.supplier.name LIKE '%" + filter + "%'";
                    break;
                case 1:
                    query = "FROM Receipt WHERE date BETWEEN '" + start + "' AND '" + end + "' AND driver LIKE '%" + filter + "%'";
                    break;
                case 2:
                    query = "FROM Receipt WHERE date BETWEEN '" + start + "' AND '" + end + "' AND vehicle LIKE '%" + filter + "%'";
                    break;
                default:
                    break;
            }
            List<Receipt> data = new ArrayList<Receipt>();
            data = ses.createQuery(query).list();
            for (Receipt receipt : data) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("id", receipt.getId());
                m.put("driver", receipt.getDriver());
                m.put("supplier", receipt.getSupplier());
                m.put("date", receipt.getDate());
                m.put("vehicle", receipt.getVehicle());
                m.put("qty", receipt.getQty());
                m.put("operator", receipt.getOperator());
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
            String source = "src/main/java/com/johnny/report/receipt.jrxml";
            Map<String, Object> parameter = new HashMap<String, Object>();
            Date dateStart = txt_start.getDate();
            Date dateEnd = txt_end.getDate();
            parameter.put("operator", mf.getOperator());
            parameter.put("start", dateStart);
            parameter.put("end", dateEnd);
            JasperReport report = JasperCompileManager.compileReport(source);
            JasperPrint filledReport = JasperFillManager.fillReport(report, parameter, jrDataSource);
            JasperViewer.viewReport(filledReport, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_filter = new javax.swing.JTextField();
        comboFilter = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txt_start = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        txt_end = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_data = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Laporan Penerimaan");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jLabel1.setText("Filter :");

        txt_filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_filterKeyReleased(evt);
            }
        });

        comboFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Supplier", "Driver", "No. Kendaraan" }));
        comboFilter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboFilterItemStateChanged(evt);
            }
        });

        jLabel2.setText("Periode :");

        txt_start.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txt_startPropertyChange(evt);
            }
        });

        jLabel3.setText("sampai");

        txt_end.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txt_endPropertyChange(evt);
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
        jScrollPane1.setViewportView(tb_data);

        jButton1.setText("Cetak");
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_filter)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_start, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_end, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(txt_start, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txt_end, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
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

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        
        Session ses = factory.openSession();
        getData(ses);
    }//GEN-LAST:event_formComponentShown

    private void txt_filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filterKeyReleased
        // TODO add your handling code here:
        Session ses = factory.openSession();
        getData(ses);
    }//GEN-LAST:event_txt_filterKeyReleased

    private void comboFilterItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboFilterItemStateChanged
        // TODO add your handling code here:
        Session ses = factory.openSession();
        getData(ses);
    }//GEN-LAST:event_comboFilterItemStateChanged

    private void txt_startPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txt_startPropertyChange
        // TODO add your handling code here:
        if (factory != null) {
            Session ses = factory.openSession();
            getData(ses);
        }

    }//GEN-LAST:event_txt_startPropertyChange

    private void txt_endPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txt_endPropertyChange
        // TODO add your handling code here:
        if (factory != null) {
            Session ses = factory.openSession();
            getData(ses);
        }
    }//GEN-LAST:event_txt_endPropertyChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cetak();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboFilter;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_data;
    private com.toedter.calendar.JDateChooser txt_end;
    private javax.swing.JTextField txt_filter;
    private com.toedter.calendar.JDateChooser txt_start;
    // End of variables declaration//GEN-END:variables
}
