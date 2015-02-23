package slip.mylexer;

import java.io.FileInputStream;
import java.io.IOException;

import slip.myLL1parser.Terminal;

public class Lexer 
{
	/*
	 * Cette classe remplit le r�le d'analyseur lexical. L'analyse utilise
	 * le principe de "la machine � �tats" pour valider ou un non un code
	 * source. Ci-dessous, les diff�rents �tats.
	 */
	// Le Lexer est en attente d'un caract�re, n'importe lequel, pour l'analyser.
	private final static int STATE_BEGIN = 0;
	// Le Lexer est en attente d'un chiffre compris entre 0 et 9.
	private final static int STATE_CATCH_DIGIT = 1;
	// Le Lexer est en attente d'un caract�re.
	private final static int STATE_CATCH_IDENTIFIER = 2;
	// Le Lexer est en attente de n'importe quel caract�re contenu dans un commentaire.
	private final static int STATE_CATCH_COMMENT = 3;
	// Pointer est un objet qui lit un fichier texte, caract�re par caract�re
	private Pointer pointer;
	
	/**
	 * Constructeur de la classe.
	 * 
	 * @param f: Chemin du fichier source
	 * @throws IOException
	 */
	public Lexer(String f) throws IOException
	{
		pointer = new Pointer(new FileInputStream(f));
	}
	
	/**
	 * Cette m�thode renvoie le prochain symbole lexical du code
	 * source contenu dans le fichier manipul� par pointer.
	 * 
	 * L'algorithme de cette m�thode utilise le principe de 
	 * "machine � �tats". La repr�sentation graphique des �tats
	 * peut etre consult�e dans le rapport.
	 * 
	 * @pre pointeur est diff�rent de null
	 * 
	 * @post La position du pointeur de pointer a avanc� d'autant
	 * de caract�res que la taille du symbole lexical renvoy� ou
	 * il ne s'est rien pass� si la position du pointeur de pointer
	 * est � la fin du fichier.
	 * 
	 * @return un objet Terminal correspondant au prochain Terminal
	 * lu dans le fichier manipul� par pointer ou le Terminal EOF 
	 * si on a parcouru tout le fichier manipul� par pointer.
	 * 
	 * @throws Exception si un caract�re est inconnu.
	 */
	public Terminal nextTerminal() throws Exception
	{
		int state = STATE_BEGIN;
		StringBuffer res = new StringBuffer();
		char c;
		
		try
		{
			while(pointer.hasNext())
			{
				switch(state)
				{
					case STATE_BEGIN:
						
						removeSpaces();
						Terminal t;
						c = pointer.remove();
						res.append(c);

						switch(c)
						{
							// Symbole < ou <=
							case '<':
								if(pointer.getChar() == '=')
								{
									pointer.remove();
									t = SpecialChar.SCPPE;
								}else
									t = SpecialChar.SCSPP;
								
								return t;

							// Symbole > ou >=
							case '>':
								if(pointer.getChar() == '=')
								{
									pointer.remove();
									t = SpecialChar.SCPGE;
								}else
									t = SpecialChar.SCSPG;
								
								return t;
							
							// Symbole != ou !(
							case '!':
								if(pointer.getChar() == '=')
								{
									pointer.remove();
									t = SpecialChar.SCDIF;
								}else
									t = SpecialChar.SCNEG;
								
								return t;
							
							// Symbole = ou ==
							case '=':
								if(pointer.getChar() == '=')
								{
									pointer.remove();
									t = SpecialChar.SCEGA;
								}else
									t = SpecialChar.SCEQUAL;
								
								return t;
							
							// Symbole // ou /
							case '/':
								if(pointer.getChar() == '/')
								{
									pointer.remove();
									res = new StringBuffer();
									state = STATE_CATCH_COMMENT;
								}else
								{
									t = SpecialChar.SCDIV;
									return t;
								}
							break;
							
							// Symbole - ou -> ou -x
							case '-':
								if(pointer.getChar() == '>')
								{
									pointer.remove();
									return SpecialChar.SCARROW;
								}else if(Character.isDigit(pointer.getChar()))
								{
									state = STATE_CATCH_DIGIT;
									break;
								}
								else
									return SpecialChar.SCMINUS;
								
							// Autres caract�res sp�ciaux
							case ',': case '.': case '(': case ')': case '{': case '}':
							case ';': case '+': case '*': case '%':
								t = SpecialChar.terminal(c);	
								return t;
							
							// Identifier ou Constant
							default:
								if(Character.isDigit(c)) state = STATE_CATCH_DIGIT;
								else if(Character.isLetter(c)) state = STATE_CATCH_IDENTIFIER;
								else 
									{	
										// Apparement le dernier caract�re (qui n'a pas de rapport avec le code) 
										// de mes fichiers provoque une erreur. J'ai utilis� le 
										// syst�me D apr�s avoir passer une heure sur le probl�me ..
										if(pointer.hasNext())
											throw new Exception("Caract�re inconnu: " + c);
									}
							break;
	
						}	
					break;
					
					case STATE_CATCH_COMMENT:
						if(pointer.getChar() == '\n')
						{
							state = STATE_BEGIN;
						}else
							pointer.remove();
					break;
					
					case STATE_CATCH_DIGIT:
						if(Character.isDigit(pointer.getChar()))
							res.append(pointer.remove());
						else
						{
							state = STATE_BEGIN;
							return new Constant(res.toString());
						}
					break;
					
					case STATE_CATCH_IDENTIFIER:
						if(Character.isLetterOrDigit(pointer.getChar()))
							res.append(pointer.remove());
						else
						{	
							state = STATE_BEGIN;
							Terminal rw = ReservedWord.terminal(res.toString());
							if(rw != null)
							{
								return rw;
							}else
								return new Identifier(res.toString());
						}
					break;
				}
			}
		}catch(IOException e)
		{
			System.out.println("Exception lors de l'analyse lexciale du fichier source: " + e.getMessage());
			return SpecialChar.EOF;
		}
		
		pointer.close();
			
		return SpecialChar.EOF;
	}

	public void removeSpaces() throws IOException 
	{	
		while(Character.isWhitespace(pointer.getChar()))
			pointer.remove();
	}
	
	public static void main(String[] arg) throws Exception
	{
		Lexer toto = new Lexer(arg[0]);
		Terminal t;
		  while ((t = toto.nextTerminal()) != SpecialChar.EOF)
		  {
		    System.out.println( t );
		  }
	}
	
}
