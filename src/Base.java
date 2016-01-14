public class Base extends Compound {
	public String name;
	
	public Base(String name){
		this.name=name;
	}
	public String toString(){
		return String.format("(%s)",name);
	}
}
