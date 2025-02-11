package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class BankTest {

    int minBalance = 0;
    int maxBalance = 3_000;
    int initialBalance = 1_000;
    int creditLimit = 1_000;
    int rate = 5;

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 999, 1000})
    public void transferFromSavingToCreditSuccessTest(int amount) {

        SavingAccount savingAccount = new SavingAccount(initialBalance, minBalance, maxBalance, rate);
        CreditAccount creditAccount = new CreditAccount(initialBalance, creditLimit, rate);
        Bank bank = new Bank();

        boolean expectedResult = true;
        boolean actualResult = bank.transfer(savingAccount, creditAccount, amount);

        Assertions.assertEquals(expectedResult, actualResult);

        int expectedCreditBalance = initialBalance + amount;
        int expectedSavingBalance = initialBalance - amount;
        int actualSavingBalance = savingAccount.getBalance();
        int actualCreditBalance = creditAccount.getBalance();

        Assertions.assertEquals(expectedSavingBalance, actualSavingBalance);
        Assertions.assertEquals(expectedCreditBalance, actualCreditBalance);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1001})
    public void transferFromSavingToCreditFailTest(int amount) {

        SavingAccount savingAccount = new SavingAccount(initialBalance, minBalance, maxBalance, rate);
        CreditAccount creditAccount = new CreditAccount(initialBalance, creditLimit, rate);
        Bank bank = new Bank();

        boolean expectedResult = false;
        boolean actualResult = bank.transfer(savingAccount, creditAccount, amount);

        Assertions.assertEquals(expectedResult, actualResult);

        int expectedBalance = initialBalance;
        int actualSavingBalance = savingAccount.getBalance();
        int actualCreditBalance = creditAccount.getBalance();

        Assertions.assertEquals(expectedBalance, actualSavingBalance);
        Assertions.assertEquals(expectedBalance, actualCreditBalance);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 1999, 2000})
    public void transferFromCreditToSavingSuccessTest(int amount) {

        SavingAccount savingAccount = new SavingAccount(initialBalance, minBalance, maxBalance, rate);
        CreditAccount creditAccount = new CreditAccount(initialBalance, creditLimit, rate);
        Bank bank = new Bank();

        boolean expectedResult = true;
        boolean actualResult = bank.transfer(creditAccount, savingAccount, amount);

        Assertions.assertEquals(expectedResult, actualResult);

        int expectedCreditBalance = initialBalance - amount;
        int expectedSavingBalance = initialBalance + amount;
        int actualSavingBalance = savingAccount.getBalance();
        int actualCreditBalance = creditAccount.getBalance();

        Assertions.assertEquals(expectedSavingBalance, actualSavingBalance);
        Assertions.assertEquals(expectedCreditBalance, actualCreditBalance);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "3500, 999",
            "2999, 1500"
    })
    public void transferFromCreditToSavingFailTest(int maxBalance, int creditLimit) {

        int amount = 2000;

        SavingAccount savingAccount = new SavingAccount(initialBalance, minBalance, maxBalance, rate);
        CreditAccount creditAccount = new CreditAccount(initialBalance, creditLimit, rate);
        Bank bank = new Bank();

        boolean expectedResult = false;
        boolean actualResult = bank.transfer(creditAccount, savingAccount, amount);

        Assertions.assertEquals(expectedResult, actualResult);

        int expectedBalance = initialBalance;
        int actualSavingBalance = savingAccount.getBalance();
        int actualCreditBalance = creditAccount.getBalance();

        Assertions.assertEquals(expectedBalance, actualSavingBalance);
        Assertions.assertEquals(expectedBalance, actualCreditBalance);
    }
}