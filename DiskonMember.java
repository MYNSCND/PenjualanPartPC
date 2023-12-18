/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TugasPrak;

/**
 *
 * @author USER
 */
public class DiskonMember extends Diskon{
    
    @Override
    double diskon (){
        diskon = harga * 0.2;
        totaldiskon = total = diskon;
      
        return total ;
    }
    
}
