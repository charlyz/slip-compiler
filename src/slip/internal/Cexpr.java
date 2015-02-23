package slip.internal ;

import java.util.ArrayList;
import java.util.List;

public class Cexpr extends Expr // arithmetic expression (expr1 aop expr2)
{
  Sexpr expr1; 
  Aop  aop;
  Sexpr expr2; 

  public Cexpr(Sexpr expr1, Aop aop, Sexpr expr2)
  { this.expr1 = expr1; this.aop = aop; this.expr2 = expr2; }
  
  public Sexpr getExpr1() {return expr1;}
  public Sexpr getExpr2() {return expr2;}
  public Aop getAop() {return aop;}
  
  public List<LMAInstruction> translateExpr()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  
	  res.addAll(expr1.translateExpr());
	  expr2.setRegExpr(1);
	  res.addAll(expr2.translateExpr());
	  
	  if(aop instanceof Remainder)
	  {
		  res.add(new LMAInstruction("DIV", 0, 1, true));
		  res.add(new LMAInstruction("LDA", 0, 0, 1, true));

	  }else
	  {
		  res.add(new LMAInstruction(aop.translateInstr(), 0, 1, true));
	  }
	  
	  return res;
  }

  public String toString()
  { return "" + expr1 + " " + aop
              + " " + expr2 ; }
}

