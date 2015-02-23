package slip.parser;
import  slip.internal.*;

public class Crel extends Ccond
{
  public static final int EQ = 0 ;
  public static final int NE = 1 ;
  public static final int LT = 2 ;
  public static final int GT = 3 ;
  public static final int LE = 4 ;
  public static final int GE = 5 ;
  
  public final Crexpr r1;
  public final int cop;
  public final Crexpr r2;

  public Crel(Crexpr r1, int cop, Crexpr r2)
  { super(REL) ; this.r1 = r1 ; this.cop = cop ; this.r2 = r2 ; }

  public String toString() { return "" + r1 + str(cop) + r2 + "" ; }

  public Stmt translate(Stmt ifTrue, Stmt ifFalse)
  {
    /*
       r1 == r2 -->  if r1 == r2 then ifTrue  else ifFalse
       r1 != r2 -->  if r1 == r2 then ifFalse else ifTrue
       r1 <  r2 -->  if r1 <  r2 then ifTrue  else ifFalse
       r1 >  r2 -->  if r2 <  r1 then ifTrue  else ifFalse
       r1 <= r2 -->  if r2 <  r1 then ifFalse else ifTrue
       r1 >= r2 -->  if r1 <  r2 then ifFalse else ifTrue
    */
    
    Cop op ;
    if (cop == EQ | cop == NE) op = new Equal() ;
    else                       op = new Less() ; 

    Crexpr re1 ; Crexpr re2 ;
    if (cop == GT | cop == LE) 
         { re1 = r2 ; re2 = r1 ; }
    else { re1 = r1 ; re2 = r2 ; }

    if (cop == NE | cop == LE | cop == GE)
    { Stmt inter = ifTrue ; ifTrue = ifFalse ; ifFalse = inter ; }

    Cond c = new Cond(op) ;
    Stmt s = new CondStmt(c, ifTrue, ifFalse) ;

    int nextVar0 = nextVar ;

    Sexpr e2;
    if (re2 instanceof CsimpleRexpr)
        e2 = (Sexpr)((CsimpleRexpr)re2).translate();
    else
    {
       e2 = new VarDes(useVar()) ;
       nextVar++ ;
       s  = ((CcompoundRexpr)re2).translate((Des)e2, s) ;
    }

    Sexpr e1;
    if (re1 instanceof CsimpleRexpr)
        e1 = (Sexpr)((CsimpleRexpr)re1).translate();
    else
    {
       e1 = new VarDes(useVar()) ;
       s  = ((CcompoundRexpr)re1).translate((Des)e1, s) ;
    }

    nextVar = nextVar0 ;

    c.setExprs(e1, e2) ;

    return s ;
  }

  String str(int op)
  {
    switch (op)
    {
      case EQ : return " == " ;
      case NE : return " != " ;
      case LT : return " < " ;
      case GT : return " > " ;
      case LE : return " <= " ;
      case GE : return " >= " ;
      default : return " [bad comparison operator] " ;
    }
  }

}

