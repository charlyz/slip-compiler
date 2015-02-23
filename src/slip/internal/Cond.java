package slip.internal;

import java.util.ArrayList;
import java.util.List;

public class Cond extends AbstractNode
{
  Sexpr expr1;
  Cop  cop;
  Sexpr expr2;

  public Cond(Sexpr expr1, Cop cop, Sexpr expr2)
  { this(cop) ; setExprs(expr1, expr2) ; }

  public Cond(Cop cop)
  { this.cop = cop ; }

  public void setExprs(Sexpr e1, Sexpr e2)
  { expr1 = e1 ; expr2 = e2 ; }

  public String toString()
  {
    return expr1 + " " + cop + " " + expr2 ;
  }
  
  
  public List<LMAInstruction> translate()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  
	  
	  res.addAll(expr1.translateExpr());
	  //res.add(new LMAInstruction("LDA", 5, 0, 0, true));
	  expr2.setRegExpr(1);
	  res.addAll(expr2.translateExpr());

	  res.add(new LMAInstruction("COMP", 0, 1, true));
	  //	 Si la cond est fausse, on saute vers l'instruction lFalse
	  if(cop instanceof Less)
		  res.add(new LMAInstruction("JGE", 0, -1, true));
	  else
		  res.add(new LMAInstruction("JNE", 0, -1, true));
	  
	  return res;
  }

}

