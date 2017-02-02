import symbolTableEntry.SymbolTableEntry;

import java.util.ArrayList;

public class SymbolTable {
    ArrayList<SymbolTableEntry> symbols = new ArrayList<>();
    SymbolTable parent; // scope in which this symbol table is defined in

    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
    }

    public SymbolTable() {
    }

    public SymbolTableEntry findSymbol(String name, boolean findInParent) {
        for (SymbolTableEntry symbolTableEntry : symbols)
            if (symbolTableEntry.name.equals(name))
                return symbolTableEntry;
        if (parent != null && findInParent)
            return parent.findSymbol(name, true);
        return null;
    }

    public SymbolTableEntry addSymbol(String name, int type, int address) {
        SymbolTableEntry symbolTableEntry = new SymbolTableEntry(name, type,address);
        symbols.add(symbolTableEntry);
        return symbolTableEntry;
    }
}
