/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TugasPrak;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.random.RandomGenerator;

/**
 *
 * @author USER
 */
public class Pembayaran extends Penjualan{
    double pembayaran, totalbayar, diskon, total = 0;
    private String kodepembayaran; 
    String Pelanggan;
    
void dataPembayaran (int Pembayaran){
     this.pembayaran = Pembayaran;
 }
double cetakPembayaran (){
return pembayaran;
 }

/*public Pembayaran(){
    this.kodepembayaran = "KP001";
}*/

public String getKodepembayaran() {
    return kodepembayaran;
}

public void setKodepembayaran(String kodepembayaran) {
    this.kodepembayaran = kodepembayaran;
}



public void setkodepembayaran(String kodepembayaran){
    this.kodepembayaran = kodepembayaran;
}

public void generateKodePembayaran(){
    LocalDateTime ld = LocalDateTime.now();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyMMddHHmmss");
    String kd = ld.format(dtf);
    this.kodepembayaran = "DS-"+kd;
}

String cetakKodePembayaran (){
return kodepembayaran;
}

double diskon()
{
    diskon = harga;
    return total;   
}

double hitungLangganan(double langganan)
{
    diskon = harga * langganan;
    total = diskon;
    return total ;
}

public double cetakTotal(){
    totalbayar = (cetakPembayaran() - cetakHarga()) + total;
    return totalbayar;
 }

void dataPelanggan(String Pelanggan){
        this.Pelanggan = Pelanggan;
 }
String cetakPelanggan(){
        return Pelanggan;
    }
}

