package slip.parser ;
import  slip.internal.Stmt ;

public class Cif extends Ccmd
{
  public Ccond c ;
  public Ccmd  tcmd ; // if cond
  public Ccmd  fcmd ; // if !cond (null if no else)

  public Cif(Ccond c, Ccmd t, Ccmd f)
  { super(IF) ; this.c = c ; this.tcmd = t ; this.fcmd = f ; }

  public Cif(Ccond c, Ccmd t)
  { this(c, t, null) ; }

  public Stmt translate(Stmt next)
  {
     Stmt  fnext;
     if (fcmd==null) fnext = next ;
     else fnext = fcmd.translate(next) ;

     return c.translate(tcmd.translate(next), fnext) ;
  }

  public String toString(int dec)
  {
    String r = spaces(dec) + "if (" + c + ")" +
               ELN + tcmd.toString(dec + INC) ;

    if (fcmd != null)
       r += ELN + spaces(dec) + "else" +
            ELN + fcmd.toString(dec + INC) ;

    return r ;
  }

}

