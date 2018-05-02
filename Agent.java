import java.util.ArrayList;
import java.util.Iterator;

public class Agent {

	private boolean searchWithAutoma = false;
	private Beliefs agentBeliefs = new Beliefs();
	private ArrayList<Action> agentActions = new ArrayList<Action>();
	private Goal agentGoal = null;
	private World myWorld;
	private String agentName;
	private String type;
	protected int actID = 0;
	private boolean planCompeted = false;
	
	public boolean isSearchWithAutoma() {
		return searchWithAutoma;
	}
	public void setSearchWithAutoma(boolean searchWithAutoma) {
		this.searchWithAutoma = searchWithAutoma;
	}
	public Beliefs getAgentBeliefs() {
		return agentBeliefs;
	}
	public void setAgentBeliefs(Beliefs agentBeliefs) {
		this.agentBeliefs = agentBeliefs;
	}
	public World getMyWorld() {
		return myWorld;
	}
	public void setMyWorld(World myWorld) {
		this.myWorld = myWorld;
	}
	public Goal getAgentGoal() {
		return agentGoal;
	}
	public void setAgentGoal(Goal agentGoal) {
		this.agentGoal = agentGoal;
	}
	public ArrayList<Action> getAgentActions() {
		return agentActions;
	}
	public void setAgentActions(ArrayList<Action> agentActions) {
		this.agentActions = agentActions;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isPlanCompeted() {
		return planCompeted;
	}
	public void setPlanCompeted(boolean planCompeted) {
		this.planCompeted = planCompeted;
	}
	//doplan???????
	public void changeAgentBeliefs(Beliefs b) {
		for (int i = 0;i < b.getBels().size();i++)
			{
				Belief temp = agentBeliefs.getBelief(b.getBels().get(i).getName());
					if (temp != null)
				{
					this.agentBeliefs.changeBeliefValue(b.getBels().get(i).getName(),b.getBels().get(i).getbValue());
				}
			}
	}
	public void addGoal(Goal g) {
		this.agentGoal = g;
	}
	public final Plan Planning(Goal g, Beliefs b) {
		if (searchWithAutoma == false)
			{
				Planner myPlanner = new Planner(g,b,agentActions);
				Plan p = new Plan();
				//Plan la=myplanner.Planning();
				myPlanner.Planning(p);
					if (p.fail == false)
						{
							return p;
						}
					else
						{
							return null;
						}
			}
		else
			{ //cerca plan con automa
				Plan retplan = new Plan();
				automaSearching(retplan,g,b);
					if (retplan.fail == false)
					{
						return retplan;
					}
					else
					{
						return null;
					}

			}

}
	private void automaSearching(Plan retplan, Goal g, Beliefs b) {
		// TODO Auto-generated method stub
		
	}
	public Beliefs Percept() {
		Beliefs newBels = new Beliefs();
			for (int i = 0;i < this.agentBeliefs.getBels().size();i++)
			{
				if (this.agentBeliefs.getBels().get(i) instanceof PrivateBelief)
				{
					newBels.addBelief(agentBeliefs.getBels().get(i));
				}
			}
			for (int i = 0;i < myWorld.getFacts().worldFacts.getBels().size();i++)
			{
				newBels.addPublicBelief(myWorld.getFacts().worldFacts.getBels().get(i).getName(),myWorld.getFacts().worldFacts.getBels().get(i).getDomain(),myWorld.getFacts().worldFacts.getBels().get(i).getbValue(),(myWorld.getFacts().worldFacts.getBels().get(i) instanceof PublicBelief ? (PublicBelief)myWorld.getFacts().worldFacts.getBels().get(i) : null).getVisibility(),(myWorld.getFacts().worldFacts.getBels().get(i) instanceof PublicBelief ? (PublicBelief)myWorld.getFacts().worldFacts.getBels().get(i) : null).getVisibleTo());
			}
	
	return newBels;
			//return myWorld.getFacts();
	}
	public void changeWorld() {
		Beliefs newBels = new Beliefs();
			for (int i = 0;i < agentBeliefs.getBels().size();i++)
			{
				if (agentBeliefs.getBels().get(i) instanceof PublicBelief)
				{
					myWorld.getFacts().worldFacts.changeBeliefValue(agentBeliefs.getBels().get(i).getName(),agentBeliefs.getBels().get(i).getbValue());
				}
			}

			//this.myWorld.worldFacts.changeFacts(newbels);
	}
	public void addAction(Action a) {
 		a.setActID(agentActions.size());
 		agentActions.add(a);
	}
	private void Awake() {
		agentBeliefs = new Beliefs();
		agentActions = new ArrayList<Action>();
		agentBeliefs = this.Percept();
		//this.changeAgentBeliefs(b);
		String[] s;
		String[] n;
		String[] p;
		Precondition pre;
		Postcondition post;
		Action a;

	}
	//problema con delegati
	public Iterator doPlan(Goal g) {
		// TODO Auto-generated method stub
		return null;
	}
}

