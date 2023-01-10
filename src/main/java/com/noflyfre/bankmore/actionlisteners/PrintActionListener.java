package com.noflyfre.bankmore.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import com.noflyfre.bankmore.logic.Bilancio;
import com.noflyfre.bankmore.logic.BilancioPrintable;

/**
 * Classe che gestisce la stampa del bilancio.
 */
public class PrintActionListener implements ActionListener {

    private Bilancio myBudget;

    public PrintActionListener(Bilancio myBudget) {
        this.myBudget = myBudget;
    }

    /**
     * Metodo che gestisce la stampa del bilancio utilizzando la classe PrinterJob. Crea un oggetto PrinterJob, imposta
     * la stampa con la classe BilancioPrintable e apre la finestra di dialogo di stampa. Se l'utente conferma la
     * stampa, viene eseguita la stampa del bilancio attraverso l'oggetto PrinterJob. In caso di errore di stampa, viene
     * stampato il messaggio di errore con l'eccezione.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new BilancioPrintable(myBudget));
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }
}
