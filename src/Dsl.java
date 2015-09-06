import java.util.ArrayList;
import java.util.HashMap;


public class Dsl {

	//public HashMap<Variable, ArrayList<Value>> declarations; 
	ArrayList<Declaration> declarations;
	public ArrayList<Relation> relationsPos;
	public ArrayList<Relation> relationsNeg;
	
	public Dsl ( ){
		declarations = new ArrayList<Declaration>();
		//declarations =  new HashMap<Variable,ArrayList<Value>>();
		relationsPos = new ArrayList<Relation>();
		relationsNeg = new ArrayList<Relation>();
	}
	
	public void addDec( Declaration dec ){
		declarations.add(dec);
	}
	
	public void addRel(Relation rel ){
		if(rel.neg) relationsNeg.add(rel);
		else relationsPos.add(rel);
			
	}
	
	public Variable getVariable(Value val){
		for( Declaration dec: declarations){
			for(Value v: dec.values){
				if(v.equals(val)) return dec.var;				
			}
		}		
		return null;
	}
	
	public ArrayList<Value> getValues(Variable var){
		for(Declaration dec: declarations){
			if(dec.var.equals(var)) return dec.values;
		}
		return null;
	}
	
	@Override
	public String toString(){
		String s = new String();
		for( Declaration dec: declarations ){
			s  += dec.toString() + "\n" ;				
		}
		
		for( Relation rel : relationsPos)
			s += rel.toString() + "\n";
		s += "!";
		for (Relation rel: relationsNeg)
			s += rel.toString() + "\n";
		return s;
	}
}
