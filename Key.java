public class Key {
    private String label;
    private int type;

    // Constructor that initializes a new Key with the specifications
    public Key(String theLabel, int theType) {
        // Converts the label lowercase for consistent comaprisons
        this.label = theLabel.toLowerCase();
        this.type = theType;
    }

    // Gets the label
    public String getLabel() {
        return label;
    }

    // Gets the type
    public int getType() {
        return type;
    }

    // Compares this.key to another key for ordering
    public int compareTo(Key k) {
        // Comparing the labels lexicographically
        int labelComparison = this.label.compareTo(k.getLabel());
        
        // If this label preceseds other label lexicographically
        if (labelComparison < 0) {
            return -1;       
        } 
        // If this label follows other label lexicographically
        else if (labelComparison > 0) {
            return 1;
        } 
        // If labels were equal, comapre by the type
        else {
            // this label smaller
            if (this.type < k.getType()) {
                return -1;
            } 
            // this label larger
            else if (this.type > k.getType()) {
                return 1;
            } 
            // Labels are equal
            else {
                return 0;  
            }
        }
    }
}
