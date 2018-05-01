import java.util.*;

public class Dictionary{

	public ArrayList <Map.Entry<String,String[]>> domini = new ArrayList<Map.Entry<String, String[]>>();

	public void addDominio(String name, String[] values) {
		domini.add(new  AbstractMap.SimpleEntry<String, String[]>(name,values));
	}
	public String[] getDominio(String name) {
		for (int i=0;i <domini.size();i++)
		{
			if (name.equals(domini.get(i).getKey()))
			{
				return domini.get(i).getValue();
			}
		}
		return null;
	}
}
