import java.util.ArrayList;
import java.util.List;

public class AntiEssence extends Compound {
	public String toString(){
		return "anti-essence";
	}

	@Override
	public List<Compound> react(List<Compound> all){
		List<Compound> r = new ArrayList<Compound>();
		boolean changed = false;
		for(Compound c:all){
			while(c instanceof Essence){
				c=((Essence)c).of;
				changed=true;
			}
			r.add(c);
		}
		return changed?r:all;
	}
}
