package com.noflyfre.bankmore.action_listeners;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class SearchActionListener implements ActionListener{

    private JTextField searchInput;
    private String lastSearchText;
    private int searchRowIndex;
    private JTable tableBudget;

    public SearchActionListener(JTextField searchInput, String lastSearchText, int searchRowIndex, JTable tableBudget) {
        this.searchInput = searchInput;
        this.lastSearchText = lastSearchText;
        this.searchRowIndex = searchRowIndex;
        this.tableBudget = tableBudget;
    }

    public void actionPerformed(ActionEvent e) {
        String searchText = searchInput.getText();
        if (!lastSearchText.equals(searchText)) {
            searchRowIndex = -1;
        }
        if (searchRowIndex == tableBudget.getRowCount() - 1) {
            JOptionPane.showMessageDialog(null, "Non ci sono più corrispondenze da mostrare.");
            searchRowIndex = -1;
        }
        if (searchRowIndex == -1) {
            for (int row = 0; row < tableBudget.getRowCount(); row++) {
                System.out.println("Actual Row: " + row);
                if (iteraRigaTabella(tableBudget, searchText, row)) {
                    break;
                } else if (!iteraRigaTabella(tableBudget, searchText, row)
                        && row == tableBudget.getRowCount() - 1) {
                    JOptionPane.showMessageDialog(null, "Non è stata trovata alcuna corrispondenza.");
                    searchRowIndex = -1;
                }
            }
        } else if (searchRowIndex != -1) {
            for (int row = searchRowIndex + 1; row < tableBudget.getRowCount(); row++) {
                System.out.println("Actual Row: " + row);
                if (iteraRigaTabella(tableBudget, searchText, row)) {
                    break;
                } else if (!iteraRigaTabella(tableBudget, searchText, row)) {
                    JOptionPane.showMessageDialog(null, "Non ci sono più corrispondenze da mostrare.");
                    searchRowIndex = -1;
                }
            }
        }
        lastSearchText = searchText;
        System.out.println("GetRowCount: " + tableBudget.getRowCount());
        System.out.println("Last text: " + lastSearchText);
        System.out.println("searchText: " + searchText);
        System.out.println("IndexSearchRow: " + searchRowIndex + "\n");
    }

    public boolean iteraRigaTabella(JTable tableBudget, String searchText, int row) {
        for (int column = 0; column < tableBudget.getColumnCount(); column++) {
            Object cellValue = tableBudget.getValueAt(row, column);
            if (cellValue != null && cellValue.toString().toLowerCase().contains(searchText)) {
                tableBudget.setRowSelectionInterval(row, row);
                tableBudget.setColumnSelectionInterval(column, column);
                Rectangle cellRect = tableBudget.getCellRect(row, 0, true);
                tableBudget.scrollRectToVisible(cellRect);
                searchRowIndex = row;
                return true;
            }
        }
        return false;
    }

}
