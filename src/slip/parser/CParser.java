package slip.parser ;                 
import slip.lexer.* ;
import slip.translation.MethodTable ;
import slip.internal.Prog ;
import fichiers.text ;


public class CParser
{

  private Lexer lex ;
  private Terminal cur ; // Current symbol ;
  private MethodTable curvt ; // Current table for local variables and formal parameters


  private String parseIdentifier() throws Exception
  {
    String id ;
    if ((cur!=null)&&(cur.sort==Terminal.ID)) 
    { id = cur.toString() ; nextSymbol();  }
    else throw new ParserException("Identifier expected instead of " + cur,
				                              lex.getLineNumber(), lex.getIndexNumber());
    return id;
  }

  private boolean isIdentifier()
  { 
    return ((cur!=null)&&(cur.sort==Terminal.ID));
  }

  private int parseIntegralLiteral() throws Exception
  {
    int i ;

    if ((cur!=null)&&(cur.sort==Terminal.IL)) 
    { i = ((IntegralLiteral)cur).valueOf() ; nextSymbol();  }
    else throw new ParserException("Integral literal expected instead of " + cur,
				                              lex.getLineNumber(), lex.getIndexNumber());
    return i;
  }

  private boolean isIntegralLiteral()
  {
    return ((cur!=null)&&(cur.sort==Terminal.IL));
  }

  private void parseTerminal(Terminal t) throws Exception
  {
    if (t==cur){ nextSymbol(); }
    else throw new ParserException("Terminal " + t +
                                   " expected instead of " + cur,
				                              lex.getLineNumber(), lex.getIndexNumber());
  }



// <special character> ::= 
//   , | . | ( | ) | "|" | & | { | } | / | > | < | = | ! | ; | * | + | - | %
// Il y a 18 caractres spŽciaux.

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
// Il y a 10 mots rŽservŽs.

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

  private Cprog parseProgram() throws Exception
  // <program> ::=   <method> 
  //              | <program> <method>
  {
     List l = new List() ;

     l.add(parseMethod());

     while (cur != null) l.add(parseMethod());

     return new Cprog(l.methodArray()) ;
  }

  private Cmethod parseMethod() throws Exception
  // <method>      ::= <name> ( <parameter list> ) <body>
  // <name>        ::= <method name> 
  //                 | <method name> / <integral literal>
  // <method name> ::=   <identifier> 
  // <body>        ::= <command sequence>
  {
     String methodName = parseIdentifier() ;

     int level ;
     if (isSLA()) 
     {  parseSLA() ; level = parseIntegralLiteral() ; }
     else level = -1 ;

     parseOPA() ;
     String[] fparam = parseParameterList();
     parseCPA() ;

     curvt  =  new MethodTable(fparam.length) ;

     int i = 0 ;
     while (i != fparam.length)
     { curvt.addFormal(fparam[i]) ; i++ ; }

     Cseq body   = parseCommandSequence() ;  

     return new Cmethod(methodName, level,  fparam, body, curvt) ;
  }
   

  private String[] parseParameterList() throws Exception
  // <parameter list> ::= 
  //            <empty> 
  //          | <non empty parameter list>
  //
  // <non empty parameter list> ::= 
  //             <formal parameter> 
  //           | <non empty parameter list> , <formal parameter>
  {
     List l = new List() ;

     if (isIdentifier())
       {
           l.add(parseIdentifier()) ;
           while (isCOM())
           {  parseCOM() ;
              l.add(parseIdentifier()) ;
           }
       }

     return l.stringArray() ;
  }


