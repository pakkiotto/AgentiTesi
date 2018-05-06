public class AutomaTransition {

	public Action transitionAction;
	public String actionDescription;
	public String sourceID;
	public String targetID;
	public AutomaState source;
	public AutomaState target;
	
	public AutomaTransition(Action a, AutomaState from, AutomaState to) {
		
		this.source = from;
		this.target = to;

		this.transitionAction = a;
		this.sourceID = from.myID;
		this.targetID = to.myID;
		this.actionDescription = a.getActID() + "$"; //ACTIONID
		this.actionDescription = actionDescription + a.getName() + "$"; //name

		for (int i = 0;i < a.getParameters().length;i++)
		{ //action parameters
			if (i != a.getParameters().length - 1)
			{
				this.actionDescription = actionDescription + a.getParameters()[i] + ",";
			}
			else
			{
				this.actionDescription = actionDescription + a.getParameters()[i] + "$";
			}
		}

		for (int i = 0;i < a.getPreconditions().size();i++)
		{ //preconditions
			if (i != 0)
			{
			this.actionDescription = actionDescription + "£";
			}
				for (int j = 0;j < a.getPreconditions().get(i).getBelsToCheck().length;j++)
				{
					if (j != a.getPreconditions().get(i).getBelsToCheck().length - 1)
					{
						this.actionDescription = actionDescription + a.getPreconditions().get(i).getBelsToCheck()[j]+ ",";
					}
					else
					{
					this.actionDescription = actionDescription + a.getPreconditions().get(i).getBelsToCheck()[j] + "%" + a.getPreconditions().get(i).isCritical();
					}
				}
				if (i == a.getPreconditions().size() - 1)
				{
					this.actionDescription = actionDescription + "$";
				}
		}
		
		this.actionDescription = actionDescription + a.getPost().getType() + "%";
		if (a.getPost().getType() == 1)
		{
			for (int i = 0;i < a.getPost().getBeliefNames().length;i++)
			{
				if (i != a.getPost().getBeliefNames().length - 1)
				{
					actionDescription = actionDescription + a.getPost().getBeliefNames()[i] + "=" + a.getPost().getNewValues()[i] + ",";
				}
				else
				{
					actionDescription = actionDescription + a.getPost().getBeliefNames()[i] + "=" + a.getPost().getNewValues()[i];
				}
			}
		}
		else
		{
			for (int i = 0;i < a.getPost().getBeliefNames().length;i++)
			{
				if (i != a.getPost().getBeliefNames().length - 1)
				{
					actionDescription = actionDescription + a.getPost().getBeliefNames()[i] + ",";
				}
				else
				{
					actionDescription = actionDescription + a.getPost().getBeliefNames()[i];
				}
			}
		}
	}
}
