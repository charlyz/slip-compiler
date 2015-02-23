package slip.parser;
import  slip.internal.Stmt;

public class Cor extends Ccond
{
  Ccond c1 ; Ccond c2 ;

  public Cor(Ccond c1, Ccond c2)
  { super(OR) ; this.c1 = c1 ; this.c2 = c2 ; }

  public String toString(){ return "" + c1 + " | " + c2 + "" ; }

  public Stmt translate(Stmt ifTrue, Stmt ifFalse)
  {
    return c1.translate(ifTrue, c2.translate(ifTrue, ifFalse)) ;
  }

}

