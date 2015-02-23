package slip.lexer; // 2.2.2005 Start 14:57 Stop 15:00

public class Identifier extends Terminal
{
  private final String val;
  
  public String toString(){ return val ;}

  private Identifier(String val, int line, int offset)
  { super(ID, line, offset); this.val = val; }

  static public Terminal terminal(String s, int line, int offset)
  {
    return new Identifier(s, line, offset) ;
     
  }

}


