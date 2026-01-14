// KembalikanBukuFrame.java - GUI untuk Mengembalikan Buku
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class KembalikanBukuFrame extends JFrame {
    private Anggota anggota;
    private JTable table;
    private DefaultTableModel tableModel;

    public KembalikanBukuFrame(Anggota anggota) {
        this.anggota = anggota;

        setTitle("Kembalikan Buku");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(46, 125, 50));
        JLabel headerLabel = new JLabel("Daftar Peminjaman Aktif");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Panel Table
        String[] columns = {"ID Peminjaman", "ID Buku", "Judul Buku", "Tgl Pinjam", "Batas Kembali"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton kembalikanBtn = new JButton("Kembalikan Buku");
        JButton refreshBtn = new JButton("Refresh");
        JButton tutupBtn = new JButton("Tutup");

        // Style untuk Kembalikan button
        kembalikanBtn.setBackground(new Color(76, 175, 80));
        kembalikanBtn.setForeground(Color.BLACK);
        kembalikanBtn.setFont(new Font("Arial", Font.BOLD, 13));
        kembalikanBtn.setFocusPainted(false);

        // Style untuk Refresh button
        refreshBtn.setBackground(new Color(33, 150, 243));
        refreshBtn.setForeground(Color.BLACK);
        refreshBtn.setFont(new Font("Arial", Font.BOLD, 13));
        refreshBtn.setFocusPainted(false);

        // Style untuk Tutup button
        tutupBtn.setBackground(new Color(96, 125, 139));
        tutupBtn.setForeground(Color.BLACK);
        tutupBtn.setFont(new Font("Arial", Font.BOLD, 13));
        tutupBtn.setFocusPainted(false);

        buttonPanel.add(kembalikanBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(tutupBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load data peminjaman
        loadPeminjamanData();

        // Action Listeners
        kembalikanBtn.addActionListener(e -> kembalikanBuku());
        refreshBtn.addActionListener(e -> loadPeminjamanData());
        tutupBtn.addActionListener(e -> dispose());
    }

    private void loadPeminjamanData() {
        tableModel.setRowCount(0);
        ArrayList<Peminjaman> peminjamanList = Database.getPeminjamanList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Peminjaman peminjaman : peminjamanList) {
            if (peminjaman.getUserID().equals(anggota.getUserID())) {
                Buku buku = Database.cariBuku(peminjaman.getBukuID());
                String judulBuku = (buku != null) ? buku.getJudul() : "Unknown";

                Object[] row = {
                        peminjaman.getPeminjamanID(),
                        peminjaman.getBukuID(),
                        judulBuku,
                        peminjaman.getTglPinjam().format(formatter),
                        peminjaman.getTglKembali().format(formatter)
                };
                tableModel.addRow(row);
            }
        }

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Anda tidak memiliki peminjaman aktif.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void kembalikanBuku() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih peminjaman yang akan dikembalikan!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String peminjamanID = (String) tableModel.getValueAt(selectedRow, 0);
        String bukuID = (String) tableModel.getValueAt(selectedRow, 1);

        // Cari peminjaman
        Peminjaman peminjaman = null;
        for (Peminjaman p : Database.getPeminjamanList()) {
            if (p.getPeminjamanID().equals(peminjamanID)) {
                peminjaman = p;
                break;
            }
        }

        if (peminjaman == null) {
            JOptionPane.showMessageDialog(this, "Peminjaman tidak ditemukan!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Hitung denda
        LocalDate tglPengembalian = LocalDate.now();
        int denda = Pengembalian.hitungDenda(peminjaman.getTglKembali(), tglPengembalian);

        // Konfirmasi pengembalian
        String message = "Apakah Anda yakin ingin mengembalikan buku ini?\n\n" +
                "Tanggal Pengembalian: " + tglPengembalian.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        if (denda > 0) {
            message += "\nDenda Keterlambatan: Rp " + denda;
        } else {
            message += "\nTidak ada denda";
        }

        int confirm = JOptionPane.showConfirmDialog(this, message,
                "Konfirmasi Pengembalian", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Proses pengembalian
            String pengembalianID = "R" + System.currentTimeMillis();
            Pengembalian pengembalian = new Pengembalian(pengembalianID, peminjamanID, tglPengembalian, denda);
            Database.tambahPengembalian(pengembalian);

            // Tambah ke riwayat
            String riwayatID = "H" + System.currentTimeMillis();
            Riwayat riwayat = new Riwayat(riwayatID, anggota.getUserID(), bukuID, tglPengembalian, denda);
            Database.tambahRiwayat(riwayat);

            // Hapus dari peminjaman aktif
            Database.hapusPeminjaman(peminjamanID);

            // Update status buku
            Buku buku = Database.cariBuku(bukuID);
            if (buku != null) {
                buku.setStatus("Tersedia");
            }

            JOptionPane.showMessageDialog(this,
                    "Pengembalian berhasil!\n" + (denda > 0 ? "Total denda: Rp " + denda : "Terima kasih!"),
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

            loadPeminjamanData();
        }
    }
}