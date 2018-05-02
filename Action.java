import java.util.ArrayList;

public class Action {
	
	private ArrayList<String> variables = new ArrayList<String>();
	private ArrayList<String[]> variablesDomain = new ArrayList<String[]>();
	private ArrayList<String> univVariables = new ArrayList<String>();
	private ArrayList<varPropertydelegate> varProp = new ArrayList<varPropertydelegate>();
	private ArrayList<ArrayList<String>> varPropPars = new ArrayList<ArrayList<String>>();
	private String name;
	private String[] parameters;
	private ArrayList <Precondition> Preconditions = new ArrayList<Precondition>();
	private Precondition pre;
	private Postcondition post;
	private boolean isAsync = false;	
	private actionImplementation myImplementation;
	public boolean done = false;
	public boolean isPossible = true;
	private int actID;
	
	public Action(String np, Precondition p, Postcondition po) {
		String[] temp = np.split("[(]", -1);
		this.name = temp[0];
		String[] temp1 = temp[1].split("[)]", -1);
		String[] temp2 = temp1[0].split("[,]", -1);
		this.parameters = temp2;
		Preconditions.add(p);
		this.post = po;
	}
	public Action(String np) {
		String[] temp = np.split("[(]", -1);
		this.name = temp[0];
		String[] temp1 = temp[1].split("[)]", -1);
		String[] temp2 = temp1[0].split("[,]", -1);
		this.parameters = temp2;
	}
	public Action(String np, Precondition p, Postcondition po, String asy) {
		String[] temp = np.split("[(]", -1);
		this.name = temp[0];
		String[] temp1 = temp[1].split("[)]", -1);
		String[] temp2 = temp1[0].split("[,]", -1);
		this.parameters = temp2;
		//pre=p;
		Preconditions.add(p);
		this.post = po;
		
		if (asy.equals("Async"))
		{
			isAsync = true;
		}
	}
	public Action(String np, String asy) {
		String[] temp = np.split("[(]", -1);
		this.name = temp[0];
		String[] temp1 = temp[1].split("[)]", -1);
		String[] temp2 = temp1[0].split("[,]", -1);
		this.parameters = temp2;
		if (asy.equals("Async"))
		{
			isAsync = true;
		}
	}
	public Action(String n, String[] p, Precondition pr, Postcondition po,ArrayList<String> v,ArrayList<String[]> vd, ArrayList<String> univV, ArrayList<varPropertydelegate> dg, ArrayList<ArrayList<String>> varpars, boolean asy ,actionImplementation ai, int id) {

		this.name = n;
		this.parameters = p;
		//pre=pr;
		Preconditions.add(pr);
		this.post = po;
		this.variables = v;
		this.variablesDomain = vd;
		this.univVariables = univV;
		this.varProp = dg;
		this.varPropPars = varpars;
		this.myImplementation = (String[] pars, Action a) -> ai.invoke(pars, a);
		isAsync = asy;
		this.actID = id;
	}
	public Action(String n, String[] p, ArrayList<Precondition> pr, Postcondition po, ArrayList<String> v, ArrayList<String[]> vd, ArrayList<String> univV, ArrayList<varPropertydelegate> dg, ArrayList<ArrayList<String>> varpars, boolean asy, actionImplementation ai, int id) {
		this.name = n;
		this.parameters = p;
		//pre=pr;
		this.Preconditions = pr;
		this.post = po;
		this.variables = v;
		this.variablesDomain = vd;
		this.univVariables = univV;
		this.varProp = dg;
		this.varPropPars = varpars;
		this.isAsync = asy;
		this.myImplementation = (String[] pars, Action a) -> ai.invoke(pars, a);
		this.actID = id;
	}
	public void isA(String s, String[] domain) {
		variables.add(s);
		variablesDomain.add(domain);
		varProp.add(null);
		varPropPars.add(null);
	}
	public void isA(String s, String[] domain, String u) {
		variables.add(s);
		variablesDomain.add(domain);
		univVariables.add(s);
		varProp.add(null);
		varPropPars.add(null);
	}
	public void isA(String s, String[] domain, String u, varPropertydelegate dg, String par1) {
		variables.add(s);
		variablesDomain.add(domain);
		univVariables.add(s);
		varProp.add(dg);
		ArrayList <String> temp = new ArrayList<String>();
		temp.add(par1);
		varPropPars.add(temp);
	}
	public void isA(String s, String[] domain, String u, varPropertydelegate dg, String par1, String par2) {
		variables.add(s);
		variablesDomain.add(domain);
		univVariables.add(s);
		varProp.add(dg);
		ArrayList <String> temp = new ArrayList<String>();
		temp.add(par1);
		temp.add(par2);
		varPropPars.add(temp);
	}
	public final void addPostcondition(String p) {
		String[] temp = p.split("[,]", -1);
		String[] postNames = new String[temp.length];
		String[] postValues = new String[temp.length];
			for (int i = 0;i < temp.length;i++)
			{
				String[] temp1 = temp[i].split("[=]", -1);
				postNames[i] = temp1[0];
				postValues[i] = temp1[1];
			}

			post = new Postcondition(postNames,postValues);
		}
	public void addPrecondition(Precondition p) {
		Preconditions.add(p);
	}
	public void addResourcePrecondition(Precondition p) {
		p.setCritical(false);
		Preconditions.add(p);
	}
	public boolean checkPreconditions(Beliefs b) {
		boolean ver = true;
		for (int i = 0;i < Preconditions.size();i++)
			{
				if (Preconditions.get(i).checkPrecondition(b) != true)
				{
					ver = false;
					break;
				}
			}
		return ver;
	}
	public boolean checkPreconditionsNotInPostConditions(Beliefs b) {
		boolean ver = true;
		for (int i = 0;i < Preconditions.size();i++)
		{
			Precondition temp = new Precondition(Preconditions.get(i).getBelsToCheck(),Preconditions.get(i).getFunction());
			for (int j = 0;j < temp.getBelsToCheck().length;j++)
			{
				if (this.post.isInPostcondition(temp.getBelsToCheck()[j]))
				{
					temp.getBelsToCheck()[j] = "ignore";
				}
			}
			if (temp.checkPrecondition(b) != true)
			{
				ver = false;
				break;
			}

		}
	return ver;
	}
	
	public int getActID() {
		return actID;
	}

	public void setActID(int actID) {
		this.actID = actID;
	}

	public void checkAction(Object bNode, ArrayList<Beliefs> newBel, ArrayList<Action> newActions) {
		// TODO Auto-generated method stub
		
	}
	@FunctionalInterface
	public interface PreconditionDelegate
	{
		boolean invoke(java.util.ArrayList<String> l);
	}
	@FunctionalInterface
	public interface PostconditionDelegate
	{
		void invoke(java.util.ArrayList<String> olds, java.util.ArrayList<String> news);
	}
	@FunctionalInterface
	public interface varPropertydelegate
	{
		boolean invoke(String variable,java.util.ArrayList<String> pars);
	}
	@FunctionalInterface
	public interface actionImplementation
	{
		java.util.Iterator invoke(String[] pars, Action a);
	}

}
