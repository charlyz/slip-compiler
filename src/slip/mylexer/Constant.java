package slip.mylexer; 

import slip.myLL1parser.Terminal;

public class Constant extends Terminal
{
  
  public String toString(){ return "Constant: " + getText() ;}

  public Constant(String val)
  { 
	  super(val); 
  }
}