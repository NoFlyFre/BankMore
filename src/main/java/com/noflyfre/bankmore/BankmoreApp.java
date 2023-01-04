package com.noflyfre.bankmore;

import javax.swing.UIManager;

/**
 * Classe principale.
 */
public final class BankmoreApp {
    private BankmoreApp() {
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatArcIJTheme");
        } catch (Exception ex) {

        }
        final Bilancio myBudget = new Bilancio();
        AppFrame frame = new AppFrame(myBudget);
    }
}
