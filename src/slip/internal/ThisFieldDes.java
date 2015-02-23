package slip.internal;

import java.util.ArrayList;
import java.util.List;

public class ThisFieldDes extends Des 
// left expression this.i
{
	
	int reg_expr = 0;
	int reg_des = 1;
	
	public int getRegExpr() {return reg_expr;}
	public void setRegExpr(int a) {reg_expr = a;}
	public int getRegDes() {return reg_des;}
	public void setRegDes(int a) {reg_des = a;}
  public ThisFieldDes(int i){ super(0, i);}
  
  public List<LMAInstruction> translateDes()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  //res.add(new LMAInstruction("LDA", getRegDes(), 4*i, Rf, true));
	  // On utilise le registre 8 au hasard car si on utilise getRegExpr ça ne va.
	  res.add(new LMAInstruction("LDM", 8, 0, Rf, true));
	  res.add(new LMAInstruction("LDA", getRegDes(), 4*i, 8, true));
	  return res;
  }
  
  public List<LMAInstruction> translateExpr()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  //res.add(new LMAInstruction("LDM", getRegExpr(), 4*i, Rf, true));
	  res.add(new LMAInstruction("LDM", 8, 0, Rf, true));
	  res.add(new LMAInstruction("LDM", getRegExpr(), 4*i, 8, true));
	  return res;
  }
}

