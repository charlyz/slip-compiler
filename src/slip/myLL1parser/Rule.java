package slip.myLL1parser;


import java.util.*;

public class Rule
{
    public NotTerminal left;
    public List<Symbol> right;

    public Rule(NotTerminal left, List<Symbol> right)
    {
    	this.left = left;
    	this.right = right;
    }
    
    public NotTerminal left(){return left;}
    public List<Symbol> right(){return right;}
    public String toString()
    {
    	StringBuffer sb = new StringBuffer(left+" ::=");

    	Iterator it = right.iterator();
    	while (it.hasNext())
    	{
    		sb.append(" ");
    		sb.append(it.next());
    	}
    	
    	return sb.toString();
    }
    
}
