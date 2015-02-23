package slip.parser;
import  slip.internal.*;

public class Ci extends CsimpleRexpr 
{
  public final int i ;

  public Ci(int i)
  { super(I) ; this.i = i ; }

  public String toString(){ return "" + i ; }

  public Expr translate()
  {  
    return  new I(i) ;
  }

}

