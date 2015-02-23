package slip.translation;
import  java.util.Hashtable;
import  slip.parser.Cmethod ;
import  java.util.Enumeration;

public class MethodTable // 20.2.2005  Début 14:35 Fin 14:59
{
  /* Variables locales et paramètres d'une méthode

  */
  static String ELN = "\n" ;

  Hashtable vt = new Hashtable();   // Variable table
  public  int lvn  ;       // number of parameters and local variables - 1
  private int count = -1 ; // to count the formal parameters

  public MethodTable(int fpn)
  { lvn = fpn - 1 ;  }

  public void addFormal(String vn) throws Exception // Add a formal parameter
  {
    if (vt.containsKey(vn)) 
       throw new Exception ("Formal parameter " + vn + " declared at least twice.") ;
    count ++ ; 
    vt.put(vn, new Integer(count));
  }

  public void addLocal(String vn) // Add a local variable if it does not exist.
  {
    if (!(vt.containsKey(vn))) 
    { lvn ++ ; 
      vt.put(vn, new Integer(lvn));
    }
  }

  public int getVar(String x)
  // Pré : x is the name of a formal parameter or local variable 
  {
    return ((Integer)(vt.get(x))).intValue() ;
  }


  public String[] makeVarArray()
  {
    String[]   var = new String[lvn + 1] ;
    Enumeration vl = vt.keys() ;
    while (vl.hasMoreElements())
    {
      String v = (String) vl.nextElement() ;
      int    i = ((Integer)(vt.get(v))).intValue() ;
      var[i]   = v ;
    }

    return var ;
  }
}

