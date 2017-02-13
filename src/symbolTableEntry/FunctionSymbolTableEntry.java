package symbolTableEntry;

public class FunctionSymbolTableEntry extends SymbolTableEntry {
	public int args, jumpCode;

	public FunctionSymbolTableEntry(String name, int address, SymbolTable symbolTable, int jumpCode) {
		super(name, FUNCTION, address, symbolTable);
		this.jumpCode = jumpCode;
	}
}
