import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TranslatorTA {
	
	private int currmaxID = 0;
	public ArrayList <AutomaState> StateList = new ArrayList<AutomaState>();
	public ArrayList<AutomaTransition> TransitionList = new ArrayList<AutomaTransition>();
	private Agent agent;
	
	public TranslatorTA(Agent a) {
		this.agent = a;
	}
	public boolean CreateList(Beliefs currentBels) {
		int stateid = 0;
		
		if ((new File("C:/Users/Carmi/Desktop/Exchange/" + agent.getAgentName() + ".xml")).isFile())	{
			ArrayList<AutomaState> tempList = RecreateList();
			stateid = currmaxID + 1;
				//Beliefs actualBels=ActualAgentBeliefs(currentBels);
			AutomaState root = new AutomaState(ActualAgentBeliefs(currentBels),stateid);
			stateid++;
			StateList.add(root);
			LinkedList queue = new LinkedList();
			queue.offer(root);
			ArrayList <Action> possibleActions = agent.getAgentActions();
			AutomaState currentNode;
			
			while (!queue.isEmpty()) {
				currentNode = (AutomaState)queue.poll();

				for (int i = 0;i < possibleActions.size();i++) {
					ArrayList<Beliefs> newbel = new ArrayList<Beliefs>();
					ArrayList<Action> newactions = new ArrayList<Action>();
					possibleActions.get(i).checkAction(currentNode.stateBeliefs,newbel,newactions);
						if (!newbel.isEmpty()){
							for (int j = 0;j < newbel.size();j++) {
								AutomaState newState = new AutomaState(newbel.get(j),stateid);
								stateid++;
								String resp = isStateInList(newState,tempList);
								if (resp == null) {
									AutomaTransition newTransition = new AutomaTransition(newactions.get(j),currentNode,newState);
									TransitionList.add(newTransition);
									StateList.add(newState);
									tempList.add(newState);
									queue.offer(newState);
								}
								else{
									AutomaState other = SearchStateByID(resp,tempList);
									AutomaTransition newTransition = new AutomaTransition(newactions.get(j),currentNode,other);
									TransitionList.add(newTransition);
								}
							}
						}
					}
				}
			return true;
			}
			else {
				AutomaState root = new AutomaState(ActualAgentBeliefs(currentBels),stateid);
				stateid++;
				StateList.add(root);
				LinkedList queue = new LinkedList();
				queue.offer(root);
				ArrayList <Action> possibleActions = agent.getAgentActions();
				AutomaState currentNode;
			while (!queue.isEmpty()){
				currentNode = (AutomaState)queue.poll();
					for (int i = 0;i < possibleActions.size();i++){
						ArrayList<Beliefs> newbel = new ArrayList<Beliefs>();
						ArrayList<Action> newactions = new ArrayList<Action>();
						possibleActions.get(i).checkAction(currentNode.stateBeliefs,newbel,newactions);
						if (!newbel.isEmpty())
						{
							for (int j = 0;j < newbel.size();j++)
							{
								//SearchTreeNode newNode=new SearchTreeNode(currentNode,newbel[j],newactions[j]);
							AutomaState newState = new AutomaState(newbel.get(j),stateid);
							stateid++;
								/**if(!newNode.isEqualAncient()){
								*/

								//	queue.Enqueue(newNode);
								//}
							String resp = isStateInList(newState,StateList);
							if (resp == null)
							{
								AutomaTransition newTransition = new AutomaTransition(newactions.get(j),currentNode,newState);
								TransitionList.add(newTransition);
								StateList.add(newState);
								queue.offer(newState);
							}
							else
							{

								AutomaState other = SearchStateByID(resp,StateList);
								AutomaTransition newTransition = new AutomaTransition(newactions.get(j),currentNode,other);
								TransitionList.add(newTransition);
							}
						}
					}
				}
			}
			return false;
		}

}
	public final Beliefs ActualAgentBeliefs(Beliefs b) {
		Beliefs ret = new Beliefs();
		for (int i = 0;i < b.getBels().size();i++)
		{
			if (b.getBels().get(i) instanceof PrivateBelief)
			{
				ret.addPrivateBelief(b.getBels().get(i).getName(),b.getBels().get(i).getDomain(),b.getBels().get(i).getbValue());
			}
			else
			{
				switch ((b.getBels().get(i) instanceof PublicBelief ? (PublicBelief)b.getBels().get(i) : null).getVisibility())
					{
						case "PROACTIVEAGENTS":
								if (agent.getType().equals("ProactiveAgent"))
								{
									ret.addPublicBelief(b.getBels().get(i).getName(),b.getBels().get(i).getDomain(),b.getBels().get(i).getbValue(),"PROACTIVEAGENTS");
								}
								break;
						case "RESOURCEAGENTS":
							if (agent.getType().equals("ResourceAgent"))
							{
								ret.addPublicBelief(b.getBels().get(i).getName(),b.getBels().get(i).getDomain(),b.getBels().get(i).getbValue(),"RESOURCEAGENTS");
							}
							break;

						case "ALL":
							ret.addPublicBelief(b.getBels().get(i).getName(),b.getBels().get(i).getDomain(),b.getBels().get(i).getbValue(),"ALL");
							break;

						case "ONLYTOAGENTS":
							String[] temp = (b.getBels().get(i) instanceof PublicBelief ? (PublicBelief)b.getBels().get(i) : null).getVisibleTo();
							boolean ver = false;
							for (String s : temp)
							{
								if (s.equals(agent.getAgentName()))
								{
									ver = true;
									break;	
								}
							}
							if (ver == true)
							{
								ret.addPublicBelief(b.getBels().get(i).getName(),b.getBels().get(i).getDomain(),b.getBels().get(i).getbValue(),"ONLYTOAGENTS",(b.getBels().get(i) instanceof PublicBelief ? (PublicBelief)b.getBels().get(i) : null).getVisibleTo());
							}
							break;
						}
					}
				}
			return ret;
	}
	public final void createAutoma(Beliefs b) {
		boolean ver = CreateList(b);
		if (ver)
		{
			UpdateXMLFile();
		}
		else
		{
			this.CreateXMLFile();
		}
	}
	public final void CreateXMLFile() {
	 	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder dBuilder;
    //Beliefs b1 = new Beliefs();
    //AutomaState s1 = new AutomaState(b1,1);
    //StateList.add(s1);
    try {
        dBuilder = dbFactory.newDocumentBuilder();
        Document automa = dBuilder.newDocument();
        //add elements to Document
        Element rootElement =
            automa.createElement("nta");
        //append root element to document
        automa.appendChild(rootElement);
        Element globalDeclarations = automa.createElement("declaration");//CreateNode("element","declaration","");
		Element template = automa.createElement("template");
		Element system = automa.createElement("system");
		system.setTextContent(agent.getAgentName() + "Process" + "=" + agent.getAgentName() + "_automa()" + ";" + "system " + agent.getAgentName() + "Process;");
		rootElement.appendChild(globalDeclarations);
		rootElement.appendChild(template);
		rootElement.appendChild(system);
		
		Element templateName=automa.createElement("name");
		templateName.setTextContent(agent.getAgentName()+"_automa");
		Element localDeclarations=automa.createElement ("declaration");
		template.appendChild(templateName);
		template.appendChild(localDeclarations);
		for (int i = 0;i < StateList.size();i++)
		{

			Element loc = automa.createElement("location");
			(loc instanceof Element ? (Element)loc : null).setAttribute("id",StateList.get(i).myID);
			Element locname = automa.createElement("name");
			locname.setTextContent(StateList.get(i).myID);
			Element comment = automa.createElement("label");
			(comment instanceof Element ? (Element)comment : null).setAttribute("kind","comments");
			comment.setTextContent( StateList.get(i).stringBeliefs) ;
			template.appendChild(loc);
			loc.appendChild(locname);
			loc.appendChild(comment);
		}
		
		for (int i = 0;i < TransitionList.size();i++)
		{


			Element trans = automa.createElement("transition");
			Element from = automa.createElement("source");
			(from instanceof Element ? (Element)from : null).setAttribute("ref",TransitionList.get(i).sourceID);
			Element to = automa.createElement("target");
			(to instanceof Element ? (Element)to : null).setAttribute("ref",TransitionList.get(i).targetID);
			Element comment = automa.createElement("label");
			(comment instanceof Element ? (Element)comment : null).setAttribute("kind","comments");
			comment.setTextContent(TransitionList.get(i).actionDescription);

			Element guard = automa.createElement("label");
			(guard instanceof Element ? (Element)guard : null).setAttribute("kind","guard");

			String guardText = "";

			MyNotationConverter mync = new MyNotationConverter();
			
			for (int j = 0;j < TransitionList.get(i).transitionAction.getPreconditions().size();j++)
			{



				if (j == TransitionList.get(i).transitionAction.getPreconditions().size() - 1)
				{


					guardText = guardText + "(" + mync.toUppaalGuard(TransitionList.get(i).transitionAction.getPreconditions().get(j).getStringPrecondition()) + ")";


				}
				else
				{

					guardText = "(" + guardText + mync.toUppaalGuard(TransitionList.get(i).transitionAction.getPreconditions().get(j).getStringPrecondition()) + ")" + "&&";

				}




			}
			//*/
			guard.setTextContent(guardText);



			Element updates = automa.createElement("label");


			(updates instanceof Element ? (Element)updates : null).setAttribute("kind","assignment");

			String[] BeliefsToChangeTranslated = new String[TransitionList.get(i).transitionAction.getPost().getBeliefNames().length];
			String[] newValuesTranslated = new String[TransitionList.get(i).transitionAction.getPost().getBeliefNames().length];
			Beliefs belsToTranslate = TransitionList.get(i).transitionAction.getPost().ApplyPostCondition(TransitionList.get(i).source.stateBeliefs);

			for (int j = 0;j < BeliefsToChangeTranslated.length;j++)
			{

				String tmp = TransitionList.get(i).transitionAction.getPost().getBeliefNames()[j];
				tmp = tmp.replace("(","_");
				tmp = tmp.replace(")","_");
				tmp = tmp.replace(",","_");
				BeliefsToChangeTranslated[j] = tmp;

				tmp = belsToTranslate.getBeliefValue(TransitionList.get(i).transitionAction.getPost().getBeliefNames()[j]);

				if (tmp.equals("true") || tmp.equals("false"))
				{


					tmp = "_" + tmp;

				}


				newValuesTranslated[j] = tmp;


			}

			String updateValue = "";


			for (int j = 0;j < BeliefsToChangeTranslated.length;j++)
			{


				if (j == BeliefsToChangeTranslated.length - 1)
				{


					updateValue = updateValue + BeliefsToChangeTranslated[j] + "=" + newValuesTranslated[j];


				}
				else
				{

					updateValue = updateValue + BeliefsToChangeTranslated[j] + "=" + newValuesTranslated[j] + ",";

				}








			}





			updates.setTextContent(updateValue);



			template.appendChild(trans);
			trans.appendChild(from);
			trans.appendChild(to);
			trans.appendChild(comment);
			trans.appendChild(guard);
			trans.appendChild(updates);

		}

		//automa.Save("C:/Users/Dario/Desktop/Exchange/" + agent.agentName + ".xml");	
		//for output to file, console
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        //for pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(automa);

        //write to console or file
        StreamResult console = new StreamResult(System.out);
        StreamResult file = new StreamResult(new File("/Users/Carmi/Desktop/WorkspaceTesi/Agent/" + agent.getAgentName() + ".xml"));//Users\carmi\Desktop\WorkspaceTesi\Agent

        //write data
        transformer.transform(source, console);
        transformer.transform(source, file);
        //System.out.println("DONE");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
	public final String isStateInList(AutomaState a, ArrayList<AutomaState> lista) {
		for (int i = 0;i < lista.size();i++)
		{
			if (a.stateBeliefs.compareBeliefs(lista.get(i).stateBeliefs) == true)
			{
				return lista.get(i).myID;
			}
		}
				return null;
	}
	public final ArrayList<AutomaState> RecreateList() {
		int stateid = 0;
		ArrayList<AutomaState> retList = new ArrayList<AutomaState>();
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document automa = dBuilder.parse("/Users/Carmi/Desktop/WorkspaceTesi/Agent/" + agent.getAgentName() + ".xml");
			automa.getDocumentElement().normalize();
			NodeList states = automa.getElementsByTagName("location");
			NodeList states2 = automa.getElementsByTagName("label");
			//Node thisNode = eventList.item(i);
			//Element section = (Element) states.item(i);
			// System.out.println("il nodo è " + section.getFirstChild());
			    for (int j = 0;j < states.getLength();j++)
			    {
			    	String temp = (states.item(j) instanceof Element ? (Element)states.item(j) : null).getAttribute("id");
					String[] temp1 = temp.split("[d]", -1);
					stateid = Integer.parseInt(temp1[1]);
					System.out.println("currmax = " + currmaxID);
					System.out.println("id numero  = " + stateid);
					
					if (stateid > currmaxID)
					{
						//System.out.println("sono entrato qui");
						//System.out.println("allora ID = " + stateid + " e invece currmax = " + currmaxID);
						currmaxID = stateid;
					}
					
					Beliefs ret = new Beliefs();
					String rep = (states2.item(j) instanceof Element ? (Element)states2.item(j) : null).getTextContent();
					//String text = (states.item(j) instanceof Element ? (Element)states.item(j): null).getElementsByTagName("label");
					//System.out.println(rep);
					String[] tempbel = rep.split("[;]", -1);
				for (int i1 = 0;i1 < tempbel.length;i1++)
				{

					String [] t1 = tempbel[i1].split("[=]", -1);
					//System.out.println("questa è t1 di zero = " + t1[0]);
					//System.out.println("questa è t1 di uno = " + t1[1]);
					ret.addBelief(t1[0],t1[1]);
				}
					retList.add(new AutomaState(ret,stateid));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    		return retList;
	}
	public final AutomaState SearchStateByID(String id, ArrayList<AutomaState> list) {
		for (int i = 0;i < list.size();i++)
		{
			if (id.equals(list.get(i).myID))
			{
				return list.get(i);
			}
		}
		return null;
	}
	public final void UpdateXMLFile(){
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document automa = dBuilder.parse("/Users/Carmi/Desktop/WorkspaceTesi/Agent/" + agent.getAgentName() + ".xml");
			automa.getDocumentElement().normalize();
			NodeList template = automa.getElementsByTagName("template");
			
			for (int i = 0;i < StateList.size();i++)
			{

				NodeList lastlist = (template instanceof Element ? (Element)template : null).getElementsByTagName("location");
				Element last = (Element) lastlist.item(lastlist.getLength() - 1);

				Element loc = automa.createElement("location");
				(loc instanceof Element ? (Element)loc : null).setAttribute("id",StateList.get(i).myID);
				Element locname = automa.createElement("name");
				locname.setTextContent(StateList.get(i).myID);
				Element comment = automa.createElement("label");
				(comment instanceof Element ? (Element)comment : null).setAttribute("kind","comments");
				comment.setTextContent(StateList.get(i).stringBeliefs);
				Node ciccio = template.item(i);
				Element cicc = (Element) ciccio;
				//template.insertAfter(loc,last);
				loc.appendChild(locname);
				loc.appendChild(comment);
			}
			for (int i = 0;i < TransitionList.size();i++)
			{


				Element trans = automa.createElement("transition");
				Element from = automa.createElement("source");
				(from instanceof Element ? (Element)from : null).setAttribute("ref",TransitionList.get(i).sourceID);
				Element to = automa.createElement("target");
				(to instanceof Element ? (Element)to : null).setAttribute("ref",TransitionList.get(i).targetID);
				Element comment = automa.createElement("label");
				(comment instanceof Element ? (Element)comment : null).setAttribute("kind","comments");
				comment.setTextContent(TransitionList.get(i).actionDescription);

				Element guard = automa.createElement("label");
				(guard instanceof Element ? (Element)guard : null).setAttribute("kind","guard");

				String guardText = "";

				MyNotationConverter mync = new MyNotationConverter();
				
				for (int j = 0;j < TransitionList.get(i).transitionAction.getPreconditions().size();j++)
				{



					if (j == TransitionList.get(i).transitionAction.getPreconditions().size() - 1)
					{


						guardText = guardText + "(" + mync.toUppaalGuard(TransitionList.get(i).transitionAction.getPreconditions().get(j).getStringPrecondition()) + ")";


					}
					else
					{

						guardText = "(" + guardText + mync.toUppaalGuard(TransitionList.get(i).transitionAction.getPreconditions().get(j).getStringPrecondition()) + ")" + "&&";

					}




				}
				//*/
				guard.setTextContent(guardText);



				Element updates = automa.createElement("label");


				(updates instanceof Element ? (Element)updates : null).setAttribute("kind","assignment");

				String[] BeliefsToChangeTranslated = new String[TransitionList.get(i).transitionAction.getPost().getBeliefNames().length];
				String[] newValuesTranslated = new String[TransitionList.get(i).transitionAction.getPost().getBeliefNames().length];
				Beliefs belsToTranslate = TransitionList.get(i).transitionAction.getPost().ApplyPostCondition(TransitionList.get(i).source.stateBeliefs);

				for (int j = 0;j < BeliefsToChangeTranslated.length;j++)
				{

					String tmp = TransitionList.get(i).transitionAction.getPost().getBeliefNames()[j];
					tmp = tmp.replace("(","_");
					tmp = tmp.replace(")","_");
					tmp = tmp.replace(",","_");
					BeliefsToChangeTranslated[j] = tmp;

					tmp = belsToTranslate.getBeliefValue(TransitionList.get(i).transitionAction.getPost().getBeliefNames()[j]);

					if (tmp.equals("true") || tmp.equals("false"))
					{


						tmp = "_" + tmp;

					}


					newValuesTranslated[j] = tmp;


				}

				String updateValue = "";


				for (int j = 0;j < BeliefsToChangeTranslated.length;j++)
				{


					if (j == BeliefsToChangeTranslated.length - 1)
					{


						updateValue = updateValue + BeliefsToChangeTranslated[j] + "=" + newValuesTranslated[j];


					}
					else
					{

						updateValue = updateValue + BeliefsToChangeTranslated[j] + "=" + newValuesTranslated[j] + ",";

					}








				}





				updates.setTextContent( updateValue )  ;



				//template.appendChild(trans);
				trans.appendChild(from);
				trans.appendChild(to);
				trans.appendChild(comment);
				trans.appendChild(guard);
				trans.appendChild(updates);

			}

			//automa.Save("C:/Users/Dario/Desktop/Exchange/" + agent.agentName + ".xml");
			//automa.Save("C:/Users/Dario/Desktop/Exchange/" + agent.agentName + ".xml");	
					//for output to file, console
			        TransformerFactory transformerFactory = TransformerFactory.newInstance();
			        Transformer transformer = transformerFactory.newTransformer();
			        //for pretty print
			        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			        DOMSource source = new DOMSource(automa);

			        //write to console or file
			       // StreamResult console = new StreamResult(System.out);
			        StreamResult file = new StreamResult(new File("/Users/Carmi/Desktop/WorkspaceTesi/Agent/" + agent.getAgentName() + ".xml"));//Users\carmi\Desktop\WorkspaceTesi\Agent

			        //write data
			       // transformer.transform(source, console);
			        transformer.transform(source, file);
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}











	
		
		
		
	}
	public int getCurrmaxID() {
		return currmaxID;
	}
	public void setCurrmaxID(int currmaxID) {
		this.currmaxID = currmaxID;
	}
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

}
