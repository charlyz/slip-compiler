package slip.parser;
import  slip.internal.Stmt;

public class Cnot extends Ccond
{
  Ccond c ; 

  public Cnot(Ccond c)
  { super(NOT) ; this.c = c ; }

  public String toString(){ return "!(" + c + ")" ; }

  public Stmt translate(Stmt ifTrue, Stmt ifFalse)
  {
    return c.translate(ifFalse, ifTrue) ;
  }
}

