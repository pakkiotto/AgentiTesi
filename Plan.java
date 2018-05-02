import java.util.*;

public class Plan {
	
	public boolean fail = false;
	private ArrayList<Action> planActions = new ArrayList<Action>();
	private ArrayList<Beliefs> planActionPostBeliefs = new ArrayList<Beliefs>();
	private int index = 0;
	
	public ArrayList<Action> getPlanActions() {
		return planActions;
	}
	public void setPlanActions(ArrayList<Action> planActions) {
		this.planActions = planActions;
	}
	public ArrayList<Beliefs> getPlanActionPostBeliefs() {
		return planActionPostBeliefs;
	}
	public void setPlanActionPostBeliefs(ArrayList<Beliefs> planActionPostBeliefs) {
		this.planActionPostBeliefs = planActionPostBeliefs;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public boolean isActionsConluded() {
		for (int i = 0;i < index;i++)
		{
			if (planActions.get(i).done == false || (planActions.get(i).isPossible == true && planActions.get(i).done == false))
			{
				return false;
			}
		}
		return true;
	}
}

