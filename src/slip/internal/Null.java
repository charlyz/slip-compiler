package slip.internal;

import java.util.ArrayList;
import java.util.List;

public class Null extends Sexpr // null (the famous null reference) 
{
	
	int reg_expr = 0;
	int reg_des = 1;
	
	public int getRegExpr() {return reg_expr;}
	public void setRegExpr(int a) {reg_expr = a;}
	public int getRegDes() {return reg_des;}
	public void setRegDes(int a) {reg_des = a;}
	
  public Null(){}

  public String toString()
  { 
    return "null" ; 
  }
  
  public List<LMAInstruction> translateExpr()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  res.add(new LMAInstruction("LDA", getRegExpr(), 0, true));
	  return res;
  }
}

