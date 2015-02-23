package slip.translation ;
import  slip.parser.* ;
import  slip.lexer.* ;
import  java.util.Hashtable ;
import  java.util.Enumeration ;
import  slip.internal.Method ;

// D�but 20.2.2005   11:24 -- 11:44
//                   16:20 -- 16:53
//                   18:07 -- ???
// Reprise 27.2.2005 10:00 -- 11:13
//                   18:05 --

public class AllMethodsTable
{  final private static String ELN = "\n" ;

/* On a une table unique de toutes les m�thodes du programme.

Cette table est index�e sur le nom des m�thodes.

- Pour chaque couple <nom de m�thode du programme, arit�>, on a
  (a) un indicateur si la m�thode est statique ou non statique
      (on ne peut avoir les deux possibilit�s en m�me temps) ;
  (b) une liste de tables sp�cifiques par m�thodes, tri�e sur le
      niveau de la m�thode.

Une seule instance de la classe est cr��e pour un programme donn�.

*/

  public  Cmethod   main = null ; // the main() method
  private Hashtable amt = new Hashtable() ;

  public String toString()
  {
    Enumeration l = amt.elements() ;
    String      s = "" ;

    if (l.hasMoreElements())
       s += l.nextElement().toString() ;

    while (l.hasMoreElements())
    {
       s += ELN + l.nextElement().toString() ;
    }

    return s ;
  }

  
  public void makeMethodsArrays()
  // Calcule une dispatch table pour toutes les m�thodes virtuelles
  // Calcule l'adresse de chaque m�thode statique
  // Calcule l'adresse de chaque dispatch table LMA
  // Met � jour LMAStmtcurAddress avec l'adresse du premier mot qui suit.
  {
    Enumeration l = amt.elements() ;

    while (l.hasMoreElements())
    {
       MethodList ml = (MethodList) l.nextElement() ;
       if (ml.isStatic()) ml.putMethodAddress();
       else ml.makeMethodsArray();
    }
  }

  public void makeDispatchArrays()
  // G�n�re les litt�raux LMA des tables de dispatch.
  {
    Enumeration l = amt.elements() ;

    while (l.hasMoreElements())
    {
       MethodList ml = (MethodList) l.nextElement() ;
       if (!ml.isStatic()) ml.makeDispatchArray();
    }
  }

  public void add(Cmethod method) throws Exception
  // ajouter une m�thode, renvoyer une exception si elle existe d�j�
  // si level == -1, la m�thode est statique
  {
    String key = method.arity + method.name ;

    if (amt.containsKey(key))
    {
      MethodList ml = (MethodList)(amt.get(key)) ;
      ml.insert(method) ;
    }
    else 
    {
      MethodList ml = new MethodList() ;
      ml.insert(method) ;
      amt.put(key, ml) ;
    }

    if (method.arity == 0 & method.level == -1 & (method.name).equals("main"))
       main = method ;
    
  } 

  public void insert(Method meth, Cmethod cmeth)
  {  
     String key = cmeth.arity + cmeth.name ;
     MethodList ml = (MethodList)(amt.get(key));
     ml.insert(meth, cmeth);
  }

  public boolean isDefined(String methName, int methArity) 
  {
    return amt.containsKey(methArity + methName) ;
  }

  public boolean isStatic(String methName, int methArity) 
  {
    return ((MethodList)(amt.get(methArity + methName))).isStatic() ;
  }

  public Method methodAddress(String methName, int methArity) // if static
  {
    return ((MethodList)(amt.get(methArity + methName))).sm ;
  }

  public Method[] methodArray(String methName, int methArity) // if virtual
  {
    return ((MethodList)(amt.get(methArity + methName))).ma ;
  }

  public int dispatchAddress(String methName, int methArity)
  {
    return ((MethodList)(amt.get(methArity + methName))).dispatchAddress ;
  }
 
  public AllMethodsTable()  
  {
  }



}

