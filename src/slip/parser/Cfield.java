package slip.parser;
import  slip.internal.*;

public class Cfield extends Clexpr 
{
  public final int i ;

  public Cfield(String x, int i)
  { super(FIELD, x) ; this.i = i ; }

  public String toString(){ return super.toString() + '.' + i ; }

  public Expr translate()
  {  
    
    return new FieldDes(getVar(x), i) ;
  } 
}

