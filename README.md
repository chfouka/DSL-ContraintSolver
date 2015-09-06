###Info:
This project implements a Domain Specific Languange (DSL) as an LL(1) grammar that allows the definition of variables and relations of equalities and inequlities between variables and values. For example:
 ```
x = { x1, x2, x3 }
y = { y1, y2, y3 }
z = { z1, z2, z3 }
{ (y1, z1), (y2, z2), y3, z3) }
!{ (x1, y2), (x1, y3), (x2, y1), (x2, y3), (x3, y1), (x3, y2) } 
 ```
###Grammar:

This is the grammar that I defined for the DSL:

 ```
Dsl           ::= Statement RestStatement
RestStatement ::= Statement DSL | 
Statement 		  ::= Declaration | Relation
Declaration 	 ::= Variable = {Values}
Values 				   ::= Value RestValues
RestValues 		 ::= ,Values | 
Relation 			  ::= {Couples} | !{Couples}
Couples 			   ::= (Value,Value) RestCouples
RestCouples 	 ::= ,Couples |
Value 				    ::= ide
Variable 			  ::= ide

```

###Parser:
The project implements a descent parser for the DSL grammar able to lexically scan the input, to tokenize it, and to generate a java data type `DSL` representing the input.

###Solver:

The project implements a contraint solver for a `DSL` as an enumerator, able to retunr all possible solutions. <br\>

### 3x3 Latin Square:
You can use the DSL to solve a 3x3 latin square problem for example. The square is represented by rows, and the input is in the file `LS3x3`
 

