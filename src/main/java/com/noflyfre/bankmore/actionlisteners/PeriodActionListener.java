package com.noflyfre.bankmore.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;

import com.noflyfre.bankmore.gui.MyTableModel;
import com.toedter.calendar.JDateChooser;

/**
 * Classe che gestisce le funzioni di selezione del periodo preimpostato.
 */
public class PeriodActionListener implements ActionListener {
    private JComboBox<String> periodChooser;
    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;
    private JButton filterBtn;
    private MyTableModel budgetTableModel;

    /**
     * Costruttore della classe PeriodActionListener.
     */
    public PeriodActionListener(JComboBox<String> periodChooser, JDateChooser startDateChooser,
            JDateChooser endDateChooser, boolean filter, JButton filterBtn, MyTableModel budgetTableModel) {
        this.periodChooser = periodChooser;
        this.startDateChooser = startDateChooser;
        this.endDateChooser = endDateChooser;
        this.filterBtn = filterBtn;
        this.budgetTableModel = budgetTableModel;
    }

    /**
     * Metodo che imposta le date corrispondenti nei field di data in base alla scelta selezionata nel menù a tendina
     * delle scelte preimpostate.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object selectedItem = periodChooser.getSelectedItem();
        LocalDate dataAttuale = LocalDate.now();

        if (selectedItem.equals("Annuale")) {
            // Imposta la data di inizio al giorno di un anno fa
            startDateChooser.setDate(Date.from(dataAttuale.minusYears(1).atStartOfDay().toInstant(ZoneOffset.UTC)));

            // Imposta la data di fine al giorno di oggi
            endDateChooser.setDate(Date.from(dataAttuale.atStartOfDay().toInstant(ZoneOffset.UTC)));

            // Attiva il filtro
            activeFilter();
        } else if (selectedItem.equals("Mensile")) {
            // Imposta la data di inizio al giorno di un mese fa
            startDateChooser.setDate(Date.from(dataAttuale.minusMonths(1).atStartOfDay().toInstant(ZoneOffset.UTC)));

            // Imposta la data di fine al giorno di oggi
            endDateChooser.setDate(Date.from(dataAttuale.atStartOfDay().toInstant(ZoneOffset.UTC)));

            // Attiva il filtro
            activeFilter();
        } else if (selectedItem.equals("Mensile")) {
            // Imposta la data di inizio al giorno di una settimana fa
            startDateChooser.setDate(Date.from(dataAttuale.minusWeeks(1).atStartOfDay().toInstant(ZoneOffset.UTC)));

            // Imposta la data di fine al giorno di oggi
            endDateChooser.setDate(Date.from(dataAttuale.atStartOfDay().toInstant(ZoneOffset.UTC)));

            // Attiva il filtro
            activeFilter();
        } else if (selectedItem.equals("Mensile")) {
            // Imposta la data di inizio al giorno di un giorno fa
            startDateChooser.setDate(Date.from(dataAttuale.minusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC)));

            // Imposta la data di fine al giorno di oggi
            endDateChooser.setDate(Date.from(dataAttuale.atStartOfDay().toInstant(ZoneOffset.UTC)));

            // Attiva il filtro
            activeFilter();
        } else if (selectedItem.equals("")) {
            // Imposta la data di inizio al giorno di un giorno fa
            startDateChooser.setDate(null);

            // Imposta la data di fine al giorno di oggi
            endDateChooser.setDate(null);

            filterBtn.setText("Filtra");
            budgetTableModel.resetTableData();
        }
    }

    /**
     * Metodo che attiva il filtro di periodo.
     */
    private void activeFilter() {
        filterBtn.setText("Reset");
        budgetTableModel.filtraVoci(startDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                endDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

}
