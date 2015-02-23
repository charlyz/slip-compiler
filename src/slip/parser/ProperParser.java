package slip.parser; // Début 12.2.2005 : 14:45 Break : 15:08
                     // Reprise 21:15 Break 24:25
                     // Reprise 13.2.2005 : 15:39 STOP 16:58
import slip.lexer.*;

public class ProperParser
{

  private Lexer lex ;
  private Terminal cur ; // Current symbol ;
  private TipTree tip ; // TipTree(cur) (if cur==null) ; null (if cur == null)

  private TipTree parseIdentifier() throws Exception
  {
    TipTree ct = tip;
    if ((cur!=null)&&(cur.sort==Terminal.ID)) { nextSymbol(); System.out.println(cur); }
    else throw new ParserException("Un identificateur " +
                                   "était attendu, au lieu de " + cur);
    return ct;
  }

  private boolean isIdentifier()
  { 
    return ((cur!=null)&&(cur.sort==Terminal.ID));
  }

  private TipTree parseIntegralLiteral() throws Exception
  {
    TipTree ct = tip;
    if ((cur!=null)&&(cur.sort==Terminal.IL)) { nextSymbol(); System.out.println(cur); }
    else throw new ParserException("Une constante entière " +
                                   "était attendue, au lieu de " + cur);
    return ct;
  }

  private boolean isIntegralLiteral()
  {
    return ((cur!=null)&&(cur.sort==Terminal.IL));
  }

  private TipTree parseTerminal(Terminal t) throws Exception
  {
    TipTree ct = tip;
    if (t==cur){ nextSymbol(); System.out.println(cur); }
    else throw new ParserException("Le symbole " + t +
                                   " était attendu, au lieu de " + cur);
    return ct ;
  }



// <special character> ::= 
//   , | . | ( | ) | "|" | & | { | } | / | > | < | = | ! | ; | * | + | - | %
// Il y a 18 caractères spéciaux.

  private TipTree parseCOM() throws Exception
  {
    return parseTerminal(SpecialCharacter.COM);     
  }

  private boolean isCOM()
  {
    return cur == SpecialCharacter.COM;     
  }
 
  private TipTree parseDOT() throws Exception
  {
    return parseTerminal(SpecialCharacter.DOT);     
  }
 
  private boolean isDOT()
  {
    return cur == SpecialCharacter.DOT;     
  }
 
  private TipTree parseOPA() throws Exception
  {
    return parseTerminal(SpecialCharacter.OPA);     
  }
 
  private boolean isOPA()
  {
    return cur == SpecialCharacter.OPA;     
  }
 
  private TipTree parseCPA() throws Exception
  {
    return parseTerminal(SpecialCharacter.CPA);     
  }

  private boolean isCPA()
  {
    return cur == SpecialCharacter.CPA;     
  }
 
  private TipTree parseOR() throws Exception
  {
    return parseTerminal(SpecialCharacter.OR);     
  }

  private boolean isOR()
  {
    return cur == SpecialCharacter.OR;     
  }

  private TipTree parseAND() throws Exception
  {
    return parseTerminal(SpecialCharacter.AND);     
  }
 
  private boolean isAND()
  {
    return cur == SpecialCharacter.AND;     
  }


  private TipTree parseOBR() throws Exception
  {
    return parseTerminal(SpecialCharacter.OBR);     
  }
  
  private boolean isOBR()
  {
    return cur == SpecialCharacter.OBR;     
  }

  private TipTree parseCBR() throws Exception
  {
    return parseTerminal(SpecialCharacter.CBR);     
  }
   
  private boolean isCBR()
  {
    return cur == SpecialCharacter.CBR;     
  }

  private TipTree parseSLA() throws Exception
  {
    return parseTerminal(SpecialCharacter.SLA);     
  }

  private boolean isSLA()
  {
    return cur == SpecialCharacter.SLA;     
  }

  private TipTree parseGT() throws Exception
  {
    return parseTerminal(SpecialCharacter.GT);     
  }
 
