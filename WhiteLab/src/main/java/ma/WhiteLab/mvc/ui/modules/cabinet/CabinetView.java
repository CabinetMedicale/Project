package ma.WhiteLab.mvc.ui.modules.cabinet;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import ma.WhiteLab.mvc.dto.CabinetDTO;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class CabinetView extends JFrame {

    JLabel lblTitre;
    JPanel panelTitre;
    JScrollPane panelTable;

    private void initLabel(){
        // === Panneau titre (NORD) ===
        lblTitre = new JLabel("Liste des cabinets du jour", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Optima", Font.BOLD, 28));
        lblTitre.setForeground(new Color(0, 102, 153));
        lblTitre.setBorder(new EmptyBorder(15, 0, 15, 0));
    }

    private void initPanels(){
        initLabel();
        panelTitre = new JPanel(new BorderLayout());
        panelTitre.setBackground(new Color(245, 245, 245));
        panelTitre.add(lblTitre, BorderLayout.CENTER);
    }

    private void initTable(List<CabinetDTO> cabinets){
        initPanels();
        // === Données du tableau ===
        String[] columns = {"Nom", "Email", "Téléphone", "Ajouté le"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        for (CabinetDTO dto : cabinets) {
            model.addRow(new Object[]{
                    dto.getNom(),
                    dto.getEmail(),
                    dto.getTelephone(),
                    dto.getDateCreationFormatee()
            });
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Optima", Font.PLAIN, 18));
        table.setRowHeight(40);
        table.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // === Centrer le contenu des cellules ===
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // === Personnaliser le header ===
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Optima", Font.BOLD, 22));
        header.setPreferredSize(new Dimension(100, 45));
        ((DefaultTableCellRenderer) header.getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        // === Scroll pane ===
        panelTable = new JScrollPane(table);
        panelTable.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
    }

    private void initContainer(List<CabinetDTO> cabinets){
        initTable(cabinets);
        var mainContainer = getContentPane();
        mainContainer.setLayout(new BorderLayout(10, 10));

        mainContainer.add(panelTitre, BorderLayout.NORTH);
        mainContainer.add(panelTable, BorderLayout.CENTER);
    }

    public CabinetView(List<CabinetDTO> cabinets) {
        super("DentalTech - Cabinets du jour");

        initContainer(cabinets);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 400);
        setLocationRelativeTo(null);
    }

    public static void showAsync(List<CabinetDTO> cabinets) {
        SwingUtilities.invokeLater(() -> new CabinetView(cabinets).setVisible(true));
    }
}

