public class SymbolTableEntry {
    public static final int ID = 1;
    public static final int FUNCTION = 2;
    public static final int ENVIRONMENT = 3;
    public static final int STRUCTURE = 4;
    String name;
    int type;

    public SymbolTableEntry(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public SymbolTableEntry() {
    }
}
