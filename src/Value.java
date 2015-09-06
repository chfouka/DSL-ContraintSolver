
public class Value {

	public String val;
	
	//public Value ( ){val = new String();}
	
	public Value ( String v ){
		//val = new String(v);
		val = v;
	}
	/*public boolean equals(Value val1){
		if(this.val.equals(val1.val)) return true;
		else return false;
	}*/
	
	@Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Value v = (Value) obj;
        return (this.val.equals(v.val));
    }
	
	
	
	public String toString(){
		return  new String(val);
	}
}
