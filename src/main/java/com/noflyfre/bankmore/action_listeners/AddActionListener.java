package com.noflyfre.bankmore.action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Formatter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.noflyfre.bankmore.Bilancio;
import com.noflyfre.bankmore.Entrata;
import com.noflyfre.bankmore.MyTableModel;
import com.noflyfre.bankmore.SerializablePieDataset;
import com.noflyfre.bankmore.Uscita;

public class AddActionListener implements ActionListener {

    private JTextField importoField;
    private JTextField dataField;
    private JTextField descrizioneField;
    private Bilancio myBudget;
    private MyTableModel budgetTableModel;
    private String dataAttuale;
    private JPanel addPanel;
    private DateTimeFormatter formatter;
    private SerializablePieDataset dataset;
    private JLabel bilancioValue;


    public AddActionListener(JTextField importoField, JTextField dataField, JTextField descrizioneField,
            Bilancio myBudget, MyTableModel budgetTableModel, String dataAttuale,
            JPanel addPanel, DateTimeFormatter formatter, SerializablePieDataset dataset, JLabel bilancioValue) {
        this.importoField = importoField;
        this.dataField = dataField;
        this.descrizioneField = descrizioneField;
        this.myBudget = myBudget;
        this.budgetTableModel = budgetTableModel;
        this.dataAttuale = dataAttuale;
        this.addPanel = addPanel;
        this.formatter = formatter;
        this.dataset = dataset;
        this.bilancioValue = bilancioValue;
    }


    @Override
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
            budgetTableModel.fireTableDataChanged();
            importoField.setText("");
            dataField.setText(dataAttuale);
            descrizioneField.setText("");
            dataset.setValue("Entrate", myBudget.totaleEntrate());
            dataset.setValue("Uscite", myBudget.totaleUscite());
            bilancioValue.setText(String.format("%.2f", myBudget.totaleBilancio()) + "€");
        }
    }
}
