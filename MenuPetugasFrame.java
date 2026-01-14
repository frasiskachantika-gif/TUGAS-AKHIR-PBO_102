// MenuPetugasFrame.java - GUI Menu untuk Petugas
import javax.swing.*;
import java.awt.*;

public class MenuPetugasFrame extends JFrame {
    private Petugas petugas;

    public MenuPetugasFrame(Petugas petugas) {
        this.petugas = petugas;

        setTitle("Sistem Peminjaman Buku - Menu Petugas");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(198, 40, 40));
        JLabel headerLabel = new JLabel("Selamat Datang, " + petugas.getNama() + " (Petugas)");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Panel Menu
        JPanel menuPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton dataBukuBtn = createMenuButton("Data Buku");
        JButton dataAnggotaBtn = createMenuButton("Data Anggota");
        JButton peminjamanAktifBtn = createMenuButton("Peminjaman Aktif");
        JButton laporanBtn = createMenuButton("Laporan");
        JButton pengaturanBtn = createMenuButton("Pengaturan");
        JButton logoutBtn = createLogoutButton(); // Tombol logout khusus

        menuPanel.add(dataBukuBtn);
        menuPanel.add(dataAnggotaBtn);
        menuPanel.add(peminjamanAktifBtn);
        menuPanel.add(laporanBtn);
        menuPanel.add(pengaturanBtn);
        menuPanel.add(logoutBtn);

        add(menuPanel, BorderLayout.CENTER);

        // Action Listeners
        dataBukuBtn.addActionListener(e -> {
            new DataBukuFrame(petugas).setVisible(true);
        });

        dataAnggotaBtn.addActionListener(e -> {
            new DataAnggotaFrame(petugas).setVisible(true);
        });

        peminjamanAktifBtn.addActionListener(e -> {
            new PeminjamanAktifFrame(petugas).setVisible(true);
        });

        laporanBtn.addActionListener(e -> {
            // TAMPILKAN MENU LAPORAN ATAU FITUR YANG SUDAH TERSEDIA
            JOptionPane.showMessageDialog(this,
                    "=== FITUR LAPORAN ===\n\n" +
                            "Fitur laporan sedang dalam pengembangan.\n" +
                            "Untuk melihat data saat ini:\n" +
                            "1. Data Buku → Lihat semua buku\n" +
                            "2. Data Anggota → Lihat semua anggota\n" +
                            "3. Peminjaman Aktif → Lihat peminjaman aktif",
                    "Info Laporan",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        pengaturanBtn.addActionListener(e -> {
            // Menampilkan info petugas - Polimorfisme
            String info = "=== INFO PETUGAS ===\n\n" +
                    "Nama: " + petugas.getNama() + "\n" +
                    "ID: " + petugas.getUserID() + "\n" +
                    "Jabatan: " + petugas.getJabatan() + "\n" +
                    "Role: " + petugas.getRole() + "\n\n" +
                    "Sistem Peminjaman Buku Perpustakaan";

            JOptionPane.showMessageDialog(this, info,
                    "Profil Petugas", JOptionPane.INFORMATION_MESSAGE);
        });

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Apakah Anda yakin ingin logout?", "Konfirmasi Logout",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                this.dispose();
            }
        });
    }

    // Method untuk membuat tombol menu biasa
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 40));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(new Color(198, 40, 40), 2));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(198, 40, 40));
                button.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
                button.setForeground(Color.BLACK);
            }
        });

        return button;
    }

    // Method khusus untuk tombol logout dengan tampilan berbeda
    private JButton createLogoutButton() {
        JButton button = new JButton("Logout");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 40));
        button.setFocusPainted(false);

        // Warna khusus untuk logout: abu-abu dengan teks hitam
        button.setBackground(new Color(220, 220, 220)); // Abu-abu terang
        button.setForeground(Color.BLACK); // Teks HITAM untuk kontras
        button.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 2));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect khusus untuk logout
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(180, 180, 180)); // Abu-abu lebih gelap saat hover
                button.setForeground(Color.BLACK);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 220, 220)); // Kembali ke abu-abu terang
                button.setForeground(Color.BLACK);
            }
        });

        return button;
    }
}