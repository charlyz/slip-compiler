package slip.internal;

import java.util.ArrayList;
import java.util.List;


public class I extends Sexpr // An integral value
{
	
	int reg_expr = 0;
	int reg_des = 1;
	
	public int getRegExpr() {return reg_expr;}
	public void setRegExpr(int a) {reg_expr = a;}
	public int getRegDes() {return reg_des;}
	public void setRegDes(int a) {reg_des = a;}
	
  int i; // this integral value

  public I(int i){ this.i = i; }

  public String toString(){ return "" + i ; }
  
  public List<LMAInstruction> translateExpr()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  res.add(new LMAInstruction("LDA", getRegExpr(), i, true));
	  return res;
  }

}

