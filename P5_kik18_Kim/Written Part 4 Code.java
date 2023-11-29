/**
 * Write pseudocode for the following: Given a string S, sort the characters in the string by
frequency. For example: “ghjklghjlgjj” → “khhllgggjjjj”. If a frequency is the same, sort
in alphabetical order. Characters in the string will all be lowercase a-z. Your pseudocode
should look like code, but does not necessarily have to compile.
 */

package Testing;

import java.util.Arrays;

class temp {
    public static String freqSort(String str) {
        /** STRATEGY:
         * create an integer array of size 26 filled with zeroes, where indexes correspond with character positions (a is at 0, z is at 25)
         * for loop iterates over the string to increment elements corresponding with the character
         * implement selection sort to select the minimum values > 0
         *  however, instead of building another integer array build a string by translating the index and element to a character sequence
         *  after selecting each element, set the value to 0
         *  repeat until there's no element > 0, will be run at most 26 times!
         * there you have it.
         */
        int[] freq = new int[26]; // frequencies of each char
        for(int i = 0; i < str.length(); i++) // O(n)!
            freq[(int)str.charAt(i)-97]++;
        
        String output = "";
        for(int i = 0; i < 26; i++) { // selection sort outer loop
            int min = 0, minIndex = 0;
            for(int j = 0; j < 26; j++) { // looking for element > 0
                if(freq[j] > min) {
                    min = freq[j];
                    minIndex = j;
                }
            }
            for(int a = 0; a < min; a++) // append chars
                output = (char)(97+minIndex) + output;
            freq[minIndex] = 0;
        }

        return output;
    }

    public static void main(String[] args) {
        String s = "ghjklghjlgjj";
        System.out.printf("Testing %s\n", s);
        System.out.println(freqSort(s));
    }
}