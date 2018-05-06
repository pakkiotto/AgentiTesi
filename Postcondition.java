import java.util.*;

public class Postcondition
{

	private String[] beliefNames;
	private String[] newValues;
	private PostconditionDelegate function;
	private ArrayList<String> oldValues = new ArrayList<String>();
	private int type = 0;
	private ArrayList<String> notApply = new ArrayList<String>();

	public Postcondition(String[] b, String[] n) {
		this.setBeliefNames(b);
		this.setNewValues(new String[n.length]);
		for (int i = 0;i < n.length;i++)
		{
			String[] temp = n[i].split("[-]", -1);
			getNewValues()[i] = temp[0];
			if (temp.length > 1)
			{
				if (temp[1].equals("n"))
				{
					notApply.add(getBeliefNames()[i]);
				}
			}

		}
		this.setNewValues(n);
		this.setType(1);
	}
	public Postcondition(String[] s, PostconditionDelegate f) {
		this.setType(2);
		this.function = (ArrayList<String> olds, ArrayList<String> news) -> f.invoke(olds, news);
		this.setBeliefNames(s);
	}
	public Postcondition() {
		this.setType(0);
	}
	public Postcondition(String[] b, String[] n, ArrayList<String> app) {
		this.setBeliefNames(b);
		this.setNewValues(n);
		this.setType(1);
		this.notApply = app;
	}
	public Postcondition(String[] s, PostconditionDelegate f, ArrayList<String> app) {
		this.setType(2);
		this.function = (ArrayList<String> olds, ArrayList<String> news) -> f.invoke(olds, news);
		this.setBeliefNames(s);
		this.notApply = app;
	}
	public Postcondition(ArrayList<String> app) {
		this.setType(0);
		this.notApply = app;
	}
	public Postcondition(String s) {
		this.setType(1);
		String[] temp = s.split("[,]", -1);
		String[] n = new String[temp.length];
		String[] p = new String[temp.length];
			for (int i = 0;i < temp.length;i++)
				{
					String [] temp1 = temp[i].split("[=]", -1);
					n[i] = temp1[0];
					String[] temp2 = temp1[1].split("[-]", -1);
					p[i] = temp2[0];
					if (temp2.length > 1)
					{
						if (temp2[1].equals("n"))
						{
							notApply.add(n[i]);
						}
					}
				}
		this.setBeliefNames(n);
		this.setNewValues(p);
	}
	public Postcondition(String s, ArrayList<String> app) {
		this.setType(1);
		String[] temp = s.split("[,]", -1);
		String[] n = new String[temp.length];
		String[] p = new String[temp.length];
			for (int i = 0;i < temp.length;i++)
			{
				String [] temp1 = temp[i].split("[=]", -1);
				n[i] = temp1[0];
				p[i] = temp1[1];
			}
		this.setBeliefNames(n);
		this.setNewValues(p);
		this.notApply = app;
	}
	public Beliefs ApplyPostCondition(Beliefs old) {
		Beliefs newBeliefs = new Beliefs();
			if (getType() != 0)
			{
				for (int i = 0;i < old.getBels().size();i++)
				{
					newBeliefs.addBelief(old.getBels().get(i).getName(),old.getBels().get(i).getDomain(),old.getBels().get(i).getbValue());
				}
			if (getType() == 1)
			{
				for (int i = 0;i < getBeliefNames().length;i++)
				{
					newBeliefs.changeBeliefValue(getBeliefNames()[i],getNewValues()[i]);
				}
			}
			else
			{
				ArrayList<String> oldvalues = new ArrayList<String>();
				ArrayList<String> newv = new ArrayList<String>();
				for (int i = 0;i < getBeliefNames().length;i++)
				{
					String temp = old.getBeliefValue(getBeliefNames()[i]);
					if (temp != null)
					{
						oldvalues.add(temp);
					}
					else
					{
						oldvalues.add(getBeliefNames()[i]);
					}
				}
			this.oldValues = oldvalues;
			function.invoke(oldvalues, newv);
			for (int i = 0;i < getBeliefNames().length;i++)
			{
				String temp = old.getBeliefValue(getBeliefNames()[i]);
					if (temp != null)
					{
						String[] temp1 = newv.get(i).split("[-]", -1);
							if (temp1.length > 1)
							{
								if (temp1[1].equals("n"))
								{
									if (this.isNotApply(getBeliefNames()[i]) == false)
									{	
										notApply.add(getBeliefNames()[i]);
									}

								}
							}
						newBeliefs.changeBeliefValue(getBeliefNames()[i],temp1[0]);
					}
			}

			}
		}
			return newBeliefs;
}
	public boolean isNotApply(String belCheck) {
		for (int i = 0;i < notApply.size();i++)
		{
			if (notApply.get(i).equals(belCheck))
			{
				return true;
			}
		}
		return false;
}
	public boolean isInPostcondition(String s) {
		for (int i = 0;i < getBeliefNames().length;i++)
		{
			if (getBeliefNames()[i].equals(s))
			{
				return true;
			}
		}

		return false;
}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String[] getBeliefNames() {
		return beliefNames;
	}
	public void setBeliefNames(String[] beliefNames) {
		this.beliefNames = beliefNames;
	}
	public String[] getNewValues() {
		return newValues;
	}
	public void setNewValues(String[] newValues) {
		this.newValues = newValues;
	}
	@FunctionalInterface
	public interface PostconditionDelegate
	{
		void invoke(java.util.ArrayList<String> olds, java.util.ArrayList<String> news);
	}
}
