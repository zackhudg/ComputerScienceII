/**
 * Uses the Sieve of Eratosthenes to form an array which determines which numbers are prime and not prime
 * up to a certain upper bound
 * author: Zack Hudgins
 */

package lab01.student;
import java.util.Scanner;

public class SieveOfEratosthenes {

    /**
     * creates the sieve, array list with 1 indicating not prime, and 0 indicating prime. index represents the number
     * @param upperBound    highest number / index in array list that gets tested for primality
     * @return values   arraylist with determined primes and not primes
     */
    public static int[] makeSieve(int upperBound){
        int[] values = new int[upperBound];
        values[0] = 1;
        values[1] = 1;
        for(int i = 2; i<= Math.sqrt((double)upperBound); i++) {
            if (values[i] == 0){
                for(int j = 0; (int)Math.pow(i, 2) + i*j < upperBound; j++){
                    values[(int)Math.pow(i, 2) + i*j] = 1;
                }
            }
        }
        return values;
    }

    /**
     * main calls and tests other functions in the class, prompts user and checks if their input is prime using Seive
     * @param args  takes any input from command line, none needed in this case
     */
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an upper bound : ");
        int upperBound = scanner.nextInt();
        int[] sieve = makeSieve(upperBound);
        boolean flag = true;
        while(flag) {
            System.out.println("Enter a positive number (0 to quit) : ");
            int num = scanner.nextInt();
            if (num < 1){
                flag = false;
                System.out.println("Goodbye!");
            } else {
                if (sieve[num] == 0) {
                    System.out.println(num + " is prime!");
                } else {
                    System.out.println(num + " is not prime.");
                }
            }
        }
    scanner.close();
    }
}
