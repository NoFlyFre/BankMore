package com.noflyfre.bankmore.action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTable;

import com.noflyfre.bankmore.Bilancio;
import com.noflyfre.bankmore.MyTableModel;
import com.noflyfre.bankmore.SerializablePieDataset;

public class DeleteActionListener implements ActionListener{

    private JTable tableBudget;
    private Bilancio myBudget;
    private MyTableModel budgetTableModel;
    private SerializablePieDataset dataset;
    private JLabel bilancioValue;

    public DeleteActionListener(JTable tableBudget, Bilancio myBudget, MyTableModel budgetTableModel,
            SerializablePieDataset dataset, JLabel bilancioValue) {
        this.tableBudget = tableBudget;
        this.myBudget = myBudget;
        this.budgetTableModel = budgetTableModel;
        this.dataset = dataset;
        this.bilancioValue = bilancioValue;
    }



    public void actionPerformed(ActionEvent e) {
        int selectedRow = tableBudget.getRowCount() - tableBudget.getSelectedRow() - 1;
        myBudget.delTransazione(myBudget.getTransazioni().get(selectedRow));
        budgetTableModel.fireTableDataChanged();
        dataset.setValue("Entrate", myBudget.totaleEntrate());
        dataset.setValue("Uscite", myBudget.totaleUscite());
        bilancioValue.setText(String.format("%.2f", myBudget.totaleBilancio()) + "€");
    }
    
}
