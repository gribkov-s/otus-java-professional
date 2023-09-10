package ru.otus.atm.atmbox.wallets;

import ru.otus.atm.atmbox.wallets.banknotes.Banknote;
import java.util.List;

public interface Wallet {
    void putBanknotesDebtOff(Banknote ... toPut);
    void putBanknotes(Banknote ... toPut);
    List<Banknote> getBanknotes();
    Long getBalance();
    Long getDebt();
    void setDebt(Long amount);
}
