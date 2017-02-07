package symbolTableEntry;

public class SymbolTableEntry {
    public static final int VAR = 1;
    public static final int FUNCTION = 2;
    public static final int ENVIRONMENT = 3;
    public static final int STRUCTURE = 4;
    public static final int ARRAY = 5;
    public static final int LABEL = 6;

    public String name;
    public int entryType;
    public int address;
    public boolean isValue;
    public String type;

    public SymbolTableEntry(String name, int entryType, int address,
                            boolean val, String type) {
        //todo clean this shit
        this.name = name;
        this.entryType = entryType;
        this.address = address;
        this.isValue = val;
        if (entryType == 1)
            this.type = type;
    }

    public SymbolTableEntry() {
    }
}
