/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TugasPrak;

/**
 *
 * @author USER
 */
public class StokBarang extends Penjualan{
        int barang, totalbarang;
        
public void dataBarang (int Barang){
this.barang = Barang;
 }

int cetakBarang(){
return barang;
 }

public int cetakTotalBarang(){
totalbarang = (cetakStokBarang()- cetakBarang());
return totalbarang;
        
 }

        //public int dikurangiStok(){
        //barang = (cetakStokBarang()- 1);
        //return barang;
        
    //}
       
}
