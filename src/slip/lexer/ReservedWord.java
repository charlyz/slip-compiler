package slip.lexer; // 2.2.2005 Start 14:46 Stop 14:55

public class ReservedWord extends Terminal
{

// <reserved word> ::= if | else | read | write | while | this | null | new
//                  | return | super
// Il y a 10 mots réservés.

  private final String val;
  static public final ReservedWord IF =
         new ReservedWord("if");
  static public final ReservedWord ELSE =
         new ReservedWord("else");
  static public final ReservedWord READ =
         new ReservedWord("read");
  static public final ReservedWord WRITE =
         new ReservedWord("write");
  static public final ReservedWord WHILE =
         new ReservedWord("while");
  static public final ReservedWord THIS =
         new ReservedWord("this");
  static public final ReservedWord NULL =
         new ReservedWord("null");
  static public final ReservedWord NEW =
         new ReservedWord("new");
  static public final ReservedWord RETURN =
         new ReservedWord("return");
  static public final ReservedWord SUPER =
         new ReservedWord("super");
  
  public String toString(){ return val ;}

  private ReservedWord(String val){ super(RW, -1, -1); this.val = val; }

  static public Terminal terminal(String s)
  {
// <reserved word> ::= if | else | read | write | while | this | null | new
//                  | return
    if (s.equals("if")) return IF ; else
    if (s.equals("else")) return ELSE ; else
    if (s.equals("read")) return READ ; else
    if (s.equals("write")) return WRITE ; else
    if (s.equals("while")) return WHILE ; else
    if (s.equals("this")) return THIS ; else
    if (s.equals("null")) return NULL ; else
    if (s.equals("new")) return NEW ; else
    if (s.equals("return")) return RETURN ; else
    if (s.equals("super")) return SUPER ; else
    
    return null ;
     
  }


}

