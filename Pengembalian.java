// Pengembalian.java - Konsep Enkapsulasi
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Pengembalian {
    private String pengembalianID;
    private String peminjamanID;
    private LocalDate tglPengembalian;
    private int denda;

    // Constructor
    public Pengembalian(String pengembalianID, String peminjamanID, LocalDate tglPengembalian, int denda) {
        this.pengembalianID = pengembalianID;
        this.peminjamanID = peminjamanID;
        this.tglPengembalian = tglPengembalian;
        this.denda = denda;
    }

    // Getter dan Setter - Enkapsulasi
    public String getPengembalianID() {
        return pengembalianID;
    }

    public void setPengembalianID(String pengembalianID) {
        this.pengembalianID = pengembalianID;
    }

    public String getPeminjamanID() {
        return peminjamanID;
    }

    public void setPeminjamanID(String peminjamanID) {
        this.peminjamanID = peminjamanID;
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

    // Method untuk menghitung denda keterlambatan
    public static int hitungDenda(LocalDate tglKembali, LocalDate tglPengembalian) {
        long hariTerlambat = ChronoUnit.DAYS.between(tglKembali, tglPengembalian);
        if (hariTerlambat > 0) {
            return (int) (hariTerlambat * 2000); // Denda Rp 2000 per hari
        }
        return 0;
    }

    // Method untuk mendapatkan info pengembalian
    public String getInfo() {
        return "Pengembalian ID: " + pengembalianID + " | Peminjaman ID: " + peminjamanID +
                " | Tanggal Pengembalian: " + tglPengembalian + " | Denda: Rp " + denda;
    }
}