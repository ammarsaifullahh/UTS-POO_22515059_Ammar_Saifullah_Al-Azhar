public class Pengiriman {
    private static final int KAPASITAS = 10;

    private String idPengiriman;
    private String tanggalKirim;
    private String[] riwayatStatus;
    private int jumlahRiwayat;
    private Paket paket;

    public Pengiriman(String idPengiriman, String tanggalKirim, Paket paket) {
        if (idPengiriman == null || idPengiriman.isBlank())
            throw new IllegalArgumentException("ID pengiriman wajib diisi");
        if (tanggalKirim == null || tanggalKirim.isBlank())
            throw new IllegalArgumentException("Tanggal kirim wajib diisi");
        if (paket == null)
            throw new IllegalArgumentException("Paket wajib diisi");
        this.idPengiriman = idPengiriman.trim();
        this.tanggalKirim = tanggalKirim.trim();
        this.paket = paket;
        this.riwayatStatus = new String[KAPASITAS];
        this.jumlahRiwayat = 0;
        this.riwayatStatus[this.jumlahRiwayat++] = paket.getStatus(); // DIBUAT
    }

    public boolean tambahRiwayat(String status) {
        if (jumlahRiwayat >= riwayatStatus.length) return false;
        if (status == null || status.equals(getStatusTerakhir())) return false;
        if (!paket.ubahStatus(status)) return false;
        riwayatStatus[jumlahRiwayat++] = status;
        return true;
    }

    public String getStatusTerakhir() {
        return jumlahRiwayat == 0 ? null : riwayatStatus[jumlahRiwayat - 1];
    }

    public void tampilkanRiwayat() {
        System.out.println("ID Pengiriman : " + idPengiriman);
        System.out.println("Tanggal Kirim : " + tanggalKirim);
        System.out.println("Nomor Resi    : " + paket.getNomorResi());
        for (int i = 0; i < jumlahRiwayat; i++)
            System.out.println((i + 1) + ". " + riwayatStatus[i]);
    }

    public Paket getPaket() { return paket; }
}
