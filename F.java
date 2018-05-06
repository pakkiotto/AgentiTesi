
public class F {
	
	public static String PLUS(String a, String b) {
		int i = Integer.parseInt(a);
		int j = Integer.parseInt(b);
		return String.valueOf(i + j);
	}
	public static String MINUS(String a, String b) {
		int i = Integer.parseInt(a);
		int j = Integer.parseInt(b);
		return String.valueOf(i - j);
	}
	public static boolean NOTEQUAL(String a, String b) {
		if (a.equals("ignore") || b.equals("ignore"))
		{
			return true;
		}
			if (a.equals(b))
			{
				return false;
			}
			else
			{
				return true;
			}
	}
	public static boolean EQUAL(String a, String b) {
		if (a.equals("ignore") || b.equals("ignore"))
		{
			return true;
		}
			if (a.equals(b))
			{
				return true;
			}
			else
			{
				return false;
			}
	}
	public static boolean AND(boolean a, boolean b) {
		return a && b;
	}
	public static boolean OR(boolean a, boolean b) {
		return a || b;
	}
	public static boolean NOT(boolean a) {
		if (a)
		{
			return false;
		}
		else
		{
			return true;
		}

	}
	public static boolean LESS(String a, String b) {
		if (a.equals("ignore") || b.equals("ignore"))
		{
			return true;
		}

		int i = Integer.parseInt(a);
		int j = Integer.parseInt(b);

		if (i < j)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean GREATEREQUAL(String a, String b) {
		if (a.equals("ignore") || b.equals("ignore"))
		{
			return true;
		}

		int i = Integer.parseInt(a);
		int j = Integer.parseInt(b);

		if (i >= j)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}



