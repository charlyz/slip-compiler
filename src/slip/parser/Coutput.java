package slip.parser ;
import  slip.internal.* ;

public class Coutput extends Ccmd
{
  public Crexpr[]  r ; 

  public Coutput(Crexpr[]  r)
  { super(OUTPUT) ; this.r = r ; }

  public Stmt translate(Stmt next)
  {
    Stmt res = next ;

    int i = r.length ;
    while (i!=0)
    {
      i-- ;
      Crexpr ri = r[i] ;
  
      if (ri instanceof Cvar)
      {
         VarDes des = (VarDes)(((Cvar)ri).translate()) ;
         res = new CmdStmt(new Out(des.getNumber()), res) ;
      }
      else
      {
         int nextVar0 = nextVar ;
         int v = useVar() ;
         nextVar++ ;
         VarDes vd = new VarDes(v) ;
         res = new CmdStmt(new Out(v), res) ;

         if (ri instanceof CsimpleRexpr)
         {
           Expr e = ((CsimpleRexpr)ri).translate();
           res    = new CmdStmt(new NormalAss(vd, e), res) ;
         }
         else
         { 
           res = ((CcompoundRexpr)ri).translate(vd, res) ;
         }
         nextVar = nextVar0 ;
      }
    }
    return res ;
  }
  public String toString(int dec)
  {
    String s = spaces(dec) + "write(" + r[0] ;

    int i = 1 ;
    while (i != r.length)
    { s += ", " + r[i]  ; i++ ; }

    return s + ") ;" ;

  }

  public String toString()
  { return toString(0) ; }


}

