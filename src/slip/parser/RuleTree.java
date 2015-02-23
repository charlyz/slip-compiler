package slip.parser; // 12.2.2005  13h58 : 14h43
                     // 14.2.2005  10h38 : 11h32
import  slip.lexer.*;


public class RuleTree extends SyntacticTree
{
  static private final char ELN = '\n';
  static public final int PROG = 0 ;
  static public final int METH = 1 ;
  static public final int NAME = 2 ;
  static public final int METHN = 3 ;
  static public final int PARAL = 4 ;
  static public final int NEPL = 5 ;
  static public final int FORM = 6 ;
  static public final int BODY = 7 ;
  static public final int CMD = 8 ;
  static public final int ASS = 9 ;
  static public final int IF = 10 ;
  static public final int WHILE = 11 ;
  static public final int CMDS = 12 ;
  static public final int INPUT = 13 ;
  static public final int OUTPUT = 14 ;
  static public final int RCMD = 15 ;
  static public final int CMDL  = 16 ;
  static public final int COND = 17 ;
  static public final int DISJ= 18 ;
  static public final int CONJ = 19 ;
  static public final int BASIC = 20 ;
  static public final int REL = 21 ;
  static public final int CO = 22 ;
  static public final int LEXPR = 23 ;
  static public final int REXPR = 24 ;
  static public final int EXPR = 25 ;
  static public final int AO = 26 ;
  static public final int TERM = 27 ;
  static public final int MO = 28 ;
  static public final int FACT = 29 ;
  static public final int VAR = 30 ;
  static public final int METHC = 31 ;
  static public final int TARGET = 32 ;
  static public final int APL = 33 ;
  static public final int LEXL = 34 ;
  static public final int REXL = 35 ;
  static public final int EMPTY = 36 ;
  
  int lp; // Non terminal symbol (left part of a production rule)
  public SyntacticTree[] rp; // Right part of a production rule
  int depht = 0 ; // depht of recursive rules
  
  public void setDepht(int d){ depht = d ; }
  
  public int sort(){ return lp ; }

  public RuleTree(int X, SyntacticTree[] alpha) // X --> alpha is the rule
  { lp = X; rp = alpha; }

  public RuleTree(int X) // X -->  is the rule
  { this(X, new SyntacticTree[0]); }

  public RuleTree(int X, SyntacticTree Y1) // X --> Y1 is the rule
  { this(X, new SyntacticTree[]{Y1}); }

  public RuleTree(int X, SyntacticTree Y1, SyntacticTree Y2) // X --> Y1 Y2 is the rule
  { this(X, new SyntacticTree[]{Y1, Y2}); }

  public RuleTree(int X, SyntacticTree Y1, SyntacticTree Y2, 
                         SyntacticTree Y3) // X --> Y1 Y2 Y3 is the rule
  { this(X, new SyntacticTree[]{Y1, Y2, Y3}); }
 
  public RuleTree(int X, SyntacticTree Y1, SyntacticTree Y2, 
                         SyntacticTree Y3, SyntacticTree Y4) // X --> Y1 Y2 Y3 Y4 is the rule
  { this(X, new SyntacticTree[]{Y1, Y2, Y3, Y4}); }

  public RuleTree(int X, SyntacticTree Y1, SyntacticTree Y2, 
                         SyntacticTree Y3, SyntacticTree Y4, 
                         SyntacticTree Y5) // X --> Y1 Y2 Y3 Y4 Y5  is the rule
  { this(X, new SyntacticTree[]{Y1, Y2, Y3, Y4, Y5}); }

  public RuleTree(int X, SyntacticTree Y1, SyntacticTree Y2, 
                         SyntacticTree Y3, SyntacticTree Y4, 
                         SyntacticTree Y5, SyntacticTree Y6) // X --> Y1 Y2 Y3 Y4 Y5 Y6 is the rule
  { this(X, new SyntacticTree[]{Y1, Y2, Y3, Y4, Y5, Y6}); }

