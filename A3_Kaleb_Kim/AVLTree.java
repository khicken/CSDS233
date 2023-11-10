/*******************************************************
 * Assignment 3
 * Name: Kim_Kaleb
 * UID: kik18
 ********************************************************/

public class AVLTree<T extends Comparable<T>> {
    private AVLTreeNode<T> mRoot; // root node
    // nodes of AVL Tree(internal)

    class AVLTreeNode<T extends Comparable<T>> {
        T key; // key
        int height; // height
        AVLTreeNode<T> left; // left children
        AVLTreeNode<T> right; // right children

        /**
         * Node class for AVL Tree
         */
        public AVLTreeNode(T key, AVLTreeNode<T> left, AVLTreeNode<T> right) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.height = 0;
        }
    }

    // AVLTree Constructor
    public AVLTree() {
        mRoot = null;
    }

    /*
     * Gets the height of the tree
     */
    private int height(AVLTreeNode<T> tree) {
        if (tree != null)
            return tree.height;

        return 0;
    }

    public int height() {
        return height(mRoot);
    }

    /**
     * Find the max value among the given numbers.
     * 
     * @param a First number
     * @param b Second number
     * @return Maximum value
     */
    private int max(int a, int b) {
        return a > b ? a : b;
    }

    /*
     * Question: a-1
     * Preorder traversal "AVL tree", print the result
     */
    private void preOrder(AVLTreeNode<T> tree) {
        if (tree != null) {
            System.out.print(tree.key + " ");
            preOrder(tree.left);
            preOrder(tree.right);
        }
    }

    public void preOrder() {
        preOrder(mRoot);
    }

    /*
     * Question: a-2
     * In-order traversal "AVL tree", print the result
     */
    private void inOrder(AVLTreeNode<T> tree) {
        if (tree != null) {
            inOrder(tree.left);
            System.out.print(tree.key + " ");
            inOrder(tree.right);
        }
    }

    public void inOrder() {
        inOrder(mRoot);
    }

    /*
     * Question: a-3
     * Post-order traversal "AVL tree", print the result
     */
    private void postOrder(AVLTreeNode<T> tree) {
        if (tree != null) {
            postOrder(tree.left);
            postOrder(tree.right);
            System.out.print(tree.key + " ");
        }
    }

    public void postOrder() {
        postOrder(mRoot);
    }

    /*
     * (Recursion) Search the node whose key-value is key in "AVL tree x"
     */
    private AVLTreeNode<T> search(AVLTreeNode<T> x, T key) {
        if (x == null)
            return x;

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return search(x.left, key);
        else if (cmp > 0)
            return search(x.right, key);
        else
            return x;
    }

    public AVLTreeNode<T> search(T key) {
        return search(mRoot, key);
    }

    /*
     * (Non-Recursion) Search the node whose key-value is key in "AVL tree x"
     */
    private AVLTreeNode<T> iterativeSearch(AVLTreeNode<T> x, T key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);

            if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
            else
                return x;
        }

        return x;
    }

    public AVLTreeNode<T> iterativeSearch(T key) {
        return iterativeSearch(mRoot, key);
    }

    /*
     * Question: a-4
     * Find min node：return the smallest node of the AVL tree when "tree" as the
     * root
     */
    private AVLTreeNode<T> minimum(AVLTreeNode<T> tree) {
        if (tree.left != null) // the leftmost node in an AVL tree is smallest
            return minimum(tree.left);
        return tree;
    }

    public T minimum() {
        AVLTreeNode<T> p = minimum(mRoot);
        if (p != null)
            return p.key;

        return null;
    }

    /*
     * Question: a-5
     * Finds max node: return the largest node of the AVL tree with "tree" as the
     * root
     */
    private AVLTreeNode<T> maximum(AVLTreeNode<T> tree) {
        if (tree.right != null) // the rightmost node in an AVL tree is largest
            return maximum(tree.right);
        return tree;
    }

    public T maximum() {
        AVLTreeNode<T> p = maximum(mRoot);
        if (p != null)
            return p.key;

        return null;
    }

    /*
     * LL：(a left rotation)。
     *
     * return: the root node after rotated
     */
    private AVLTreeNode<T> leftLeftRotation(AVLTreeNode<T> k2) {
        AVLTreeNode<T> k1;

        k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;

        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(height(k1.left), k2.height) + 1;

        return k1;
    }

    /*
     * RR: (right roration)。
     *
     * return: the root node after rotated
     */
    private AVLTreeNode<T> rightRightRotation(AVLTreeNode<T> k1) {
        AVLTreeNode<T> k2;

        k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;

        k1.height = max(height(k1.left), height(k1.right)) + 1;
        k2.height = max(height(k2.right), k1.height) + 1;

        return k2;
    }

    /*
     * LR: (left double roration)。
     *
     * return: the root node after rotated
     */
    private AVLTreeNode<T> leftRightRotation(AVLTreeNode<T> k3) {
        k3.left = rightRightRotation(k3.left);

        return leftLeftRotation(k3);
    }

    /*
     * RL: (right double roration)。
     *
     * return: the root node after rotated
     */
    private AVLTreeNode<T> rightLeftRotation(AVLTreeNode<T> k1) {
        k1.right = leftLeftRotation(k1.right);

        return rightRightRotation(k1);
    }

    /*
     * Question a-6
     * insert an element into the tree, return the root node
     *
     * @param tree: the root node of AVL tree
     * 
     * @param key: the insert key-value
     * 
     * @return tree: root node
     */
    private AVLTreeNode<T> insert(AVLTreeNode<T> tree, T key) {
        if (tree == null) {
            try {
                tree = new AVLTreeNode<T>(key, null, null);
            } catch (Exception e) {
                System.out.println("ERROR: create avltree node failed!");
                return null;
            }
        } else {
            int cmp = key.compareTo(tree.key); // comparison

            if (cmp < 0) { // key is less
                tree.left = insert(tree.left, key); // 'rebuild' left subtree aka update with the insertion
            } else if (cmp > 0) { // key is greater
                tree.right = insert(tree.right, key); // 'rebuild' right subtree aka update with the insertion
            } else { // key is same, nothing changes
                System.out.println("Insert Fail: Cannot insert the same element!");
                return tree;
            }
        }
 
        tree.height = max(height(tree.left), height(tree.right)) + 1; // update tree height

        // balance the tree after insertion
        int bf = height(tree.right) - height(tree.left);
        if (bf < -1) { // tipped to the left
            if (key.compareTo(tree.left.key) < 0) // left's left is longer than its right (outer)
                return leftLeftRotation(tree);
            else // left's right is longer than its left (inner)
                return leftRightRotation(tree);
        } else if (bf > 1) { // tipped to the right
            if (key.compareTo(tree.right.key) > 0) // right's right is longer than its left (outer)
                return rightRightRotation(tree);
            else // right's left is longer than its right (inner)
                return rightLeftRotation(tree);
        }
        return tree;
    }

    public void insert(T key) {
        mRoot = insert(mRoot, key);
    }

    /**
     * Question: a-7
     * Delete the node (z), then return the root node
     *
     * @param tree: the root node of AVL tree
     * 
     * @param z: the node to be deleted
     * 
     * @return tree: root node
     */
    private AVLTreeNode<T> remove(AVLTreeNode<T> tree, AVLTreeNode<T> z) {
        // if the root is empty or there are no nodes to delete, return "null"
        if (tree == null || z == null)
            return null;

        int cmp = z.key.compareTo(tree.key);
        if (cmp < 0) { // The node to be deleted is in the "left subtree of tree"
            tree.left = remove(tree.left, z);
        } else if (cmp > 0) { // The node to be deleted is in the "right subtree of tree"
            tree.right = remove(tree.right, z);
        } else { // we found the node to be deleted!
            // If both the left and right children of "tree" are not empty
            if ((tree.left != null) && (tree.right != null)) { // if we remove the node, we'll have two separate trees
                // if (height(tree.left) > height(tree.right)) { // left is taller, so we shorten it
                    AVLTreeNode<T> inorderSuccessor = minimum(tree.right);
                    tree.key = inorderSuccessor.key; // replace root's key with inorder successor (NOT REPLACING ENTIRE NODE)
                    remove(tree.right, inorderSuccessor);
            } else { // only one branch or none at all
                tree = (tree.left != null) ? tree.left : tree.right;
                if(tree == null)
                    return tree;
            }
        }

        tree.height = max(height(tree.left), height(tree.right)) + 1;
        int bf = height(tree.right) - height(tree.left);
        if (bf < -1) { // tipped to the left
            if (z.key.compareTo(tree.left.key) < 0) // left's left is longer than its right (outer)
                return leftLeftRotation(tree);
            else // left's right is longer than its left (inner)
                return leftRightRotation(tree);
        } else if (bf > 1) { // tipped to the right
            if (z.key.compareTo(tree.right.key) > 0) // right's right is longer than its left (outer)
                return rightRightRotation(tree);
            else // right's left is longer than its right (inner)
                return rightLeftRotation(tree);
        }
        return tree;
    }

    public void remove(T key) {
        AVLTreeNode<T> z;

        if ((z = search(mRoot, key)) != null)
            mRoot = remove(mRoot, z);
    }

    /*
     * Destroy Tree
     */
    private void destroy(AVLTreeNode<T> tree) {
        if (tree == null)
            return;

        if (tree.left != null)
            destroy(tree.left);
        if (tree.right != null)
            destroy(tree.right);

        tree = null;
    }

    public void destroy() {
        destroy(mRoot);
    }

    /*
     * Print Tree
     *
     * @param key: key-value
     * 
     * @param direction:0 : means the node this the root node.
     * 
     * @param direction:-1 : -1，means the node is the left child of its parent;
     * 
     * @param direction:1 : 1，means the node is the right child of its parent;
     */
    private void print(AVLTreeNode<T> tree, T key, int direction) {
        if (tree != null) {
            if (direction == 0)
                System.out.printf("%2d is root\n", tree.key, key);
            else
                System.out.printf("%2d is %2d's %6s child\n", tree.key, key, direction == 1 ? "right" : "left");

            print(tree.left, tree.key, -1);
            print(tree.right, tree.key, 1);
        }
    }

    public void print() {
        if (mRoot != null)
            print(mRoot, mRoot.key, 0);
    }
}
