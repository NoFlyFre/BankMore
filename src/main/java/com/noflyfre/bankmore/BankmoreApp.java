package com.noflyfre.bankmore;

/**
 * Classe principale.
 */
public final class BankmoreApp {
    private BankmoreApp() {
    }
    //cali ti amo
    public static void main(String[] args) {
        Bilancio myBudget = new Bilancio();
        AppFrame frame = new AppFrame(myBudget);
    }
}
