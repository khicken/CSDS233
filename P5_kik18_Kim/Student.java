/**
 * Student.java
 * 
 * CSDS 233 Assignment 5
 * 
 * AUTHOR: Kaleb Kim
 * LAST MODIFIED: 11/28/23
*/

package P5_kik18_Kim;

class Student {
    private String name;
    private int balance;

    // simple constructor
    public Student(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    /**
     * Fetches the student's balance.
     * @return The student's balance (duh)
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Updates the student's balance to a new amount.
     * @param newAmount New balance amount
     */
    public void updateBalance(int newAmount) {
        balance = newAmount;
    }

    /**
     * @return Student's name (duh)
     */
    public String getName() {
        return name;
    }

    @Override
    /**
     * @return Student's name
     */
    public String toString() {
        return name;
    }
}