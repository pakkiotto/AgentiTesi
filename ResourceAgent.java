import java.util.Iterator;

public class ResourceAgent extends Agent{
	
	//protected NavMeshAgent myagent;
	//protected Animation myanim;
	private Semaphore S;
	//private GUIText text;
	public MailBox myBox;
	public String exclusiveCluster = null;
	public GameObject isServing = null;
	protected boolean isActing = false;
	protected Iterator currentAction = null;
	
	void awake() {
		GameObject gameObject = null;
		myBox=new MailBox(gameObject);
	}
	public void menageMessage(Message m) {
		switch (m.getPerformative())	{
				case "Join" :
			GameObject gameObject;
			if (exclusiveCluster.equals(""))
								{
									GameObject sen = m.getSender();
									Message resp = new Message("Response","JoinOK",gameObject);
									resp.changeID(m.getMyID());
									exclusiveCluster = (String)m.getContent();
									myBox.sendMail(resp,sen);
								}
								else
								{
									GameObject sen = m.getSender();
									Message resp = new Message("Response","JoinNO",gameObject);
									resp.changeID(m.getMyID());
									myBox.sendMail(resp,sen);
								}
									break;
				case "SendGoal":
								String send = m.getSender().name;
								Goal g = (Goal)m.getContent();
								if ((exclusiveCluster.equals(send)) || (agentGoal == null && exclusiveCluster.equals("")))
								{
									StopCoroutine("doPlan");	
									StopCoroutine("Idle");
									StopCoroutine("DoAction");
									if (currentAction != null)
									{
										StopCoroutine(currentAction);
									}
									ReleaseAllResources();
									isServing = m.getSender();
									agentGoal = g;
									StartCoroutine("SearchPlan",g);
								}
									break;
				case "Leave":
							String sendr = m.getSender().name;
							if (exclusiveCluster.equals(sendr))
							{
								exclusiveCluster = null;
							}
							break;

			}
}
	public void writeInMailBox(Message m) {
		myBox.writeInBox(m);
}
	//yield return non disponibile con java
	@Override
	public Iterator doPlan(Goal g){}

}