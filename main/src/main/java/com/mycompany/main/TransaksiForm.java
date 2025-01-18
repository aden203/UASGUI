/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.main;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author acer
 */
public class TransaksiForm extends javax.swing.JFrame {

    /**
     * Creates new form TransaksiForm
     */
    private String currentUsername;

    public TransaksiForm() {
        initComponents();
        comboBoxBarang.addActionListener((ActionEvent evt) -> {
            String selectedItem = (String) comboBoxBarang.getSelectedItem();
            if (selectedItem != null) {
                String idBarang = selectedItem.split(" - ")[0]; // Ambil ID Barang
                loadBarangDetails(idBarang);
            }
        });
        generateTransactionID();
        loadBarangComboBox();
        loadTransaksiTable();
    }

    public TransaksiForm(String username) {
        this.currentUsername = username;
        initComponents();
        comboBoxBarang.addActionListener((ActionEvent evt) -> {
            String selectedItem = (String) comboBoxBarang.getSelectedItem();
            if (selectedItem != null) {
                String idBarang = selectedItem.split(" - ")[0]; // Ambil ID Barang
                loadBarangDetails(idBarang);
            }
        });
        generateTransactionID();
        loadBarangComboBox();
        loadTransaksiTable();
        outputLabelTanggal.setText(java.time.LocalDate.now().toString());

    }

