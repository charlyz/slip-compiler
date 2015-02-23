package slip.parser;
import  slip.internal.Stmt;
import  slip.internal.Des;

public abstract class Crexpr extends Cnode 
{
  public static final int LEXPR  = 0 ;
  public static final int I      = 7 ;
  public static final int MINUS  = 1 ;
  public static final int BINARY = 2 ;
  public static final int THIS   = 3 ;
  public static final int NULL   = 4 ;
  public static final int NEW    = 5 ;
  public static final int CALL   = 6 ;
  
  public final int sort ;

  public Crexpr(int s)
  { sort = s ; }

  public abstract Stmt translate(Des des, Stmt next);

 
 }

