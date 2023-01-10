package com.noflyfre.bankmore.logic;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.noflyfre.bankmore.gui.AppFrame;

/**
 * Classe principale.
 */
public final class BankmoreApp {
    private BankmoreApp() {
    }

    /**
     * Metodo main. Viene settato il lookandfeel, creato un nuovo bilancio ed inizializzato il frame.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatArcIJTheme");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        final Bilancio myBudget = new Bilancio();
        AppFrame frame = new AppFrame(myBudget);
    }
}
