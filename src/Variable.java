
public class Variable {
	public String ide;
	
	//public Variable ( ){}
	
	public Variable ( String s ){
		//ide = new String(s);
		ide = s;
	}
	@Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Variable v = (Variable) obj;
        return (this.ide.equals(v.ide));
    }
	@Override
	public String toString(){
		return ide;
	}
}
