/**
 * AUTHOR: Kaleb Kim
 * LAST DATE MODIFIED: 10/9/23
 * DESCRIPTION: LinkedList minimal implementation,
 *              SCROLL DOWN A BIT FOR THE SUCCESSORSWAP METHOD + QUESTIONS
 */

package problem1;

public class LinkedList {
    Node head;

    LinkedList() {
        head = null;
    }

    LinkedList(int key) {
        head = new Node(key);
    }

    /**
     * Swaps the node's successor with the node
     * @param a key (value) of the node 
     * @return true if swap has been successful
     */
    public boolean successorSwap(int a) {
        Node current = this.head; // for iteration
        while(current != null) {
            if(current.key == a) {
                // if there is a successor, swap
                if(current.next != null) {
                    int temp = current.next.key; // store successor
                    current.next.key = current.key; // assign current to successor
                    current.key = temp; // assign successor to current
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    /** PROGRAMMING EXERCISE QUESTIONS FOR PART A)
     * 2) The major concept of the algorithm is to swap key values of the two nodes after
     *    finding the node of interest by value.
     * 
     * 3) For linked list [1, 3, 5, 6],
     *    successorSwap(3) would result in [1, 5, 3, 6].
     * 
     * 4) The runtime is O(n) since there's a single loop that linearly passes through the array.
     *    The swap itself is O(1), constant time, since it's a couple statements.
     */

    // method to insert a new node
    public boolean insert(int key) {
        if (this.head == null) {
            this.head = new Node(key);
        } else {
            Node current = this.head;
            while(this.head.next != null) {
                current = current.next;
            }

            // insert the new node at last node
            current.next = new Node(key);
        }
        return true;
    }
}