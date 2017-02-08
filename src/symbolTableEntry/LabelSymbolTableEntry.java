package symbolTableEntry;

import java.util.ArrayList;

public class LabelSymbolTableEntry extends SymbolTableEntry {
    public ArrayList<Integer> references = new ArrayList<>();
    public Integer line;

    public LabelSymbolTableEntry(String name, int address, SymbolTable symbolTable, Integer line) {
        super(name, LABEL, address, symbolTable);
        this.line = line;
    }
}
