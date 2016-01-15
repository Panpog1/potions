
public class Essence extends Compound{
	public final Compound of;
	
	public Essence(Compound of){
		this.of=of;
	}
	
	public String toString(){
		return String.format("!"+of);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((of == null) ? 0 : of.hashCode());
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
		Essence other = (Essence) obj;
		if (of == null) {
			if (other.of != null)
				return false;
		} else if (!of.equals(other.of))
			return false;
		return true;
	}
}
