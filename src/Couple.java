
public class Couple<A,B> {

	public A c1;
	public B c2;
	
	public Couple ( A c1, B c2 ){
		this.c1 = c1;
		this.c2 = c2;
	}
	
	@Override
	public String toString(){
		String s = new String();
		s = "(" + c1.toString() + "," + c2.toString() +") "; 
		return s;
		
	}
}
