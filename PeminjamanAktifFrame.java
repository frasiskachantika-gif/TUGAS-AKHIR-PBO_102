// PeminjamanAktifFrame.java - GUI untuk Melihat Peminjaman Aktif (Petugas)
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PeminjamanAktifFrame extends JFrame {
    private Petugas petugas;
    private JTable table;
    private DefaultTableModel tableModel;

    public PeminjamanAktifFrame(Petugas petugas) {
        this.petugas = petugas;

        setTitle("Peminjaman Aktif - Petugas");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(198, 40, 40));
        JLabel headerLabel = new JLabel("Daftar Peminjaman Aktif");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Panel Info
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel infoLabel = new JLabel("Total Peminjaman Aktif: ");
        JLabel countLabel = new JLabel("0");
        countLabel.setFont(new Font("Arial", Font.BOLD, 14));
        countLabel.setForeground(new Color(198, 40, 40));
        infoPanel.add(infoLabel);
        infoPanel.add(countLabel);

        // Panel Table
        String[] columns = {"ID Peminjaman", "ID Anggota", "Nama Anggota", "ID Buku", "Judul Buku", "Tgl Pinjam", "Batas Kembali"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(infoPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Panel Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton refreshBtn = new JButton("Refresh");
        JButton detailBtn = new JButton("Lihat Detail");
        JButton tutupBtn = new JButton("Tutup");

        detailBtn.setBackground(new Color(33, 150, 243));
        detailBtn.setForeground(Color.WHITE);

        buttonPanel.add(refreshBtn);
        buttonPanel.add(detailBtn);
        buttonPanel.add(tutupBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load data
        loadPeminjamanData();
        countLabel.setText(String.valueOf(tableModel.getRowCount()));

        // Action Listeners
        refreshBtn.addActionListener(e -> {
            loadPeminjamanData();
            countLabel.setText(String.valueOf(tableModel.getRowCount()));
        });

        detailBtn.addActionListener(e -> lihatDetail());
        tutupBtn.addActionListener(e -> dispose());
    }

    private void loadPeminjamanData() {
        tableModel.setRowCount(0);
        ArrayList<Peminjaman> peminjamanList = Database.getPeminjamanList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Peminjaman peminjaman : peminjamanList) {
            // Cari data anggota
            String namaAnggota = "Unknown";
            for (User user : Database.getUserList()) {
                if (user.getUserID().equals(peminjaman.getUserID())) {
                    namaAnggota = user.getNama();
                    break;
                }
            }

            // Cari data buku
            Buku buku = Database.cariBuku(peminjaman.getBukuID());
            String judulBuku = (buku != null) ? buku.getJudul() : "Unknown";

            Object[] row = {
                    peminjaman.getPeminjamanID(),
                    peminjaman.getUserID(),
                    namaAnggota,
                    peminjaman.getBukuID(),
                    judulBuku,
                    peminjaman.getTglPinjam().format(formatter),
                    peminjaman.getTglKembali().format(formatter)
            };
            tableModel.addRow(row);
        }
    }

    private void lihatDetail() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih peminjaman untuk melihat detail!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String peminjamanID = (String) tableModel.getValueAt(selectedRow, 0);
        String anggotaID = (String) tableModel.getValueAt(selectedRow, 1);
        String namaAnggota = (String) tableModel.getValueAt(selectedRow, 2);
        String bukuID = (String) tableModel.getValueAt(selectedRow, 3);
        String judulBuku = (String) tableModel.getValueAt(selectedRow, 4);
        String tglPinjam = (String) tableModel.getValueAt(selectedRow, 5);
        String tglKembali = (String) tableModel.getValueAt(selectedRow, 6);

        String detail = "=== DETAIL PEMINJAMAN ===\n\n" +
                "ID Peminjaman: " + peminjamanID + "\n" +
                "ID Anggota: " + anggotaID + "\n" +
                "Nama Anggota: " + namaAnggota + "\n" +
                "ID Buku: " + bukuID + "\n" +
                "Judul Buku: " + judulBuku + "\n" +
                "Tanggal Pinjam: " + tglPinjam + "\n" +
                "Batas Pengembalian: " + tglKembali;

        JTextArea textArea = new JTextArea(detail);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JOptionPane.showMessageDialog(this, new JScrollPane(textArea),
                "Detail Peminjaman", JOptionPane.INFORMATION_MESSAGE);
    }
}