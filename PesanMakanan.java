/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author acer
 */
public class PesanMakanan {
    private static final String URL = "jdbc:mysql://localhost:3306/pesan_makanan";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static Connection connection;
    private static PreparedStatement tambahData;

	public static void main(String[] args) {
	
	Scanner input = new Scanner(System.in);
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
 	System.out.println("8. Air Mineral_________________Rp.3000\n\n\n");


 	String yon;	//pilihan yes or no
 	int item;	//pilihan menu
 	int n;		//jumlah yang ingin dipesan
 	int totalharga = 0;
 	boolean status = true;
 	String kodeTransaksi;
 	int nilaiacak = 1 + r.nextInt(10000);

 	do{
 		System.out.print("Lakukan Pemesnaan Makanan (Y/N) ? ");
 		yon = input.nextLine();
 		if(yon.equalsIgnoreCase("Y") || yon.equalsIgnoreCase("Yes")){

 			System.out.print("Masukkan Nama Pemesan 	:"); nama   = input.nextLine();
 			System.out.print("Masukkan Nomor HP 	:"); noHP   = input.nextLine();
 			System.out.print("Masukkan alamat 	:"); alamat = input.nextLine();
 			
 			boolean status2 = true;
 			String yon2;
 			String yon3;
 			do{
 				System.out.print("\n\nApa yang ingin anda Pesan ? (Masukkan Nomor item Pada Menu) ");
 				item = input.nextInt();
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

 			}else{
 				System.out.println("\nPesanan Anda Akan dibatalakan");
 				System.out.println("Terima Kasih Telah Menggunakan");
 				System.exit(0);
 			}

 		}else{
 			System.out.println("Terima Kasih telah Menggunakan");
 			System.exit(0);
 		}
 	}while(!yon.equalsIgnoreCase("Y") && !yon.equalsIgnoreCase("N") && !yon.equalsIgnoreCase("Yes") && !yon.equalsIgnoreCase("No"));


        }
    
}
