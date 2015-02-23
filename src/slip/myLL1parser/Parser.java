package slip.myLL1parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;

import slip.mylexer.Constant;
import slip.mylexer.Identifier;
import slip.mylexer.Lexer;
import slip.mylexer.ReservedWord;
import slip.mylexer.SpecialChar;


public class Parser 
{
	// Tableau � deux dimensions permettant de choisir une r�gle � executer
	// en fonction du non terminal au dessus la pile et du terminal lu.
	private Rule[][] ParsingTable;
	// Grammaire qui est utilis�e pour accepter ou non le code source.
	private Grammar G;
	// Chemin du fichier contenant la sp�cification BNF de la grammaire.
	private String GrammarPath = "SyntaxeConcreteLL1BIS.txt";
	// Chemin du fichier contenant le code source � parser.
	private String InputPath;
	// Analyseur lexical qui fournit au fur et � mesure chaque symbole du code source.
	private Lexer Lex;
	// Relation entre les objets Terminal du lexer et les objets Terminal de la grammaire
	// pour permettre l'utilisation de la table de parsing
	private HashMap<Terminal, Integer> SymbolToIndex = new HashMap<Terminal, Integer>();
	// Relation entre un non terminal et toutes les r�gles de d�rivation de ce dernier.
	private HashMap<NotTerminal, Set<Rule>> AllLeftNotTerminals;
	
	/**
	 * Constructeur de la classe.
	 * @param s: Chemin du fichier source � parser.
	 */
	public Parser(String s)
	{
		InputPath = s;
	}
	
	/**
	 * Cr�e la relation "Objet Terminal du Lexer" -> "Index dans la parsing table"
	 * dans la HashMap SymbolToIndex
	 * 
	 * @post SymbolToIndex modifi�
	 *
	 */
	public void GenerateSymbolToIndex()
	{
		/*
		 * Il n'existe aucun lien entre les objets Terminal du lexer et les objets
		 * Terminal de la grammaire. Cette relation est importante pour pouvoir utiliser
		 * la table de parsing qui a �t� cr��e � partir des terminaux de la grammaire.
		 * 
		 * Etape 1: Mettre tous les terminaux provenant du Lexer dans une HashMap TerminalsLexer
		 * 
		 * Etape 2: Pour chaque terminal de TerminalsLexer, chercher son correspondant dans
		 * G.Terminals() pour connaitre son index dans la table de parsing
		 * 
		 */
		HashMap<String, Terminal> TerminalsLexer = new HashMap<String, Terminal>();
		
		TerminalsLexer.put(Grammar.Constant.getText(), Grammar.Constant);
		TerminalsLexer.put(Grammar.Identifier.getText(), Grammar.Identifier);
		TerminalsLexer.put("if", ReservedWord.RWIF);
		TerminalsLexer.put("else", ReservedWord.RWELSE);
		TerminalsLexer.put("then", ReservedWord.RWTHEN);
		TerminalsLexer.put("while", ReservedWord.RWWHILE);
		TerminalsLexer.put("read", ReservedWord.RWREAD);
		TerminalsLexer.put("write", ReservedWord.RWWRITE);
		TerminalsLexer.put("new", ReservedWord.RWNEW);
		TerminalsLexer.put("super", ReservedWord.RWSUPER);
		TerminalsLexer.put("this", ReservedWord.RWTHIS);
		TerminalsLexer.put("null", ReservedWord.RWNULL);
		TerminalsLexer.put("do", ReservedWord.RWDO);
		TerminalsLexer.put("meth", ReservedWord.RWMETH);
		TerminalsLexer.put("return", ReservedWord.RWRETURN);
		TerminalsLexer.put("end", ReservedWord.RWEND);
		TerminalsLexer.put("elseif", ReservedWord.RWELSEIF);
		 
		TerminalsLexer.put(".", SpecialChar.SCDOT);
		TerminalsLexer.put("(", SpecialChar.SCPARL);
		TerminalsLexer.put(")", SpecialChar.SCPARR);
		TerminalsLexer.put("{", SpecialChar.SCACCL);
		TerminalsLexer.put("}", SpecialChar.SCACCR);
		TerminalsLexer.put("&", SpecialChar.SCAND);
		TerminalsLexer.put("/", SpecialChar.SCDIV);
		TerminalsLexer.put("<", SpecialChar.SCSPP);
		TerminalsLexer.put(">", SpecialChar.SCSPG);
		TerminalsLexer.put("=", SpecialChar.SCEQUAL);
		TerminalsLexer.put("!", SpecialChar.SCNEG);
		TerminalsLexer.put(",", SpecialChar.SCVIR);
		TerminalsLexer.put(";", SpecialChar.SCPOINTVIR);
		TerminalsLexer.put("*", SpecialChar.SCMUL);
		TerminalsLexer.put("+", SpecialChar.SCPLUS);
		TerminalsLexer.put("-", SpecialChar.SCMINUS);
		TerminalsLexer.put("%", SpecialChar.SCMOD);
		TerminalsLexer.put("<=", SpecialChar.SCPPE);
		TerminalsLexer.put(">=", SpecialChar.SCPGE);
		TerminalsLexer.put("!=", SpecialChar.SCDIF);
		TerminalsLexer.put("==", SpecialChar.SCEGA);
		TerminalsLexer.put("->", SpecialChar.SCARROW);
		TerminalsLexer.put("EOF", SpecialChar.EOF);
		
		SymbolToIndex = new HashMap<Terminal, Integer>();
		Iterator<Terminal> it = G.Terminals().iterator();
		// Pour chaque terminal de la grammaire, on cherche son correspondant
		// dans TerminalsLexer
		while(it.hasNext())
		{
			Terminal t = it.next();
			SymbolToIndex.put(TerminalsLexer.get(t.getText()), t.index());
		}
	}
	
