package slip.internal;

public class Plus extends Aop // arithmetic operator +
{
  public Plus()
  { super('+'); }
  
  public String translateInstr()
  {
	  return "ADD";
  }
}

