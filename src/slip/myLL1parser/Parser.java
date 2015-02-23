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
	// Tableau à deux dimensions permettant de choisir une règle à executer
	// en fonction du non terminal au dessus la pile et du terminal lu.
	private Rule[][] ParsingTable;
	// Grammaire qui est utilisée pour accepter ou non le code source.
	private Grammar G;
	// Chemin du fichier contenant la spécification BNF de la grammaire.
	private String GrammarPath = "SyntaxeConcreteLL1BIS.txt";
	// Chemin du fichier contenant le code source à parser.
	private String InputPath;
	// Analyseur lexical qui fournit au fur et à mesure chaque symbole du code source.
	private Lexer Lex;
	// Relation entre les objets Terminal du lexer et les objets Terminal de la grammaire
	// pour permettre l'utilisation de la table de parsing
	private HashMap<Terminal, Integer> SymbolToIndex = new HashMap<Terminal, Integer>();
	// Relation entre un non terminal et toutes les règles de dérivation de ce dernier.
	private HashMap<NotTerminal, Set<Rule>> AllLeftNotTerminals;
	
	/**
	 * Constructeur de la classe.
	 * @param s: Chemin du fichier source à parser.
	 */
	public Parser(String s)
	{
		InputPath = s;
	}
	
	/**
	 * Crée la relation "Objet Terminal du Lexer" -> "Index dans la parsing table"
	 * dans la HashMap SymbolToIndex
	 * 
	 * @post SymbolToIndex modifié
	 *
	 */
	public void GenerateSymbolToIndex()
	{
		/*
		 * Il n'existe aucun lien entre les objets Terminal du lexer et les objets
		 * Terminal de la grammaire. Cette relation est importante pour pouvoir utiliser
		 * la table de parsing qui a été créée à partir des terminaux de la grammaire.
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
	 * l'analyse lexicale ou pendant l'analyse syntaxique, l'analyse est stoppée.
	 * 
	 * @return "Analyse lexicale terminée" s'affiche à l'écran si le code source
	 * est syntaxiquement correct à la grammaire ou une exception est déclenchée
	 * s'il y a une erreur.
	 * 
	 * @post G initialisé, Lex initialisé, SymbolToIndex intialisé, ParseTable
	 * initialisé
	 * 
	 * @throws Exception
	 */
	public Tree<Symbol> parse() throws Exception
	{
		/*
		 * Cette méthode vérifie que le code source est syntaxiquement
		 * correct par rapport à la grammaire.
		 * 
		 * Etape 1: Chargement de l'analyseur lexical
		 * Etape 2: Chargement de la grammaire.
		 * Etape 3: Création de la grammaire étendue.
		 * Etape 4: Calcul des ensembles P1 et S1.
		 * Etape 5: Vérification que la grammaire est bien LL1.
		 * Etape 6: Génération des relations objets Terminal du Lexer -> Index dans la parsing table
		 * 			(Les index sont calculés à la création d'un Terminal)
		 * Etape 7: Génération de la table de parsing
		 * Etape 8: Analyse syntaxique (+ génération de l'arbre syntaxique)	 
		 * */

		// Analyse Lexicale.
		// Le fichier contenant le code source sera traduit en 
		// une série d'objets du package Slip.mylexer.* représentant des "Terminal" 
		// dont le dernier est EOF signifiant qu'on a lu tous les symboles. 
		// On utilisera plus bas la méthode nextTerminal() qui renvoit 
		// le prochain symbole à lire.
		// Si un symbole n'est pas reconnu par l'analyseur lexical, une 
		// exception se déclenche (par exemple ').
		Lex = new Lexer(InputPath);
		
		// Chargement de la grammaire
		GrammarLoader GL = new GrammarLoader(GrammarPath);
		// Le fichier contenant la grammaire BNF du langage est
		// traduit en une série de symboles de type Slip.myLL1parser.Terminal
		// et Slip.myLL1parser.NotTerminal.
		// Si la syntaxe BNF n'est pas respectée ou si un Non Terminal ne 
		// possède pas au moins une dérivation, une exception se déclenche.
		// NB: Pour charger une grammaire BNF plus facilement, celle ci
		// doit respecter certaines normes spécifiées dans la classe GrammarLoader.
		G = GL.load();
		// Augmentation de la grammaire selon ce schéma:
		// Pour toute règle:
		// Y  -> alpha X beta
		// on crée
		// X2 -> beta Y2
		// où alpha, beta sont des terminaux.
		G.ComputeExtendedRules();
		// Calcul des ensembles P1 de chaque symbole en utilisant
		// l'algorithme du point fixe.
		G.ComputeP1();
		// Calcul des ensembles S1 de chaque symbole "de base".
		// Tous les ensembles P1 des symboles "prime" deviennent les 
		// ensembles S1 de leur correspondant "de base".
		G.ComputeS1();
		
		// Vérification que la grammaire est bien LL1.
		// Condition 1: Pour chaque non terminal X, il ne peut pas exister de 
		// dérivations différentes o et o' de X telle que l'intersection de 
		// p1(o) et p1(o') est non vide. 
		checkCondition1();
		// Condition 2: Pour chaque non terminal X, si p1(X) contient lambda alors l'intersection
		// de p1(X) et s1(X) doit etre vide.
		checkCondition2();

		// On crée une relation entre un objet Terminal renvoyé par le Lexer 
		// et son index dans la table de parsing. En effet, la table de parsing
		// est générée à partir d'objet Terminal de GrammarLoader.
		GenerateSymbolToIndex();
		
		// Génération de la table de parsing.
		// Pour chaque règle "de base":
		// Soit A -> alpha, pour chaque élément "a" de p1(alpha) 
		// on définit table[A, a] : A -> alpha.
		//
		// Si lambda est contenu dans p1(alpha), on va aller voir AUSSI dans 
		// l'ensemble s1(alpha) qui sont les terminaux pouvant se trouver après A
		// puisque p1(alpha) peut etre vide. On fera donc:
		// Pour tout b dans s1(A): table[A, b] : A -> alpha
		GenerateParsingTable();
		
		// Pile qui sert à savoir ce qui est attendu comme symbole provenant du lexer
		Stack<Tree<Symbol>> stack = new Stack<Tree<Symbol>>();
		// Premier terminal du code source
		Terminal t = Lex.nextTerminal();
		// Ajout d'un élément à la pile, le symbole <PROGRAM>
		// On encapsule l'élément dans un arbre qui servira à la traduction
		// en langage interne. Pour l'algorithme, il ne sert à rien.
		Tree<Symbol> root = new Tree<Symbol>(Grammar.Program);
		stack.push(root);
		
		System.out.println("Analyse syntaxique...");
		// Début de l'algorithme
		while(!stack.isEmpty())
		{
			// Récupération du symbole au sommet de la pile
			Tree<Symbol> tree = stack.pop();
			Symbol x = tree.element();
			
			//System.out.println("Symbole attendu: " + x);
			//System.out.println("Symbole en cours: " + t);
			//System.out.println("Symboles restants sur la pile: " + stack);
			//System.out.println();
			
			// Si le symbole attendu est non terminal, on va vérifier 
			// que le symbole en cours est identique à ce symbole
			// ou qu'il est égal à lambda (auquels cas on passera au symbole
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
			// Si le symbole attendu est non terminal, on va récupérer la règle
			// à appliquer en fonction de celui-ci et du symbole en cours.
			else if(x instanceof NotTerminal)
			{ 		
				int index;
				// On récupère l'index du symbole en cours dans la parsing table
				if(t instanceof Identifier)
					index = SymbolToIndex.get(Grammar.Identifier);
				else if(t instanceof Constant)
					index = SymbolToIndex.get(Grammar.Constant);
				else
					index = SymbolToIndex.get(t);
				
				//System.out.println(x.index() + " " + index + " " + ParsingTable[x.index()][index]);
				
				// Récupération de la règle
				Rule r;
				if((r = ParsingTable[x.index()][index]) == null)
					throw new Exception("Pas de règle pour " + t.getText());
				
				// On ajoute sur la pile tous les symboles présents dans
				// la règle à exécuter.
				// On les ajoute dans l'ordre inverse pour qu'on puisse les
				// retirer dans le bon ordre avec la pile.
				List<Symbol> right = r.right();
				int i = right.size()-1; 

				while(i>-1)
				{	
					// On ajoute des enfants à l'arbre contenant le symbole attendu.
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
			System.out.println("Analyse syntaxique terminée.");
			return root;
		}
		else
			throw new Exception("Dernier symbole différent de EOF.");	
	}
	
	/**
	 * Vérifie que la première condition pour qu'une grammaire soit LL1 
	 * est correcte.
	 * 
	 * @pre Les ensembles P1 de chaque symbole de la grammaire G doivent être calculés.
	 * @throws Exception
	 */
	public void checkCondition1() throws Exception
	{
		/*
		 * Cette méthode vérifie que la grammaire G respecte la première condition
		 * LL1 à savoir pour chaque non terminal X, il ne peut pas exister de 
		 * dérivations différentes o et o' de X telle que l'intersection de 
		 * p1(o) et p1(o') est non vide. En pratique celà pose problème car
		 * il y aura une incohérence lors de la génération de la table de parsing.
		 * 
		 * Le principe est simple on crée une relation NotTerminal -> Set of Derivations
		 * Ensuite, pour chaque non terminal, on vérifie qu'aucun
		 * ensemble p1 de ses dérivations ne possède un meme terminal.
		 */
		
		// Génération de la relation NotTerminal -> Ensemble de dérivations
		if(AllLeftNotTerminals == null)
			GenerateAllLeftNotTerminal();

		// Soit x -> Y1 | Y2 | .. | Yn
		// Pour chaque dérivation Yi, on vérifie que l'intersection de p1(Yi) et p1(Yj),
		// où j>i, est vide.
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
				
				// Pour chaque Yj où j>i
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
							throw new Exception(	"Le non terminal '" + x + "' possède deux dérivations ayant des éléments de p1 identiques: "
													+ " " + t);
					}
				}
				i++;
			}
		}
	}
	
	/**
	 * Génère la relation NotTerminal -> Ensemble de ses dérivations
	 * dans la HashMap AllLeftNotTerminals.
	 * 
	 * @post AllLeftNotTerminals modifié
	 *
	 */
	public void GenerateAllLeftNotTerminal()
	{
		AllLeftNotTerminals = new HashMap<NotTerminal, Set<Rule>>();
		// On parcourt toutes les règles pour récupérer toutes les
		// dérivations de chaque non terminal.
		Iterator<Rule> it = G.Rules().iterator();
		while (it.hasNext())
		{
			Rule r = it.next();
			// TerminalRules est l'ensemble des dérivations du non terminal r.left
		    Set<Rule> TerminalRules;
		    
		    // Si le nonterminal r.left n'a pas encore de dérivation, on 
		    // crée un ensemble dans lequel on stockera les dites dérivations.
		    if ((TerminalRules = AllLeftNotTerminals.get(r.left())) == null)
				AllLeftNotTerminals.put(r.left(), (TerminalRules = new HashSet<Rule>()));
			
		    TerminalRules.add(r);
		}
	}
	
	/**
	 * Vérifie que la deuxième condition pour qu'une grammaire soit LL1 est correct.
	 * 
	 * @pre Les ensembles p1 de chaque symboles de la grammaire G doivent etre calculés
	 * @pre Les ensembles s1 de chaque symbole de la grammaire G non étendue doivent etre calculés
	 * @throws Exception
	 */
	public void checkCondition2() throws Exception
	{
		/*
		 * Cette méthode vérifie que la deuxième condition pour qu'une grammaire soit 
		 * LL1 est remplie à savoir: 
		 * Pour chaque non terminal x, si p1(x) contient lambda alors l'intersection
		 * de p1(x) et s1(x) doit etre vide.
		 * 
		 * Le principe est simple, on récupère chaque non terminal x de la grammaire G,
		 * on vérifie si l'ensemble P1(x) contient lambda. Si oui, on vérifie, pour chaque 
		 * symbole de p1(x) qu'il ne se trouve pas dans s1(x).
		 */
		NotTerminal x;
		
		// On parcourt tous les non terminal de la grammaire
		Iterator<NotTerminal> it = G.NotTerminals().iterator(); 
		while(it.hasNext())
		{	
			// On vérifie que p1(x) ne contient pas lambda
			if((x = it.next()).P1().contains(Grammar.Lambda))
			{	
				Set<Terminal> P1x = x.P1();
				//System.out.println(x + ": " + P1x);
				Iterator<Terminal> it2 = P1x.iterator();
				Terminal t;
				// Lambda se trouve dans p1(x), on parcourt donc l'ensemble p1(x)
				// et on vérifie qu'aucun symbole de p1(x) ne se trouve dans s1(x).
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
	 * Génère la table de parsing de la grammaire G
	 * dans le tableau à deux dimensions ParsingTable
	 * 
	 * @pre Les ensembles p1 de chaque symboles de la grammaire G doivent etre calculés
	 * @pre Les ensembles s1 de chaque symbole de la grammaire G non étendue doivent etre calculés
	 */
	public void GenerateParsingTable()
	{
		/*
		 * Comme fait au cours, on va créer un tableau en deux dimensions
		 * de taille NonTerminalList.size() et TerminalList.size().
		 * Le but de ce tableau est de savoir quel règle choisir en fonction
		 * du non terminal courant sur la stack et le symbol lu dans le code 
		 * source. 
		 * 
		 * Soit A -> alpha, pour chaque élément "a" de p1(alpha) 
		 * on définit table[A, a] : A -> alpha.
		 * 
		 * Si lambda est contenu dans p1(alpha), on va aller voir AUSSI dans 
		 * l'ensemble s1(alpha) qui sont les terminaux pouvant se trouver après A
		 * puisque p1(alpha) peut etre vide. On fera donc:
		 * Pour tout b dans s1(A): table[A, b] : A -> alpha
		 */
		
		Rule[][] table = new Rule[G.NotTerminals().size()][G.Terminals().size()];

		Iterator<Rule> it = G.Rules().iterator();
		// Parcours des règles
		while(it.hasNext())
		{
			Rule r = it.next();
			
			// On définit A -> alpha
			NotTerminal A = r.left();
			List<Symbol> alpha = r.right();
			
			// On récupère l'ensemble p1(alpha)
			// On est certain d'avoir un résultat car tous les ensembles
			// p1 ont été calculés précédement.
			Iterator<Terminal> ItP1 = G.MergeSets(alpha).iterator();
			boolean isLambda = false;
			
			// Pour chaque "premier terminal" pouvant etre obtenu en dérivant A
			// On crée table[A, a] : A -> aplha sauf si "a" == Lambda
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
			
			// Si on a découvert un lambda dans p1(alpha), on va aussi
			// ajouter les éléments de s1(A) à la liste.
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
