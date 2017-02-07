package symbolTableEntry;

import java.util.ArrayList;

public class LabelSymbolTableEntry extends SymbolTableEntry {
    public ArrayList<Integer> references = new ArrayList<>();
    public Integer address = -1;

    public LabelSymbolTableEntry(String name) {
        this.entryType = LABEL;
        this.name = name;
    }
}
