package student;

import bank.AccountType;
import edu.rit.cs.Currency;

/**
 * CD Account, earns interest on amount that is over a certain minimum, subclass of BankAccount
 * @author: Zack Hudgins
 */

public class CDAccount extends BankAccount {
    /**
     * MINIMUM_BALANCE: minimum balance in CD in order to get interest on excess
     * MONTHLY_INTEREST_RATE: monthly interest rate that excess gets
     */
    public static final Currency MINIMUM_BALANCE = new Currency(1000, 0);
    public static final double MONTHLY_INTEREST_RATE = 5.0E-4;

    /**
     * consstuctor CDAccount
     * @param newMoney  starting amount in account
     * @param owner     name of account owner
     */
    public CDAccount(Currency newMoney, String owner){
        super(newMoney, owner, AccountType.DEBIT);
    }

    /**
     * does calculations at the end of each month cycle that determines whether interest is earned
     *      and how much. Updates interest rates
     */
    public void endOfMonth(){
        if (this.getCurrentBalance().compareTo(MINIMUM_BALANCE) == 1) {
            Currency intEarned = (super.getCurrentBalance().subtract(MINIMUM_BALANCE)).multiply(
                    MONTHLY_INTEREST_RATE);
            super.setInterestAccrued(intEarned);
            super.addInterest(intEarned);
        }
    }

    /**
     * returns string of account type
     * @return "CD"
     */
    public String getAccountType(){
        return "CD";
    }

    /**
     * stringifies account
     * @return string version of CD account
     */
    @Override
    public String toString(){
        return super.toString() + "CD";
    }
}
