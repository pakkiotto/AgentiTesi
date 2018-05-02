import java.util.*;

public class Goal
{
	private boolean remove = false;
	private ArrayList<Belief> goalBel = new ArrayList<Belief>();
	private Precondition pre;
	private int type = 0;
	private ArrayList<String> variables = new ArrayList<String>();
	private ArrayList<String[]> variablesDomain = new ArrayList<String[]>();
	private String name;
	private int priority;
	private Plan ReachingPlan = null;
	private boolean isPossible = true;
	private boolean isReached = false;
	
	public Goal(String name, int pr){
		this.setType(1);
		this.name = name;
		this.setPriority(pr);
	}
	public void addgoalBelief(String name, String[] d, String val){
		getGoalBel().add(new Belief(name,d,val));
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public ArrayList<Belief> getGoalBel() {
		return goalBel;
	}
	public void setGoalBel(ArrayList<Belief> goalBel) {
		this.goalBel = goalBel;
	}
	public boolean checkGoalCondition(Beliefs b) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
	
	
}
