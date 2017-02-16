import symbolTableEntry.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

public class CodeGenerator {
	public static final int STRING_BUFFER_ADDRESS = 100 * 1000;
	public static final int STRING_SIZE_ADDRESS = 99 * 1000;
	public static final int STRING_ADDRESS_ADDRESS = 99 * 1000 + 4;
	public static final int ACTIVATION_RECORD_SIZE = 10240;
	public static final int RETURN_SIZE = 20;
	public static final String BASE = "0";
	public static final String FLOAT_H1 = "20";
	public static final String FLOAT_H2 = "24";
	public static final String FLOAT_H3 = "28";
	public static final String FLOAT_H4 = "32";
	public static final String CASTED = "36";
	public static final String AR_H1 = "40";
	public static final String FRAME_POINTER = "100";
	public static final String HEAP_POINTER = "104";
	Scanner scanner; // This was my way of informing CG about Constant Values
	// detected by Scanner, you can do whatever you like

	// Define any variables needed for code generation
	Parser parser;
	ArrayList<Object> ss = new ArrayList<>();
	ArrayList<Code> codes = new ArrayList<>();
	int relativeAddress = 0;
	String type;
	Code previousIf;
	int functionArgs = 0;
	int parameterIndex;
	public FunctionSymbolTableEntry currentFunc = null;
	ArrayList<CaseLink> firstLinks = new ArrayList<>();
	ArrayList<LoopLink> loopLinks = new ArrayList<>();
	public ArrayList<ActivationRecord> display = new ArrayList<>();

	public String[] bitwiseOperators = { "&", "|", "^", "%" };
	public String[] logicalOperators = { "=", "!=", ">", "<", ">=", "<=", "&&", "||" };
	public String[] arithmeticOperators = { "*", "/", "+", "-" };
	public String[] goodTypes = { "real", "integer", "boolean", "character" };

    public CodeGenerator(Scanner scanner, Parser parser) {
        this.scanner = scanner;
        this.parser = parser;
        init();
    }

    private void makeCode(String upcode, String op1, String op2, String op3) {
        codes.add(new Code(upcode, new Operand(op1), new Operand(op2), new Operand(op3)));
    }

    private void makeCode(String upcode, String op1, String op2) {
        codes.add(new Code(upcode, new Operand(op1), new Operand(op2), null));
    }

    private void makeCode(String upcode, String op1) {
        codes.add(new Code(upcode, new Operand(op1), null, null));
    }

    private void makeCode(String upcode) {
        codes.add(new Code(upcode, null, null, null));
    }

    Integer getPc() {
        return codes.size() - 1;
    }

    Object popSS() {
        return ss.remove(ss.size() - 1);
    }

    void pushSS(Object a) {
        ss.add(a);
    }

    private void fail() {
        throw new RuntimeException("Compilation failed");
    }

