package ru.otus.atm;

import ru.otus.atm.atmbox.AtmBox;
import ru.otus.atm.atmbox.wallets.Wallet;
import ru.otus.atm.atmbox.wallets.banknotes.Banknote;

import java.util.List;

public interface Atm {
    public Long getBalance();
    public Wallet getBanknotes(Wallet wallet);
    public Wallet putBanknotes(Wallet wallet);
    public List<Long> showBoxes();
}
