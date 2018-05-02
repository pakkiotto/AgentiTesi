import java.util.ArrayList;
import java.util.LinkedList;

public class Planner {
	
	private ArrayList<Action> planActions = new ArrayList<Action>();
	private ArrayList<Action> possibleActions = new ArrayList<Action>();
	private Goal planGoal;
	private Beliefs startBeliefs;
	
	public Planner(Goal g, Beliefs b, ArrayList<Action> a) {
		this.planGoal = g;
		this.startBeliefs = b;
		this.possibleActions = a;
	}

	public void planning(Plan p) {

		boolean success = false;
		SearchTreeNode root = new SearchTreeNode(startBeliefs);
		LinkedList queue = new LinkedList();
		queue.offer(root);
		SearchTreeNode currentNode = root;
		ArrayList<Action> result = new ArrayList<Action>();
		ArrayList <Beliefs> resultBeliefs = new ArrayList<Beliefs>();
		int t = 0;
		while (success == false && !queue.isEmpty())
			{
				t++;
					if (t > 1000000)
					{
												break;
					}

				currentNode = (SearchTreeNode)queue.poll();
				if (isSuccess(currentNode.bNode))
				{
					success = true;
				}
				else
				{
					//Debug.Log(possibleActions.Count);
					for (int i = 0;i < possibleActions.size();i++)
					{
						ArrayList<Beliefs> newBel = new ArrayList<Beliefs>();
						ArrayList<Action> newActions = new ArrayList<Action>();
						possibleActions.get(i).checkAction(currentNode.bNode,newBel,newActions);
						if (!newBel.isEmpty())
						{
							for (int j = 0;j < newBel.size();j++)
							{
								SearchTreeNode newNode = new SearchTreeNode(currentNode,newBel.get(j),newActions.get(j));
								if (!newNode.isEqualAncient())
								{
									queue.offer(newNode);
								}
							}

						}
					}

				}
			}

		if (success)
		{
			//Plan p=new Plan();
			returnSuccessPlan(currentNode,result,resultBeliefs);
			//Debug.Log("numero cicli " + t);
			for (int j = result.size() - 1;j >= 0;j--)
			{
				p.getPlanActions().add(result.get(j));
				p.getPlanActionPostBeliefs().add(resultBeliefs.get(j));
			}
			//if(p!=null){
			//print (p.planActions.Count);
			//}
			//return p;
		}
		else
		{
			//Debug.Log("numero cicli " + t);
			p.fail = true;
		}
}
	public boolean isSuccess(Beliefs b) {
		if (planGoal.getType() == 1)
			{
			for (int i = 0; i < planGoal.getGoalBel().size();i++)
				{
					Belief temp = b.getBelief(planGoal.getGoalBel().get(i).getName());
					if (temp != null)
					{
						if (temp.getbValue() == planGoal.getGoalBel().get(i).getbValue())
						{

						}
					else
						{
							return false;
						}
					}
					else
						{
							return false;
						}
				}
					return true;
			}
				else
				{
					return planGoal.checkGoalCondition(b);
				}
	}
	public void Planning(Plan p) {
		// TODO Auto-generated method stub
		
	}
	private void returnSuccessPlan(SearchTreeNode node, ArrayList<Action> result, ArrayList<Beliefs> blist) {
		if (node.parent != null)
		{
			result.add(node.edge);
			blist.add(node.bNode);
			returnSuccessPlan(node.parent, result, blist);
		}
	}
}



