import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SidebarPanel extends JPanel {
    public SidebarPanel(CardLayout cardLayout, JPanel contentPanel, 
                        StokGudangPanel stokGudangPanel, 
                        BarangKeluarPanel barangKeluarPanel, 
                        LaporanPanel laporanPanel) {
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Perlebar ukuran sidebar agar teks tidak terpotong
        setPreferredSize(new Dimension(210, 0));
        setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));

        JLabel navLabel = new JLabel("NAVIGASI MENU");
        navLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(navLabel);
        add(Box.createRigidArea(new Dimension(0, 15)));

        // Tombol tanpa nomor dan teks lebih pas
        JButton btnStokGudang = new JButton("Stok Gudang");
        JButton btnBarangKeluar = new JButton("Barang Keluar");
        JButton btnLaporan = new JButton("Laporan & Log");

        // Sesuaikan lebar maksimum tombol dengan sidebar baru
        Dimension btnSize = new Dimension(190, 38);
        btnStokGudang.setMaximumSize(btnSize);
        btnBarangKeluar.setMaximumSize(btnSize);
        btnLaporan.setMaximumSize(btnSize);

        add(btnStokGudang);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(btnBarangKeluar);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(btnLaporan);

        // EVENT LISTENER
        btnStokGudang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "stokGudang");
            }
        });

        btnBarangKeluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "barangKeluar");
                barangKeluarPanel.refreshData();
            }
        });

        btnLaporan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "laporan");
                laporanPanel.refreshData();
            }
        });
    }
}