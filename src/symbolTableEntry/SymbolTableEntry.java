package symbolTableEntry;

public class SymbolTableEntry {
	public static final int VAR = 1;
	public static final int FUNCTION = 2;
	public static final int ENVIRONMENT = 3;
	public static final int STRUCTURE = 4;
	public static final int ARRAY = 5;

	public String name;
	public int entryType;
	public int address;
	public boolean isValue;
	public String type;
	public static SymbolTableEntry value = new SymbolTableEntry();

	public SymbolTableEntry(String name, int entryType, int address,
			boolean val, String type) {
		this.name = name;
		this.entryType = entryType;
		this.address = address;
		this.isValue = val;
		value.isValue = true;
		if (entryType == 1)
			this.type = type;
	}

	public SymbolTableEntry() {
	}
}
