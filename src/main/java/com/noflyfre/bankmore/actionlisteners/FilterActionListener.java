package com.noflyfre.bankmore.actionlisteners;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import com.noflyfre.bankmore.Bilancio;
import com.noflyfre.bankmore.MyTableModel;
import com.toedter.calendar.JDateChooser;

public class FilterActionListener implements ActionListener {

    private Bilancio myBudget;
    private MyTableModel budgetTableModel;
    private JButton filterBtn;
    private Boolean filter;
    private String btnText;
    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;

    public FilterActionListener(Bilancio myBudget, MyTableModel budgetTableModel, JButton filterBtn, Boolean filter,
            JDateChooser startDateChooser, JDateChooser endDateChooser) {
        this.myBudget = myBudget;
        this.budgetTableModel = budgetTableModel;
        this.filterBtn = filterBtn;
        this.filter = filter;
        this.btnText = filterBtn.getText();
        this.startDateChooser = startDateChooser;
        this.endDateChooser = endDateChooser;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Filtro: " + filter);
        if (filter == false) {
            filterBtn.setText("Reset");
            filter = true;
            budgetTableModel.filtraVoci(
                    startDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    endDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } else {
            filterBtn.setText("Filtra");
            filter = false;
            budgetTableModel.resetTableData();
            startDateChooser.setDate(null);
            endDateChooser.setDate(null);
        }
    }

}
