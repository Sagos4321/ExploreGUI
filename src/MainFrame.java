import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainFrame() {
        setTitle("Sistem Manajemen Gudang");
        setSize(900, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // LAYOUT #1: BorderLayout
        setLayout(new BorderLayout());

        // Header Sederhana di NORTH
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.add(new JLabel("SISTEM MANAJEMEN GUDANG - PT MAJU JAYA"));
        add(headerPanel, BorderLayout.NORTH);

        // Data Model Bersama
        DataGudang dataGudang = new DataGudang();

        // Panel Konten
        StokGudangPanel stokGudangPanel = new StokGudangPanel(dataGudang);
        BarangKeluarPanel barangKeluarPanel = new BarangKeluarPanel(dataGudang);
        LaporanPanel laporanPanel = new LaporanPanel(dataGudang);

        // LAYOUT #2: CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.add(stokGudangPanel, "stokGudang");
        contentPanel.add(barangKeluarPanel, "barangKeluar");
        contentPanel.add(laporanPanel, "laporan");

        add(contentPanel, BorderLayout.CENTER);

        // Sidebar di WEST
        SidebarPanel sidebar = new SidebarPanel(cardLayout, contentPanel, stokGudangPanel, barangKeluarPanel, laporanPanel);
        add(sidebar, BorderLayout.WEST);

        cardLayout.show(contentPanel, "stokGudang");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
