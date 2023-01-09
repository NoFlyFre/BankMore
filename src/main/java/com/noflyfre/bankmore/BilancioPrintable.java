package com.noflyfre.bankmore;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.time.format.DateTimeFormatter;

/**
 * Classe che implementa l'interfaccia Printable e che permette di stampare il
 * bilancio.
 */
@SuppressWarnings("checkstyle:magicnumber")
public class BilancioPrintable implements Printable {
    private Bilancio myBudget;

    /**
     * Costruttore di BilancioPrintable.
     * 
     * @param myBudget bilancio da stampare.
     */
    public BilancioPrintable(Bilancio myBudget) {
        this.myBudget = myBudget;
    }

    /**
     * Metodo che viene chiamato per ottenere il contenuto da
     * stampare.
     * 
     * @param graphics   Oggetto Graphics che rappresenta l'area di stampa
     * @param pageFormat Formato della pagina
     * @param pageIndex  Indice della pagina da stampare
     * @return Un intero che indica se la pagina esiste o meno
     * @throws PrinterException In caso ci sia un errore durante la stampa
     */
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        // Calcola il numero di pagine totali da stampare
        int numPages = (int) Math.ceil((double) myBudget.getTransazioni().size() / 40);

        // Se pageIndex è maggiore del numero di pagine totali, non ci sono altre pagine
        // da stampare
        if (pageIndex >= numPages) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        int y = 20;
        int startIndex = pageIndex * 40;
        for (int i = startIndex; i < startIndex + 40 && i < myBudget.getTransazioni().size(); i++) {
            VoceBilancio voce = myBudget.getTransazioni().get(i);
            g2d.drawString(voce.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 20, y);
            String.valueOf(voce.getAmount());
            g2d.drawString(String.format("%.2f", voce.getAmount()) + "€", 120, y);
            g2d.drawString(voce.getDescrizione(), 220, y);
            y += 20;
        }
        return PAGE_EXISTS;
    }
}