    private void generateTransactionID() {
        try (Connection conn = KoneksiDB.getConnection()) {
            String query = "SELECT COUNT(*) AS jumlah FROM transaksi";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String uniqueID = "TRX-" + System.currentTimeMillis(); // Menggunakan timestamp
                outputLabelID.setText(uniqueID);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadBarangComboBox() {
        try (Connection conn = KoneksiDB.getConnection()) {
            String query = "SELECT id_barang, nama_barang FROM barang";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            comboBoxBarang.removeAllItems(); // Kosongkan daftar sebelumnya
            while (rs.next()) {
                String item = rs.getInt("id_barang") + " - " + rs.getString("nama_barang");
                comboBoxBarang.addItem(item);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadBarangDetails(String idBarang) {
        try (Connection conn = KoneksiDB.getConnection()) {
            String query = "SELECT nama_barang, harga FROM barang WHERE id_barang = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, idBarang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                outputLabelNamaBarang.setText(rs.getString("nama_barang"));
                outputLabelHarga.setText(rs.getBigDecimal("harga").toString());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getUserId(String username) throws SQLException {
        try (Connection conn = KoneksiDB.getConnection()) {
            String query = "SELECT id_user FROM users WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_user");
            } else {
                throw new SQLException("User tidak ditemukan!");
            }
        }
    }

    private void loadTransaksiTable() {
        try (Connection conn = KoneksiDB.getConnection()) {
            String query = "SELECT tb.id_transaksi, b.nama_barang, tb.jumlah, (tb.harga * tb.jumlah) AS total_harga "
                    + "FROM transaksi_barang tb "
                    + "JOIN barang b ON tb.id_barang = b.id_barang "
                    + "WHERE tb.id_transaksi = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, outputLabelID.getText());
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) transaksiTable.getModel();
            model.setRowCount(0); // Kosongkan tabel

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_transaksi"),
                    rs.getString("nama_barang"),
                    rs.getInt("jumlah"),
                    rs.getBigDecimal("total_harga")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        generateTransactionID(); // Buat ID transaksi baru
        comboBoxBarang.setSelectedIndex(-1);
        txtJumlah.setText("");
        outputLabelNamaBarang.setText("");
        outputLabelHarga.setText("");
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
        comboBoxBarang = new javax.swing.JComboBox<>();
        labelPilihBarang = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        labelJumlah = new javax.swing.JLabel();
        labelidtransaksi = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        labeltanggal = new javax.swing.JLabel();
        labelnamabarang = new javax.swing.JLabel();
        labelharga = new javax.swing.JLabel();
        outputLabelID = new javax.swing.JLabel();
        outputLabelTanggal = new javax.swing.JLabel();
        outputLabelNamaBarang = new javax.swing.JLabel();
        outputLabelHarga = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        transaksiTable = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuOpsi = new javax.swing.JMenu();
        MenuItemKembali = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        comboBoxBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Barang" }));
        comboBoxBarang.setToolTipText("");
        comboBoxBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxBarangActionPerformed(evt);
            }
        });

        labelPilihBarang.setText("Barang");

        title.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        title.setText("Transaksi");

        labelJumlah.setText("Jumlah");

        labelidtransaksi.setText("ID Transaksi");

        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });

        labeltanggal.setText("Tanggal");

        labelnamabarang.setText("Nama Barang");

        labelharga.setText("Harga");

        transaksiTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "Nama Barang", "Jumlah", "Total Harga"
            }
        ));
        jScrollPane1.setViewportView(transaksiTable);

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelidtransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputLabelID, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(labeltanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelnamabarang, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(labelharga, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(outputLabelTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addComponent(outputLabelNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(outputLabelHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPilihBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboBoxBarang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(180, 180, 180))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSimpan)
                        .addGap(210, 210, 210))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPilihBarang))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelJumlah)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(btnSimpan)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelidtransaksi)
                    .addComponent(labeltanggal)
                    .addComponent(labelnamabarang)
                    .addComponent(labelharga))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outputLabelID, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputLabelTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputLabelNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputLabelHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 710));

        MenuOpsi.setText("Opsi");

        MenuItemKembali.setText("Kembali");
        MenuItemKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemKembaliActionPerformed(evt);
            }
        });
        MenuOpsi.add(MenuItemKembali);

        jMenuBar1.add(MenuOpsi);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void MenuItemKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemKembaliActionPerformed
        // TODO add your handling code here:
        UserProfileForm userProfileForm = new UserProfileForm(currentUsername);
        userProfileForm.setVisible(true);
        this.dispose(); // Tutup BarangForm
    }//GEN-LAST:event_MenuItemKembaliActionPerformed

    private void comboBoxBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxBarangActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_comboBoxBarangActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        String idTransaksi = outputLabelID.getText(); // ID Transaksi sekarang string
        String tanggal = java.time.LocalDate.now().toString();
        String selectedItem = (String) comboBoxBarang.getSelectedItem();
        if (selectedItem == null || txtJumlah.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih barang dan masukkan jumlah!");
            return;
        }
        String idBarang = selectedItem.split(" - ")[0];
        int jumlah = Integer.parseInt(txtJumlah.getText());
        BigDecimal harga = new BigDecimal(outputLabelHarga.getText());
        BigDecimal total = harga.multiply(new BigDecimal(jumlah));

        try (Connection conn = KoneksiDB.getConnection()) {
            // Simpan ke tabel transaksi
            String insertTransaksi = "INSERT INTO transaksi (id_transaksi, id_user, tanggal, total) VALUES (?, ?, ?, ?)";
            PreparedStatement psTransaksi = conn.prepareStatement(insertTransaksi);
            psTransaksi.setString(1, idTransaksi); // Set sebagai string
            psTransaksi.setInt(2, getUserId(currentUsername));
            psTransaksi.setString(3, tanggal);
            psTransaksi.setBigDecimal(4, total);
            psTransaksi.executeUpdate();

            // Simpan ke tabel transaksi_barang
            String insertTransaksiBarang = "INSERT INTO transaksi_barang (id_transaksi, id_barang, jumlah, harga) VALUES (?, ?, ?, ?)";
            PreparedStatement psBarang = conn.prepareStatement(insertTransaksiBarang);
            psBarang.setString(1, idTransaksi);
            psBarang.setString(2, idBarang);
            psBarang.setInt(3, jumlah);
            psBarang.setBigDecimal(4, harga);
            psBarang.executeUpdate();

            loadTransaksiTable();
            resetForm();
            JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TransaksiForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransaksiForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransaksiForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransaksiForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransaksiForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MenuItemKembali;
    private javax.swing.JMenu MenuOpsi;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> comboBoxBarang;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelJumlah;
    private javax.swing.JLabel labelPilihBarang;
    private javax.swing.JLabel labelharga;
    private javax.swing.JLabel labelidtransaksi;
    private javax.swing.JLabel labelnamabarang;
    private javax.swing.JLabel labeltanggal;
    private javax.swing.JLabel outputLabelHarga;
    private javax.swing.JLabel outputLabelID;
    private javax.swing.JLabel outputLabelNamaBarang;
    private javax.swing.JLabel outputLabelTanggal;
    private javax.swing.JLabel title;
    private javax.swing.JTable transaksiTable;
    private javax.swing.JTextField txtJumlah;
    // End of variables declaration//GEN-END:variables
}