	/**
	 * Renvoit l'arbre syntaxique du code source contenu dans InputPath.
	 * Si une exception survient dans le chargement de la grammaire, pendant
	 * l'analyse lexicale ou pendant l'analyse syntaxique, l'analyse est stopp�e.
	 * 
	 * @return "Analyse lexicale termin�e" s'affiche � l'�cran si le code source
	 * est syntaxiquement correct � la grammaire ou une exception est d�clench�e
	 * s'il y a une erreur.
	 * 
	 * @post G initialis�, Lex initialis�, SymbolToIndex intialis�, ParseTable
	 * initialis�
	 * 
	 * @throws Exception
	 */
	public Tree<Symbol> parse() throws Exception
	{
		/*
		 * Cette m�thode v�rifie que le code source est syntaxiquement
		 * correct par rapport � la grammaire.
		 * 
		 * Etape 1: Chargement de l'analyseur lexical
		 * Etape 2: Chargement de la grammaire.
		 * Etape 3: Cr�ation de la grammaire �tendue.
		 * Etape 4: Calcul des ensembles P1 et S1.
		 * Etape 5: V�rification que la grammaire est bien LL1.
		 * Etape 6: G�n�ration des relations objets Terminal du Lexer -> Index dans la parsing table
		 * 			(Les index sont calcul�s � la cr�ation d'un Terminal)
		 * Etape 7: G�n�ration de la table de parsing
		 * Etape 8: Analyse syntaxique (+ g�n�ration de l'arbre syntaxique)	 
		 * */

		// Analyse Lexicale.
		// Le fichier contenant le code source sera traduit en 
		// une s�rie d'objets du package Slip.mylexer.* repr�sentant des "Terminal" 
		// dont le dernier est EOF signifiant qu'on a lu tous les symboles. 
		// On utilisera plus bas la m�thode nextTerminal() qui renvoit 
		// le prochain symbole � lire.
		// Si un symbole n'est pas reconnu par l'analyseur lexical, une 
		// exception se d�clenche (par exemple ').
		Lex = new Lexer(InputPath);
		
		// Chargement de la grammaire
		GrammarLoader GL = new GrammarLoader(GrammarPath);
		// Le fichier contenant la grammaire BNF du langage est
		// traduit en une s�rie de symboles de type Slip.myLL1parser.Terminal
		// et Slip.myLL1parser.NotTerminal.
		// Si la syntaxe BNF n'est pas respect�e ou si un Non Terminal ne 
		// poss�de pas au moins une d�rivation, une exception se d�clenche.
		// NB: Pour charger une grammaire BNF plus facilement, celle ci
		// doit respecter certaines normes sp�cifi�es dans la classe GrammarLoader.
		G = GL.load();
		// Augmentation de la grammaire selon ce sch�ma:
		// Pour toute r�gle:
		// Y  -> alpha X beta
		// on cr�e
		// X2 -> beta Y2
		// o� alpha, beta sont des terminaux.
		G.ComputeExtendedRules();
		// Calcul des ensembles P1 de chaque symbole en utilisant
		// l'algorithme du point fixe.
		G.ComputeP1();
		// Calcul des ensembles S1 de chaque symbole "de base".
		// Tous les ensembles P1 des symboles "prime" deviennent les 
		// ensembles S1 de leur correspondant "de base".
		G.ComputeS1();
		
		// V�rification que la grammaire est bien LL1.
		// Condition 1: Pour chaque non terminal X, il ne peut pas exister de 
		// d�rivations diff�rentes o et o' de X telle que l'intersection de 
		// p1(o) et p1(o') est non vide. 
		checkCondition1();
		// Condition 2: Pour chaque non terminal X, si p1(X) contient lambda alors l'intersection
		// de p1(X) et s1(X) doit etre vide.
		checkCondition2();

		// On cr�e une relation entre un objet Terminal renvoy� par le Lexer 
		// et son index dans la table de parsing. En effet, la table de parsing
		// est g�n�r�e � partir d'objet Terminal de GrammarLoader.
		GenerateSymbolToIndex();
		
		// G�n�ration de la table de parsing.
		// Pour chaque r�gle "de base":
		// Soit A -> alpha, pour chaque �l�ment "a" de p1(alpha) 
		// on d�finit table[A, a] : A -> alpha.
		//
		// Si lambda est contenu dans p1(alpha), on va aller voir AUSSI dans 
		// l'ensemble s1(alpha) qui sont les terminaux pouvant se trouver apr�s A
		// puisque p1(alpha) peut etre vide. On fera donc:
		// Pour tout b dans s1(A): table[A, b] : A -> alpha
		GenerateParsingTable();
		
		// Pile qui sert � savoir ce qui est attendu comme symbole provenant du lexer
		Stack<Tree<Symbol>> stack = new Stack<Tree<Symbol>>();
		// Premier terminal du code source
		Terminal t = Lex.nextTerminal();
		// Ajout d'un �l�ment � la pile, le symbole <PROGRAM>
		// On encapsule l'�l�ment dans un arbre qui servira � la traduction
		// en langage interne. Pour l'algorithme, il ne sert � rien.
		Tree<Symbol> root = new Tree<Symbol>(Grammar.Program);
		stack.push(root);
		
		System.out.println("Analyse syntaxique...");
		// D�but de l'algorithme
		while(!stack.isEmpty())
		{
			// R�cup�ration du symbole au sommet de la pile
			Tree<Symbol> tree = stack.pop();
			Symbol x = tree.element();
			
			//System.out.println("Symbole attendu: " + x);
			//System.out.println("Symbole en cours: " + t);
			//System.out.println("Symboles restants sur la pile: " + stack);
			//System.out.println();
			
			// Si le symbole attendu est non terminal, on va v�rifier 
			// que le symbole en cours est identique � ce symbole
			// ou qu'il est �gal � lambda (auquels cas on passera au symbole
			// suivant de la pile).
			if(x instanceof Terminal)
			{
				if((x == Grammar.Identifier && t instanceof Identifier) ||
				   (x == Grammar.Constant && t instanceof Constant)     ||
				   (x.getText().equals(t.getText())))
				{	
					// Terminal lu correspond au Terminal attendu, on lit le prochain Terminal.
					t = Lex.nextTerminal();
				}					
				else if(x != Grammar.Lambda)
				{
					throw new Exception("Symbole '" + t.getText() + "' incorrect. Attendu: " + x);
				}
			}
			// Si le symbole attendu est non terminal, on va r�cup�rer la r�gle
			// � appliquer en fonction de celui-ci et du symbole en cours.
			else if(x instanceof NotTerminal)
			{ 		
				int index;
				// On r�cup�re l'index du symbole en cours dans la parsing table
				if(t instanceof Identifier)
					index = SymbolToIndex.get(Grammar.Identifier);
				else if(t instanceof Constant)
					index = SymbolToIndex.get(Grammar.Constant);
				else
					index = SymbolToIndex.get(t);
				
				//System.out.println(x.index() + " " + index + " " + ParsingTable[x.index()][index]);
				
				// R�cup�ration de la r�gle
				Rule r;
				if((r = ParsingTable[x.index()][index]) == null)
					throw new Exception("Pas de r�gle pour " + t.getText());
				
				// On ajoute sur la pile tous les symboles pr�sents dans
				// la r�gle � ex�cuter.
				// On les ajoute dans l'ordre inverse pour qu'on puisse les
				// retirer dans le bon ordre avec la pile.
				List<Symbol> right = r.right();
				int i = right.size()-1; 

				while(i>-1)
				{	
					// On ajoute des enfants � l'arbre contenant le symbole attendu.
					// Ces enfants contiennent les futurs symboles attendus de la pile.
					// A la fin de l'algorithme, on aura donc un arbre syntaxique du code source.
					Tree<Symbol> tmp = new Tree<Symbol>(right.get(i));
					tree.add(tmp);
					stack.push(tmp);
					i--;
				}
			}
		}
		
		// Si le dernier symbole du lexer est le symbole de fin du fichier. On
		// renvoit l'arbre syntaxique. Sinon on provoque une exception.
		if(t == SpecialChar.EOF)
		{	
			System.out.println("Analyse syntaxique termin�e.");
			return root;
		}
		else
			throw new Exception("Dernier symbole diff�rent de EOF.");	
	}
	
