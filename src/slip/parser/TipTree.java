package slip.parser; // 12.2.2005  13h50 : 13h58
import  slip.lexer.Terminal;


public class TipTree extends SyntacticTree
{
  public Terminal t;
  int line;
  int offset;

  public TipTree(Terminal t)
  { this.t = t; }

  public TipTree(Terminal t, int line, int offset)
  { this(t); this.line = line; this.offset = offset; }

  public String toString(){ return t.toString(); }

}