  public RuleTree[] methodArray()
  // this.sort() == PROG  & this.depht > 0
  {
     int d = depht;
     RuleTree[] r = new RuleTree[d];
     d--;
     RuleTree p = this ;
     while (d!=0) { r[d] = (RuleTree)p.rp[2]; d--; p = (RuleTree)p.rp[1]; }
     r[0] = (RuleTree)p.rp[1] ;

     return r ;
  }

  public String methodName()
  // Pre : sort() == METH | sort() == NAME
  {
     if (sort() == METH) return ((RuleTree)rp[0]).methodName() ;
     if (sort() == NAME) return ((RuleTree)rp[0]).toString() ;
     return null ;
  }

  public int level()
  // Pre : sort() == METH | sort() == NAME
  {
     if (sort() == METH) return ((RuleTree)rp[0]).level() ;
     if (sort() == NAME) 
        if (rp.length == 1) return -1 ;
        else return ((IntegralLiteral)((TipTree)rp[2]).t).valueOf();
     return -2 ;   
  }

  public String[] formalParameterArray()
  // Pre : sort() == METH | sort() = PARAL
  {
     if (sort() == METH) return ((RuleTree)rp[2]).formalParameterArray() ;
     if (sort() == PARAL) 
     {   
        String[] r = new String[depht];
        int      d = depht--;
        RuleTree l = (RuleTree)rp[0];
        while (d > 0)
        { r[d] = ((RuleTree)(l.rp[2])).rp[0].toString();
          d--;
          l = (RuleTree)(l.rp[0]);
        }
        if (d == 0) r[0] = ((RuleTree)(l.rp[0])).rp[0].toString();
     }
     return null ;   

  }

  private String toString(int indent, int incIndent)
  { 
    int i = 0;
    String s = spaces(indent) + "[ " + leftPart() + " : " ;
    while (i != rp.length)
    { s += ELN; 
      SyntacticTree st = rp[i];
      if (st instanceof TipTree)
           s += spaces(indent + incIndent) + (TipTree)(st);
      else s += ((RuleTree)(st)).toString(indent + incIndent, incIndent);
      i++ ; 
    }
    return s + ELN + spaces(indent) + "]" ; 
  }

  private String spaces(int l)
  {
    char[] tspaces = new char[l];
    int i = 0;
    while (i != tspaces.length) { tspaces[i] = ' '; i++; }

    return String.valueOf(tspaces);
  }

  public String toString(){ return toString(0, 1); }
  
  private String leftPart()
  {
    switch (lp)
    {
      case      PROG : return "<program>";   
      case      METH : return "<method>";  
      case      NAME : return "<name>";   
      case      METHN : return "<method name>";  
      case      PARAL : return "<parameter list>";  
      case      NEPL : return "<non empty parameter list>";   
      case      FORM : return "<formal parameter>";  
      case      BODY : return "<body>";
      case      CMD : return "<command>";  
      case      ASS : return "<assignment>";  
      case      IF : return "<conditional command>";  
      case      WHILE : return "<while command>";  
      case      CMDS : return "<command sequence>";   
      case      INPUT : return "<input command>";   
      case      OUTPUT : return "<output command>";   
      case      RCMD : return "<return>";   
      case      CMDL : return "<command list>";   
      case      COND : return "<condition>";   
      case      DISJ: return "<disjunction>";   
      case      CONJ : return "<conjunction>";   
      case      BASIC : return "<basic condition>";   
      case      REL : return "<relation>";   
      case      CO : return "<comparison operator>";   
      case      LEXPR : return "<left expression>";   
      case      REXPR : return "<right expression>";   
      case      EXPR : return "<expression>";  
      case      AO : return "<additive operator>";   
      case      TERM : return "<term>";  
      case      MO : return "<multiplicative operator> ";   
      case      FACT : return "<factor> ";   
      case      VAR : return "<variable>";   
      case      METHC : return "<method call>";   
      case      TARGET : return "<target>";  
      case      APL : return "<actual parameter list>";   
      case      LEXL : return "<left expression list>";   
      case      REXL : return "<right expression list>";   
      case      EMPTY : return "<empty>";   
      default   :       return "Turlututu !";
    }

  }

}