    public void Generate(String sem) {
        // System.err.println(sem); // Just for debug
        if (sem.equals("NoSem"))
            return;

        java.lang.reflect.Method method;
        try {
            method = this.getClass().getMethod("cg" + sem.substring(1));
            try {
                method.invoke(this);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                fail();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                fail();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                fail();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            fail();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void cgmakeBool() {
        type = "boolean";
    }

    public void cgmakeInteger() {
        type = "integer";
    }

    public void cgmakeCharacter() {
        type = "character";
    }

    public void cgmakeString() {
        type = "string";
    }

    public void cgmakeReal() {
        type = "real";
    }

    public void cgmakeLong() {
        type = "long";
    }

    public void cgaddReturn() {
        // todo
    }

	public void cgmake() {
		boolean isGlobal = (parser.currentSymbolTable.parent == null) ? true : false;
		parser.currentSymbolTable.addSymbol(new VarSymbolTableEntry(scanner.previousID,
				relativeAddress, parser.currentSymbolTable, false, type, isGlobal));
		// parser.currentSymbolTable.addSymbol(scanner.previousID, SymbolTableEntry.VAR,
		// relativeAddress, false, type);
		// needs TODO if we want to implement structures!
		if (type.equals("boolean"))
			relativeAddress++;
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
    }

	public void cgaddArg() {
		FunctionSymbolTableEntry func = (FunctionSymbolTableEntry) popSS();
		func.argNames.add(scanner.previousID);
		func.argTypes.add(type);
		func.argAddress.add(functionArgs);
		functionArgs += getTypeSize(type);
		pushSS(func);
	}

	private int getTypeSize(String type) {
		if (type.equals("boolean") || type.equals("character"))
			return 1;
		if (type.equals("integer") || type.equals("real"))
			return 4;
		return -1;

	}

    public void cgmakeLate() {
        // TODO
    }

    public void cgmakeOut() {
        // TODO
    }

	public void cgstartBlock() {
		if (parser.currentSymbolTable.parent == null)
			makeCode(":=", "gd_i_" + FRAME_POINTER, "gd_i_" + BASE);

		SymbolTable symbolTable = new SymbolTable(parser.currentSymbolTable);
		parser.currentSymbolTable = symbolTable;

	}

	public void cgendBlock() {
		parser.currentSymbolTable = parser.currentSymbolTable.parent;
		if (parser.currentSymbolTable.parent == null)
			makeCode(":=", "gd_i_" + HEAP_POINTER, "gd_i_" + BASE);
	}

    public void cgreleaseStr() {
        // TODO
    }

    public void cgcontinue() {
        if (loopLinks.isEmpty()) {
            throw new RuntimeException("break outside of loop");
        }
        LoopLink loopLink = loopLinks.get(loopLinks.size() - 1);
        makeCode("jmp", "im_i_" + loopLink.start);
    }

    public void cgbreak() {
        if (loopLinks.isEmpty()) {
            throw new RuntimeException("break outside of loop");
        }
        makeCode("jmp");
        loopLinks.get(loopLinks.size() - 1).breaks.add(getPc());
    }

    public void cgreturn() {
        // TODO
    }

    public void cgassignMulti() {
        // TODO
    }

    public void cggoto() {
        Code gotoCode = new Code("jmp");
        codes.add(gotoCode);
        String id = scanner.previousID;
        SymbolTableEntry symbolTableEntry = parser.currentSymbolTable.findSymbol(id, false);
        if (symbolTableEntry != null) {// label was created before
            LabelSymbolTableEntry labelSymbolTableEntry = (LabelSymbolTableEntry) symbolTableEntry;
            if (labelSymbolTableEntry.line == -1) { // just add it to the references
                // System.err.println("goto without address " + id);
                labelSymbolTableEntry.references.add(getPc());
            } else { // we know the address
                // System.err.println("goto with address " + id);
                gotoCode.op1 = new Operand("im", "i", labelSymbolTableEntry.line.toString());
            }
        } else { // we have to create it now
            // System.err.println("new goto without address " + id);
            LabelSymbolTableEntry labelSymbolTableEntry = new LabelSymbolTableEntry(id, -1,
                    parser.currentSymbolTable, -1);
            labelSymbolTableEntry.references.add(getPc());
            parser.currentSymbolTable.addSymbol(labelSymbolTableEntry);
        }
    }

    public void cgmakeLabel() {
        String id = scanner.previousID;
        SymbolTableEntry symbolTableEntry = parser.currentSymbolTable.findSymbol(id, false);
        if (symbolTableEntry == null) { // just create, no need to do anything
            // System.err.println("new label " + id);
            LabelSymbolTableEntry labelSymbolTableEntry = new LabelSymbolTableEntry(id, -1,
                    parser.currentSymbolTable, getPc() + 1);
            parser.currentSymbolTable.addSymbol(labelSymbolTableEntry);

        } else {
            // System.err.println("label " + id);
            LabelSymbolTableEntry labelSymbolTableEntry = (LabelSymbolTableEntry) symbolTableEntry;
            labelSymbolTableEntry.line = getPc() + 1;
            for (int i : labelSymbolTableEntry.references) {
                // System.err.println("fixed code " + id + " " + i);
                codes.get(i).op1 = new Operand("im", "i", labelSymbolTableEntry.line.toString());
            }
            labelSymbolTableEntry.references.clear();
        }
    }

    public void cgfindEnv() {
        // TODO
    }

    public void cgfindProperty() {
        // TODO
    }

    public void cglogicalOr() {
        binary("||");
    }

    public void cglogicalAnd() {
        binary("&&");
    }

    public void cgbitWiseOr() {
        binary("|");
    }

    public void cgbitWiseAnd() {
        binary("&");
    }

    public void cgxor() {
        binary("^");
    }

    public void cgequal() {
        binary("==");
    }

    public void cgnotEqual() {
        binary("!=");
    }

    public void cgless() {
        binary("<");
    }

    public void cglessEqual() {
        binary("<=");
    }

    public void cgmore() {
        binary(">");
    }

    public void cgmoreEqual() {
        binary(">=");
    }

    public void cgadd() {
        binary("+");
    }

    public void cgsubtract() {
        binary("-");
    }

    public void cgmult() {
        binary("*");
    }

    public void cgdivide() {
        binary("/");
    }

    public void cgremainder() {
        binary("%");
    }

    public void cguMinus() {
        unary("u-");
    }

    public void cguComplement() {
        unary("~");
    }

    public void cguNot() {
        unary("!");
    }

    public void cgjz() {
        popFirst();
        // generate the jump Code
        makeCode("jz", "gi_b_8");
        pushSS(getPc());
    }

    public void cgcmpJz() {
        int pc = (Integer) popSS();
        previousIf = codes.get(pc);
        previousIf.op2 = new Operand("im", "i", Integer.toString(getPc() + 1));
    }

    public void cgjpCmpJz() {
        previousIf.op2 = new Operand("im", "i", Integer.toString(getPc() + 2));
        makeCode("jmp");
        pushSS(getPc());
    }

    public void cgcmpJp() {
        int lastJump = (Integer) popSS();
        Code jumpCode = codes.get(lastJump);
        jumpCode.op1 = new Operand("im", "i", Integer.toString(codes.size()));
    }

    private void fillBreaks() {
        LoopLink loopLink = loopLinks.remove(loopLinks.size() - 1);
        int end = getPc() + 1;
        for (Integer i : loopLink.breaks) {
            codes.get(i).op1 = new Operand("im_i_" + end);
        }
    }

    public void cgwhileJpCjz() {
        int whileLine = (Integer) popSS();
        Code whileCode = codes.get(whileLine);
        whileCode.op2 = new Operand("im", "i", Integer.toString(getPc() + 2));

        Integer whileEvalLine = (Integer) popSS();
        makeCode("jmp", "im_i_" + whileEvalLine);
        fillBreaks();
    }

    public void cgpushPCAddLink() {
        pushSS(getPc() + 1);
        loopLinks.add(new LoopLink(getPc() + 1));
    }

    public void cgpushPC() {
        pushSS(getPc() + 1);
    }

    public void cgdoJz() { // it should be doJnz
        popFirst();
        Integer doLine = (Integer) popSS();
        makeCode("!", "gi_b_8", "gi_b_8");
        // generate the jump Code
        makeCode("jz", "gi_b_8", "im_i_" + doLine.toString());
        fillBreaks();
    }

    public void cgcaseJump() {
        popFirst();
        makeCode("-", "gi_i_8", "", "gd_i_12");
        pushSS(getPc());
        makeCode("+", "gd_i_12", "", "gd_i_12");
        pushSS(getPc());
        makeCode("jmp", "gd_i_12");
    }

    public void cgpushFirstAddr() {
        cgpop();

        firstLinks.add(new CaseLink(getPc() + 1, Integer.parseInt(scanner.CV)));
    }

    public void cgpushAddr() {
        cgpop();
        CaseLink last = firstLinks.get(firstLinks.size() - 1);
        while (last.next != null)
            last = last.next;
        last.next = new CaseLink(getPc() + 1, Integer.parseInt(scanner.CV));

    }

    public void cgjpLink() {
        makeCode("jmp");
        pushSS(getPc());
    }

    public void cgfillLinks() {
        ArrayList<CaseLink> lastCase = new ArrayList<CaseLink>();
        CaseLink first = firstLinks.remove(firstLinks.size() - 1);
        while (first != null) {
            lastCase.add(first);
            first = first.next;
        }
        CaseLink max = Collections.max(lastCase);
        CaseLink min = Collections.min(lastCase);
        Integer out = getPc() + max.value - min.value + 2;
        Integer start = getPc() + 1;
        for (int i = min.value; i <= max.value; ++i)
            makeCode("jmp", "im_i_" + out);
        for (CaseLink cl : lastCase)
            codes.get(start + cl.value - min.value).op1 = new Operand("im", "i",
                    cl.address.toString());
        for (int i = 0; i < lastCase.size(); ++i) {
            Integer addr = (Integer) popSS();
            codes.get(addr).op2 = new Operand("im", "i", out.toString());
        }
        Integer plusAddr = (Integer) popSS();
        codes.get(plusAddr).op2 = new Operand("im", "i", start.toString());

        Integer minusAddr = (Integer) popSS();

        codes.get(minusAddr).op2 = new Operand("im", "i", min.value.toString());

    }

	public void cgpushParameter() {
		// pop the incoming argument from stack
		String type = popFirst();

		FunctionSymbolTableEntry func = (FunctionSymbolTableEntry) popSS();
		// find address of the argument in the activation record!
		makeCode("-", "gd_i_" + FRAME_POINTER, "im_i_" + func.argAddress.get(parameterIndex),
				"gd_i_4");
		// checkType
		if (type.equals(func.argTypes.get(parameterIndex))) {
			makeCode(":=", "gi_i_8", "gi_i_4");
		} else {
			// invalid argument type!
			throw new RuntimeException("invalid argument type");
		}
		parameterIndex++;
		pushSS(func);
	}

	public void cgpushFirstParameter() {
		parameterIndex = 0;
		// pop the incoming argument from stack
		String type = popFirst();
		// pop the caller
		FunctionSymbolTableEntry func = (FunctionSymbolTableEntry) popSS();

		// find address of the argument in the activation record!
		makeCode("-", "gd_i_" + FRAME_POINTER, "im_i_" + func.argAddress.get(parameterIndex),
				"gd_i_4");
		// checkType
		if (type.equals(func.argTypes.get(parameterIndex))) {
			makeCode(":=", "gi_i_8", "gi_i_4");
		} else {
			// invalid argument type!
			throw new RuntimeException("invalid argument type");
		}
		parameterIndex++;
		// push caller for next arguments or call!
		pushSS(func);
	}

	public void cgcall() {
		int finish = 4;

		// push return address
		makeCode("-", "gd_i_" + FRAME_POINTER, "im_i_4", "gd_i_" + FRAME_POINTER);
		makeCode(":=", "im_i_" + (getPc() + finish), "gi_i_" + FRAME_POINTER);
		makeCode("+", "gd_i_" + FRAME_POINTER, "im_i_4", "gd_i_" + FRAME_POINTER);
		// jump to the start of function
		FunctionSymbolTableEntry func = (FunctionSymbolTableEntry) popSS();
		makeCode("jmp", "im_i_" + (func.jumpCode + 1) + "");
	}

	public void cgpushReturn() {
		makeCode("+", "im_i_" + ACTIVATION_RECORD_SIZE, "gd_i_" + FRAME_POINTER, "gd_i_"
				+ FRAME_POINTER);

    }

	public void cgmakeConstInteger() {
		// find the constant if not found make an entry
		VarSymbolTableEntry id = (VarSymbolTableEntry) parser.currentSymbolTable.findSymbol(
				scanner.CV, false);
		boolean notSet = true;
		if (id == null) {
			id = new VarSymbolTableEntry(scanner.CV, relativeAddress, parser.currentSymbolTable,
					false, "integer", false);
			parser.currentSymbolTable.addSymbol(id);
			// id = parser.currentSymbolTable.addSymbol(scanner.CV, SymbolTableEntry.VAR,
			// relativeAddress, false, "integer");
			relativeAddress += 4;
			// set the constant
			// add relative address
			makeCode("+", "gd_i_" + BASE, "im_i_" + Integer.toString(id.getAddress()), "gd_i_4");
			// set constant value
			makeCode(":=", "im_i_" + scanner.CV, "gi_i_4");
			notSet = false;
		}
		// push the entry
		pushConst(notSet, id);
	}

	public void cgmakeConstCharacter() {
		// find the constant if not found make an entry
		VarSymbolTableEntry id = (VarSymbolTableEntry) parser.currentSymbolTable.findSymbol(
				scanner.CV, false);
		boolean notSet = true;
		if (id == null) {
			id = new VarSymbolTableEntry(scanner.CV, relativeAddress, parser.currentSymbolTable,
					false, "character", false);
			parser.currentSymbolTable.addSymbol(id);
			// id = parser.currentSymbolTable.addSymbol(scanner.CV, SymbolTableEntry.VAR,
			// relativeAddress, false, "integer");
			relativeAddress += 1;
			// set the constant
			// add relative address
			makeCode("+", "gd_i_" + BASE, "im_i_" + Integer.toString(id.getAddress()), "gd_i_4");
			// set constant value
			makeCode(":=", "im_c_" + scanner.CV.charAt(1), "gi_c_4");
			notSet = false;
		}
		// push the entry
		pushConst(notSet, id);
	}

    public void cgmakeConstString() {
        // todo
    }

	public void cgmakeConstReal() {
		// find the constant if not found make an entry
		VarSymbolTableEntry id = (VarSymbolTableEntry) parser.currentSymbolTable.findSymbol(
				scanner.CV, false);
		boolean notSet = true;
		if (id == null) {
			id = new VarSymbolTableEntry(scanner.CV, relativeAddress, parser.currentSymbolTable,
					false, "real", false);
			parser.currentSymbolTable.addSymbol(id);
			// id = parser.currentSymbolTable.addSymbol(scanner.CV, SymbolTableEntry.VAR,
			// relativeAddress, false, "integer");
			relativeAddress += 4;
			// set the constant
			// add relative address
			makeCode("+", "gd_i_" + BASE, "im_i_" + Integer.toString(id.getAddress()), "gd_i_4");
			// set constant value
			makeCode(":=", "im_f_" + scanner.CV, "gi_f_4");
			notSet = false;
		}
		// push the entry
		pushConst(notSet, id);
	}

    public void cgpushFalse() {
        setBool(false);
    }

    public void cgpushTrue() {
        setBool(true);
    }

	private void setBool(boolean input) {
		// find the constant if not found make an entry
		VarSymbolTableEntry id = (VarSymbolTableEntry) parser.currentSymbolTable.findSymbol(
				scanner.CV, false);
		boolean notSet = true;
		if (id == null) {
			id = new VarSymbolTableEntry(scanner.CV, relativeAddress, parser.currentSymbolTable,
					false, "boolean", false);
			parser.currentSymbolTable.addSymbol(id);
			relativeAddress++;
			// set the constant
			// find address
			makeCode("+", "gd_i_" + BASE, "im_i_" + Integer.toString(id.getAddress()), "gd_i_4");
			// set constant value
			makeCode(":=", "im_b_" + (input ? "true" : "false"), "gi_b_4");
			notSet = false;
		}
		// push the entry
		pushConst(notSet, id);
	}

	private void pushConst(boolean notSet, VarSymbolTableEntry id) {
		if (notSet)
			makeCode("+", "gd_i_" + BASE, "im_i_" + Integer.toString(id.getAddress()), "gd_i_4");
		// push relative address:
		// find the stack pointer
		makeCode(":=sp", "gd_i_8");
		// push relative address:
		makeCode(":=", "gd_i_4", "gi_i_8");
		// increase stack pointer
		makeCode("+sp", "im_i_4");
		pushSS(id);
	}

    public void cgmakeStruct() {
        // todo
    }

    public void cgisVoid() {
        // todo
    }

    public void cgwrite() {
        // find the value of the targeted address
        String type = popFirst();
        if (type.equals("integer"))
            makeCode("wi", "gi_i_8");
        if (type.equals("character"))
            makeCode("wt", "gi_c_8");
        if (type.equals("real"))
            makeCode("wf", "gi_f_8");
        if (type.equals("string"))
            makeCode("wt", "gi_i_8");
        if (type.equals("boolean"))
            makeCode("wi", "gi_i_8");

    }

    public void cgpop() {
        // checkType

        VarSymbolTableEntry popped = (VarSymbolTableEntry) popSS();
        // decrease stack pointer
        makeCode("-sp", "im_i_4");

        freeIfTemp(popped);
    }

    public void cgread() {
        // find the value of the targeted address
        String type = popFirst();
        if (type.equals("integer"))
            makeCode("ri", "gi_i_8");
        if (type.equals("real"))
            makeCode("rf", "gi_f_8");
        if (type.equals("string"))
            readString();
        // codes.add(new Code("rt", new Operand("gi", "i", "8"), null, null));
    }

    private void freeString(int relativeAddress) {
        // if you are changing the code, change the following values accordingly
        int start = getPc() + 1;
        int fm = start + 3;
        int end = start + 7;
        int out = start + 8;
        // start:
        // mem[8] <-- address of string
        makeCode("+", "gd_i_0", "im_i_" + relativeAddress, "gd_i_8");
        // if string is null goto out
        makeCode("jz", "gi_i_8", "im_i_" + out);
        // set string to null: m[m[8]] <-- 0
        makeCode(":=", "im_i_0", "gi_i_8");
        // fm:
        // if we reached end of string goto out
        makeCode("jz", "gi_b_8", "im_i_" + end);
        // free memory m[8]
        makeCode("fmm", "gd_i_8", "im_i_1");
        // ++m[8]
        makeCode("+", "gd_i_8", "im_i_1", "gd_i_8");
        // goto fm
        makeCode("jmp", "im_i_" + fm);
        // end:
        // free last char of the string
        makeCode("fmm", "gd_i_8", "im_i_1");
        // out:
    }

    private void freeString() {
        // if you are changing the code, change the following values accordingly
        int start = getPc() + 1;
        int fm = start + 4;
        int end = start + 8;
        int out = start + 9;
        // start:
        // if string is null goto out
        makeCode("jz", "gi_i_8", "im_i_" + out);
        // set string to null: m[m[8]] <-- 0
        makeCode(":=", "im_i_0", "gi_i_8");
        // fm:
        // if we reached end of string goto out
        makeCode("jz", "gi_b_8", "im_i_" + end);
        // free memory m[8]
        makeCode("fmm", "gd_i_8", "im_i_1");
        // ++m[8]
        makeCode("+", "gd_i_8", "im_i_1", "gd_i_8");
        // goto fm
        makeCode("jmp", "im_i_" + fm);
        // end:
        // free last char of the string
        makeCode("fmm", "gd_i_8", "im_i_1");
        // out:
    }

    private void readString() {
        freeString();
        int start = getPc() + 1;
        int out = start; // todo
        int read_char = start; // todo
        // set size to 0
        makeCode(":=", "im_i_0", "gd_i_" + STRING_SIZE_ADDRESS);
        // read char:
        // m[add] <-- SA + size
        makeCode("+", "im_i_" + STRING_BUFFER_ADDRESS, "gd_i_" + STRING_SIZE_ADDRESS, "gd_i_"
                + STRING_ADDRESS_ADDRESS);
        // read char into m[add]
        makeCode("rt", "gi_c_" + STRING_ADDRESS_ADDRESS);
        // check if read char is not enter
        makeCode("!=", "im_c_13", "gi_c_" + STRING_ADDRESS_ADDRESS, "gd_b_12");
        // if so goto out
        makeCode("jz", "gd_b_12", "im_i_" + out);
        // ++size
        makeCode("+", "im_i_1", "gd_i_" + STRING_SIZE_ADDRESS);
        // goto read char
        makeCode("jmp", "im_i_" + read_char);
        // out:
        // m[add] <-- 0
        makeCode(":=", "im_c_0", "gi_c_" + STRING_ADDRESS_ADDRESS);
        // pop address of string into m[4]
        makeCode("");
    }

    public void cgassignStr() {
        // todo
    }

    public void cgaddStr() {
        parser.structs.add(scanner.previousID);
        // todo
    }

    public void cgaddEnv() {
        // todo
    }

    public void cgassignArr() {
        // todo
    }

    public void cggoForward() {
        // todo
    }

    public void cgarray() {
        // todo
    }

	public void cgmain() {
		Integer jumpCode = (Integer) popSS();
		codes.get(jumpCode).op1 = new Operand("im", "i", (jumpCode + 1) + "");
		makeCode(":=", "im_i_" + STRING_BUFFER_ADDRESS, "gd_i_" + FRAME_POINTER);
		currentFunc = null;
	}

	public void cginitiateFunc() {
		currentFunc = (FunctionSymbolTableEntry) popSS();
		cgstartBlock();
	}

	public void cgcmpFunc() {
		FunctionSymbolTableEntry func = (FunctionSymbolTableEntry) popSS();
		func.args = functionArgs;
		pushSS(func);

	}

	public void cgcmpMain() {

		// cgendBlock();

    }

	public void cgfJump() {
		functionArgs = 8;

        makeCode("jmp");
        pushSS(getPc());
    }

	public void cgcmpFJump() {
		// find return address
		makeCode("-", "gd_i_" + FRAME_POINTER, "im_i_4", "gd_i_" + FRAME_POINTER);
		makeCode(":=", "gd_i_" + FRAME_POINTER, "gd_i_4");
		makeCode("+", "gd_i_" + FRAME_POINTER, "im_i_4", "gd_i_" + FRAME_POINTER);

		// decrease frame pointer
		makeCode("-", "gd_i_" + FRAME_POINTER, "im_i_" + ACTIVATION_RECORD_SIZE, "gd_i_"
				+ FRAME_POINTER);
		// adjust symbol table start
		makeCode(":=", "gd_i_" + FRAME_POINTER, "gd_i_" + BASE);
		makeCode("jmp", "gi_i_4");
		Integer jumpCode = (Integer) popSS();
		codes.get(jumpCode).op1 = new Operand("im", "i", (getPc() + 1) + "");
		cgendBlock();

	}

	public void cgpushF() {
		Integer jumpCode = (Integer) getPc();
		FunctionSymbolTableEntry function = new FunctionSymbolTableEntry(scanner.previousID, -1,
				parser.currentSymbolTable, jumpCode);
		parser.currentSymbolTable.addSymbol(function);

		pushSS(function);
	}

    public void cgassign() {
        String type1 = popFirst();
        String type2 = popSecond();

        if (type1.equals(type2)) {
            if (type1.equals("integer"))
                makeCode(":=", "gi_i_8", "gi_i_12");
            if (type1.equals("boolean"))
                makeCode(":=", "gi_b_8", "gi_b_12");
            if (type1.equals("character"))
                makeCode(":=", "gi_c_8", "gi_c_12");
            if (type1.equals("real"))
                makeCode(":=", "gi_f_8", "gi_f_12");

        } else {
            if (type1.equals("real") && type2.equals("integer")) {
                ftoi("gi_f_8", "gi_i_12");
            } else if (type1.equals("integer") && type2.equals("real")) {
                itof("gi_i_8", "gi_f_12");
            } else if (type2.equals("integer")
                    && (type1.equals("boolean") || type1.equals("character"))) {
                makeCode(":=", "gi_i_8", "gi_i_12");
            } else if (type2.equals("character")
                    && (type1.equals("boolean") || type1.equals("integer"))) {
                makeCode(":=", "gi_c_8", "gi_c_12");
            } else if (type2.equals("boolean")
                    && (type1.equals("integer") || type1.equals("character"))) {
                makeCode(":=", "gi_b_8", "gi_b_12");
            } else {
                // ERROR TODO
            }
        }
    }

    private void ftoi(String input, String output) {
        int start = getPc() + 1;
        int skipSign = start + 17;
        makeCode(":=", input, "gd_f_" + FLOAT_H1);
        makeCode(":=", input, "gd_f_" + FLOAT_H2);
        makeCode(":=", input, "gd_f_" + FLOAT_H3);

        makeCode("&", "im_i_-2147483648", "gd_i_" + FLOAT_H1, "gd_i_" + FLOAT_H2);
        makeCode("<<", "im_i_9", "gd_i_" + FLOAT_H1);
        makeCode("<<", "im_i_1", "gd_i_" + FLOAT_H3);
        makeCode(">>", "im_i_24", "gd_i_" + FLOAT_H3);
        makeCode(">>", "im_i_1", "gd_i_" + FLOAT_H1);
        makeCode("|", "im_i_-2147483648", "gd_i_" + FLOAT_H1, "gd_i_" + FLOAT_H1);
        makeCode("-", "gd_i_" + FLOAT_H3, "im_i_127", "gd_i_" + FLOAT_H3);
        makeCode("-", "im_i_30", "gd_i_" + FLOAT_H3, "gd_i_" + FLOAT_H3);
        makeCode(">>", "gd_i_" + FLOAT_H3, "gd_i_" + FLOAT_H1);
        makeCode("&", "im_i_1", "gd_i_" + FLOAT_H1, "gd_i_" + FLOAT_H4);
        makeCode(">>", "im_i_1", "gd_i_" + FLOAT_H1);
        makeCode("+", "gd_i_" + FLOAT_H1, "gd_i_" + FLOAT_H4, "gd_i_" + FLOAT_H1);
        makeCode("jz", "gd_i_" + FLOAT_H2, "im_i_" + skipSign);
        makeCode("u-", "gd_i_" + FLOAT_H1, "gd_i_" + FLOAT_H1);
        makeCode(":=", "gd_i_" + FLOAT_H1, output);

    }

    private void itof(String input, String output) {
        int start = getPc() + 1;
        int positive = start + 7;
        int end_loop = start + 13;
        int out = start + 21;
        makeCode(":=", input, "gd_i_" + FLOAT_H1); // start
        makeCode("jz", "gd_i_" + FLOAT_H1, "im_i_" + out);
        makeCode(":=", "im_i_0", "gd_i_" + FLOAT_H2);
        makeCode(":=", "im_i_0", "gd_i_" + FLOAT_H3);
        makeCode("<", "gd_i_" + FLOAT_H1, "im_i_0", "gd_b_" + FLOAT_H2);
        makeCode("jz", "gd_b_" + FLOAT_H2, "im_i_" + positive);
        makeCode("u-", "gd_i_" + FLOAT_H1, "gd_i_" + FLOAT_H1);
        makeCode("&", "im_i_-2147483648", "gd_i_" + FLOAT_H1, "gd_i_" + FLOAT_H4);
        makeCode("^", "im_i_-2147483648", "gd_i_" + FLOAT_H4, "gd_i_" + FLOAT_H4);
        makeCode("jz", "gd_i_" + FLOAT_H4, "im_i_" + end_loop);
        makeCode("<<", "im_i_1", "gd_i_" + FLOAT_H1);
        makeCode("+", "im_i_1", "gd_i_" + FLOAT_H3, "gd_i_" + FLOAT_H3);
        makeCode("jmp", "im_i_" + positive);
        makeCode("<<", "im_i_1", "gd_i_" + FLOAT_H1);
        makeCode(">>", "im_i_9", "gd_i_" + FLOAT_H1);
        makeCode("-", "im_i_31", "gd_i_" + FLOAT_H3, "gd_i_" + FLOAT_H3);
        makeCode("+", "im_i_127", "gd_i_" + FLOAT_H3, "gd_i_" + FLOAT_H3);
        makeCode("<<", "im_i_23", "gd_i_" + FLOAT_H3);
        makeCode("<<", "im_i_31", "gd_i_" + FLOAT_H2);
        makeCode("|", "gd_i_" + FLOAT_H1, "gd_i_" + FLOAT_H2, "gd_i_" + FLOAT_H1);
        makeCode("|", "gd_i_" + FLOAT_H1, "gd_i_" + FLOAT_H3, "gd_i_" + FLOAT_H1);
        makeCode(":=", "gd_f_" + FLOAT_H1, output);
    }

	public void cgpush() {
		SymbolTableEntry id = parser.currentSymbolTable.findSymbol(scanner.previousID, true);
		if (id == null) {
			// it might be a function argument
			if (currentFunc != null) {
				for (String name : currentFunc.argNames) {
					if (name.equals(scanner.previousID)) {
						int index = currentFunc.argNames.indexOf(name);
						VarSymbolTableEntry var = new VarSymbolTableEntry(name,
								currentFunc.argAddress.get(index), parser.currentSymbolTable,
								false, currentFunc.argTypes.get(index), false);
						// find address:
						makeCode("-", "gd_i_" + BASE,
								"im_i_" + Integer.toString(currentFunc.argAddress.get(index)),
								"gd_i_4");
						// find the stack pointer
						makeCode(":=sp", "gd_i_8");
						// push address:
						makeCode(":=", "gd_i_4", "gi_i_8");
						// increase stack pointer
						makeCode("+sp", "im_i_4");
						// reset the frame pointer if global
						pushSS(var);
						return;
					}
				}
			}
		} else {
			// if global should set global address!
			VarSymbolTableEntry var = null;
			if (id.getClass() == VarSymbolTableEntry.class) {
				var = (VarSymbolTableEntry) id;
				if (var.isGlobal)
					makeCode(":=", "gd_i_" + HEAP_POINTER, "gd_i_" + BASE);
			}
			// find address:
			makeCode("+", "gd_i_" + BASE, "im_i_" + Integer.toString(id.getAddress()), "gd_i_4");
			// find the stack pointer
			makeCode(":=sp", "gd_i_8");
			// push address:
			makeCode(":=", "gd_i_4", "gi_i_8");
			// increase stack pointer
			makeCode("+sp", "im_i_4");
			// reset the frame pointer if global
			if (var != null && var.isGlobal) {
				makeCode(":=", "gd_i_" + FRAME_POINTER, "gd_i_" + BASE);
			}
			pushSS(id);
		}
	}

    public void cgaDscp() {
        // todo
    }

	private void pushCurrent(String type) {
		// find the stack pointer
		makeCode(":=sp", "gd_i_8");
		// push relative address:
		makeCode(":=", "gd_i_4", "gi_i_8");
		// increase stack pointer
		makeCode("+sp", "im_i_4");
		pushSS(new VarSymbolTableEntry("temp", -1, null, true, type, false));
	}

	private void init() {
		makeCode("gmm", "im_i_1024", "gd_i_" + HEAP_POINTER);

	}

    private void binary(String operand) {
        if (isLogicalOperator(operand)) {
            logicalBinary(operand);
        } else if (isArithmeticOperator(operand)) {
            arithmeticBinary(operand);
        } else if (isBitwiseOperator(operand)) {
            bitwiseBinary(operand);
        } else {
            throw new RuntimeException("invalid Operator");
        }
    }

    private void bitwiseBinary(String operand) {
        // condition change if cast!
        if (popFirst().equals("integer") && popSecond().equals("integer")) {
            makeCode("gmm", "im_i_4", "gd_i_4");
            makeCode(operand, "gi_i_12", "gi_i_8", "gi_i_4");
            this.pushCurrent("integer");
        } else {
            throw new RuntimeException("invalid arguments");
        }
    }

    private void arithmeticBinary(String operand) {
        String type1 = popFirst();
        String type2 = popSecond();
        String outputType = "i";

        if (!(isGoodType(type2) && isGoodType(type1)))
            throw new RuntimeException("invalid argument types");
        if (type1.equals("real") || type2.equals("real"))
            outputType = "f";
        if (!type1.equals(type2)) {
            if (isBigger(type1, type2)) {
                itof("gi_i_12", "gd_f_" + CASTED);
                makeCode(":=", "im_i_" + CASTED, "gd_i_12");
            } else if (isBigger(type2, type1)) {
                itof("gi_i_8", "gd_f_" + CASTED);
                makeCode(":=", "im_i_" + CASTED, "gd_i_8");
            }
        }
        makeCode("gmm", "im_i_4", "gd_i_4");
        makeCode(operand, "gi_" + outputType + "_12", "gi_" + outputType + "_8", "gi_i_4");
        this.pushCurrent(outputType.equals("f") ? "real" : "integer");

    }

    private void logicalBinary(String operand) {
        String type1 = popFirst();
        String type2 = popSecond();
        String outputType = "i";

        if (!(isGoodType(type2) && isGoodType(type1)))
            throw new RuntimeException("invalid argument types");
        if (type1.equals("real") || type2.equals("real"))
            outputType = "f";
        if (!type1.equals(type2)) {
            if (isBigger(type1, type2)) {
                itof("gi_i_12", "gd_f_" + CASTED);
                makeCode(":=", "im_i_" + CASTED, "gd_i_12");
            } else if (isBigger(type2, type1)) {
                itof("gi_i_8", "gd_f_" + CASTED);
                makeCode(":=", "im_i_" + CASTED, "gd_i_8");
            }
        }
        makeCode("gmm", "im_i_4", "gd_i_4");
        makeCode(operand, "gi_" + outputType + "_12", "gi_" + outputType + "_8", "gi_b_4");
        this.pushCurrent("boolean");

    }

    private boolean isGoodType(String type) {
        for (String s : goodTypes)
            if (s.equals(type))
                return true;
        return false;
    }

    private boolean isBigger(String type1, String type2) {
        return type1.equals("real");
    }

    private void unary(String operand) {
        String type = popFirst();
        if (operand.equals("u-")) {
            if (type.equals("integer")) {
                // get a temp
                makeCode("gmm", "im_i_4", "gd_i_4");
                // unary this and set currentValue
                makeCode(operand, "gi_i_8", "gi_i_4");
                // push currentValue
                this.pushCurrent("integer");
            } else if (type.equals("real")) {
                // get a temp
                makeCode("gmm", "im_i_4", "gd_i_4");
                // unary this and set currentValue
                makeCode(operand, "gi_f_8", "gi_f_4");
                // push currentValue
                this.pushCurrent("real");
            } else {
                throw new RuntimeException();
            }
        } else if (operand.equals("!")) {
            if (type.equals("boolean")) {
                // get a temp
                makeCode("gmm", "im_i_1", "gd_i_4");
                // unary this and set currentValue
                makeCode(operand, "gi_b_8", "gi_b_4");
                // push currentValue
                this.pushCurrent("boolean");
            } else {
                throw new RuntimeException();
            }
        } else if (operand.equals("~")) {
            if (type.equals("integer")) {
                // get a temp
                makeCode("gmm", "im_i_4", "gd_i_4");
                // unary this and set currentValue
                makeCode(operand, "gi_i_8", "gi_i_4");
                // push currentValue
                this.pushCurrent("integer");
            } else {
                throw new RuntimeException();
            }
        }
    }

    private String popFirst() {
        // checkType
        VarSymbolTableEntry popped = (VarSymbolTableEntry) popSS();
        // decrease stack pointer
        makeCode("-sp", "im_i_4");
        // find the stack pointer
        makeCode(":=sp", "gd_i_4");
        // pop operand
        makeCode(":=", "gi_i_4", "gd_i_8");

        freeIfTemp(popped);
        return popped.type;
    }

    private void freeIfTemp(VarSymbolTableEntry popped) {
        if (popped.val)
            makeCode("fmm", "gd_i_8", "im_i_4");
    }

    private String popSecond() {
        // checkType
        VarSymbolTableEntry popped = (VarSymbolTableEntry) popSS();
        // decrease stack pointer
        makeCode("-sp", "im_i_4");
        // find the stack pointer
        makeCode(":=sp", "gd_i_4");
        // pop operand
        makeCode(":=", "gi_i_4", "gd_i_12");
        // freeIfTemp();
        freeIfTemp2(popped);
        return popped.type;
    }

    private void freeIfTemp2(VarSymbolTableEntry popped) {
        if (popped.val)
            makeCode("fmm", "gd_i_12", "im_i_4");
    }

    private boolean isLogicalOperator(String op) {
        for (String s : logicalOperators)
            if (s.equals(op))
                return true;
        return false;
    }

    private boolean isBitwiseOperator(String op) {
        for (String s : bitwiseOperators)
            if (s.equals(op))
                return true;
        return false;
    }

    private boolean isArithmeticOperator(String op) {
        for (String s : arithmeticOperators)
            if (s.equals(op))
                return true;
        return false;
    }

	public void FinishCode() // You may need this
	{
		makeCode("fmm", "gd_i_" + HEAP_POINTER, "im_i_1024");
	}

    public void WriteOutput(String outputName) {
        // Can be used to print the generated code to output
        // I used this because in the process of compiling, I stored the
        // generated code in a structure
        // If you want, you can output a code line just when it is generated
        // (strongly NOT recommended!!)
        String output = "";
        try {
            PrintWriter writer = new PrintWriter(new File(outputName), "UTF-8");
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
