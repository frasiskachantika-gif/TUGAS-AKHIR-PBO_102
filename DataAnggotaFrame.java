// DataAnggotaFrame.java - GUI untuk Mengelola Data Anggota (Petugas)
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DataAnggotaFrame extends JFrame {
    private Petugas petugas;
    private JTable table;
    private DefaultTableModel tableModel;

    public DataAnggotaFrame(Petugas petugas) {
        this.petugas = petugas;

        setTitle("Data Anggota - Petugas");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(198, 40, 40));
        JLabel headerLabel = new JLabel("Manajemen Data Anggota Perpustakaan");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Panel Table
        String[] columns = {"ID Anggota", "Nama", "Kelas", "Kontak", "Role"};
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
        JButton tambahBtn = new JButton("Tambah Anggota");
        JButton editBtn = new JButton("Edit Anggota");
        JButton hapusBtn = new JButton("Hapus Anggota");
        JButton refreshBtn = new JButton("Refresh");
        JButton tutupBtn = new JButton("Tutup");

        tambahBtn.setBackground(new Color(76, 175, 80));
        tambahBtn.setForeground(Color.WHITE);
        editBtn.setBackground(new Color(255, 152, 0));
        editBtn.setForeground(Color.WHITE);
        hapusBtn.setBackground(new Color(244, 67, 54));
        hapusBtn.setForeground(Color.WHITE);

        buttonPanel.add(tambahBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(hapusBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(tutupBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load data
        loadAnggotaData();

        // Action Listeners
        tambahBtn.addActionListener(e -> tambahAnggota());
        editBtn.addActionListener(e -> editAnggota());
        hapusBtn.addActionListener(e -> hapusAnggota());
        refreshBtn.addActionListener(e -> loadAnggotaData());
        tutupBtn.addActionListener(e -> dispose());
    }

    private void loadAnggotaData() {
        tableModel.setRowCount(0);
        ArrayList<User> userList = Database.getUserList();
        for (User user : userList) {
            if (user instanceof Anggota) {
                Anggota anggota = (Anggota) user;
                Object[] row = {
                        anggota.getUserID(),
                        anggota.getNama(),
                        anggota.getKelas(),
                        anggota.getKontak(),
                        anggota.getRole()
                };
                tableModel.addRow(row);
            }
        }
    }

    private void tambahAnggota() {
        JTextField idField = new JTextField();
        JTextField namaField = new JTextField();
        JTextField kelasField = new JTextField();
        JTextField kontakField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {
                "ID Anggota:", idField,
                "Nama:", namaField,
                "Kelas:", kelasField,
                "Kontak:", kontakField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Tambah Anggota Baru",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String id = idField.getText().trim();
            String nama = namaField.getText().trim();
            String kelas = kelasField.getText().trim();
            String kontak = kontakField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (id.isEmpty() || nama.isEmpty() || kelas.isEmpty() || kontak.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cek apakah ID sudah ada
            for (User user : Database.getUserList()) {
                if (user.getUserID().equals(id)) {
                    JOptionPane.showMessageDialog(this, "ID Anggota sudah ada!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Anggota anggotaBaru = new Anggota(id, nama, password, kelas, kontak);
            Database.getUserList().add(anggotaBaru);

            JOptionPane.showMessageDialog(this, "Anggota berhasil ditambahkan!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            loadAnggotaData();
        }
    }

    private void editAnggota() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih anggota yang akan diedit!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String anggotaID = (String) tableModel.getValueAt(selectedRow, 0);
        Anggota anggota = null;

        for (User user : Database.getUserList()) {
            if (user.getUserID().equals(anggotaID) && user instanceof Anggota) {
                anggota = (Anggota) user;
                break;
            }
        }

        if (anggota == null) return;

        JTextField namaField = new JTextField(anggota.getNama());
        JTextField kelasField = new JTextField(anggota.getKelas());
        JTextField kontakField = new JTextField(anggota.getKontak());

        Object[] message = {
                "Nama:", namaField,
                "Kelas:", kelasField,
                "Kontak:", kontakField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Anggota",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            anggota.setNama(namaField.getText().trim());
            anggota.setKelas(kelasField.getText().trim());
            anggota.setKontak(kontakField.getText().trim());

            JOptionPane.showMessageDialog(this, "Data anggota berhasil diupdate!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            loadAnggotaData();
        }
    }

    private void hapusAnggota() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih anggota yang akan dihapus!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String anggotaID = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus anggota ini?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Database.getUserList().removeIf(u -> u.getUserID().equals(anggotaID));
            JOptionPane.showMessageDialog(this, "Anggota berhasil dihapus!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            loadAnggotaData();
        }
    }
}