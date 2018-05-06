
public class AutomaState{

	public Beliefs stateBeliefs = new Beliefs();
	public String stringBeliefs;
	public int ID = 0;
	public String myID;

	public AutomaState(Beliefs b, int ids) {

		this.stateBeliefs = b;
		this.ID = ids;
		this.myID = "id" + ID;
		stringBeliefs = "";
		this.ID++;
		
		for (int i = 0;i < stateBeliefs.getBels().size();i++)
		{
			if (i != stateBeliefs.getBels().size() - 1)
			{
				stringBeliefs = stringBeliefs + stateBeliefs.getBels().get(i).getName() + "=" + stateBeliefs.getBels().get(i).getbValue() + ";";
			}
			else
			{
				stringBeliefs =stringBeliefs + stateBeliefs.getBels().get(i).getName() + "=" + stateBeliefs.getBels().get(i).getbValue();
			}
		}
	}
}
