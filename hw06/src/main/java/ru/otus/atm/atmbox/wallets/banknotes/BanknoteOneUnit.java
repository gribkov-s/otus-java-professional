package ru.otus.atm.atmbox.wallets.banknotes;

public class BanknoteOneUnit implements Banknote {
    @Override
    public Long getNominal() { return 1L; }
}
