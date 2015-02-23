package slip.parser;
import  slip.internal.* ;

public class Cbinary extends CcompoundRexpr 
{
  public static final int PLUS  = 0 ; 
  public static final int TIMES = 1 ; 
  public static final int MINUS = 2 ; 
  public static final int DIV   = 3 ; 
  public static final int MOD   = 4 ; 
  
  public final Crexpr r1 ;
  public final int    op ;
  public final Crexpr r2 ;

  public Cbinary(Crexpr r1, int op, Crexpr r2)
  { super(BINARY) ; this.r1 = r1 ; this.op = op ; this.r2 = r2 ; }

  public Stmt translate(Des des, Stmt next)
  { 
    int nextVar0 = nextVar ;

    NormalAss a = new NormalAss(des, null) ;
    Stmt   s = new CmdStmt(a, next) ;

    Sexpr   e2 ;
    if (r2 instanceof CsimpleRexpr)  
        e2 = (Sexpr)((CsimpleRexpr)r2).translate() ;
    else 
      { 
        nextVar++ ;
        VarDes d2 = new VarDes(useVar()) ;
        s  = ((CcompoundRexpr)r2).translate(d2, s) ;
        e2 = d2 ;
      }

    Sexpr   e1 ;
    if (r1 instanceof CsimpleRexpr)  
        e1 = (Sexpr)((CsimpleRexpr)r1).translate() ;
    else 
      { 
        nextVar = nextVar0 ;
        VarDes d1 = new VarDes(useVar()) ;   
        s  = ((CcompoundRexpr)r1).translate(d1, s) ;
        e1 = d1 ;
      }

    Aop aop ;
    switch (op)
     {
       case PLUS  : aop = new Plus() ; break ;
       case TIMES : aop = new Times() ; break ;
       case MINUS : aop = new Minus() ; break ;
       case DIV   : aop = new Divide() ;  break ;
       case MOD   : aop = new Remainder() ;  break ;
       default    : aop = null ; 
     }

     if (e2  == null) System.out.println("e2 == " + null);   

     a.setExpr(new Cexpr(e1, aop, e2)) ;
     nextVar = nextVar0 ;

     return s ;
  }

  public String toString(int dec)
  { return spaces(dec) + "(" + r1 + str(op) + r2 + ")" ; }

  public String toString(){return toString(0) ; }

  String str(int op)
  {
     switch (op)
     {
       case PLUS  : return " + " ; 
       case TIMES : return " * " ; 
       case MINUS : return " - " ; 
       case DIV   : return " / " ; 
       case MOD   : return " % " ; 
       default    : return " [unknown arithmetic operator] " ;
       
     }
  }
}

