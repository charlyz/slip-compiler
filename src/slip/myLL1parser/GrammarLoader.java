package slip.myLL1parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class GrammarLoader 
{
	private HashMap<String, Terminal> TerminalMap;
	private HashMap<String, NotTerminal> NotTerminalMap;
	private List<Rule> Rules;
	private BufferedReader In;
	private Set<NotTerminal> AllLeftNotTerminal = new HashSet<NotTerminal>();
	
	public GrammarLoader(String f) 
	{	
		TerminalMap = new HashMap<String, Terminal>();
		NotTerminalMap = new HashMap<String, NotTerminal>();
		Rules = new LinkedList<Rule>();
		
		// On ajoute les balises pr�d�finies.
		TerminalMap.put(Grammar.Lambda.getText(), Grammar.Lambda);
		TerminalMap.put(Grammar.Identifier.getText(), Grammar.Identifier);
		TerminalMap.put(Grammar.Constant.getText(), Grammar.Constant);
		NotTerminalMap.put(Grammar.Program.getText(), Grammar.Program);
		
		try 
		{
			In = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public Grammar load() throws Exception
	{
		String line;

		while((line = In.readLine())!= null)
		{
			if(line.length() == 0 || line.startsWith("//"))
				continue;
			// On s�pare left et right
			String[] tmp = line.trim().split("::=");
			if(tmp.length != 2)
				throw new Exception("Erreur de syntaxe de la grammaire: sigle ::= manquant");
			
			// On r�cup�re left, si left est Terminal, on provoque une erreur
			Symbol left = nextSymbol(tmp[0].replace("\t", "").trim());
			if(!(left instanceof NotTerminal))
				throw new Exception("Erreur de syntaxe de la grammaire: un symbole de gauche est terminal");
			
			// On s�pare toutes les d�rivations possibles de right
			String[] tokens = tmp[1].split("\\|");
			int i = 0;
			List<Symbol> right = new LinkedList<Symbol>();
			
			// Pour chaque d�rivation, on r�cup�re les symboles
			while(i<tokens.length)
			{
				String[] sym = tokens[i].replace("\t", "").split(" ");
				int j = 0;
				
				// On r�cup�re chaque symbole et on l'ajoute � une liste
				while(j<sym.length)
				{	
					if(sym[j].trim().length()>2)
						right.add(nextSymbol(sym[j]));
					j++;
				}

				// Si right est vide, il y a un probleme.
				if(right.isEmpty())
					throw new Exception("Erreur de syntaxe de la grammaire: un symbole de droite est manquant");
				
				// On cr�e la r�gle, left et right sont correctes.
				Rules.add(new Rule((NotTerminal)left, right));
				AllLeftNotTerminal.add((NotTerminal)left);
				right = new LinkedList<Symbol>();
				i++;
			}
		}
		
		// On v�rifie que tous les non terminaux soient joignables.
		checkGrammar();
		
		return new Grammar(new LinkedList<Terminal>(TerminalMap.values()), new LinkedList<NotTerminal>(NotTerminalMap.values()), Rules);
	}

	/**
	 * pre: balise != null && balise.length > 2
	 * @param balise
	 * @return
	 */
	private Symbol nextSymbol(String balise) throws Exception
	{		
		char c1 = balise.charAt(0);
		char cn = balise.charAt(balise.length()-1);

		String name = balise.substring(1, balise.length()-1);

		// La balise est un terminal
		if(c1 == '\'' && cn == '\'')
		{
			Terminal symbol = TerminalMap.get(name);
			// Si le symbole ne se trouve pas dans la map des terminal, on l'ajoute.
			if(symbol == null)
			{
				symbol = new Terminal(name);
				TerminalMap.put(name, symbol);
			}
			return symbol;
		}
		// La balise est un non terminal
		else if(c1 == '<' && cn == '>')
		{
			NotTerminal symbol = NotTerminalMap.get(name);
			// Si le symbole ne se trouve pas dans la map des non terminal, on l'ajoute.
			if(symbol == null)
			{
				symbol = new NotTerminal(name);
				NotTerminalMap.put(name, symbol);
			}
			return symbol;
		}
		else
			// La balise n'est pas contenue entre '' et <> donc est invalide
			throw new Exception("Erreur dans la syntaxe de la grammaire: la balise '" + balise + "' n'est pas entre '' ou <>");
	}
	
	/**
	 * pr�: grammaire d�j� charg�e
	 * @throws IOException
	 */
	public void checkGrammar() throws Exception
	{
		Iterator<Rule> it = Rules.iterator();
		
		// On parcourt toutes les r�gles pour v�rifier que les non terminaux
		// � droite aient tous une d�rivation.
		while(it.hasNext())
		{
			Iterator<Symbol> itRight = it.next().right().iterator();
			while(itRight.hasNext())
			{
				Symbol s = itRight.next();
				if(s instanceof NotTerminal)
				{
					// On v�rifie que le non terminal "s" situ� � droite d'une r�gle
					// poss�de une d�rivation.
					if(!AllLeftNotTerminal.contains(s))
						throw new Exception("Le non terminal " + (NotTerminal)s + " n'a pas de d�rivation.");
				}
			}
			
		}
	}

}
