package slip.parser; // Début 6.2.2005 17:45 ; Break 20:15
                     // Reprise 22:00 ; Fin 7.2.2005 00:07
                     // No longer supported (12.2.2005 : 14:45)
import slip.lexer.*;

public class BooleanParser
{

  private Lexer lex ;
  private Terminal cur ; // Current symbol ;

  private void parseIdentifier() throws Exception
  {
    if ((cur!=null)&&(cur.sort==Terminal.ID)) { cur = lex.nextSymbol(); System.out.println(cur); }
    else throw new ParserException("Un identificateur " +
                                   "était attendu, au lieu de " + cur);
  }

  private boolean isIdentifier()
  {
    return ((cur!=null)&&(cur.sort==Terminal.ID));
  }

  private void parseIntegralLiteral() throws Exception
  {
    if ((cur!=null)&&(cur.sort==Terminal.IL)) { cur = lex.nextSymbol(); System.out.println(cur); }
    else throw new ParserException("Une constante entière " +
                                   "était attendue, au lieu de " + cur);
  }

  private boolean isIntegralLiteral()
  {
    return ((cur!=null)&&(cur.sort==Terminal.IL));
  }

  private void parseTerminal(Terminal t) throws Exception
  {
    if (t==cur){ cur = lex.nextSymbol(); System.out.println(cur); }
    else throw new ParserException("Le symbole " + t +
                                   " était attendu, au lieu de " + cur);
  }



// <special character> ::= 
//   , | . | ( | ) | "|" | & | { | } | / | > | < | = | ! | ; | * | + | - | %
// Il y a 18 caractères spéciaux.

  private void parseCOM() throws Exception
  {
    parseTerminal(SpecialCharacter.COM);     
  }

  private boolean isCOM()
  {
    return cur == SpecialCharacter.COM;     
  }
 
  private void parseDOT() throws Exception
  {
    parseTerminal(SpecialCharacter.DOT);     
  }
 
  private boolean isDOT()
  {
    return cur == SpecialCharacter.DOT;     
  }
 
  private void parseOPA() throws Exception
  {
    parseTerminal(SpecialCharacter.OPA);     
  }
 
  private boolean isOPA()
  {
    return cur == SpecialCharacter.OPA;     
  }
 
  private void parseCPA() throws Exception
  {
    parseTerminal(SpecialCharacter.CPA);     
  }

  private boolean isCPA()
  {
    return cur == SpecialCharacter.CPA;     
  }
 
  private void parseOR() throws Exception
  {
    parseTerminal(SpecialCharacter.OR);     
  }

  private boolean isOR()
  {
    return cur == SpecialCharacter.OR;     
  }

  private void parseAND() throws Exception
  {
    parseTerminal(SpecialCharacter.AND);     
  }
 
  private boolean isAND()
  {
    return cur == SpecialCharacter.AND;     
  }


  private void parseOBR() throws Exception
  {
    parseTerminal(SpecialCharacter.OBR);     
  }
  
  private boolean isOBR()
  {
    return cur == SpecialCharacter.OBR;     
  }

  private void parseCBR() throws Exception
  {
    parseTerminal(SpecialCharacter.CBR);     
  }
   
  private boolean isCBR()
  {
    return cur == SpecialCharacter.CBR;     
  }

  private void parseSLA() throws Exception
  {
    parseTerminal(SpecialCharacter.SLA);     
  }

  private boolean isSLA()
  {
    return cur == SpecialCharacter.SLA;     
  }

  private void parseGT() throws Exception
  {
    parseTerminal(SpecialCharacter.GT);     
  }
 
  private boolean isGT()
  {
    return cur == SpecialCharacter.GT;     
  }

  private void parseLT() throws Exception
  {
    parseTerminal(SpecialCharacter.LT);     
  }
 
  private boolean isLT()
  {
    return cur == SpecialCharacter.LT;     
  }

  private void parseEQU() throws Exception
  {
    parseTerminal(SpecialCharacter.EQU);     
  }

