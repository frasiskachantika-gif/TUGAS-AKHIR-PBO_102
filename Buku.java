// Buku.java - Konsep Enkapsulasi
public class Buku {
    private String bukuID;
    private String judul;
    private String penulis;
    private String tahunTerbit;
    private String status; // "Tersedia" atau "Dipinjam"

    // Constructor
    public Buku(String bukuID, String judul, String penulis, String tahunTerbit, String status) {
        this.bukuID = bukuID;
        this.judul = judul;
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        this.status = status;
    }

    // Getter dan Setter - Enkapsulasi
    public String getBukuID() {
        return bukuID;
    }

    public void setBukuID(String bukuID) {
        this.bukuID = bukuID;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getTahunTerbit() {
        return tahunTerbit;
    }

    public void setTahunTerbit(String tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Method untuk mendapatkan info buku
    public String getInfo() {
        return bukuID + " - " + judul + " (" + penulis + ") - " + status;
    }
}