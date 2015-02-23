package slip.translation;

import java.util.ArrayList;
import java.util.Iterator;

public class LMAProgram 
{
	private ArrayList<LMAInstruction> stack;
	
	public LMAProgram()
	{
		stack = new ArrayList<LMAInstruction>();
	}
	
	public void addInstruction(LMAInstruction instr)
	{
		stack.add(instr);
	}
	
	public Iterator iterator()
	{
		return stack.iterator();
	}

}
