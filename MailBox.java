import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class MailBox {
	
	private ArrayList<Map.Entry<Integer,Message>> myBox = new ArrayList<Map.Entry<Integer, Message>>();
	private GameObject owner;
	
	public MailBox(GameObject o){
		this.owner=o;
	}
	public Message readMail()	{
		if (myBox.size() > 0)
			{
			Message ret = myBox.get(0).getValue();
			myBox.remove(0);
			return ret;
			}
				else
			{
				return null;
			}


	}
	public Message readMailByID(int id) {
		Message ret=null;
		for (int i = 0;i < myBox.size();i++)
		{
			if (myBox.get(i).getKey() == id)
			{
				ret = myBox.get(i).getValue();
				myBox.remove(i);
				break;
			}
		}
		return ret;
	}
	public void writeInBox(Message m) {
		Map.Entry<Integer,Message> newMail = new AbstractMap.SimpleEntry<Integer, Message>(m.getMyID(),m);
		myBox.add(newMail);
	}
	public void sendMail(Message m, String dest) {
		GameObject d = GameObject.find(dest);
		d.sendMessage("WriteInMailBox",m);
	}
	public void sendMail(Message m, GameObject dest) {
		Object tempVar = dest.getComponent("ResourceAgent");
		ResourceAgent ag = tempVar instanceof ResourceAgent ? (ResourceAgent)tempVar : null;
		MailBox box = ag.myBox;
		box.writeInBox(m);
	}
}
