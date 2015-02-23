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
		// Program est son propre symbole étendu
		ExtendedSymbols.put(Program, Program2);
		
		Iterator<Rule> it = Rules.iterator();
		
		// Pour chaque règle, on va créer un symbole étendu
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
					
					// Si on a une règle du style X' -> X', on ne l'ajoute pas
					if(X == left && right.isEmpty()) 
						continue;
					
					String X2_Name = X.getText()+"'";
					String Y2_Name = left.getText()+"'";
					
					NotTerminal X2 = NotTerminalNames.get(X2_Name);
					if(X2 == null)
					{
						// Le non terminal étendu X' n'existe pas, on le crée
						X2 = new NotTerminal(X2_Name);
						// On l'ajoute à la map qui établit la relation name <--> symbole
						NotTerminalNames.put(X2_Name, X2);
						// On ajoute ce nouveau non terminal à la liste des non terminaux
						NotTerminalList.add(X2);
					}

					NotTerminal Y2 = NotTerminalNames.get(Y2_Name);
					if(Y2 == null)
					{
						// Le non terminal étendu X' n'existe pas, on le crée
						Y2 = new NotTerminal(Y2_Name);
						// On l'ajoute à la map qui établit la relation name --> symbole
						NotTerminalNames.put(Y2_Name, Y2);
						// On ajoute ce nouveau non terminal à la liste des non terminaux
						NotTerminalList.add(Y2);
					}
					// On crée la partie de droite de la règle définissant X2
					LinkedList<Symbol> X2_Right = new LinkedList<Symbol>(right);
					X2_Right.add(Y2);
					// On ajoute enfin la nouvelle règle créée
					ExtendedRules.add(new Rule(X2, X2_Right));
					// On définit la relation Symbole --> Symbole étendu
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
			// Si l'ensemble P1 de "s" est vide c'est qu'il n'a pas encore été défini.
			// On retourne un ensemble vide.
			if(s.P1().isEmpty())
				return new HashSet<Terminal>();
			
			// Si l'ensemble P1 de "s" possède un lambda, on ajoute cet ensemble au résultat
			// et on passe au symbole suivant. En effet, p1(s) renvoit les premiers terminaux 
			// possibles. Si parmis ceux ci se trouvent un lambda, on va prendre en compte les
			// éléments de p1(symbol) du symbol suivant.
			if(s.P1().contains(Lambda))
				res.addAll(s.P1());
			// Il n'y a pas de lambda dans l'ensemble p1 de "s", on stoppe la boucle et 
			// on enlève les lambda du résultat. Il y a un lambda dans le résutat que 
			// si tous les ensembles P1 à droite d'une règle en possèdent un.
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
		 * Pour cet algorithme, on va utiliser le concept d'itération du point fixe.
		 * C'est à dire que chaque ensemble P1 d'un symbole dépend des ensembles P1
		 * des symboles qui le dérivent. Etant donné qu'il n'y pas un ordre des symboles 
		 * pour calculer les ensembles, on tente de calculer tous les ensembles P1 de 
		 * tous les symboles, si les informations sont insuffisantes on ne fait rien et on
		 * passe au symbole suivant. Quand on a tenté de calculer les ensembles P1 
		 * de tous les symboles, on recommence l'opération jusqu'à ce que, petit à petit,
		 * tous les symboles soient calculés. 
		 */

		// Avant de commencer, on définit la valeur des ensembles P1
		// des terminaux. Ceux ci ont comme unique élément eux meme.
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
			
			// Pour chaque symbole de gauche d'une règle, on va tenter de définir
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
	 * pré: CreateAugmentedGrammar d'abord
	 * @param s
	 */
	public void ComputeS1()
	{	
		/*
		 * Rappel: En augmentant la grammaire, on a ajouté un
		 * certain nombre de règles de la manière suivante:
		 * Y  -> alpha X beta
		 * X2 -> beta Y2
		 * 
		 * Celà va permettre de connaitre les symboles pouvant
		 * apparaitre "après" chaque symbole présent "à la base".
		 * Le principe est simple, on définit l'ensemble s1 de S comme
		 * étant égal à l'ensemble p1 de S' (que l'on connait déjà). 
		 * 
		 */
		for(Entry<NotTerminal, NotTerminal> entry : ExtendedSymbols.entrySet()) 
			entry.getKey().S1().addAll(entry.getValue().P1());
	}
	
	public List<Rule> Rules() {return Rules;}
	public List<Terminal> Terminals() {return TerminalList;}
	public List<NotTerminal> NotTerminals() {return NotTerminalList;}
}
