/**
 * GoodHashFunc takes a string and creates a hash code from it
 *
 * author: Zack Hudgins
 */
package lab01.student;
import java.util.Scanner;

public class GoodHashFunc {

    /**takes String input, returns int from hashing input
     * @param       input   original String to be hashed
     * @return      result  int, hashed string
     */
    public static int computeHash(String input){
        int len = input.length();
        int[] hashes = new int[len];
        for(int i=0; i<len; i++) {
            int hash = input.charAt(i) * (int) Math.pow(31, len - i - 1);
            hashes[i] = hash;
        }
        int result = 0;
        int j = 0;
        while(j<len) {
            result += hashes[j];
            j++;
        }
        return result;
    }

    /**
     * main function, independently tests the computeHash function
     * @param args  takes user input from command line, not needed in this case
     */

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a string : ");
        String input = scanner.next();
        System.out.println(computeHash(input));

        scanner.close();
    }
}
