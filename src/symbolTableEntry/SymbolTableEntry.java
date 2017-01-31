package symbolTableEntry;

public class SymbolTableEntry {
    public static final int VAR = 1;
    public static final int FUNCTION = 2;
    public static final int ENVIRONMENT = 3;
    public static final int STRUCTURE = 4;
    public static final int ARRAY = 5;

    public String name;
    public int type;

    public SymbolTableEntry(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public SymbolTableEntry() {
    }
}