  private boolean isEQU()
  {
    return cur == SpecialCharacter.EQU;     
  }

  private void parseNOT() throws Exception
  {
    parseTerminal(SpecialCharacter.NOT);     
  }
 
  private boolean isNOT()
  {
    return cur == SpecialCharacter.NOT;     
  }

 
  private void parseSEM() throws Exception
  {
    parseTerminal(SpecialCharacter.SEM);     
  }
 
  private void parseTIM() throws Exception
  {
    parseTerminal(SpecialCharacter.TIM);     
  }

  private boolean isTIM()
  {
    return cur == SpecialCharacter.TIM;     
  }

 
  private void parsePLU() throws Exception
  {
    parseTerminal(SpecialCharacter.PLU);     
  }

  private boolean isPLU()
  {
    return cur == SpecialCharacter.PLU;     
  }

  private void parseMIN() throws Exception
  {
    parseTerminal(SpecialCharacter.MIN);     
  }

  private boolean isMIN()
  {
    return cur == SpecialCharacter.MIN;     
  }

  private void parseREM() throws Exception
  {
    parseTerminal(SpecialCharacter.REM);     
  }

  private boolean isREM()
  {
    return cur == SpecialCharacter.REM;     
  }


// <reserved word> ::= if | else | read | write | while | this | null | new
//                  | return | super
// Il y a 10 mots réservés.

  private void parseIF() throws Exception
  {
    parseTerminal(ReservedWord.IF);     
  }

  private boolean isIF() 
  {
    return cur == ReservedWord.IF;     
  }

  private void parseELSE() throws Exception
  {
    parseTerminal(ReservedWord.ELSE);     
  }

  private boolean isELSE() 
  {
    return cur == ReservedWord.ELSE;     
  }

  private void parseREAD() throws Exception
  {
    parseTerminal(ReservedWord.READ);     
  }

  private boolean isREAD() 
  {
    return cur == ReservedWord.READ;     
  }


  private void parseWRITE() throws Exception
  {
    parseTerminal(ReservedWord.WRITE);     
  }

  private boolean isWRITE() 
  {
    return cur == ReservedWord.WRITE;     
  }

  private void parseWHILE() throws Exception
  {
    parseTerminal(ReservedWord.WHILE);     
  }

  private boolean isWHILE() 
  {
    return cur == ReservedWord.WHILE;     
  }

  private void parseTHIS() throws Exception
  {
    parseTerminal(ReservedWord.THIS);     
  }

  private boolean isTHIS() 
  {
    return cur == ReservedWord.THIS;     
  }

  private void parseNULL() throws Exception
  {
    parseTerminal(ReservedWord.NULL);     
  }

  private boolean isNULL() 
  {
    return cur == ReservedWord.NULL;     
  }

  private void parseNEW() throws Exception
  {
    parseTerminal(ReservedWord.NEW);     
  }

  private boolean isNEW() 
  {
    return cur == ReservedWord.NEW;     
  }

  private void parseRETURN() throws Exception
  {
    parseTerminal(ReservedWord.RETURN);     
  }

  private boolean isRETURN() 
  {
    return cur == ReservedWord.RETURN;     
  }

  private void parseSUPER() throws Exception
  {
    parseTerminal(ReservedWord.SUPER);     
  }

  private boolean isSUPER() 
  {
    return cur == ReservedWord.SUPER;     
  }

  private void parseProgram() throws Exception
  // <program> ::=   <method> 
  //              | <program> <method>
  {
     parseMethod();
     while (cur != null) parseMethod();
  }

  private void parseMethod() throws Exception
  // <method>  ::= <name> ( <parameter list> ) <body>
  {
     parseName();
     parseOPA();
     parseParameterList();
     parseCPA();
     parseBody();   
  }

  private void parseName() throws Exception
  // <name>    ::=   <method name> 
  //               | <method name> / <integral literal>
  {
     parseIdentifier();
     if (isSLA()) { parseSLA(); parseIntegralLiteral(); }
  }
   

