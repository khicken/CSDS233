/**
 * AUTHOR: Kaleb Kim
 * LAST DATE MODIFIED: 10/9/23
 * DESCRIPTION: Node of a linkedlist
 */

package problem1;

// this is literally the same node as BST but it's next/prev instead of left/right
public class Node {
    public int key;
    public Node next, prev;

    Node(int key) {
        this.key = key;
        next = null;
        prev = null;
    }
}