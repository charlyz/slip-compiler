package slip.parser;
import  slip.internal.Method;
import  slip.internal.Stmt;

import  slip.translation.MethodTable ;
import  java.util.Enumeration ;

public class Cmethod extends Cnode
{
  public final String name ;
  public final int level ; // -1 => static
  public final int arity ; // == fparam.length
  public final String[] fparam ;
  public final Cseq body;
  public final MethodTable curvt ;
  public int   getVar(String x){ return curvt.getVar(x) ; }

  public final String[] vt ; // table of formal parameters and variables

  public Cmethod(String name, int level, String[] fparam, 
                 Cseq body, MethodTable curvt)
  { this.name   = name ; 
    this.level  = level ; 
    this.fparam = fparam ; 
    this.arity  = fparam.length ; 
    this.body   = body ; 
    this.curvt  = curvt ;
    this.vt     = curvt.makeVarArray() ;

  }

  public Method translate()
  {
    maxVar      = vt.length ;
    nextVar     = maxVar + 1 ;
    Cnode.curvt = curvt ;

    Method res = new Method(name, 
                            vt,
                            level==-1,
                            level, 
                            arity, 
                            vt.length,
                            0);
    Cnode.curm = res ;

    Stmt first = body.translate(res) ;

    res.setPar(first, maxVar - vt.length) ;

    return res ;
  }

  public String toStringBis()
  {
    String s = name + "/" + level + "(" + arity + ")" ;

    int    i = 0 ;
    while (i != vt.length)
    {
      s += ELN + "  " + vt[i] + " : " + i ;
      i++ ;
    }
    
    return s ;
  }

  public String toString(int dec)
  {
    String s = spaces(dec) + name ;
    
    if (level != -1) s += "/" + level ;
    s += "(" ;
    

    if (arity != 0)
    { s += fparam[0] ; 
      int i = 1 ;
      while (i != arity)
      { s += ", " + fparam[i] ; i++ ; }
    }

    return s + (")" + ELN) + body.toString(dec) ;
  }

  public String toString()
  { return toString(0) ; }

}

