package com.noflyfre.bankmore.action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.noflyfre.bankmore.Bilancio;
import com.noflyfre.bankmore.Entrata;
import com.noflyfre.bankmore.MyTableModel;
import com.noflyfre.bankmore.SerializablePieDataset;
import com.noflyfre.bankmore.Uscita;

public class ModActionListener implements ActionListener{

    JTable tableBudget;
    JTextField importoField;
    JTextField dataField;
    JTextField descrizioneField;
    JPanel addPanel;
    DateTimeFormatter formatter;
    Bilancio myBudget;
    MyTableModel budgetTableModel;
    SerializablePieDataset dataset;
    JLabel bilancioValue;

    

    public ModActionListener(JTable tableBudget, JTextField importoField, JTextField dataField,
            JTextField descrizioneField, JPanel addPanel, DateTimeFormatter formatter, Bilancio myBudget,
            MyTableModel budgetTableModel, SerializablePieDataset dataset, JLabel bilancioValue) {
        this.tableBudget = tableBudget;
        this.importoField = importoField;
        this.dataField = dataField;
        this.descrizioneField = descrizioneField;
        this.addPanel = addPanel;
        this.formatter = formatter;
        this.myBudget = myBudget;
        this.budgetTableModel = budgetTableModel;
        this.dataset = dataset;
        this.bilancioValue = bilancioValue;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
                importoField.setText(Objects.toString(tableBudget.getValueAt(tableBudget.getSelectedRow(), 1))
                        .replaceAll("[^0-9.,]", "").replaceAll(",", "."));
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
                    budgetTableModel.fireTableDataChanged();
                    dataset.setValue("Entrate", myBudget.totaleEntrate());
                    dataset.setValue("Uscite", myBudget.totaleUscite());
                    bilancioValue.setText(String.format("%.2f", myBudget.totaleBilancio()) + "€");
                }
            }
    
}
