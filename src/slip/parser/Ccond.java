package slip.parser;
import  slip.internal.Stmt;

public abstract class Ccond extends Cnode
{
  static final public int OR  = 0 ;
  static final public int AND = 1 ;
  static final public int NOT = 2 ;
  static final public int REL = 3 ;

  public final int sort ; 

  public Ccond(int s)
  { sort = s ; }

  public String toString(){  return "(cond)" ; }

  public abstract Stmt translate(Stmt ifTrue, Stmt ifFalse);
  // Pré : ifTrue is the statement to execute if the condition is true
  //       ifFalse  "  "  " ... " false
  // Post : a set of statement to execute the condition has been created
  //        it uses only temporary variables with number >= varNext
  //       varNext = varNext_0

}

