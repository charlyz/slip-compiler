package slip.internal;

public class Minus extends Aop // arithmetic operator -
{
  public Minus()
  { super('-'); }
  
  public String translateInstr()
  {
	  return "SUB";
  }
}

