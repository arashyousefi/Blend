package symbolTableEntry;

public class VarSymbolTableEntry extends SymbolTableEntry {
    public boolean val;
    public String type;

    public VarSymbolTableEntry(String name, int address, SymbolTable symbolTable,
                               boolean val, String type) {
        super(name, VAR, address, symbolTable);
        this.val = val;
        this.type = type;
    }
}
