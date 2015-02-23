package slip.lexer;
import slip.parser.Symbol;

public abstract class Terminal extends slip.parser.Symbol // 6.2.2005 Start 13:36 ; Stop
{
  final public int sort;
  final public int line;
  final public int offset;
  static final public int SC = 0; // Special char
  static final public int RW = 1; // Reserved word
  static final public int ID = 2; // IDentifier
  static final public int IL = 3; // Integral Literal
  

  Terminal(int sort, int line, int offset)
  { this.sort = sort; 
    this.line = line; 
    this.offset = offset; 
  }

}

