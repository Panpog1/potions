import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
	static Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
		List<Compound> idgs = new ArrayList<Compound>();
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
						List<Compound> x = idg.react(idgs);
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
			return inner==null?null:(new Essence(inner));
		}
		if(s.startsWith("(")&&s.endsWith(")")){
			return new Base(s.substring(1, s.length()-1));
		}
		if(s.equals("anti-essence")||s.equals("Ae")){
			return new AntiEssence();
		}
		return null;
	}
}

