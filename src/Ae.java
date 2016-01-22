import java.util.Set;

public class Ae extends Compound {

	public Ae() {
	}

	@Override
	public String toStringSimple() {
		return "Ae";
	}

	@Override
	public boolean react(Set<Compound> idgs) {
		for (Compound c : idgs) {
			if (c instanceof E) {
				Compound newC = ((E) c).getInner();
				idgs.remove(c);
				idgs.add(newC);
				return true;
			}
		}
		return false;
	}
}
