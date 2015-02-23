package slip.parser;
import  slip.internal.Des;
import  slip.internal.Stmt;

public class Cass extends Ccmd
{
  public Clexpr l ;
  public Crexpr r ;

  public Cass(Clexpr l, Crexpr r)
  { super(ASS) ; this.l = l ; this.r = r ; }

  public String toString(int dec)
  { return spaces(dec) + l + " = " + r + " ;" ; }

  public String toString(){return toString(0) ; }

  
  public Stmt translate(Stmt next)
  {
     Des des = (Des)l.translate() ;
     return r.translate(des, next) ;
  }
}

