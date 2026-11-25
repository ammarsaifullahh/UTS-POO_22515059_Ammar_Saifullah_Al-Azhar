public class SortedListPaket {
    private Paket[] data;
    private int size;
    private int kapasitas;

    public SortedListPaket(int kapasitas) {
        if (kapasitas <= 0) throw new IllegalArgumentException("Kapasitas harus > 0");
        this.kapasitas = kapasitas;
        this.data = new Paket[kapasitas];
        this.size = 0;
    }

    public boolean insert(Paket paket) {
        if (paket == null || isFull() || search(paket.getNomorResi()) != null) return false;
        int pos = 0;
        while (pos < size && data[pos].getNomorResi().compareTo(paket.getNomorResi()) < 0) pos++;
        for (int i = size; i > pos; i--) data[i] = data[i - 1];
        data[pos] = paket;
        size++;
        return true;
    }

    public boolean remove(String resi) {
        int pos = indexOf(resi);
        if (pos < 0) return false;
        for (int i = pos; i < size - 1; i++) data[i] = data[i + 1];
        data[--size] = null;
        return true;
    }

    public Paket search(String resi) {
        int pos = indexOf(resi);
        return pos < 0 ? null : data[pos];
    }

    public Paket get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Indeks tidak valid: " + index);
        return data[index];
    }

    public void tampilkan() {
        if (isEmpty()) { System.out.println("Belum ada paket."); return; }
        for (int i = 0; i < size; i++) {
            System.out.println("=== Paket #" + (i + 1) + " ===");
            data[i].tampilkanInfo();
            System.out.println();
        }
    }

    public int size()        { return size; }
    public boolean isFull()  { return size >= kapasitas; }
    public boolean isEmpty() { return size == 0; }

    private int indexOf(String resi) {
        if (resi == null || resi.isBlank()) return -1;
        int lo = 0, hi = size - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int cmp = data[mid].getNomorResi().compareTo(resi);
            if (cmp == 0) return mid;
            if (cmp < 0) lo = mid + 1; else hi = mid - 1;
        }
        return -1;
    }
}
