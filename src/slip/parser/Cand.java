package slip.parser;
import  slip.internal.Stmt ;


public class Cand extends Ccond
{
  Ccond c1 ; Ccond c2 ;

  public Cand(Ccond c1, Ccond c2)
  { super(AND) ; this.c1 = c1 ; this.c2 = c2 ; }

  public String toString(){ return "" + c1 + " & " + c2 + "" ; }

  public Stmt translate(Stmt ifTrue, Stmt ifFalse)
  {
    return c1.translate(c2.translate(ifTrue, ifFalse), ifFalse) ;
  }
}

