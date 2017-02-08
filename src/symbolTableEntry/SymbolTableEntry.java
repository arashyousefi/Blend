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
    public SymbolTable parent;

    public SymbolTableEntry(String name, int entryType, int address, SymbolTable symbolTable) {
        //todo clean this shit
        this.parent = symbolTable;
        this.name = name;
        this.entryType = entryType;
        this.address = address;
    }

    public SymbolTableEntry() {
    }

    public int getAddress() {
        return (parent.offset * 1024 + address);
    }
}
