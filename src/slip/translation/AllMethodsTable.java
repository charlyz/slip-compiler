package slip.translation ;
import  slip.parser.* ;
import  slip.lexer.* ;
import  java.util.Hashtable ;
import  java.util.Enumeration ;
import  slip.internal.Method ;

// Début 20.2.2005   11:24 -- 11:44
//                   16:20 -- 16:53
//                   18:07 -- ???
// Reprise 27.2.2005 10:00 -- 11:13
//                   18:05 --

public class AllMethodsTable
{  final private static String ELN = "\n" ;

/* On a une table unique de toutes les méthodes du programme.

Cette table est indexée sur le nom des méthodes.

- Pour chaque couple <nom de méthode du programme, arité>, on a
  (a) un indicateur si la méthode est statique ou non statique
      (on ne peut avoir les deux possibilités en même temps) ;
  (b) une liste de tables spécifiques par méthodes, triée sur le
      niveau de la méthode.

Une seule instance de la classe est créée pour un programme donné.

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
  // Calcule une dispatch table pour toutes les méthodes virtuelles
  // Calcule l'adresse de chaque méthode statique
  // Calcule l'adresse de chaque dispatch table LMA
  // Met à jour LMAStmtcurAddress avec l'adresse du premier mot qui suit.
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
  // Génère les littéraux LMA des tables de dispatch.
  {
    Enumeration l = amt.elements() ;

    while (l.hasMoreElements())
    {
       MethodList ml = (MethodList) l.nextElement() ;
       if (!ml.isStatic()) ml.makeDispatchArray();
    }
  }

  public void add(Cmethod method) throws Exception
  // ajouter une méthode, renvoyer une exception si elle existe déjà
  // si level == -1, la méthode est statique
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

