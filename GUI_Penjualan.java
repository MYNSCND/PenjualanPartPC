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
public class GUI_Penjualan extends javax.swing.JFrame {

    /**
     * Creates new form GUI_Penjualan
     */
    public Connection con;
    
    String NamaProduk, NamaPembeli;
    int Harga, StokBarang, Garansi, ID;
    
    public GUI_Penjualan() {
        initComponents();
        Penjualan pc = new Penjualan();
        txtgaransi.setText(Integer.toString(pc.garansi));
        txtgaransi.setEnabled(true);
        DefaultTableModel dataModel = (DefaultTableModel) tabel_penjualan.getModel();
        int rowCount = dataModel.getRowCount();
        while (rowCount > 0){
            dataModel.removeRow(rowCount - 1);
            rowCount = dataModel.getRowCount();
        }
    tampil();
}
    public  void koneksi(){
        try
        {
        con = null; 
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/oop_2218101?user=root&password=");
      }catch(ClassNotFoundException e){
            Logger.getLogger(GUI_Penjualan.class.getName()).log(Level.SEVERE, null, e);
        }catch (SQLException e) {
            Logger.getLogger(GUI_Penjualan.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception es) {
            Logger.getLogger(GUI_Penjualan.class.getName()).log(Level.SEVERE, null, es);
        }
    }
    
    public void tampil(){
        DefaultTableModel tabelhead = new DefaultTableModel();
        tabelhead.addColumn("ID");
        tabelhead.addColumn("Nama Produk");
        tabelhead.addColumn("Harga");
        tabelhead.addColumn("Nama Pembeli");
        tabelhead.addColumn("Stok Barang");
        tabelhead.addColumn("Garansi");
        try
        {
        koneksi();
        String sql = "Select * from tb_penjualan";
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery(sql);
        while(res.next()){
            tabelhead.addRow(new Object[]{res.getString("ID"),res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5)});
        }
        tabel_penjualan.setModel(tabelhead);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Gagal Memuat Data");
        }
    }
    
    public void insert(){
        try
        {
            koneksi();
            Statement st = con.createStatement();
            NamaProduk = txtnp.getText();
            Harga = Integer.parseInt(txtharga.getText());
            NamaPembeli = txtnpem.getText();
            StokBarang = Integer.parseInt(txtstok.getText());
            Garansi = Integer.parseInt(txtgaransi.getText());
            
            String sql = "INSERT INTO tb_penjualan (NamaProduk, Harga, NamaPembeli, StokBarang, Garansi) VALUES ('"+NamaProduk+"','"+Harga+"','"+NamaPembeli+"','"+StokBarang+"','"+Garansi+"')";
            st.executeUpdate(sql);
            st.close();
            tampil();
            JOptionPane.showMessageDialog(this, "Data Berhasil Ditambahkan");
            clear();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapus(){
        int confirm = JOptionPane.showConfirmDialog(this, "Anda yakin ingin menghapus data ini?","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(confirm ==0){
            try{
                koneksi();
                Statement st = con.createStatement();
                String query = "DELETE FROM tb_penjualan where ID = '"+ID+"'";
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
        Penjualan pj = new Penjualan();
        txtnp.setText(NamaProduk);
        txtharga.setText(String.valueOf(Harga));
        txtnpem.setText(NamaPembeli);
        txtstok.setText(String.valueOf(StokBarang));
        txtgaransi.setText(String.valueOf(Garansi));
    }
    
    public void clear() {
    txtnp.setText("");
    txtharga.setText("");
    txtnpem.setText("");
    txtstok.setText("");
    txtgaransi.setText("");
 }
    
    public void update(){
        try {
            koneksi();
            Penjualan sb = new Penjualan();
            Statement stat = con.createStatement();
            NamaProduk = txtnp.getText();
            Harga = Integer.parseInt(txtharga.getText());
            NamaPembeli = txtnpem.getText();
            StokBarang = Integer.parseInt(txtstok.getText());
            Garansi = Integer.parseInt(txtgaransi.getText());
            String sql = "UPDATE tb_penjualan SET NamaProduk='"+NamaProduk+"',Harga='"+Harga+"',NamaPembeli='"+NamaPembeli+"',StokBarang='"+StokBarang+"',Garansi='"+Garansi+"' where ID = '"+ID+"'";
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
        tabelhead.addColumn("ID");
        tabelhead.addColumn("Nama Produk");
        tabelhead.addColumn("Harga");
        tabelhead.addColumn("Nama Pembeli");
        tabelhead.addColumn("Stok Barang");
        tabelhead.addColumn("Garansi");
        String search = txtcari.getText();
        try {
            koneksi();
            String sql = "SELECT * FROM tb_penjualan where NamaProduk LIKE '%"+search+"%' OR Harga LIKE '%"+search+"%' OR NamaPembeli='"+search+"%' OR StokBarang='"+search+"%' OR Garansi='"+search+"'";
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                tabelhead.addRow(new Object[]{res.getString("ID"),res.getString("TotalBarang"), res.getString("BarangTerbeli"), res.getString("NamaProduk"), res.getString("StockTersisa")});
                
            }
            tabel_penjualan.setModel(tabelhead);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtnp = new javax.swing.JTextField();
        txtharga = new javax.swing.JTextField();
        txtnpem = new javax.swing.JTextField();
        txtstok = new javax.swing.JTextField();
        txtgaransi = new javax.swing.JTextField();
        btncetak = new javax.swing.JButton();
        btnclose = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        btnbatal = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_penjualan = new javax.swing.JTable();
        btnupdate = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("PENJUALAN KOMPONEN PC");

        jLabel2.setText("NAMA PRODUK");

        jLabel3.setText("HARGA");

        jLabel4.setText("NAMA PEMBELI");

        jLabel5.setText("STOK BARANG");

        jLabel6.setText("GARANSI");

        btncetak.setText("CETAK");
        btncetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncetakActionPerformed(evt);
            }
        });

        btnclose.setText("CLOSE");
        btnclose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncloseActionPerformed(evt);
            }
        });