  private Ccmd parseCommand() throws Exception
  // <command> ::=   <assignment> 
  //           | <conditional command> 
  //           | <while command>
  //           | <command sequence> 
  //           | <input command> 
  //           | <output command>
  //           | <return>
  {
   Ccmd cmd;

   if (isIF())    cmd = parseConditionalCommand(); else
   if (isWHILE()) cmd = parseWhileCommand();       else
   if (isOBR())   cmd = parseCommandSequence();    else
   if (isREAD())  cmd = parseInputCommand();       else
   if (isWRITE()) cmd = parseOutputCommand();      else
   if (isRETURN())cmd = parseReturn();             else
                  cmd = parseAssignment();
   
   return cmd ;
  }

   
  private Cif parseConditionalCommand() throws Exception
  // <conditional command> ::= 
  //            if <condition> <command> 
  //          | if <condition> <command> else <command>
  {
    parseIF();
    Ccond cond  = parseCondition();
    Ccmd  cmdt  = parseCommand();

    if (isELSE()) 
    {  parseELSE() ; return new Cif(cond, cmdt, parseCommand()) ; }
    else return new Cif(cond, cmdt) ;
  }
  
   
  private Cass parseAssignment() throws Exception
  // <assignment> ::= <left expression> = <right expression> ;
  {
    Clexpr l = parseLeftExpression() ;
               parseEQU() ;
    Crexpr r = parseRightExpression() ;
               parseSEM() ;
 
    return new Cass(l, r) ;
  }
  
   
  private Ccond parseCondition() throws Exception
  // <condition> ::= ( <disjunction> )
  {
               parseOPA() ;
     Ccond c = parseDisjunction() ;
               parseCPA() ;

     return c ;
  }
  
   
  private Ccond parseDisjunction() throws Exception
  // <disjunction> ::= 
  //            <conjunction> 
  //          | <disjunction> "|" <conjunction>
  {
     Ccond disj = parseConjunction() ;

     while (isOR()) 
     { parseOR() ; disj = new Cor(disj, parseConjunction()) ; }
 
     return disj;
  }
    
  private Ccond parseConjunction() throws Exception
  // <conjunction> ::=
  //             <basic condition>
  //           | <conjunction> & <basic condition>
  {
     Ccond conj = parseBasicCondition() ;

     while (isAND())
     { parseAND() ; conj = new Cand(conj, parseBasicCondition()) ; }

     return conj;
  }
  
  private Ccond parseBasicCondition() throws Exception
  // <basic condition> ::=
  //            <relation>
  //          | ! <condition>
  {
     if (isNOT()) 
     { parseNOT() ; return new Cnot(parseCondition()) ; }
     else return parseRelation() ; 
  }
  
  private Crel parseRelation() throws Exception
  // <relation> ::= <right expression> <comparison operator> <right expression>
  {
     Crexpr r1 = parseRightExpression() ;
     int    op = parseComparisonOperator() ;
     Crexpr r2 = parseRightExpression() ;

     return new Crel(r1, op, r2) ;
  }
   
  private int parseComparisonOperator() throws Exception
  // <comparison operator> ::= = = | ! = | < | > | < = | > =
  {
     if (isEQU()) { parseEQU() ; parseEQU() ; return Crel.EQ ; }  else
     if (isNOT()) { parseNOT() ; parseEQU() ; return Crel.NE ; }  else
     if (isLT())
        { parseLT() ; 
          if (isEQU()) 
          { parseEQU() ; return Crel.LE ; } 
          else return Crel.LT ; 
        } 
     else // >
        { parseGT(); 
          if (isEQU()) 
          { parseEQU() ; return Crel.GE ; } 
          else return Crel.GT ; 
        } 
  }
   
  private Cwhile parseWhileCommand() throws Exception
  // <while command> ::= while <condition> <command>
  {
     parseWHILE() ;

     return new Cwhile(parseCondition(), parseCommand()) ;
  }

  private Cseq parseCommandSequence() throws Exception
  // <command sequence> ::= { <command list> }
  {
                parseOBR() ;
    Ccmd[] cl = parseCommandList() ;
                parseCBR() ;

    return new Cseq(cl) ;
  }
  
  private Ccmd[] parseCommandList() throws Exception
  // <command list> ::= 
  //            <empty> 
  //          | <command list> <command>
  {
    List l = new List();
    
    while (!isCBR()) l.add(parseCommand()) ; 
 
    return l.cmdArray(); 
  }

