package com.noflyfre.bankmore.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import com.noflyfre.bankmore.actionlisteners.AddActionListener;
import com.noflyfre.bankmore.actionlisteners.DeleteActionListener;
import com.noflyfre.bankmore.actionlisteners.ExportActionListener;
import com.noflyfre.bankmore.actionlisteners.FilterActionListener;
import com.noflyfre.bankmore.actionlisteners.LoadActionListener;
import com.noflyfre.bankmore.actionlisteners.ModActionListener;
import com.noflyfre.bankmore.actionlisteners.PeriodActionListener;
import com.noflyfre.bankmore.actionlisteners.PrintActionListener;
import com.noflyfre.bankmore.actionlisteners.SaveActionListener;
import com.noflyfre.bankmore.actionlisteners.SearchActionListener;
import com.noflyfre.bankmore.logic.AutoSave;
import com.noflyfre.bankmore.logic.Bilancio;
import com.noflyfre.bankmore.logic.Entrata;
import com.noflyfre.bankmore.logic.Uscita;
import com.toedter.calendar.JDateChooser;

/**
 * Classe che definisce il frame della GUI.
 */
@SuppressWarnings("checkstyle:magicnumber")
public class AppFrame extends JFrame {
    private final Font btnFont = new Font("Arial", Font.PLAIN, 20);
    private final Font h1Font = new Font("Arial", Font.BOLD, 40);
    private final Font h2Font = new Font("Arial", Font.BOLD, 25);
    private final Dimension btnDimension = new Dimension(120, 40);
    private final Dimension frameMinSize = new Dimension(850, 700);

    private JButton delBtn = new JButton("Elimina");
    private JButton addBtn = new JButton("Aggiungi");
    private JButton modBtn = new JButton("Modifica");
    private JButton saveBtn = new JButton("Salva");
    private JButton loadBtn = new JButton("Carica");
    private JButton expBtn = new JButton("Esporta");
    private JButton printBtn = new JButton("Stampa");
    private JButton searchBtn = new JButton("Cerca");
    private JButton filterBtn = new JButton("Filtra");
    private JPanel addPanel = new JPanel();

    private JTextField importoField = new JTextField(20);
    private JTextField dataField = new JTextField(20);
    private JTextField descrizioneField = new JTextField(20);

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String dataAttualeString = LocalDate.now().format(formatter);

    private JDateChooser dataInizioChooser = new JDateChooser();
    private JDateChooser dataFineChooser = new JDateChooser();

    private int searchRowIndex = -1;
    private String lastSearchText = "";
    private boolean filter;

    private AutoSave autoSaver;

    /**
     * Costruttore della classe AppFrame. Inizializza le proprietà principali del
     * frame ed inizializza i componenti
     * grafici.
     */
    public AppFrame(Bilancio myBudget) {
        FlatArcIJTheme.setup();
        setTitle("BankMore");
        generaTransazioni(myBudget);
        initComponents(myBudget);
        autoSaver = new AutoSave(myBudget);
        autoSaver.start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(frameMinSize);
        setVisible(true);
    }

