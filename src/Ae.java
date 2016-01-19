import java.util.HashSet;

public class Ae extends Compound {
	static Ae ae = new Ae();

	private Ae() {
	}

	@Override
	public String toStringSimple() {
		return "Ae";
	}

	@Override
	public boolean react(HashSet<Compound> idgs) {
		for (Compound c : idgs) {
			if (c instanceof E) {
				Compound newC = ((E) c).inner;
				idgs.remove(c);
				idgs.add(newC);
				return true;
			}
		}
		return false;
	}
}
