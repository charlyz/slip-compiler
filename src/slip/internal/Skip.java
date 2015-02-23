package slip.internal;

import java.util.ArrayList;
import java.util.List;

public class Skip extends Cmd // skip
{
  static int count = 0 ; 

  public Skip(){ count ++ ; }

  public String toString()
  { 
    return "skip" ; 
  }

	public List<LMAInstruction> translate() 
	{	
		ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
		res.add(new LMAInstruction("NOP", 0, 0, true));
		return res;
	}
}

