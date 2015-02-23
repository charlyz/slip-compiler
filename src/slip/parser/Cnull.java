package slip.parser;
import  slip.internal.*;

public class Cnull extends CsimpleRexpr 
{
  public Cnull()
  { super(NULL) ; }

  public String toString() { return "null" ; }

  public Expr translate()
  {  
    return  new Null() ;
  }
}

