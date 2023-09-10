package ru.otus.atm.atmbox.wallets;

import ru.otus.atm.atmbox.wallets.banknotes.Banknote;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BasicWallet implements Wallet {

    private Long debt = 0L;
    private LinkedList<Banknote> banknotes = new LinkedList<Banknote>();

    public BasicWallet(Long debt) {
        this.debt = debt;
    }
    public BasicWallet(Banknote ... toPut) {
        this.banknotes.addAll(Arrays.asList(toPut));
    }

    @Override
    public void putBanknotesDebtOff(Banknote ... toPut) {
        for (Banknote banknote : toPut) {
            if (debt > 0) {
                banknotes.push(banknote);
                debt = debt - banknote.getNominal();
            }
        }
    }

    @Override
    public void putBanknotes(Banknote ... toPut) {
        banknotes.addAll(Arrays.asList(toPut));
    }

    @Override
    public List<Banknote> getBanknotes() {
        List<Banknote> toReceive = banknotes.stream().toList();
        banknotes.clear();
        return toReceive;
    }

    @Override
    public Long getBalance() {
        return banknotes.stream()
                .map(Banknote::getNominal)
                .reduce(Long::sum)
                .orElse(0L);
    }

    @Override
    public Long getDebt() {
        return this.debt;
    }

    @Override
    public void setDebt(Long amount) {
        this.debt = amount;
    }
}
