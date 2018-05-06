import java.util.*;

public class MyNotationConverter {
	
	public ArrayList <String> beliefsName = new ArrayList<String>();

	public final String toPrefixNotation(String s) {
		int i0 = 0;
		int pendent = 0;
		int pending = 0;

		while (i0 < s.length())
		{
			String isop = RecognizeOperator(s,i0);
			if (isop != null)
			{
				if (pendent > 0)
				{
					i0 = i0 + isop.length();
				}
				else
				{
					i0 = i0 + isop.length();
					pending++;
				}
			}
			else
			{
				switch (s.charAt(i0))
				{
					case '(':
								pendent++;
								i0++;
								break;
					case')':

								pendent--;
								i0++;
								if (pending > 0 && pendent == 0)
								{
									for (int i = 0;i < pending;i++)
									{
										StringBuffer b=new StringBuffer(s);
										b.insert(i0, ")");
										s = b.toString();
										s = "(" + s;
										i0 = i0 + 2;
									}
									pending = 0;
								}
								
								break;
					default:
								i0++;
								break;
				}
			}
		}
		
		int i1 = 0;
		int i2 = 0;
		Stack st = new Stack();
		String newrep = "";
		while (i1 < s.length())
		{
			char analyzing = s.charAt(i1);
			if (analyzing == '(')
			{ //parentesi aperta
				st.push("%" + String.valueOf(i2));
				i1++;	
			}
			else
			{
				String temp = RecognizeOperator(s,i1);
				if (temp != null)
				{ //operatore
					switch (temp)
					{
						case "==":
									st.push("F.EQUAL");
									i1 = i1 + 2;
									break;
									
						case ">":
									st.push("F.GREATER");
									i1++;
									break;
						
						case "<":
									st.push("F.LESS");
									i1++;
									break;

						case"<=":
									st.push("F.LESSEQUAL");
									i1 = i1 + 2;
									break;
						
						case ">=":
									st.push("F.GREATEREQUAL");
									i1 = i1 + 2;
									break;

						case "&&":
									st.push("F.AND");
									i1 = i1 + 2;
									break;

						case "||":
									st.push("F.OR");
									i1 = i1 + 2;
									break;
						
						case "!":
									st.push("F.NOT");	
									i1++;
									break;
						
						case"!=":
									st.push("F.NOTEQUAL");
									i1 = i1 + 2;
									break;
					}
				}
				else
				{
					if (s.charAt(i1) == ')')
					{ //parentesi chiusa
						String toappend = "";
						String totalappend = "";
						int appindex = 0;
						newrep = newrep + "),";

						while (!toappend.startsWith("%"))
						{
							toappend = (String)st.pop();
							if (!toappend.startsWith("%"))
							{
							totalappend = totalappend + toappend;
							}
							else
							{
								totalappend = totalappend + "(";
								String[] t = toappend.split("[%]", -1);
								appindex = Integer.parseInt(t[1]);
							}

						}
						StringBuffer b2 =new StringBuffer(newrep);
						b2.insert(i0, ")");
						newrep = b2.toString();
						i2 = newrep.length();
						i1++;
					}
					else
					{ //operando

						StringBuffer b3 =new StringBuffer(s);
						b3.delete(0,i1);
						String temp2 = b3.toString();
						//String temp2 = s.Remove(0,i1);
						String op = RecognizeOperand(temp2,false);

						newrep = newrep + op + ",";
						i1 = i1 + op.length();
						if (!beliefsName.contains(op))
						{
						beliefsName.add(op);
						}
					}
				}
			}
		}
		if (newrep.endsWith(","))
		{	
			StringBuffer b4 =new StringBuffer(newrep);
			b4.deleteCharAt(newrep.length() - 1);
			newrep = b4.toString();
			//newrep = newrep.Remove(newrep.getLength() - 1);
		}
		for (int i = 0;i < newrep.length();i++)
		{
			if (newrep.charAt(i) == ')')
			{
				if (newrep.charAt(i-1) == ',')
				{
					StringBuffer b3 =new StringBuffer(newrep);
					b3.delete(i-1,1);
					newrep = b3.toString();
					//newrep = newrep.Remove(i - 1,1);
				}
			}
		}
		ArrayList<String> tempbels = new ArrayList<String>();
		ArrayList<String> tempbels2 = new ArrayList<String>();
		for (int i = 0;i < beliefsName.size();i++)
		{
			int gnuk = 0;
			OutObject<Integer> tempOut_gnuk = new OutObject<Integer>();
			if (TryParseHelper.tryParseInt(beliefsName.get(i), tempOut_gnuk))
			{
				gnuk = tempOut_gnuk.argValue;
				tempbels.add("$" + beliefsName.get(i));
				tempbels2.add("$" + beliefsName.get(i));
			}
			else
			{
				gnuk = tempOut_gnuk.argValue;
				tempbels.add(beliefsName.get(i));
				tempbels2.add(beliefsName.get(i));
			}
		}
		String swap;
		for (int i = tempbels.size() - 1;i > 0;i--)
		{ //ordino per lunghezza
			for (int j = 0;j < i;j++)
			{
				if (tempbels.get(j).length() < tempbels.get(j + 1).length())
				{
					swap = tempbels.get(j);
					tempbels.set(j, tempbels.get(j + 1));
					tempbels.set(j + 1, swap);
				}
			}
		}
		for (int i = 0;i < tempbels.size();i++)
		{
			if (tempbels.get(i).startsWith("$"))
			{
					String gnek = StringHelper.remove(tempbels.get(i), 0,1);
					newrep = newrep.replace(gnek,tempbels.get(i));
			}
		}
		for (int i = 0;i < beliefsName.size();i++)
		{
			int ind = tempbels2.indexOf(tempbels.get(i));
			newrep = newrep.replace(tempbels.get(i),"l[" + ind + "]");
		}
		return newrep;
	}
	private static boolean RecognizeEndOperand(char c) {
		if (c == '=' || c == '&' || c == '|' || c == '<' || c == '>' || c == ')' || c == '!')
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private static String RecognizeOperator(String s, int i) {
		char c = s.charAt(i);
		if (c == '=' || c == '&' || c == '|' || c == '<' || c == '>' || c == '!')
		{
			if (c == '<' || c == '>')
			{
				if (s.charAt(i + 1) == '=')
				{
					char[] temp1 = new char[]{s.charAt(i), s.charAt(i + 1)};
					String temp2 = new String(temp1);
					return temp2;
				}
				else
				{
					if (c == '<')
					{
						return "<";
					}
					else
					{
						return ">";
					}
				}
			}
			else
			{
				if (s.charAt(i) == '!')
				{
					if (s.charAt(i + 1) == '=')
					{
						return "!=";
					}
					else
					{
						return "!";
					}
				}
				else
				{
					switch (s.charAt(i))
					{
					case '=':
							return "==";

					case '!':
							return "!";

					case '&':
							return "&&";

					case '|':
							return "||";

					case '<':
							return "<";

					case '>':
							return ">";
					}
				}

				return null;
			}
		}
		else
		{
			return null;
		}
	}
	public static boolean isOperand(char c) {
		if (c == '=' || c == '&' || c == '|' || c == '<' || c == '>' || c == '!' || c == '+' || c == '-' || c == '*' || c == '(' || c == ')')
		{
			return true;
		}
		
		return false;
	}
	public static String RecognizeOperand(String s, boolean HasArgument) {
		String temp = new String();
		boolean hasA = HasArgument;
		char exam = s.charAt(0);
		if (exam == '(')
		{			
			hasA = true;
		}
			if (RecognizeEndOperand(exam) == true)
			{
				if (HasArgument == true)
				{
					return ")";
				}
				else
				{
					return "";
				}
			}
			else
			{
				String newst = StringHelper.remove(s, 0,1);
				return s.charAt(0) + MyNotationConverter.RecognizeOperand(newst, hasA);
			}
	}
	public final String toUppaalGuard(String s) {
		int i1 = 0;
		String newrep = s;
		while (i1 < s.length())
		{
			if (isOperand(s.charAt(i1)))
			{
				i1++;
			}
			else
			{
				String temp2 = StringHelper.remove(s, 0,i1);
				String op = RecognizeOperand(temp2,false);
				i1 = i1 + op.length();
				if (!beliefsName.contains(op))
				{
					beliefsName.add(op);
				}
			}
		}
		for (int i = 0;i < beliefsName.size();i++)
		{
			if (beliefsName.get(i).contains("("))
			{
				String n = beliefsName.get(i).replace("(","_");
				n = n.replace(")","_");
				n = n.replace(",","_");
				newrep = newrep.replace(beliefsName.get(i),n);
				}
				else
				{
					int gnuk = 0;
					if ((beliefsName.get(i).equals("true")) || (beliefsName.get(i).equals("false")))
					{
						newrep = newrep.replace(beliefsName.get(i),"_" + beliefsName.get(i));
					}
				}
			}
			return newrep;
		}
}

