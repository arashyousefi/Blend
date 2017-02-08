package symbolTableEntry;

import java.util.ArrayList;

public class SymbolTable {
    public static final int SYMBOL_TABLE_SIZE = 1024;
    public ArrayList<SymbolTableEntry> symbols = new ArrayList<>();
    public SymbolTable parent; // scope in which this symbol table is defined in
    public int offset;

    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
        this.offset = parent.offset + 1;
    }

    public SymbolTable() {
        parent = null;
        offset = 0;
    }

    public SymbolTableEntry findSymbol(String name, boolean findInParent) {
        for (SymbolTableEntry symbolTableEntry : symbols)
            if (symbolTableEntry.name.equals(name))
                return symbolTableEntry;
        if (parent != null && findInParent)
            return parent.findSymbol(name, true);
        return null;
    }

    public void addSymbol(SymbolTableEntry symbolTableEntry) {
        symbols.add(symbolTableEntry);
    }

//    public SymbolTableEntry addSymbol(String name, int entryType, int address,
//                                      boolean val, String type) {
//        SymbolTableEntry symbolTableEntry = new SymbolTableEntry(name,
//                entryType, address, val, type);
//        symbols.add(symbolTableEntry);
//        return symbolTableEntry;
//    }
}