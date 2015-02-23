package slip.internal;

import java.util.ArrayList;
import java.util.List;

public class NormalAss extends Ass
{
  Des left ; // left expression
  Expr right ; // right expression

  public NormalAss(Des left, Expr right)
  { this.left = left ; this.right = right ; }

  public void setExpr(Expr right)
  { this.right = right ; }

  public String toString()
  { 
    return left + " := " + right  ; 
  }
  
  public List<LMAInstruction> translate()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  
	  left.setRegDes(4);
	  res.addAll(left.translateDes());
	  res.addAll(right.translateExpr());
	  // Affecte à des la valeur de R0 
	  res.add(new LMAInstruction("STM", 0, 0, 4, true));
	  
	  return res;
  }
}

