// Peminjaman.java - Konsep Enkapsulasi
import java.time.LocalDate;

public class Peminjaman {
    private String peminjamanID;
    private String userID;
    private String bukuID;
    private LocalDate tglPinjam;
    private LocalDate tglKembali;

    // Constructor
    public Peminjaman(String peminjamanID, String userID, String bukuID, LocalDate tglPinjam, LocalDate tglKembali) {
        this.peminjamanID = peminjamanID;
        this.userID = userID;
        this.bukuID = bukuID;
        this.tglPinjam = tglPinjam;
        this.tglKembali = tglKembali;
    }

    // Getter dan Setter - Enkapsulasi
    public String getPeminjamanID() {
        return peminjamanID;
    }

    public void setPeminjamanID(String peminjamanID) {
        this.peminjamanID = peminjamanID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBukuID() {
        return bukuID;
    }

    public void setBukuID(String bukuID) {
        this.bukuID = bukuID;
    }

    public LocalDate getTglPinjam() {
        return tglPinjam;
    }

    public void setTglPinjam(LocalDate tglPinjam) {
        this.tglPinjam = tglPinjam;
    }

    public LocalDate getTglKembali() {
        return tglKembali;
    }

    public void setTglKembali(LocalDate tglKembali) {
        this.tglKembali = tglKembali;
    }

    // Method untuk mendapatkan info peminjaman
    public String getInfo() {
        return "Peminjaman ID: " + peminjamanID + " | User: " + userID + " | Buku: " + bukuID +
                " | Tanggal Pinjam: " + tglPinjam + " | Batas Kembali: " + tglKembali;
    }
}