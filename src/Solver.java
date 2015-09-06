import java.util.ArrayList;
import java.util.HashMap;


public class Solver {

	public Dsl dsl;
	public ArrayList<Couple<Variable,Value>> assignment;
	public ArrayList<Variable> variables;
	
	public boolean firstsolution; /*for ex4*/
	public HashMap<Variable,ArrayList<Couple<Value,Value>>> mustConstraints;	/*for ex5*/
	public HashMap<Variable,ArrayList<Couple<Value,Value>>> notConstraints; /*for ex5*/
	
	
	public Solver( Dsl d ){	
		dsl = d;
		assignment = new ArrayList<Couple<Variable,Value>>();
		variables = new ArrayList<Variable>();
		firstsolution = false;
		for (Declaration dec: dsl.declarations){
			variables.add(dec.var);
		}
		
		mustConstraints = new  HashMap<Variable, ArrayList<Couple<Value,Value>>>();
		notConstraints = new  HashMap<Variable,ArrayList<Couple<Value,Value>>>();
	}
	public boolean solve(int level, ArrayList<Variable> notassigned){
		if( level == 0 ) {notassigned = new ArrayList<Variable> ();
		notassigned.addAll(variables);}
		System.out.println(level);
		
		System.out.println("solve(): variables" + notassigned.toString());
		System.out.println("solve(): asiignments" + assignment.toString());
		
		Variable var = notassigned.get(0); //select variable in variables
		System.out.println("solve: var = " + var.ide);
		ArrayList<Value> varvalues = dsl.getValues(var);
		
		for(Value val: varvalues){
			System.out.println("solve: value = " + val.val);
			Couple<Variable,Value> assign = new Couple<Variable,Value>(var,val);
			
			ArrayList<Couple<Value,Value>> must = new ArrayList<Couple<Value,Value>>();
			ArrayList<Couple<Value,Value>> not = new ArrayList<Couple<Value,Value>>();

			if (Consistent(assign, must, not)){
				assignment.add(assign);
				mustConstraints.put(var, must);
				System.out.println("solve(): assign" + assign.toString() + mustConstraints.toString());
				notConstraints.put(var, not);
				if(assignComplete()){
					System.out.println("Solve(): assComplete");
					return true;
					}
				
				else{
					ArrayList<Variable> vars = new ArrayList<Variable>();
					vars.addAll(notassigned);
					vars.remove(var);
					if (solve(level+1, vars))return true; 
				}
			}			
			assignment.remove(assign);
		}
		return false;	
	}
	
	public boolean Consistent(Couple<Variable,Value> assign, ArrayList<Couple<Value,Value>> must, ArrayList<Couple<Value,Value>> not){
		ArrayList<Couple<Value,Value>> tempmust = new ArrayList<Couple<Value,Value>>();
		ArrayList<Couple<Value,Value>> tempnot = new ArrayList<Couple<Value,Value>>();

		for(Relation rel: dsl.relationsNeg){
			for(Couple<Value,Value> constraint: rel.couples){
				Variable varsx = dsl.getVariable(constraint.c1);
				Variable vardx = dsl.getVariable(constraint.c2);
				System.out.println("ConsistenN constraint:" + constraint.toString() + "varsx: " + varsx + " vardx" + vardx);
				if(varsx.equals(assign.c1) && assign.c2.equals(constraint.c1)){
					System.out.println("match1");
					
					tempnot.add(constraint);
					
					for(Couple<Variable,Value> assn: assignment){
						if(assn.c1.equals(vardx) && assn.c2.equals(constraint.c2))
							return false;								
					}
				}
				if(vardx.equals(assign.c1) && assign.c2.equals(constraint.c2)){
					System.out.println("match2");
					
					tempnot.add(constraint);
					
					for(Couple<Variable,Value> assn: assignment){
						if(assn.c1.equals(varsx) && assn.c2.equals(constraint.c1))
							return false;				
					}
				}
				
			}
		}
		
		for(Relation rel: dsl.relationsPos){
			for(Couple<Value,Value> constraint: rel.couples){
				Variable varsx = dsl.getVariable(constraint.c1);
				Variable vardx = dsl.getVariable(constraint.c2);
				System.out.println("ConsistenP constraint:" + constraint.toString() + "varsx: " + varsx + " vardx" + vardx);
				if(varsx.equals(assign.c1) && (assign.c2.equals(constraint.c1)) ){
					System.out.println("match1");
					
					tempmust.add(constraint);
					
					for(Couple<Variable,Value> assn: assignment){
						if((assn.c1.equals(vardx)) && !(assn.c2.equals(constraint.c2)))
							return false;
					}
				}
				
				if(vardx.equals(assign.c1) && (assign.c2.equals(constraint.c2) )){
					System.out.println("match2");

					tempmust.add(constraint);

					for(Couple<Variable,Value> assn: assignment){
						if((assn.c1.equals(varsx)) && (assn.c2.equals(constraint.c1 )) && 
						!(assign.c2.equals(constraint.c2)) ) 
						return false;
					}
				}
			}
		}
		
		//System.out.println("consisten(): tempmust  tempnot " + tempmust.toString() + tempnot.toString());
		must.addAll(tempmust);
		not.addAll(tempnot);
		return true;
	}
	