	/**
	 * V�rifie que la premi�re condition pour qu'une grammaire soit LL1 
	 * est correcte.
	 * 
	 * @pre Les ensembles P1 de chaque symbole de la grammaire G doivent �tre calcul�s.
	 * @throws Exception
	 */
	public void checkCondition1() throws Exception
	{
		/*
		 * Cette m�thode v�rifie que la grammaire G respecte la premi�re condition
		 * LL1 � savoir pour chaque non terminal X, il ne peut pas exister de 
		 * d�rivations diff�rentes o et o' de X telle que l'intersection de 
		 * p1(o) et p1(o') est non vide. En pratique cel� pose probl�me car
		 * il y aura une incoh�rence lors de la g�n�ration de la table de parsing.
		 * 
		 * Le principe est simple on cr�e une relation NotTerminal -> Set of Derivations
		 * Ensuite, pour chaque non terminal, on v�rifie qu'aucun
		 * ensemble p1 de ses d�rivations ne poss�de un meme terminal.
		 */
		
		// G�n�ration de la relation NotTerminal -> Ensemble de d�rivations
		if(AllLeftNotTerminals == null)
			GenerateAllLeftNotTerminal();

		// Soit x -> Y1 | Y2 | .. | Yn
		// Pour chaque d�rivation Yi, on v�rifie que l'intersection de p1(Yi) et p1(Yj),
		// o� j>i, est vide.
		for(Entry<NotTerminal, Set<Rule>> entry : AllLeftNotTerminals.entrySet()) 
		{
			NotTerminal x = entry.getKey();
			Set<Rule> Y = entry.getValue();
			
			// Pour chaque Yi
			Iterator<Rule> it3 = Y.iterator();
			int i = 1;
			while(it3.hasNext())
			{
				Rule r = it3.next();
				Set<Terminal> P1Yi = G.MergeSets(r.right());
				
				// Pour chaque Yj o� j>i
				Iterator<Rule> it4 = Y.iterator();
				for(int j=1;j<=i;j++)
					it4.next();
				
				while(it4.hasNext())
				{
					Rule r2 = it4.next();
					Set<Terminal> P1Yj = G.MergeSets(r2.right());
					
					// Intersection de p1(Bi) et p1(Bj) vide ?
					Iterator<Terminal> it5 = P1Yj.iterator();
					while(it5.hasNext())
					{	
						Terminal t = it5.next();
						if(P1Yi.contains(t))
							throw new Exception(	"Le non terminal '" + x + "' poss�de deux d�rivations ayant des �l�ments de p1 identiques: "
													+ " " + t);
					}
				}
				i++;
			}
		}
	}
	
