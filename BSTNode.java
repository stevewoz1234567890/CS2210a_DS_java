  public class BSTNode {
    private Record record;
    private BSTNode parent;
    private BSTNode leftChild;
    private BSTNode rightChild;

    // Initializes new BSTNode with the specifications
    public BSTNode(Record item) {
        this.record = item;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }

    // Gets the Record stored in this node
    public Record getRecord() {
        return record;
    }

    // Sets this node
    public void setRecord(Record d) {
        this.record = d;
    }

    // Gets left child of this node
    public BSTNode getLeftChild() {
        return leftChild;
    }

    // Sets the left child of this node
    public void setLeftChild(BSTNode u) {
        this.leftChild = u;
    }

    // Gets right child of this node
    public BSTNode getRightChild() {
        return rightChild;
    }

    // Sets the right child of this node
    public void setRightChild(BSTNode u) {
        this.rightChild = u;
    }

   // Gets the parent of this node
    public BSTNode getParent() {
        return parent;
    }

    // Sets the parent of this node
    public void setParent(BSTNode u) {
        this.parent = u;
    }

   // Checks if this node is a leaf
    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }
}
