import java.util.HashMap;
import java.util.Map;

public class KantorEkspedisi {
    private String namaKantor;
    private String alamatKantor;
    private double tarifPerKg;
    private SortedListPaket daftarPaket;
    private Map<String, Pengiriman> daftarPengiriman = new HashMap<>();

    public KantorEkspedisi(String namaKantor, String alamatKantor, double tarifPerKg, int kapasitas) {
        if (namaKantor == null || namaKantor.isBlank())
            throw new IllegalArgumentException("Nama kantor wajib diisi");
        if (alamatKantor == null || alamatKantor.isBlank())
            throw new IllegalArgumentException("Alamat kantor wajib diisi");
        if (tarifPerKg < 0)
            throw new IllegalArgumentException("Tarif tidak boleh negatif");
        this.namaKantor = namaKantor.trim();
        this.alamatKantor = alamatKantor.trim();
        this.tarifPerKg = tarifPerKg;
        this.daftarPaket = new SortedListPaket(kapasitas);
    }

    public boolean buatPaket(String nomorResi, String tujuan, String alamatTujuan,
                             double berat, Pengirim pengirim) {
        try {
            return daftarPaket.insert(
                new Paket(nomorResi, tujuan, alamatTujuan, berat, tarifPerKg, pengirim));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Paket cariPaket(String resi) {
        return daftarPaket.search(resi);
    }

    public boolean hapusPaket(String resi) {
        if (!daftarPaket.remove(resi)) return false;
        daftarPengiriman.remove(resi);
        return true;
    }

    public boolean prosesStatus(String resi, String statusBaru, String tanggal) {
        Paket paket = daftarPaket.search(resi);
        if (paket == null) return false;
        Pengiriman pengiriman = daftarPengiriman.get(resi);
        if (pengiriman == null) {
            Pengiriman baru = new Pengiriman("PGR-" + resi, tanggal, paket);
            if (!baru.tambahRiwayat(statusBaru)) return false;
            daftarPengiriman.put(resi, baru);
            return true;
        }
        return pengiriman.tambahRiwayat(statusBaru);
    }

    public void tampilkanPaket() {
        daftarPaket.tampilkan();
    }

    public SortedListPaket getDaftarPaket() {
        return daftarPaket;
    }

    public Pengiriman getPengiriman(String resi) {
        return daftarPengiriman.get(resi);
    }
}
