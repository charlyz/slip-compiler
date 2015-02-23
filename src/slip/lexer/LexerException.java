package slip.lexer; // 2.2.2005 Start 15:07 Stop 15:11

public class LexerException extends Exception
{
  private final String msg; 
		private int ln;
		private int pos;
  
  public String toString(){ return msg + " at line " + ln + " at position " + pos + "." ;}
		public int ln(){ return ln ; }
  public int pos(){ return pos ; }
  public String msg(){ return msg ; }
  
  public LexerException(String s, int ln, int pos)
		{ 
		  msg = "Lexical error: " + s ; 
		  this.ln = ln; this.pos = pos ; }


}