  private boolean isGT()
  {
    return cur == SpecialCharacter.GT;     
  }

  private TipTree parseLT() throws Exception
  {
    return parseTerminal(SpecialCharacter.LT);     
  }
 
  private boolean isLT()
  {
    return cur == SpecialCharacter.LT;     
  }

  private TipTree parseEQU() throws Exception
  {
    return parseTerminal(SpecialCharacter.EQU);     
  }

  private boolean isEQU()
  {
    return cur == SpecialCharacter.EQU;     
  }

  private TipTree parseNOT() throws Exception
  {
    return parseTerminal(SpecialCharacter.NOT);     
  }
 
  private boolean isNOT()
  {
    return cur == SpecialCharacter.NOT;     
  }

 
  private TipTree parseSEM() throws Exception
  {
    return parseTerminal(SpecialCharacter.SEM);     
  }
 
  private TipTree parseTIM() throws Exception
  {
    return parseTerminal(SpecialCharacter.TIM);     
  }

  private boolean isTIM()
  {
    return cur == SpecialCharacter.TIM;     
  }

 
  private TipTree parsePLU() throws Exception
  {
    return parseTerminal(SpecialCharacter.PLU);     
  }

  private boolean isPLU()
  {
    return cur == SpecialCharacter.PLU;     
  }

  private TipTree parseMIN() throws Exception
  {
    return parseTerminal(SpecialCharacter.MIN);     
  }

  private boolean isMIN()
  {
    return cur == SpecialCharacter.MIN;     
  }

  private TipTree parseREM() throws Exception
  {
    return parseTerminal(SpecialCharacter.REM);     
  }

  private boolean isREM()
  {
    return cur == SpecialCharacter.REM;     
  }


// <reserved word> ::= if | else | read | write | while | this | null | new
//                  | return | super
// Il y a 10 mots réservés.

  private TipTree parseIF() throws Exception
  {
    return parseTerminal(ReservedWord.IF);     
  }

  private boolean isIF() 
  {
    return cur == ReservedWord.IF;     
  }

  private TipTree parseELSE() throws Exception
  {
    return parseTerminal(ReservedWord.ELSE);     
  }

  private boolean isELSE() 
  {
    return cur == ReservedWord.ELSE;     
  }

  private TipTree parseREAD() throws Exception
  {
    return parseTerminal(ReservedWord.READ);     
  }

  private boolean isREAD() 
  {
    return cur == ReservedWord.READ;     
  }


  private TipTree parseWRITE() throws Exception
  {
    return parseTerminal(ReservedWord.WRITE);     
  }

  private boolean isWRITE() 
  {
    return cur == ReservedWord.WRITE;     
  }

  private TipTree parseWHILE() throws Exception
  {
    return parseTerminal(ReservedWord.WHILE);     
  }

  private boolean isWHILE() 
  {
    return cur == ReservedWord.WHILE;     
  }

  private TipTree parseTHIS() throws Exception
  {
    return parseTerminal(ReservedWord.THIS);     
  }

  private boolean isTHIS() 
  {
    return cur == ReservedWord.THIS;     
  }

  private TipTree parseNULL() throws Exception
  {
    return parseTerminal(ReservedWord.NULL);     
  }

  private boolean isNULL() 
  {
    return cur == ReservedWord.NULL;     
  }

  private TipTree parseNEW() throws Exception
  {
    return parseTerminal(ReservedWord.NEW);     
  }

  private boolean isNEW() 
  {
    return cur == ReservedWord.NEW;     
  }

  private TipTree parseRETURN() throws Exception
  {
    return parseTerminal(ReservedWord.RETURN);     
  }

  private boolean isRETURN() 
  {
    return cur == ReservedWord.RETURN;     
  }

  private TipTree parseSUPER() throws Exception
  {
    return parseTerminal(ReservedWord.SUPER);     
  }

