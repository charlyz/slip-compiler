This is a school project. Goal was to create a syntax and translate it into readable machine code. More details [here](http://iamlookingforaninternship.com/ressources/lt.pdf) (in french).

# Syntaxe 

```javascript

stmt ::= l cmd l | l if cond then l else l  end
cmd ::= ass | call | in | out
cond ::= sexr cop sexpr
ass ::= des = expr | x = new/i

call ::= x = {m x,..,x} | x = {x.m x,..,x} | x = {super.m x,..,x}

in ::= {read x}

out ::= {write x}
cop ::= = | <
des ::= x | x.i | this.i
aop ::= + | - | * | / | %

decl ::= {meth name x,..,x} l {stmt} l end x
name ::= m | m.i

prog ::= {decl}

expr ::= sexpr | cexpr
sexpr ::= i | this | null | des
cexpr ::= sexpr aop sexpr
```

# Factorial 

```javascript
main () {
  x = 5; 
  y = fact(x) ;
  write (y) ;
}

fact (x) 
{
  if (x==0) return (1) ;
  else return (x * fact(x-1)) ;
}
```

# Result

```asm
00000   LDA  11, 00000
00004   LDA  12, 00318
00008   LDA  13, 65520
00012   JUMP 10, 00026
00016   LDM  14, 65532
00020   LDM  15, 65528
00024   HALT  0, 0
// method main()
00026   LDA   0, 00024 (12)
00030   COMP  0, 13
00032   JLE   0, 00046
00036   LDM  14, 65532
00040   LDM  15, 65528
00044   HALT  0, 0
00046   STM  11, 00016 (12)
00050   STM  10, 00020 (12)
00054   LDA  11, 00000 (12)
00058   ADDA 12, 00024
// [ lab5 : x#1 := 5 ; go to lab4]
00062   LDA   4, 00004 (11)
00066   LDA   0, 00005
00070   STM   0, 00000 (4)
// [ lab4 : #3 := x#1 ; go to lab3]
00074   LDA   4, 00012 (11)
00078   LDM   0, 00004 (11)
00082   STM   0, 00000 (4)
// [ lab3 : #2 := fact(#3) ; go to lab2]
00086   LDA   0, 00004 (12)
00090   COMP  0, 13
00092   JLE   0, 00106
00096   LDM  14, 65532
00100   LDM  15, 65528
00104   HALT  0, 0
00106   LDM   0, 00012 (11)
00110   STM   0, 00004 (12)
00114   JUMP 10, 00156
00118   STM   0, 00008 (11)
// [ lab2 : write(#2) ; go to lab1]
00122   LDM  15, 00008 (11)
65524   LIT  C'OUT:'
00126   LDM  14, 65524
00130   HALT  0, 0
00132   JUMP  0, 00136
00136   LDM   0, 00000 (11)
00140   LDA  12, 00000 (11)
00144   LDM  11, 00016 (12)
00148   LDM  10, 00020 (12)
00152   JUMP  0, 00000 (10)
// end of method main()
// method fact()
00156   LDA   0, 00024 (12)
00160   COMP  0, 13
00162   JLE   0, 00176
00166   LDM  14, 65532
00170   LDM  15, 65528
00174   HALT  0, 0
00176   STM  11, 00016 (12)
00180   STM  10, 00020 (12)
00184   LDA  11, 00000 (12)
00188   ADDA 12, 00024
// [ lab11 : if x#1 == 0 then go to lab10 else go to lab9]
00192   LDM   0, 00004 (11)
00196   LDA   1, 00000
00200   COMP  0, 1
00202   JNE   0, 00222
// [ lab10 : result#0 := 1 ; go to lab6]
00206   LDA   4, 00000 (11)
00210   LDA   0, 00001
00214   STM   0, 00000 (4)
00218   JUMP  0, 00298
// [ lab9 : #3 := x#1 - 1 ; go to lab8]
00222   LDA   4, 00012 (11)
00226   LDM   0, 00004 (11)
00230   LDA   1, 00001
00234   SUB   0, 1
00236   STM   0, 00000 (4)
// [ lab8 : #3 := fact(#3) ; go to lab7]
00240   LDA   0, 00004 (12)
00244   COMP  0, 13
00246   JLE   0, 00260
00250   LDM  14, 65532
00254   LDM  15, 65528
00258   HALT  0, 0
00260   LDM   0, 00012 (11)
00264   STM   0, 00004 (12)
00268   JUMP 10, 00156
00272   STM   0, 00012 (11)
// [ lab7 : result#0 := x#1 * #3 ; go to lab6]
00276   LDA   4, 00000 (11)
00280   LDM   0, 00004 (11)
00284   LDM   1, 00012 (11)
00288   MUL   0, 1
00290   STM   0, 00000 (4)
00294   JUMP  0, 00298
00298   LDM   0, 00000 (11)
00302   LDA  12, 00000 (11)
00306   LDM  11, 00016 (12)
00310   LDM  10, 00020 (12)
00314   JUMP  0, 00000 (10)
// end of method fact()
65532   LIT  C'ERR:'
65528   LIT  C'SOF'
```
