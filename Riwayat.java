// Riwayat.java - Konsep Enkapsulasi
import java.time.LocalDate;

public class Riwayat {
    private String riwayatID;
    private String userID;
    private String bukuID;
    private LocalDate tglPengembalian;
    private int denda;

    // Constructor
    public Riwayat(String riwayatID, String userID, String bukuID, LocalDate tglPengembalian, int denda) {
        this.riwayatID = riwayatID;
        this.userID = userID;
        this.bukuID = bukuID;
        this.tglPengembalian = tglPengembalian;
        this.denda = denda;
    }

    // Getter dan Setter - Enkapsulasi
    public String getRiwayatID() {
        return riwayatID;
    }

    public void setRiwayatID(String riwayatID) {
        this.riwayatID = riwayatID;
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

    public LocalDate getTglPengembalian() {
        return tglPengembalian;
    }

    public void setTglPengembalian(LocalDate tglPengembalian) {
        this.tglPengembalian = tglPengembalian;
    }

    public int getDenda() {
        return denda;
    }

    public void setDenda(int denda) {
        this.denda = denda;
    }

    // Method untuk mendapatkan info riwayat
    public String getInfo() {
        return "Riwayat ID: " + riwayatID + " | User: " + userID + " | Buku: " + bukuID +
                " | Tanggal Pengembalian: " + tglPengembalian + " | Denda: Rp " + denda;
    }
}