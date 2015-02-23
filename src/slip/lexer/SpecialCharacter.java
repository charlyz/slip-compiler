package slip.lexer; // 2.2.2005 Start 13:40 Stop 14:08

public class SpecialCharacter extends Terminal
{

// <special character> ::= 
//   , | . | ( | ) | "|" | & | { | } | / | > | < | = | ! | ; | * | + | - | %
// Il y a 18 caractères spéciaux.

  private final char val;
  static public final SpecialCharacter COM =
         new SpecialCharacter(',');
  static public final SpecialCharacter DOT =
         new SpecialCharacter('.');
  static public final SpecialCharacter OPA =
         new SpecialCharacter('(');
  static public final SpecialCharacter CPA =
         new SpecialCharacter(')');
  static public final SpecialCharacter OR =
         new SpecialCharacter('|');
  static public final SpecialCharacter AND =
         new SpecialCharacter('&');
  static public final SpecialCharacter NOT =
         new SpecialCharacter('!');
  static public final SpecialCharacter EQU =
         new SpecialCharacter('=');
  static public final SpecialCharacter OBR =
         new SpecialCharacter('{');
  static public final SpecialCharacter CBR =
         new SpecialCharacter('}');
  static public final SpecialCharacter LT =
         new SpecialCharacter('<');
  static public final SpecialCharacter GT =
         new SpecialCharacter('>');
  static public final SpecialCharacter SLA =
         new SpecialCharacter('/');
  static public final SpecialCharacter TIM =
         new SpecialCharacter('*');
  static public final SpecialCharacter PLU =
         new SpecialCharacter('+');
  static public final SpecialCharacter MIN =
         new SpecialCharacter('-');
  static public final SpecialCharacter REM =
         new SpecialCharacter('%');
  static public final SpecialCharacter SEM =
         new SpecialCharacter(';');
  

  public String toString(){ return "" + val ;}

  private SpecialCharacter(char val){ super(SC, -1, -1); this.val = val; }

  static public Terminal terminal(char c)
  {
    switch (c)
    { 
      case ',' : return COM ;
      case '.' : return DOT ;
      case '(' : return OPA ;
      case ')' : return CPA ;
      case '|' : return OR  ;
      case '&' : return AND ;
      case '!' : return NOT ;
      case '=' : return EQU ;
      case '{' : return OBR ;
      case '}' : return CBR ;
      case '<' : return LT ;
      case '>' : return GT ;
      case '/' : return SLA ;
      case '*' : return TIM ;
      case '+' : return PLU ;
      case '-' : return MIN ;
      case '%' : return REM ;
      case ';' : return SEM ;
      default  : return null ;
    } 
  }


}

