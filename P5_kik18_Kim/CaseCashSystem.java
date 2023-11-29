/**
 * CashCashSystem.java
 * 
 * CSDS 233 Assignment 5
 * 
 * AUTHOR: Kaleb Kim
 * LAST MODIFIED: 11/28/23
 * 
 * This file contains the tester in the main method at the bottom of the file.
*/

package P5_kik18_Kim;

import java.util.List;
import java.util.Iterator;
import java.util.Arrays;
import java.util.ArrayList;

public class CaseCashSystem {
    private List<Student> students; // list that holds all the students in the system

    /**
     * Initialize a new CaseCashSystem with an empty database of students.
     */
    public CaseCashSystem() {
        students = new ArrayList<Student>();
    }

    /**
     * Runs the simulation provided by a list of String commands. Every call clears the previous list of students.
     * @param commands List of input commands
     * @return List of outputs from input commands
     */
    public List<String> runSimulation(List<String> commands) {
        if(!students.isEmpty())
            students.clear(); // clear students

        Iterator<String> i = commands.iterator(); // use iterator to get through all commands
        String[] curr; // line command split into parameters
        List<String> outputs = new ArrayList<>();
        while(i.hasNext()) {
            curr = i.next().replace(" ", "").split(",");
            switch(curr[0]) {
                case "INIT": // format is "INIT, name, initialBalance"
                    outputs.add(String.valueOf(init(curr[1], Integer.parseInt(curr[2]))));
                break;
                case "GET": // format is "GET, name"
                    outputs.add(String.valueOf(getBalance(curr[1])));
                break;
                case "TRANSFER": // format is "TRANSFER, studentA, studentB, amount"
                    outputs.add(String.valueOf(transfer(studentSearch(curr[1]), studentSearch(curr[2]), Integer.parseInt(curr[3]))));
                break;
                case "WITHDRAWAL": // format is "WITHDRAWAL, studentA, amount"
                    outputs.add(String.valueOf(withdrawal(studentSearch(curr[1]), Integer.parseInt(curr[2]))));
                break;
                case "DEPOSIT": // format is "DEPOSIT, studentA, amount"
                    outputs.add(String.valueOf(deposit(studentSearch(curr[1]), Integer.parseInt(curr[2]))));
                break;
                case "SORT": // format is ”SORT, name or balance”
                    if(curr[1].equals("name")) { // name
                        outputs.add(sortName().toString());
                    } else { // balance
                        outputs.add(sortBalance().toString());
                    }
                break;
            }
        }

        return outputs;
    }

    /**
     * Initializes a student with a name and an initial account balance. Corresponds to "INIT, name, initialBalance".
     * @param name Student name
     * @param initialBalance Starting balance of the student, cannot be negative
     * @return true if the student doesn't already exist, false if a student already exists
     */
    public boolean init(String name, int initialBalance) {
        Student student = studentSearch(name); // using helper method to find student

        try {
            if(student == null) { // called if a student doesn't exist
                students.add(new Student(name, initialBalance));
                return true;
            }
        } catch(Exception e) { // for whatever reason there's a stupid error, shouldn't happen though
            System.out.println("======= ERROR! =======");
            System.out.println(e);
        }
        return false; // if the student wasn't added...
    }

    /**
     * Return the balance of a given student. Corresponds to "GET, name".
     * @param name Name of student
     * @return Student balance, -1 if student not found
     */
    public int getBalance(String name) {
        Student student = studentSearch(name); // using helper method to find student

        if(student != null) // student was found!
            return student.getBalance();
        return -1;
    }

    /**
     * Deposit money from a student account. Corresponds to "DEPOSIT, studentA, amount".
     * @param student Student that will (hopefully) gain money
     * @param amount Amount of money that will enter the student's account
     * @return true if deposit is successful, false if deposit fails (negative input)
     */
    public boolean deposit(Student student, int amount) {
        Student output = studentSearch(student.getName()); // using helper method to find student

        if(output != null) { // student found!
            output.updateBalance(output.getBalance() + amount);
            return true;
        }
        System.out.println("Please enter a valid student!");
        return false; // woohoo
    }

    /**
     * Transfers the amount from Student A's account to Student B's account.
     * The balance of the given accounts are not changed at all if the amount given is invalid.
     * Corresponds to "TRANSFER, studentA, studentB, amount".
     * @param studentA Student A
     * @param studentB Student B
     * @param amount Amount to transfer, must be positive
     * @return true if transfer is successful, false if transfer results in a negative balance
     */
    public boolean transfer(Student studentA, Student studentB, int amount) {
        if(amount < 0 || students.get(students.indexOf(studentA)).getBalance() - amount < 0) // checks if amount is invalid or not
            return false; // invalid
        if(!(students.contains(studentA) && students.contains(studentB))) // ensuring both students exist in the database
            return false; // also invalid
        students.get(students.indexOf(studentA)).updateBalance(studentA.getBalance() - amount); // update studentA in db
        students.get(students.indexOf(studentB)).updateBalance(studentB.getBalance() + amount); // update studentB in db
        return true; // update successful! (right)
    }

    /**
     * Utilizes merge sort to sort student names in alphabetical order. Corresponds to "SORT, name".
     * @return Sorted list of student names in alphabetical order
     */
    public List<String> sortName() {
        List<String> unsortedNames = new ArrayList<>(); // need to convert to a list of strings
        for(Student a: students)
            unsortedNames.add(a.getName());
        
        List<String> sorted = mergeSort(unsortedNames); // call mergeSort, it's recursive so we can't have it in here
        return sorted;
    }

