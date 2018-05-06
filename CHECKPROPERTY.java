import java.util.ArrayList;

public class CHECKPROPERTY {
	public static boolean ISLESSTHAN(String v, ArrayList<String> par) {
		int i = Integer.parseInt(par.get(0));
		int j = Integer.parseInt(par.get(1));
		if (i < j)
		{
			return true;
		}
		else
		{
			return false;
		}

	}
	public static boolean ISLESSEQUALTHAN(String v, ArrayList<String> par) {
		int i = Integer.parseInt(par.get(0));
		int j = Integer.parseInt(par.get(1));

		if (i <= j)
		{
			return true;
		}
		else
		{
			return false;
		}

	}
	public static boolean ISEQUAL(String v, ArrayList<String> par) {
		if (par.get(0).equals(par.get(1)))
		{
			return true;
		}
		else
		{
			return false;
		}

	}
}
