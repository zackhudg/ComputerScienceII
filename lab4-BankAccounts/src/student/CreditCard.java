package student;

import bank.AccountType;
import edu.rit.cs.Currency;

/**
 * Credit Card Account, subclass of BankAccount, has negative balance and limits usage
 *      based off credit limit. Still has interest, just increases the (negative) balance
 * @author: Zack Hudgins
 */

public class CreditCard extends BankAccount{
    /**
     * creditLimit: max amount that can be spent until no more can be put on credit card
     * monthlyInterestRate: rate that balance accumulates (negative) interest
     */
    private Currency creditLimit;
    private double monthlyInterestRate;

    /**
     * constructor for credit card
     * @param creditLimit   max limit that balance can hold before it must be payed off
     * @param interestRate  rate balance accumulates (neg) interest
     * @param owner         name of owner
     */
    public CreditCard(Currency creditLimit, double interestRate, String owner) {
        super(creditLimit, owner, AccountType.CREDIT);
        this.monthlyInterestRate = interestRate/12;
        this.creditLimit = creditLimit;
    }

    /**
     * returns how much is owed on the credit card
     * @return  amount owed
     */
    @Override
    public Currency getCurrentBalance(){
        return super.getCurrentBalance().subtract(this.creditLimit);
    }

    /**
     * does calculations at the end of each month cycle that determines
     *       how much interest earned. Updates interest rates
     */
    public void endOfMonth(){
        Currency intEarned = getCurrentBalance().multiply(
                monthlyInterestRate);
        super.setInterestAccrued(intEarned);
        super.addInterest(intEarned);
    }

    /**
     * returns string of account type
     * @return "CC"
     */
    public String getAccountType(){
        return "CC";
    }
}
