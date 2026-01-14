// Anggota.java - Konsep Pewarisan (Inheritance dari User)
public class Anggota extends User {
    private String kelas;
    private String kontak;

    // Constructor
    public Anggota(String userID, String nama, String password, String kelas, String kontak) {
        super(userID, nama, "Anggota", password);
        this.kelas = kelas;
        this.kontak = kontak;
    }

    // Getter dan Setter - Enkapsulasi
    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    // Override method - Polimorfisme
    @Override
    public String getInfo() {
        return "Anggota: " + getNama() + " | Kelas: " + kelas + " | Kontak: " + kontak;
    }

    @Override
    public void displayMenu() {
        System.out.println("=== Menu Anggota ===");
        System.out.println("1. Cari Buku");
        System.out.println("2. Pinjam Buku");
        System.out.println("3. Riwayat Peminjaman");
        System.out.println("4. Kembalikan Buku");
        System.out.println("5. Profil");
    }
}