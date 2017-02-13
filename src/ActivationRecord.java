import java.util.ArrayList;

import symbolTableEntry.SymbolTable;

public class ActivationRecord {
	SymbolTable symbolTable;
	ArrayList<String> argTypes;
	ArrayList<String> retTypes;
	ArrayList<Integer> args;
	ArrayList<Integer> rets;

	public ActivationRecord(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}

}
