package com.noflyfre.bankmore.actionlisteners;

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

import com.noflyfre.bankmore.gui.MyTableModel;
import com.noflyfre.bankmore.gui.SerializablePieDataset;
import com.noflyfre.bankmore.logic.Bilancio;
import com.noflyfre.bankmore.logic.Entrata;
import com.noflyfre.bankmore.logic.Uscita;
import com.noflyfre.bankmore.logic.VoceBilancio;

/**
 * Classe che gestisce la modifica di una voce nella tabella.
 */
public class ModActionListener implements ActionListener {
    private JTable tableBudget;
    private JTextField importoField;
    private JTextField dataField;
    private JTextField descrizioneField;
    private JPanel addPanel;
    private DateTimeFormatter formatter;
    private Bilancio myBudget;
    private MyTableModel budgetTableModel;
    private SerializablePieDataset dataset;
    private JLabel bilancioValue;
    private boolean filter;

    /**
     * Costruttore della classe ModActionListener.
     *
     * @param tableBudget
     * @param importoField
     * @param dataField
     * @param descrizioneField
     * @param addPanel
     * @param formatter
     * @param myBudget
     * @param budgetTableModel
     * @param dataset
     * @param bilancioValue
     */
    public ModActionListener(JTable tableBudget, JTextField importoField, JTextField dataField,
            JTextField descrizioneField, JPanel addPanel, DateTimeFormatter formatter, Bilancio myBudget,
            MyTableModel budgetTableModel, SerializablePieDataset dataset, JLabel bilancioValue, boolean filter) {
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
        this.filter = filter;
    }

    /**
     * Il metodo actionPerformed viene chiamato quando viene selezionato l'elemento associato alla classe che implementa
     * l'interfaccia ActionListener e viene utilizzato per modificare una transazione presente nella tabella
     * tableBudget. Se l'utente seleziona una riga della tabella, i campi importoField, dataField e descrizioneField
     * vengono impostati con i valori della transazione selezionata. Viene quindi visualizzata una finestra di conferma
     * per richiedere all'utente di confermare la modifica della transazione. Se l'utente conferma la modifica, viene
     * aggiornata la transazione nella tabella e nel dataset della tabella, e viene aggiornato il valore del bilancio
     * visualizzato. Se l'utente non conferma la modifica o se non è stata selezionata alcuna riga della tabella, non
     * viene effettuata alcuna modifica.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (filter) {
            return;
        }
        VoceBilancio voceSelezionata = budgetTableModel.getDati()
                .get(budgetTableModel.getRowCount() - tableBudget.getSelectedRow() - 2);
        if (tableBudget.getSelectedRow() != tableBudget.getRowCount() - 1) {
            if (voceSelezionata instanceof Uscita) {
                importoField.setText("-" + Objects.toString(tableBudget.getValueAt(tableBudget.getSelectedRow(), 1))
                        .replaceAll("[^0-9.,]", "").replaceAll(",", "."));
            } else {
                importoField.setText(Objects.toString(tableBudget.getValueAt(tableBudget.getSelectedRow(), 1))
                        .replaceAll("[^0-9.,]", "").replaceAll(",", "."));
            }
            dataField.setText(Objects.toString(tableBudget.getValueAt(tableBudget.getSelectedRow(), 0)));
            descrizioneField.setText(Objects.toString(tableBudget.getValueAt(tableBudget.getSelectedRow(), 2)));
            int result = JOptionPane.showConfirmDialog(null, addPanel, "Modifica transazione",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    double importo = Double.parseDouble(importoField.getText());
                    LocalDate data = LocalDate.parse(dataField.getText(), formatter);
                    String descrizione = descrizioneField.getText();
                    int selectedRow = tableBudget.getRowCount() - tableBudget.getSelectedRow() - 2;
                    if (importo > 0) {
                        myBudget.updateTransazione(selectedRow, new Entrata(data, descrizione, importo));
                    } else {
                        myBudget.updateTransazione(selectedRow, new Uscita(data, descrizione, importo));
                    }
                    budgetTableModel.fireTableDataChanged();
                    dataset.setValue("Entrate", myBudget.totaleEntrate());
                    dataset.setValue("Uscite", myBudget.totaleUscite());
                    bilancioValue.setText(String.format("%.2f", myBudget.totaleBilancio()) + "€");
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null,
                            "Inserisci i dati corretti!\nN.B: La data deve essere inserita esattamente in formato dd/MM/yyyy.",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
