package symbolTableEntry;

public class Operand {
	String addressMode, type, value;

	public Operand(String addressMode, String type, String value) {
		this.addressMode = addressMode;
		this.type = type;
		this.value = value;
	}

	public String getText() {
		return addressMode + "_" + type + "_" + value;
	}
}
