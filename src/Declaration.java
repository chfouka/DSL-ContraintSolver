import java.util.ArrayList;


public class Declaration extends Statement {

	public Variable var;
	public ArrayList<Value> values;
	
	//public Declaration ( ){}
	
	public Declaration ( Variable v, ArrayList<Value> val ) {
		var = v;
		//values = new ArrayList<Value>(val) ;
		values = val;
	}
	
	@Override
	public String toString(){
		String s = new String();
		s = var.ide + "={ ";
		for( Value v: values )
			s += v.val + ", ";
		s += "}";
		return s;
		
	}
} 	
