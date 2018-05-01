import java.util.ArrayList;

public class Beliefs {
	
		private ArrayList<Belief> bels = new ArrayList<Belief>();
		private Agent myAgent;

		
		public Beliefs(Agent a){
			this.setMyAgent(a);
		}

		public Beliefs(){}

		public void addBelief(String n, String[] d, String v){
			getBels().add(new Belief(n,d,v));
		}

		public void addBelief(String n, String v){
			getBels().add(new Belief(n,v));
		}

		public Agent getMyAgent() {
			return myAgent;
		}

		public void setMyAgent(Agent myAgent) {
			this.myAgent = myAgent;
		}
		
		public void addPublicBelief(String n, String[] d, String v, String vis){
			getBels().add(new PublicBelief(n,d,v,vis));
		}
		public void addPublicBelief(String n, String[] d, String v, String vis, String[] visTo){
			getBels().add(new PublicBelief(n,d,v,vis,visTo));
		}
		public void addPrivateBelief(String n, String[] d, String v){
			getBels().add(new PrivateBelief(n,d,v));
		}
		public void addBelief(Belief b){
			getBels().add(b);
		}
		public ArrayList<Belief> myBeliefs(){
			return getBels();
		}
		public Belief getBelief(String n){
			for (Belief bel : getBels())
			{
				if (n.equals(bel.getName())){
					return bel;
				}
			}
			return null;
		}
		public String getBeliefValue(String n){
			for (Belief bel : getBels())
			{
				if (n.equals(bel.getName())){
					return bel.getbValue();
				}
			}
			return null;
		}
		public void changeBeliefValue(String beliefName, String newValue){
			this.getBelief(beliefName).changeValue(newValue);
		}/*
		public boolean isInBeliefs(Beliefs b){
			for (Belief bel : bels)
			{
				Belief temp = this.getBelief(bel.getName());
				if (temp != null)
				{
					if (!(temp.getbValue() == bel.getbValue()))
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
		}*/
		public boolean isInBeliefs(Beliefs b){
			for (int i = 0;i < b.getBels().size();i++)
			{
				Belief temp = this.getBelief(b.getBels().get(i).getName());
				if (temp != null)
				{
					if (!(temp.getbValue() == b.getBels().get(i).getbValue()))
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
		public boolean compareBeliefs(Beliefs b){
			boolean ver = true;
				if (b.getBels().size() == this.getBels().size())
				{
				for (int i = 0;i < b.getBels().size();i++)
					{
						Belief temp = b.getBelief(this.getBels().get(i).getName());
						if (temp != null)
						{
							if (temp.getbValue() == this.getBels().get(i).getbValue())
							{
								ver = true;
							}
							else
								{
									ver = false;
									break;
								}
						}
						else
							{
								ver = false;
								break;
							}
					}
				}
				else
					{
						ver = false;
					}
						
				
			return ver;
		}
		public  String stringRepresentation(Beliefs b){
				String resp = "";

					for (int i = 0;i < b.getBels().size();i++)
					{
						if (i != b.getBels().size() - 1){
							resp = resp + b.getBels().get(i).getName() + "=" + b.getBels().get(i).getbValue() + ";";
						}
						else{
							resp = resp + b.getBels().get(i).getName() + "=" + b.getBels().get(i).getbValue();
						}
					}
					return resp;

		}
		public 	Beliefs translateStringRepresentation(String rep){
			Beliefs ret = new Beliefs();
			String[] tempbel = rep.split("[;]", -1);
				
				for (int i = 0;i < tempbel.length;i++){
					String [] t1 = tempbel[i].split("[=]", -1);
					ret.addBelief(t1[0],t1[1]);
				}
					return ret;
		}

		public ArrayList<Belief> getBels() {
			return bels;
		}

		public void setBels(ArrayList<Belief> bels) {
			this.bels = bels;
		}
}







