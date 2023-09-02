package ru.otus.atm.atmbox;


import ru.otus.atm.atmbox.wallets.Wallet;

public interface AtmBox {
    Long getBalance();
    Long getNominal();
    Wallet getBanknotes(Wallet wallet);
    Wallet putBanknotes(Wallet wallet);
}
