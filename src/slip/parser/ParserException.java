package slip.parser; // 2.2.2005 Start 20:00 Stop 20:04

public class ParserException extends Exception
{
  private final String msg; 
		private int ln;
		private int pos;
  
  public String toString(){ return msg + " at line " + ln + " at position " + pos + "." ;}
		public int ln(){ return ln ; }
  public int pos(){ return pos ; }
  public String msg(){ return msg ; }
  
  public ParserException(String s, int ln, int pos)
		{ 
		  msg = "Syntactic error: " + s ; 
		  this.ln = ln; this.pos = pos ; }

}
