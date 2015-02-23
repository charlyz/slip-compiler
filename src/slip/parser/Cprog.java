package slip.parser ;
import  slip.internal.Method ;
import  slip.internal.Prog ;
import  slip.translation.AllMethodsTable ;


public class Cprog extends Cnode
{
  public Cmethod[] method;

  public AllMethodsTable amt = new AllMethodsTable() ;

  public Cprog(Cmethod[] m) throws Exception
  { 
    method = m ; 

    int i = 0 ;
    while (i!=m.length)
    {  amt.add(m[i]) ; i++ ; }
  }

  public Prog translate()
  {
    Method[] meths = new Method[method.length] ;

    int i = 0 ;
    while (i!=meths.length)
    { meths[i] = method[i].translate() ; 
      amt.insert(meths[i], method[i]) ;
      i++ ;
    }

    return new Prog(meths) ;
  }

  public String toString(int dec)
  {
    
    String s = method[0].toString(dec) ;

    int i = 1 ;
    while (i != method.length)
    { s+= "" + ELN + ELN + method[i].toString(dec) ; i++ ; }

    return s ;
  }

  public String toString()
  {  return toString(0) ; }

}