  private void parseParameterList() throws Exception
  // <parameter list> ::= 
  //            <empty> 
  //          | <non empty parameter list>
  //
  // <non empty parameter list> ::= 
  //             <formal parameter> 
  //           | <non empty parameter list> , <formal parameter>
  {
     if (isIdentifier())
     {
         parseIdentifier();
         while (isCOM()) { parseCOM(); parseIdentifier(); }
     }
  }

  private void parseBody() throws Exception
  // <body>    ::= <command sequence>
  {
    parseCommandSequence();
  }


  private void parseCommand() throws Exception
  // <command> ::=   <assignment> 
  //           | <conditional command> 
  //           | <while command>
  //           | <command sequence> 
  //           | <input command> 
  //           | <output command>
  //           | <return>
  {
   if (isIF())    parseConditionalCommand(); else
   if (isWHILE()) parseWhileCommand();       else
   if (isOBR())   parseCommandSequence();    else
   if (isREAD())  parseInputCommand();       else
   if (isWRITE()) parseOutputCommand();      else
   if (isRETURN()) parseReturn();            else
                  parseAssignment();
  }

   
  private void parseConditionalCommand() throws Exception
  // <conditional command> ::= 
  //            if <condition> <command> 
  //          | if <condition> <command> else <command>
  {
    parseIF();
    parseCondition();
    parseCommand();
    if (isELSE()) 
     { parseELSE();
       parseCommand();
     }
  }
  
   
  private void parseAssignment() throws Exception
  // <assignment> ::= <left expression> = <right expression> ;
  {
    parseLeftExpression();
    parseEQU();
    parseRightExpression();
    parseSEM();
  }
  
   
  private void parseCondition() throws Exception
  // <condition> ::= ( <disjunction> )
  {
     parseOPA();
     parseDisjunction();
     parseCPA();
  }
  
   
  private void parseDisjunction() throws Exception
  // <disjunction> ::= 
  //            <conjunction> 
  //          | <disjunction> "|" <conjunction>
  {
     parseConjunction();
     while (isOR()) { parseOR(); parseConjunction(); }
  }
    
  private void parseConjunction() throws Exception
  // <conjunction> ::=
  //             <basic condition>
  //           | <conjunction> & <basic condition>
  {
     parseBasicCondition();
     while (isAND()) { parseAND(); parseBasicCondition(); }
  }
  
  private void parseBasicCondition() throws Exception
  // <basic condition> ::=
  //            <relation>
  //          | ! <condition>
  {
     if (isNOT()) { parseNOT(); parseCondition(); }
     else parseRelation(); 
  }
  
  private void parseRelation() throws Exception
  // <relation> ::= <right expression> <comparison operator> <right expression>
  {
     parseRightExpression();
     parseComparisonOperator();
     parseRightExpression();
  }
   
  private void parseComparisonOperator() throws Exception
  // <comparison operator> ::= = | ! = | < | > | < = | > =
  {
     if (isEQU()) { parseEQU(); parseEQU(); }   else
     if (isNOT()) { parseNOT(); parseEQU(); }   else
     if (isLT())
        { parseLT(); if (isEQU()) parseEQU(); } else
     { parseGT(); if (isEQU()) parseEQU(); }
  }
   
  private void parseWhileCommand() throws Exception
  // <while command> ::= while <condition> <command>
  {
     parseWHILE();
     parseCondition();
     parseCommand();
  }

  private void parseCommandSequence() throws Exception
  // <command sequence> ::= { <command list> }
  {
    parseOBR();
    parseCommandList();
    parseCBR();
  }
  
  private void parseCommandList() throws Exception
  // <command list> ::= 
  //            <empty> 
  //          | <command list> <command>
  {
    while (!isCBR()) parseCommand();  
  }

  private void parseInputCommand() throws Exception
  // <input command>  ::= read  ( <left expression list> ) ;
  {
    parseREAD();
    parseOPA();
    parseLeftExpressionList();
    parseCPA();
    parseSEM();
  }

