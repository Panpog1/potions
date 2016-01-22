public class E extends Compound {
	private final Compound inner;

	public E(Compound inner) {
		this.inner = inner;
	}

	public Compound getInner() {
		return inner;
	}

	public String toStringSimple() {
		return String.format("E" + inner.toStringNoNums());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 2; // to avoid hash collision of Ex with Rx
		result = prime * result + super.hashCode() + 1;
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
		E other = (E) obj;
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
