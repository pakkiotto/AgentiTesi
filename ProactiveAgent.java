import java.util.*;
public class ProactiveAgent extends ResourceAgent{

	public ArrayList<Goal> myGoals = new ArrayList<Goal>();
	private int pro = 0;


	private void Awake() {
		GameObject gameObject = null;
		myBox = new MailBox(gameObject);
	}
	//come gestisco yield?
	public Iterator<?> Deliberate() {
		return currentAction;}
	@Override
	public Iterator<?> doPlan(Goal g){
		return currentAction; }
	public Iterator<?> doAction(Action a){
		return currentAction;}
	//non riesco a comprendere
	public void deleteByPriority(int p) {
		for (int i = 0;i < myGoals.size();i++)
			{
				if (myGoals.get(i).getPriority() == p)
				{
					myGoals.remove(i);
					break;
				}
			}
	}
	public void orderByPriority() {
			Goal swap;
			for (int i = myGoals.size() - 1;i > 0;i--)
			{
				for (int j = 0;j < i;j++)
				{
					if (myGoals.get(j).getPriority() < myGoals.get(j + 1).getPriority())
					{
						swap = myGoals.get(j);
						myGoals.set(j, myGoals.get(j + 1));
						myGoals.set(j + 1, swap);
					}
				}
			}
		}
	
}