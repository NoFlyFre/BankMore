package com.noflyfre.bankmore.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.noflyfre.bankmore.Bilancio;
import com.noflyfre.bankmore.Entrata;
import com.noflyfre.bankmore.MyTableModel;
import com.noflyfre.bankmore.SerializablePieDataset;
import com.noflyfre.bankmore.Uscita;

/**
 * Classe che gestisce l'aggiunta di una transazione al bilancio.
 */
@SuppressWarnings("checkstyle:parameternumber")
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

    /**
     * Costruttore della classe.
     * @param importoField     campo di inserimento importo da cui prendere
     *                         l'importo
     * @param dataField        campo di inserimento data da cui prendere la data
     * @param descrizioneField campo di inserimento descrizione da cui prendere la
     *                         descrizione
     * @param myBudget         bilancio da modificare
     * @param budgetTableModel modello da modificare
     * @param dataAttuale      data attuale da inserire di default
     * @param addPanel         pannello da visualizzare
     * @param formatter        formatter di data
     * @param dataset          dataser da aggiornare, per modificare il grafico
     * @param bilancioValue    valore di bilancio da modificare
     */
    public AddActionListener(JTextField importoField, JTextField dataField, JTextField descrizioneField,
            Bilancio myBudget, MyTableModel budgetTableModel, String dataAttuale, JPanel addPanel,
            DateTimeFormatter formatter, SerializablePieDataset dataset, JLabel bilancioValue) {
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
        int result = JOptionPane.showConfirmDialog(null, addPanel, "Nuova transazione", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
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
            } catch (NumberFormatException exc) {
                JOptionPane.showMessageDialog(addPanel,
                        "Inserisci i dati corretti!\nLa data deve essere inserita esattamente in formato dd/MM/yyyy.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                importoField.setText("");
                dataField.setText(dataAttuale);
                descrizioneField.setText("");
            } catch (DateTimeParseException exc) {
                JOptionPane.showMessageDialog(addPanel,
                        "Inserisci i dati corretti!\nLa data deve essere inserita esattamente in formato dd/MM/yyyy.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                importoField.setText("");
                dataField.setText(dataAttuale);
                descrizioneField.setText("");
            }
        }
    }
}
