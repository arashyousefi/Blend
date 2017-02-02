import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import symbolTableEntry.SymbolTableEntry;

public class CodeGenerator {
	Scanner scanner; // This was my way of informing CG about Constant Values
						// detected by Scanner, you can do whatever you like

	// Define any variables needed for code generation
	Parser parser;
	ArrayList<SymbolTableEntry> ss = new ArrayList<>();
	ArrayList<Code> codes = new ArrayList<Code>();
	int relativeAddress = 0;
	String type;

	public CodeGenerator(Scanner scanner, Parser parser) {
		this.scanner = scanner;
		this.parser = parser;
		init();
	}

	public void Generate(String sem) {
		System.out.println(sem); // Just for debug

		if (sem.equals("NoSem"))
			return;
		else if (sem.equals("@aDscp")) {
			// TODO
		} else if (sem.equals("@push")) {
			SymbolTableEntry id = parser.currentSymbolTable.findSymbol(
					scanner.previousID, true);
			if (id == null) {
				// TODO runtime exception here!
			} else {
				// get heap address:
				codes.add(new Code(":=", new Operand("gd", "i", "0"),
						new Operand("ld", "i", "0"), null));
				// add relative address
				codes.add(new Code("+", new Operand("ld", "i", "0"),
						new Operand("im", "i", Integer.toString(id.address)),
						new Operand("ld", "i", "0")));
				// find the stack pointer
				codes.add(new Code(":=sp", new Operand("ld", "i", "4"), null,
						null));
				// push relative address:
				codes.add(new Code(":=", new Operand("ld", "i", "0"),
						new Operand("li", "i", "4"), null));
				// increase stack pointer
				codes.add(new Code("+", new Operand("ld", "i", "4"),
						new Operand("im", "i", "4"),
						new Operand("ld", "i", "4")));
				// set stack pointer
				codes.add(new Code("sp:=", new Operand("ld", "i", "4"), null,
						null));
				System.out.println(id.name + "pushed with value:" + id.address);
			}
		} else if (sem.equals("@assign")) {
			// find the stack pointer
			codes.add(new Code(":=sp", new Operand("ld", "i", "0"), null, null));
			// pop second operand
			codes.add(new Code(":=", new Operand("li", "i", "0"), new Operand(
					"ld", "i", "4"), null));
			// decrease stack pointer
			codes.add(new Code("-", new Operand("ld", "i", "0"), new Operand(
					"im", "i", "4"), new Operand("ld", "i", "0")));
			// set stack pointer
			codes.add(new Code("sp:=", new Operand("ld", "i", "0"), null, null));
			// find the stack pointer
			codes.add(new Code(":=sp", new Operand("ld", "i", "0"), null, null));
			// pop first operand
			codes.add(new Code(":=", new Operand("li", "i", "0"), new Operand(
					"ld", "i", "8"), null));
			// decrease stack pointer
			codes.add(new Code("-", new Operand("ld", "i", "0"), new Operand(
					"im", "i", "4"), new Operand("ld", "i", "0")));
			// set stack pointer
			codes.add(new Code("sp:=", new Operand("ld", "i", "0"), null, null));

			// assign the two
			codes.add(new Code(":=", new Operand("li", "i", "4"), new Operand(
					"li", "i", "8"), null));

		} else if (sem.equals("@assignStr")) {
			// TODO
		} else if (sem.equals("@addStr")) {
			parser.structs.add(scanner.previousID);
			// TODO
		} else if (sem.equals("@addEnv")) {
			// TODO
		} else if (sem.equals("@assignArr")) {
			// TODO
		} else if (sem.equals("@goForward")) {
			// TODO
		} else if (sem.equals("@array")) {
			// TODO
		} else if (sem.equals("@main")) {
			// TODO
		} else if (sem.equals("@cmpFunc")) {
			// TODO
		} else if (sem.equals("@makeBool")) {
			type = "bool";
		} else if (sem.equals("@makeInteger")) {
			type = "int";
		} else if (sem.equals("@makeCharacter")) {
			type = "char";
		} else if (sem.equals("@makeString")) {
			type = "string";
		} else if (sem.equals("@makeReal")) {
			type = "real";
		} else if (sem.equals("@makeLong")) {
			type = "long";
		} else if (sem.equals("@make")) {
			parser.currentSymbolTable.addSymbol(scanner.previousID,
					SymbolTableEntry.VAR, relativeAddress);
			// needs TODO if we want to implement structures!
			if (type.equals("bool"))
				++relativeAddress;
			if (type.equals("int"))
				relativeAddress += 4;
			if (type.equals("string")) {
				// TODO
			}
			if (type.equals("real"))
				relativeAddress += 4;

			if (type.equals("char"))
				relativeAddress++;
			if (type.equals("long"))
				relativeAddress += 8;

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
			// find the stack pointer
			codes.add(new Code(":=sp", new Operand("ld", "i", "0"), null, null));
			// pop second operand
			codes.add(new Code(":=", new Operand("li", "i", "0"), new Operand(
					"ld", "i", "4"), null));
			// decrease stack pointer
			codes.add(new Code("-", new Operand("ld", "i", "0"), new Operand(
					"im", "i", "4"), new Operand("ld", "i", "0")));
			// set stack pointer
			codes.add(new Code("sp:=", new Operand("ld", "i", "0"), null, null));
			// find the stack pointer
			codes.add(new Code(":=sp", new Operand("ld", "i", "0"), null, null));
			// pop first operand
			codes.add(new Code(":=", new Operand("li", "i", "0"), new Operand(
					"ld", "i", "8"), null));
			// decrease stack pointer
			codes.add(new Code("-", new Operand("ld", "i", "0"), new Operand(
					"im", "i", "4"), new Operand("ld", "i", "0")));
			// set stack pointer
			codes.add(new Code("sp:=", new Operand("ld", "i", "0"), null, null));
			// get temp
			codes.add(new Code("gmm", new Operand("ld", "i", "12"),
					new Operand("im", "i", "4"), null));
			// add the two
			codes.add(new Code("+", new Operand("li", "i", "4"), new Operand(
					"li", "i", "8"), new Operand("li", "i", "12")));
			// find the stack pointer
			codes.add(new Code(":=sp", new Operand("ld", "i", "0"), null, null));
			// push result
			codes.add(new Code(":=", new Operand("ld", "i", "12"), new Operand(
					"li", "i", "0"), null));
			// increase stack pointer
			codes.add(new Code("+", new Operand("ld", "i", "0"), new Operand(
					"im", "i", "4"), new Operand("ld", "i", "0")));
			// set stack pointer
			codes.add(new Code("sp:=", new Operand("ld", "i", "0"), null, null));

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
			// find the constant if not found make an entry
			SymbolTableEntry id = parser.currentSymbolTable.findSymbol(
					scanner.CV, true);
			boolean notSet = true;
			if (id == null) {
				id = parser.currentSymbolTable.addSymbol(scanner.CV,
						SymbolTableEntry.VAR, relativeAddress);
				relativeAddress += 4;
				// set the constant
				codes.add(new Code(":=", new Operand("gd", "i", "0"),
						new Operand("ld", "i", "0"), null));
				// add relative address
				codes.add(new Code("+", new Operand("ld", "i", "0"),
						new Operand("im", "i", Integer.toString(id.address)),
						new Operand("ld", "i", "0")));
				codes.add(new Code(":=", new Operand("im", "i", scanner.CV),
						new Operand("li", "i", "0"), null));
				notSet = false;
			}
			// push the entry
			// get heap address:
			if (notSet) {
				codes.add(new Code(":=", new Operand("gd", "i", "0"),
						new Operand("ld", "i", "0"), null));
				// add relative address
				codes.add(new Code("+", new Operand("ld", "i", "0"),
						new Operand("im", "i", Integer.toString(id.address)),
						new Operand("ld", "i", "0")));
			}
			// push relative address:
			// find the stack pointer
			codes.add(new Code(":=sp", new Operand("ld", "i", "4"), null, null));
			// push relative address:
			codes.add(new Code(":=", new Operand("ld", "i", "0"), new Operand(
					"li", "i", "4"), null));
			// increase stack pointer
			codes.add(new Code("+", new Operand("ld", "i", "4"), new Operand(
					"im", "i", "4"), new Operand("ld", "i", "4")));
			// set stack pointer
			codes.add(new Code("sp:=", new Operand("ld", "i", "4"), null, null));
			System.out.println(id.name + "pushed with value:" + id.address);

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
		} else if (sem.equals("@write")) {
			// get top of stack
			codes.add(new Code(":=sp", new Operand("ld", "i", "0"), null, null));
			// print it
			codes.add(new Code("wi", new Operand("li", "i", "0"), null, null));
		}

	}

	private void init() {

		codes.add(new Code("gmm", new Operand("im", "i", "1024"), new Operand(
				"gd", "i", "0"), null));

	}

	public void FinishCode() // You may need this
	{
		codes.add(new Code("fmm", new Operand("gd", "i", "0"), new Operand(
				"im", "i", "1024"), null));

	}

	public void WriteOutput(String outputName) {
		// Can be used to print the generated code to output
		// I used this because in the process of compiling, I stored the
		// generated code in a structure
		// If you want, you can output a code line just when it is generated
		// (strongly NOT recommended!!)
		String output = "";
		try {
			PrintWriter writer = new PrintWriter(new File("sampleProgram.out"),
					"UTF-8");
			for (Code code : codes) {
				output = code.getText();
				// writer.append(output);
				writer.println(output);

			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Error creating output file!");
			e.printStackTrace();
		}
	}
}
