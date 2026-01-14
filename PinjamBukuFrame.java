// PinjamBukuFrame.java - GUI untuk Meminjam Buku
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PinjamBukuFrame extends JFrame {
    private Anggota anggota;
    private JTextField bukuIDField;
    private JLabel judulLabel, penulisLabel, statusLabel, tglPinjamLabel, tglKembaliLabel;

    public PinjamBukuFrame(Anggota anggota) {
        this.anggota = anggota;

        setTitle("Pinjam Buku");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(46, 125, 50));
        JLabel headerLabel = new JLabel("Form Peminjaman Buku");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Panel Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // ID Buku
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("ID Buku:"), gbc);
        gbc.gridx = 1;
        bukuIDField = new JTextField(15);
        formPanel.add(bukuIDField, gbc);
        gbc.gridx = 2;
        JButton cariBtn = new JButton("Cari");
        formPanel.add(cariBtn, gbc);

        row++;

        // Judul
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Judul:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        judulLabel = new JLabel("-");
        judulLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        formPanel.add(judulLabel, gbc);
        gbc.gridwidth = 1;

        row++;

        // Penulis
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Penulis:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        penulisLabel = new JLabel("-");
        penulisLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        formPanel.add(penulisLabel, gbc);
        gbc.gridwidth = 1;

        row++;

        // Status
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        statusLabel = new JLabel("-");
        statusLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        formPanel.add(statusLabel, gbc);
        gbc.gridwidth = 1;

        row++;

        // Tanggal Pinjam
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Tanggal Pinjam:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        tglPinjamLabel = new JLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        tglPinjamLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        formPanel.add(tglPinjamLabel, gbc);
        gbc.gridwidth = 1;

        row++;

        // Tanggal Kembali
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Batas Kembali:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        tglKembaliLabel = new JLabel(LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        tglKembaliLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        formPanel.add(tglKembaliLabel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Panel Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton pinjamBtn = new JButton("Pinjam Buku");
        JButton batalBtn = new JButton("Batal");
        buttonPanel.add(pinjamBtn);
        buttonPanel.add(batalBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        cariBtn.addActionListener(e -> cariBuku());
        pinjamBtn.addActionListener(e -> pinjamBuku());
        batalBtn.addActionListener(e -> dispose());
    }

    private void cariBuku() {
        String bukuID = bukuIDField.getText().trim();
        if (bukuID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan ID Buku terlebih dahulu!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Buku buku = Database.cariBuku(bukuID);
        if (buku != null) {
            judulLabel.setText(buku.getJudul());
            penulisLabel.setText(buku.getPenulis());
            statusLabel.setText(buku.getStatus());
            statusLabel.setForeground(buku.getStatus().equals("Tersedia") ? Color.GREEN : Color.RED);
        } else {
            JOptionPane.showMessageDialog(this, "Buku dengan ID " + bukuID + " tidak ditemukan!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            resetForm();
        }
    }

    private void pinjamBuku() {
        String bukuID = bukuIDField.getText().trim();
        if (bukuID.isEmpty() || judulLabel.getText().equals("-")) {
            JOptionPane.showMessageDialog(this, "Cari buku terlebih dahulu!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Buku buku = Database.cariBuku(bukuID);
        if (buku == null) {
            JOptionPane.showMessageDialog(this, "Buku tidak ditemukan!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!buku.getStatus().equals("Tersedia")) {
            JOptionPane.showMessageDialog(this, "Buku sedang tidak tersedia!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Proses peminjaman
        String peminjamanID = "P" + System.currentTimeMillis();
        LocalDate tglPinjam = LocalDate.now();
        LocalDate tglKembali = tglPinjam.plusDays(7);

        Peminjaman peminjaman = new Peminjaman(peminjamanID, anggota.getUserID(), bukuID, tglPinjam, tglKembali);
        Database.tambahPeminjaman(peminjaman);

        // Update status buku
        buku.setStatus("Dipinjam");

        JOptionPane.showMessageDialog(this,
                "Peminjaman berhasil!\n" +
                        "ID Peminjaman: " + peminjamanID + "\n" +
                        "Batas pengembalian: " + tglKembali.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                "Sukses", JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }

    private void resetForm() {
        judulLabel.setText("-");
        penulisLabel.setText("-");
        statusLabel.setText("-");
        statusLabel.setForeground(Color.BLACK);
    }
}