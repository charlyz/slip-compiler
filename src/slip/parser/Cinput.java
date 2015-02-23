package slip.parser ;
import  slip.internal.* ;

public class Cinput extends Ccmd
{
  public Clexpr[]  l ; 

  public Cinput(Clexpr[]  l)
  { super(INPUT) ; this.l = l ; }

  public Stmt translate(Stmt next)
  {
    Stmt r = next ;

    int i = l.length ;
    while (i!=0)
    {
      i-- ;
      Des di = (Des)(l[i].translate()) ;
      if (di instanceof VarDes)
      {
         r = new CmdStmt(new In(di.getNumber()), r) ;
      }
      else
      {      
         int v = useVar() ;
         r = new CmdStmt(
                 new NormalAss(di, new VarDes(v)),
                 r) ;
         r = new CmdStmt(new In(v), r) ;
      }
    }
    return r ;
  }

  public String toString(int dec)
  {
    String r = spaces(dec) + "read(" + l[0] ;

    int i = 1 ;
    while (i != l.length)
    { r += ", " + l[i] ; i++ ; }

    return r + ") ;" ;

  }

  public String toString()
  { return toString(0) ; }

}

