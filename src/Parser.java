import java.io.Reader;
import java.util.ArrayList;


public class Parser {

	public Scanner scanner;
	public Scanner.Type token;
	public Dsl dsl;
	
	public Parser ( Reader r ) {
		dsl = new Dsl();
		scanner = new Scanner(r);
	}
	
	public boolean match(Scanner.Type ttk ) throws SyntaxException {
		token = scanner.nextToken();	
		System.out.println("match: " + token.toString());
		if (token == ttk) return true;	
		else{
			System.out.println("match: throw");
			throw new SyntaxException(); 
		}
	}
	
	public Dsl Parse() throws SyntaxException{
		ParseDsl();
		match(Scanner.Type.EOF);
		return dsl;
	}
		
	public void ParseDsl() throws SyntaxException{
		/*DSL ::= Statement RestStatement*/
		try{
			statement();
			Restm();
		}
		catch(SyntaxException e){
			System.err.println("Parsing ERROR");
		}		
}
	
	public void statement() throws SyntaxException{
		/*Statement ::= Declaration | Relation */	
		Statement stat;
		token = scanner.nextToken();

		if(token == Scanner.Type.EOF)
			return;
		else if( token == Scanner.Type.VARIABLE){
			scanner.PushBack();
			stat = Declaration();
			dsl.addDec((Declaration) stat);
			return;
		}
		else if(token == Scanner.Type.AG || token == Scanner.Type.NOT){
			scanner.PushBack();
			stat = Relation();
			dsl.addRel((Relation) stat);
			return;
		}		
		else{
			System.out.println("statement(): " + token.toString());
			System.out.println("statement() throw");
			throw new SyntaxException();
		}
		
	}
	
	public void Restm() throws SyntaxException{
		/*RestStatement ::= Statement DSL | epsilon*/		
		token = scanner.nextToken();
		System.out.println("Restm(): token = " + token.toString()  );

		if(token == Scanner.Type.EOF){
			scanner.PushBack();
			return;
		}		
		else if(token == Scanner.Type.VARIABLE || token == Scanner.Type.AG || token == Scanner.Type.NOT){
			scanner.PushBack();
			statement();
			ParseDsl();
			return;
		}		
		else{	
			System.out.println("Restm(): "+ token.toString());
			System.out.println("Rest(): " + "throw");
			throw new SyntaxException();
		}
	}
	
	public Statement Declaration() throws SyntaxException{
		/* Declaration::= Variable = {Values} */
		Variable var =  Variable();
		match(Scanner.Type.ASSIGN);
		match(Scanner.Type.AG);
		ArrayList<Value> values = Values();
		match(Scanner.Type.CG);
		
		return new Declaration(var,values);
	}
	
	
	public Variable Variable() throws SyntaxException {
		/*Variable ::= ide */
		token = scanner.nextToken();
		if(token != Scanner.Type.VARIABLE) throw new SyntaxException();	
		System.out.println("Variable(): " + token.toString() );
		return new Variable(scanner.sval()) ;
		
	}
	
	
	public ArrayList<Value> Values() throws SyntaxException{
		/*Values ::= Value RestValues*/	
		ArrayList<Value> vals = new ArrayList<Value>();
		
		token = scanner.nextToken();	
		if(token != Scanner.Type.VARIABLE) throw new SyntaxException();
		System.out.println("Values(): "+ token.toString());
	    vals.add(new Value(scanner.sval()));
		ArrayList<Value> rest = RestV();
		if( rest != null) 
			vals.addAll(rest);
		
		return vals;
		
	}
	
	public ArrayList<Value> RestV() throws SyntaxException{
		/* RestValues ::= , Values | epsilon */
		
		token = scanner.nextToken();
		if(token == Scanner.Type.COMMA){
			System.out.println("RestV(): " + token.toString());
			return Values();
		}
		
		if(token == Scanner.Type.CG){
			scanner.PushBack();
			return null;
		}
		
		else throw new SyntaxException();
		
	}

	public Statement Relation( ) throws SyntaxException{
		/*Relation ::= {Couples} | !{Couples}*/
		boolean b = false ;
		
		token = scanner.nextToken();
		System.out.println("Relation(): "+ token.toString());
		
		if(token == Scanner.Type.AG)
			b = false;	
		if(token == Scanner.Type.NOT){
			match(Scanner.Type.AG);
			b = true;
		}
		ArrayList<Couple<Value,Value>> couples = Couples();
		match(Scanner.Type.CG);
		
		return new Relation(couples, b);
	}
	
	public ArrayList<Couple<Value,Value>> Couples() throws SyntaxException{
		/*Couples ::= (Value, Value) RestCouples */
		ArrayList<Couple<Value,Value>> cps = new ArrayList<Couple<Value,Value>>();
		
		match(Scanner.Type.AT);
		Value v1 = Value();
		match(Scanner.Type.COMMA);
		Value v2 = Value();
		match(Scanner.Type.CT);
		Couple<Value,Value> c = new Couple<Value, Value>(v1, v2);
		cps.add(c);
		
		ArrayList<Couple<Value,Value>> rest = Restc();
		
		if(rest != null)
			cps.addAll(rest);
		
		return cps;
	}
	
	public Value Value() throws SyntaxException{
		/*Value ::= ide*/
		token = scanner.nextToken();
		System.out.println("Value(): " + token.toString() );
		if(token != Scanner.Type.VARIABLE) throw new SyntaxException();	
		Value v = new Value(scanner.sval());
		return v;
		
	}
	
	public ArrayList<Couple<Value,Value>> Restc( ) throws SyntaxException{
		/*RestCouples ::= , Couples | epsilon*/
		
		token = scanner.nextToken();
		System.out.println("RestC(): "+ token.toString());
		if(token == Scanner.Type.COMMA)
			return Couples();
		if(token == Scanner.Type.CG){
			scanner.PushBack();
			return null;
		}
		
		else throw new SyntaxException();
	}
}

