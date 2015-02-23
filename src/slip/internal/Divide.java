package slip.internal;

public class Divide extends Aop // arithmetic operator /
{
  public Divide()
  { super('/'); }
  
  public String translateInstr()
  {
	  return "DIV";
  }
}

