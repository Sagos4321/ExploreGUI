import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataGudang {

    private DefaultTableModel barangModel;
    private DefaultTableModel stokModel;
    private ArrayList<String> logList;
    private int barangCounter = 0;

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public DataGudang() {
        String[] barangCols = {"No", "Nama Barang", "Kategori", "Satuan", "Harga (Rp)", "Stok"};
        barangModel = new DefaultTableModel(barangCols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        String[] stokCols = {"Waktu", "Nama Barang", "Tipe", "Jumlah", "Keterangan"};
        stokModel = new DefaultTableModel(stokCols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        logList = new ArrayList<>();

        tambahBarang("Semen Portland 50kg", "Bahan Bangunan", "Sak", 65000, 120);
        tambahBarang("Besi Beton 10mm", "Bahan Bangunan", "Batang", 85000, 250);
    }

    public void tambahBarang(String nama, String kategori, String satuan, int harga, int stok) {
        barangCounter++;
        barangModel.addRow(new Object[]{barangCounter, nama, kategori, satuan, harga, stok});
        addLog("Barang masuk/ditambahkan: " + nama + " (Stok awal: " + stok + ")");
    }

    public void hapusBarang(int row) {
        String nama = (String) barangModel.getValueAt(row, 1);
        barangModel.removeRow(row);
        for (int i = 0; i < barangModel.getRowCount(); i++) {
            barangModel.setValueAt(i + 1, i, 0);
        }
        barangCounter = barangModel.getRowCount();
        addLog("Barang dihapus dari sistem: " + nama);
    }

    public boolean prosesKeluar(String namaBarang, int jumlah, String keterangan) {
        for (int i = 0; i < barangModel.getRowCount(); i++) {
            if (barangModel.getValueAt(i, 1).equals(namaBarang)) {
                int stokSekarang = (int) barangModel.getValueAt(i, 5);
                if (stokSekarang < jumlah) return false; // Stok tidak cukup

                barangModel.setValueAt(stokSekarang - jumlah, i, 5);
                String waktu = LocalDateTime.now().format(DTF);
                stokModel.insertRow(0, new Object[]{waktu, namaBarang, "Keluar", jumlah, keterangan});
                addLog("Barang Keluar (Terjual): " + namaBarang + " (" + jumlah + " unit)");
                return true;
            }
        }
        return false;
    }

    public void addLog(String message) {
        logList.add(0, "[" + LocalDateTime.now().format(DTF) + "] " + message);
    }

    public DefaultTableModel getBarangModel() { return barangModel; }
    public DefaultTableModel getStokModel() { return stokModel; }
    public ArrayList<String> getLogList() { return logList; }

    public String[] getNamaBarangArray() {
        String[] names = new String[barangModel.getRowCount()];
        for (int i = 0; i < barangModel.getRowCount(); i++) {
            names[i] = (String) barangModel.getValueAt(i, 1);
        }
        return names;
    }

    public int getTotalJenisBarang() { return barangModel.getRowCount(); }
    public int getTotalStokKeluar() {
        int total = 0;
        for (int i = 0; i < stokModel.getRowCount(); i++) {
            if ("Keluar".equals(stokModel.getValueAt(i, 2))) total += (int) stokModel.getValueAt(i, 3);
        }
        return total;
    }
}