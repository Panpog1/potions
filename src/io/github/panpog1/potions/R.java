package io.github.panpog1.potions;

import java.util.Set;

public class R extends Compound {
	private Compound inner;

	public R(Compound inner) {
		this.inner = inner;
	}

	@Override
	public boolean react(Set<Compound> ids) {
		return ids.remove(inner);
	}

	public String toStringSimple() {
		return "R" + inner.toStringNoNums();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((inner == null) ? 0 : inner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		R other = (R) obj;
		if (!super.equals(other))
			return false;
		if (inner == null) {
			if (other.inner != null)
				return false;
		} else if (!inner.equals(other.inner))
			return false;
		return true;
	}
}
