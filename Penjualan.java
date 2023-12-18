package TugasPrak;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author USER
 */
public class Penjualan {
    String namaproduk, namapembeli;
    int harga, garansi, stokbarang;
    
 void dataProduk (String Namaproduk){
        this.namaproduk = Namaproduk;
    }
 void dataPembeli (String Namapembeli){
     this.namapembeli = Namapembeli;
 }
 void dataHarga (int Harga){
     this.harga = Harga;
 }
 void dataStokBarang (int Stokbarang){
     this.stokbarang = Stokbarang;
 }
 String cetakProduk (){
     return namaproduk;
 }
 String cetakPembeli (){
     return namapembeli;
 }
 int cetakHarga (){
     return harga;
 }
 int cetakStokBarang (){
     return stokbarang;
 }
 public Penjualan (){
     this.garansi = 3;
     //this.stokbarang = 10;
 }
 
 public int cetakPenjualan(){
     return garansi;
 }
 
}
