// LoginFrame.java - GUI untuk Login
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signUpButton;

    public LoginFrame() {
        setTitle("Sistem Peminjaman Buku Perpustakaan - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 73, 94));
        JLabel headerLabel = new JLabel("Selamat Datang di Sistem Peminjaman Buku");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Panel Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Panel Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");

        // Style untuk LOGIN button - Background putih, teks hitam
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(Color.BLACK); // TEKS HITAM
        loginButton.setFont(new Font("Arial", Font.BOLD, 13));
        loginButton.setPreferredSize(new Dimension(100, 35));
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(true);
        loginButton.setContentAreaFilled(true);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(52, 73, 94), 2));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Style untuk SIGN UP button - Background putih, teks hitam
        signUpButton.setBackground(Color.WHITE);
        signUpButton.setForeground(Color.BLACK); // TEKS HITAM
        signUpButton.setFont(new Font("Arial", Font.BOLD, 13));
        signUpButton.setPreferredSize(new Dimension(100, 35));
        signUpButton.setFocusPainted(false);
        signUpButton.setOpaque(true);
        signUpButton.setContentAreaFilled(true);
        signUpButton.setBorder(BorderFactory.createLineBorder(new Color(52, 73, 94), 2));
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect untuk LOGIN button
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(52, 73, 94)); // Biru tua saat hover
                loginButton.setForeground(Color.WHITE); // Teks putih saat hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(Color.WHITE); // Kembali ke putih
                loginButton.setForeground(Color.BLACK); // Teks hitam
            }
        });

        // Hover effect untuk SIGN UP button
        signUpButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signUpButton.setBackground(new Color(52, 73, 94)); // Biru tua saat hover
                signUpButton.setForeground(Color.WHITE); // Teks putih saat hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signUpButton.setBackground(Color.WHITE); // Kembali ke putih
                signUpButton.setForeground(Color.BLACK); // Teks hitam
            }
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listener untuk Login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // Action Listener untuk Sign Up
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bukaFormSignUp();
            }
        });

        // Enter key untuk login
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan Password tidak boleh kosong!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = Database.login(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login berhasil! Selamat datang, " + user.getNama(),
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

            // Buka menu sesuai role user - Polimorfisme
            if (user instanceof Anggota) {
                new MenuAnggotaFrame((Anggota) user).setVisible(true);
            } else if (user instanceof Petugas) {
                new MenuPetugasFrame((Petugas) user).setVisible(true);
            }

            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username atau Password salah!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method untuk Sign Up (Pendaftaran)
    private void bukaFormSignUp() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JTextField namaField = new JTextField();
        JTextField kelasField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        panel.add(new JLabel("Nama Lengkap:"));
        panel.add(namaField);
        panel.add(new JLabel("Kelas:"));
        panel.add(kelasField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Konfirmasi Password:"));
        panel.add(confirmPasswordField);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Sign Up - Pendaftaran Anggota Baru",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nama = namaField.getText().trim();
            String kelas = kelasField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Validasi input
            if (nama.isEmpty() || kelas.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Semua field harus diisi!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this,
                        "Password dan konfirmasi password tidak sama!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.length() < 3) {
                JOptionPane.showMessageDialog(this,
                        "Password minimal 3 karakter!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Generate ID dan username otomatis
            String anggotaID = generateAnggotaID();
            String username = anggotaID; // Username sama dengan ID

            // Cek apakah username sudah ada
            for (User user : Database.getUserList()) {
                if (user.getUserID().equals(username)) {
                    JOptionPane.showMessageDialog(this,
                            "Username sudah digunakan. Silakan coba lagi.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Buat objek anggota baru (kontak default)
            Anggota anggotaBaru = new Anggota(anggotaID, nama, password, kelas, "08xxxxxxxxxx");

            // Tambahkan ke database
            Database.getUserList().add(anggotaBaru);

            JOptionPane.showMessageDialog(this,
                    "🎉 Sign Up Berhasil!\n\n" +
                            "ID Anggota: " + anggotaID + "\n" +
                            "Username: " + username + "\n" +
                            "Password: " + password + "\n\n" +
                            "Silakan login dengan kredensial di atas.",
                    "Sign Up Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Method untuk generate ID Anggota otomatis
    private String generateAnggotaID() {
        int maxID = 0;

        // Cari ID anggota terbesar
        for (User user : Database.getUserList()) {
            if (user instanceof Anggota) {
                String userID = user.getUserID();
                if (userID.startsWith("A")) {
                    try {
                        int idNum = Integer.parseInt(userID.substring(1));
                        if (idNum > maxID) {
                            maxID = idNum;
                        }
                    } catch (NumberFormatException e) {
                        // Skip jika format tidak sesuai
                    }
                }
            }
        }

        // Generate ID baru
        return String.format("A%03d", maxID + 1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}