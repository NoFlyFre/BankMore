package com.noflyfre.bankmore;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Classe che definisce il frame della GUI.
 */
@SuppressWarnings("checkstyle:magicnumber")
public class AppFrame extends JFrame implements ActionListener {
    private final Font btnFont = new Font("Arial", Font.PLAIN, 20);
    private final Font h1Font = new Font("Arial", Font.BOLD, 40);
    private final Font h2Font = new Font("Arial", Font.BOLD, 25);
    private final Dimension btnDimension = new Dimension(150, 40);
    private final Dimension frameMinSize = new Dimension(400, 300);

    private JButton delBtn = new JButton("Elimina");
    private JButton addBtn = new JButton("Aggiungi");
    private JButton modBtn = new JButton("Modifica");
    private JButton saveBtn = new JButton("Salva");
    private JButton loadBtn = new JButton("Carica");
    private JButton expBtn = new JButton("Esporta");
    private JButton printBtn = new JButton("Stampa");
    private JButton searchBtn = new JButton("Cerca");
    private JPanel addPanel = new JPanel();

    private JTextField importoField = new JTextField(20);
    private JTextField dataField = new JTextField(20);
    private JTextField descrizioneField = new JTextField(20);

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String dataAttuale = LocalDate.now().format(formatter);

    private int searchRowIndex = -1;
    private String lastSearchText = "";

    /**
     * Costruttore della classe AppFrame.
     * Inizializza le proprietà principali del frame ed inizializza i componenti
     * grafici.
     */
    public AppFrame(Bilancio myBudget) {
        FlatLightLaf.setup();
        setTitle("BankMore");
        generaTransazioni(myBudget, formatter);
        initComponents(myBudget);
        myBudget.stampaBilancio();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(frameMinSize);
        setVisible(true);
    }

