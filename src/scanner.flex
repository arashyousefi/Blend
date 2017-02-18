/* JFlex example: partial Java language lexer specification */


%%

%public
%class FlexScanner
%function NextToken
%type Token
%unicode
%line
%column

%{
    StringBuffer string = new StringBuffer();

    public int lineNumber() {
        return yyline;
    }
%}

%eofval{
    return new Token("EOF", "$");
%eofval}



LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f\v]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment   = "<--"  ~"-->"

// Comment can be the last line of the file, without line terminator.
EndOfLineComment     = "--" {InputCharacter}* {LineTerminator}?

DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

Identifier = [:jletter:] [:jletterdigit:]*

DecIntegerLiteral = [0-9]*
HexIntegerLiteral = 0x[0-9a-f]+
RealLiteral = [0-9]+\.[0-9]* | [0-9]*\.[0-9]+
CharLiteral = '[^']' | '\''

%state STRING

%%

/* keywords */
<YYINITIAL> "array"                     { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "assign"                    { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "bool"                      { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "break"                     { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "case"                      { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "char"                      { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "concat"                    { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "continue"                  { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "do"                        { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "else"                      { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "endcase"                   { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "environment"               { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "false"                     { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "function"                  { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "goto"                      { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "if"                        { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "int"  	                    { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "isvoid"                    { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "label"                     { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "late"                      { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "long"                      { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "main"                      { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "of"                        { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "out"                       { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "read"                      { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "real"                      { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "release"                   { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "return"                    { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "string"                    { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "strlen"                    { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "structure"                 { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "true"                      { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "void"                      { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "while"                     { return new Token("KEYWORD", yytext()); }
<YYINITIAL> "write"                     { return new Token("KEYWORD", yytext()); }

<YYINITIAL> {
  /* literals */
  {HexIntegerLiteral}            { return new Token("idHEX", yytext()); }
  {RealLiteral}                  { return new Token("idREAL", yytext()); }
  {CharLiteral}                  { return new Token("idCHAR", yytext()); }
  {DecIntegerLiteral}            { return new Token("idINT", yytext()); }

  /* identifiers */
  {Identifier}                   { return new Token("idID", yytext()); }

  \"                             { string.setLength(0); yybegin(STRING); }

  /* syntax */
  ","                            { return new Token("SYNTAX", yytext()); }
  "."                            { return new Token("SYNTAX", yytext()); }
  "["                            { return new Token("SYNTAX", yytext()); }
  "]"                            { return new Token("SYNTAX", yytext()); }
  ";"                            { return new Token("SYNTAX", yytext()); }
  "{"                            { return new Token("SYNTAX", yytext()); }
  "}"                            { return new Token("SYNTAX", yytext()); }
  "::"                           { return new Token("SYNTAX", yytext()); }
  ":"                            { return new Token("SYNTAX", yytext()); }
  "("                            { return new Token("SYNTAX", yytext()); }
  ")"                            { return new Token("SYNTAX", yytext()); }

  /* operators */
  ":="                           { return new Token("OPERATOR", yytext()); }

  "/"                            { return new Token("OPERATOR", yytext()); }
  "%"                            { return new Token("OPERATOR", yytext()); }
  "*"                            { return new Token("OPERATOR", yytext()); }
  "-"                            { return new Token("OPERATOR", yytext()); }
  "&"                            { return new Token("OPERATOR", yytext()); }
  "^"                            { return new Token("OPERATOR", yytext()); }
  "|"                            { return new Token("OPERATOR", yytext()); }
  "||"                           { return new Token("OPERATOR", yytext()); }
  "&&"                           { return new Token("OPERATOR", yytext()); }
  "+"                            { return new Token("OPERATOR", yytext()); }
  "~"                            { return new Token("OPERATOR", yytext()); }
  "!"                            { return new Token("OPERATOR", yytext()); }

  "="                            { return new Token("OPERATOR", yytext()); }
  "<="                            { return new Token("OPERATOR", yytext()); }
  ">="                            { return new Token("OPERATOR", yytext()); }
  "!="                            { return new Token("OPERATOR", yytext()); }
  "<"                            { return new Token("OPERATOR", yytext()); }
  ">"                            { return new Token("OPERATOR", yytext()); }




  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
}

<STRING> {
  \"                             { yybegin(YYINITIAL);
                                   return
                                   new Token("STRING", string.toString()); }
  [^\n\r\"\\]+                   { string.append( yytext() ); }
  \\t                            { string.append('\t'); }
  \\n                            { string.append('\n'); }

  \\r                            { string.append('\r'); }
  \\\"                           { string.append('\"'); }
  \\                             { string.append('\\'); }
}

/* error fallback */
[^]                              { throw new Error("Illegal character <"+
                                                    yytext()+">"); }
