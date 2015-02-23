package slip.lexer;

import fichiers.text;


public class Lexer
{

/*  

Un symbole de base est définit par la grammaire BNF ci-dessous.

<symbol> ::=   <special character>
             | <reserved word>
             | <identifier>
             | <integral literal>

<special character> ::= , | . | ( | ) | "|" | & | { | } | / | > | < | = | !

<reserved word> ::= if | else | read | write | while | this | null | new
                  | return | super
                
(En pratique, on enlève les mots réservés de l'ensemble des identificateurs,
pour faciliter l'élimination des ambiguïtés syntaxiques.)

On peut définir comme suit le rôle de l'analyseur lexical.

En présence d'un fichier de texte ne contenant... (plus tard).

*/

private boolean letter(char c)
{
  switch (c)
   {
     case 'a' : return true;
     case 'b' : return true;
     case 'c' : return true;
     case 'd' : return true;
     case 'e' : return true;
     case 'f' : return true;
     case 'g' : return true;
     case 'h' : return true;
     case 'i' : return true;
     case 'j' : return true;
     case 'k' : return true;
     case 'l' : return true;
     case 'm' : return true;
     case 'n' : return true;
     case 'o' : return true;
     case 'p' : return true;
     case 'q' : return true;
     case 'r' : return true;
     case 's' : return true;
     case 't' : return true;
     case 'u' : return true;
     case 'v' : return true;
     case 'w' : return true;
     case 'x' : return true;
     case 'y' : return true;
     case 'z' : return true;
     case 'A' : return true;
     case 'B' : return true;
     case 'C' : return true;
     case 'D' : return true;
     case 'E' : return true;
     case 'F' : return true;
     case 'G' : return true;
     case 'H' : return true;
     case 'I' : return true;
     case 'J' : return true;
     case 'K' : return true;
     case 'L' : return true;
     case 'M' : return true;
     case 'N' : return true;
     case 'O' : return true;
     case 'P' : return true;
     case 'Q' : return true;
     case 'R' : return true;
     case 'S' : return true;
     case 'T' : return true;
     case 'U' : return true;
     case 'V' : return true;
     case 'W' : return true;
     case 'X' : return true;
     case 'Y' : return true;
     case 'Z' : return true;
     default : return false;
   }
}

private boolean specialChar(char c)
{
  switch (c)
   {
     case ',' : return true;
     case '.' : return true;
     case '(' : return true;
     case ')' : return true;
     case '{' : return true;
     case '}' : return true;
     case '<' : return true;
     case '>' : return true;
     case '=' : return true;
     case '/' : return true;
     case '!' : return true;
     case '|' : return true;
     case '&' : return true;
     case ';' : return true;
     case '*' : return true;
     case '+' : return true;
     case '%' : return true;
     case '-' : return true;
     
     default : return false;
   }
}

private boolean digit(char c)
{
  switch (c)
   {
     case '0' : return true;
     case '1' : return true;
     case '2' : return true;
     case '3' : return true;
     case '4' : return true;
     case '5' : return true;
     case '6' : return true;
     case '7' : return true;
     case '8' : return true;
     case '9' : return true;
     default : return false;
   }
}

private boolean space(char c)
{ return (c == ' ')|(c == text.TAB); }

private text source;
private boolean eos;
private char[] cline = new char[0]; // ligne courante
private int l = -1; // numéro de la ligne courante (on commence à 0.)
                    // (nbre de lignes du fichier si eos)
private int i;      // position dans la ligne courante (on commence à 0.)
private int oldi = -1 ;
private int oldl = -2 ;

public int getLineNumber(){ return oldl + 1 ;}
public int getIndexNumber(){ return oldi + 1;}

private void findFirstChar()
{
  /* Invariant : 
     ---------
    source est ouvert en lecture &&
    tous les caractères examinés jusqu'ici sont des espaces ou des eln 
  */

  while ((i != cline.length && space(cline[i]))|
         (i == cline.length & !source.eof()))
    if (i!= cline.length) i++;
    else { cline = (source.readlnString()).toCharArray();
           i = 0; l++;  
         }

  eos = (i==cline.length)&(source.eof()); 
  if (eos) source.close();
}

public Lexer(text slipFile) throws LexerException
{
  source = slipFile ;
  source.reset();
  int rc = source.ioError();
  if (rc != 0) throw new LexerException("Cannot open source file"
                  + " : Error no " + rc, -1, 0);
  findFirstChar();
}

public Lexer(String fileName) throws LexerException {this(new text(fileName));}

public boolean eos(){ return eos; }

private int parseIntegralLiteral()
{
  int r = cline[i] - '0'; i++;
  while ((i!=cline.length)&&(digit(cline[i])))
  { r = r * 10 + cline[i] - '0'; i++; }

  return r;
}

private String parseIdentifier()
{
  int i0 = i;  i++;
  while ((i!=cline.length)&&(digit(cline[i])|letter(cline[i])))
  {  i++; }

  char[] r = new char[i-i0]; int j = 0;
  while (i0!=i) { r[j] = cline[i0]; i0++; j++; }

  return String.valueOf(r);
}

public Terminal nextSymbol() throws LexerException
{
  if (eos) return null;

  oldl = l; oldi = i;

  if (specialChar(cline[i])) 
    { char c = cline[i];
      i++; 
      findFirstChar(); 
      return SpecialCharacter.terminal(c); }
  else if (digit(cline[i]))
    {
      int val = parseIntegralLiteral();
      findFirstChar();
      return IntegralLiteral.terminal(val, oldl, oldi);
    }
  else if (letter(cline[i]))
    { String s = parseIdentifier();
      findFirstChar();
      Terminal t = ReservedWord.terminal(s);
      if (t == null) return Identifier.terminal(s, oldl, oldi);
      else return t ;
    }    
  else throw new LexerException("Invalid character '" + cline[i] + "'", l + 1, i + 1);
}

public static void main(String[] arg) throws Exception
{
  Lexer toto = new Lexer(arg[0]);

  while (!toto.eos())
  {
    Terminal t = toto.nextSymbol();
    System.out.println( t + " " + t.line + " " + t.sort + " " + toto.eos());
  }

}


}

