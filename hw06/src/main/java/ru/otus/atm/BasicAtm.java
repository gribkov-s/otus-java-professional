package ru.otus.atm;

import ru.otus.atm.atmbox.AtmBox;
import ru.otus.atm.atmbox.wallets.Wallet;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class BasicAtm implements Atm {

    private TreeSet<AtmBox> atmBoxes = new TreeSet<AtmBox>();

    public BasicAtm(AtmBox ... boxes) {
        this.atmBoxes.addAll(Arrays.asList(boxes));
    }

    @Override
    public Long getBalance() {
        return atmBoxes.stream()
                .map(AtmBox::getBalance)
                .reduce(Long::sum)
                .orElse(0L);
    }

    @Override
    public Wallet getBanknotes(Wallet wallet) {
        for (AtmBox box : atmBoxes) {
            box.getBanknotes(wallet);
        }
        return wallet;
    }

    @Override
    public Wallet putBanknotes(Wallet wallet) {
        for (AtmBox box : atmBoxes) {
            box.putBanknotes(wallet);
        }
        return wallet;
    }

    @Override
    public List<Long> showBoxes() {
        return atmBoxes.stream()
                .map(AtmBox::getNominal)
                .toList();
    }
}