	public boolean getNextSolution(){
		if(!firstsolution){
			firstsolution = true;
			if(nextSol(0,0)) return true;
		}			
		int i,j,k;
		//index = assignment.size() -1 ;
		Couple<Variable,Value> assign = assignment.get(assignment.size() -1 );
		/*i = (dsl.getValues(assign.c1)).indexOf(assign.c2);
		assignment.remove(assign);
		mustConstraints.remove(assign.c1);
		notConstraints.remove(assign.c1);
		boolean b = nextSol(variables.indexOf(assign.c1), i+1); */
		boolean b = false;
		while( !(assignment.isEmpty()) && !b ){
			//index = assignment.size() -1;
			assign = assignment.get(assignment.size() -1);
			i = (dsl.getValues(assign.c1)).indexOf(assign.c2);
			mustConstraints.remove(assignment.get(assignment.size() -1).c1);
			notConstraints.remove(assignment.get(assignment.size() -1).c1);
			assignment.remove(assignment.size()-1);
			b = nextSol(variables.indexOf(assign.c1), i+1);
			
			/*k = assignment.size();
			int num = 0;
			for(j=assignment.size() -1; j<k; j++){
				num ++;
				mustConstraints.remove(assignment.get(assignment.size() -1).c1);
				notConstraints.remove(assignment.get(assignment.size() -1).c1);
				assignment.remove(assignment.size()-1);
			}
			System.out.println("***CICLO*** " + num); */
			//System.out.println("getnext() index, j:" + index + " " + (i+1) );
		}
		System.out.println(assignment.isEmpty());
		return b;
			
	}
	
	public boolean nextSol(int index, int i){
		Variable var = variables.get(index);
		ArrayList<Value> varvalues = dsl.getValues(var);
		System.out.println("nextSol: assignment:" + assignment);
		System.out.println("nextSol: variable:" + var );
		
		for(int j=i; j<varvalues.size(); j++){
			Couple<Variable, Value> assign = new Couple<Variable,Value>(var, varvalues.get(j));
			System.out.println("nextSol: control:" + assign );
			ArrayList<Couple<Value,Value>> must = new ArrayList<Couple<Value,Value>>();
			ArrayList<Couple<Value,Value>> not = new ArrayList<Couple<Value,Value>>();

			if (Consistent(assign, must ,not)){
				mustConstraints.put(var, must);
				notConstraints.put(var, not);
				System.out.println("solve(): assign" + assign.toString() + mustConstraints.toString());
				System.out.println("nextSol: control: OK"  );
				assignment.add(assign);
				if(assignComplete()) {return true;}					
				else {return nextSol(index+1, 0);}  /*sicuramente in 'variables' quelle dopo var non sono ancora assegnate*/
			}	
			System.out.println("nextSol: control: notOK"  );
			assignment.remove(assign);
		}
		return false;	
	}
		
	public boolean assignComplete(){
		for(Variable v: variables){
			boolean b = false;
			for(Couple<Variable,Value> c : assignment){
				if(c.c1.equals(v)) b = true;
			}
			if(!b) return false;
		}
		return true;
	}
	
	public String toString(){
		String s = new String();
		s = "< ";
		for(Couple<Variable,Value> assign : assignment)
			s += assign.toString() + ", ";
		s += ">"; 		
		return s;
	}
}

