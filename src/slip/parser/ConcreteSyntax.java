class ConcreteSyntax
{

/* Début : 10:21 ; Break 11:40

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

Définition : "programme source SLIP"

La syntaxe concrète, ci-dessus, définit un programme SLIP comme une suite de caractères
ASCII *imprimables* à l'exclusion de l'espace et de tout caractère de
contrôle. Par définition, on obtient un programme source SLIP, à partir d'un
programme SLIP en syntaxe concrète "stricto sensu", en ajoutant entre deux caractères
quelconques une séquence finie arbitraire d'espaces, de caractères de fin de ligne, et 
de caractères de tabulation, avec la restriction qu'on ne peut insérer de telles
séquences (non vides) dans un "identifier", ni dans un "integral literal", ni dans 
un "mot réservé" tel que "if", "while", "new", "this", etc. Il s'en suit que, dans
un programme source SLIP (correct), on ne peut trouver de séquence de caractères
successifs formée d'un premier caractère égal à un chiffre ou à une lettre, suivi 
d'une suite non vide de caractères espace, de fin de ligne ou de tabulation, et 
terminée par une lettre ou un chiffre.

*/

}

