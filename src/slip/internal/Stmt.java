package slip.internal;

import java.util.List;

public abstract class Stmt extends AbstractNode
{
  static private int count = 1 ; // Counter of Stmt labels.
  public final String label ; // The label of this statement.
  int timeStamp = 0 ; // to avoid looping in toString() ;
  // Retient la première instruction lma du stmt.
  public LMAInstruction firstInstr;
  public boolean generated = false;

  Stmt(){ label = "lab" + count ; count++ ;}
		
  abstract String toComment() ;
  public abstract List<LMAInstruction> translate();
}

