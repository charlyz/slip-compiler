package slip.myLL1parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Tree<S>
{
	// Descendants du noeud
	private LinkedList<Tree<S>> Children = new LinkedList<Tree<S>>();
	// Symbol contenu dans le noeud
	private S e;

	public Tree(S e){this.e = e;}
	
	public void add(Tree<S> t){Children.addFirst(t);}
	
	public LinkedList<Tree<S>> children(){return Children;}
	
	public Tree<S> child(int n){return Children.get(n);}
	
	public int NbChildren(){return Children.size();}
	
	public S element(){return e;}
	
	public void setElement(S e){this.e = e;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		Iterator it;
		
		sb.append(e.toString());

		if(!Children.isEmpty())
		{
			sb.append("(");
			sb.append(Children);
			sb.append(")\n");
		}
		
		return sb.toString();
	}
}