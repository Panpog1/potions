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
		return String.format("If(%s,%s)", condition.toStringSimple(), body.toStringSimple());
	}

	@Override
	public boolean react(Set<Compound> idgs) {
		if (!idgs.contains(condition))
			return false;
		idgs.remove(this);
		idgs.add(body);
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		If other = (If) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		return true;
	}
}
