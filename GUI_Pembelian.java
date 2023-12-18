/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package TugasPrak;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class GUI_Pembelian extends javax.swing.JFrame {

    /**
     * Creates new form GUI_Diskon
     */
    public Connection con;

    double Pembayaran, HargaBarang, Kembali;
    String KodePembayaran, Langganan, NamaPembeli, NamaBarang, Kembalian, Diskon;

    public GUI_Pembelian() {
        initComponents();
        DefaultTableModel dataModel = (DefaultTableModel) tabel_data_pembayaran.getModel();
        int rowCount = dataModel.getRowCount();
        while (rowCount > 0) {
            dataModel.removeRow(rowCount - 1);
            rowCount = dataModel.getRowCount();
        }
        tampil();
    }

    public void koneksi() {
        try {
            con = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/oop_2218101?user=root&password=");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(GUI_Pembelian.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException e) {
            Logger.getLogger(GUI_Pembelian.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception es) {
            Logger.getLogger(GUI_Pembelian.class.getName()).log(Level.SEVERE, null, es);
        }
    }

    public void tampil() {
        DefaultTableModel tabelhead = new DefaultTableModel();
        tabelhead.addColumn("Pembayaran");
        tabelhead.addColumn("Harga Barang");
        tabelhead.addColumn("Kode Pembayaran");
        tabelhead.addColumn("Langganan");
        tabelhead.addColumn("Nama Pembeli");
        tabelhead.addColumn("Nama Barang");
        tabelhead.addColumn("Diskon");
        tabelhead.addColumn("Kembalian");
        try {
            koneksi();
            String sql = "Select * from tb_pembelian";
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                tabelhead.addRow(new Object[]{res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(8), res.getString(7)});
            }
            tabel_data_pembayaran.setModel(tabelhead);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Memuat Data");
        }
    }

    public void insert() {
        try {
            koneksi();
            Pembayaran pb = new Pembayaran();
            Statement st = con.createStatement();
            Pembayaran = Double.parseDouble(txtpembayaran.getText());
            HargaBarang = Double.parseDouble(txthargabarang.getText());
            KodePembayaran = txtkp.getText();
            Langganan = ((String) cmb_langganan.getSelectedItem());
            NamaPembeli = txtnp.getText();
            NamaBarang = txtnb.getText();
            double Kembalian = Pembayaran - HargaBarang;
            double diskon = (HargaBarang*10/100);
            if (cmb_langganan.getSelectedIndex() == 1) {
                Kembalian = Pembayaran - (HargaBarang - diskon);
            } else if (cmb_langganan.getSelectedIndex() == 2) {
                Kembalian = Pembayaran - (HargaBarang - diskon);
            } else {
                Kembalian = Pembayaran - HargaBarang;
            }
            
//            int stokTersisa = sb.cetakTotalBarang();
           
            String sql = "INSERT INTO tb_pembelian (Pembayaran, HargaBarang, KodePembayaran, Langganan, NamaPembeli, NamaBarang, Kembalian,Diskon) VALUES ('" + Pembayaran + "','" + HargaBarang + "','" + KodePembayaran + "','" + Langganan + "','" + NamaPembeli + "','" + NamaBarang + "','" + Kembalian + "','"+diskon+"')";
            st.executeUpdate(sql);
            st.close();
            tampil();
            JOptionPane.showMessageDialog(this, "Data Berhasil Ditambahkan");
            Pembayaran pembayaran = new Pembayaran();
            pembayaran.generateKodePembayaran();
            txtkp.setText(pembayaran.getKodepembayaran());
            clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

     public void hapus(){
        int confirm = JOptionPane.showConfirmDialog(this, "Anda yakin ingin menghapus data ini?","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(confirm ==0){
            try{
                koneksi();
                Statement st = con.createStatement();
                String query = "DELETE FROM tb_pembelian where KodePembayaran = '"+KodePembayaran+"'";
                st.executeUpdate(query);
                st.close();
                tampil();
                JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus");
                clear();
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Gagal Menghapus Data");
            }
        }
    }
     
     public void itemTerpilih(){
        Penjualan sb = new Penjualan();
        txtpembayaran.setText(String.valueOf(Pembayaran));
        txthargabarang.setText(String.valueOf(HargaBarang));
        txtkp.setText(KodePembayaran);
        cmb_langganan.getSelectedItem();
        txtnp.setText(NamaPembeli);
        txtnb.setText(NamaBarang);
        sb.cetakStokBarang();
    }
    
    public void clear() {
        txtpembayaran.setText("");
        txthargabarang.setText("");
        txtnp.setText("");
        txtnb.setText("");
    }
    
    public void update(){
        try {
            koneksi();
            Pembayaran sb = new Pembayaran();
            Statement stat = con.createStatement();
            Pembayaran = Double.parseDouble(txtpembayaran.getText());
            HargaBarang = Double.parseDouble(txthargabarang.getText());
            KodePembayaran = txtkp.getText();
            Langganan = ((String) cmb_langganan.getSelectedItem());
            NamaPembeli = txtnp.getText();
            NamaBarang = txtnb.getText();
           double Kembalian = Pembayaran - HargaBarang;
            double diskon = (HargaBarang*10/100);
            if (cmb_langganan.getSelectedIndex() == 1) {
                Kembalian = Pembayaran - (HargaBarang - diskon);
            } else if (cmb_langganan.getSelectedIndex() == 2) {
                Kembalian = Pembayaran - (HargaBarang - diskon);
            } else {
                Kembalian = Pembayaran - HargaBarang;
            }
            
            String sql = "UPDATE tb_pembelian SET Pembayaran='"+Pembayaran+"',HargaBarang='"+HargaBarang+"',KodePembayaran='"+KodePembayaran+"',Langganan='"+Langganan+"',NamaPembeli='"+NamaPembeli+"',NamaBarang='"+NamaBarang+"',Kembalian='"+Langganan+"', Diskon='"+diskon+"'";
            stat.executeUpdate(sql);
            stat.close();
            tampil();
            JOptionPane.showMessageDialog(this, "Data Berhasil Diperbarui");
            clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }      
    }
    
    public void cari(){
        DefaultTableModel tabelhead = new DefaultTableModel();
        tabelhead.addColumn("Pembayaran");
        tabelhead.addColumn("Harga Barang");
        tabelhead.addColumn("Kode Pembayaran");
        tabelhead.addColumn("Langganan");
        tabelhead.addColumn("Nama Pembeli");
        tabelhead.addColumn("Nama Barang");
        tabelhead.addColumn("Kembalian");
        String search = txtcari.getText();
        try {
            koneksi();
            String sql = "SELECT * FROM tb_pembelian where Pembayaran LIKE '%"+search+"%' OR HargaBarang LIKE '%"+search+"%' OR KodePembayaran='"+search+"%' OR Langganan='"+search+"%' OR NamaBarang='"+search+"%' OR Kembalian='"+search+"'";
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                tabelhead.addRow(new Object[]{res.getString("ID"),res.getString("TotalBarang"), res.getString("BarangTerbeli"), res.getString("NamaProduk"), res.getString("StockTersisa")});
                
            }
            tabel_data_pembayaran.setModel(tabelhead);
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

        txthargabarang = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtkp = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cmb_langganan = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_data_pembayaran = new javax.swing.JTable();
        txtpembayaran = new javax.swing.JTextField();
        btn_battal = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btnbayar = new javax.swing.JToggleButton();
        btnclose = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtnp = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtnb = new javax.swing.JTextField();
        btnupdate = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel4.setText("KODE PEMBAYARAN");

        Pembayaran pembayaran = new Pembayaran();
        pembayaran.generateKodePembayaran();
        String kp = pembayaran.getKodepembayaran();        
        txtkp.setText(kp);
        txtkp.setEditable(false);
        txtkp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkpActionPerformed(evt);
            }
        });

        jLabel1.setText("PEMBELIAN");

        cmb_langganan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak Langganan", "Langganan", "Member", " " }));
        cmb_langganan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_langgananActionPerformed(evt);
            }
        });

        jLabel2.setText("PEMBAYARAN");

        tabel_data_pembayaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "PEMBAYARAN", "HARGA BARANG", "KODE PEMBAYARAN", "LANGGANAN", "NAMA PEMBELI", "NAMA BARANG", "KEMBALIAN", "DISKON"
            }
        ));
        tabel_data_pembayaran.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                tabel_data_pembayaranComponentAdded(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_data_pembayaran);

        btn_battal.setText("BATAL");
        btn_battal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_battalActionPerformed(evt);
            }
        });

        btn_hapus.setText("HAPUS");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        btnbayar.setText("BAYAR");
        btnbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbayarActionPerformed(evt);
            }
        });

        btnclose.setText("CLOSE");
        btnclose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncloseActionPerformed(evt);
            }
        });

        jLabel3.setText("HARGA BARANG");

        jLabel5.setText("NAMA PEMBELI");

        jLabel6.setText("NAMA BARANG");

        btnupdate.setText("UPDATE");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        jLabel7.setText("SEARCH");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txthargabarang, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtkp)
                            .addComponent(txtpembayaran)
                            .addComponent(txtnp)
                            .addComponent(txtnb))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmb_langganan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnupdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_battal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnbayar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnclose, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                    .addComponent(btn_hapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(370, 370, 370))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtpembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_langganan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txthargabarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_battal)
                    .addComponent(btn_hapus))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtkp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(btnbayar)
                    .addComponent(btnclose))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnupdate))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtnb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtkpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkpActionPerformed

    private void cmb_langgananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_langgananActionPerformed
        // TODO add your handling code here
    }//GEN-LAST:event_cmb_langgananActionPerformed

    private void btn_battalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_battalActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btn_battalActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
       hapus();
        /*DefaultTableModel dataModel = (DefaultTableModel) tabel_data_pembayaran.getModel();
        int rowCount = dataModel.getRowCount();
        while (rowCount > 0) {
            dataModel.removeRow(rowCount - 1);
            rowCount = dataModel.getRowCount();
        }*/
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btnbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbayarActionPerformed
        // TODO add your handling code here:
        insert();
       /* try {
            DefaultTableModel dataModel = (DefaultTableModel) tabel_data_pembayaran.getModel();
            List list = new ArrayList<>();
            tabel_data_pembayaran.setAutoCreateColumnsFromModel(true);
            Pembayaran bayar = new DiskonMember();
            bayar.dataHarga(Integer.parseInt(txthargabarang.getText()));
            bayar.dataPembayaran(Integer.parseInt(txtpembayaran.getText()));
            bayar.setKodepembayaran(txtkp.getText());
            bayar.dataPelanggan((String) cmb_langganan.getSelectedItem());
            bayar.dataPembeli(txtnp.getText());
            bayar.dataProduk(txtnb.getText());
            bayar.diskon();
            list.add(bayar.cetakHarga());
            list.add(bayar.cetakPembayaran());
            list.add(bayar.cetakKodePembayaran());
            list.add(bayar.cetakPelanggan());
            list.add(bayar.cetakPembeli());
            list.add(bayar.cetakProduk());
            list.add(bayar.cetakTotal());
            dataModel.addRow(list.toArray());
            Pembayaran pembayaran = new Pembayaran();
            pembayaran.generateKodePembayaran();
            String kp = pembayaran.getKodepembayaran();
            txtkp.setText(kp);

            if (cmb_langganan.getSelectedIndex() == 0) {
                Pembayaran pb = new Pembayaran();
                pb.dataPembayaran(Integer.parseInt(txtpembayaran.getText()));
                pb.dataHarga(Integer.parseInt(txthargabarang.getText()));
                pb.setkodepembayaran(txtkp.getText());
            } else if (cmb_langganan.getSelectedIndex() == 1) {
                Pembayaran pbl = new Pembayaran();
                pbl.dataPembayaran(Integer.parseInt(txtpembayaran.getText()));
                pbl.dataHarga(Integer.parseInt(txthargabarang.getText()));
                pbl.setkodepembayaran(txtkp.getText());
            } else {
                DiskonMember dm = new DiskonMember();
                dm.dataPembayaran(Integer.parseInt(txtpembayaran.getText()));
                dm.dataHarga(Integer.parseInt(txthargabarang.getText()));
                dm.setkodepembayaran(txtkp.getText());
            }
            JOptionPane.showMessageDialog(null, "Data anda Ditambahkan Ke tabel");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(rootPane, "Harga Barang Diisi Dengan Angka!!");
        } finally {
            System.out.println("Program Berhasil Berjalan");
        }

        //Pembayaran bayar = new Pembayaran();
        //bayar.dataPembayaran(Integer.parseInt(txtpembayaran.getText()));
        //bayar.dataHarga(Integer.parseInt(txthargabarang.getText()));
        //bayar.setkodepembayaran(txtkp.getText());
        //memopembayaran.append("Kode Pembayaran = " +bayar.);*/

    }//GEN-LAST:event_btnbayarActionPerformed

    private void btncloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncloseActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btncloseActionPerformed

    private void tabel_data_pembayaranComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_tabel_data_pembayaranComponentAdded
        // TODO add your handling code here:
        int index = tabel_data_pembayaran.getSelectedRow();
        Pembayaran = Double.parseDouble(tabel_data_pembayaran.getValueAt(index, 0).toString());
        HargaBarang = Double.parseDouble(tabel_data_pembayaran.getValueAt(index, 1).toString());
        KodePembayaran = tabel_data_pembayaran.getValueAt(index, 2).toString();
        itemTerpilih();
        NamaPembeli = tabel_data_pembayaran.getValueAt(index, 4).toString();
        NamaBarang = tabel_data_pembayaran.getValueAt(index, 5).toString();
        Diskon = tabel_data_pembayaran.getValueAt(index, 6).toString();
        Kembalian = tabel_data_pembayaran.getValueAt(index, 7).toString();
    }//GEN-LAST:event_tabel_data_pembayaranComponentAdded

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnupdateActionPerformed

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
            java.util.logging.Logger.getLogger(GUI_Pembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_Pembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_Pembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_Pembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_Pembelian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_battal;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JToggleButton btnbayar;
    private javax.swing.JToggleButton btnclose;
    private javax.swing.JButton btnupdate;
    private javax.swing.JComboBox<String> cmb_langganan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabel_data_pembayaran;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txthargabarang;
    private static javax.swing.JTextField txtkp;
    private javax.swing.JTextField txtnb;
    private javax.swing.JTextField txtnp;
    private javax.swing.JTextField txtpembayaran;
    // End of variables declaration//GEN-END:variables
}
