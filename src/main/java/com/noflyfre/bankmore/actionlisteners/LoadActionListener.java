package com.noflyfre.bankmore.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.noflyfre.bankmore.Bilancio;
import com.noflyfre.bankmore.MyTableModel;
import com.noflyfre.bankmore.SerializablePieDataset;

public class LoadActionListener implements ActionListener {

    private Bilancio myBudget;
    private MyTableModel budgetTableModel;
    private SerializablePieDataset dataset;
    private JLabel bilancioValue;

    public LoadActionListener(Bilancio myBudget, MyTableModel budgetTableModel, SerializablePieDataset dataset,
            JLabel bilancioValue) {
        this.myBudget = myBudget;
        this.budgetTableModel = budgetTableModel;
        this.dataset = dataset;
        this.bilancioValue = bilancioValue;
    }

    public void actionPerformed(ActionEvent e) {
        Bilancio budgetCaricato = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Apri file");
        fileChooser.setApproveButtonText("Apri");
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileToOpen))) {
                // Leggere l'oggetto bilancio dallo stream e assegnarlo a myBudget
                budgetCaricato = (Bilancio) in.readObject();
                myBudget = budgetCaricato;
                dataset.setValue("Entrate", myBudget.totaleEntrate());
                dataset.setValue("Uscite", myBudget.totaleUscite());
                bilancioValue.setText(String.format("%.2f", myBudget.totaleBilancio()) + "€");
                budgetTableModel.setOriginalData(myBudget.getTransazioni());
                budgetTableModel.setDati(myBudget.getTransazioni());
                budgetTableModel.fireTableDataChanged();
            } catch (IOException exc) {
                JOptionPane.showMessageDialog(fileChooser, "File non esistente.", "Errore di caricamento",
                        JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(fileChooser, "File non supportato.", "Errore di caricamento",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
