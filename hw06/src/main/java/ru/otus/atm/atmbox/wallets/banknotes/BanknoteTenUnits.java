package ru.otus.atm.atmbox.wallets.banknotes;

public class BanknoteTenUnits implements Banknote {
    @Override
    public Long getNominal() {
        return 10L;
    }
}