  private boolean isSUPER() 
  {
    return cur == ReservedWord.SUPER;     
  }

  private RuleTree parseProgram() throws Exception
  // <program> ::=   <method> 
  //              | <program> <method>
  {
     RuleTree r = new RuleTree(RuleTree.PROG, parseMethod());
     int      d = 1;

     while (cur != null) 
       { r = new RuleTree(RuleTree.PROG, r, parseMethod()); d++; }

     r.setDepht(d);
     return r ;
  }

  private RuleTree parseMethod() throws Exception
  // <method>  ::= <name> ( <parameter list> ) <body>
  {
     return new RuleTree(RuleTree.METH,
     parseName(),
     parseOPA(),
     parseParameterList(),
     parseCPA(),
     parseBody());  
  }

  private RuleTree parseName() throws Exception
  // <name>    ::=   <method name> 
  //               | <method name> / <integral literal>
  {
     RuleTree methn = parseMethodName();

     if (isSLA()) 
          return new RuleTree(RuleTree.NAME, methn, parseSLA(), parseIntegralLiteral());
     else return new RuleTree(RuleTree.NAME, methn) ;
  }

  private RuleTree parseMethodName() throws Exception
  // <method name> ::=   <identifier> 
  {
     return new RuleTree(RuleTree.METHN, parseIdentifier());
  }
   

  private RuleTree parseParameterList() throws Exception
  // <parameter list> ::= 
  //            <empty> 
  //          | <non empty parameter list>
  //
  // <non empty parameter list> ::= 
  //             <formal parameter> 
  //           | <non empty parameter list> , <formal parameter>
  {
     RuleTree r; int d = 0; // d == depht
     if (isIdentifier())
        {
           RuleTree nepl = 
           new RuleTree(RuleTree.NEPL, new RuleTree(RuleTree.FORM, parseIdentifier()));
           d = 1;

           while (isCOM())
           {  nepl = new RuleTree(RuleTree.NEPL, 
                                 nepl, 
                                 parseCOM(), 
                                 new RuleTree(RuleTree.FORM, parseIdentifier()));
              d++;
           }
           r =  new RuleTree(RuleTree.PARAL, nepl) ;
        }
     else  r =  new RuleTree(RuleTree.PARAL, new RuleTree(RuleTree.EMPTY)) ;

     r.setDepht(d);
     return r ;
  }

  private RuleTree parseBody() throws Exception
  // <body>    ::= <command sequence>
  {
    return new RuleTree(RuleTree.BODY, parseCommandSequence());
  }


  private RuleTree parseCommand() throws Exception
  // <command> ::=   <assignment> 
  //           | <conditional command> 
  //           | <while command>
  //           | <command sequence> 
  //           | <input command> 
  //           | <output command>
  //           | <return>
  {
   RuleTree cmd;

   if (isIF())    cmd = parseConditionalCommand(); else
   if (isWHILE()) cmd = parseWhileCommand();       else
   if (isOBR())   cmd = parseCommandSequence();    else
   if (isREAD())  cmd = parseInputCommand();       else
   if (isWRITE()) cmd = parseOutputCommand();      else
   if (isRETURN())cmd = parseReturn();            else
                  cmd = parseAssignment();
   
   return new RuleTree(RuleTree.CMD, cmd) ;
  }

   
  private RuleTree parseConditionalCommand() throws Exception
  // <conditional command> ::= 
  //            if <condition> <command> 
  //          | if <condition> <command> else <command>
  {
    SyntacticTree ift  = parseIF();
    RuleTree cond = parseCondition();
    RuleTree cmd  = parseCommand();

    if (isELSE()) 
         return new RuleTree(RuleTree.IF, ift, cond, cmd, parseELSE(), parseCommand());
    else return new RuleTree(RuleTree.IF, ift, cond, cmd);
  }
  
   
  private RuleTree parseAssignment() throws Exception
  // <assignment> ::= <left expression> = <right expression> ;
  {
    return new RuleTree(RuleTree.ASS,
                        parseLeftExpression(),
                        parseEQU(),
                        parseRightExpression(),
                        parseSEM());
  }
  
   
  private RuleTree parseCondition() throws Exception
  // <condition> ::= ( <disjunction> )
  {
     return new RuleTree(RuleTree.COND,
                         parseOPA(),
                         parseDisjunction(),
                         parseCPA());
  }
  
   
  private RuleTree parseDisjunction() throws Exception
  // <disjunction> ::= 
  //            <conjunction> 
  //          | <disjunction> "|" <conjunction>
  {
     RuleTree disj = new RuleTree(RuleTree.DISJ, parseConjunction());

     while (isOR()) 
       disj = new RuleTree(RuleTree.DISJ, disj, parseOR(), parseConjunction()); 
 
     return disj;
  }
    