    /**
     * Metodo che inizializza e posiziona i componenti nella GUI.
     *
     * @param myBudget
     *                 Oggetto di tipo Bilancio passato alla GUI per la
     *                 rappresentazione dei dati.
     */
    private void initComponents(Bilancio budget) {
        final Bilancio myBudget = budget;

        MyTableModel budgetTableModel = new MyTableModel(myBudget);
        JTable tableBudget = new JTable(budgetTableModel);

        JTextField searchInput = new JTextField(20); // da finire, inserire testo precompilato

        String[] periods = { "", "Annuale", "Mensile", "Settimanale", "Giornaliero" };
        JComboBox<String> periodChooser = new JComboBox<String>(periods);

        JDateChooser startDateChooser = new JDateChooser(null, "dd/MM/yyyy");
        JDateChooser endDateChooser = new JDateChooser(null, "dd/MM/yyyy");

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
        JPanel tableBtnsPanel = new JPanel();
        JPanel searchPanel = new JPanel();
        JPanel bodyPanel = new JPanel();
        JPanel bilancioPanel = new JPanel();
        JPanel dateChooserPanel = new JPanel();

        /* Definizione dei layout */
        setLayout(new BorderLayout());
        banner.setLayout(new FlowLayout());
        footer.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        leftPanel.setLayout(new BorderLayout());
        tableBtnsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.X_AXIS));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        bilancioPanel.setLayout(new BoxLayout(bilancioPanel, BoxLayout.Y_AXIS));
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
        dateChooserPanel.setLayout(new BoxLayout(dateChooserPanel, BoxLayout.Y_AXIS));

        // Precompila il campo di input della data con la data attuale
        dataField.setText(dataAttualeString);
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
        SerializablePieDataset dataset = new SerializablePieDataset();
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
        AddActionListener addBtnListener = new AddActionListener(importoField, dataField, descrizioneField, myBudget,
                budgetTableModel, dataAttualeString, addPanel, formatter, dataset, bilancioValue, filter);
        addBtn.addActionListener(addBtnListener);

        delBtn.setPreferredSize(btnDimension);
        delBtn.setFont(btnFont);
        DeleteActionListener delBtnListener = new DeleteActionListener(tableBudget, myBudget, budgetTableModel, dataset,
                bilancioValue, filter);
        delBtn.addActionListener(delBtnListener);

        modBtn.setPreferredSize(btnDimension);
        modBtn.setFont(btnFont);
        ModActionListener modBtnListener = new ModActionListener(tableBudget, importoField, dataField, descrizioneField,
                addPanel, formatter, myBudget, budgetTableModel, dataset, bilancioValue, filter);
        modBtn.addActionListener(modBtnListener);

        periodChooser.setPreferredSize(btnDimension);
        periodChooser.setFont(btnFont);
        PeriodActionListener periodChooserListener = new PeriodActionListener(periodChooser, startDateChooser,
                endDateChooser, filter, filterBtn, budgetTableModel);
        periodChooser.addActionListener(periodChooserListener);

        saveBtn.setPreferredSize(btnDimension);
        saveBtn.setFont(btnFont);
        SaveActionListener saveBtnListener = new SaveActionListener(myBudget);
        saveBtn.addActionListener(saveBtnListener);

        loadBtn.setPreferredSize(btnDimension);
        loadBtn.setFont(btnFont);
        LoadActionListener loadBtnListener = new LoadActionListener(myBudget, budgetTableModel, dataset, bilancioValue);
        loadBtn.addActionListener(loadBtnListener);

        expBtn.setPreferredSize(btnDimension);
        expBtn.setFont(btnFont);
        ExportActionListener expBtnListener = new ExportActionListener(myBudget);
        expBtn.addActionListener(expBtnListener);

        printBtn.setPreferredSize(btnDimension);
        printBtn.setFont(btnFont);
        PrintActionListener printBtnListener = new PrintActionListener(myBudget);
        printBtn.addActionListener(printBtnListener);

        searchInput.setMaximumSize(new Dimension(550, 100));
        searchBtn.setPreferredSize(btnDimension);
        searchBtn.setFont(btnFont);
        SearchActionListener searchBtnListener = new SearchActionListener(searchInput, lastSearchText, searchRowIndex,
                tableBudget);
        searchBtn.addActionListener(searchBtnListener);
        searchInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchBtn.doClick();
                }
            }
        });

        /* Inserimento dei componenti e dei pannelli */
        banner.add(titleBanner);

        footer.add(saveBtn);
        footer.add(loadBtn);
        footer.add(expBtn);
        footer.add(printBtn);

        dateChooserPanel.add(startDateChooser);
        dateChooserPanel.add(endDateChooser);
        dateChooserPanel.add(filterBtn);
        filterBtn.setAlignmentX(CENTER_ALIGNMENT);
        FilterActionListener filterBtnListener = new FilterActionListener(budgetTableModel, filterBtn, filter,
                startDateChooser, endDateChooser, periodChooser);
        filterBtn.addActionListener(filterBtnListener);
        
        tableBtnsPanel.add(addBtn);
        tableBtnsPanel.add(modBtn);
        tableBtnsPanel.add(delBtn);
        tableBtnsPanel.add(periodChooser);
        tableBtnsPanel.add(dateChooserPanel);

        leftPanel.add(tableBtnsPanel, BorderLayout.NORTH);
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

    /**
     * Metodo che genera causalmente n transazioni.
     */
    public void generaTransazioni(Bilancio myBudget) {
        Random random = new Random();
        int year;
        int month;
        int dayOfMonth;
        LocalDate randomDate;

        for (int i = 0; i < 10; i++) {
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
