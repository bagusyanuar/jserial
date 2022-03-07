/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.johnny.view.master;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortInvalidPortException;
import com.johnny.view.MainFrame;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;

/**
 *
 * @author johnny
 */
public class DeviceFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form DeviceFrame
     */
    MainFrame mf;

    public DeviceFrame() {
        initComponents();
    }

    public void setCombo(SerialPort[] list) {
        combo_port.removeAllItems();
        for (SerialPort list1 : list) {
            combo_port.addItem(list1.getSystemPortName());
        }
    }

    public MainFrame getMf() {
        return mf;
    }

    public void setMf(MainFrame mf) {
        this.mf = mf;
    }

    private void connect() {
        String port = combo_port.getSelectedItem().toString();
        if (!mf.getPort().isOpen()) {
            try {
                mf.setPort(SerialPort.getCommPort(port));
                mf.getPort().setComPortParameters(9600, 8, 1, 0, false);
                mf.getPort().openPort();
                mf.setCommListener();
                setStatus(true);
                checkConnection();
                JOptionPane.showMessageDialog(null, "Succes Connect To Device");
                hide();
            } catch (SerialPortInvalidPortException e) {
                JOptionPane.showMessageDialog(null, "Error Open");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please Has Been Opened");
        }

    }

    boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private void disconnect() {
        if (mf.getPort().isOpen()) {
            try {
                mf.getPort().closePort();
                setStatus(false);
                checkConnection();
                JOptionPane.showMessageDialog(null, "Succes Disconnect Device");
            } catch (HeadlessException e) {
                JOptionPane.showMessageDialog(null, "Error Close Port");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please Open Port");
        }

    }

    private void OnConnect() {
        btn_connect.setEnabled(false);
        btn_disconnect.setEnabled(true);
        mf.setDeviceLabel("Connected...");
    }

    private void OnDisconnect() {
        btn_connect.setEnabled(true);
        btn_disconnect.setEnabled(false);
        mf.setDeviceLabel("Not Connected...");
    }

    private void checkConnection() {
        if (isStatus() == true) {
            OnConnect();
        } else {
            OnDisconnect();
        }
    }

    private void refreshPort() {
        try {
            SerialPort[] list = SerialPort.getCommPorts();
            setCombo(list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Port ");
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
        combo_port = new javax.swing.JComboBox<>();
        btn_connect = new javax.swing.JButton();
        btn_disconnect = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Form Setting Device");

        jLabel1.setText("Available Port :");

        btn_connect.setText("Connect");
        btn_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_connectActionPerformed(evt);
            }
        });

        btn_disconnect.setText("Disconnect");
        btn_disconnect.setEnabled(false);

        btn_refresh.setText("Refresh");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_connect, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_disconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(combo_port, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_refresh)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(combo_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_refresh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_connect)
                    .addComponent(btn_disconnect))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btn_connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_connectActionPerformed
        // TODO add your handling code here:
        connect();
    }//GEN-LAST:event_btn_connectActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        refreshPort();
    }//GEN-LAST:event_btn_refreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_connect;
    private javax.swing.JButton btn_disconnect;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JComboBox<String> combo_port;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}