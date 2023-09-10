package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.atm.BasicAtm;
import ru.otus.atm.atmbox.BasicAtmBox;
import ru.otus.atm.atmbox.wallets.BasicWallet;
import ru.otus.atm.atmbox.wallets.banknotes.*;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BasicAtmTest {

    private BasicAtm createAtm() {
        BasicAtmBox[] boxes = {
                new BasicAtmBox(1L),
                new BasicAtmBox(5L),
                new BasicAtmBox(10L),
                new BasicAtmBox(50L),
                new BasicAtmBox(100L)
        };
        return new BasicAtm(boxes);
    }

    private BasicWallet createWallet() {
        Banknote[] banknotes = {
                new BanknoteOneUnit(),
                new BanknoteOneUnit(),
                new BanknoteOneUnit(),
                new BanknoteOneUnit(),
                new BanknoteOneUnit(),
                new BanknoteFiveUnits(),
                new BanknoteFiveUnits(),
                new BanknoteFiveUnits(),
                new BanknoteFiveUnits(),
                new BanknoteTenUnits(),
                new BanknoteTenUnits(),
                new BanknoteTenUnits(),
                new BanknoteFiftyUnits(),
                new BanknoteFiftyUnits(),
                new BanknoteOneHundredUnits()
        };
        return new BasicWallet(banknotes);
    }

    @Test
    @DisplayName("create ATM")
    void createAtmTest(){
        //when
        BasicAtm atm = createAtm();
        //then
        List<Long> boxesNominals = Arrays.asList(100L, 50L, 10L, 5L, 1L);
        assertThat(atm.showBoxes()).isEqualTo(boxesNominals);
    }

    @Test
    @DisplayName("create wallet")
    void createWalletTest(){
        //when
        BasicWallet wallet = createWallet();
        wallet.setDebt(111L);
        //then
        assertThat(wallet.getBalance()).isEqualTo(255);
        assertThat(wallet.getDebt()).isEqualTo(111);
    }

    @Test
    @DisplayName("put banknotes to ATM")
    void putBanknotesToAtmTest() {
        //given
        BasicAtm atm = createAtm();
        BasicWallet wallet = createWallet();
        //when
        atm.putBanknotes(wallet);
        //then
        assertThat(atm.getBalance()).isEqualTo(255);
        assertThat(wallet.getBalance()).isEqualTo(0);
    }

    @Test
    @DisplayName("get banknotes from ATM")
    void getBanknotesFromAtmTest() {
        //given
        BasicAtm atm = createAtm();
        BasicWallet wallet = createWallet();
        atm.putBanknotes(wallet);
        //when
        wallet.setDebt(111L);
        atm.getBanknotes(wallet);
        //then
        assertThat(atm.getBalance()).isEqualTo(144);
        assertThat(wallet.getBalance()).isEqualTo(111);
        assertThat(wallet.getDebt()).isEqualTo(0);
    }

    @Test
    @DisplayName("get banknotes from ATM with debt rest")
    void getBanknotesFromAtmWithDebtTest() {
        //given
        BasicAtm atm = createAtm();
        BasicWallet wallet = createWallet();
        atm.putBanknotes(wallet);
        //when
        wallet.setDebt(500L);
        atm.getBanknotes(wallet);
        //then
        assertThat(atm.getBalance()).isEqualTo(0);
        assertThat(wallet.getBalance()).isEqualTo(255);
        assertThat(wallet.getDebt()).isEqualTo(245);
    }
}
