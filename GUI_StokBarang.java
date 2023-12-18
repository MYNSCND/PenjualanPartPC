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
public class GUI_StokBarang extends javax.swing.JFrame {

    /**
     * Creates new form GUI_StokBarang
     */
    public Connection con;
    
    String NamaProduk;
    int TotalBarang, BarangTerbeli, StockTersisa, ID;
    
    public GUI_StokBarang() {
        initComponents();
        DefaultTableModel dataModel = (DefaultTableModel) tabel_stok_barang.getModel();
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
            Logger.getLogger(GUI_StokBarang.class.getName()).log(Level.SEVERE, null, e);
        }catch (SQLException e) {
            Logger.getLogger(GUI_StokBarang.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception es) {
            Logger.getLogger(GUI_StokBarang.class.getName()).log(Level.SEVERE, null, es);
        }
    }
   
    
    public void tampil(){
        DefaultTableModel tabelhead = new DefaultTableModel();
        tabelhead.addColumn("ID");
        tabelhead.addColumn("Total Barang");
        tabelhead.addColumn("Barang Terbeli");
        tabelhead.addColumn("Nama Produk");
        tabelhead.addColumn("Stok Tersisa");
        try
        {
        koneksi();
        String sql = "Select * from GUI_StokBarang";
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery(sql);
        while(res.next()){
            tabelhead.addRow(new Object[]{res.getString("ID"),res.getString(1),res.getString(2),res.getString(3),res.getString(4)});
        }
        tabel_stok_barang.setModel(tabelhead);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Gagal Memuat Data");
        }
    }
    
    public void insert(){
        try
        {
            koneksi();
            StokBarang sb = new StokBarang();
            Statement st = con.createStatement();
            TotalBarang = Integer.parseInt(txtbarang.getText());
            BarangTerbeli = Integer.parseInt(txtbarangterbeli.getText());
            NamaProduk = txtnamaproduk.getText();
            sb.stokbarang = TotalBarang;
            sb.barang = BarangTerbeli;
            int stokTersisa = sb.cetakTotalBarang();
            
            String sql = "INSERT INTO gui_stokbarang (TotalBarang, BarangTerbeli, NamaProduk, StockTersisa) VALUES ('"+TotalBarang+"','"+BarangTerbeli+"','"+NamaProduk+"','"+stokTersisa+"')";
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
                String query = "DELETE FROM gui_stokbarang where ID = '"+ID+"'";
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
        StokBarang sb = new StokBarang();
        txtbarang.setText(String.valueOf(TotalBarang));
        txtbarangterbeli.setText(String.valueOf(BarangTerbeli));
        txtnamaproduk.setText(NamaProduk);
        sb.cetakTotalBarang() ;
    }
    
    public void clear() {
    txtbarang.setText("");
    txtbarangterbeli.setText("");
    txtnamaproduk.setText("");
 }

    public void update(){
        try {
            koneksi();
            StokBarang sb = new StokBarang();
            Statement stat = con.createStatement();
            TotalBarang = Integer.parseInt(txtbarang.getText());
            BarangTerbeli = Integer.parseInt(txtbarangterbeli.getText());
            NamaProduk = txtnamaproduk.getText();
            sb.stokbarang = TotalBarang;
            sb.barang = BarangTerbeli;
            int stokTersisa = sb.cetakTotalBarang();
            String sql = "UPDATE gui_stokbarang SET TotalBarang='"+TotalBarang+"',BarangTerbeli='"+BarangTerbeli+"',NamaProduk='"+NamaProduk+"',StockTersisa='"+StockTersisa+"' where ID = '"+ID+"'";
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
        tabelhead.addColumn("Total Barang");
        tabelhead.addColumn("Barang Terbeli");
        tabelhead.addColumn("Nama Produk");
        tabelhead.addColumn("Kembalian");
        String search = txtsearch.getText();
        try {
            koneksi();
            String sql = "SELECT * FROM gui_stokbarang where BarangTerbeli LIKE '%"+search+"%' OR NamaProduk LIKE '%"+search+"%' OR StockTersisa='"+search+"'";
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                tabelhead.addRow(new Object[]{res.getString("ID"),res.getString("TotalBarang"), res.getString("BarangTerbeli"), res.getString("NamaProduk"), res.getString("StockTersisa")});
                
            }
            tabel_stok_barang.setModel(tabelhead);
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
        txtbarang = new javax.swing.JTextField();
        txtbarangterbeli = new javax.swing.JTextField();
        txtnamaproduk = new javax.swing.JTextField();
        btncetak = new javax.swing.JButton();
        btnclose = new javax.swing.JButton();
        btnbatal = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_stok_barang = new javax.swing.JTable();
        btnupdate = new javax.swing.JButton();
        txtsearch = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("STOK BARANG");

        jLabel2.setText("TOTAL BARANG");

        jLabel3.setText("BARANG YANG TERBELI");

        jLabel4.setText("NAMA PRODUK");

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

        btnbatal.setText("BATAL");
        btnbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbatalActionPerformed(evt);
            }
        });

