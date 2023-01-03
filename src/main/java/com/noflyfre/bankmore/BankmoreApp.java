package com.noflyfre.bankmore;

/**
 * Classe principale.
 */
public final class BankmoreApp {
    private BankmoreApp() {
    }

    public static void main(String[] args) {
        Bilancio myBudget = new Bilancio();
        AppFrame frame = new AppFrame(myBudget);
    }
}
