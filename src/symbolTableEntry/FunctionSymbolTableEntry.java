package symbolTableEntry;

public class FunctionSymbolTableEntry extends SymbolTableEntry {
    public int args;

    public FunctionSymbolTableEntry() {
        this.entryType = FUNCTION;
    }
}
