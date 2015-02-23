package slip.parser;
import  slip.internal.CmdStmt ;
import  slip.internal.Skip ;
import  slip.internal.Stmt ;


public class Cwhile extends Ccmd
{
  public Ccond c ;
  public Ccmd  cmd ; 

  public Cwhile(Ccond c, Ccmd cmd)
  { super(WHILE) ; this.c = c ; this.cmd = cmd ; }

  public String toString(int dec)
  { 
    return spaces(dec) + "while (" + c + (")" + ELN)
           + cmd.toString(dec + INC) ;
  }

  public Stmt translate(Stmt next)
  {
    CmdStmt skip = new CmdStmt(new Skip()) ;
    Stmt res  = c.translate(skip, next) ;
    skip.setLabel(cmd.translate(res)) ;

    return res ;
  }

}