  private RuleTree parseConjunction() throws Exception
  // <conjunction> ::=
  //             <basic condition>
  //           | <conjunction> & <basic condition>
  {
     RuleTree conj = new RuleTree(RuleTree.CONJ, parseBasicCondition());

     while (isAND())
       conj = new RuleTree(RuleTree.CONJ, conj, parseAND(), parseBasicCondition()); 

     return conj;
  }
  
  private RuleTree parseBasicCondition() throws Exception
  // <basic condition> ::=
  //            <relation>
  //          | ! <condition>
  {
     if (isNOT()) 
        return new RuleTree(RuleTree.BASIC, parseNOT(), parseCondition());
     else return new RuleTree(RuleTree.BASIC, parseRelation()); 
  }
  
  private RuleTree parseRelation() throws Exception
  // <relation> ::= <right expression> <comparison operator> <right expression>
  {
     return new RuleTree(RuleTree.REL, 
                         parseRightExpression(),
                         parseComparisonOperator(),
                         parseRightExpression());
  }
   
  private RuleTree parseComparisonOperator() throws Exception
  // <comparison operator> ::= = = | ! = | < | > | < = | > =
  {
     if (isEQU()) return new RuleTree(RuleTree.CO, parseEQU(), parseEQU());    else
     if (isNOT()) return new RuleTree(RuleTree.CO, parseNOT(), parseEQU());    else
     if (isLT())
        { TipTree lt = parseLT(); 
          if (isEQU()) 
               return new RuleTree(RuleTree.CO, lt, parseEQU());
          else return new RuleTree(RuleTree.CO, lt); }                         else
        { TipTree gt = parseGT(); 
          if (isEQU()) 
               return new RuleTree(RuleTree.CO, gt, parseEQU());
          else return new RuleTree(RuleTree.CO, gt);   }
  }
   
  private RuleTree parseWhileCommand() throws Exception
  // <while command> ::= while <condition> <command>
  {
     return new RuleTree(RuleTree.WHILE,
                         parseWHILE(),
                         parseCondition(),
                         parseCommand());
  }

  private RuleTree parseCommandSequence() throws Exception
  // <command sequence> ::= { <command list> }
  {
    return new RuleTree(RuleTree.CMDS,
                        parseOBR(),
                        parseCommandList(),
                        parseCBR());
  }
  
  private RuleTree parseCommandList() throws Exception
  // <command list> ::= 
  //            <empty> 
  //          | <command list> <command>
  {
    RuleTree cmdl = new RuleTree(RuleTree.CMDL, new RuleTree(RuleTree.EMPTY));
    
    while (!isCBR()) 
      cmdl = new RuleTree(RuleTree.CMDL, cmdl, parseCommand()); 
 
    return cmdl; 
  }

  private RuleTree parseInputCommand() throws Exception
  // <input command>  ::= read  ( <left expression list> ) ;
  {
    return new RuleTree(RuleTree.INPUT, 
                        parseREAD(),
                        parseOPA(),
                        parseLeftExpressionList(),
                        parseCPA(),
                        parseSEM());
  }

