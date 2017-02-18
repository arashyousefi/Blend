/* The following code was generated by JFlex 1.4.3 on 2/18/17 12:51 PM */

/* JFlex example: partial Java language lexer specification */



/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 2/18/17 12:51 PM from the specification file
 * <tt>scanner.flex</tt>
 */
public class FlexScanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int STRING = 2;
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\13\1\3\1\2\1\0\1\3\1\1\16\13\4\0\1\3\1\54"+
    "\1\45\1\0\1\11\1\51\1\52\1\21\1\46\1\46\1\10\1\51"+
    "\1\46\1\5\1\20\1\7\1\15\11\14\1\47\1\46\1\4\1\50"+
    "\1\6\2\0\32\11\1\46\1\55\1\46\1\51\1\11\1\0\1\22"+
    "\1\31\1\36\1\42\1\34\1\17\1\27\1\37\1\26\1\11\1\35"+
    "\1\33\1\43\1\30\1\32\2\11\1\23\1\25\1\40\1\41\1\12"+
    "\1\44\1\16\1\24\1\11\1\46\1\53\1\46\1\51\41\13\2\0"+
    "\4\11\4\0\1\11\2\0\1\13\7\0\1\11\4\0\1\11\5\0"+
    "\27\11\1\0\37\11\1\0\u01ca\11\4\0\14\11\16\0\5\11\7\0"+
    "\1\11\1\0\1\11\21\0\160\13\5\11\1\0\2\11\2\0\4\11"+
    "\10\0\1\11\1\0\3\11\1\0\1\11\1\0\24\11\1\0\123\11"+
    "\1\0\213\11\1\0\5\13\2\0\236\11\11\0\46\11\2\0\1\11"+
    "\7\0\47\11\7\0\1\11\1\0\55\13\1\0\1\13\1\0\2\13"+
    "\1\0\2\13\1\0\1\13\10\0\33\11\5\0\3\11\15\0\5\13"+
    "\6\0\1\11\4\0\13\13\5\0\53\11\37\13\4\0\2\11\1\13"+
    "\143\11\1\0\1\11\10\13\1\0\6\13\2\11\2\13\1\0\4\13"+
    "\2\11\12\13\3\11\2\0\1\11\17\0\1\13\1\11\1\13\36\11"+
    "\33\13\2\0\131\11\13\13\1\11\16\0\12\13\41\11\11\13\2\11"+
    "\4\0\1\11\5\0\26\11\4\13\1\11\11\13\1\11\3\13\1\11"+
    "\5\13\22\0\31\11\3\13\104\0\1\11\1\0\13\11\67\0\33\13"+
    "\1\0\4\13\66\11\3\13\1\11\22\13\1\11\7\13\12\11\2\13"+
    "\2\0\12\13\1\0\7\11\1\0\7\11\1\0\3\13\1\0\10\11"+
    "\2\0\2\11\2\0\26\11\1\0\7\11\1\0\1\11\3\0\4\11"+
    "\2\0\1\13\1\11\7\13\2\0\2\13\2\0\3\13\1\11\10\0"+
    "\1\13\4\0\2\11\1\0\3\11\2\13\2\0\12\13\4\11\7\0"+
    "\1\11\5\0\3\13\1\0\6\11\4\0\2\11\2\0\26\11\1\0"+
    "\7\11\1\0\2\11\1\0\2\11\1\0\2\11\2\0\1\13\1\0"+
    "\5\13\4\0\2\13\2\0\3\13\3\0\1\13\7\0\4\11\1\0"+
    "\1\11\7\0\14\13\3\11\1\13\13\0\3\13\1\0\11\11\1\0"+
    "\3\11\1\0\26\11\1\0\7\11\1\0\2\11\1\0\5\11\2\0"+
    "\1\13\1\11\10\13\1\0\3\13\1\0\3\13\2\0\1\11\17\0"+
    "\2\11\2\13\2\0\12\13\1\0\1\11\17\0\3\13\1\0\10\11"+
    "\2\0\2\11\2\0\26\11\1\0\7\11\1\0\2\11\1\0\5\11"+
    "\2\0\1\13\1\11\7\13\2\0\2\13\2\0\3\13\10\0\2\13"+
    "\4\0\2\11\1\0\3\11\2\13\2\0\12\13\1\0\1\11\20\0"+
    "\1\13\1\11\1\0\6\11\3\0\3\11\1\0\4\11\3\0\2\11"+
    "\1\0\1\11\1\0\2\11\3\0\2\11\3\0\3\11\3\0\14\11"+
    "\4\0\5\13\3\0\3\13\1\0\4\13\2\0\1\11\6\0\1\13"+
    "\16\0\12\13\11\0\1\11\7\0\3\13\1\0\10\11\1\0\3\11"+
    "\1\0\27\11\1\0\12\11\1\0\5\11\3\0\1\11\7\13\1\0"+
    "\3\13\1\0\4\13\7\0\2\13\1\0\2\11\6\0\2\11\2\13"+
    "\2\0\12\13\22\0\2\13\1\0\10\11\1\0\3\11\1\0\27\11"+
    "\1\0\12\11\1\0\5\11\2\0\1\13\1\11\7\13\1\0\3\13"+
    "\1\0\4\13\7\0\2\13\7\0\1\11\1\0\2\11\2\13\2\0"+
    "\12\13\1\0\2\11\17\0\2\13\1\0\10\11\1\0\3\11\1\0"+
    "\51\11\2\0\1\11\7\13\1\0\3\13\1\0\4\13\1\11\10\0"+
    "\1\13\10\0\2\11\2\13\2\0\12\13\12\0\6\11\2\0\2\13"+
    "\1\0\22\11\3\0\30\11\1\0\11\11\1\0\1\11\2\0\7\11"+
    "\3\0\1\13\4\0\6\13\1\0\1\13\1\0\10\13\22\0\2\13"+
    "\15\0\60\11\1\13\2\11\7\13\4\0\10\11\10\13\1\0\12\13"+
    "\47\0\2\11\1\0\1\11\2\0\2\11\1\0\1\11\2\0\1\11"+
    "\6\0\4\11\1\0\7\11\1\0\3\11\1\0\1\11\1\0\1\11"+
    "\2\0\2\11\1\0\4\11\1\13\2\11\6\13\1\0\2\13\1\11"+
    "\2\0\5\11\1\0\1\11\1\0\6\13\2\0\12\13\2\0\4\11"+
    "\40\0\1\11\27\0\2\13\6\0\12\13\13\0\1\13\1\0\1\13"+
    "\1\0\1\13\4\0\2\13\10\11\1\0\44\11\4\0\24\13\1\0"+
    "\2\13\5\11\13\13\1\0\44\13\11\0\1\13\71\0\53\11\24\13"+
    "\1\11\12\13\6\0\6\11\4\13\4\11\3\13\1\11\3\13\2\11"+
    "\7\13\3\11\4\13\15\11\14\13\1\11\17\13\2\0\46\11\1\0"+
    "\1\11\5\0\1\11\2\0\53\11\1\0\u014d\11\1\0\4\11\2\0"+
    "\7\11\1\0\1\11\1\0\4\11\2\0\51\11\1\0\4\11\2\0"+
    "\41\11\1\0\4\11\2\0\7\11\1\0\1\11\1\0\4\11\2\0"+
    "\17\11\1\0\71\11\1\0\4\11\2\0\103\11\2\0\3\13\40\0"+
    "\20\11\20\0\125\11\14\0\u026c\11\2\0\21\11\1\0\32\11\5\0"+
    "\113\11\3\0\3\11\17\0\15\11\1\0\4\11\3\13\13\0\22\11"+
    "\3\13\13\0\22\11\2\13\14\0\15\11\1\0\3\11\1\0\2\13"+
    "\14\0\64\11\40\13\3\0\1\11\3\0\2\11\1\13\2\0\12\13"+
    "\41\0\3\13\2\0\12\13\6\0\130\11\10\0\51\11\1\13\1\11"+
    "\5\0\106\11\12\0\35\11\3\0\14\13\4\0\14\13\12\0\12\13"+
    "\36\11\2\0\5\11\13\0\54\11\4\0\21\13\7\11\2\13\6\0"+
    "\12\13\46\0\27\11\5\13\4\0\65\11\12\13\1\0\35\13\2\0"+
    "\13\13\6\0\12\13\15\0\1\11\130\0\5\13\57\11\21\13\7\11"+
    "\4\0\12\13\21\0\11\13\14\0\3\13\36\11\15\13\2\11\12\13"+
    "\54\11\16\13\14\0\44\11\24\13\10\0\12\13\3\0\3\11\12\13"+
    "\44\11\122\0\3\13\1\0\25\13\4\11\1\13\4\11\3\13\2\11"+
    "\11\0\300\11\47\13\25\0\4\13\u0116\11\2\0\6\11\2\0\46\11"+
    "\2\0\6\11\2\0\10\11\1\0\1\11\1\0\1\11\1\0\1\11"+
    "\1\0\37\11\2\0\65\11\1\0\7\11\1\0\1\11\3\0\3\11"+
    "\1\0\7\11\3\0\4\11\2\0\6\11\4\0\15\11\5\0\3\11"+
    "\1\0\7\11\16\0\5\13\32\0\5\13\20\0\2\11\23\0\1\11"+
    "\13\0\5\13\5\0\6\13\1\0\1\11\15\0\1\11\20\0\15\11"+
    "\3\0\33\11\25\0\15\13\4\0\1\13\3\0\14\13\21\0\1\11"+
    "\4\0\1\11\2\0\12\11\1\0\1\11\3\0\5\11\6\0\1\11"+
    "\1\0\1\11\1\0\1\11\1\0\4\11\1\0\13\11\2\0\4\11"+
    "\5\0\5\11\4\0\1\11\21\0\51\11\u0a77\0\57\11\1\0\57\11"+
    "\1\0\205\11\6\0\4\11\3\13\2\11\14\0\46\11\1\0\1\11"+
    "\5\0\1\11\2\0\70\11\7\0\1\11\17\0\1\13\27\11\11\0"+
    "\7\11\1\0\7\11\1\0\7\11\1\0\7\11\1\0\7\11\1\0"+
    "\7\11\1\0\7\11\1\0\7\11\1\0\40\13\57\0\1\11\u01d5\0"+
    "\3\11\31\0\11\11\6\13\1\0\5\11\2\0\5\11\4\0\126\11"+
    "\2\0\2\13\2\0\3\11\1\0\132\11\1\0\4\11\5\0\51\11"+
    "\3\0\136\11\21\0\33\11\65\0\20\11\u0200\0\u19b6\11\112\0\u51cd\11"+
    "\63\0\u048d\11\103\0\56\11\2\0\u010d\11\3\0\20\11\12\13\2\11"+
    "\24\0\57\11\1\13\4\0\12\13\1\0\31\11\7\0\1\13\120\11"+
    "\2\13\45\0\11\11\2\0\147\11\2\0\4\11\1\0\4\11\14\0"+
    "\13\11\115\0\12\11\1\13\3\11\1\13\4\11\1\13\27\11\5\13"+
    "\20\0\1\11\7\0\64\11\14\0\2\13\62\11\21\13\13\0\12\13"+
    "\6\0\22\13\6\11\3\0\1\11\4\0\12\13\34\11\10\13\2\0"+
    "\27\11\15\13\14\0\35\11\3\0\4\13\57\11\16\13\16\0\1\11"+
    "\12\13\46\0\51\11\16\13\11\0\3\11\1\13\10\11\2\13\2\0"+
    "\12\13\6\0\27\11\3\0\1\11\1\13\4\0\60\11\1\13\1\11"+
    "\3\13\2\11\2\13\5\11\2\13\1\11\1\13\1\11\30\0\3\11"+
    "\2\0\13\11\5\13\2\0\3\11\2\13\12\0\6\11\2\0\6\11"+
    "\2\0\6\11\11\0\7\11\1\0\7\11\221\0\43\11\10\13\1\0"+
    "\2\13\2\0\12\13\6\0\u2ba4\11\14\0\27\11\4\0\61\11\u2104\0"+
    "\u016e\11\2\0\152\11\46\0\7\11\14\0\5\11\5\0\1\11\1\13"+
    "\12\11\1\0\15\11\1\0\5\11\1\0\1\11\1\0\2\11\1\0"+
    "\2\11\1\0\154\11\41\0\u016b\11\22\0\100\11\2\0\66\11\50\0"+
    "\15\11\3\0\20\13\20\0\7\13\14\0\2\11\30\0\3\11\31\0"+
    "\1\11\6\0\5\11\1\0\207\11\2\0\1\13\4\0\1\11\13\0"+
    "\12\13\7\0\32\11\4\0\1\11\1\0\32\11\13\0\131\11\3\0"+
    "\6\11\2\0\6\11\2\0\6\11\2\0\3\11\3\0\2\11\3\0"+
    "\2\11\22\0\3\13\4\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\1\1\0\1\2\2\3\5\4\2\5\2\1\1\5"+
    "\1\6\1\2\16\5\1\7\2\6\2\4\1\10\1\11"+
    "\1\12\1\0\1\3\1\0\1\5\1\13\1\0\2\5"+
    "\1\0\4\5\1\14\17\5\1\15\1\16\1\17\1\20"+
    "\2\0\1\5\1\21\1\5\1\22\22\5\2\0\13\5"+
    "\1\0\13\5";

  private static int [] zzUnpackAction() {
    int [] result = new int[121];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\56\0\134\0\212\0\134\0\270\0\346\0\u0114"+
    "\0\u0142\0\134\0\u0170\0\u019e\0\u01cc\0\u01fa\0\u0228\0\u0256"+
    "\0\u0284\0\u02b2\0\u02e0\0\u030e\0\u033c\0\u036a\0\u0398\0\u03c6"+
    "\0\u03f4\0\u0422\0\u0450\0\u047e\0\u04ac\0\u04da\0\u0508\0\134"+
    "\0\134\0\u0536\0\u0564\0\u0592\0\u05c0\0\134\0\u05ee\0\u061c"+
    "\0\u064a\0\u0678\0\u06a6\0\u0256\0\u06d4\0\u0702\0\u0730\0\u075e"+
    "\0\u078c\0\u07ba\0\u07e8\0\u0816\0\u0170\0\u0844\0\u0872\0\u08a0"+
    "\0\u08ce\0\u08fc\0\u092a\0\u0958\0\u0986\0\u09b4\0\u09e2\0\u0a10"+
    "\0\u0a3e\0\u0a6c\0\u0a9a\0\u0ac8\0\134\0\134\0\134\0\134"+
    "\0\u0af6\0\u0b24\0\u0b52\0\u06d4\0\u0b80\0\134\0\u0bae\0\u0bdc"+
    "\0\u0c0a\0\u0c38\0\u0c66\0\u0c94\0\u0cc2\0\u0cf0\0\u0d1e\0\u0d4c"+
    "\0\u0d7a\0\u0da8\0\u0dd6\0\u0e04\0\u0e32\0\u0e60\0\u0e8e\0\u0ebc"+
    "\0\u0eea\0\u0f18\0\u0f46\0\u0f74\0\u0fa2\0\u0fd0\0\u0ffe\0\u102c"+
    "\0\u105a\0\u1088\0\u10b6\0\u10e4\0\u1112\0\u1140\0\u116e\0\u119c"+
    "\0\u11ca\0\u11f8\0\u1226\0\u1254\0\u1282\0\u12b0\0\u12de\0\u130c"+
    "\0\u133a";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[121];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\1\4\2\5\1\6\1\7\1\10\1\11\1\12"+
    "\1\13\1\14\1\3\1\15\1\16\1\13\1\17\1\20"+
    "\1\21\1\22\1\23\1\13\1\24\1\25\1\26\1\13"+
    "\1\27\1\30\1\31\1\32\1\13\1\33\1\13\1\34"+
    "\1\13\1\35\1\36\1\37\1\40\1\41\1\42\2\12"+
    "\1\43\1\44\1\10\1\3\1\45\2\3\42\45\1\46"+
    "\7\45\1\47\60\0\1\5\60\0\1\50\42\0\1\12"+
    "\12\0\1\51\120\0\1\12\15\0\1\52\56\0\7\13"+
    "\2\0\23\13\22\0\7\13\2\0\10\13\1\53\12\13"+
    "\25\0\2\15\2\0\1\54\51\0\2\15\1\55\1\0"+
    "\1\54\46\0\7\13\2\0\1\56\16\13\1\57\3\13"+
    "\25\0\2\54\40\0\56\60\11\0\7\13\2\0\1\13"+
    "\1\61\1\13\1\62\17\13\22\0\7\13\2\0\12\13"+
    "\1\63\10\13\22\0\7\13\2\0\16\13\1\64\4\13"+
    "\22\0\6\13\1\65\2\0\3\13\1\66\2\13\1\67"+
    "\14\13\22\0\7\13\2\0\10\13\1\70\12\13\22\0"+
    "\7\13\2\0\1\13\1\71\6\13\1\72\12\13\22\0"+
    "\6\13\1\65\2\0\17\13\1\67\3\13\22\0\7\13"+
    "\2\0\1\73\7\13\1\74\12\13\22\0\7\13\2\0"+
    "\6\13\1\75\2\13\1\76\11\13\22\0\7\13\2\0"+
    "\1\76\7\13\1\77\4\13\1\100\5\13\22\0\7\13"+
    "\2\0\1\13\1\101\21\13\22\0\7\13\2\0\10\13"+
    "\1\65\12\13\22\0\7\13\2\0\1\102\22\13\22\0"+
    "\7\13\2\0\1\13\1\103\13\13\1\104\5\13\60\0"+
    "\1\41\1\12\57\0\1\12\56\0\1\12\2\0\1\45"+
    "\2\0\42\45\1\0\7\45\24\0\1\105\4\0\1\106"+
    "\7\0\1\107\4\0\1\110\15\0\1\111\50\0\1\51"+
    "\1\4\1\5\53\51\10\0\1\112\56\0\7\13\2\0"+
    "\4\13\1\113\16\13\25\0\2\114\1\0\1\114\2\0"+
    "\1\114\6\0\1\114\2\0\1\114\1\0\1\114\3\0"+
    "\1\114\24\0\7\13\2\0\11\13\1\76\11\13\22\0"+
    "\7\13\2\0\6\13\1\115\14\13\32\0\1\116\45\0"+
    "\7\13\2\0\1\13\1\117\21\13\22\0\7\13\2\0"+
    "\3\13\1\120\17\13\22\0\7\13\2\0\1\121\10\13"+
    "\1\122\4\13\1\123\4\13\22\0\7\13\2\0\1\13"+
    "\1\124\21\13\22\0\1\13\1\14\5\13\2\0\23\13"+
    "\22\0\7\13\2\0\16\13\1\65\4\13\22\0\7\13"+
    "\2\0\16\13\1\35\4\13\22\0\7\13\2\0\12\13"+
    "\1\125\10\13\22\0\7\13\2\0\10\13\1\126\12\13"+
    "\22\0\7\13\2\0\7\13\1\127\6\13\1\130\4\13"+
    "\22\0\7\13\2\0\6\13\1\131\14\13\22\0\1\13"+
    "\1\132\5\13\2\0\20\13\1\133\2\13\22\0\7\13"+
    "\2\0\3\13\1\130\17\13\22\0\7\13\2\0\6\13"+
    "\1\134\14\13\22\0\7\13\2\0\1\135\22\13\22\0"+
    "\7\13\2\0\17\13\1\130\3\13\22\0\7\13\2\0"+
    "\4\13\1\136\16\13\22\0\7\13\2\0\4\13\1\137"+
    "\16\13\22\0\7\13\2\0\4\13\1\140\16\13\11\0"+
    "\5\111\1\141\50\111\10\112\1\142\45\112\11\0\7\13"+
    "\2\0\20\13\1\65\2\13\22\0\7\13\2\0\14\13"+
    "\1\143\6\13\22\0\7\13\2\0\1\144\22\13\22\0"+
    "\7\13\2\0\4\13\1\145\16\13\22\0\7\13\2\0"+
    "\11\13\1\65\6\13\1\65\2\13\22\0\7\13\2\0"+
    "\12\13\1\146\10\13\22\0\7\13\2\0\17\13\1\147"+
    "\3\13\22\0\7\13\2\0\4\13\1\74\4\13\1\150"+
    "\5\13\1\151\3\13\22\0\7\13\2\0\1\152\22\13"+
    "\22\0\7\13\2\0\11\13\1\65\11\13\22\0\7\13"+
    "\2\0\12\13\1\126\10\13\22\0\7\13\2\0\12\13"+
    "\1\65\10\13\22\0\7\13\2\0\5\13\1\65\15\13"+
    "\22\0\7\13\2\0\4\13\1\153\16\13\22\0\7\13"+
    "\2\0\14\13\1\146\6\13\22\0\7\13\2\0\14\13"+
    "\1\154\1\13\1\155\4\13\22\0\7\13\2\0\1\13"+
    "\1\65\21\13\22\0\7\13\2\0\6\13\1\65\14\13"+
    "\22\0\7\13\2\0\16\13\1\130\4\13\22\0\7\13"+
    "\2\0\11\13\1\130\11\13\11\0\5\111\1\156\50\111"+
    "\7\112\1\5\1\142\45\112\11\0\7\13\2\0\16\13"+
    "\1\157\4\13\22\0\7\13\2\0\2\13\1\65\20\13"+
    "\22\0\7\13\2\0\5\13\1\136\15\13\22\0\7\13"+
    "\2\0\1\76\22\13\22\0\7\13\2\0\1\13\1\136"+
    "\21\13\22\0\7\13\2\0\12\13\1\136\10\13\22\0"+
    "\7\13\2\0\14\13\1\160\6\13\22\0\7\13\2\0"+
    "\13\13\1\65\7\13\22\0\7\13\2\0\1\13\1\161"+
    "\21\13\22\0\7\13\2\0\1\67\22\13\22\0\7\13"+
    "\2\0\4\13\1\162\16\13\11\0\5\111\1\156\1\5"+
    "\47\111\11\0\7\13\2\0\4\13\1\163\16\13\22\0"+
    "\7\13\2\0\16\13\1\164\4\13\22\0\7\13\2\0"+
    "\10\13\1\165\12\13\22\0\7\13\2\0\6\13\1\101"+
    "\14\13\22\0\7\13\2\0\10\13\1\136\12\13\22\0"+
    "\7\13\2\0\17\13\1\166\3\13\22\0\7\13\2\0"+
    "\6\13\1\167\14\13\22\0\7\13\2\0\1\13\1\130"+
    "\21\13\22\0\7\13\2\0\21\13\1\170\1\13\22\0"+
    "\7\13\2\0\12\13\1\171\10\13\22\0\7\13\2\0"+
    "\6\13\1\67\14\13\11\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4968];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\1\1\0\1\11\1\1\1\11\4\1\1\11\25\1"+
    "\2\11\4\1\1\11\1\1\1\0\1\1\1\0\2\1"+
    "\1\0\2\1\1\0\24\1\4\11\2\0\3\1\1\11"+
    "\22\1\2\0\13\1\1\0\13\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[121];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
    StringBuffer string = new StringBuffer();

    public int lineNumber() {
        return yyline;
    }


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public FlexScanner(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public FlexScanner(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 2248) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public Token NextToken() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 9: 
          { yybegin(YYINITIAL);
                                   return
                                   new Token("STRING", string.toString());
          }
        case 19: break;
        case 14: 
          { string.append('\n');
          }
        case 20: break;
        case 8: 
          { string.append( yytext() );
          }
        case 21: break;
        case 1: 
          { return new Token("idINT", yytext());
          }
        case 22: break;
        case 17: 
          { return new Token("idHEX", yytext());
          }
        case 23: break;
        case 18: 
          { return new Token("idCHAR", yytext());
          }
        case 24: break;
        case 10: 
          { string.append('\\');
          }
        case 25: break;
        case 7: 
          { string.setLength(0); yybegin(STRING);
          }
        case 26: break;
        case 4: 
          { return new Token("OPERATOR", yytext());
          }
        case 27: break;
        case 12: 
          { return new Token("KEYWORD", yytext());
          }
        case 28: break;
        case 2: 
          { throw new Error("Illegal character <"+
                                                    yytext()+">");
          }
        case 29: break;
        case 13: 
          { string.append('\r');
          }
        case 30: break;
        case 5: 
          { return new Token("idID", yytext());
          }
        case 31: break;
        case 16: 
          { string.append('\"');
          }
        case 32: break;
        case 11: 
          { return new Token("idREAL", yytext());
          }
        case 33: break;
        case 6: 
          { return new Token("SYNTAX", yytext());
          }
        case 34: break;
        case 3: 
          { /* ignore */
          }
        case 35: break;
        case 15: 
          { string.append('\t');
          }
        case 36: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
              {     return new Token("EOF", "$");
 }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
