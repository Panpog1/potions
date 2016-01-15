import java.util.HashSet;


public class Ae extends Compound {
	static Ae ae=new Ae();
	
	private Ae(){}
	
	public String toString(){
		return "Ae";
	}

	@Override
	public boolean react(HashSet<Compound> idgs){
		System.out.println(idgs);
		for(Compound c:idgs){
			System.out.println(idgs);
			if(c instanceof E){
				Compound newC=((E)c).of;
				idgs.remove(c);
				idgs.add(newC);
				return true;
			}
		}
		return false;
	}
}
