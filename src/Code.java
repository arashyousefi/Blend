
public class Code {
	String opcode;
	Operand op1, op2, op3;

	public Code(String opcode, Operand op1, Operand op2, Operand op3) {
		this.op1 = op1;
		this.op2 = op2;
		this.op3 = op3;
		this.opcode = opcode;
	}

	public String getText() {
		String text1 = "", text2 = "", text3 = "";
		if (op1 != null)
			text1 = op1.getText();
		if (op2 != null)
			text2 = op2.getText();
		if (op3 != null)
			text3 = op3.getText();

		return opcode + " " + text1 + " " + text2 + " " + text3 + "\n";
	}
}
