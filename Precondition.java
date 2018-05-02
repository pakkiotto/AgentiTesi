import java.util.ArrayList;

public class Precondition {
		
	private String[] belsToCheck;
	private boolean isCritical = true;
	private Func1Param<ArrayList<String>,Boolean> function;
	private String stringPrecondition = "";
	
	public Precondition(String[] belsToCheck2, Func1Param<ArrayList<String>, Boolean> function2) {
		// TODO Auto-generated constructor stub
	}
	public boolean checkPrecondition(Beliefs b) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isCritical() {
		return isCritical;
	}
	public void setCritical(boolean isCritical) {
		this.isCritical = isCritical;
	}
	public String[] getBelsToCheck() {
		return belsToCheck;
	}
	public void setBelsToCheck(String[] belsToCheck) {
		this.belsToCheck = belsToCheck;
	}
	public Func1Param<ArrayList<String>,Boolean> getFunction() {
		return function;
	}
	public void setFunction(Func1Param<ArrayList<String>,Boolean> function) {
		this.function = function;
	}
	public String getStringPrecondition() {
		return stringPrecondition;
	}
	public void setStringPrecondition(String stringPrecondition) {
		this.stringPrecondition = stringPrecondition;
	}
	@FunctionalInterface
	public interface Func1Param<T, TResult>
	{
		TResult invoke(T t);
	}

}
