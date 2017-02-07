import java.io.FileInputStream;
import java.io.InputStream;

/**
 * This is the template of class 'scanner'. You should place your own 'scanner class here and
 * your scanner should match this interface.
 */
public class Scanner {
    public String CV;
    public String previousID;
    int lineNumber = 1;
    Parser parser;
    private FlexScanner flexScanner;
    private InputStream inputStream;
  
    Scanner(String filename, Parser parser) throws Exception {
        this.parser = parser;
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
//        System.err.println(String.format("flex scanner output: entryType: %s id: %s\nline " +
//                        "number: %d",
//                token.type, token.id, lineNumber));
        if (token.type.equals("STRING") ||
                token.type.equals("idINT") ||
                token.type.equals("idHEX") ||
                token.type.equals("idREAL") ||
                token.type.equals("idCHAR")) {
//            System.err.println(String.format("constant detected entryType: %s value: %s", token
//                            .type,
//                    token.id));
            CV = token.id;
//            System.err.println("return value is: " + token.type);
            return token.type;
        }
        if (token.type.equals("idID")) {
//            System.err.println("return value is: " + "id");
            previousID = token.id;
            for (String s : parser.structs) {
                if (s.equals(token.id)) {
                    parser.lastStruct = token.id;
//                    System.err.println("Struct detected: " + token.id);
//                    System.err.println("return value is: structType");
                    return "structType";
                }
            }
            return "id";
        }
//        System.err.println("return value is: " + token.id);
        return token.id;
    }

}
