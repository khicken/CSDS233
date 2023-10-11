/**
 * AUTHOR: Kaleb Kim
 * LAST DATE MODIFIED: 10/9/23
 * DESCRIPTION: Binary search tree
 */

package problem2;

public class BinarySearchTree {
    private Node head;
    private int size;

    BinarySearchTree() {
        this.head = null;
    }

    BinarySearchTree(int headKey) {
        this.head = new Node(headKey);
    }

    /**
     * Recurively insert a node. Time complexity of O(logn).
     * @param key Value to insert into the tree
     */
    public void insert(int key) {
        this.insert(head, key);
    }

    /**
     * Recurively insert a node. Time complexity of O(logn).
     * @param root Head of the tree/subtree
     * @param key Value to insert into the tree
     */
    public void insert(Node root, int key) {
        insertRec(root, key);
    }

    private Node insertRec(Node root, int key) {
        if(root == null) {
            this.size++;
            if(this.head == null)
                head = root;
            return new Node(key);
        }

        if(key < root.key) {
            root.left = insertRec(root.left, key);
        } else if(key > root.key) {
            root.right = insertRec(root.right, key);
        }

        return root; // new root node when it's finished
    }

    /**
     * Recursively list/backtrack for the preorder print of the tree.
     */
    public void preorderRec() {
        preorderRec(head);
        System.out.println("");
    }

    /**
     * Recursively list/backtrack for the preorder print of the tree/subtree.
     * @param root Head of the tree/subtree
     */
    private void preorderRec(Node root) {
        if(root == null)
            return;

        // preorder is node-left-right, statements are listed in priority
        System.out.print(root.key + " ");
        preorderRec(root.left); // traverse left
        preorderRec(root.right); // after all left elements are hit, traverse right
    }

    /**
     * Recursively sum the keys of a tree
     * @return an integer that's the sum what else?
     */
    public int sum(Node root) {
        if(root == null) return 0;
        return root.key + sum(root.left) + sum(root.right);
    }

    /**
     * Finds kth largest element in tree, using a size heuristic
     * @param k
     * @return
     */
    Node kthBiggest(int k) {
        return kthBiggest(head, k);
    }

    /**
     * Finds kth largest element in tree, using a size heuristic
     * @param root use the head of the tree please
     * @param k
     * @return
     */
    Node kthBiggest(Node root, int k) {
        if(k > size) return null; // bozo case

        Node curr = root;
        while(curr != null && k > 0) { // once k reaches 0 we've hit our element going down
            // if we're at the rightmost element which is the largest value
            if(curr.right == null) {
                k--;
                if(k == 0)
                    return curr;
                curr = curr.left; // keep going if we're not at k
            } else {
                Node succ = curr.right; // inorder successor
                // traverse down the left subtree
                while(succ.left != null && succ.left != curr)
                    succ = succ.left;
                
                if(succ.left == null) {
                    // nothing left in successor, so move up
                    succ.left = curr;
                    // move current to its right to run loop again
                    curr = curr.right;
                } else { // largest element in left subtree of successor...
                    succ.left = null; // nothing else to look at
                    k--;
                    if(k == 0)
                        return curr;
                    curr = curr.left; // keep going if we're not at k
                }
            }
        }
        return null;
    }

    public Node getHead() {
        return head;
    }
}