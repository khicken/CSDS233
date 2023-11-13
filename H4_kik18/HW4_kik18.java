/***
 * AUTHOR: Kaleb Kim
 * DATE CREATED: 11/13/23
 * DATE MODIFIED: 11/13/23
 * 
 * DESCRIPTION: Programming part of HW4
 * 
 * USAGE:
 * - create a hashtable
 * - insertLinearProbe inserts an array of integer keys using linear probing
 * - insertDoubleHash inserts an array of integer keys using double hashing with function hash(key) = 7 - key % 7
 * - tester written in main method of HW4_kik18 class,
 * 
 */

import java.util.Arrays;

class HashTable {
        private int size; // size of table
        private int[] table; // keys are input, table is...the table
        /**
         * Hash Table with a specified size.
         * @param size int
         */
        HashTable(int size) {
            this.size = size;
            this.table = new int[size];
        }

        /***
         * Inserts an integer array of keys into a hash table, handling collisions via linear probing.
         * @param keys Array of keys to be inserted
         * @return true if all keys were inserted successfully
         */
        public boolean insertLinearProbe(int[] keys) {
            for(int a: keys) {
                if(table[a%size] == 0) { // when there's an empty slot
                    table[a%size] = a;
                    System.out.println(Arrays.toString(table));
                } else if(!linearProbe(a)) { // when there isn't an empty slot
                    // if this is run, ruh roh
                    System.out.println("Error: Unable to insert via linear probing!");
                    return false;
                }
            }
            System.out.println("Inserting via linear probing success!");
            return true;
        }

        // helper method that's only called when there's a collision
        private boolean linearProbe(int key) {
            int i = 1; // counter
            while(i <= size) {
                if(table[(key+i)%size] == 0) {
                    table[(key+i)%size] = key;
                    System.out.println(Arrays.toString(table));
                    return true;
                }
                i++;
            }
            return false;
        }

        /***
         * Inserts an integer array of keys into a hash table, handling collisions via double hashing.
         * Uses the function hash(key) = 7 - key % 7
         * @param keys
         * @return true if all keys were inserted successfully
         */
        public boolean insertDoubleHash(int[] keys) {
            for(int a: keys) {
                if(table[a%size] == 0) { // when there's an empty slot
                    table[a%size] = a;
                    System.out.println(Arrays.toString(table));
                } else if(!doubleHash(a)) { // called when there's a collision
                    // if this is run, ruh roh
                    System.out.println("Error: Unable to insert via double hashing!");
                    return false;
                }
            }
            System.out.println("Inserting via double hashing success!");
            return true;
        }

        // helper method that's only called when there's a collision
        private boolean doubleHash(int key) {
            int i = 1; // counter
            int curr = 0;
            while(i < size) {
                curr = (key + i*(7 - key%7))%size;
                if(table[curr] == 0) {
                    table[curr] = key;
                    System.out.println(Arrays.toString(table));
                    return true;
                }
                i++;
            }
            return false;
        }

        /***
         * Prints the table, what else?
         */
        public void printTable() {
            System.out.println(Arrays.toString(table));
        }
    }

class HW4_kik18 {
    public static void main(String[] args) {
        System.out.println("PART ONE");
        HashTable partOne = new HashTable(10);
        int[] partOneKeys = {14, 17, 18, 3, 8, 1, 18, 11, 13, 20};
        partOne.insertLinearProbe(partOneKeys);

        System.out.println("\nPART TWO A");
        HashTable partTwoA = new HashTable(10);
        int[] partTwoAKeys = {2, 12, 22, 32, 42, 52, 62, 72, 82, 92};
        partTwoA.insertLinearProbe(partTwoAKeys);

        System.out.println("\nPART TWO B");
        HashTable partTwoB = new HashTable(20);
        int[] partTwoBKeys = {2, 12, 22, 32, 42, 52, 62, 72, 82, 92, 14, 17, 18, 3, 8, 1, 18, 11, 13, 20};
        partTwoB.insertDoubleHash(partTwoBKeys);
    }
}