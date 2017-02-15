package symbolTableEntry;

public class VarSymbolTableEntry extends SymbolTableEntry {
    public boolean val;
    public String type;
    public boolean isGlobal;
    public VarSymbolTableEntry(String name, int address, SymbolTable symbolTable,
                               boolean val, String type,boolean isGlobal) {
        super(name, VAR, address, symbolTable);
        this.val = val;
        this.type = type;
        this.isGlobal = isGlobal;
    }
}
