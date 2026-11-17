import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StokGudangPanel extends JPanel {
    private DataGudang dataGudang;
    private JTable table;
    private JTextField txtNama, txtKategori, txtSatuan, txtHarga, txtStok;

    public StokGudangPanel(DataGudang dataGudang) {
        this.dataGudang = dataGudang;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JLabel("Pusat Stok Gudang (Barang Masuk & Inventaris Utama)"), BorderLayout.NORTH);

        table = new JTable(dataGudang.getBarangModel());
        // MEMBUAT KOLOM TABEL FIX / TIDAK BISA DIPINDAH
        table.getTableHeader().setReorderingAllowed(false);
        
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form Tambah Barang Masuk
        JPanel formPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        txtNama = new JTextField(8);
        txtKategori = new JTextField(8);
        txtSatuan = new JTextField(8);
        txtHarga = new JTextField(8);
        txtStok = new JTextField(8);

        formPanel.add(new JLabel("Nama Barang"));
        formPanel.add(new JLabel("Kategori"));
        formPanel.add(new JLabel("Satuan"));
        formPanel.add(new JLabel("Harga (Rp)"));
        formPanel.add(new JLabel("Jumlah Stok"));

        formPanel.add(txtNama);
        formPanel.add(txtKategori);
        formPanel.add(txtSatuan);
        formPanel.add(txtHarga);
        formPanel.add(txtStok);

        JPanel bottomWrap = new JPanel(new BorderLayout(5, 5));
        bottomWrap.add(formPanel, BorderLayout.CENTER);

        JButton btnTambah = new JButton("Tambah Barang Masuk");
        JButton btnHapus = new JButton("Hapus Data");
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnTambah);
        btnPanel.add(btnHapus);
        bottomWrap.add(btnPanel, BorderLayout.SOUTH);

        add(bottomWrap, BorderLayout.SOUTH);

        // EVENT LISTENER
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nama = txtNama.getText().trim();
                    String kat = txtKategori.getText().trim();
                    String sat = txtSatuan.getText().trim();
                    int harga = Integer.parseInt(txtHarga.getText().trim());
                    int stok = Integer.parseInt(txtStok.getText().trim());

                    if(nama.isEmpty()) {
                        JOptionPane.showMessageDialog(StokGudangPanel.this, "Nama barang tidak boleh kosong!");
                        return;
                    }

                    dataGudang.tambahBarang(nama, kat, sat, harga, stok);
                    txtNama.setText(""); txtKategori.setText(""); txtSatuan.setText(""); txtHarga.setText(""); txtStok.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(StokGudangPanel.this, "Harga dan Stok harus berupa angka!");
                }
            }
        });

        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    dataGudang.hapusBarang(row);
                } else {
                    JOptionPane.showMessageDialog(StokGudangPanel.this, "Pilih baris pada tabel terlebih dahulu!");
                }
            }
        });
    }
}