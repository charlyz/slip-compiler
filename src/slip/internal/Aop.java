package slip.internal;

public abstract class Aop extends AbstractNode // arithmetic operator
{
  final char aop; // '+', '*', '-', '/', '%'

  Aop(char aop)
  { this.aop = aop; }
  
  public abstract String translateInstr();

  public String toString(){ return "" + aop ; }
}