  private void parseOutputCommand() throws Exception
  // <output command> ::= write ( <right expression list> ) ;
  {
    parseWRITE();
    parseOPA();
    parseRightExpressionList();
    parseCPA();
    parseSEM();
  }
  
  private void parseReturn() throws Exception
  // <return>  ::= return ( <right expression> );
  {
    parseRETURN();
    parseOPA();
    parseRightExpressionList();
    parseCPA();
    parseSEM();
  }
  
  private void parseLeftExpressionList() throws Exception
  // <left expression list> ::= 
  //            <left expression> 
  //          | <left expression list> , <left expression> 
  {
     parseLeftExpression();
     while (isCOM()) { parseCOM(); parseLeftExpression(); } 
  }
 
  private void parseRightExpressionList() throws Exception
  // <right expression list> ::=    
  //            <right expression> 
  //          | <right expression list> , <right expression> 
  {
    parseRightExpression();
    while (isCOM()) { parseCOM(); parseRightExpression(); }   
  }

  private void parseLeftExpression() throws Exception
  // <left expression> ::= 
  //            <variable> 
  //          | <variable> . <integral literal>
  {
    parseIdentifier();
    if (isDOT()) {  parseDOT(); parseIntegralLiteral(); } 
  }
 
  private void parseRightExpression() throws Exception
  // <right expression> ::= <expression>
  {
     parseExpression(); 
  }
 
  private void parseExpression() throws Exception
  // <expression> ::= 
  //            <term> 
  //          | - <term> 
  //          | <expression> <additive operator> <term>
  // <additive operator> ::= + | -
  {
    if (isMIN()) parseMIN();
    parseTerm();

    while (isPLU()|isMIN()) 
       { if (isPLU()) parsePLU(); else parseMIN();
         parseTerm(); 
       }
  }

  private void parseTerm() throws Exception
  // <term>   ::= 
  //            <factor> 
  //          | <term> <multiplicative operator> <factor>
  //
  // <multiplicative operator> ::= * | / | %
  {
    parseFactor();
    while (isTIM()|isSLA()|isREM()) 
       { if (isTIM()) parseTIM(); else 
         if (isSLA()) parseSLA(); else 
                      parseREM();
         parseFactor(); 
       }
  }
 
  private void parseFactor() throws Exception
  /* <factor> ::=
                <left expression> 
              | <integral literal> 
              | this 
              | null 
              | new / <integral literal>
              | <method call>
              | ( <expression> )

    <variable> ::= <identifier>

    <method call> ::= <target> <method name> ( <actual parameter list> )

    <target> ::=
                <empty>
              | <variable> .
              | super .
   */
  {
     if (isIntegralLiteral()) parseIntegralLiteral(); else
     if (isTHIS())            parseTHIS();            else
     if (isNULL())            parseNULL();            else
     if (isNEW())    { parseNEW(); parseSLA(); parseIntegralLiteral(); } else
     if (isOPA())    { parseOPA(); parseExpression(); parseCPA(); }      else
     if (isSUPER())    { parseSUPER(); parseDOT(); parseIdentifier(); 
                                     parseActualParameterList();  }      else
     {  parseIdentifier();
        if (isDOT()) { parseDOT(); 
                       if (isIntegralLiteral()) parseIntegralLiteral(); 
                       else {  parseIdentifier(); parseActualParameterList(); }}
        else if (isOPA()) parseActualParameterList();
     }
  }

  public void parseActualParameterList() throws Exception
  // <actual parameter list> ::=
  //            <empty>
  //          | <left expression list>
  {
     parseOPA();
     if (!isCPA()) parseRightExpressionList();
     parseCPA();
  }

  public BooleanParser(String fileName) throws Exception
  {
    lex = new Lexer(fileName);
    cur = lex.nextSymbol();

  }

  public boolean parse()
  {  try { parseProgram(); }
     catch (Exception e)
     {  
        System.out.println(e);
        return false; 
     }
     return true ;
  }

  public static void main(String[] arg) throws Exception
  {
    BooleanParser toto = new BooleanParser(arg[0]);
    System.out.println(toto.parse());
  }
}

