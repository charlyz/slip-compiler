package slip.parser ;
import  slip.internal.Stmt ;

public abstract class Ccmd extends Cnode
{
  static final public int ASS    = 0 ;
  static final public int IF     = 1 ;
  static final public int WHILE  = 2 ;
  static final public int SEQ    = 3 ;
  static final public int INPUT  = 4 ;
  static final public int OUTPUT = 5 ;
  static final public int RETURN = 6 ;

  public int sort ;

  public Ccmd(int sort){ this.sort = sort ; }

  public String toString(int dec) { return spaces(dec) + "cmd" ; }

  public abstract Stmt translate(Stmt next);

}

