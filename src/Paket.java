import java.util.List;

public class Paket {
    private static final List<String> URUTAN = List.of(
        "DIBUAT", "DIPROSES", "DIKIRIM", "DALAM_PERJALANAN",
        "TIBA_DI_KOTA_TUJUAN", "DIANTAR", "TERKIRIM");

    private String nomorResi;
    private String tujuan;
    private String alamatTujuan;
    private double berat;
    private double tarifPerKg;
    private double ongkir;
    private String status;
    private Pengirim pengirim;

    public Paket(String nomorResi, String tujuan, String alamatTujuan,
                 double berat, double tarifPerKg, Pengirim pengirim) {
        this.nomorResi = require(nomorResi, "Nomor resi");
        this.tujuan = require(tujuan, "Tujuan");
        this.alamatTujuan = require(alamatTujuan, "Alamat tujuan");
        if (berat <= 0) throw new IllegalArgumentException("Berat harus > 0");
        if (tarifPerKg < 0) throw new IllegalArgumentException("Tarif tidak boleh negatif");
        if (pengirim == null) throw new IllegalArgumentException("Pengirim wajib diisi");
        this.berat = berat;
        this.tarifPerKg = tarifPerKg;
        this.pengirim = pengirim;
        this.status = URUTAN.get(0);
        this.ongkir = hitungOngkir();
    }

    public double hitungOngkir() {
        ongkir = berat * tarifPerKg;
        return ongkir;
    }

    public boolean ubahStatus(String statusBaru) {
        int sekarang = URUTAN.indexOf(status);
        if (URUTAN.indexOf(statusBaru) != sekarang + 1) return false;
        status = statusBaru;
        return true;
    }

    public void tampilkanInfo() {
        System.out.println("Nomor Resi    : " + nomorResi);
        System.out.println("Tujuan        : " + tujuan);
        System.out.println("Alamat Tujuan : " + alamatTujuan);
        System.out.println("Berat         : " + berat + " kg");
        System.out.println("Tarif/Kg      : " + tarifPerKg);
        System.out.println("Ongkir        : " + ongkir);
        System.out.println("Status        : " + status);
        System.out.println("-- Pengirim --");
        pengirim.tampilkanInfo();
    }

    public String getNomorResi()    { return nomorResi; }
    public String getTujuan()       { return tujuan; }
    public String getAlamatTujuan() { return alamatTujuan; }
    public double getBerat()        { return berat; }
    public double getOngkir()       { return ongkir; }
    public String getStatus()       { return status; }
    public Pengirim getPengirim()   { return pengirim; }

    private static String require(String v, String label) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(label + " wajib diisi");
        return v.trim();
    }
}
