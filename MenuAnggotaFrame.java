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
        JButton logoutBtn = createMenuButton("Logout");

        logoutBtn.setBackground(new Color(211, 47, 47));
        logoutBtn.setForeground(Color.WHITE);

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
            JOptionPane.showMessageDialog(this, "Fitur Riwayat Peminjaman belum tersedia.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        kembalikanBtn.addActionListener(e -> {
            new KembalikanBukuFrame(anggota).setVisible(true);
        });

        profilBtn.addActionListener(e -> {
            // Menampilkan info anggota - Polimorfisme
            JOptionPane.showMessageDialog(this, anggota.getInfo(),
                    "Profil Anggota", JOptionPane.INFORMATION_MESSAGE);
        });

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Apakah Anda yakin ingin logout?", "Konfirmasi Logout",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                this.dispose();
            }
        });
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 40));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(new Color(46, 125, 50), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(46, 125, 50));
                button.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!text.equals("Logout")) {
                    button.setBackground(Color.WHITE);
                    button.setForeground(Color.BLACK);
                }
            }
        });

        return button;
    }
}