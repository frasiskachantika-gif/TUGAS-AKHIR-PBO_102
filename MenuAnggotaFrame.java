// MenuAnggotaFrame.java - GUI Menu untuk Anggota
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAnggotaFrame extends JFrame {
    private Anggota anggota;

    public MenuAnggotaFrame(Anggota anggota) {
        this.anggota = anggota;

        setTitle("Sistem Peminjaman Buku - Menu Anggota");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(46, 125, 50));
        JLabel headerLabel = new JLabel("Selamat Datang, " + anggota.getNama() + " (Anggota)");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Panel Menu
        JPanel menuPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton cariBukuBtn = createMenuButton("Cari Buku");
        JButton pinjamBukuBtn = createMenuButton("Pinjam Buku");
        JButton riwayatBtn = createMenuButton("Riwayat Peminjaman");
        JButton kembalikanBtn = createMenuButton("Kembalikan Buku");
        JButton profilBtn = createMenuButton("Profil");
        JButton logoutBtn = createLogoutButton();

        menuPanel.add(cariBukuBtn);
        menuPanel.add(pinjamBukuBtn);
        menuPanel.add(riwayatBtn);
        menuPanel.add(kembalikanBtn);
        menuPanel.add(profilBtn);
        menuPanel.add(logoutBtn);

        add(menuPanel, BorderLayout.CENTER);

        // Action Listeners
        cariBukuBtn.addActionListener(e -> {
            new CariBukuFrame(anggota).setVisible(true);
        });

        pinjamBukuBtn.addActionListener(e -> {
            new PinjamBukuFrame(anggota).setVisible(true);
        });

        riwayatBtn.addActionListener(e -> {
            bukaRiwayatPeminjaman();
        });

        kembalikanBtn.addActionListener(e -> {
            new KembalikanBukuFrame(anggota).setVisible(true);
        });

        profilBtn.addActionListener(e -> {
            // Menampilkan info anggota - Polimorfisme
            String info = "=== PROFIL ANGGOTA ===\n\n" +
                    "Nama: " + anggota.getNama() + "\n" +
                    "ID Anggota: " + anggota.getUserID() + "\n" +
                    "Kelas: " + anggota.getKelas() + "\n" +
                    "Kontak: " + anggota.getKontak() + "\n" +
                    "Role: " + anggota.getRole() + "\n\n" +
                    "Sistem Peminjaman Buku Perpustakaan";

            JOptionPane.showMessageDialog(this, info,
                    "Profil Anggota", JOptionPane.INFORMATION_MESSAGE);
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

    // Method untuk membuka riwayat peminjaman
    private void bukaRiwayatPeminjaman() {
        // Cek apakah ada riwayat peminjaman
        boolean adaRiwayat = false;
        StringBuilder riwayatText = new StringBuilder();
        riwayatText.append("=== RIWAYAT PEMINJAMAN ===\n\n");

        // Cari riwayat peminjaman untuk anggota ini
        for (Peminjaman p : Database.getPeminjamanList()) {
            if (p.getUserID().equals(anggota.getUserID())) {
                adaRiwayat = true;
                Buku buku = Database.cariBuku(p.getBukuID());
                String judulBuku = (buku != null) ? buku.getJudul() : "Buku Tidak Ditemukan";

                riwayatText.append("ID Peminjaman: ").append(p.getPeminjamanID()).append("\n")
                        .append("Buku: ").append(judulBuku).append("\n")
                        .append("Tanggal Pinjam: ").append(p.getTglPinjam()).append("\n")
                        .append("Batas Kembali: ").append(p.getTglKembali()).append("\n")
                        .append("Status: Aktif\n")
                        .append("----------------------------\n");
            }
        }

        // Cek riwayat pengembalian
        for (Riwayat r : Database.getRiwayatList()) {
            if (r.getUserID().equals(anggota.getUserID())) {
                adaRiwayat = true;
                Buku buku = Database.cariBuku(r.getBukuID());
                String judulBuku = (buku != null) ? buku.getJudul() : "Buku Tidak Ditemukan";

                riwayatText.append("ID Riwayat: ").append(r.getRiwayatID()).append("\n")
                        .append("Buku: ").append(judulBuku).append("\n")
                        .append("Tanggal Kembali: ").append(r.getTglPengembalian()).append("\n")
                        .append("Denda: Rp ").append(r.getDenda()).append("\n")
                        .append("Status: Selesai\n")
                        .append("----------------------------\n");
            }
        }

        if (!adaRiwayat) {
            riwayatText.append("Belum ada riwayat peminjaman.\n")
                    .append("Silakan pinjam buku terlebih dahulu.");
        }

        // Tampilkan dalam JTextArea dengan scroll
        JTextArea textArea = new JTextArea(riwayatText.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(this, scrollPane,
                "Riwayat Peminjaman - " + anggota.getNama(),
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Method untuk membuat tombol menu biasa
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 40));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(new Color(46, 125, 50), 2));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(46, 125, 50));
                button.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
                button.setForeground(Color.BLACK);
            }
        });

        return button;
    }

    // Method khusus untuk tombol logout
    private JButton createLogoutButton() {
        JButton button = new JButton("Logout");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 40));
        button.setFocusPainted(false);

        // Warna khusus untuk logout: abu-abu dengan teks hitam
        button.setBackground(new Color(220, 220, 220)); // Abu-abu terang
        button.setForeground(Color.BLACK); // Teks HITAM
        button.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 2));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect khusus untuk logout
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(180, 180, 180)); // Abu-abu lebih gelap
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