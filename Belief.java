
public class Belief {
	
	//nome del belief
	private String name;
	//dominio del belief
	private String[] domain;
	//valore
	private String bValue;
	
	//costruttori
	public Belief(String n, String[] d, String v){
		
		this.name = n;
		this.domain = d;
		this.bValue = v;
	
	}
	
	public Belief(String n,String v){

		this.name=n;
		this.bValue=v;

	}
	
	// getter e setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getDomain() {
		return domain;
	}
	public void setDomain(String[] domain) {
		this.domain = domain;
	}
	public String getbValue() {
		return bValue;
	}
	public void setbValue(String bValue) {
		this.bValue = bValue;
	}
	
	//funzioni
	public void changeValue(String newvalue){

		this.bValue = newvalue;
	
	}
	
	
}
