package slip.parser;

class Cell
{
  Object n ;
  Cell next ;

  Cell(Object n, Cell next)
  { this.n = n ; this.next = next ; }

}

class List
{
  private int length  = 0 ;
  Cell first = null ;

  void add(Object toto)
  {
     first = new Cell(toto, first) ;
     length++ ;
  }

  Cmethod[] methodArray()
  {
    Cmethod[] r = new Cmethod[length] ;
    int i = length ;

    while (i != 0) // also first != null
    { i-- ; r[i] = (Cmethod)(first.n) ; first = first.next ; }

    return r ;
  }

  Ccmd[] cmdArray()
  {
    Ccmd[] r = new Ccmd[length] ;
    int i = length ;

    while (i != 0) // also first != null
    { i-- ; r[i] = (Ccmd)(first.n) ; first = first.next ; }

    return r ;
  }

  Clexpr[] lexprArray()
  {
    Clexpr[] r = new Clexpr[length] ;
    int i = length ;

    while (i != 0) // also first != null
    { i-- ; r[i] = (Clexpr)(first.n) ; first = first.next ; }

    return r ;
  }


  Crexpr[] rexprArray()
  {
    Crexpr[] r = new Crexpr[length] ;
    int i = length ;

    while (i != 0) // also first != null
    { i-- ; r[i] = (Crexpr)(first.n) ; first = first.next ; }

    return r ;
  }

  String[] stringArray()
  {
    String[] r = new String[length] ;
    int i = length ;

    while (i != 0) // also first != null
    { i-- ; r[i] = (String)(first.n) ; first = first.next ; }

    return r ;
  }

}

