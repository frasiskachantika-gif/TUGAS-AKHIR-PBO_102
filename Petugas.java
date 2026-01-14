// Petugas.java - Konsep Pewarisan (Inheritance dari User)
public class Petugas extends User {
    private String jabatan;

    // Constructor
    public Petugas(String userID, String nama, String password, String jabatan) {
        super(userID, nama, "Petugas", password);
        this.jabatan = jabatan;
    }

    // Getter dan Setter - Enkapsulasi
    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    // Override method - Polimorfisme
    @Override
    public String getInfo() {
        return "Petugas: " + getNama() + " | Jabatan: " + jabatan;
    }

    @Override
    public void displayMenu() {
        System.out.println("=== Menu Petugas ===");
        System.out.println("1. Data Buku");
        System.out.println("2. Data Anggota");
        System.out.println("3. Peminjaman Aktif");
        System.out.println("4. Laporan");
        System.out.println("5. Pengaturan");
    }
}