        btnhapus.setText("HAPUS");
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });

        btnbatal.setText("BATAL");
        btnbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbatalActionPerformed(evt);
            }
        });

        tabel_penjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "NAMA PRODUK", "HARGA", "NAMA PEMBELI", "STOK BARANG", "GARANSI"
            }
        ));
        tabel_penjualan.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                tabel_penjualanComponentAdded(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_penjualan);

        btnupdate.setText("UPDATE");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });

        jLabel7.setText("SEARCH");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtnp)
                            .addComponent(txtharga)
                            .addComponent(txtnpem)
                            .addComponent(txtstok, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtgaransi, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btncetak)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnclose))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnhapus)
                        .addGap(86, 86, 86)
                        .addComponent(btnbatal))
                    .addComponent(btnupdate))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtnp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtnpem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtstok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtgaransi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btncetak)
                            .addComponent(btnclose))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnhapus)
                            .addComponent(btnbatal))
                        .addGap(18, 18, 18)
                        .addComponent(btnupdate))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btncetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncetakActionPerformed
        // TODO add your handling code here:
        insert();
//        JOptionPane.showMessageDialog(null, "Data anda Ditambahkan Ke tabel");
//        DefaultTableModel dataModel = (DefaultTableModel)
//        tabel_penjualan.getModel();
//        List list = new ArrayList<>();
//        tabel_penjualan.setAutoCreateColumnsFromModel(true);
//        Penjualan pc = new Penjualan();
//        pc.dataProduk(txtnp.getText());
//        pc.dataHarga(Integer.parseInt(txtharga.getText()));
//        pc.dataPembeli(txtnpem.getText());
//        pc.dataStokBarang(Integer.parseInt(txtstok.getText()));
//        list.add(pc.cetakProduk());
//        list.add(pc.cetakHarga());
//        list.add(pc.cetakPembeli());
//        list.add(pc.cetakStokBarang());
//        list.add(pc.cetakPenjualan() + "TAHUN");
//        dataModel.addRow(list.toArray());
        
       /* memonota.setText(" ");
        Penjualan pc = new Penjualan();
        pc.dataProduk(txtnp.getText());
        pc.dataPembeli(txtnpem.getText());
        pc.dataHarga(Integer.parseInt(txtharga.getText()));
        pc.dataStokBarang(Integer.parseInt(txtstok.getText()));
        memonota.append("NOTA PENJUALAN PART PC \n");
        memonota.append("====================== \n");
        memonota.append("Nama Produk    : " + pc.cetakProduk() + "\n");
        memonota.append("Nama Pembeli  : " + pc.cetakPembeli()+ "\n");
        memonota.append("Harga                : " + pc.cetakHarga()+ "\n");
        memonota.append("Stok Barang      : " + pc.cetakStokBarang()+ "\n");
        memonota.append("====================== \n");
        memonota.append("GARANSI AKTIF " + pc.garansi+ " TAHUN" + "\n");
        memonota.append("====================== \n"); */
    }//GEN-LAST:event_btncetakActionPerformed

    private void btncloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncloseActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btncloseActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        // TODO add your handling code here:
        hapus();
    }//GEN-LAST:event_btnhapusActionPerformed

    private void btnbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbatalActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnbatalActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnupdateActionPerformed

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
         cari();
    }//GEN-LAST:event_txtcariActionPerformed

    private void tabel_penjualanComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_tabel_penjualanComponentAdded
        // TODO add your handling code here:
        int index = tabel_penjualan.getSelectedRow();
        ID = Integer.parseInt(tabel_penjualan.getValueAt(index, 0).toString());
        NamaProduk = tabel_penjualan.getValueAt(index, 1).toString();
        Harga  = Integer.parseInt(tabel_penjualan.getValueAt(index, 2).toString());
        NamaPembeli = tabel_penjualan.getValueAt(index, 3).toString();
        StokBarang = Integer.parseInt(tabel_penjualan.getValueAt(index, 4).toString());
        Garansi = Integer.parseInt(tabel_penjualan.getValueAt(index, 5).toString());
        itemTerpilih();
    }//GEN-LAST:event_tabel_penjualanComponentAdded

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
            java.util.logging.Logger.getLogger(GUI_Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_Penjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbatal;
    private javax.swing.JButton btncetak;
    private javax.swing.JButton btnclose;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnupdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabel_penjualan;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtgaransi;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtnp;
    private javax.swing.JTextField txtnpem;
    private javax.swing.JTextField txtstok;
    // End of variables declaration//GEN-END:variables
}
