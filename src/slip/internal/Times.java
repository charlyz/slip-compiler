package slip.internal;

public class Times extends Aop // arithmetic operator *
{
  public Times()
  { super('*'); }
  
  public String translateInstr()
  {
	  return "MUL";
  }
}

