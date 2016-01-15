
import java.util.Scanner;
import java.util.HashSet;

public class Main {
	private static Scanner in = new Scanner(System.in);
	private static HashSet<Compound> idgs=new HashSet<Compound>();
	
	public static void main(String[] args) {
		for(String arg:args){
			int skipped=0;
			Compound c = parse(arg);
			if(c==null){
				System.out.printf("I don't recognise the ingridient %s.\nSkiping it.\n",arg);
				skipped++;
			}else{
				add(c);
			}
			if(skipped==0){
				System.out.printf("All ingidients sucsesfully added");
			}
			if(skipped>1){
				System.out.printf("%d ingridients skiped\n",skipped);
			}
		}
		while(true){
			System.out.print("Cauldron contains: ");
			for(Compound idg:idgs){
				System.out.print(idg+" ");
			}
			System.out.print("\nAdd ad an ingredient: ");
			//Idg is ingridient
			String nextLine = in.nextLine();
			if(nextLine.startsWith("#")){
				System.out.println("Comand mode. args to convert curent contents to comand line form");
				String comand = in.nextLine();
				if(comand.equals("args")){
					System.out.println();
				}else{
					System.out.println("unkown comand");
				}
			}else{
				Compound nextIdg = parse(nextLine);
				if(nextIdg==null){
					System.out.print("I don't recognise that ingridient");
				}else{
					add(nextIdg);
				}
			}
		}
	}
	private static void add(Compound nextIdg) {
		idgs.add(nextIdg);
		boolean done=false;
		while(!done){
			done=true;
			for(Compound idg:idgs){
				if(idg.react(idgs)){
					done=false;
					break;
				}
			}
		}
	}
	private static Compound parse(String s){
		if(s.startsWith("E")){
			Compound inner =parse(s.substring(1));
			return inner==null?null:(new E(inner));
		}
		if(s.startsWith("(")&&s.endsWith(")")){
			return new Base(s.substring(1, s.length()-1));
		}
		if(s.equals("anti-essence")||s.equals("Ae")){
			return Ae.ae;
		}
		if(s.startsWith("R")){
			Compound inner =parse(s.substring(1));
			return inner==null?null:(new R(inner));
		}
		return null;
	}
}