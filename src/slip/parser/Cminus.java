package slip.parser ;
import  slip.internal.* ;

public class Cminus extends CcompoundRexpr 
{
  public final Crexpr r ;

  public Cminus(Crexpr r)
  { super(MINUS) ; this.r = r ; }

  public Stmt translate(Des des, Stmt next)
  { 
    int nextVar0 = nextVar ;

    NormalAss a = new NormalAss(des, null) ;
    Stmt      s = new CmdStmt(a, next) ;

    Sexpr   e2 ;
    if (r instanceof CsimpleRexpr)  
        e2 = (Sexpr)((CsimpleRexpr)r).translate() ;
    else 
      { VarDes d2 = new VarDes(useVar()) ;
        nextVar++ ;
        s  = ((CcompoundRexpr)r).translate(d2, s) ;
        e2 = d2 ;
      }

     a.setExpr(new Cexpr(new I(0) , new Minus(), e2)) ;
     nextVar = nextVar0 ;

     return s ;
  }

  public String toString(int dec)
  { return spaces(dec) + "- " + r ; }

  public String toString(){return toString(0) ; }

}

