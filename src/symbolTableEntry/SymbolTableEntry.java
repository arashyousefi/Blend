package symbolTableEntry;

public class SymbolTableEntry {
    public static final int VAR = 1;
    public static final int FUNCTION = 2;
    public static final int ENVIRONMENT = 3;
    public static final int STRUCTURE = 4;
    public static final int ARRAY = 5;

    public String name;
    public int entryType;

    public SymbolTableEntry(String name, int entryType) {
        this.name = name;
        this.entryType = entryType;
    }

    public SymbolTableEntry() {
    }
}
