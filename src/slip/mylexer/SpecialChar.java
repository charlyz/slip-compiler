package slip.mylexer;
import slip.myLL1parser.Terminal;

public class SpecialChar extends Terminal
{

  	/* <SPECIAL SYMBOLS> ::= 	 . | ( | ) | { | } | & | / | < | > | = | ! | ,
  								; | * | + | - | % | <= | >= | != | == | ->
  	*/
  
	// Comparateurs conditionnels
	public final static Terminal SCPPE = new SpecialChar("<=");
	public final static Terminal SCSPP = new SpecialChar("<");
	public final static Terminal SCPGE = new SpecialChar(">=");
	public final static Terminal SCSPG = new SpecialChar(">");
	public final static Terminal SCEGA = new SpecialChar("==");
	public final static Terminal SCDIF = new SpecialChar("!=");
	public final static Terminal SCAND = new SpecialChar("&");
	
	// Caractères spéciaux
	public final static Terminal SCDOT = new SpecialChar(".");
	public final static Terminal SCPOINTVIR = new SpecialChar(";");
	public final static Terminal SCVIR = new SpecialChar(",");
	public final static Terminal SCNEG = new SpecialChar("!");
	public final static Terminal SCEQUAL = new SpecialChar("=");
	public final static Terminal SCPARL = new SpecialChar("(");
	public final static Terminal SCPARR = new SpecialChar(")");
	public final static Terminal SCACCL = new SpecialChar("{");
	public final static Terminal SCACCR = new SpecialChar("}");
	public final static Terminal SCARROW = new SpecialChar("->");
	
	// Opérateurs arithmétiques
	public final static Terminal SCMUL = new SpecialChar("*");
	public final static Terminal SCPLUS = new SpecialChar("+");
	public final static Terminal SCMINUS = new SpecialChar("-");
	public final static Terminal SCMOD = new SpecialChar("%");
	public final static Terminal SCDIV = new SpecialChar("/");
	
	// Caractère spécial pour définir la fin du fichier
	public final static Terminal EOF = new SpecialChar("EOF");
  
  public String toString(){ return "SpecialChar: " + getText() ;}

  public SpecialChar(String val)
  { 
	  super(val); 
  }
  
  public static Terminal terminal(char c)
  {
	  switch(c)
	  {
	  	case '.': return SCDOT;
	  	case '!': return SCNEG;
	  	case '=': return SCEQUAL;
	  	case '(': return SCPARL;
	  	case ')': return SCPARR;
	  	case ',': return SCVIR;
	  	case ';': return SCPOINTVIR;
	  	case '{': return SCACCL;
	  	case '}': return SCACCR;
	  	case '*': return SCMUL;
	  	case '/': return SCDIV;
	  	case '%': return SCMOD;
	  	case '-': return SCMINUS;
	  	case '+': return SCPLUS;
	  }
	  return null;
  }
  
}