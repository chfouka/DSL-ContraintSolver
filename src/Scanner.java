import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;


public class Scanner {
	private StreamTokenizer input;
	
	public enum Type {VARIABLE, ASSIGN, COMMA, NOT, AT, AG, CT, CG, EOF, INVALID_TOKEN};
	
	public Scanner(Reader r ){
		input = new StreamTokenizer(r);
		input.resetSyntax();
		input.eolIsSignificant(false);
		input.wordChars('a', 'z');
		input.wordChars('A', 'Z');
		input.wordChars('0', '9');
		input.ordinaryChar(',');
		input.ordinaryChar('{');
		input.ordinaryChar('}');
		input.ordinaryChar('(');
		input.ordinaryChar(')');
		input.ordinaryChar('!');
		input.ordinaryChar('=');
		input.whitespaceChars('\u0000', '\u0020');
	}
	
	public Type nextToken(){
		
		try{
				switch(input.nextToken()){
				case StreamTokenizer.TT_EOF:
					return Type.EOF;
					
				case StreamTokenizer.TT_WORD:
					return Type.VARIABLE;
				case '{':
					return Type.AG;
				case '}':
					return Type.CG;
				case '(':
					return Type.AT;
				case ')':
					return Type.CT;
				case '=':
					return Type.ASSIGN;
					
				case ',':
					return Type.COMMA;
					
				case '!':
					return Type.NOT;
				}
			}		
		catch(IOException ex){
			return Type.EOF;
			}		
		return Type.INVALID_TOKEN;		
	}
	
	public void PushBack(){
		input.pushBack();
	}
	
	public String sval(){
		return new String(input.sval);
	}
	
}
