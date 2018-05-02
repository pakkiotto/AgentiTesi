import java.util.ArrayList;

public class Semaphore{

	public ArrayList<Integer> semaphore;
	private int ticket = 0;
	
	private void awake() {
		this.semaphore = new ArrayList<Integer>();
	}
	public int enqueueAccess() {
		int t = ticket;
		semaphore.add(t);
			if (ticket == 1000)
			{
				ticket = 0;
			}
			else
			{
				ticket++;
			}
			
		return t;
	}

	public boolean requestAccess(int t) {
		if (semaphore.get(0).equals(t))
		{
			return true;
		}
		else
		{
			return false;
		}

	}
	public void releaseAccess(int t) {
		if (semaphore.get(0).equals(t))
		{
			semaphore.remove(0);
		}
	}
}