    /**
     * Helper method that recursively splits and merges a list for sorting.
     * @param arr List to be sorted
     * @return Sorted list
     */
    private List<String> mergeSort(List<String> arr) {
        if(arr.size() <= 1) // if it's a single element or empty array, no need to split further
            return arr;
        List<String> left = mergeSort(arr.subList(0, arr.size()/2)); // recursively split left subarray
        List<String> right = mergeSort(arr.subList(arr.size()/2, arr.size())); // recursively split right subarray
        return merge(left, right); // merge!!!
    }

    /**
     * Helper method that merges two sublists together in order.
     * @param left Left sublist
     * @param right Right sublist
     * @return A sorted list.
     */
    private List<String> merge(List<String> left, List<String> right) {
        int leftSize = left.size(), rightSize = right.size(); // reduces time complexity by a little bit since size() isn't constant
        int l = 0, r = 0;
        List<String> output = new ArrayList<String>();
        while(l + r < leftSize + rightSize) { // pass through both arrays by checking where the index is
            if(l == leftSize) { // means everything from the left subarray is added already, yet right isn't done (if right was done the loop wouldn't be running dawg)
                output.add(right.get(r));
                r++;
            } else if(r == rightSize) { // means everything from the right subarray is added already, yet left isn't done
                output.add(left.get(l));
                l++;
            } else if(left.get(l).compareTo(right.get(r)) < 0) { // current element in left array is "less" than that in the right array
                output.add(left.get(l));
                l++;
            } else {
                output.add(right.get(r));
                r++;
            }
        }

        return output;
    }

    /**
     * Quick sorts student names in the order of smallest to largest balance in their account.
     * Corresponds to "SORT, balance".
     * @return Sorted list of student names based on how poor to rich they are.
     */
    public List<String> sortBalance() {
        quickSort(students, 0, students.size()-1); // call quickSort, it's also recursive so we can't have it in here
        List<String> sortedNames = new ArrayList<>();
        for(Student a: students)
            sortedNames.add(a.getName());
        return sortedNames;
    }

    /**
     * Helper method that directly sorts the list given via recursion.
     * @param arr List to be sorted
     * @param l Starting index of list (0)
     * @param r Ending index of list (list's size - 1)
     */
    private void quickSort(List<Student> arr, int l, int r) {
        if(l < r) { // keep going as long as the left and right indexes don't meet
            int p = partition(arr, l, r); // move the pivot to the right spot and get its new index
            quickSort(arr, l, p - 1); // sort the section lower than pivot
            quickSort(arr, p + 1, r); // sort the section higher than pivot
        }
    }

    /**
     * Helper method that partitions a list about a pivot (element at end)
     * @param arr List that needs to be partitioned
     * @param l Left (ending) index of subarray to be partitioned
     * @param r Right (ending) index of subarray to be partitioned
     * @return New pivot index in between the partitioned arrays
     */
    private int partition(List<Student> arr, int l, int r) {
        Student pivot = arr.get(r); // set pivot to be the last element of the list
        int sm = l - 1; // smallest element index
        for(int i = l; i < r; i++) {
            if(arr.get(i).getBalance() < pivot.getBalance() && sm != i) { // if less than the pivot, swap with last visited element smaller than pivot
                sm++; // knowing that the next element is larger than the pivot cause we never moved l past it
                Student temp = arr.get(i);
                arr.set(i, arr.get(sm));
                arr.set(sm, temp);
            } // if current element is greater, i moves on but l stays in place
        }
        // finally swap the pivot to be in the middle of the low/high subarrays
        sm++; // now the index is on the first element larger than the pivot, swapping this
        Student temp = arr.get(sm);
        arr.set(sm, arr.get(arr.size()-1));
        arr.set(arr.size()-1, temp);

        return sm; // return pivot to enable recursion in the quickSort() method
    }
    
    /**
     * Removes money from a student account. If the withdrawal is invalid, the balance of the student remains unchanged.
     * Corresponds to "WITHDRAWAL, studentA, amount".
     * @param student Student that'll lose money under the right condition.
     * @param amount Amount to be yoinked from a student.
     * @return true if the removal is successful, false if there'll be a negative balance
     */
    public boolean withdrawal(Student student, int amount) {
         // checking if student doesn't exist or amount exceeds the student's current balance
        if(studentSearch(student.getName()) == null || student.getBalance() - amount < 0)
            return false; // wow that student doesn't exist
        students.get(students.indexOf(student)).updateBalance(student.getBalance() - amount); // update student in db
        return true;
    }

    /**
     * Helper method that looks up a student.
     * @param name Name of student that needs to be found
     * @return Student object if found, null if not
     */
    private Student studentSearch(String name) {
        Iterator<Student> i = students.iterator();
        Student curr;
        while(i.hasNext()) {
            curr = i.next();
            if(curr.getName().equals(name))
                return curr;
        }
        return null;
    }

    // main method, serves as tester
    public static void main(String[] args) {
        String[] inputsArray = {"INIT, Tammy, 200", "INIT, Kim, 300", "INIT, Quyen, 400", "SORT, name", "SORT, balance", "TRANSFER, Kim, Tammy, 100", "SORT, name", "SORT, balance"};
        List<String> inputs = Arrays.asList(inputsArray); // converting an array to a list

        CaseCashSystem caseCashSystem = new CaseCashSystem();
        List<String> outputs = caseCashSystem.runSimulation(inputs);

        System.out.println(outputs);
    }
}
