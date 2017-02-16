package symbolTableEntry;

import java.util.ArrayList;

public class FunctionSymbolTableEntry extends SymbolTableEntry {
	public int args, jumpCode;
	public ArrayList<String> argNames = new ArrayList<>();
	public ArrayList<String> argTypes = new ArrayList<>();
	public ArrayList<Integer> argAddress = new ArrayList<>();
	public ArrayList<String> retTypes = new ArrayList<>();

	public FunctionSymbolTableEntry(String name, int address, SymbolTable symbolTable, int jumpCode) {
		super(name, FUNCTION, address, symbolTable);
		this.jumpCode = jumpCode;
	}
}
