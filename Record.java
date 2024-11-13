public class Record {
    private Key theKey;
    private String data;

   // Initializes a new Record object with the specifications
    public Record(Key k, String theData) {
        this.theKey = k;
        this.data = theData;
    }

    // Getter method to get the key
    public Key getKey() {
        return theKey;
    }

    // Getter method to get the data item
    public String getDataItem() {
        return data;
    }
}
