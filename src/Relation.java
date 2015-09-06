import java.util.ArrayList;


public class Relation extends Statement {
	
	public boolean neg;
	public ArrayList<Couple<Value,Value>> couples;
	
	//public Relation ( ) {}
	public Relation (ArrayList<Couple<Value,Value>> c, boolean b){
		couples = c;
		neg = b;
	}
	
	/*public boolean exist(Couple<Value,Value> couple){
		for(Couple<Value,Value> c: couples){
			if(c.c1.equals(couple.c1) && c.c2.equals(couple.c2))  return true;
		}
		return false;
	}*/
	
	public String toString(){
		String s = new String();
		s = "{ ";
		for(Couple<Value,Value> c : couples)
			s += c.toString() + ", ";
		s += "}";
		return s;
	}
}
