import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BarangKeluarPanel extends JPanel {
    private DataGudang dataGudang;
    private JComboBox<String> comboBarang;
    private JTextField txtJumlah, txtKeterangan;
    private JTable tableStok;

    public BarangKeluarPanel(DataGudang dataGudang) {
        this.dataGudang = dataGudang;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JLabel("Interface Penjualan / Barang Keluar (Kasir Gudang)"), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        
        // Form Input Barang Keluar
        JPanel form = new JPanel(new GridLayout(2, 3, 5, 5));
        comboBarang = new JComboBox<>(dataGudang.getNamaBarangArray());
        txtJumlah = new JTextField(8);
        txtKeterangan = new JTextField(8);

        form.add(new JLabel("Pilih Barang"));
        form.add(new JLabel("Jumlah Keluar"));
        form.add(new JLabel("Keterangan / Pembeli"));

        form.add(comboBarang);
        form.add(txtJumlah);
        form.add(txtKeterangan);

        JPanel topFormWrap = new JPanel(new BorderLayout());
        topFormWrap.add(form, BorderLayout.CENTER);
        
        JButton btnProses = new JButton("Proses Barang Keluar");
        JPanel pBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pBtn.add(btnProses);
        topFormWrap.add(pBtn, BorderLayout.SOUTH);

        centerPanel.add(topFormWrap, BorderLayout.NORTH);

        // Tabel Riwayat Transaksi Keluar
        tableStok = new JTable(dataGudang.getStokModel());
        // MEMBUAT KOLOM TABEL FIX / TIDAK BISA DIPINDAH
        tableStok.getTableHeader().setReorderingAllowed(false);
        
        centerPanel.add(new JScrollPane(tableStok), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // EVENT LISTENER
        btnProses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nama = (String) comboBarang.getSelectedItem();
                    if (nama == null) {
                        JOptionPane.showMessageDialog(BarangKeluarPanel.this, "Tidak ada barang di gudang!");
                        return;
                    }
                    int jml = Integer.parseInt(txtJumlah.getText().trim());
                    String ket = txtKeterangan.getText().trim();

                    boolean ok = dataGudang.prosesKeluar(nama, jml, ket);
                    if (!ok) {
                        JOptionPane.showMessageDialog(BarangKeluarPanel.this, "Gagal! Stok barang tidak mencukupi.");
                    } else {
                        JOptionPane.showMessageDialog(BarangKeluarPanel.this, "Transaksi barang keluar berhasil dicatat!");
                        txtJumlah.setText("");
                        txtKeterangan.setText("");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BarangKeluarPanel.this, "Jumlah harus berupa angka valid!");
                }
            }
        });
    }

    public void refreshData() {
        comboBarang.removeAllItems();
        for (String s : dataGudang.getNamaBarangArray()) {
            comboBarang.addItem(s);
        }
    }
}