    /**
     * Metodo che inizializza e posiziona i componenti nella GUI.
     * 
     * @param myBudget Oggetto di tipo Bilancio passato alla GUI per la
     *                 rappresentazione dei dati.
     */
    private void initComponents(Bilancio myBudget) {
        MyTableModel model = new MyTableModel(myBudget.getTransazioni());
        JTable tableBudget = new JTable(model);

        JTextField searchInput = new JTextField(20); // da finire, inserire testo precompilato

        String[] periods = { "Annuale", "Mensile", "Settimanale", "Giornaliero" };
        JComboBox periodChooser = new JComboBox(periods);

        JLabel bilancioTitle = new JLabel("Bilancio");
        JLabel titleRicerca = new JLabel("Ricerca transazione:");
        JLabel bilancioValue = new JLabel();

        JLabel titleBanner = new JLabel("BANKMORE");

        /* Creazione dei vari panel */
        JPanel banner = new JPanel();
        JPanel footer = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JScrollPane tableScrollPane = new JScrollPane(tableBudget);
        JPanel tbaleBtnsPanel = new JPanel();
        JPanel searchPanel = new JPanel();
        JPanel bodyPanel = new JPanel();
        JPanel bilancioPanel = new JPanel();

        /* Definizione dei layout */
        setLayout(new BorderLayout());
        banner.setLayout(new FlowLayout());
        footer.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        leftPanel.setLayout(new BorderLayout());
        tbaleBtnsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.X_AXIS));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        bilancioPanel.setLayout(new BoxLayout(bilancioPanel, BoxLayout.Y_AXIS));
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));

        // Precompila il campo di input della data con la data attuale
        dataField.setText(dataAttuale);
        addPanel.add(new JLabel("Importo:"));
        addPanel.add(importoField);
        addPanel.add(Box.createVerticalStrut(15)); // Spazio vuoto
        addPanel.add(new JLabel("Data:"));
        addPanel.add(dataField);
        addPanel.add(Box.createVerticalStrut(15)); // Spazio vuoto
        addPanel.add(new JLabel("Descrizione:"));
        addPanel.add(descrizioneField);

        /* Inserimento dinamico del valore del bilancio */
        bilancioValue.setFont(new Font("Arial", Font.PLAIN, 22));
        bilancioValue.setText(String.format("%.2f", myBudget.totaleBilancio()) + "€");

        /* Creazione del grafico */
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Entrate", myBudget.totaleEntrate());
        dataset.setValue("Uscite", myBudget.totaleUscite());
        JFreeChart chart = ChartFactory.createPieChart("Titolo del grafico", dataset, true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Entrate", new Color(112, 224, 0));
        plot.setSectionPaint("Uscite", new Color(208, 0, 0));
        ChartPanel chartPanel = new ChartPanel(chart);
        chart.setTitle("Grafico");
        chartPanel.setPreferredSize(new Dimension(bodyPanel.getSize().width / 2, bodyPanel.getSize().height / 2));

        /* Modifiche font */
        titleBanner.setFont(h1Font);
        bilancioTitle.setFont(h2Font);
        titleRicerca.setFont(h2Font);

        /* Modifiche dei pulsanti e dei componenti */
        addBtn.setPreferredSize(btnDimension);
        addBtn.setFont(btnFont);
        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        null,
                        addPanel,
                        "Nuova transazione",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    int importo = Integer.parseInt(importoField.getText());
                    LocalDate data = LocalDate.parse(dataField.getText(), formatter);
                    String descrizione = descrizioneField.getText();
                    if (importo > 0) {
                        myBudget.addTransazione(new Entrata(data, descrizione, importo));
                    } else {
                        myBudget.addTransazione(new Uscita(data, descrizione, importo));
                    }
                    model.fireTableDataChanged();
                    importoField.setText("");
                    dataField.setText(dataAttuale);
                    descrizioneField.setText("");
                    dataset.setValue("Entrate", myBudget.totaleEntrate());
                    dataset.setValue("Uscite", myBudget.totaleUscite());
                    bilancioValue.setText(String.format("%.2f", myBudget.totaleBilancio()) + "€");
                }
            }
        });

        delBtn.setPreferredSize(btnDimension);
        delBtn.setFont(btnFont);
        delBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableBudget.getRowCount() - tableBudget.getSelectedRow() - 1;
                myBudget.delTransazione(myBudget.getTransazioni().get(selectedRow));
                model.fireTableDataChanged();
                dataset.setValue("Entrate", myBudget.totaleEntrate());
                dataset.setValue("Uscite", myBudget.totaleUscite());
                bilancioValue.setText(String.format("%.2f", myBudget.totaleBilancio()) + "€");
            }
        });
        modBtn.setPreferredSize(btnDimension);
        modBtn.setFont(btnFont);
        modBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                importoField.setText(Objects.toString(tableBudget.getValueAt(tableBudget.getSelectedRow(), 1)).replaceAll("[^0-9.,]","").replaceAll(",","."));
                dataField.setText(Objects.toString(tableBudget.getValueAt(tableBudget.getSelectedRow(), 0)));
                descrizioneField.setText(Objects.toString(tableBudget.getValueAt(tableBudget.getSelectedRow(), 2)));
                int result = JOptionPane.showConfirmDialog(
                        null,
                        addPanel,
                        "Modifica transazione",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    double importo = Double.parseDouble(importoField.getText());
                    LocalDate data = LocalDate.parse(dataField.getText(), formatter);
                    String descrizione = descrizioneField.getText();
                    int selectedRow = tableBudget.getRowCount() - tableBudget.getSelectedRow() - 1;
                    if (importo > 0) {
                        myBudget.updateTransazione(selectedRow,
                                new Entrata(data, descrizione, importo));
                    } else {
                        myBudget.updateTransazione(selectedRow,
                                new Uscita(data, descrizione, importo));
                    }
                    model.fireTableDataChanged();
                    dataset.setValue("Entrate", myBudget.totaleEntrate());
                    dataset.setValue("Uscite", myBudget.totaleUscite());
                    bilancioValue.setText(String.format("%.2f", myBudget.totaleBilancio()) + "€");
                }
            }
        });

        periodChooser.setPreferredSize(btnDimension);
        periodChooser.setFont(btnFont);
        periodChooser.addActionListener(this);

        saveBtn.setPreferredSize(btnDimension);
        saveBtn.setFont(btnFont);
        loadBtn.setPreferredSize(btnDimension);
        loadBtn.setFont(btnFont);
        expBtn.setPreferredSize(btnDimension);
        expBtn.setFont(btnFont);
        printBtn.setPreferredSize(btnDimension);
        printBtn.setFont(btnFont);

        searchInput.setMaximumSize(new Dimension(550, 100));
        searchBtn.setPreferredSize(btnDimension);
        searchBtn.setFont(btnFont);
        searchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchText = searchInput.getText();
                if (!lastSearchText.equals(searchText)) {
                    searchRowIndex = -1;
                }
                if (searchRowIndex == tableBudget.getRowCount() - 1) {
                    JOptionPane.showMessageDialog(null, "Non ci sono più corrispondenze da mostrare.");
                    searchRowIndex = -1; 
                }
                if (searchRowIndex == -1) {
                    for (int row = 0; row < tableBudget.getRowCount(); row++) {
                        System.out.println("Actual Row: " + row);
                        if (iteraRigaTabella(tableBudget, searchText, row)) {
                            break;
                        } else if (!iteraRigaTabella(tableBudget, searchText, row)
                                && row == tableBudget.getRowCount() - 1) {
                            JOptionPane.showMessageDialog(null, "Non è stata trovata alcuna corrispondenza.");
                            searchRowIndex = -1;
                        }
                    }
                } else if (searchRowIndex != -1) {
                    for (int row = searchRowIndex + 1; row < tableBudget.getRowCount(); row++) {
                        System.out.println("Actual Row: " + row);
                        if (iteraRigaTabella(tableBudget, searchText, row)) {
                            break;
                        } else if (!iteraRigaTabella(tableBudget, searchText, row)) {
                            JOptionPane.showMessageDialog(null, "Non ci sono più corrispondenze da mostrare.");
                            searchRowIndex = -1;
                        }
                    }
                }
                lastSearchText = searchText;
                System.out.println("GetRowCount: " + tableBudget.getRowCount());
                System.out.println("Last text: " + lastSearchText);
                System.out.println("searchText: " + searchText);
                System.out.println("IndexSearchRow: " + searchRowIndex + "\n");
            }
        });

        /* Inserimento dei componenti e dei pannelli */
        banner.add(titleBanner);

        footer.add(saveBtn);
        footer.add(loadBtn);
        footer.add(expBtn);
        footer.add(printBtn);

        tbaleBtnsPanel.add(addBtn);
        tbaleBtnsPanel.add(modBtn);
        tbaleBtnsPanel.add(delBtn);
        tbaleBtnsPanel.add(periodChooser);

        leftPanel.add(tbaleBtnsPanel, BorderLayout.NORTH);
        leftPanel.add(tableScrollPane, BorderLayout.CENTER);

        searchPanel.add(Box.createVerticalStrut(20));
        searchPanel.add(titleRicerca);
        titleRicerca.setAlignmentX(CENTER_ALIGNMENT);
        searchPanel.add(Box.createVerticalStrut(10));
        searchPanel.add(searchInput);
        searchPanel.add(Box.createVerticalStrut(10));
        searchPanel.add(searchBtn);
        searchBtn.setAlignmentX(CENTER_ALIGNMENT);
        searchPanel.add(Box.createVerticalStrut(20));

        rightPanel.setPreferredSize(new Dimension(750, getSize().height));

        bilancioPanel.add(Box.createVerticalStrut(20));
        bilancioPanel.add(bilancioTitle);
        bilancioPanel.add(bilancioValue);
        bilancioTitle.setAlignmentX(CENTER_ALIGNMENT);
        bilancioValue.setAlignmentX(CENTER_ALIGNMENT);
        bilancioPanel.add(Box.createVerticalStrut(20));

        rightPanel.add(bilancioPanel);
        rightPanel.add(chartPanel);
        rightPanel.add(searchPanel);

        bodyPanel.add(leftPanel, BorderLayout.WEST);
        bodyPanel.add(rightPanel, BorderLayout.EAST);

        add(banner, BorderLayout.NORTH);
        add(footer, BorderLayout.SOUTH);
        add(bodyPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

    private boolean iteraRigaTabella(JTable tableBudget, String searchText, int row) {
        for (int column = 0; column < tableBudget.getColumnCount(); column++) {
            Object cellValue = tableBudget.getValueAt(row, column);
            if (cellValue != null && cellValue.toString().toLowerCase().contains(searchText)) {
                tableBudget.setRowSelectionInterval(row, row);
                tableBudget.setColumnSelectionInterval(column, column);
                Rectangle cellRect = tableBudget.getCellRect(row, 0, true);
                tableBudget.scrollRectToVisible(cellRect);
                searchRowIndex = row;
                return true;
            }
        }
        return false;
    }

    public void generaTransazioni(Bilancio myBudget, DateTimeFormatter formatter) {
        Random random = new Random();
        int year;
        int month;
        int dayOfMonth;
        LocalDate randomDate;

        for (int i = 0; i < 50; i++) {
            year = random.nextInt(2022 - 1970 + 1) + 1970;
            month = random.nextInt(12) + 1;
            dayOfMonth = random.nextInt(28) + 1;
            randomDate = LocalDate.of(year, month, dayOfMonth);
            double randomAmount = random.nextDouble() * 1000; // genera un valore casuale compreso tra 0 e 1000
            if (random.nextBoolean()) { // genera un valore casuale true o false
                Entrata entrata = new Entrata(randomDate, "Voce di entrata numero " + i, randomAmount);
                myBudget.addTransazione(entrata);
            } else {
                Uscita uscita = new Uscita(randomDate, "Voce di uscita numero " + i, -randomAmount);
                myBudget.addTransazione(uscita);
            }
        }
    }
}
