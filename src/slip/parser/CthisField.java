package slip.parser;
import  slip.internal.*;

public class CthisField extends Clexpr 
{
  public final int i ;

  public CthisField(int i)
  { super(THIS, "this") ; this.i = i ; }

  public String toString(){ return super.toString() + '.' + i ; }

  public Expr translate()
  {  
    
    return new ThisFieldDes(i) ;
  } 
}

