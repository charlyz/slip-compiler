package slip.parser;
import  slip.internal.* ;

public class Cnew extends CcompoundRexpr 
{
  public final int i ;

  public Cnew(int i)
  { super(NEW) ; this.i = i ; }

  public Stmt translate(Des des, Stmt next)
  {
    return new CmdStmt(new NewAss(des.getNumber(), i), next ) ;
  }

  public String toString(int dec)
  { return spaces(dec) + "new/" + i ; }

  public String toString(){return toString(0) ; }
}

