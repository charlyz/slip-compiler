package slip.internal ;

import java.util.ArrayList;
import java.util.List;


public class CmdStmt extends Stmt // l cmd l
{
  // initial label == this
  Cmd  cmd;
  public Stmt next; // final label

  public CmdStmt(Cmd cmd){ this.cmd = cmd ; }

  public CmdStmt(Cmd cmd, Stmt next)
  { this(cmd); setLabel(next) ; }


  public void setLabel(Stmt l){ next = l ; }

  public String toString()
  { 
    timeStamp = magic ; // to generate it only once.
    
    String res = "  " + toComment() ;

    if (next.timeStamp < magic & !(next instanceof Method)) 
       res += ELN + next ;

    return res ;
    
  }

  public String toComment()
  {    
    String res = "[ " + this.label + " : " + cmd 
                + " ; go to " + next.label + "]" ;

    return res ; 
  }


	public List<LMAInstruction> translate() 
	{
		ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
		
		res.add(new LMAInstruction(this.toComment()));
		res.addAll(cmd.translate());
		firstInstr = res.get(1);
		
		/*if(next.firstInstr != null)
		{
			LMAInstruction nextInstr = new LMAInstruction("JUMP", 0, -1, false);
			nextInstr.setUnknownAddr(next.firstInstr);
			nextInstr.generateAddress();
			res.add(nextInstr);
		}*/
		
		generated = true;
		return res;
	}

}

