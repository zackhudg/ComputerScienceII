/**
 *determines whether given number is a prime number or not
 * author: Zack Hudgins
 */
package lab01.student;
import java.util.Scanner;

public class PrimalityTest {

    /**
     * determines whether given int unput is prime or not
     * @param number    number being tested for primality
     * @return boolean  true = prime, false = not prime
     */
    public static boolean isPrime(int number){
        if(number <= 1){
            return false;
        } else if(number == 2){ ;
            return true;
        } else {
            if (number % 2 == 0){
                return false;
            } else {
                for (int i = 3; i < number; i = i + 2){
                    if (number % i == 0){
                        return false;
                    }
                }
                return true;
            }
        }
    }

    /**
     * main function, prompts user for input to test for primality
     * @param args  reads from command line, not needed in this case
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while(flag) {
            System.out.println("Enter a number (0 to quit) : ");
            int num = scanner.nextInt();
            if (num < 1){
                flag = false;
                System.out.println("Goodbye!");
            } else {
                if (isPrime(num)) {
                    System.out.println(num + " is prime!");
                } else {
                    System.out.println(num + " is not prime.");
                }
            }
        }
    scanner.close();
    }

}
