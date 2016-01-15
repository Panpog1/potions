import java.util.HashSet;


public class Ae extends Compound {
	static Ae ae=new Ae();
	
	private Ae(){}
	
	public String toString(){
		return "Ae";
	}

	@Override
	public HashSet<Compound> react(HashSet<Compound> all){
		HashSet<Compound> r = new HashSet<Compound>();
		boolean changed = false;
		for(Compound c:all){
			while(c instanceof E){
				c=((E)c).of;
				changed=true;
			}
			r.add(c);
		}
		return changed?r:all;
	}
}
