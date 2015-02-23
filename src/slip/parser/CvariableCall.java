package slip.parser ;
import  slip.internal.* ;

public class CvariableCall extends Ccall 
{
  public String target ;

  public CvariableCall(String x, String m, Crexpr[] e)
  { super(VARIABLE, m, e) ; target = x ; }

  Call call(int x, String m, int[] iEparam)
  {
    return new VariableCall(x, getVar(target), m, iEparam) ;
  }

  public String toString(int dec)
  {
     return spaces(dec) + target + "." + super.toString();
  }

  public String toString()
  {
     return toString(0);
  }
  
}

