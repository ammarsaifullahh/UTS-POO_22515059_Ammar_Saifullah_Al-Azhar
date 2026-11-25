public class Pengirim {
    private String idPengirim;
    private String nama;
    private String noTelepon;
    private String alamat;

    public Pengirim(String idPengirim, String nama, String noTelepon, String alamat) {
        this.idPengirim = require(idPengirim, "ID pengirim");
        this.nama = require(nama, "Nama pengirim");
        this.noTelepon = require(noTelepon, "Nomor telepon");
        this.alamat = require(alamat, "Alamat");
    }

    public void ubahData(String nama, String noTelepon, String alamat) {
        this.nama = require(nama, "Nama pengirim");
        this.noTelepon = require(noTelepon, "Nomor telepon");
        this.alamat = require(alamat, "Alamat");
    }

    public void tampilkanInfo() {
        System.out.println("ID Pengirim : " + idPengirim);
        System.out.println("Nama        : " + nama);
        System.out.println("No. Telepon : " + noTelepon);
        System.out.println("Alamat      : " + alamat);
    }

    public String getIdPengirim() { return idPengirim; }
    public String getNama()       { return nama; }
    public String getNoTelepon()  { return noTelepon; }
    public String getAlamat()     { return alamat; }

    private static String require(String v, String label) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(label + " wajib diisi");
        return v.trim();
    }
}
