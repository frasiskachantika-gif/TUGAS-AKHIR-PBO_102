// DataBukuFrame.java - GUI untuk Mengelola Data Buku (Petugas)
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DataBukuFrame extends JFrame {
    private Petugas petugas;
    private JTable table;
    private DefaultTableModel tableModel;

    public DataBukuFrame(Petugas petugas) {
        this.petugas = petugas;

        setTitle("Data Buku - Petugas");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(198, 40, 40));
        JLabel headerLabel = new JLabel("Manajemen Data Buku Perpustakaan");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Panel Table
        String[] columns = {"ID Buku", "Judul", "Penulis", "Tahun Terbit", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton tambahBtn = new JButton("Tambah Buku");
        JButton editBtn = new JButton("Edit Buku");
        JButton hapusBtn = new JButton("Hapus Buku");
        JButton refreshBtn = new JButton("Refresh");
        JButton tutupBtn = new JButton("Tutup");

        // Style untuk Tambah button
        tambahBtn.setBackground(new Color(76, 175, 80));
        tambahBtn.setForeground(Color.BLACK);
        tambahBtn.setFont(new Font("Arial", Font.BOLD, 13));
        tambahBtn.setFocusPainted(false);
        tambahBtn.setPreferredSize(new Dimension(140, 35));
        tambahBtn.setBorder(BorderFactory.createLineBorder(new Color(56, 142, 60), 2));
        tambahBtn.setOpaque(true);
        tambahBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Style untuk Edit button
        editBtn.setBackground(new Color(255, 152, 0));
        editBtn.setForeground(Color.BLACK);
        editBtn.setFont(new Font("Arial", Font.BOLD, 13));
        editBtn.setFocusPainted(false);
        editBtn.setPreferredSize(new Dimension(140, 35));
        editBtn.setBorder(BorderFactory.createLineBorder(new Color(230, 126, 34), 2));
        editBtn.setOpaque(true);
        editBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Style untuk Hapus button
        hapusBtn.setBackground(new Color(244, 67, 54));
        hapusBtn.setForeground(Color.BLACK);
        hapusBtn.setFont(new Font("Arial", Font.BOLD, 13));
        hapusBtn.setFocusPainted(false);
        hapusBtn.setPreferredSize(new Dimension(140, 35));
        hapusBtn.setBorder(BorderFactory.createLineBorder(new Color(198, 40, 40), 2));
        hapusBtn.setOpaque(true);
        hapusBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Style untuk Refresh button
        refreshBtn.setBackground(new Color(33, 150, 243));
        refreshBtn.setForeground(Color.BLACK);
        refreshBtn.setFont(new Font("Arial", Font.BOLD, 13));
        refreshBtn.setFocusPainted(false);
        refreshBtn.setPreferredSize(new Dimension(100, 35));
        refreshBtn.setBorder(BorderFactory.createLineBorder(new Color(25, 118, 210), 2));
        refreshBtn.setOpaque(true);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Style untuk Tutup button
        tutupBtn.setBackground(new Color(96, 125, 139));
        tutupBtn.setForeground(Color.BLACK);
        tutupBtn.setFont(new Font("Arial", Font.BOLD, 13));
        tutupBtn.setFocusPainted(false);
        tutupBtn.setPreferredSize(new Dimension(100, 35));
        tutupBtn.setBorder(BorderFactory.createLineBorder(new Color(69, 90, 100), 2));
        tutupBtn.setOpaque(true);
        tutupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(tambahBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(hapusBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(tutupBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load data
        loadBukuData();

        // Action Listeners
        tambahBtn.addActionListener(e -> tambahBuku());
        editBtn.addActionListener(e -> editBuku());
        hapusBtn.addActionListener(e -> hapusBuku());
        refreshBtn.addActionListener(e -> loadBukuData());
        tutupBtn.addActionListener(e -> dispose());
    }

    private void loadBukuData() {
        tableModel.setRowCount(0);
        ArrayList<Buku> bukuList = Database.getBukuList();
        for (Buku buku : bukuList) {
            Object[] row = {
                    buku.getBukuID(),
                    buku.getJudul(),
                    buku.getPenulis(),
                    buku.getTahunTerbit(),
                    buku.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    private void tambahBuku() {
        JTextField idField = new JTextField();
        JTextField judulField = new JTextField();
        JTextField penulisField = new JTextField();
        JTextField tahunField = new JTextField();

        Object[] message = {
                "ID Buku:", idField,
                "Judul:", judulField,
                "Penulis:", penulisField,
                "Tahun Terbit:", tahunField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Tambah Buku Baru",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String id = idField.getText().trim();
            String judul = judulField.getText().trim();
            String penulis = penulisField.getText().trim();
            String tahun = tahunField.getText().trim();

            if (id.isEmpty() || judul.isEmpty() || penulis.isEmpty() || tahun.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cek apakah ID sudah ada
            if (Database.cariBuku(id) != null) {
                JOptionPane.showMessageDialog(this, "ID Buku sudah ada!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Buku bukuBaru = new Buku(id, judul, penulis, tahun, "Tersedia");
            Database.getBukuList().add(bukuBaru);

            JOptionPane.showMessageDialog(this, "Buku berhasil ditambahkan!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            loadBukuData();
        }
    }

    private void editBuku() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih buku yang akan diedit!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String bukuID = (String) tableModel.getValueAt(selectedRow, 0);
        Buku buku = Database.cariBuku(bukuID);

        if (buku == null) return;

        JTextField judulField = new JTextField(buku.getJudul());
        JTextField penulisField = new JTextField(buku.getPenulis());
        JTextField tahunField = new JTextField(buku.getTahunTerbit());
        String[] statusOptions = {"Tersedia", "Dipinjam"};
        JComboBox<String> statusCombo = new JComboBox<>(statusOptions);
        statusCombo.setSelectedItem(buku.getStatus());

        Object[] message = {
                "Judul:", judulField,
                "Penulis:", penulisField,
                "Tahun Terbit:", tahunField,
                "Status:", statusCombo
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Buku",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            buku.setJudul(judulField.getText().trim());
            buku.setPenulis(penulisField.getText().trim());
            buku.setTahunTerbit(tahunField.getText().trim());
            buku.setStatus((String) statusCombo.getSelectedItem());

            JOptionPane.showMessageDialog(this, "Buku berhasil diupdate!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            loadBukuData();
        }
    }

    private void hapusBuku() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih buku yang akan dihapus!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String bukuID = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus buku ini?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Database.getBukuList().removeIf(b -> b.getBukuID().equals(bukuID));
            JOptionPane.showMessageDialog(this, "Buku berhasil dihapus!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            loadBukuData();
        }
    }
}