package slip.internal;

import java.util.List;

public abstract class Des extends Sexpr 
// left expression x | x.i | this.i
{
  int x; // the variable index (x) or 0 (this)
		int i; // the field number (x.i | this.i)	or 0 (x)
		
  public int getNumber() { return x ; }
  public int getFieldNumber() {return i;}

  public Des(int x, int i){ this.x = x; this.i = i;}

  public abstract List<LMAInstruction> translateDes();
  
  public String toString()
		{ 
			 String r ;
			 if (x==0 &  i!=0) r = "this" ; 
				     else r = varName(x) ;
			 if (i!=0) r += "." + i ; 
		
		  return r ;
		}
}