        btnhapus.setText("HAPUS");
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });

        tabel_stok_barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "TOTAL BARANG", "BARANG TERBELI", "NAMA PRODUK", "STOCK TERSISA"
            }
        ));
        tabel_stok_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_stok_barangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_stok_barang);

        btnupdate.setText("UPDATE");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        txtsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtsearchKeyPressed(evt);
            }
        });

        jLabel5.setText("SEARCH");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtbarangterbeli, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtbarang)
                                    .addComponent(txtnamaproduk))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btncetak)
                                            .addComponent(btnbatal))
                                        .addGap(26, 26, 26)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnhapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnclose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(btnupdate))))
                        .addGap(14, 14, 14))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtbarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnupdate))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtbarangterbeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbatal)
                    .addComponent(btnhapus))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtnamaproduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncetak)
                    .addComponent(btnclose))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btncloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncloseActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btncloseActionPerformed

    private void btncetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncetakActionPerformed
        // TODO add your handling code here:
//        JOptionPane.showMessageDialog(null, "Data anda Ditambahkan Ke tabel");
//        DefaultTableModel dataModel = (DefaultTableModel)
//        tabel_stok_barang.getModel();
//        List list = new ArrayList<>();
//        tabel_stok_barang.setAutoCreateColumnsFromModel(true);
//        StokBarang bayar = new StokBarang();
//        bayar.dataStokBarang(Integer.parseInt(txtbarang.getText()));
//        bayar.dataBarang(Integer.parseInt(txtbarangterbeli.getText()));
//        bayar.dataProduk(txtnamaproduk.getText());
//        list.add(bayar.cetakStokBarang());
//        list.add(bayar.cetakBarang());
//        list.add(bayar.cetakProduk());
//        list.add(bayar.cetakTotalBarang());
//        dataModel.addRow(list.toArray());
insert();
        
        /*StokBarang sb = new StokBarang();
        sb.dataProduk(txtnamaproduk.getText());
        sb.dataBarang(Integer.parseInt(txtbarangterbeli.getText()));
        sb.dataStokBarang(Integer.parseInt(txtbarang.getText()));
        memostok.append("STOK BARANG TERSEDIA\n");
        memostok.append("====================\n");
        memostok.append("Nama Barang : "+sb.cetakProduk()+"\n");
        memostok.append("Total Barang : "+sb.cetakStokBarang()+"\n");
        memostok.append("Barang Terbeli : "+sb.cetakBarang()+"\n");
        memostok.append("====================\n");
        memostok.append("Stock Tersisa = " + sb.cetakTotalBarang()+ "\n");*/
    }//GEN-LAST:event_btncetakActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        // TODO add your handling code here:
//        DefaultTableModel dataModel = (DefaultTableModel) tabel_stok_barang.getModel();
//        int rowCount = dataModel.getRowCount();
//        while (rowCount>0){
//            dataModel.removeRow(rowCount - 1);
//            rowCount= dataModel.getRowCount();
//        }
hapus();
    }//GEN-LAST:event_btnhapusActionPerformed

    private void btnbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbatalActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnbatalActionPerformed

    private void tabel_stok_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_stok_barangMouseClicked
        // TODO add your handling code here:
        int index = tabel_stok_barang.getSelectedRow();
        ID = Integer.parseInt(tabel_stok_barang.getValueAt(index, 0).toString());
        TotalBarang = Integer.parseInt(tabel_stok_barang.getValueAt(index, 1).toString());
        BarangTerbeli  = Integer.parseInt(tabel_stok_barang.getValueAt(index, 2).toString());
        NamaProduk = tabel_stok_barang.getValueAt(index, 3).toString();
        StockTersisa = Integer.parseInt(tabel_stok_barang.getValueAt(index, 4).toString());
        itemTerpilih();
    }//GEN-LAST:event_tabel_stok_barangMouseClicked

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnupdateActionPerformed

    private void txtsearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsearchKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            cari();
        }
    }//GEN-LAST:event_txtsearchKeyPressed

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
            java.util.logging.Logger.getLogger(GUI_StokBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_StokBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_StokBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_StokBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_StokBarang().setVisible(true);
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabel_stok_barang;
    private javax.swing.JTextField txtbarang;
    private javax.swing.JTextField txtbarangterbeli;
    private javax.swing.JTextField txtnamaproduk;
    private javax.swing.JTextField txtsearch;
    // End of variables declaration//GEN-END:variables
}
