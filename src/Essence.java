
public class Essence extends Compound{
	public final Compound of;
	
	public Essence(Compound of){
		this.of=of;
	}
	
	public String toString(){
		return String.format("!"+of);
	}
}