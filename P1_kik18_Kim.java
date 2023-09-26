/**
 * CSDS233 Assignment 1 - Programming section
 * Author: Kaleb Kim
 * Date last modified: 9/25/23
 */

import java.util.Arrays;
import java.util.Scanner;

public class P1_kik18_Kim {
    /**
     * Time Complexity: O(n)
     * Reasoning: O(n) for the filtering line
     *            Single linear pass through half the string.
     *            Methods charAt() and length() for a string both have time complexities of O(1)
     *
     * Input has been processed to ignore capitlization and truncate of spaces.
     */
    static boolean palindromeIterative(String input) {
        String inp = input.replace(" ", "").toLowerCase(); // filtering

        int end = inp.length()/2;
        for(int i = 0; i < end; i++) {
            if(inp.charAt(i) != inp.charAt(inp.length()-i-1))
                return false;
        }
        return true;
    }

    /**
     * Time Complexity: O(n^2) when filtering input, O(n) when not filtering input
     * Reasoning: Recursive approach that breaks the string in a linear sequence, making up to n/2 calls which becomes O(n)
     *            However, the filtering line is O(n) and is called n/2 times, which brings the time complexity up
     * 
     * Input has been processed to ignore capitlization and truncate of spaces.
     */
    static boolean palindromeRecursive(String input) {
        String inp = input.replace(" ", "").toLowerCase(); // brings the time complexity to O(n^2)...

        if(inp.length() <= 1) return true;
        if(inp.charAt(0) == input.charAt(inp.length()-1))
            return palindromeRecursive(inp.substring(1, inp.length()-1));
        return false;
    }

    /**
     * Time complexity: O(nlogn)
     * Reasoning: Calls Arrays.sort() which has time complexity of O(nlogn) due to a dual-pivot quick sort algorithm which is the worst runtime in the method
     */
    static boolean anagramChecker(String x, String y) {
        if(x.length() != y.length()) // bozo case
            return false;
        char[] a = x.toCharArray();
        char[] b = y.toCharArray();
        Arrays.sort(a);
        Arrays.sort(b);
        return Arrays.equals(a, b);
    }

    /**
     * Time complexity: O(n)
     * Reasoning: .substring() has a time complexity of O(n) and there are no loops that depend on n
     */
    static String addSubstring(String input, String substring, int index) {
        if(index == 0)
            return substring + input;
        else if(index < input.length())
            return input.substring(0, index) + substring + input.substring(index, input.length());
        else if(index == input.length())
            return input + substring;
        return "ERROR: INVALID INDEX GIVEN"; // just in case input isn't checked before
    }

    /**
     * Get the length of a string in a goofy manner, O(n) due to a linear pass
     * @param input The string
    */
    static int getLength(String input) {
        // I know this can be easily done with recursion or toCharArray or input.length(),
        //      but I wanted to do something silly.
        int i = 0;
        try {
            while(true) {
                if(input.charAt(i) > 0)
                    i++;
            }
        } catch(Exception e) { // once the loop exceeds the string size
            return i;
        }
    }

    /**
     * Count the number of times a substring exists in a string, O(n^2) due to .substring() calls nested in a linear pass
     * @param input Big string
     * @param substring Smaller string
     * @return Occurances
     */
    static int occuranceCounter(String input, String substring) {
        if(input.length() == 0 || substring.length() == 0) return 0; // filter bozo cases

        int counter = 0;
        for(int i = 0; i < input.length()-substring.length()+1; i++) { // using a sliding window
            counter = (input.substring(i, i+substring.length()).equals(substring)) ? counter + 1 : counter;
        }
        return counter;
    }

