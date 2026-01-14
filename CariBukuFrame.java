// CariBukuFrame.java - GUI untuk Mencari Buku
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CariBukuFrame extends JFrame {
    private Anggota anggota;
    private JTextField searchField;
    private JTable table;
    private DefaultTableModel tableModel;

    public CariBukuFrame(Anggota anggota) {
        this.anggota = anggota;

        setTitle("Cari Buku");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(46, 125, 50));
        JLabel headerLabel = new JLabel("Cari Buku Perpustakaan");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Panel Search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.add(new JLabel("Cari Judul/Penulis:"));
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Cari");
        JButton showAllBtn = new JButton("Tampilkan Semua");

        // Style untuk Search button
        searchBtn.setBackground(new Color(33, 150, 243));
        searchBtn.setForeground(Color.BLACK);
        searchBtn.setFont(new Font("Arial", Font.BOLD, 13));
        searchBtn.setFocusPainted(false);

        // Style untuk ShowAll button
        showAllBtn.setBackground(new Color(76, 175, 80));
        showAllBtn.setForeground(Color.BLACK);
        showAllBtn.setFont(new Font("Arial", Font.BOLD, 13));
        showAllBtn.setFocusPainted(false);

        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(showAllBtn);
        add(searchPanel, BorderLayout.NORTH);

        // Panel Table
        String[] columns = {"ID Buku", "Judul", "Penulis", "Tahun Terbit", "Status"};
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
        JButton closeBtn = new JButton("Tutup");

        // Style untuk Close button
        closeBtn.setBackground(new Color(96, 125, 139));
        closeBtn.setForeground(Color.BLACK);
        closeBtn.setFont(new Font("Arial", Font.BOLD, 13));
        closeBtn.setFocusPainted(false);

        buttonPanel.add(closeBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load semua buku saat pertama kali dibuka
        loadAllBooks();

        // Action Listeners
        searchBtn.addActionListener(e -> searchBooks());
        showAllBtn.addActionListener(e -> loadAllBooks());
        closeBtn.addActionListener(e -> dispose());

        searchField.addActionListener(e -> searchBooks());
    }

    private void loadAllBooks() {
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

    private void searchBooks() {
        String keyword = searchField.getText().toLowerCase();
        if (keyword.isEmpty()) {
            loadAllBooks();
            return;
        }

        tableModel.setRowCount(0);
        ArrayList<Buku> bukuList = Database.getBukuList();
        for (Buku buku : bukuList) {
            if (buku.getJudul().toLowerCase().contains(keyword) ||
                    buku.getPenulis().toLowerCase().contains(keyword)) {
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

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Buku tidak ditemukan.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}