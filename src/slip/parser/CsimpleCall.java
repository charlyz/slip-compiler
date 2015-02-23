package slip.parser ;
import  slip.internal.* ;

public class CsimpleCall extends Ccall 
{

  public CsimpleCall(String m, Crexpr[] e)
  { super(SIMPLE, m, e) ; }

  Call call(int x, String m, int[] iEparam)
  {
    return new SimpleCall(x, m, iEparam) ;
  }
  
  public String toString(int dec)
  {
     return spaces(dec) + super.toString();
  }

  public String toString()
  {
     return toString(0);
  }
  
}

