package com.noflyfre.bankmore.logic;

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
     * @param myBudget
     *                 bilancio da stampare.
     */
    public BilancioPrintable(Bilancio myBudget) {
        this.myBudget = myBudget;
    }

    /**
     *
     * Questo metodo sovrascrive {@link Printable#print(Graphics, PageFormat, int)}
     * per creare una stampa personalizzata
     * del bilancio. Il metodo utilizza il {@link Graphics2D} per disegnare i dati
     * del bilancio sulla pagina stampata.
     * Il metodo utilizza un ciclo per stampare le voci del bilancio a partire
     * dall'indice specificato, fino a quando
     * non si raggiunge la fine delle voci del bilancio o se si raggiunge il limite
     * massimo di voci per pagina.
     *
     * @param graphics
     *                   L'oggetto fornito dal sistema per disegnare sulla pagina
     *                   stampata.
     * @param pageFormat
     *                   Il formato della pagina fornito dal sistema per la stampa.
     * @param pageIndex
     *                   L'indice della pagina corrente da stampare.
     *
     * @return Un intero che indica se la pagina esiste o no. Restituisce
     *         PAGE_EXISTS se la pagina esiste, altrimenti
     *         restituisce NO_SUCH_PAGE.
     *
     * @throws PrinterException
     *                          In caso di errore durante la stampa.
     */
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        // Calcola il numero di pagine totali da stampare considerando 40 transazioni
        // per pagina.
        int numPages = (int) Math.ceil((double) myBudget.getTransazioni().size() / 40);

        // Se pageIndex è maggiore del numero di pagine totali, non ci sono altre pagine
        // da stampare.
        if (pageIndex >= numPages) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        /**
         * Questo metodo si occupa di stampare le informazioni del bilancio nella pagina
         * corrente. Inizialmente imposta
         * la coordinata y a 20, rappresentante la posizione iniziale del testo. Viene
         * poi calcolato l'indice di
         * partenza, che indica da quale transazione deve iniziare la stampa,
         * utilizzando l'indice della pagina corrente
         * moltiplicato per 40. Viene poi utilizzato un ciclo for per stampare le
         * informazioni delle transazioni, a
         * partire dall'indice di partenza fino ad un massimo di 40 transazioni o fino
         * alla fine delle transazioni
         * presenti nel bilancio. Per ogni transazione vengono stampate la data,
         * l'importo e la descrizione in posizioni
         * specifiche della pagina. La coordinata y viene incrementata di 20 ad ogni
         * iterazione per evitare
         * sovrapposizioni del testo.
         */
        int y = 20;
        int startIndex = pageIndex * 40;
        for (int i = startIndex; i < startIndex + 40 && i < myBudget.getTransazioni().size(); i++) {
            VoceBilancio voce = myBudget.getTransazioni().get(i);
            g2d.drawString(voce.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 20, y);
            if (voce instanceof Uscita) {
                g2d.drawString("-" + String.format("%.2f", voce.getAmount()) + "€", 120, y);
            } else {
                g2d.drawString(String.format("%.2f", voce.getAmount()) + "€", 120, y);
            }
            g2d.drawString(voce.getDescrizione(), 220, y);
            y += 20;
        }
        return PAGE_EXISTS;
    }
}
