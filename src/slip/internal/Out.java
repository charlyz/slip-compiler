package slip.internal;

import java.util.ArrayList;
import java.util.List;

public class Out extends Cmd // write(x)
{
  int x; 

  public Out(int x){ this.x = x; }

  public String toString()
  { return "write(" + varName(x) + ")" ; }
  
  public List<LMAInstruction> translate()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  
	  res.add(new LMAInstruction("LDM", Rentier, 4*x, Rf, true));

	  LMAInstruction instr1 = new LMAInstruction("OUT:", false);
	  LMAInstruction instr2 =  new LMAInstruction("LDM", Rmessage, -1, false);
	  instr2.setUnknownAddr(instr1);
	  
	  instr1.generateAddress();
	  res.add(instr1);
	  instr2.generateAddress();
	  res.add(instr2);
	  res.add(new LMAInstruction("HALT", 0, 0, true));
	  
	  return res;
  }
}

