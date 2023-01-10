package com.noflyfre.bankmore.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JTable;

import com.noflyfre.bankmore.gui.MyTableModel;
import com.noflyfre.bankmore.gui.SerializablePieDataset;
import com.noflyfre.bankmore.logic.Bilancio;

/**
 * Classe che gestisce il listener per eliminare una voce dal bilancio.
 */
public class DeleteActionListener implements ActionListener {
    private JTable tableBudget;
    private Bilancio myBudget;
    private MyTableModel budgetTableModel;
    private SerializablePieDataset dataset;
    private JLabel bilancioValue;
    private boolean filter;

    /**
     * Costruttore della classe.
     *
     * @param tableBudget
     *            tabella da modificare
     * @param myBudget
     *            bilancio da modificare
     * @param budgetTableModel
     *            modello di tabella da modificare
     * @param dataset
     *            dataser da modificare per il grafico
     * @param bilancioValue
     *            valore bilancio da modificare
     */
    public DeleteActionListener(JTable tableBudget, Bilancio myBudget, MyTableModel budgetTableModel,
            SerializablePieDataset dataset, JLabel bilancioValue, boolean filter) {
        this.tableBudget = tableBudget;
        this.myBudget = myBudget;
        this.budgetTableModel = budgetTableModel;
        this.dataset = dataset;
        this.bilancioValue = bilancioValue;
        this.filter = filter;
    }

    /**
     * Metodo che elimina una transazione dal bilancio. Questo meotodo elimina la transazione selezionata sulla tabella
     * dal bilancio, ed oltre a ciò, aggiorna la tabella, i dataset per il grafico, ed il valore del bilancio nella GUI.
     */
    public void actionPerformed(ActionEvent e) {
        if (filter) {
            return;
        }
        /**
         * viene usato getRowCount - getSelectedRow - 2, perchè nella visualizzazione della tabella le righe sono
         * invertite rispetto alla realtà, quindi per considerare la riga corretta, devo re-invertire il valore
         * selezionato. Oltre a ciò, considerando che c'è la riga del totale, va tolto 2 invece che 1.
         */
        int selectedRow = tableBudget.getRowCount() - tableBudget.getSelectedRow() - 2;
        myBudget.delTransazione(myBudget.getTransazioni().get(selectedRow));
        budgetTableModel.fireTableDataChanged();
        dataset.setValue("Entrate", myBudget.totaleEntrate());
        dataset.setValue("Uscite", myBudget.totaleUscite());
        bilancioValue.setText(String.format("%.2f", myBudget.totaleBilancio()) + "€");
    }
}
