package com.noflyfre.bankmore.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import com.noflyfre.bankmore.Bilancio;
import com.noflyfre.bankmore.BilancioPrintable;

public class PrintActionListener implements ActionListener {

    Bilancio myBudget;

    public PrintActionListener(Bilancio myBudget) {
        this.myBudget = myBudget;
    }

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