  private Cinput parseInputCommand() throws Exception
  // <input command>  ::= read  ( <left expression list> ) ;
  {
                 parseREAD() ;
                 parseOPA() ;
    Clexpr[] l = parseLeftExpressionList() ;
                 parseCPA() ;
                 parseSEM() ;

    return new Cinput(l);
  }

  private Coutput parseOutputCommand() throws Exception
  // <output command> ::= write ( <right expression list> ) ;
  {
                 parseWRITE() ;
                 parseOPA() ;
    Crexpr[] r = parseRightExpressionList() ;
                 parseCPA() ;
                 parseSEM() ;

    return new Coutput(r);
  }
  
  private Creturn parseReturn() throws Exception
  // <return>  ::= return ( <right expression> ) ;
  {
                 parseRETURN() ;
                 parseOPA() ;
    Crexpr r =   parseRightExpression() ;
                 parseCPA() ;
                 parseSEM() ;

    return new Creturn(r);
  }
  
  private Clexpr[] parseLeftExpressionList() throws Exception
  // <left expression list> ::= 
  //            <left expression> 
  //          | <left expression list> , <left expression> 
  {
     List l = new List();
     l.add(parseLeftExpression()) ;

     while (isCOM()) 
       { parseCOM() ; l.add(parseLeftExpression()) ; }

     return l.lexprArray() ;
  }
 
  private Crexpr[] parseRightExpressionList() throws Exception
  // <right expression list> ::=    
  //            <right expression> 
  //          | <right expression list> , <right expression> 
  {
     List l = new List();
     l.add(parseRightExpression()) ;

     while (isCOM()) 
       { parseCOM() ; l.add(parseRightExpression()) ; }

     return l.rexprArray() ;
  }

  private Clexpr parseLeftExpression() throws Exception
  // <left expression> ::= 
  //            <variable> 
		//          | this . <integral literal>
  //          | <variable> . <integral literal>
  // <variable> ::= <identifier>
  {
			
			 if (isTHIS())
					{ parseTHIS() ; parseDOT() ; return new CthisField(parseIntegralLiteral()) ; }
    String var = parseIdentifier();

    curvt.addLocal(var) ;

    if (isDOT()) 
    {  parseDOT() ;  return new Cfield(var, parseIntegralLiteral()) ; }
    else return new Cvar(var) ;
  }
 
  private Crexpr parseRightExpression() throws Exception
  // <right expression> ::= <expression>
  {
     return parseExpression() ; 
  }
 
  private Crexpr parseExpression() throws Exception
  // <expression> ::= 
  //            <term> 
  //          | - <term> 
  //          | <expression> <additive operator> <term>
  {
    Crexpr expr;

    if (isMIN()) 
    {  parseMIN() ;  expr = new Cminus(parseTerm()) ; }
    else             expr = parseTerm() ;

    while (isPLU()|isMIN()) 
    {  int op = parseA0() ; expr = new Cbinary(expr, op, parseTerm()) ; }

    return expr;
  }

  private int parseA0() throws Exception
  // <additive operator> ::= + | -
  {
     if (isPLU()) 
          { parsePLU() ; return Cbinary.PLUS ; }
     else { parseMIN() ; return Cbinary.MINUS ; }
  }

  private Crexpr parseTerm() throws Exception
  // <term>   ::= 
  //            <factor> 
  //          | <term> <multiplicative operator> <factor>
  //
  // <multiplicative operator> ::= * | / | %
  {
    Crexpr term = parseFactor() ;

    while (isTIM()|isSLA()|isREM()) 
    { term = new Cbinary(term, parseMO(), parseFactor()) ; }

    return term;
  }

  private int parseMO() throws Exception
  // <multiplicative operator> ::= * | / | %
  {
     if (isTIM()) { parseTIM() ; return Cbinary.TIMES ; } else
     if (isSLA()) { parseSLA() ; return Cbinary.DIV   ; } else
                  { parseREM() ; return Cbinary.MOD   ; } 
  }
 
