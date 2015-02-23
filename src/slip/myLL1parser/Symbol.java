package slip.myLL1parser;

import java.util.HashSet;
import java.util.Set;

public class Symbol
{
    protected String text;
    // Tous les premiers terminaux vers lesquels on peut dériver le symbole this
    private Set<Terminal> p1 = new HashSet<Terminal>();
    // Tous les terminaux pouvant suivre le symbole this
    private Set<Terminal> s1 = new HashSet<Terminal>();
    // Index dans la parseTable
    public int ptIndex = 0;

    public Symbol(String s)
    {	
    	text = s;
    }

    public String getText() { return text; }

    public boolean equals(Object o){ return o instanceof Symbol && text != null && text.equals(((Symbol)o).getText());}

    public Set<Terminal> P1() { return p1; }
    
    public Set<Terminal> S1() { return s1; }
    
    public int index() { return ptIndex; }
}
