package slip.parser ;
import  slip.internal.Stmt ;
import  slip.internal.VarDes ;

public class Creturn extends Ccmd
{
  public Crexpr  r ; 

  public Creturn(Crexpr  r)
  { super(RETURN) ; this.r = r ; }

  public String toString(int dec)
  { 
    return spaces(dec) + "return (" + r + ") ;" ;
  }

  
  public Stmt translate(Stmt next)
  {
     return r.translate(new VarDes(0), Cnode.curm) ;
  }
}