  private Crexpr parseFactor() throws Exception
  /* <factor> ::=
                <left expression> 
              | <integral literal> 
              | this 
              | null 
              | new / <integral literal>
              | <method call>
              | ( <expression> )

    <method call> ::= <target> <method name> ( <actual parameter list> )

    <target> ::=
                <empty>
              | <variable> .
              | super .
   */
  {
     if (isIntegralLiteral()) 
        return new Ci(parseIntegralLiteral()) ; 
     if (isTHIS())            
        { parseTHIS() ; 
									 if (!isDOT()) return new Cthis() ; 
								  parseDOT();
								  return new CthisField(parseIntegralLiteral()) ;
								}  
     if (isNULL())            
        { parseNULL() ; return new Cnull() ; }  
     if (isNEW())    
        { parseNEW() ; parseSLA() ; return new Cnew(parseIntegralLiteral()) ;} 
     if (isOPA())    
        {  parseOPA() ; Crexpr r = parseExpression() ; parseCPA() ;  return r ; }

     /* Here 5 possible cases remains
   
        1) <variable> (not followed by "." nor "(")
        2) <variable> . <integral literal>
        3) <variable> . <method name> ( < actual parameter list > )
        4) super . <method name> ( < actual parameter list > )
        5) <method name> ( < actual parameter list >  )
   
     */

     int cas = 0; // On ne connaît pas la réponse (le cas de figure réel).
     String var   = null ;
     String methn = null ;
     int   intLit = -1 ; 
     String    id = null;

     if (isSUPER()) { parseSUPER(); cas = 4; }
     else id = parseIdentifier();

     if (isDOT()) // cas 2, 3 ou 4
     { 
        parseDOT();
        if (isIntegralLiteral()) 
             { cas = 2; var = id ; intLit = parseIntegralLiteral(); }
        else {  // cas 3 ou 4
                if (cas!=4)
                {  cas = 3; var = id ; }
                methn = parseIdentifier() ;
             }
     }
     else // cas 1 ou 5
     {
        if (isOPA()) // cas 5
             {  cas = 5; methn = id ; }
        else {  cas = 1; var   = id ; }
     }

     RuleTree factor = null;

     switch (cas)
     {
        case 1 : curvt.addLocal(var) ; 
                 return new Cvar(var) ;
 
        case 2 : curvt.addLocal(var) ;
                 return new Cfield(var, intLit) ;

        case 3 : {                    parseOPA() ;
                   Crexpr[] aparam =  parseActualParameterList() ;
                                      parseCPA() ; 
                   curvt.addLocal(var) ;
                   return new CvariableCall(var, methn, aparam) ; 
                 }

        case 4 : {                    parseOPA() ;
                   Crexpr[] aparam =  parseActualParameterList() ;
                                      parseCPA() ; 
                   return new CsuperCall(methn, aparam) ; 
                 }
        case 5 : {                    parseOPA() ;
                   Crexpr[] aparam =  parseActualParameterList() ;
                                      parseCPA() ; 
                   return new CsimpleCall(methn, aparam) ; 
                 }
        default : break; 
     }
     
     return null ; // impossible

  }

  public Crexpr[] parseActualParameterList() throws Exception
  // <actual parameter list> ::=
  //            <empty>
  //          | <right expression list>
  { 
     if (isCPA()) 
          return new Crexpr[0];
     else return parseRightExpressionList() ;
  }

  public CParser(text slipFile) throws Exception
  {
    lex = new Lexer(slipFile);
    nextSymbol();

  }
		
  public CParser(String fileName) throws Exception
  {
    this(new text(fileName)) ;
  }

  private void nextSymbol() throws Exception
  { 
    cur = lex.nextSymbol();
  }

  public Cprog parse() throws Exception
  {  
     return parseProgram(); 

  }

  public static void main(String[] arg) throws Exception
  {
    CParser toto = new CParser(arg[0]) ;
    Cprog   lulu = toto.parse() ;
    System.out.println("" + lulu) ;
    
    System.out.println("----------------------------------------") ;
    if (lulu != null) System.out.println("" + lulu.amt) ;

    Prog    titi = lulu.translate() ;
    System.out.println("----------------------------------------") ;
    System.out.println("" + titi) ;
  }
}

