import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import symbolTableEntry.SymbolTableEntry;

public class CodeGenerator {
	Scanner scanner; // This was my way of informing CG about Constant Values
						// detected by Scanner, you can do whatever you like

	// Define any variables needed for code generation

	ArrayList<SymbolTableEntry> ss = new ArrayList<>();
	ArrayList<Code> codes = new ArrayList<Code>();

	public CodeGenerator(Scanner scanner) {
		this.scanner = scanner;
	}

	public void Generate(String sem) {
		System.out.println(sem); // Just for debug

		if (sem.equals("NoSem"))
			return;
		else if (sem.equals("@aDscp")) {
			// TODO
		} else if (sem.equals("@push")) {
			// ss.add(e);
		} else if (sem.equals("@assign")) {
			// TODO
		} else if (sem.equals("@assignStr")) {
			// TODO
		} else if (sem.equals("@addStr")) {
			// TODO
		} else if (sem.equals("@addEnv")) {
			// TODO
		} else if (sem.equals("@assignArr")) {
			// TODO
		} else if (sem.equals("@goForward")) {
			// TODO
		} else if (sem.equals("@array")) {
			// TODO
		} else if (sem.equals("@array")) {
			// TODO
		} else if (sem.equals("@main")) {
			// TODO
		} else if (sem.equals("@cmpFunc")) {
			// TODO
		} else if (sem.equals("@makeBool")) {
			// TODO
		} else if (sem.equals("@makeInteger")) {
			// TODO
		} else if (sem.equals("@makeCharacter")) {
			// TODO
		} else if (sem.equals("@makeString")) {
			// TODO
		} else if (sem.equals("@makeReal")) {
			// TODO
		} else if (sem.equals("@makeLong")) {
			// TODO
		} else if (sem.equals("@addArg")) {
			// TODO
		} else if (sem.equals("@makeLate")) {
			// TODO
		} else if (sem.equals("@makeOut")) {
			// TODO
		} else if (sem.equals("@startBlock")) {
			// TODO
		} else if (sem.equals("@endBlock")) {
			// TODO
		} else if (sem.equals("@goto")) {
			// TODO
		} else if (sem.equals("@releaseStr")) {
			// TODO
		} else if (sem.equals("@break")) {
			// TODO
		} else if (sem.equals("@return")) {
			// TODO
		} else if (sem.equals("@assignMulti")) {
			// TODO
		} else if (sem.equals("@makeLabel")) {
			// TODO
		} else if (sem.equals("@findEnv")) {
			// TODO
		} else if (sem.equals("@findProperty")) {
			// TODO
		} else if (sem.equals("@logicalOr")) {
			// TODO
		} else if (sem.equals("@logicalAnd")) {
			// TODO
		} else if (sem.equals("@bitWiseOr")) {
			// TODO
		} else if (sem.equals("@bitWiseAnd")) {
			// TODO
		} else if (sem.equals("@xor")) {
			// TODO
		} else if (sem.equals("@equal")) {
			// TODO
		} else if (sem.equals("@notEqual")) {
			// TODO
		} else if (sem.equals("@less")) {
			// TODO
		} else if (sem.equals("@lessEqual")) {
			// TODO
		} else if (sem.equals("@more")) {
			// TODO
		} else if (sem.equals("@moreEqual")) {
			// TODO
		} else if (sem.equals("@add")) {
			// TODO
		} else if (sem.equals("@subtract")) {
			// TODO
		} else if (sem.equals("@mult")) {
			// TODO
		} else if (sem.equals("@divide")) {
			// TODO
		} else if (sem.equals("@remainder")) {
			// TODO
		} else if (sem.equals("@uMinus")) {
			// TODO
		} else if (sem.equals("@uComplement")) {
			// TODO
		} else if (sem.equals("@uNot")) {
			// TODO
		} else if (sem.equals("@jz")) {
			// TODO
		} else if (sem.equals("@jzCmpJz")) {
			// TODO
		} else if (sem.equals("@cmpJz")) {
			// TODO
		} else if (sem.equals("@jpLink")) {
			// TODO
		} else if (sem.equals("@fillLinks")) {
			// TODO
		} else if (sem.equals("@pushParameter")) {
			// TODO
		} else if (sem.equals("@call")) {
			// TODO
		} else if (sem.equals("@makeConstInteger")) {
			// TODO
		} else if (sem.equals("@makeConstCharacter")) {
			// TODO
		} else if (sem.equals("@makeConstString")) {
			// TODO
		} else if (sem.equals("@makeConstReal")) {
			// TODO
		} else if (sem.equals("@pushTrue")) {
			// TODO
		} else if (sem.equals("@pushFalse")) {
			// TODO
		} else if (sem.equals("@makeStruct")) {
			// TODO
		} else if (sem.equals("@isVoid")) {
			// TODO
		}

	}

	public void FinishCode() // You may need this
	{

	}

	public void WriteOutput(String outputName) {
		// Can be used to print the generated code to output
		// I used this because in the process of compiling, I stored the
		// generated code in a structure
		// If you want, you can output a code line just when it is generated
		// (strongly NOT recommended!!)
		String output = "";
		for (Code code : codes) {
			output = output + code.getText();
		}
		try {
			PrintWriter writer = new PrintWriter(new File("sampleProgram.out"),
					"UTF-8");
			writer.println(output);
			writer.close();
		} catch (IOException e) {
            System.out.println("Error creating output file!");
            e.printStackTrace();
		}
	}
}
