import java.io.FileInputStream;
import java.io.InputStream;

/**
 * This is the template of class 'scanner'. You should place your own 'scanner class here and
 * your scanner should match this interface.
 */
public class Scanner {
    public String CV;
    int lineNumber = 1;
    private FlexScanner flexScanner;
    private InputStream inputStream;

    Scanner(String filename) throws Exception {
        inputStream = new FileInputStream(filename);
        flexScanner = new FlexScanner(inputStream);
//        File f = new File(filename);
//        if (!f.exists())
//            throw new Exception("File does not exist: " + f);
//        if (!f.isFile())
//            throw new Exception("Should not be a directory: " + f);
//        if (!f.canRead())
//            throw new Exception("Can not read input file: " + f);
//        // ...
    }

    public int getLineNumber() {
        return (lineNumber = flexScanner.lineNumber());
    }

    public String NextToken() throws Exception {
        Token token = flexScanner.NextToken();
        lineNumber = flexScanner.lineNumber();
        System.out.println(String.format("flex scanner output: type: %s id: %s\nline number: %d",
                token.type, token.id, lineNumber));
        if (token.type.equals("STRING") ||
                token.type.equals("idINT") ||
                token.type.equals("idHEX") ||
                token.type.equals("idREAL") ||
                token.type.equals("idCHAR")) {
            System.out.println(String.format("constant detected type: %s value: %s", token.type,
                    token.id));
            CV = token.id;
            System.out.println("return value is: " + token.type);
            return token.type;
        }
        if (token.type.equals("idID")) {
            System.out.println("return value is: " + "id");
            return "id";
        }
        System.out.println("return value is: " + token.id);
        return token.id;
    }

}
