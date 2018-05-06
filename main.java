
public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Beliefs beliefs1 = new Beliefs();
		
		beliefs1.addBelief("cucciolo", "piccolo");
		beliefs1.addBelief("amre", "piccolo");
		beliefs1.addBelief("nabbo", "piccolo");
		beliefs1.addBelief("fortnite", "piccolo");
		
		Belief belief1, belief2;
		
		belief1 = beliefs1.getBelief("fortnite");
		//belief2 = beliefs1.getBeliefValue("piccolo");
		
		String name = belief1.getName();
		String value = beliefs1.getBeliefValue("amre");
		beliefs1.changeBeliefValue("amre", "lacasadicarta");
		String value2 = beliefs1.getBeliefValue("amre");
		System.out.println("il val del belief2 e= "+value);
		System.out.println("il nuovo val del belief2 e= "+value2);
		
	}

}
