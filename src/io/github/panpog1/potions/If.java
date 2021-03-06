package io.github.panpog1.potions;

import java.util.Arrays;

public class If extends Compound {

	private Compound[] conditions;
	private Compound body;

	public If(Compound[] conditions, Compound body) {
		this.conditions = conditions;
		this.body = body;
	}

	public If(Compound[] parts) {
		this(Arrays.copyOf(parts, parts.length - 1), parts[parts.length - 1]);
	}

	@Override
	public String toStringSimple() {
		String s = "";
		for (Compound condition : conditions) {
			s += condition.toStringNoNums() + ",";
		}
		return String.format("If(%s%s)", s, body.toStringNoNums());
	}

	@Override
	public boolean react(Cauldron cauldron) {
		for (Compound condition : conditions)
			if (!cauldron.idgs.contains(condition))
				return false;
		cauldron.idgs.remove(this);
		cauldron.idgs.add(body);
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + Arrays.hashCode(conditions);
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
		if (!Arrays.equals(conditions, other.conditions))
			return false;
		return true;
	}
}
