class ConcreteSyntax
{

/* D�but : 10:21 ; Break 11:40

<program> ::=   <method> 
              | <program> <method>

<method>  ::= <name> ( <parameter list> ) <body>

<name>    ::=   <method name> 
              | <method name> / <integral literal>

<method name> ::= <identifier>

<parameter list> ::= 
                <empty> 
              | <non empty parameter list>
 
<non empty parameter list> ::= 
                <formal parameter> 
              | <non empty parameter list> , <formal parameter>

<body>    ::= <command sequence>

<formal parameter> ::= <identifier>


<command> ::=   <assignment> 
              | <conditional command> 
              | <while command>
              | <command sequence> 
              | <input command> 
              | <output command>
              | <return>

<assignment> ::= <left expression> = <right expression> ;

<conditional command> ::= 
                if <condition> <command> 
              | if <condition> <command> else <command>

<condition> ::= ( <disjunction> )

<disjunction> ::= 
                <conjunction> 
              | <disjunction> "|" <conjunction>

<conjunction> ::=
                <basic condition>
              | <conjunction> & <basic condition>

<basic condition> ::=
                <relation>
              | ! <relation>

<relation> ::= <right expression> <comparison operator> <right expression>

<comparison operator> ::= = = | ! = | < | > | < = | > =

<while command> ::= while <condition> <command>

<command sequence> ::= { <command list> }

<command list> ::= 
                <empty> 
              | <command list> <command>

<input command>  ::= read  ( <left expression list> ) ;

<output command> ::= write ( <right expression list> ) ;

<return>  ::= return ( <left expression> ) ;

<left expression list> ::= 
                <left expression> 
              | <left expression list> , <left expression> 

<right expression list> ::=    
                <right expression> 
              | <right expression list> , <right expression> 

<left expression> ::= 
                <variable> 
              | <variable> . <integral literal>

<right expression> ::= <expression>

<expression> ::= 
                <term> 
              | - <term> 
              | <expression> <additive operator> <term>

<additive operator> ::= + | -

<term>   ::= 
                <factor> 
              | <term> <multiplicative operator> <factor>

<multiplicative operator> ::= * | / | %

<factor> ::=
                <left expression> 
              | <integral literal> 
              | this 
              | null 
              | new / <integral literal>
              | <method call>
              | ( <expression> )

<variable> ::= <identifier>

<method call> ::= <target> <method name> ( <actual parameter list> )

<target> ::=
                <empty>
              | <variable> .
              | super .

 <actual parameter list> ::=
                <empty>
              | <left expression list>



<identifier> ::= <letter> | <identifier> <letter> | <identifier> <digit>
<letter>  ::= a | b | ... | z | A | B | ... | Z
<digit>   ::= 0 | 1 | ... | 9
<empty>   ::= "the empty sequence of ascii characters"

*/

/* Restart : 12:59 ; Stop : 13:13

D�finition : "programme source SLIP"

La syntaxe concr�te, ci-dessus, d�finit un programme SLIP comme une suite de caract�res
ASCII *imprimables* � l'exclusion de l'espace et de tout caract�re de
contr�le. Par d�finition, on obtient un programme source SLIP, � partir d'un
programme SLIP en syntaxe concr�te "stricto sensu", en ajoutant entre deux caract�res
quelconques une s�quence finie arbitraire d'espaces, de caract�res de fin de ligne, et 
de caract�res de tabulation, avec la restriction qu'on ne peut ins�rer de telles
s�quences (non vides) dans un "identifier", ni dans un "integral literal", ni dans 
un "mot r�serv�" tel que "if", "while", "new", "this", etc. Il s'en suit que, dans
un programme source SLIP (correct), on ne peut trouver de s�quence de caract�res
successifs form�e d'un premier caract�re �gal � un chiffre ou � une lettre, suivi 
d'une suite non vide de caract�res espace, de fin de ligne ou de tabulation, et 
termin�e par une lettre ou un chiffre.

*/

}