	/**
	 * G�n�re la relation NotTerminal -> Ensemble de ses d�rivations
	 * dans la HashMap AllLeftNotTerminals.
	 * 
	 * @post AllLeftNotTerminals modifi�
	 *
	 */
	public void GenerateAllLeftNotTerminal()
	{
		AllLeftNotTerminals = new HashMap<NotTerminal, Set<Rule>>();
		// On parcourt toutes les r�gles pour r�cup�rer toutes les
		// d�rivations de chaque non terminal.
		Iterator<Rule> it = G.Rules().iterator();
		while (it.hasNext())
		{
			Rule r = it.next();
			// TerminalRules est l'ensemble des d�rivations du non terminal r.left
		    Set<Rule> TerminalRules;
		    
		    // Si le nonterminal r.left n'a pas encore de d�rivation, on 
		    // cr�e un ensemble dans lequel on stockera les dites d�rivations.
		    if ((TerminalRules = AllLeftNotTerminals.get(r.left())) == null)
				AllLeftNotTerminals.put(r.left(), (TerminalRules = new HashSet<Rule>()));
			
		    TerminalRules.add(r);
		}
	}
	
	/**
	 * V�rifie que la deuxi�me condition pour qu'une grammaire soit LL1 est correct.
	 * 
	 * @pre Les ensembles p1 de chaque symboles de la grammaire G doivent etre calcul�s
	 * @pre Les ensembles s1 de chaque symbole de la grammaire G non �tendue doivent etre calcul�s
	 * @throws Exception
	 */
	public void checkCondition2() throws Exception
	{
		/*
		 * Cette m�thode v�rifie que la deuxi�me condition pour qu'une grammaire soit 
		 * LL1 est remplie � savoir: 
		 * Pour chaque non terminal x, si p1(x) contient lambda alors l'intersection
		 * de p1(x) et s1(x) doit etre vide.
		 * 
		 * Le principe est simple, on r�cup�re chaque non terminal x de la grammaire G,
		 * on v�rifie si l'ensemble P1(x) contient lambda. Si oui, on v�rifie, pour chaque 
		 * symbole de p1(x) qu'il ne se trouve pas dans s1(x).
		 */
		NotTerminal x;
		
		// On parcourt tous les non terminal de la grammaire
		Iterator<NotTerminal> it = G.NotTerminals().iterator(); 
		while(it.hasNext())
		{	
			// On v�rifie que p1(x) ne contient pas lambda
			if((x = it.next()).P1().contains(Grammar.Lambda))
			{	
				Set<Terminal> P1x = x.P1();
				//System.out.println(x + ": " + P1x);
				Iterator<Terminal> it2 = P1x.iterator();
				Terminal t;
				// Lambda se trouve dans p1(x), on parcourt donc l'ensemble p1(x)
				// et on v�rifie qu'aucun symbole de p1(x) ne se trouve dans s1(x).
				while(it2.hasNext())
				{
					if(x.S1().contains((t = it2.next())))
						throw new Exception(	"L'intersection de l'ensemble s1(x) et p1(Yi) du non terminal: '" + x + "' est non vide: "
												+ " " + t);
				}
			}
		}
	}
	
