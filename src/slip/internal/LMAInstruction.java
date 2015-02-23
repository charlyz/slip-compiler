package slip.internal;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashSet;

public class LMAInstruction 
{
	private String instr;
	private int arg1;
	private int arg2;
	private String lit;
	private int optionArg;
	// 1: Instr 2 param, 2: Instr 3 param, 3: lit 
	private int type;
	private boolean unknownAddr = false;
	private boolean unknownArg2 = false;
	private LMAInstruction unknownInstr;
	private int addr;
	private String comment;
	private String typeLit = "C";
	
	private static HashSet<String> shortInstr = new HashSet();
	static
	{
		shortInstr = new HashSet<String>();
		shortInstr.add("HALT");
		shortInstr.add("NOP");
		shortInstr.add("ADD");
		shortInstr.add("SUB");
		shortInstr.add("MUL");
		shortInstr.add("DIV");
		shortInstr.add("COMP");
	}
	
	public LMAInstruction(int type, boolean genaddr)
	{
		arg1 = -1;
		arg2 = -1;
		optionArg = -1;
		this.type = type;
		if(genaddr) generateAddress();
	}
	
	public void generateAddress()
	{	
		if(type == 3)
		{
			setAddr(AbstractNode.currAddrLit);
			AbstractNode.currAddrLit -= 4;
		}else
		{
			setAddr(AbstractNode.currAddr);
			if(shortInstr.contains(instr))
				AbstractNode.currAddr = AbstractNode.currAddr+2;
			else
				AbstractNode.currAddr = AbstractNode.currAddr+4;
		}
	}
	
	public LMAInstruction(String instr, int arg1, int arg2, boolean genaddr)
	{
		this.instr = instr;
		this.arg1 = arg1;
		this.arg2 = arg2;
		optionArg = -1;
		type = 1;
		if(genaddr) generateAddress();
	}
	
	public LMAInstruction(String instr, int arg1, int arg2, int optionArg, boolean genaddr)
	{
		this.instr = instr;
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.optionArg = optionArg;
		type = 2;
		if(genaddr) generateAddress();
	}
	
	public LMAInstruction(String lit, boolean genaddr)
	{
		this.lit = lit;
		arg1 = -1;
		arg2 = -1;
		optionArg = -1;
		type = 3;
		if(genaddr) generateAddress();
	}
	
	public LMAInstruction(String comment)
	{
		lit = null;
		this.comment = comment;
		arg1 = -1;
		arg2 = -1;
		optionArg = -1;
		type = 4;
	}
	
	public void setArg1(int i) {arg1=i;}
	public void setArg2(int i) {arg2=i;}
	public void setOptionArg(int i) {optionArg=i;}
	public void setLit(String s) {lit=s;}
	public void setInstr(String s) {instr=s;}
	public void setUnknownAddr(LMAInstruction instr) {unknownAddr=true;unknownInstr=instr;}
	public void setUnknownArg2(LMAInstruction instr) {unknownArg2=true;unknownInstr=instr;}
	public void setAddr(int a) {addr=a;}
	public void setComment(String c) {comment=c;}
	public void setTypeLit(String c) {typeLit=c;}
	
	public boolean isShort(){return shortInstr.contains(instr);}
	
	public int getAddr() {return addr;}
	public int getArg1() {return arg1;}
	public int getArg2() {return arg2;}
	public int getOptionArg() {return optionArg;}
	public String getLit() {return lit;}
	public String getInstr() {return instr;}
	public LMAInstruction getUnknownInstr() {return unknownInstr;}
	public boolean isUnknownAddr() {return unknownAddr;}
	public boolean isUnknownArg2() {return unknownArg2;}
	public String getComment() {return comment;}
	
	public String toString()
	{
		String buffer = null;
		
		if(isUnknownAddr())
			arg2 = getUnknownInstr().getAddr();
		
		if(isUnknownArg2())
			arg2 = Integer.valueOf(getUnknownInstr().getLit());
		
		NumberFormat formatter = new DecimalFormat("00000");
		
		String spaces = " ";
		if(type!=4 && (type!=3 && instr.length()<4))
			spaces += " ";

		if(type!=4 && arg1<10)
			spaces += " ";
		
		String arg2_tmp = String.valueOf(arg2);
		if(instr!=null && !shortInstr.contains(instr))
			arg2_tmp = formatter.format(arg2);
		
		switch(type)
		{
			case 1:
				buffer = formatter.format(addr) + "   " + instr + spaces + arg1 + ", " + arg2_tmp;
			break;
			
			case 2:
				buffer = formatter.format(addr) + "   " + instr + spaces + arg1 + ", " + arg2_tmp + " (" + optionArg + ")";
			break;
			
			case 3:
				String a, b;
				if(typeLit.equals("C"))
				{	a = "'"; b = "'";}
				else
				{	a = "("; b = ")";}	
				buffer = formatter.format(addr) + "   " + "LIT  " + typeLit + a + lit + b;
			break;
			
			case 4:
				buffer = "// " + comment;
		}
		
		return buffer;
	}
}