  private RuleTree parseOutputCommand() throws Exception
  // <output command> ::= write ( <right expression list> ) ;
  {
    return new RuleTree(RuleTree.OUTPUT,
                        parseWRITE(),
                        parseOPA(),
                        parseRightExpressionList(),
                        parseCPA(),
                        parseSEM());
  }
  
  private RuleTree parseReturn() throws Exception
  // <return>  ::= return ( <right expression> );
  {
    return new RuleTree(RuleTree.RCMD,
                        parseRETURN(),
                        parseOPA(),
                        parseRightExpressionList(),
                        parseCPA(),
                        parseSEM());
  }
  
  private RuleTree parseLeftExpressionList() throws Exception
  // <left expression list> ::= 
  //            <left expression> 
  //          | <left expression list> , <left expression> 
  {
     RuleTree lexl = new RuleTree(RuleTree.LEXL, parseLeftExpression());

     while (isCOM()) 
       lexl = new RuleTree(RuleTree.LEXL, lexl, parseCOM(), parseLeftExpression()); 

     return lexl;
  }
 
  private RuleTree parseRightExpressionList() throws Exception
  // <right expression list> ::=    
  //            <right expression> 
  //          | <right expression list> , <right expression> 
  {
     RuleTree rexl = new RuleTree(RuleTree.REXL, parseRightExpression());

     while (isCOM()) 
       rexl = new RuleTree(RuleTree.REXL, rexl, parseCOM(), parseRightExpression()); 
    
     return rexl;
  }

  private RuleTree parseVariable() throws Exception
  // <variable> ::= <identifier>
  {
     return new RuleTree(RuleTree.VAR, parseIdentifier());
  }

  private RuleTree parseLeftExpression() throws Exception
  // <left expression> ::= 
  //            <variable> 
  //          | <variable> . <integral literal>
  {
    RuleTree var = parseVariable();

    if (isDOT()) 
         return new RuleTree(RuleTree.LEXPR, var, parseDOT(), parseIntegralLiteral()); 
    else return new RuleTree(RuleTree.LEXPR, var);
  }
 
  private RuleTree parseRightExpression() throws Exception
  // <right expression> ::= <expression>
  {
     return new RuleTree(RuleTree.REXPR, parseExpression()); 
  }
 
  private RuleTree parseExpression() throws Exception
  // <expression> ::= 
  //            <term> 
  //          | - <term> 
  //          | <expression> <additive operator> <term>
  {
    RuleTree expr;

    if (isMIN()) 
         expr = new RuleTree(RuleTree.EXPR, parseMIN(), parseTerm());
    else expr = new RuleTree(RuleTree.EXPR, parseTerm());

    while (isPLU()|isMIN()) 
      expr = new RuleTree(RuleTree.EXPR, expr, parseA0(), parseTerm());

    return expr;
  }

  private RuleTree parseA0() throws Exception
  // <additive operator> ::= + | -
  {
     if (isPLU()) 
          return new RuleTree(RuleTree.AO, parsePLU());
     else return new RuleTree(RuleTree.AO, parseMIN());
  }

  private RuleTree parseTerm() throws Exception
  // <term>   ::= 
  //            <factor> 
  //          | <term> <multiplicative operator> <factor>
  //
  // <multiplicative operator> ::= * | / | %
  {
    RuleTree term = new RuleTree(RuleTree.TERM, parseFactor());
    while (isTIM()|isSLA()|isREM()) 
      term = new RuleTree(RuleTree.TERM, term, parseMO(), parseFactor());

    return term;
  }

  private RuleTree parseMO() throws Exception
  // <multiplicative operator> ::= * | / | %
  {
     if (isTIM()) 
          return new RuleTree(RuleTree.MO, parseTIM()); else
     if (isSLA()) 
          return new RuleTree(RuleTree.MO, parseSLA()); 
     else return new RuleTree(RuleTree.MO, parseREM());
  }
 
