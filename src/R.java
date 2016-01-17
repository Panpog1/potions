import java.util.HashSet;

public class R extends Compound {
	private Compound inner;

	public R(Compound inner) {
		this.inner = inner;
	}

	@Override
	public boolean react(HashSet<Compound> ids) {
		return ids.remove(inner);
	}

	@Override
	public String toString() {
		return "R" + inner.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inner == null) ? 0 : inner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		R other = (R) obj;
		if (inner == null) {
			if (other.inner != null)
				return false;
		} else if (!inner.equals(other.inner))
			return false;
		return true;
	}
}
