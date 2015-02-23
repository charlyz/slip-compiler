package slip.parser; // 21.2.2005 Début 21:45 Fin 23:26
                     // 22.2      Début 21:20 Fin 21:37
                     // 28              21:11 Fin 23:07
                     //  1.3            21:45     22:54
                     //  3.3            22:00     23:00
                     //  6.3             9:30     12:00
                     // 28.3            10:30     12:55
                     //                 13:50     15:30
                     //                 21:10     22:42
import  slip.translation.MethodTable ;
import  slip.internal.Method ;

public abstract class Cnode 
{
  public final static int INC = 2 ;
  public final static String ELN = "\n" ;

  public static MethodTable curvt ;
  public int getVar(String x){ return curvt.getVar(x) + 1 ; }

  public static int nextVar ;
  public static int maxVar ;
  public static void setNextVar(int i){ nextVar = i ; maxVar = i ; }
  public static int useVar()
  {
    if (nextVar > maxVar) maxVar = nextVar ;
    return nextVar ;
  }

  static Method curm;
  

  String spaces(int l)
  {
    if (l == 0) return "" ;

    char[] tspaces = new char[l];
    int i = 0;
    while (i != tspaces.length) { tspaces[i] = ' '; i++; }

    return String.valueOf(tspaces);
  }

  public String toString(int dec)
  { return "erreur" ; }
}

