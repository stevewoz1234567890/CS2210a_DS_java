import java.util.List;
import java.util.ArrayList;

public class BSTDictionary implements BSTDictionaryADT {

    private final BinarySearchTree tree;
    private BSTNode root;

    // Initializes BSTDictionary
    public BSTDictionary() {
        this.tree = new BinarySearchTree();
    }

    // Searches for and returns the specified key's record, or null if not found
    @Override
    public Record get(Key k) {
        BSTNode node = tree.get(tree.getRoot(), k);
        return (node != null) ? node.getRecord() : null;
    }

    // Inserts a new Record into the Dictionary
    @Override
    public void put(Record d) throws DictionaryException {
        // Check if the key already exists in the tree
        if (tree.get(tree.getRoot(), d.getKey()) != null) {
            throw new DictionaryException("A record with the given key already exists.");
        }
        // If the root is null, set the new record as the root
        if (tree.getRoot() == null) {
            tree.setRoot(new BSTNode(d));
        } else {
            // Insert the record in the appropriate position in the tree
            tree.insert(tree.getRoot(), d);
        }
    }

    // Removes the specified Record by key
    @Override
    public void remove(Key k) throws DictionaryException {
        // Check if the key exists in the tree
        if (tree.get(tree.getRoot(), k) == null) {
            throw new DictionaryException("Record not found in the dictionary.");
        }
        // Perform the removal
        tree.remove(tree.getRoot(), k);
    }

    // Finds and returns the successor of the specified key
    @Override
    public Record successor(Key k) {
        BSTNode node = tree.successor(tree.getRoot(), k);
        return (node != null) ? node.getRecord() : null;
    }

    // Finds and returns the predecessor of the specified key
    @Override
    public Record predecessor(Key k) {
        BSTNode node = tree.predecessor(tree.getRoot(), k);
        return (node != null) ? node.getRecord() : null;
    }

    // Implement the list() method to list all records in the dictionary
    public List<Record> list() {
        List<Record> records = new ArrayList<>();
        inOrderTraversal(root, records);
        return records;
    }

    // Helper method to perform in-order traversal
    private void inOrderTraversal(BSTNode node, List<Record> records) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.getLeftChild(), records); // Traverse left
        records.add(node.getRecord()); // Add current node's record
        inOrderTraversal(node.getRightChild(), records); // Traverse right
    }

    // Implement the smallest() method to find the smallest record in the tree
    public Record smallest() {
        if (root == null)
            return null;
        BSTNode node = smallestNode(root);
        return node.getRecord();
    }

    // Helper method to find the node with the smallest key
    private BSTNode smallestNode(BSTNode node) {
        while (node.getLeftChild() != null) {
            node = node.getLeftChild();
        }
        return node;
    }

    // Implement the largest() method to find the largest record in the tree
    public Record largest() {
        if (root == null)
            return null;
        BSTNode node = largestNode(root);
        return node.getRecord();
    }

    // Helper method to find the node with the largest key
    private BSTNode largestNode(BSTNode node) {
        while (node.getRightChild() != null) {
            node = node.getRightChild();
        }
        return node;
    }
}
