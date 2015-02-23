package slip.parser;
import  slip.internal.*;

public class Cthis extends CsimpleRexpr 
{
  public Cthis()
  { super(THIS) ; }

  public String toString(){ return "this" ; }

  
  public Expr translate()
  {  
    return new This()  ;
  }


}

