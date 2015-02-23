package slip.mylexer; 

import slip.myLL1parser.Terminal;

public class Identifier extends Terminal
{
  public String toString(){ return "Identifier: " + getText() ;}

  public Identifier(String val)
  {  
	  super(val); 
  }
}


