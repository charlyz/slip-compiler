package slip.myLL1parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import slip.mylexer.Constant;
import slip.mylexer.Identifier;

public class Grammar 
{
	public final static Terminal Lambda = new Terminal("lambda");
	public final static Identifier Identifier = new Identifier("identifier");
	public final static Constant Constant = new Constant("constant");
	public final static NotTerminal Program = new NotTerminal("PROGRAM");
	
	private List<Rule> Rules;
	private List<Rule> ExtendedRules;
	private List<Terminal> TerminalList;
	private List<NotTerminal> NotTerminalList;
	private Map<NotTerminal, NotTerminal> ExtendedSymbols = new HashMap<NotTerminal, NotTerminal>();
	
	public Grammar(List<Terminal> t, List<NotTerminal> nt, List<Rule> r)
	{
		TerminalList = t;
		NotTerminalList = nt;
		Rules = r;
	}
	
	public void ComputeExtendedRules()
	{	
		HashMap<String, NotTerminal> NotTerminalNames = new HashMap<String, NotTerminal>();
		
		// Program' -> lambda
		LinkedList<Symbol> right = new LinkedList<Symbol>();
		right.add(Lambda);
		NotTerminal Program2 = new NotTerminal(Grammar.Program.getText()+"'");
		ExtendedRules = new LinkedList<Rule>(Rules);
		ExtendedRules.add(new Rule(Program2, right));
		// Program est son propre symbole �tendu
		ExtendedSymbols.put(Program, Program2);
		
		Iterator<Rule> it = Rules.iterator();
		
		// Pour chaque r�gle, on va cr�er un symbole �tendu
		// du symbole de gauche.
		// Y  -> alpha X beta
		// X2 -> beta Y2
		while(it.hasNext())
		{
			Rule p = it.next();
			right = new LinkedList<Symbol>(p.right());
			NotTerminal left = p.left();
			
			while(!right.isEmpty())
			{
				Symbol symbol = right.removeFirst();
				
				if(symbol instanceof NotTerminal)
				{
					NotTerminal X = (NotTerminal)symbol;
					
					// Si on a une r�gle du style X' -> X', on ne l'ajoute pas
					if(X == left && right.isEmpty()) 
						continue;
					
					String X2_Name = X.getText()+"'";
					String Y2_Name = left.getText()+"'";
					
					NotTerminal X2 = NotTerminalNames.get(X2_Name);
					if(X2 == null)
					{
						// Le non terminal �tendu X' n'existe pas, on le cr�e
						X2 = new NotTerminal(X2_Name);
						// On l'ajoute � la map qui �tablit la relation name <--> symbole
						NotTerminalNames.put(X2_Name, X2);
						// On ajoute ce nouveau non terminal � la liste des non terminaux
						NotTerminalList.add(X2);
					}

					NotTerminal Y2 = NotTerminalNames.get(Y2_Name);
					if(Y2 == null)
					{
						// Le non terminal �tendu X' n'existe pas, on le cr�e
						Y2 = new NotTerminal(Y2_Name);
						// On l'ajoute � la map qui �tablit la relation name --> symbole
						NotTerminalNames.put(Y2_Name, Y2);
						// On ajoute ce nouveau non terminal � la liste des non terminaux
						NotTerminalList.add(Y2);
					}
					// On cr�e la partie de droite de la r�gle d�finissant X2
					LinkedList<Symbol> X2_Right = new LinkedList<Symbol>(right);
					X2_Right.add(Y2);
					// On ajoute enfin la nouvelle r�gle cr��e
					ExtendedRules.add(new Rule(X2, X2_Right));
					// On d�finit la relation Symbole --> Symbole �tendu
					ExtendedSymbols.put(X, X2);
				}
			}
		}
	}
	
	public Set<Terminal> MergeSets(List<Symbol> l)
	{
		Iterator<Symbol> it = l.iterator();
		Set<Terminal> res = new HashSet<Terminal>();
		
		while(it.hasNext())
		{
			Symbol s = it.next();
			// Si l'ensemble P1 de "s" est vide c'est qu'il n'a pas encore �t� d�fini.
			// On retourne un ensemble vide.
			if(s.P1().isEmpty())
				return new HashSet<Terminal>();
			
			// Si l'ensemble P1 de "s" poss�de un lambda, on ajoute cet ensemble au r�sultat
			// et on passe au symbole suivant. En effet, p1(s) renvoit les premiers terminaux 
			// possibles. Si parmis ceux ci se trouvent un lambda, on va prendre en compte les
			// �l�ments de p1(symbol) du symbol suivant.
			if(s.P1().contains(Lambda))
				res.addAll(s.P1());
			// Il n'y a pas de lambda dans l'ensemble p1 de "s", on stoppe la boucle et 
			// on enl�ve les lambda du r�sultat. Il y a un lambda dans le r�sutat que 
			// si tous les ensembles P1 � droite d'une r�gle en poss�dent un.
			else
			{
				res.remove(Lambda);
				res.addAll(s.P1());
				break;
			}
		}
		return res;
	}
	
	public void ComputeP1()
	{
		/*
		 * Pour cet algorithme, on va utiliser le concept d'it�ration du point fixe.
		 * C'est � dire que chaque ensemble P1 d'un symbole d�pend des ensembles P1
		 * des symboles qui le d�rivent. Etant donn� qu'il n'y pas un ordre des symboles 
		 * pour calculer les ensembles, on tente de calculer tous les ensembles P1 de 
		 * tous les symboles, si les informations sont insuffisantes on ne fait rien et on
		 * passe au symbole suivant. Quand on a tent� de calculer les ensembles P1 
		 * de tous les symboles, on recommence l'op�ration jusqu'� ce que, petit � petit,
		 * tous les symboles soient calcul�s. 
		 */

		// Avant de commencer, on d�finit la valeur des ensembles P1
		// des terminaux. Ceux ci ont comme unique �l�ment eux meme.
		Iterator<Terminal> it = TerminalList.iterator();
		while(it.hasNext())
		{	
			Terminal t = it.next();
			t.P1().add(t);
		}
		
		boolean b = true;
		
		while(b)
		{	
			b = false;
			Iterator<Rule> it_Rules = ExtendedRules.iterator();
			
			// Pour chaque symbole de gauche d'une r�gle, on va tenter de d�finir
			// son ensemble P1.
			while(it_Rules.hasNext())
			{
				Rule r = it_Rules.next();
				if(r.left().P1().addAll(MergeSets(r.right())))
					b = true;
			}
		}
	}
	
	/**
	 * pr�: CreateAugmentedGrammar d'abord
	 * @param s
	 */
	public void ComputeS1()
	{	
		/*
		 * Rappel: En augmentant la grammaire, on a ajout� un
		 * certain nombre de r�gles de la mani�re suivante:
		 * Y  -> alpha X beta
		 * X2 -> beta Y2
		 * 
		 * Cel� va permettre de connaitre les symboles pouvant
		 * apparaitre "apr�s" chaque symbole pr�sent "� la base".
		 * Le principe est simple, on d�finit l'ensemble s1 de S comme
		 * �tant �gal � l'ensemble p1 de S' (que l'on connait d�j�). 
		 * 
		 */
		for(Entry<NotTerminal, NotTerminal> entry : ExtendedSymbols.entrySet()) 
			entry.getKey().S1().addAll(entry.getValue().P1());
	}
	
	public List<Rule> Rules() {return Rules;}
	public List<Terminal> Terminals() {return TerminalList;}
	public List<NotTerminal> NotTerminals() {return NotTerminalList;}
}
