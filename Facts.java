
public class Facts {

	public Beliefs worldFacts=new Beliefs();
	
	public void addFact(Belief b){
		worldFacts.addBelief(b);
	}
	public void addFact(String n,String[] d,String v,String vis){
		worldFacts.addPublicBelief(n,d,v,vis);
	}
	public void addFact(String n,String[] d,String v,String vis,String[] visto){
		worldFacts.addPublicBelief(n,d,v,vis,visto);
	}
	public void changeFacts(Beliefs b){
		worldFacts=b;
	}
}
