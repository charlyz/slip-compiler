package slip.internal;

import java.util.List;

public abstract class Expr extends AbstractNode // Any (right) expression
{
       public abstract List<LMAInstruction> translateExpr();     
}

