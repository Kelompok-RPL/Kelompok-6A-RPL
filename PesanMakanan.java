/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Kelompok 6
 * - Fazreen Nurul Hikam Pambudi
 * - Bob Raozal
 * - Anisa
 * - M. Malvino Rozi
 * - Shofian Ramadhan
 */
public class PesanMakanan {
    private static final String URL = "jdbc:mysql://localhost:3306/pesan_makanan";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static Connection connection;
    private static PreparedStatement tambahData;

    public static void main(String[] args) {
    
    Scanner input = new Scanner(System.in);
        Scanner pilihan = new Scanner(System.in);
    ArrayList<String> pesanan = new ArrayList<String>();
    ArrayList<Integer> harga_pesanan = new ArrayList<Integer>();
    Random r = new Random();
    
    //List Menu
    String [] menu = {" ", "Nasi Goreng", "Mie Goreng", "Mie Ayam", "Seblak", "Bakso", "Es Teh Manis", "Lemon Tea", "Air Mineral"};
    int [] h_menu = {0, 18000, 15000, 17000, 15000, 17000, 5000, 7000, 3000};

    try{
        connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }catch (SQLException e){ 
        throw new RuntimeException(e);
    }

    //Data diri pengguna yang diperlukan
    String nama;
    String noHP;
    String alamat;

    //Tampilan Menu
    System.out.println("Selamat Datang Di Apilikasi Pesan Makanan Kelompok 6");
    System.out.println("Berikut adalah menu yang tersedia");
    System.out.println("----------------- MENU ----------------");
    System.out.println("No |Item                       |Harga");
    System.out.println("1. Nasi Goreng_________________Rp.18000");
    System.out.println("2. Mie Goreng__________________Rp.15000");
    System.out.println("3. Mie Ayam____________________Rp.17000");
    System.out.println("4. Seblak______________________RP.15000");
    System.out.println("5. Bakso_______________________Rp.17000");
    System.out.println("6. Es Teh Manis________________Rp.5000");
    System.out.println("7. Lemon Tea___________________Rp.7000");
    System.out.println("8. Air Mineral_________________Rp.3000\n\n");
        
        System.out.println("Apa yang ingin anda lakukan?");
        System.out.println("1. Lakukan Pemesanan");
        System.out.println("2. Lihat Riwayat Pemesanan");
        System.out.print("\nPilih :");
        int pilih1 = pilihan.nextInt();
        
        
        if(pilih1 == 1){
            
            String yon; //pilihan yes or no
            int item;   //pilihan menu
            int n;      //jumlah yang ingin dipesan
            int totalharga = 0;
            boolean status = true;
            String kodeTransaksi;
            int nilaiacak = 1 + r.nextInt(10000);
            String metode = "";
            do{
                    System.out.print("Lanjutkan Pemesnaan Makanan (Y/N) ? ");
                    yon = input.nextLine();
                    if(yon.equalsIgnoreCase("Y") || yon.equalsIgnoreCase("Yes")){

                            System.out.print("Masukkan Nama Pemesan     :"); nama   = input.nextLine();
                            System.out.print("Masukkan Nomor HP     :"); noHP   = input.nextLine();
                            System.out.print("Masukkan alamat   :"); alamat = input.nextLine();

                            boolean status2 = true;
                            String yon2;
                            String yon3;
                            do{
                                    System.out.print("\n\nApa yang ingin anda Pesan ? (Masukkan Nomor item Pada Menu) ");
                                    item = input.nextInt();
                                    if(item > 0 && item < menu.length){
                                        System.out.print("Berapa jumlah " +  menu[item] + " yang anda inginkan? ");
                                        n = input.nextInt();

                                        for (int i=1; i <= n; i++){
                                                pesanan.add(menu[item]);
                                                harga_pesanan.add(h_menu[item]);
                                        }

                                        //cek pesanan
                                        //System.out.println(pesanan.toString());
                                        //System.out.println(harga_pesanan.toString());
                                        System.out.print("\n\nApakah masih ada tambahan (Y/N) ? ");
                                        yon2 = input.next();

                                        if(yon2.equalsIgnoreCase("N") || yon2.equalsIgnoreCase("No")){
                                                status2 = false;
                                        }

                                    }else{
                                        System.out.println("Maaf pilihan tidak tersedia");
                                    }

                            }while(status2);

                            System.out.println("Berikut Pesanan Yang anda buat\n");
                            System.out.println("Item           |Harga");
                            for(int i = 0; i < pesanan.size(); i++){
                                    System.out.println(pesanan.get(i)+"\t"+harga_pesanan.get(i));
                                    totalharga = totalharga + harga_pesanan.get(i);
                            }
                            System.out.println("Total Harga:\t" + totalharga);

                            System.out.print("\n\nKorfirmasi Pemesanan anda (Y/N) ? ");
                            yon3 = input.next();
                            kodeTransaksi = (nama.substring(0,2)).toUpperCase() + noHP.substring(noHP.length()-2) + (alamat.substring(alamat.length()-2)).toUpperCase() + nilaiacak;
                            try {
                                tambahData = connection.prepareStatement("INSERT INTO transaksi (Kode_Transaksi, Nama_Pemesan, No_Telp, Alamat, Daftar_Pesanan, Total_Harga)" + " VALUES (?, ?, ?, ?, ?, ?)");
                                tambahData.setString(1, kodeTransaksi);
                                tambahData.setString(2, nama);
                                tambahData.setString(3, noHP);
                                tambahData.setString(4, alamat);
                                tambahData.setString(5, pesanan.toString());
                                tambahData.setInt(6, totalharga);
                                int result = tambahData.executeUpdate();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                            //cek kode
                            //System.out.println(kodeTransaksi);

                            if(yon3.equalsIgnoreCase("Y") || yon3.equalsIgnoreCase("Yes")){
                                    System.out.println("\nPilih metode pembayaran anda");
                                    System.out.println("1. Debit");
                                    System.out.println("2. Tunai");
                                    boolean yon4 = true;
                                    
                                    do{
                                        System.out.println("\nPilih :");
                                        int pilih2 = pilihan.nextInt();
                                        if (pilih2 == 1){
                                            metode = "Debit";
                                            yon4 = false;
                                        }else if (pilih2 == 2){
                                            metode = "Tunai";
                                            yon4 = false;
                                        }else{
                                            System.out.println("Maaf masukkan tidak sesuai");
                                        }
                                    }while(yon4);
                                    
                                    System.out.println("\nBerikut Kupon Pemesanan Anda");
                                    System.out.println("Tunjukkan Kupon pada kurir sebagai bukti pemesanan\n");
                                    
                                    System.out.println("===========================================");
                                    System.out.println("Terima Kasih telah melakukan Pemesanan :D");
                                    System.out.println("============================================");
                                    System.out.println("Kode Transaksi      : "+ kodeTransaksi);
                                    System.out.println("Nama Pemesan        : "+ nama);
                                    System.out.println("No. Telp            : "+ noHP);
                                    System.out.println("Alamat              : "+ alamat);
                                    System.out.println("Berikut Daftar Pesanan Anda");
                                    System.out.println("Item           |Harga");
                                    for(int i = 0; i < pesanan.size(); i++){
                                        System.out.println(pesanan.get(i)+"\t"+harga_pesanan.get(i));
                                        totalharga = totalharga + harga_pesanan.get(i);
                                    }
                                    System.out.println("Total Harga         : "+ totalharga);
                                    System.out.println("Metode Pembayaran   : "+ metode);

                            }else{
                                    System.out.println("\nPesanan Anda Akan dibatalakan");
                                    System.out.println("Terima Kasih Telah Menggunakan");
                                    System.exit(0);
                            }

                    }else if(yon.equalsIgnoreCase("N") || yon.equalsIgnoreCase("No")){

                            System.out.println("Terima Kasih telah Menggunakan");
                            System.exit(0);

                    }else{

                            System.out.println("Masukkan tidak sesuai");

                    }
            }while(!yon.equalsIgnoreCase("Y") && !yon.equalsIgnoreCase("N") && !yon.equalsIgnoreCase("Yes") && !yon.equalsIgnoreCase("No"));
      
        }else{
            System.out.println("\nMasukkan Kode Pemesanan");
            String kode = input.next();
                try{
                    PreparedStatement stmt = connection.prepareStatement("select * from transaksi where Kode_Transaksi= '"+kode+"'");  
                    ResultSet rs = stmt.executeQuery();
                    DatabaseMetaData md = connection.getMetaData();
                    ResultSet rs2 = md.getColumns(null, null, "transaksi", "Kode_Transaksi");
                    
                    if (rs.next()) {
                        while(rs2.next()){
                            System.out.println("\nKode Transaksi  : "+rs.getString(1));
                            System.out.println("Nama Pemesan    : "+rs.getString(2));
                            System.out.println("No. Telp        : "+rs.getString(3));
                            System.out.println("Alamat          : "+rs.getString(4));
                            System.out.println("Daftar Pesanan  : "+rs.getString(5));
                            System.out.println("Total Harga     : "+rs.getInt(6));
                        }
                    }else{
                        System.out.println("Maaf riwayat pembelian tidak ditemukan.");
                    }
                }catch(Exception e){
                    
                }
            
        }
    
        }
}