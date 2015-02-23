package slip.parser ;
import  slip.internal.* ;

public abstract class Ccall extends CcompoundRexpr 
{
  public static final int SIMPLE   = 0 ;
  public static final int VARIABLE = 1 ;
  public static final int SUPER    = 2 ;

  public final int sort ;
  
  public String methodName ;
  public Crexpr[] eparam ;

  public Ccall(int sort, String m, Crexpr[] e)
  { super(CALL) ; this.sort = sort ; methodName = m ; eparam = e; }

  abstract Call call(int x, String m, int[] iEparam);
  
  public Stmt translate(Des des, Stmt next)
  {
     int[] iEparam = new int[eparam.length] ;

     int varNumber ;
     if (des instanceof VarDes)  
         varNumber = des.getNumber() ;
     else 
     {   varNumber = useVar() ;
         VarDes d1 = new VarDes(varNumber) ;
         next = new CmdStmt(new NormalAss(des, d1) , next) ;
     }

     Call call = call(varNumber, methodName, iEparam) ;
     return translateEparam(iEparam, new CmdStmt(call, next) ) ;    
  }

  public Stmt translateEparam(int[] iEparam, Stmt next)
  // Pré : eparam.length = iEparam.length
  { 
     Stmt r = next ;

     int  nextVar0 = nextVar ; 
     int i = eparam.length ;

     // Optimisation possible pour les paramètres qui sont des
     // variables simples.

     while (i != 0)
     {
       i-- ;
       nextVar    = nextVar0 + i ;
       iEparam[i] = nextVar ;
       VarDes des = new VarDes(useVar()) ;
       Crexpr  ri = eparam[i] ;
       
       if (ri instanceof CsimpleRexpr)  
       {  
         NormalAss a = new NormalAss(des, ((CsimpleRexpr)ri).translate()) ;
                   r = new CmdStmt(a, r) ;
       }
       else 
       { 
         r  = ((CcompoundRexpr)ri).translate(des, r) ;
       }
     }
     return r ;
  }

  public String toString()
  { 
    String s = methodName + "(" ;
    
    if (eparam.length != 0) 
    {  s += eparam[0] ;
       int i = 1 ;
       while (i != eparam.length)
       { s += ", " + eparam[i] ; i++ ; }
    }

    return s + ")" ; 
  }

}