    /**
     * Reverse a sentence's words, O(n) using a sliding window/linear pass
     * @param input A sentence to reverse
     * @return A reversed sentence
     */
    static String sentenceReversal(String input) {
        if(input.length() <= 1) return input;

        // using a sliding window with rightMark being the rightmost index of the word
        String out = "";
        int rightMark = input.length()-1;
        char endCharacter = 'x'; // will be appended if no longer 'x'
        for(int i = input.length()-1; i >= 0; i--) {
            if(input.charAt(i) == ' ') {
                out += input.substring(i+1, rightMark+1) + " ";
                rightMark = i-1;
            }

            // once a punctuation character is hit
            if((int)input.charAt(i) >= 33 && (int)input.charAt(i) <= 46) {
                endCharacter = input.charAt(i);
                if(i == rightMark) // this will be true if we're at the end of a word
                    rightMark--; // remove the punctuation of the word if a bozo puts multiple exclamation mark
            }
        }

        out += input.substring(0, rightMark + 1); // add first word to the end
        out = (endCharacter != 'x') ? out + endCharacter : out; // add endCharacter if one was found
        return out;
    }

    // Bundle an interation of the app into a method to invoke continuous recursive calls
    static void actualApp() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" 1. Palindrome Check\n 2. Anagram Check\n 3. Add Substring\n 4. Get Length\n 5. Count Occurances\n 6. Reverse Sentence\n 7. Quit");
        System.out.print("Choose an option: ");
        int option = 7;
        try { // validate input
            option = scanner.nextInt();
            if(option < 1 || option > 7) {
                System.out.println("Please choose a valid option between 1-7.\n");
                actualApp(); // haha back to the start
            }
            scanner.nextLine();
        } catch(Exception e) {
            System.out.println("\nInvalid input! Please input a number between 1-7. Here are the options again:");
            actualApp(); // haha back to the start again
        }
        
        if(option == 7) { // exit the program before the scanner statement
            System.out.println("Adios");
            scanner.close();
            System.exit(0);
        }

        System.out.print("\nEnter string: "); // this is an input for all cases 1-6, not 7
        String paramOne = scanner.nextLine();

        switch(option) {
            case 1: // palindrome iterative
                System.out.println("\nPalindrome status: " + String.valueOf(palindromeIterative(paramOne)));
                break;
            case 2: // anagram checker
                System.out.print("Enter a string to compare anagrams: ");
                String anagramCompare = scanner.nextLine();
                System.out.println("\nAnagram status: " + String.valueOf(anagramChecker(paramOne, anagramCompare)));
                break;
            case 3: // substring insertion with input validation
                System.out.print("Substring to be inserted: ");
                String substringInsert = scanner.nextLine();
                System.out.print("Substring to be inserted: ");
                int indexInsert = 0;
                while(true) { // validate input to be an integer
                    try {
                        indexInsert = scanner.nextInt();
                        if(indexInsert > paramOne.length() && indexInsert >= 0) {
                            System.out.print("Please input an integer that's a valid index within the string range: ");
                            continue;
                        }
                        scanner.nextLine();
                        break;
                    } catch(Exception e) {
                        System.out.println("\nInvalid input! Asking for an integer here.");
                    }
                }
                System.out.println("\nNew string: " + addSubstring(paramOne, substringInsert, indexInsert));
                break;
            case 4: // get length of string
                System.out.println("\nLength of string: " + String.valueOf(getLength(paramOne)));
                break;
            case 5: // count occurances of substring
                System.out.print("Enter a substring to count occurances: ");
                String substringOccurancesInput = scanner.nextLine();
                System.out.printf("\nOccurances of substring '%s' in string '%s': %d\n", substringOccurancesInput, paramOne, occuranceCounter(paramOne, substringOccurancesInput));
                break;
            case 6: // reverse sentence
                System.out.println(sentenceReversal(paramOne));
                break;
        }

        System.out.println("\nNeed to do something else?");
        actualApp();
    }
    
    // Wow! Two lines of code in a method?????!!!
    public static void main(String[] args) {
        System.out.println("Welcome to the App!");
        actualApp(); // this loops itself!
    }

    // resources used: my smooth, giant brain
}