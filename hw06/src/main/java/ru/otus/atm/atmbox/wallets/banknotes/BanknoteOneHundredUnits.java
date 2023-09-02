package ru.otus.atm.atmbox.wallets.banknotes;

public class BanknoteOneHundredUnits implements Banknote {
    @Override
    public Long getNominal() {
        return 100L;
    }
}
