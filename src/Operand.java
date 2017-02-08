public class Operand {
    String addressMode, type, value;

    public Operand(String addressMode, String type, String value) {
        this.addressMode = addressMode;
        this.type = type;
        this.value = value;
    }

    public Operand(String op) {
        String[] args = op.split("_");
        this.addressMode = args[0];
        this.type = args[1];
        this.value = args[2];
    }

    public String getText() {
        return addressMode + "_" + type + "_" + value;
    }
}
