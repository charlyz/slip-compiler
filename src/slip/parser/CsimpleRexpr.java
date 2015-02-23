package slip.parser;
import  slip.internal.CmdStmt;
import  slip.internal.Des;
import  slip.internal.Expr;
import  slip.internal.NormalAss;
import  slip.internal.Stmt;

public abstract class CsimpleRexpr extends Crexpr 
{
  public CsimpleRexpr(int s)
  { super(s) ; }

  public abstract Expr translate();
  
  public  Stmt translate(Des des, Stmt next)
  {   
    return new CmdStmt(new NormalAss(des, translate()), next) ;
  }

}

