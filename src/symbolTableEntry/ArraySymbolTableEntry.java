package symbolTableEntry;

public class ArraySymbolTableEntry extends SymbolTableEntry {
    public int dimension;
    public int[] upper_bound, lower_bound;

    public ArraySymbolTableEntry() {
        this.entryType = ARRAY;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
        this.lower_bound = new int[dimension];
        this.upper_bound = new int[dimension];
    }
}
