import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import symbolTableEntry.SymbolTableEntry;

public class CodeGenerator {
	Scanner scanner; // This was my way of informing CG about Constant Values
						// detected by Scanner, you can do whatever you like

	// Define any variables needed for code generation
	Parser parser;
	ArrayList<Object> ss = new ArrayList<>();
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
						new Operand("gd", "i", "4"), null));
				// add relative address
				codes.add(new Code("+", new Operand("gd", "i", "4"),
						new Operand("im", "i", Integer.toString(id.address)),
						new Operand("gd", "i", "4")));
				// find the stack pointer
				codes.add(new Code(":=sp", new Operand("gd", "i", "8"), null,
						null));
				// push relative address:
				codes.add(new Code(":=", new Operand("gd", "i", "4"),
						new Operand("gi", "i", "8"), null));
				// increase stack pointer
				codes.add(new Code("+", new Operand("gd", "i", "8"),
						new Operand("im", "i", "4"),
						new Operand("gd", "i", "8")));
				// set stack pointer
				codes.add(new Code("sp:=", new Operand("gd", "i", "8"), null,
						null));
				ss.add(id);

			}
		} else if (sem.equals("@assign")) {
			if (popFirst().equals(popSecond())) {

				codes.add(new Code(":=", new Operand("gi", "i", "8"),
						new Operand("gi", "i", "12"), null));
			} else {
				// TODO ERROR or cast if possible
			}
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
			type = "boolean";
		} else if (sem.equals("@makeInteger")) {
			type = "integer";
		} else if (sem.equals("@makeCharacter")) {
			type = "character";
		} else if (sem.equals("@makeString")) {
			type = "string";
		} else if (sem.equals("@makeReal")) {
			type = "real";
		} else if (sem.equals("@makeLong")) {
			type = "long";
		} else if (sem.equals("@make")) {
			parser.currentSymbolTable.addSymbol(scanner.previousID,
					SymbolTableEntry.VAR, relativeAddress, false, type);
			// needs TODO if we want to implement structures!
			if (type.equals("boolean"))
				++relativeAddress;
			if (type.equals("integer"))
				relativeAddress += 4;
			if (type.equals("string")) {
				// TODO
			}
			if (type.equals("real"))
				relativeAddress += 4;

			if (type.equals("character"))
				relativeAddress++;
			if (type.equals("long"))
				relativeAddress += 8;
			this.Generate("@push");
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
			binary("||");
		} else if (sem.equals("@logicalAnd")) {
			binary("&&");
		} else if (sem.equals("@bitWiseOr")) {
			binary("|");
		} else if (sem.equals("@bitWiseAnd")) {
			binary("&");
		} else if (sem.equals("@xor")) {
			binary("^");
		} else if (sem.equals("@equal")) {
			binary("==");
		} else if (sem.equals("@notEqual")) {
			binary("!=");
		} else if (sem.equals("@less")) {
			binary("<");
		} else if (sem.equals("@lessEqual")) {
			binary("<=");
		} else if (sem.equals("@more")) {
			binary(">");
		} else if (sem.equals("@moreEqual")) {
			binary(">=");
		} else if (sem.equals("@add")) {
			binary("+");
		} else if (sem.equals("@subtract")) {
			binary("-");
		} else if (sem.equals("@mult")) {
			binary("*");
		} else if (sem.equals("@divide")) {
			binary("/");
		} else if (sem.equals("@remainder")) {
			binary("%");
		} else if (sem.equals("@uMinus")) {
			unary("u-");
		} else if (sem.equals("@uComplement")) {
			unary("~");
		} else if (sem.equals("@uNot")) {
			unary("!");
		} else if (sem.equals("@jz")) {
			popFirst();
			// generate the jump Code
			codes.add(new Code("jz", new Operand("gi", "b", "8"), null, null));
			// push pc
			ss.add(codes.size() - 1);

		} else if (sem.equals("@jpCmpJz")) {
			// get pc
			int pc = (Integer) (ss.get(ss.size() - 1));
			ss.remove(ss.size() - 1);
			Code jumpCode = codes.get(pc);
			jumpCode.op2 = new Operand("im", "i",
					Integer.toString(codes.size()));

		} else if (sem.equals("@cmpJz")) {
			// get pc
			int pc = (Integer) (ss.get(ss.size() - 1));
			ss.remove(ss.size() - 1);
			Code jumpCode = codes.get(pc);
			jumpCode.op2 = new Operand("im", "i",
					Integer.toString(codes.size()));

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
				id = parser.currentSymbolTable
						.addSymbol(scanner.CV, SymbolTableEntry.VAR,
								relativeAddress, false, "integer");
				relativeAddress += 4;
				// set the constant
				codes.add(new Code(":=", new Operand("gd", "i", "0"),
						new Operand("gd", "i", "4"), null));
				// add relative address
				codes.add(new Code("+", new Operand("gd", "i", "4"),
						new Operand("im", "i", Integer.toString(id.address)),
						new Operand("gd", "i", "4")));
				// set constant value
				codes.add(new Code(":=", new Operand("im", "i", scanner.CV),
						new Operand("gi", "i", "4"), null));
				notSet = false;
			}
			// push the entry
			// get heap address:
			if (notSet) {
				codes.add(new Code(":=", new Operand("gd", "i", "0"),
						new Operand("gd", "i", "4"), null));
				// add relative address
				codes.add(new Code("+", new Operand("gd", "i", "4"),
						new Operand("im", "i", Integer.toString(id.address)),
						new Operand("gd", "i", "4")));
			}
			// push relative address:
			// find the stack pointer
			codes.add(new Code(":=sp", new Operand("gd", "i", "8"), null, null));
			// push relative address:
			codes.add(new Code(":=", new Operand("gd", "i", "4"), new Operand(
					"gi", "i", "8"), null));
			// increase stack pointer
			codes.add(new Code("+", new Operand("gd", "i", "8"), new Operand(
					"im", "i", "4"), new Operand("gd", "i", "8")));
			// set stack pointer
			codes.add(new Code("sp:=", new Operand("gd", "i", "8"), null, null));
			ss.add(id);

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
			// find the value of the targeted address
			String type = popFirst();
			if (type.equals("integer"))
				codes.add(new Code("wi", new Operand("gi", "i", "8"), null,
						null));
			if (type.equals("real"))
				codes.add(new Code("wf", new Operand("gi", "i", "8"), null,
						null));
			if (type.equals("string"))
				codes.add(new Code("wt", new Operand("gi", "i", "8"), null,
						null));

		} else if (sem.equals("@pop")) {
			popFirst();
		} else if (sem.equals("@read")) {
			// find the value of the targeted address
			String type = popFirst();
			if (type.equals("integer"))
				codes.add(new Code("ri", new Operand("gi", "i", "8"), null,
						null));
			if (type.equals("real"))
				codes.add(new Code("rf", new Operand("gi", "i", "8"), null,
						null));
			if (type.equals("string"))
				codes.add(new Code("rt", new Operand("gi", "i", "8"), null,
						null));

		}

	}

	private void pushCurrent(String type) {
		// find the stack pointer
		codes.add(new Code(":=sp", new Operand("gd", "i", "8"), null, null));
		// push relative address:
		codes.add(new Code(":=", new Operand("gd", "i", "4"), new Operand("gi",
				"i", "8"), null));
		// increase stack pointer
		codes.add(new Code("+", new Operand("gd", "i", "8"), new Operand("im",
				"i", "4"), new Operand("gd", "i", "8")));
		// set stack pointer
		codes.add(new Code("sp:=", new Operand("gd", "i", "8"), null, null));
		ss.add(new SymbolTableEntry("temp", 1, 0, true, type));
	}

	private void init() {

		codes.add(new Code("gmm", new Operand("im", "i", "1024"), new Operand(
				"gd", "i", "0"), null));

	}

	private void binary(String operand) {
		// condition change if cast!
		if (popFirst().equals("integer") && popSecond().equals("integer")) {
			// get a temp
			codes.add(new Code("gmm", new Operand("im", "i", "4"), new Operand(
					"gd", "i", "4"), null));
			// binary this and push
			codes.add(new Code(operand, new Operand("gi", "i", "8"),
					new Operand("gi", "i", "12"), new Operand("gi", "i", "4")));
			this.pushCurrent("integer");
		} else {
			// TODO ERROR!
		}
	}

	private void unary(String operand) {

		if (popFirst().equals("integer")) {
			// get a temp
			codes.add(new Code("gmm", new Operand("im", "i", "4"), new Operand(
					"gd", "i", "4"), null));
			// unary this and set currentValue
			codes.add(new Code(operand, new Operand("gi", "i", "8"),
					new Operand("gi", "i", "4"), null));
			// push currentValue
			this.pushCurrent("integer");
		} else {
			// TODO ERROR!
		}
	}

	private String popFirst() {
		// checkType
		SymbolTableEntry popped = (SymbolTableEntry) ss.get(ss.size() - 1);
		ss.remove(ss.size() - 1);
		// find the stack pointer
		codes.add(new Code(":=sp", new Operand("gd", "i", "4"), null, null));
		// decrease stack pointer
		codes.add(new Code("-", new Operand("gd", "i", "4"), new Operand("im",
				"i", "4"), new Operand("gd", "i", "4")));
		// set stack pointer
		codes.add(new Code("sp:=", new Operand("gd", "i", "4"), null, null));
		// find the stack pointer
		codes.add(new Code(":=sp", new Operand("gd", "i", "4"), null, null));
		// pop operand
		codes.add(new Code(":=", new Operand("gi", "i", "4"), new Operand("gd",
				"i", "8"), null));

		freeIfTemp(popped);
		return popped.type;
	}

	private void freeIfTemp(SymbolTableEntry popped) {
		if (popped.isValue)
			codes.add(new Code("fmm", new Operand("gd", "i", "8"), new Operand(
					"im", "i", "4"), null));
	}

	private String popSecond() {
		// checkType
		SymbolTableEntry popped = (SymbolTableEntry) ss.get(ss.size() - 1);
		ss.remove(ss.size() - 1);
		// find the stack pointer
		codes.add(new Code(":=sp", new Operand("gd", "i", "4"), null, null));
		// decrease stack pointer
		codes.add(new Code("-", new Operand("gd", "i", "4"), new Operand("im",
				"i", "4"), new Operand("gd", "i", "4")));
		// set stack pointer
		codes.add(new Code("sp:=", new Operand("gd", "i", "4"), null, null));
		// find the stack pointer
		codes.add(new Code(":=sp", new Operand("gd", "i", "4"), null, null));
		// pop first operand
		codes.add(new Code(":=", new Operand("gi", "i", "4"), new Operand("gd",
				"i", "12"), null));
		// freeIfTemp();
		freeIfTemp2(popped);
		return popped.type;
	}

	private void freeIfTemp2(SymbolTableEntry popped) {
		if (popped.isValue)
			codes.add(new Code("fmm", new Operand("gd", "i", "12"),
					new Operand("im", "i", "4"), null));
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
