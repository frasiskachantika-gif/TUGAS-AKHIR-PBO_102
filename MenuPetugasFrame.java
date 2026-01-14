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
        JButton logoutBtn = createMenuButton("Logout");

        logoutBtn.setBackground(new Color(211, 47, 47));
        logoutBtn.setForeground(Color.WHITE);

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
            JOptionPane.showMessageDialog(this, "Fitur Laporan belum tersedia.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        pengaturanBtn.addActionListener(e -> {
            // Menampilkan info petugas - Polimorfisme
            JOptionPane.showMessageDialog(this, petugas.getInfo(),
                    "Info Petugas", JOptionPane.INFORMATION_MESSAGE);
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
        button.setBorder(BorderFactory.createLineBorder(new Color(198, 40, 40), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(198, 40, 40));
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