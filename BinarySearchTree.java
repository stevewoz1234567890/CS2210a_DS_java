public class BinarySearchTree {
    private BSTNode root;

    // Initializes binary search tree
    public BinarySearchTree() {
        this.root = null;
    }

    // Returns the root node of the BST
    public BSTNode getRoot() {
        return root;
    }

    // Sets the root node of the BST
    public void setRoot(BSTNode root) {
        this.root = root;
    }

    // Searches for a node with the specified key
    public BSTNode get(BSTNode r, Key k) {
        if (r == null) {
            return null;
        }

        int comparison = k.compareTo(r.getRecord().getKey());

        if (comparison == 0) {
            return r;
        } else if (comparison < 0) {
            return get(r.getLeftChild(), k);
        } else {
            return get(r.getRightChild(), k); 
        }
    }

    // Inserts a new node into the tree
    public void insert(BSTNode r, Record d) throws DictionaryException {
        Key k = d.getKey();

        if (r == null) {
            root = new BSTNode(d);
        } else {
            int comparison = k.compareTo(r.getRecord().getKey());

            if (comparison == 0) {
                throw new DictionaryException("Key already exists in the tree.");
            } else if (comparison < 0) {
                if (r.getLeftChild() == null) {
                    BSTNode newNode = new BSTNode(d);
                    r.setLeftChild(newNode);
                    newNode.setParent(r);
                } else {
                    insert(r.getLeftChild(), d);
                }
            } else {
                if (r.getRightChild() == null) {
                    BSTNode newNode = new BSTNode(d);
                    r.setRightChild(newNode);
                    newNode.setParent(r);
                } else {
                    insert(r.getRightChild(), d);
                }
            }
        }
    }

    // Removes the node with the specified key
    public void remove(BSTNode r, Key k) throws DictionaryException {
        BSTNode nodeToDelete = get(r, k);
        
        if (nodeToDelete == null) {
            throw new DictionaryException("Key not found in the tree.");
        }

        if (nodeToDelete.isLeaf()) {
            replaceNode(nodeToDelete, null);
        } else if (nodeToDelete.getLeftChild() != null && nodeToDelete.getRightChild() != null) {
            BSTNode successor = smallest(nodeToDelete.getRightChild());
            nodeToDelete.setRecord(successor.getRecord());
            remove(successor, successor.getRecord().getKey());
        } else {
            BSTNode child = (nodeToDelete.getLeftChild() != null) ? nodeToDelete.getLeftChild() : nodeToDelete.getRightChild();
            replaceNode(nodeToDelete, child);
        }
    }

    // Returns the successor node of the specified node
    public BSTNode successor(BSTNode r, Key k) {
        BSTNode current = get(r, k);
        if (current == null) return null;

        if (current.getRightChild() != null) {
            return smallest(current.getRightChild());
        }

        BSTNode parent = current.getParent();
        while (parent != null && current == parent.getRightChild()) {
            current = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    // Returns the predecessor node of the specified node
    public BSTNode predecessor(BSTNode r, Key k) {
        BSTNode current = get(r, k);
        if (current == null) return null;

        if (current.getLeftChild() != null) {
            return largest(current.getLeftChild());
        }

        BSTNode parent = current.getParent();
        while (parent != null && current == parent.getLeftChild()) {
            current = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    // Returns the node with the smallest key in the specified subtree
    public BSTNode smallest(BSTNode r) {
        while (r != null && r.getLeftChild() != null) {
            r = r.getLeftChild();
        }
        return r;
    }

    // Returns the node with the largest key in the specified subtree
    public BSTNode largest(BSTNode r) {
        while (r != null && r.getRightChild() != null) {
            r = r.getRightChild();
        }
        return r;
    }

    // Replaces the specified node with a new child node during deletion
    private void replaceNode(BSTNode node, BSTNode newChild) {
        if (node.getParent() == null) {
            root = newChild;
        } else if (node == node.getParent().getLeftChild()) {
            node.getParent().setLeftChild(newChild);
        } else {
            node.getParent().setRightChild(newChild);
        }
        if (newChild != null) {
            newChild.setParent(node.getParent());
        }
    }
}
