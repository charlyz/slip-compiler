package slip.parser;

public abstract class Clexpr extends CsimpleRexpr 
{
  public static final int VAR   = 0 ;
  public static final int FIELD = 1 ;
		public static final int THIS  = 2 ;

  public final int sort ;
  public final String x ;

  public Clexpr(int s, String x)
  { super(LEXPR) ; sort = s ; this.x = x ; }

  public String toString(){ return x ; }

}

