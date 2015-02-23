package slip.internal;

import java.util.ArrayList;
import java.util.List;

public class FieldDes extends Des 
// left expression x . i (i-th field of an object)
{
	
	int reg_expr = 0;
	int reg_des = 1;
	
	public int getRegExpr() {return reg_expr;}
	public void setRegExpr(int a) {reg_expr = a;}
	public int getRegDes() {return reg_des;}
	public void setRegDes(int a) {reg_des = a;}
	
  public FieldDes(int x, int i){ super(x, i) ; }
  
  public List<LMAInstruction> translateDes()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  res.add(new LMAInstruction("LDM", getRegDes(), 4*x, Rf, true));
	  res.add(new LMAInstruction("LDA", getRegDes(), 4*i, getRegDes(), true));
	  return res;
  }
  
  public List<LMAInstruction> translateExpr()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  res.add(new LMAInstruction("LDM", 3, 4*x, Rf, true));
	  res.add(new LMAInstruction("LDM", getRegExpr(), 4*i, 3, true));
	  return res;
  }
}
