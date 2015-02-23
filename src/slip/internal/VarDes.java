package slip.internal;

import java.util.ArrayList;
import java.util.List;

public class VarDes extends Des // left expression x 
{
	int reg_expr = 0;
	int reg_des = 1;
	
	public int getRegExpr() {return reg_expr;}
	public void setRegExpr(int a) {reg_expr = a;}
	public int getRegDes() {return reg_des;}
	public void setRegDes(int a) {reg_des = a;}
	
  public VarDes(int x){super(x, 0);}
  
  public List<LMAInstruction> translateDes()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  res.add(new LMAInstruction("LDA", getRegDes(), 4*x, Rf, true));
	  return res;
  }
  
  public List<LMAInstruction> translateExpr()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  res.add(new LMAInstruction("LDM", getRegExpr(), 4*x, Rf, true));
	  return res;
  }
}

