package slip.parser;
import  slip.internal.*;

public class Cvar extends Clexpr 
{

  public Cvar(String x)
  { super(VAR, x) ; }

  public Expr translate()
  {  
    return new VarDes(getVar(x)) ;
  } 
}

