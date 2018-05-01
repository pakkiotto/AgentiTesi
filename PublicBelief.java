public class PublicBelief extends Belief
{
	private String Visibility;
	private String[] VisibleTo;

	public PublicBelief(String n, String[] d, String v, String vis){
		super(n,d,v);
		this.setVisibility(vis);
		this.setVisibleTo(new String[0]);
	}
	public PublicBelief(String n, String[] d, String v, String vis, String[] visTo){
		super(n,d,v);
		this.setVisibility(vis);
		this.setVisibleTo(visTo);
	}
	public String getVisibility() {
		return Visibility;
	}
	public void setVisibility(String visibility) {
		Visibility = visibility;
	}
	public String[] getVisibleTo() {
		return VisibleTo;
	}
	public void setVisibleTo(String[] visibleTo) {
		VisibleTo = visibleTo;
	}
}
