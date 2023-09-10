package ru.otus.atm.atmbox;

import ru.otus.atm.atmbox.wallets.banknotes.*;
import ru.otus.atm.atmbox.wallets.Wallet;

import java.util.LinkedList;
import java.util.List;

public class BasicAtmBox implements AtmBox, Comparable<BasicAtmBox> {
    private final Long nominal;
    private LinkedList<Banknote> banknotes = new LinkedList<Banknote>();

    public BasicAtmBox(Long nominal) {
        this.nominal = nominal;
    }

    @Override
    public Long getBalance() {
        return banknotes.stream()
                .map(Banknote::getNominal)
                .reduce(Long::sum)
                .orElse(0L);
    }

    @Override
    public Long getNominal() {
        return this.nominal;
    }

    @Override
    public Wallet getBanknotes(Wallet wallet) {
        Long banknotesInBox = (long) banknotes.size();

        if (banknotesInBox > 0) {
            Long debt = wallet.getDebt();
            Long banknotesRequired = debt / nominal;
            Long debtToPayOff = banknotesRequired > banknotesInBox ? banknotesInBox : banknotesRequired;
            Long debtRest = debt - debtToPayOff * nominal;

            for (long i = 1; i <= debtToPayOff; i ++) {
                Banknote banknote = banknotes.pop();
                wallet.putBanknotesDebtOff(banknote);
            }
            wallet.setDebt(debtRest);
        }
        return wallet;
    }

    @Override
    public Wallet putBanknotes(Wallet wallet) {

        List<Banknote> banknotesToPut = wallet.getBanknotes();

        for (Banknote banknote : banknotesToPut) {
            Long banknoteNominal = banknote.getNominal();
            if (banknoteNominal.equals(nominal)) {
                banknotes.push(banknote);
            } else {
                wallet.putBanknotes(banknote);
            }
        }
        return wallet;
    }

    @Override
    public int hashCode() {
        return (- nominal.intValue() ^ (- nominal.intValue() >>> 32));
    }

    @Override
    public int compareTo(BasicAtmBox box) {
        return (- nominal.intValue() - box.nominal.intValue());
    }
}
