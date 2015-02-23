package slip.internal;

import java.util.List;

public abstract class Cmd extends AbstractNode
{
  public abstract List<LMAInstruction> translate();
}

