
public class Message {
	
	private String performative;
	private Object content;
	private static int ID;
	private int myID;
	private GameObject sender;
	
	public int getMyID() {
		return myID;
	}
	public void setMyID(int myID) {
		this.myID = myID;
	}
	public static int getID() {
		return ID;
	}
	public static void setID(int iD) {
		ID = iD;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public String getPerformative() {
		return performative;
	}
	public void setPerformative(String performative) {
		this.performative = performative;
	}
	public GameObject getSender() {
		return sender;
	}
	public void setSender(GameObject sender) {
		this.sender = sender;
	}
	
	public Message(String p, Object o, GameObject s) {
		this.performative = p;
			if (!p.equals("Response"))
			{
				this.myID = ID;
				ID++;
			}
		this.content = o;
		this.sender = s;
	}
	public void changeID(int i){
		this.myID=i;
	}
	
}
