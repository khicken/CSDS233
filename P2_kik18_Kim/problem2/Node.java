/**
 * AUTHOR: Kaleb Kim
 * LAST DATE MODIFIED: 10/9/23
 * DESCRIPTION: Node of a binary search tree
 */

package problem2;

// this is literally the same node as a doubly linkedlist but it's left/right instead of next/prev
public class Node {
    public Node left, right;
    public int key;
    Node(int key) {
        this.key = key;
        this.left = null;
        this.right = null;
    }
}