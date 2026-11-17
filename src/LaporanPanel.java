import javax.swing.*;
import java.awt.*;

public class LaporanPanel extends JPanel {
    private DataGudang dataGudang;
    private JLabel lblJenis, lblKeluar;
    private JTextArea txtLog;

    public LaporanPanel(DataGudang dataGudang) {
        this.dataGudang = dataGudang;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JLabel("Laporan & Log Aktivitas Sistem Gudang"), BorderLayout.NORTH);

        JPanel stats = new JPanel(new GridLayout(1, 2, 10, 0));
        lblJenis = new JLabel("0", SwingConstants.CENTER);
        lblKeluar = new JLabel("0", SwingConstants.CENTER);

        JPanel p1 = new JPanel(new BorderLayout()); p1.add(new JLabel("Total Jenis Barang", SwingConstants.CENTER), BorderLayout.NORTH); p1.add(lblJenis, BorderLayout.CENTER);
        JPanel p2 = new JPanel(new BorderLayout()); p2.add(new JLabel("Akumulasi Barang Keluar", SwingConstants.CENTER), BorderLayout.NORTH); p2.add(lblKeluar, BorderLayout.CENTER);

        stats.add(p1); stats.add(p2);

        JPanel centerWrap = new JPanel(new BorderLayout(10, 10));
        centerWrap.add(stats, BorderLayout.NORTH);

        txtLog = new JTextArea();
        txtLog.setEditable(false);
        centerWrap.add(new JScrollPane(txtLog), BorderLayout.CENTER);

        add(centerWrap, BorderLayout.CENTER);
        refreshData();
    }

    public void refreshData() {
        lblJenis.setText(String.valueOf(dataGudang.getTotalJenisBarang()));
        lblKeluar.setText(String.valueOf(dataGudang.getTotalStokKeluar()));

        StringBuilder sb = new StringBuilder();
        for (String log : dataGudang.getLogList()) {
            sb.append(log).append("\n");
        }
        txtLog.setText(sb.toString());
    }
}