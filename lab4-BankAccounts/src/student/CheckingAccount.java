package student;

import bank.AccountType;
import edu.rit.cs.Currency;

/**
 * Checking Account, subclass of BankAccount, earns interest if bonus is applied to the account (CI)
 *      or doesn't earn interest if no bonus (CN)
 * @author: Zack Hudgins
 */

public class CheckingAccount extends BankAccount {

    /**
     * bonus: whether or not checking account earns interest
     * BONUS_MONTHLY_RATE: monthly interest rate if applicable
     * PREMIUM_CHECKING_MINIMUM_BALANCE: minimum balance to earn interest
     * chkAcctType: CI or CN depending on whether or not the acct earns interest
     */
    private boolean bonus;
    public static double BONUS_MONTHLY_RATE = 8.333333333333333E-5;
    public static final Currency PREMIUM_CHECKING_MINIMUM_BALANCE = new Currency(500, 0);
    public final String chkAcctType;


    /**
     * constructor for checking account
     * @param newMoney  starting money in new account
     * @param owner     name of owner
     * @param bonus     interest earned (true) or not (false)
     */
    public CheckingAccount(Currency newMoney, String owner, boolean bonus){
        super(newMoney, owner, AccountType.DEBIT);
        this.bonus = bonus;
        if (this.bonus){
            this.chkAcctType = "CI";
        }else{
            this.chkAcctType = "CN";
        }
    }

    /**
     * does calculations at the end of each month cycle that determines whether interest is earned
     *      and how much. Updates interest rates
     */
    public void endOfMonth(){
        if (this.getCurrentBalance().compareTo(PREMIUM_CHECKING_MINIMUM_BALANCE) ==1 && this.bonus == true) {
            Currency intEarned = getCurrentBalance().multiply(
                    BONUS_MONTHLY_RATE);
            super.setInterestAccrued(intEarned);
            super.addInterest(intEarned);

        }
    }

    /**
     * returns account type in string
     * @return CI or CN depending if earns interest or not
     */
    public String getAccountType(){
        return this.chkAcctType;
    }

    /**
     * stringifies account
     * @return string version of checking account
     */
    @Override
    public String toString(){
        return super.toString() + " Checking";
    }
}
