
import java.util.Scanner;
import java.util.HashSet;

public class Main {
	static Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
		HashSet<Compound> idgs = new HashSet<Compound>();
		while(true){
			System.out.print("Cauldron contains: "+idgs+
					"\nAdd ad an ingredient: ");
			//Idg is ingridient
			Compound nextIdg = parse(in.nextLine());
			if(nextIdg==null){
				System.out.print("I don't recognise that ingridient");
			}else{
				idgs.add(nextIdg);
				boolean done=false;
				while(!done){
					for(Compound idg:idgs){
						HashSet<Compound> x = idg.react(idgs);
						if(x!=idgs){
							done=false;
							idgs=x;
							break;
						}else{
							done=true;
						}
					}
				}
			}
		}
	}
	private static Compound parse(String s){
		if(s.startsWith("!")){
			Compound inner =parse(s.substring(1));
			return inner==null?null:(new E(inner));
		}
		if(s.startsWith("(")&&s.endsWith(")")){
			return new Base(s.substring(1, s.length()-1));
		}
		if(s.equals("anti-essence")||s.equals("Ae")){
			return Ae.ae;
		}
		return null;
	}
}
