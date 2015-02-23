package slip.internal;

import java.util.ArrayList;
import java.util.List;

public class In extends Cmd // read(x)
{
  int x; 

  public In(int x){ this.x = x; }

  public String toString()
  { return "read(" + varName(x) + ")" ; }
  
  public List<LMAInstruction> translate()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  LMAInstruction instr1 = new LMAInstruction("IN: ", false);
	  LMAInstruction instr2 =  new LMAInstruction("LDM", Rmessage, -1, false);
	  instr2.setUnknownAddr(instr1);
	  
	  instr1.generateAddress();
	  res.add(instr1);
	  instr2.generateAddress();
	  res.add(instr2);
	  res.add(new LMAInstruction("HALT", 0, 0, true));
	  res.add(new LMAInstruction("STM", Rentier, 4*x, Rf, true));
	  
	  return res;
  }
                
}
