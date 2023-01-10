package com.noflyfre.bankmore.actionlisteners;

import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;

import com.noflyfre.bankmore.gui.MyTableModel;
import com.noflyfre.bankmore.logic.Bilancio;
import com.toedter.calendar.JDateChooser;

/**
 * Classe che gestisce il fitraggio dei dati della tabella.
 */
public class FilterActionListener implements ActionListener {

    private Bilancio myBudget;
    private MyTableModel budgetTableModel;
    private JButton filterBtn;
    private Boolean filter;
    private String btnText;
    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;
    private JComboBox periodChooser;

    /**
     * Costruttore della classe FilterActionListener.
     */
    public FilterActionListener(Bilancio myBudget, MyTableModel budgetTableModel, JButton filterBtn, Boolean filter,
            JDateChooser startDateChooser, JDateChooser endDateChooser, JComboBox periodChooser) {
        this.myBudget = myBudget;
        this.budgetTableModel = budgetTableModel;
        this.filterBtn = filterBtn;
        this.filter = filter;
        this.btnText = filterBtn.getText();
        this.startDateChooser = startDateChooser;
        this.endDateChooser = endDateChooser;
        this.periodChooser = periodChooser;
    }

    /**
     * Questo metodo rappresenta l'evento di pressione del bottone di filtraggio delle transazioni. Se il filtraggio non
     * è attivo, viene attivato e vengono filtrate le transazioni in base alle date specificate. Se il filtraggio è già
     * attivo, viene disattivato e vengono mostrate tutte le transazioni. Il bottone viene aggiornato in base allo stato
     * di filtraggio.
     *
     * @param e
     *            L'evento di pressione del bottone di filtraggio.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!filter) {
            filterBtn.setText("Reset");
            filter = true;
            budgetTableModel.filtraVoci(
                    startDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    endDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } else {
            filterBtn.setText("Filtra");
            filter = false;
            periodChooser.setSelectedIndex(0);
            budgetTableModel.resetTableData();
            startDateChooser.setDate(null);
            endDateChooser.setDate(null);
        }
    }

}
