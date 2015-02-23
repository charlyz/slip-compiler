package slip.myLL1parser;

public class NotTerminal extends Symbol
{
	// Index courant dans la liste des non terminaux de la grammaire
	public static int NotTerminalIndex = 0;
	
    public NotTerminal(String s)
    {
    	super(s);
    	ptIndex = NotTerminalIndex;
    	NotTerminalIndex++;
    }

    public boolean equals(Object o)
    {
    	return (o instanceof NotTerminal && super.equals(o));
    }
    
    public String toString(){return "<"+getText()+">";}
}
