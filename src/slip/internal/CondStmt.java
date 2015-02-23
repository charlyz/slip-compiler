package slip.internal;

import java.util.ArrayList;
import java.util.List;

public class CondStmt extends Stmt // l if cond then l else l
{
  // initial label == this
  Cond cond;
  Stmt ltrue;  // final label if true
  Stmt lfalse; // final label if false
  
  // Instruction qui définit si on saute à ltrue ou à lfalse
  LMAInstruction instrJGE;



  public CondStmt(Cond cond){ this.cond = cond; }
  public void setTrueLabel(Stmt l) { ltrue = l; }
  public void setFalseLabel(Stmt l){ lfalse = l; }

  public CondStmt(Cond cond, Stmt t, Stmt f)
  { this(cond) ; ltrue = t ; lfalse = f ; }

  public String toString()
  { 
    timeStamp = magic ; // to generate it only once.

    String res = "  " + toComment() ;

    if (ltrue.timeStamp  < magic & !(ltrue instanceof Method)) 
       res += ELN + ltrue ;
    if (lfalse.timeStamp < magic & !(lfalse instanceof Method))
       res += ELN + lfalse ;

    return res ;
  }
  
  public List<LMAInstruction> translate()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  
	  res.add(new LMAInstruction(this.toComment()));
	  res.addAll(cond.translate());
	  firstInstr = res.get(1);
	  
	  // On récupère l'instruction JGE pour définir son adresse de jump
	  instrJGE = res.get(res.size()-1);
	  generated = true;
	  return res;
  }

  public String toComment() 
  { 
    String res = "[ " + this.label + " : if " + cond 
                  + " then go to " + ltrue.label 
                  + " else go to " + lfalse.label 
                  + "]" ;

    return res ;
  }

}

