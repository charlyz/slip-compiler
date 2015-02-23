package slip.internal ;

public abstract class Sexpr extends Expr // simple expression 
{
	public abstract int getRegExpr();
	public abstract void setRegExpr(int a);
	public abstract int getRegDes();
	public abstract void setRegDes(int a);
}

