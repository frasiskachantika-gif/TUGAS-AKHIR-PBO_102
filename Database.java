// Database.java - Simulasi Database menggunakan ArrayList
import java.util.ArrayList;

public class Database {
    private static ArrayList<User> userList = new ArrayList<>();
    private static ArrayList<Buku> bukuList = new ArrayList<>();
    private static ArrayList<Peminjaman> peminjamanList = new ArrayList<>();
    private static ArrayList<Pengembalian> pengembalianList = new ArrayList<>();
    private static ArrayList<Riwayat> riwayatList = new ArrayList<>();

    // Inisialisasi data awal
    static {
        // Data User (Anggota dan Petugas)
        userList.add(new Anggota("A001", "Budi Santoso", "123", "B", "081234567890"));
        userList.add(new Anggota("A002", "Siti Nurhaliza", "123", "A", "082345678901"));
        userList.add(new Petugas("P001", "Admin Perpustakaan", "admin", "Kepala Perpustakaan"));

        // Data Buku
        bukuList.add(new Buku("B001", "Pemrograman Java", "John Doe", "2020", "Tersedia"));
        bukuList.add(new Buku("B002", "Algoritma dan Struktur Data", "Jane Smith", "2019", "Tersedia"));
        bukuList.add(new Buku("B003", "Basis Data", "Ahmad Dahlan", "2021", "Tersedia"));
        bukuList.add(new Buku("B004", "Jaringan Komputer", "Siti Aminah", "2022", "Tersedia"));
        bukuList.add(new Buku("B005", "Sistem Operasi", "Budi Raharjo", "2020", "Tersedia"));
    }

    // Getter untuk data
    public static ArrayList<User> getUserList() {
        return userList;
    }

    public static ArrayList<Buku> getBukuList() {
        return bukuList;
    }

    public static ArrayList<Peminjaman> getPeminjamanList() {
        return peminjamanList;
    }

    public static ArrayList<Pengembalian> getPengembalianList() {
        return pengembalianList;
    }

    public static ArrayList<Riwayat> getRiwayatList() {
        return riwayatList;
    }

    // Method untuk mencari user berdasarkan username dan password
    public static User login(String username, String password) {
        for (User user : userList) {
            if (user.getUserID().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // Method untuk mencari buku berdasarkan ID
    public static Buku cariBuku(String bukuID) {
        for (Buku buku : bukuList) {
            if (buku.getBukuID().equals(bukuID)) {
                return buku;
            }
        }
        return null;
    }

    // Method untuk menambah peminjaman
    public static void tambahPeminjaman(Peminjaman peminjaman) {
        peminjamanList.add(peminjaman);
    }

    // Method untuk menambah pengembalian
    public static void tambahPengembalian(Pengembalian pengembalian) {
        pengembalianList.add(pengembalian);
    }

    // Method untuk menambah riwayat
    public static void tambahRiwayat(Riwayat riwayat) {
        riwayatList.add(riwayat);
    }

    // Method untuk menghapus peminjaman
    public static void hapusPeminjaman(String peminjamanID) {
        peminjamanList.removeIf(p -> p.getPeminjamanID().equals(peminjamanID));
    }
}