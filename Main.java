// Main.java - Entry Point Aplikasi Sistem Peminjaman Buku Perpustakaan
import javax.swing.*;

/**
 * Class Main sebagai entry point aplikasi
 * Sistem Peminjaman Buku Perpustakaan
 *
 * Penerapan 3 Konsep OOP:
 * 1. Enkapsulasi - Semua atribut class menggunakan private dengan getter/setter
 * 2. Pewarisan - Class Anggota dan Petugas extends dari class User
 * 3. Polimorfisme - Method getInfo() dan displayMenu() di-override di child class
 *
 * @author Fransiska Nanda Chantika
 * @version 1.0
 */
public class Main {

    /**
     * Method main sebagai entry point aplikasi
     * Menampilkan LoginFrame sebagai halaman awal
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Set Look and Feel untuk tampilan yang lebih baik
        try {
            // Menggunakan system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Jika gagal, gunakan default look and feel
            e.printStackTrace();
        }

        // Menjalankan aplikasi GUI di Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Tampilkan splash screen atau welcome message (opsional)
                showWelcomeMessage();

                // Buat dan tampilkan LoginFrame
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);

                // Print informasi ke console
                printApplicationInfo();
            }
        });
    }

    /**
     * Menampilkan welcome message dialog
     */
    private static void showWelcomeMessage() {
        String message = "Selamat Datang di\n" +
                "Sistem Peminjaman Buku Perpustakaan\n\n" +
                "Developed by: Fransiska Nanda Chantika\n" +
                "NIM: 2400018102\n" +
                "Kelas: B";

        JOptionPane.showMessageDialog(null,
                message,
                "Welcome",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Print informasi aplikasi ke console
     */
    private static void printApplicationInfo() {
        System.out.println("================================================");
        System.out.println("  SISTEM PEMINJAMAN BUKU PERPUSTAKAAN");
        System.out.println("================================================");
        System.out.println("Nama    : Fransiska Nanda Chantika");
        System.out.println("NIM     : 2400018102");
        System.out.println("Kelas   : B");
        System.out.println("Dosen   : Bapak Ali Tarmuji, S.T, M.Cs");
        System.out.println("================================================");
        System.out.println("\nKonsep OOP yang diterapkan:");
        System.out.println("1. Enkapsulasi (Encapsulation)");
        System.out.println("   - Semua atribut menggunakan private");
        System.out.println("   - Akses menggunakan getter/setter");
        System.out.println("\n2. Pewarisan (Inheritance)");
        System.out.println("   - Class User sebagai parent class");
        System.out.println("   - Class Anggota dan Petugas extends User");
        System.out.println("\n3. Polimorfisme (Polymorphism)");
        System.out.println("   - Override method getInfo()");
        System.out.println("   - Override method displayMenu()");
        System.out.println("================================================");
        System.out.println("\nLogin Credentials:");
        System.out.println("Anggota  - Username: A001, Password: 123");
        System.out.println("Anggota  - Username: A002, Password: 123");
        System.out.println("Petugas  - Username: P001, Password: admin");
        System.out.println("================================================");
        System.out.println("\nAplikasi siap digunakan!");
        System.out.println();
    }

    /**
     * Method untuk demonstrasi konsep OOP di console
     * (Opsional - untuk keperluan testing)
     */
    public static void demonstrasiOOP() {
        System.out.println("\n=== DEMONSTRASI KONSEP OOP ===\n");

        // 1. Enkapsulasi
        System.out.println("1. ENKAPSULASI:");
        Buku buku = new Buku("B001", "Pemrograman Java", "John Doe", "2020", "Tersedia");
        System.out.println("   Buku ID (via getter): " + buku.getBukuID());
        System.out.println("   Judul (via getter): " + buku.getJudul());
        buku.setStatus("Dipinjam");
        System.out.println("   Status diubah (via setter): " + buku.getStatus());

        // 2. Pewarisan
        System.out.println("\n2. PEWARISAN:");
        Anggota anggota = new Anggota("A001", "Budi", "123", "B", "08123456789");
        System.out.println("   Anggota extends User");
        System.out.println("   Nama dari parent class: " + anggota.getNama());
        System.out.println("   Kelas dari child class: " + anggota.getKelas());

        // 3. Polimorfisme
        System.out.println("\n3. POLIMORFISME:");
        User user1 = new Anggota("A001", "Budi", "123", "B", "08123456789");
        User user2 = new Petugas("P001", "Admin", "admin", "Kepala Perpustakaan");

        System.out.println("   Method getInfo() di-override:");
        System.out.println("   - " + user1.getInfo()); // Memanggil versi Anggota
        System.out.println("   - " + user2.getInfo()); // Memanggil versi Petugas

        System.out.println("\n   Method displayMenu() di-override:");
        System.out.print("   - Anggota: ");
        user1.displayMenu(); // Memanggil versi Anggota
        System.out.print("   - Petugas: ");
        user2.displayMenu(); // Memanggil versi Petugas

        System.out.println("\n=== END DEMONSTRASI ===\n");
    }
}