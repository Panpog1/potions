import java.util.HashSet;

public abstract class Compound {
	/**
	 * @param idgs All compounds to check reactivity with including its self
	 * @return all if no reactions are detected else a new List containing the compounds after reaction.
	 */
	public HashSet<Compound> react(HashSet<Compound> idgs){
		return idgs;
	}
}
