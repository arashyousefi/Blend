import symbolTableEntry.LabelSymbolTableEntry;
import symbolTableEntry.SymbolTableEntry;

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
    Scanner scanner; // This was my way of informing CG about Constant Values
    // detected by Scanner, you can do whatever you like

    // Define any variables needed for code generation
    Parser parser;
    ArrayList<Object> ss = new ArrayList<>();
    ArrayList<Code> codes = new ArrayList<>();
    int relativeAddress = 0;
    String type;
    Code previousIf;

	ArrayList<CaseLink> firstLinks = new ArrayList<CaseLink>();

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

    public void Generate(String sem) {
//        System.err.println(sem); // Just for debug
        if (sem.equals("NoSem"))
            return;

        java.lang.reflect.Method method;
        try {
            method = this.getClass().getMethod("cg" + sem.substring(1));
            try {
                method.invoke(this);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                //todo fail
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                //todo fail
            } catch (InvocationTargetException e) {
                //todo fail
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            //todo fail
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            //todo fail
        }

        return;
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
        //todo
    }

    public void cgmake() {
        parser.currentSymbolTable.addSymbol(scanner.previousID, SymbolTableEntry.VAR,
                relativeAddress, false, type);
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
    }

    public void cgaddArg() {
        // TODO

    }

    public void cgmakeLate() {
        // TODO

    }

    public void cgmakeOut() {
        // TODO

    }

    public void cgstartBlock() {
        // TODO
    }

    public void cgendBlock() {
        // TODO
    }

    public void cgreleaseStr() {
        // TODO

    }

    public void cgbreak() {
        // TODO

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
            if (labelSymbolTableEntry.address == -1) { // just add it to the references
//                System.err.println("goto without address " + id);
                labelSymbolTableEntry.references.add(getPc());
            } else { // we know the address
//                System.err.println("goto with address " + id);
                gotoCode.op1 = new Operand("im", "i", labelSymbolTableEntry.address.toString());
            }
        } else { // we have to create it now
//            System.err.println("new goto without address " + id);
            LabelSymbolTableEntry labelSymbolTableEntry = new LabelSymbolTableEntry(id);
            labelSymbolTableEntry.references.add(getPc());
            parser.currentSymbolTable.addSymbol(labelSymbolTableEntry);
        }
    }

    public void cgmakeLabel() {
        String id = scanner.previousID;
        SymbolTableEntry symbolTableEntry = parser.currentSymbolTable.findSymbol(id, false);
        if (symbolTableEntry == null) { // just create, no need to do anything
//            System.err.println("new label " + id);
            LabelSymbolTableEntry labelSymbolTableEntry = new LabelSymbolTableEntry(id);
            labelSymbolTableEntry.address = getPc() + 1;
            parser.currentSymbolTable.addSymbol(labelSymbolTableEntry);

        } else {
//            System.err.println("label " + id);
            LabelSymbolTableEntry labelSymbolTableEntry = (LabelSymbolTableEntry) symbolTableEntry;
            labelSymbolTableEntry.address = getPc() + 1;
            for (int i : labelSymbolTableEntry.references) {
//                System.err.println("fixed code " + id + " " + i);
                codes.get(i).op1 = new Operand("im", "i",
                        labelSymbolTableEntry.address.toString());
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
        codes.add(new Code("jz", new Operand("gi", "b", "8")));
        pushSS(getPc());
    }

    public void cgcmpJz() {
        int pc = (Integer) popSS();
        previousIf = codes.get(pc);
        previousIf.op2 = new Operand("im", "i", Integer.toString(getPc() + 1));
    }

    public void cgjpCmpJz() {
        previousIf.op2 = new Operand("im", "i", Integer.toString(getPc() + 2));

        codes.add(new Code("jmp"));
        pushSS(getPc());

    }

    public void cgcmpJp() {
        int lastJump = (Integer) popSS();
        Code jumpCode = codes.get(lastJump);
        jumpCode.op1 = new Operand("im", "i", Integer.toString(codes.size()));
    }

    public void cgwhileJpCjz() {
        int whileLine = (Integer) popSS();
        Code whileCode = codes.get(whileLine);
        whileCode.op2 = new Operand("im", "i", Integer.toString(getPc() + 2));

        Integer whileEvalLine = (Integer) popSS();

        codes.add(new Code("jmp", new Operand("im", "i", whileEvalLine.toString())));

    }

	public void cgpushPC() {
		pushSS(getPc() + 1);
	}

    public void cgdoJz() { // it should be doJnz
        popFirst();
        Integer doLine = (Integer) popSS();
//        codes.add(new Code("!", new Operand("gi", "b", "8"), new Operand("gi", "b", "8")));
        makeCode("!", "gi_b_8", "gi_b_8");
        // generate the jump Code
//        codes.add(new Code("jz", new Operand("gi", "b", "8"),
//                new Operand("im", "i", doLine.toString())));
        makeCode("jz", "gi_b_8", "im_i_" + doLine.toString());
    }

	public void cgcaseJump() {
		popFirst();
		codes.add(new Code("-", new Operand("gi", "i", "8"), null, new Operand("gd", "i", "12")));
		pushSS(getPc());
		codes.add(new Code("+", new Operand("gd", "i", "12"), null, new Operand("gd", "i", "12")));
		pushSS(getPc());
		codes.add(new Code("jmp", new Operand("gd", "i", "12")));
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
		codes.add(new Code("jmp"));
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
		for (int i = min.value; i <= max.value; ++i) {
			codes.add(new Code("jmp", new Operand("im", "i", out.toString())));
		}
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
        //todo
    }

    public void cgcall() {
        //todo
    }

    public void cgmakeConstInteger() {
        // find the constant if not found make an entry
        SymbolTableEntry id = parser.currentSymbolTable.findSymbol(scanner.CV, true);
        boolean notSet = true;
        if (id == null) {
            id = parser.currentSymbolTable.addSymbol(scanner.CV, SymbolTableEntry.VAR,
                    relativeAddress, false, "integer");
            relativeAddress += 4;
            // set the constant
            codes.add(new Code(":=", new Operand("gd", "i", "0"), new Operand("gd", "i", "4"),
                    null));
            // add relative address
            codes.add(new Code("+", new Operand("gd", "i", "4"), new Operand("im", "i",
                    Integer.toString(id.address)), new Operand("gd", "i", "4")));
            // set constant value
            codes.add(new Code(":=", new Operand("im", "i", scanner.CV), new Operand("gi",
                    "i", "4"), null));
            notSet = false;
        }
        // push the entry
        // get heap address:
        if (notSet) {
            codes.add(new Code(":=", new Operand("gd", "i", "0"), new Operand("gd", "i", "4"),
                    null));
            // add relative address
            codes.add(new Code("+", new Operand("gd", "i", "4"), new Operand("im", "i",
                    Integer.toString(id.address)), new Operand("gd", "i", "4")));
        }
        // push relative address:
        // find the stack pointer
        codes.add(new Code(":=sp", new Operand("gd", "i", "8"), null, null));
        // push relative address:
        codes.add(new Code(":=", new Operand("gd", "i", "4"), new Operand("gi", "i", "8"),
                null));
        // increase stack pointer
        codes.add(new Code("+", new Operand("gd", "i", "8"), new Operand("im", "i", "4"),
                new Operand("gd", "i", "8")));
        // set stack pointer
        codes.add(new Code("sp:=", new Operand("gd", "i", "8"), null, null));
        pushSS(id);
    }

    public void cgmakeConstCharacter() {
        //todo
    }

    public void cgmakeConstString() {
        //todo
    }

    public void cgmakeConstReal() {
        //todo
    }

    public void cgpushFalse() {
        //todo
    }

    public void cgpushTrue() {
        //todo
    }

    public void cgmakeStruct() {
        //todo
    }

    public void cgisVoid() {
        //todo
    }

    public void cgwrite() {
        // find the value of the targeted address
        String type = popFirst();
        if (type.equals("integer"))
            codes.add(new Code("wi", new Operand("gi", "i", "8"), null, null));
        if (type.equals("real"))
            codes.add(new Code("wf", new Operand("gi", "i", "8"), null, null));
        if (type.equals("string"))
            codes.add(new Code("wt", new Operand("gi", "i", "8"), null, null));
    }

	public void cgpop() {
		// checkType
		SymbolTableEntry popped = (SymbolTableEntry) popSS();
		// find the stack pointer
		codes.add(new Code(":=sp", new Operand("gd", "i", "4")));
		// decrease stack pointer
		codes.add(new Code("-", new Operand("gd", "i", "4"), new Operand("im", "i", "4"),
				new Operand("gd", "i", "4")));
		// set stack pointer
		codes.add(new Code("sp:=", new Operand("gd", "i", "4")));
		// find the stack pointer

		freeIfTemp(popped);
	}

    public void cgread() {
        // find the value of the targeted address
        String type = popFirst();
        if (type.equals("integer"))
            codes.add(new Code("ri", new Operand("gi", "i", "8"), null, null));
        if (type.equals("real"))
            codes.add(new Code("rf", new Operand("gi", "i", "8"), null, null));
        if (type.equals("string"))
            readString();
//            codes.add(new Code("rt", new Operand("gi", "i", "8"), null, null));
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
        makeCode("+", "im_i_" + STRING_BUFFER_ADDRESS, "gd_i_" + STRING_SIZE_ADDRESS, "gd_i_" +
                STRING_ADDRESS_ADDRESS);
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
        //todo
    }

    public void cgaddstr() {
        parser.structs.add(scanner.previousID);
        //todo
    }

    public void cgaddEnv() {
        //todo
    }

    public void cgassignArr() {
        //todo
    }

	public void cggoForward() {
		// todo
	}

	public void cgarray() {
		// todo
	}

    public void cgmain() {
        //todo
    }

    public void cgcmpFunc() {
        //todo
    }

    public void cgassign() {
        if (popFirst().equals(popSecond())) {
            codes.add(new Code(":=", new Operand("gi", "i", "8"),
                    new Operand("gi", "i", "12"), null));
        } else {
            // TODO ERROR or cast if possible
        }
    }

    public void cgpush() {
        SymbolTableEntry id = parser.currentSymbolTable.findSymbol(scanner.previousID, true);
        if (id == null) {
            // TODO runtime exception here!
        } else {
            // get heap address:
            codes.add(new Code(":=", new Operand("gd", "i", "0"), new Operand("gd", "i", "4"),
                    null));
            // add relative address
            codes.add(new Code("+", new Operand("gd", "i", "4"), new Operand("im", "i",
                    Integer.toString(id.address)), new Operand("gd", "i", "4")));
            // find the stack pointer
            codes.add(new Code(":=sp", new Operand("gd", "i", "8"), null, null));
            // push address:
            codes.add(new Code(":=", new Operand("gd", "i", "4"), new Operand("gi", "i", "8"),
                    null));
            // increase stack pointer
            codes.add(new Code("+", new Operand("gd", "i", "8"), new Operand("im", "i", "4"),
                    new Operand("gd", "i", "8")));
            // set stack pointer
            codes.add(new Code("sp:=", new Operand("gd", "i", "8"), null, null));
            pushSS(id);
        }
    }

    public void cgaDscp() {
        //todo
    }

	private void pushCurrent(String type) {
		// find the stack pointer
		codes.add(new Code(":=sp", new Operand("gd", "i", "8"), null, null));
		// push relative address:
		codes.add(new Code(":=", new Operand("gd", "i", "4"), new Operand("gi", "i", "8"), null));
		// increase stack pointer
		codes.add(new Code("+", new Operand("gd", "i", "8"), new Operand("im", "i", "4"),
				new Operand("gd", "i", "8")));
		// set stack pointer
		codes.add(new Code("sp:=", new Operand("gd", "i", "8"), null, null));
		pushSS(new SymbolTableEntry("temp", 1, 0, true, type));
	}

    private void init() {

        codes.add(new Code("gmm", new Operand("im", "i", "1024"), new Operand("gd", "i", "0"),
                null));

    }

    private void binary(String operand) {
        // condition change if cast!
        if (popFirst().equals("integer") && popSecond().equals("integer")) {
            // get a temp
            codes.add(new Code("gmm", new Operand("im", "i", "4"), new Operand("gd", "i", "4"),
                    null));
            // binary this and push
            codes.add(new Code(operand, new Operand("gi", "i", "12"), new Operand("gi", "i", "8"),
                    new Operand("gi", "i", "4")));
            this.pushCurrent("integer");
        } else {
            // TODO ERROR!
        }
    }

    private void unary(String operand) {

        if (popFirst().equals("integer")) {
            // get a temp
            codes.add(new Code("gmm", new Operand("im", "i", "4"), new Operand("gd", "i", "4"),
                    null));
            // unary this and set currentValue
            codes.add(new Code(operand, new Operand("gi", "i", "8"), new Operand("gi", "i", "4"),
                    null));
            // push currentValue
            this.pushCurrent("integer");
        } else {
            // TODO ERROR!
        }
    }

    private String popFirst() {
        // checkType
        SymbolTableEntry popped = (SymbolTableEntry) popSS();
        // find the stack pointer
        codes.add(new Code(":=sp", new Operand("gd", "i", "4")));
        // decrease stack pointer
        codes.add(new Code("-", new Operand("gd", "i", "4"), new Operand("im", "i", "4"),
                new Operand("gd", "i", "4")));
        // set stack pointer
        codes.add(new Code("sp:=", new Operand("gd", "i", "4")));
        // find the stack pointer
        codes.add(new Code(":=sp", new Operand("gd", "i", "4")));
        // pop operand
        codes.add(new Code(":=", new Operand("gi", "i", "4"), new Operand("gd", "i", "8")));

        freeIfTemp(popped);
        return popped.type;
    }

    private void freeIfTemp(SymbolTableEntry popped) {
        if (popped.isValue)
            codes.add(new Code("fmm", new Operand("gd", "i", "8"), new Operand("im", "i", "4")));
    }

    private String popSecond() {
        // checkType
        SymbolTableEntry popped = (SymbolTableEntry) popSS();
        // find the stack pointer
        codes.add(new Code(":=sp", new Operand("gd", "i", "4")));
        // decrease stack pointer
        codes.add(new Code("-", new Operand("gd", "i", "4"), new Operand("im", "i", "4"),
                new Operand("gd", "i", "4")));
        // set stack pointer
        codes.add(new Code("sp:=", new Operand("gd", "i", "4")));
        // find the stack pointer
        codes.add(new Code(":=sp", new Operand("gd", "i", "4")));
        // pop first operand
        codes.add(new Code(":=", new Operand("gi", "i", "4"), new Operand("gd", "i", "12")));
        // freeIfTemp();
        freeIfTemp2(popped);
        return popped.type;
    }

    private void freeIfTemp2(SymbolTableEntry popped) {
        if (popped.isValue)
            codes.add(new Code("fmm", new Operand("gd", "i", "12"), new Operand("im", "i", "4")));
    }

    public void FinishCode() // You may need this
    {
        codes.add(new Code("fmm", new Operand("gd", "i", "0"), new Operand("im", "i", "1024")));
    }

    public void WriteOutput(String outputName) {
        // Can be used to print the generated code to output
        // I used this because in the process of compiling, I stored the
        // generated code in a structure
        // If you want, you can output a code line just when it is generated
        // (strongly NOT recommended!!)
        String output = "";
        try {
            PrintWriter writer = new PrintWriter(new File("sampleProgram.out"), "UTF-8");
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
