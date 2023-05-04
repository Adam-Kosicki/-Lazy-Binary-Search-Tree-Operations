public class LazyBinarySearchTree {

    public TreeNode root; // root node of the tree
    private int currSize; // current size of the tree
    private String toStringString = ""; // string used to store the tree's preorder traversal

    public LazyBinarySearchTree(){ // constructor
        root = null;
        currSize = 0;
    }

    public LazyBinarySearchTree(TreeNode node){ // constructor with a given root node
        root = node;
        currSize = 1;
    }

    public int height(){ // get the height of the tree
        return getHeight(root);
    }

    private int getHeight(TreeNode node) { // helper method to get the height of a node
        if (node == null){
            return -1;
        }
        return 1 + Math.max(getHeight(node.getLeftChild()), getHeight(node.getRightChild()));
    }

    public int size(){ // get the current size of the tree
        return currSize;
    }

    public boolean insert(int key){ // insert a new node with the given key
        if (key < 1 || key > 99){ // check if the key is within the acceptable range
            throw new IllegalArgumentException();
        }
        if (root == null){ // if the tree is empty, create a new root node with the given key
            TreeNode temp = new TreeNode(key);
            root = temp;
            currSize++;
            return true;
        }
        int oldSize = currSize;
        performInsert(root, key); // otherwise, perform the insert recursively starting from the root
        if (oldSize < currSize)
            return true;
        else
            return false;
    }

    private void performInsert(TreeNode node, int key) { // helper method to perform the insert recursively
        if (key < node.getKey() && node.getLeftChild() != null){
            performInsert(node.getLeftChild(),key);
        }
        else if (key > node.getKey() && node.getRightChild() != null){
            performInsert(node.getRightChild(),key);
        }
        else if (node.getKey() == key && node.isDeleted()){ // if a node with the same key exists but is deleted, undelete it
            node.setDeleted(false);
            currSize++;
            return;
        }
        else if (node.getKey() == key && !node.isDeleted()){ // if a node with the same key exists and is not deleted, do nothing
            return;
        }
        else if (key < node.getKey() && node.getLeftChild() == null){ // if there is no left child, create a new node as the left child
            TreeNode newNode = new TreeNode(key);
            node.setLeftChild(newNode);
            currSize++;
            return;
        }
        else if (key > node.getKey() && node.getRightChild() == null){ // if there is no right child, create a new node as the right child
            TreeNode newNode = new TreeNode(key);
            node.setRightChild(newNode);
            currSize++;
            return;
        }
    }

    public boolean delete(int key){ // delete the node with the given key
        if (key < 1 || key > 99){ // check if the key is within the acceptable range
            throw new IllegalArgumentException();
        }
        return performDelete(root,key); // perform the delete recursively starting from the root
    }

    // Perform deletion of a node with a given key
    private boolean performDelete(TreeNode node, int key) {
        if (node == null) // If node is null, it means the key was not found
            return false;
        if(node.getKey() == key){ // If the node's key matches the given key
            node.setDeleted(true); // Mark the node as deleted
            return true;
        }
        if(key < node.getKey()){ // If the given key is less than the node's key, move left
            return performDelete(node.getLeftChild(),key);
        }
        if(key > node.getKey()){ // If the given key is greater than the node's key, move right
            return performDelete(node.getRightChild(),key);
        }
        return false; // If none of the above conditions are true, the key was not found
    }

    // Find the minimum key in the tree
    public int findMin(){
        int[] min = new int[]{-1}; // Create an array to store the minimum value found so far
        inOrderMin(root,min); // Call a helper function to traverse the tree and find the minimum
        return min[0]; // Return the minimum value found
    }

    // Helper function to traverse the tree in-order and find the minimum value
    private void inOrderMin(TreeNode node, int[] min){
        if (node != null){
            inOrderMin(node.getLeftChild(),min);
            if (!node.isDeleted()){ // If the node is not marked as deleted
                if (min[0] == -1) {
                    min[0] = node.getKey(); // Update the minimum value found so far
                }
                return;
            }
            inOrderMin(node.getRightChild(),min);
        }
    }

    // Find the maximum key in the tree
    public int findMax(){
        int[] max = new int[]{-1}; // Create an array to store the maximum value found so far
        forMaximum(root,max); // Call a helper function to traverse the tree and find the maximum
        return max[0]; // Return the maximum value found
    }

    // Helper function to traverse the tree in reverse-order and find the maximum value
    private void forMaximum(TreeNode node, int[] max){
        if (node!= null){
            forMaximum(node.getRightChild(), max);
            if (!node.isDeleted()){ // If the node is not marked as deleted
                if (max[0] == -1){
                    max[0] = node.getKey(); // Update the maximum value found so far
                }
                return;
            }
            forMaximum(node.getLeftChild(), max);
        }
    }

    // Check if the tree contains a node with the given key
    public boolean contains(int key){
        if (key < 1 || key > 99){ // Check if the key is within the expected range
            throw new IllegalArgumentException(); // Throw an exception if the key is out of range
        }
        return doesContain(root, key); // Call a helper function to traverse the tree and check if the key exists
    }

    private boolean doesContain(TreeNode node, int key){
        if (node == null){
            return false;
        }
        if(node.getKey() == key && !node.isDeleted()){
            return true;
        }
        if(key < node.getKey()){
            return doesContain(node.getLeftChild(),key);
        }
        if(key > node.getKey()){
            return doesContain(node.getRightChild(),key);
        }
        return false;
    }

        public String toString() {						//for preorder traversal
        preorderString(root);
        String returned = toStringString;
        toStringString = "";
        return returned;
    }

    private void preorderString(TreeNode node) {
        if (node == null){
            return;
        }
        toStringString+= " ";
        if (node.isDeleted()) {
            toStringString += "*";
        }
        toStringString += node.getKey() + "";

        preorderString(node.getLeftChild());
        preorderString(node.getRightChild());
    }
}

class TreeNode {					//node class
    // A class representing a node in a binary search tree
    private int key;                   // Value of the node
    private boolean deleted;           // Flag indicating if the node is deleted
    private TreeNode leftChild;        // Reference to the left child node
    private TreeNode rightChild;       // Reference to the right child node

    public TreeNode(int value){
        // Constructor for the node
        leftChild = null;
        rightChild = null;
        key = value;
        deleted = false;
    }

    // Getter and setter methods for the instance variables
    public int getKey() {
        return key;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }
}

