
public class World {

	private Semaphore s;
	private GUIText text;
	private Facts worldFacts = new Facts();
	private Dictioary worldDictionary = new Dictionary();

	public void start() {
		worldFacts = new Facts();
	}
	
	public void update() {
		
		
	}
	
	public Facts getFacts(){
		return worldFacts;
	}
}
