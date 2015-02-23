package slip.parser ;
import  slip.internal.* ;

public class CsuperCall extends Ccall 
{
  public CsuperCall(String m, Crexpr[] e)
  { super(SUPER, m, e) ;  }
  
  Call call(int x, String m, int[] iEparam)
  {
    return new SuperCall(x, m, iEparam) ;
  }

  public String toString(int dec)
  {
     return spaces(dec) + "super." + super.toString();
  }

  public String toString()
  {
     return toString(0);
  }
  

}

