package slip.translation ;
import  slip.parser.Cmethod ;
import  slip.internal.Method ;

class MethodList  // 20.2.2005 Début : 15:00 Fin : ???
{
  MethodListCell first = null ;
  private int maxlevel = -1 ;
  Method[] ma ; // Dispatch table for virtual methods
  int      dispatchAddress ; // Adresse LMA de la dispatch table
  Method   sm ; // Static method

  public boolean isStatic(){ return maxlevel == -1 ; }
  
  private final static String ELN = "\n" ;

  public String toString()
  {
     String s = "" ;

     MethodListCell next = first ;
     while (next != null)
     { s += ELN + next.m.toStringBis() ; 
       next = next.next ;
     }

     s += ELN + "maxlevel == " + maxlevel ;

     if (isStatic())
        s+= ELN + methodAddressToString() ;
     else s += ELN + methodsArrayToString() ;

     return s ;
  }

  public void makeMethodsArray()
  {
    
    ma = new Method[maxlevel + 1] ;

    int i = 0 ; 
    MethodListCell next = first ;
    Method im = null ;

    while (i != maxlevel +1)
    {  
      if (next.m.level == i)
      {  im = next.im ; next = next.next ; }

      ma[i] = im ; i++ ;
    }
  }

  

  public String methodsArrayToString()
  {
    String s = "" ;

    if (ma==null) return "Table of methods is not defined yet." ;

    int i = 0 ;
    while (i!= maxlevel + 1)
    { s += ELN + "  level/" + i + " is " ;
      if (ma[i]!=null) s+= ma[i].toStringBis() ;
      else s+= "not defined." ;
      i++ ;
    }

    return s ;
  }

  public String methodAddressToString()
  {
    if (sm==null) return "Address of method is not defined yet." ;

    return "static method : " + sm.toStringBis() ;
  }

  public void putMethodAddress(){  sm = first.im ; }

  public void insert(Method im, Cmethod c)
  {
    MethodListCell cur = first ;
    while (cur.m != c) cur = cur.next ;
    cur.putMethod(im) ;
  }

  public void insert(Cmethod c) throws Exception 
  {
    
    if (first == null) 
    { 
       first = new MethodListCell(c) ; 
       maxlevel = c.level ;
       return ; 

    }

    if (first.m.level == c.level) 
    throw new Exception("Deux méthodes " + c.name 
                        + "/" + c.level + "(" + c.arity + ")");

    if (c.level == -1)
    throw new Exception("La méthode " + c.name +
                        "(" + c.arity + ")" +
                        " est à la fois statique et non statique.");

    MethodListCell mlc = new MethodListCell(c) ;


    if (first.m.level > c.level) 
    { 
       mlc.next = first ; 
       first = mlc ; 
       return ;
    }

    MethodListCell pre  = first ;
    MethodListCell next = first.next ;


    /* Invariant
       ---------
       c.level > x.m.level pour tous les x de first jusque pre.
    */

    while ((next != null) && (next.m.level < c.level)) 
    { pre = next ; next = next.next ; }

    if (next == null) { pre.next = mlc ; maxlevel = c.level ; }
    else if (next.m.level == c.level) 
         throw new Exception("Deux méthodes " + c.name 
                        + "/" + c.level + "(" + c.arity + ")");
         else { pre.next = mlc ; mlc.next = next ; } 

    return ;

  }

  public int maxLevel(){ return maxlevel ; }

}

