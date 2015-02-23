package slip.parser;
import  slip.internal.Stmt;

public class Cseq extends Ccmd
{
  public Ccmd[]  cmd ; 

  public Cseq(Ccmd[] cmd)
  { super(SEQ) ; this.cmd = cmd ; }

  public String toString(int dec)
  {
    String s = spaces(dec) + "{" ;

    int i = 0 ;
    while (i!=cmd.length)
    { s += "" + ELN + cmd[i].toString(dec + INC) ; i++ ; }

    return s + ("" + ELN) + spaces(dec) + "}" ;
  }

  public Stmt translate(Stmt next)
  {
     int    i = cmd.length ;
     Stmt res = next ;

     while (i!=0)
     {
       i--; res = cmd[i].translate(res) ;
     }

     return res ;
  }

}

