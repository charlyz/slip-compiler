package slip.lexer; // 2.2.2005 Start 14:57 Stop 15:00

public class IntegralLiteral extends Terminal
{
  private final int val; // >= 0
  
  public String toString(){ return "" + val ;}

  public int valueOf(){ return val; }

  private IntegralLiteral(int val, int line, int offset)
  { super(IL, line, offset); this.val = val; }

  static public Terminal terminal(int i, int line, int offset)
  {
    return new IntegralLiteral(i, line, offset) ;
     
  }

}