	/**
	 * G�n�re la table de parsing de la grammaire G
	 * dans le tableau � deux dimensions ParsingTable
	 * 
	 * @pre Les ensembles p1 de chaque symboles de la grammaire G doivent etre calcul�s
	 * @pre Les ensembles s1 de chaque symbole de la grammaire G non �tendue doivent etre calcul�s
	 */
	public void GenerateParsingTable()
	{
		/*
		 * Comme fait au cours, on va cr�er un tableau en deux dimensions
		 * de taille NonTerminalList.size() et TerminalList.size().
		 * Le but de ce tableau est de savoir quel r�gle choisir en fonction
		 * du non terminal courant sur la stack et le symbol lu dans le code 
		 * source. 
		 * 
		 * Soit A -> alpha, pour chaque �l�ment "a" de p1(alpha) 
		 * on d�finit table[A, a] : A -> alpha.
		 * 
		 * Si lambda est contenu dans p1(alpha), on va aller voir AUSSI dans 
		 * l'ensemble s1(alpha) qui sont les terminaux pouvant se trouver apr�s A
		 * puisque p1(alpha) peut etre vide. On fera donc:
		 * Pour tout b dans s1(A): table[A, b] : A -> alpha
		 */
		
		Rule[][] table = new Rule[G.NotTerminals().size()][G.Terminals().size()];

		Iterator<Rule> it = G.Rules().iterator();
		// Parcours des r�gles
		while(it.hasNext())
		{
			Rule r = it.next();
			
			// On d�finit A -> alpha
			NotTerminal A = r.left();
			List<Symbol> alpha = r.right();
			
			// On r�cup�re l'ensemble p1(alpha)
			// On est certain d'avoir un r�sultat car tous les ensembles
			// p1 ont �t� calcul�s pr�c�dement.
			Iterator<Terminal> ItP1 = G.MergeSets(alpha).iterator();
			boolean isLambda = false;
			
			// Pour chaque "premier terminal" pouvant etre obtenu en d�rivant A
			// On cr�e table[A, a] : A -> aplha sauf si "a" == Lambda
			while(ItP1.hasNext())
			{
				Terminal a = ItP1.next();
				
				if(a == Grammar.Lambda)
					isLambda = true;
				else{
					table[A.index()][a.index()] = r;
					//System.out.println("table[" + A.getText() + ", " + a.getText() + "] = " + r);
				}
			}
			
			// Si on a d�couvert un lambda dans p1(alpha), on va aussi
			// ajouter les �l�ments de s1(A) � la liste.
			if(isLambda)
			{
				Iterator<Terminal> ItS1 = A.S1().iterator();
				// Pour tous les terminaux "b" suivant A on fait:
				// table[A, b] : A -> alpha
				while(ItS1.hasNext())
				{
					Terminal b = ItS1.next();
					table[A.index()][b.index()] = r;
					//System.out.println("table[" + A.getText() + ", " + b.getText() + "] = " + r);
				}
			}
		}
		
		ParsingTable = table;
	}
	
	public static void main(String[] args)
	{
		try {
			Parser P = new Parser(args[0]);
			P.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
