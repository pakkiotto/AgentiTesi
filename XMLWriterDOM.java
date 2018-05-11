
/*public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Beliefs beliefs1 = new Beliefs();
		
		beliefs1.addBelief("cucciolo", "piccolo");
		beliefs1.addBelief("amre", "piccolo");
		beliefs1.addBelief("nabbo", "piccolo");
		beliefs1.addBelief("fortnite", "piccolo");
		
		Belief belief1, belief2;
		
		belief1 = beliefs1.getBelief("fortnite");
		//belief2 = beliefs1.getBeliefValue("piccolo");
		
		String name = belief1.getName();
		String value = beliefs1.getBeliefValue("amre");
		beliefs1.changeBeliefValue("amre", "lacasadicarta");
		String value2 = beliefs1.getBeliefValue("amre");
		System.out.println("il val del belief2 e= "+value);
		System.out.println("il nuovo val del belief2 e= "+value2);*/


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLWriterDOM {
	
	private static int currmaxID = 0;
	public static ArrayList <AutomaState> StateList = new ArrayList<AutomaState>();
	public static ArrayList<AutomaTransition> TransitionList = new ArrayList<AutomaTransition>();
	private static Agent agent = new Agent("mimmo2");
	

    public static void main(String[] args) {
    	int stateid = 0;
		ArrayList<AutomaState> retList = new ArrayList<AutomaState>();
		
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



			template.appendChild(trans);
			trans.appendChild(from);
			trans.appendChild(to);
			trans.appendChild(comment);
			trans.appendChild(guard);
			trans.appendChild(updates);

		}

		automa.Save("C:/Users/Dario/Desktop/Exchange/" + agent.agentName + ".xml");












	}





}



		
		/*
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
					//System.out.println("currmax = " + currmaxID);
					//System.out.println("id numero  = " + stateid);
					
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
					System.out.println(ret.getBels().get(i1).getName());
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
		
    		System.out.println();
	}
    */

    }
    
     /*  
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder dBuilder;
        Beliefs b1 = new Beliefs();
        AutomaState s1 = new AutomaState(b1,1);
        StateList.add(s1);
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
    	/*
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
            //test lettura file da path
            Document automa2 = dBuilder.parse("/Users/Carmi/Desktop/WorkspaceTesi/Agent/" + agent.getAgentName() + ".xml");
         
            DOMSource source2 = new DOMSource(automa);
            System.out.println("Source2");
            //transformer.transform(source2,console);
            automa2.getDocumentElement().normalize();
            System.out.println("Root element :" + automa2.getDocumentElement().getNodeName());
            //NodeList states = automa2.getElementsByTagName("location");
            NodeList states = automa2.getElementsByTagName("location");
            NodeList states2 = automa2.getElementsByTagName("label");

            for (int i=0; i< states.getLength(); i++) {
                //Node thisNode = eventList.item(i);
                Element section = (Element) states.item(i);
               // System.out.println("il nodo è " + section.getFirstChild());
                currmaxID = 0;
                for (int j = 0;j < states.getLength();j++)
        		{
                	
        			String temp = (states.item(j) instanceof Element ? (Element)states.item(j) : null).getAttribute("id");
        			String[] temp1 = temp.split("[d]", -1);
        			int stateid = Integer.parseInt(temp1[1]);
        			System.out.println("currmax = " + currmaxID);
        			System.out.println("id numero  = " + stateid);
        			
        			if (stateid > currmaxID)
        			{
        				System.out.println("sono entrato qui");
        				System.out.println("allora ID = " + stateid + " e invece currmax = " + currmaxID);
        				currmaxID = stateid;

        			}
        			
        			Beliefs ret = new Beliefs();
        			String rep = (states2.item(j) instanceof Element ? (Element)states2.item(j) : null).getTextContent();
        			//String text = (states.item(j) instanceof Element ? (Element)states.item(j): null).getElementsByTagName("label");
        			System.out.println(rep);

        		String[] tempbel = rep.split("[;]", -1);

        		for (int i1 = 0;i1 < tempbel.length;i1++)
        		{

        			String [] t1 = tempbel[i1].split("[=]", -1);
        			System.out.println("questa è t1 di zero = " + t1[0]);
        			System.out.println("questa è t1 di uno = " + t1[1]);
        			ret.addBelief(t1[0],t1[1]);



        		}


        			//retList.add(new AutomaState(ret,stateid));



        		}

        		//return retList;

        }

            //NodeList states = ((tempVar instanceof XmlElement ? (XmlElement)tempVar : null).GetElementsByTagName("template")[0] instanceof XmlElement ? (XmlElement)(tempVar instanceof XmlElement ? (XmlElement)tempVar : null).GetElementsByTagName("template")[0] : null).GetElementsByTagName("location");
           // for (int temp = 0; temp < states.getLength(); temp++) {
             //   Node nNode = states.item(temp);
               // System.out.println("\nCurrent Element :" + nNode.getNodeName());          
            
            //write data
            //transformer.transform(source, console);
            //transformer.transform(source, file);
            System.out.println("DONE");

        
                
            } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
    

/*
    private static Node getEmployee(Document doc, String id, String name, String age, String role,
            String gender) {
        Element employee = doc.createElement("Employee");

        //set id attribute
        employee.setAttribute("id", id);

        //create name element
        employee.appendChild(getEmployeeElements(doc, employee, "name", name));

        //create age element
        employee.appendChild(getEmployeeElements(doc, employee, "age", age));

        //create role element
        employee.appendChild(getEmployeeElements(doc, employee, "role", role));

        //create gender element
        employee.appendChild(getEmployeeElements(doc, employee, "gender", gender));

        return employee;
    }


    //utility method to create text node
    private static Node getEmployeeElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
    */
