import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private KantorEkspedisi kantor;

    public static void main(String[] args) {
        new Main().jalan();
    }

    private void jalan() {
        try {
            this.kantor = new KantorEkspedisi(
                tanya("Nama kantor"),
                tanya("Alamat kantor"),
                bacaAngka("Tarif per kg"),
                (int) bacaAngka("Kapasitas paket"));
        } catch (IllegalArgumentException e) {
            System.out.println("Data kantor tidak valid: " + e.getMessage());
            return;
        }

        int pilihan;
        do {
            tampilkanMenu();
            pilihan = bacaPilihan();
            prosesPilihan(pilihan);
        } while (pilihan != 0);
    }

    private void tampilkanMenu() {
        System.out.println();
        System.out.println("=====================================");
        System.out.println("       SISTEM EKSPEDISI PAKET");
        System.out.println("=====================================");
        System.out.println("1. Daftarkan Paket Baru");
        System.out.println("2. Cari Paket Berdasarkan Nomor Resi");
        System.out.println("3. Hapus Paket");
        System.out.println("4. Perbarui Status Paket");
        System.out.println("5. Tampilkan Seluruh Paket");
        System.out.println("6. Tampilkan Riwayat Pengiriman");
        System.out.println("0. Keluar");
        System.out.println("=====================================");
    }

    private void prosesPilihan(int pilihan) {
        switch (pilihan) {
            case 1:
                if (inputPaket() != null) System.out.println("Paket berhasil didaftarkan.");
                break;
            case 2:
                cariPaket();
                break;
            case 3:
                System.out.println(kantor.hapusPaket(tanya("Nomor resi"))
                    ? "Paket dihapus." : "Paket tidak ditemukan.");
                break;
            case 4:
                perbaruiStatus();
                break;
            case 5:
                kantor.tampilkanPaket();
                break;
            case 6:
                Pengiriman pengiriman = kantor.getPengiriman(tanya("Nomor resi"));
                if (pengiriman == null) System.out.println("Riwayat tidak ditemukan.");
                else pengiriman.tampilkanRiwayat();
                break;
            case 0:
                System.out.println("Terima kasih.");
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    private Paket inputPaket() {
        String resi = tanya("Nomor resi");
        String tujuan = tanya("Kota tujuan");
        String alamatTujuan = tanya("Alamat tujuan");
        double berat = bacaAngka("Berat (kg)");
        if (!(berat > 0)) {
            System.out.println("Berat harus lebih dari 0.");
            return null;
        }
        Pengirim pengirim = inputPengirim();
        if (pengirim == null) return null;
        if (!kantor.buatPaket(resi, tujuan, alamatTujuan, berat, pengirim)) {
            System.out.println("Gagal: resi duplikat, kapasitas penuh, atau data tidak valid.");
            return null;
        }
        return kantor.cariPaket(resi);
    }

    private Pengirim inputPengirim() {
        try {
            return new Pengirim(
                tanya("ID pengirim"),
                tanya("Nama pengirim"),
                tanya("No telepon"),
                tanya("Alamat pengirim"));
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
            return null;
        }
    }

    private void cariPaket() {
        Paket paket = kantor.cariPaket(tanya("Nomor resi"));
        if (paket == null) System.out.println("Paket tidak ditemukan.");
        else paket.tampilkanInfo();
    }

    private void perbaruiStatus() {
        String resi = tanya("Nomor resi");
        Paket paket = kantor.cariPaket(resi);
        if (paket == null) {
            System.out.println("Paket tidak ditemukan.");
            return;
        }
        System.out.println("Status saat ini: " + paket.getStatus());
        String statusBaru = tanya("Status baru").toUpperCase();
        System.out.println(kantor.prosesStatus(resi, statusBaru, LocalDate.now().toString())
            ? "Status diperbarui menjadi " + paket.getStatus() + "."
            : "Perubahan ditolak: status harus berurutan.");
    }

    private int bacaPilihan() {
        System.out.print("Masukkan pilihan: ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String tanya(String label) {
        System.out.print(label + ": ");
        return scanner.nextLine().trim();
    }

    private double bacaAngka(String label) {
        try {
            return Double.parseDouble(tanya(label));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
