package slip.myLL1parser;

public  class Terminal extends Symbol
{
  public int line;
  public int col;
  //Index courant dans la liste des non terminaux de la grammaire
  private static int TerminalIndex = 0;
  
  public Terminal(String s)
  {
	super(s);
	ptIndex = TerminalIndex;
	TerminalIndex++;
  }

  public String toString()
  {
	  return "'" + text + "'";
  }
  
}

