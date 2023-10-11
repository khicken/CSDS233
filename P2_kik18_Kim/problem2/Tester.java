package problem2;

public class Tester {
    public static void main(String[] main) {
        BinarySearchTree BST = new BinarySearchTree(10);
        // these call the internal method insert(Node root, int key) just encapsulated!
        BST.insert(6);
        BST.insert(2);
        BST.insert(16);
        BST.insert(34);
        BST.insert(18);
        BST.insert(6);
        
        // this calls an internal method preorderRec(Node root), just encapsulated!
        BST.preorderRec();

        // this calls an internal method kthBiggest(Node root, int k), just encapsulated!
        System.out.println(BST.kthBiggest(2).key); // returns 18, which is the 2nd largest element in BST
    }
}
