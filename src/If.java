import java.util.Set;

public class If extends Compound {

	private Compound condition;
	private Compound body;

	public If(Compound condition, Compound body) {
		this.condition = condition;
		this.body = body;
	}

	@Override
	public String toStringSimple() {
		return String.format("If(%s,%s)", condition, body);
	}

	@Override
	public boolean react(Set<Compound> idgs) {
		if (!idgs.contains(condition))
			return false;
		idgs.remove(this);
		idgs.add(body);
		return true;
	}
}
