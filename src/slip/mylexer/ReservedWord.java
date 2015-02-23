package slip.mylexer; 

import slip.myLL1parser.Terminal;

public class ReservedWord extends Terminal
{
	/*
	   <RESERVED WORD> ::= 	if | else | then | while | read | write | new | super
							| this | null | do | meth | elseif | return | end
	 */

	public final static Terminal RWIF = new ReservedWord("if");
	public final static Terminal RWELSE = new ReservedWord("else");
	public final static Terminal RWTHEN = new ReservedWord("then");
	public final static Terminal RWWHILE = new ReservedWord("while");
	public final static Terminal RWREAD = new ReservedWord("read");
	public final static Terminal RWWRITE = new ReservedWord("write");
	public final static Terminal RWNEW = new ReservedWord("new");
	public final static Terminal RWSUPER = new ReservedWord("super");
	public final static Terminal RWTHIS = new ReservedWord("this");
	public final static Terminal RWNULL = new ReservedWord("null");
	public final static Terminal RWDO = new ReservedWord("do");
	public final static Terminal RWMETH = new ReservedWord("meth");
	public final static Terminal RWEND = new ReservedWord("end");
	public final static Terminal RWRETURN = new ReservedWord("return");
	public final static Terminal RWELSEIF = new ReservedWord("elseif");
  
	public String toString(){ return "ReservedWord: " + getText() ;}

	public ReservedWord(String val)
	{
		super(val);
	}
	
	public static Terminal terminal(String s)
	{
		if(s.equals("if")) return RWIF;
		else if(s.equals("then")) return RWTHEN;
		else if(s.equals("else")) return RWELSE;
		else if(s.equals("while")) return RWWHILE;
		else if(s.equals("read")) return RWREAD;
		else if(s.equals("write")) return RWWRITE;
		else if(s.equals("new")) return RWNEW;
		else if(s.equals("super")) return RWSUPER;
		else if(s.equals("this")) return RWTHIS;
		else if(s.equals("new")) return RWNEW;
		else if(s.equals("null")) return RWNULL;
		else if(s.equals("do")) return RWDO;
		else if(s.equals("meth")) return RWMETH;
		else if(s.equals("end")) return RWEND;
		else if(s.equals("return")) return RWRETURN;
		else if(s.equals("elseif")) return RWELSEIF;
		else return null;
	}
}