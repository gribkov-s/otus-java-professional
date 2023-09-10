package ru.otus.atm.atmbox.wallets.banknotes;

public class BanknoteFiftyUnits implements Banknote {
    @Override
    public Long getNominal() {
        return 50L;
    }
}