  private RuleTree parseFactor() throws Exception
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
        return new RuleTree(RuleTree.FACT, parseIntegralLiteral()); else
     if (isTHIS())            
        return new RuleTree(RuleTree.FACT, parseTHIS());            else
     if (isNULL())            
        return new RuleTree(RuleTree.FACT, parseNULL());            else
     if (isNEW())    
        return new RuleTree(RuleTree.FACT, parseNEW(), parseSLA(), parseIntegralLiteral()); else
     if (isOPA())    
        return new RuleTree(RuleTree.FACT, parseOPA(), parseExpression(), parseCPA()); 

     /* Here 5 possible cases remains
   
        1) <variable> (not followed by "." nor "(")
        2) <variable> . <integral literal>
        3) <variable> . <method name> ( < actual parameter list > )
        4) super . <method name> ( < actual parameter list > )
        5) <method name> ( < actual parameter list >  )
   
     */

     int cas = 0; // On ne connaît pas la réponse (le cas de figure réel).
     TipTree  superTree = null;
     RuleTree var = null;
     TipTree  dot = null;
     RuleTree methn = null;
     TipTree  intLit = null; 
     TipTree  id = null;

     if (isSUPER()) { superTree = parseSUPER(); cas = 4; }
     else id = parseIdentifier();

     if (isDOT()) // cas 2, 3 ou 4
     { 
        dot = parseDOT();
        if (isIntegralLiteral()) { cas = 2; intLit = parseIntegralLiteral(); }
        else {  // cas 3 ou 4
                if (cas!=4)
                {  cas = 3; var = new RuleTree(RuleTree.VAR, id); }
                methn = parseMethodName();
             }
     }
     else // cas 1 ou 5
     {
        if (isOPA()) // cas 5
             {  cas = 5; methn = new RuleTree(RuleTree.METHN, id); }
        else {  cas = 1; var   = new RuleTree(RuleTree.VAR, id); }
     }

     RuleTree factor = null;

     switch (cas)
     {
        case 1 : factor = new RuleTree(RuleTree.FACT, var); break;
        case 2 : factor = new RuleTree(RuleTree.FACT, var, dot, intLit); break;
        case 3 : factor = new RuleTree(RuleTree.FACT,
                                       new RuleTree(RuleTree.TARGET, var, dot),
                                       methn,
                                       parseOPA(),
                                       parseActualParameterList(),
                                       parseCPA()); break;
        case 4 : factor = new RuleTree(RuleTree.FACT,
                                       new RuleTree(RuleTree.TARGET, superTree, dot),
                                       methn,
                                       parseOPA(),
                                       parseActualParameterList(),
                                       parseCPA()); break;
        case 5 : factor = new RuleTree(RuleTree.FACT,
                                       new RuleTree(RuleTree.TARGET, new RuleTree(RuleTree.EMPTY)),
                                       methn,
                                       parseOPA(),
                                       parseActualParameterList(),
                                       parseCPA()); break; 
        default : break; 
     }
     
     return factor;

  }

  public RuleTree parseActualParameterList() throws Exception
  // <actual parameter list> ::=
  //            <empty>
  //          | <right expression list>
  { 
     if (isCPA()) 
          return new RuleTree(RuleTree.APL, new RuleTree(RuleTree.EMPTY));
     else return new RuleTree(RuleTree.APL, parseRightExpressionList());
  }

  public ProperParser(String fileName) throws Exception
  {
    lex = new Lexer(fileName);
    nextSymbol();

  }

  private void nextSymbol() throws Exception
  { 
    cur = lex.nextSymbol();
    if (cur!=null) tip = new TipTree(cur);
    else tip = null;
  }

  public RuleTree parse()
  {  
     RuleTree st;

     try { st = parseProgram(); }
     catch (Exception e)
     {  
        System.out.println(e);
        return null; 
     }
     return st ;
  }

  public static void main(String[] arg) throws Exception
  {
    ProperParser toto = new ProperParser(arg[0]);
    //System.out.println(toto.parse());
    
  }
}

