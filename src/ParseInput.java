import java.util.List;
import java.util.Scanner;
import java.lang.*;
import java.util.*;

import static java.lang.String.*;
import static java.util.Objects.requireNonNull;

public class ParseInput{
	
	private static Graph<String> graph = new Graph<>();	
	private static String Request = "";
	
	private static void setRequest(String req){
		Request = req;
	}
	private static String getRequest(){
		return Request;
	}

    public static void parseInput() {		
		int line = 0;
		Scanner sc = new Scanner(System.in);
        
        inputParsing:        
        while(sc.hasNextLine()) {
			line++; // Update line for error message information
			String strInput =sc.nextLine().trim();
			
			// Switch for command type in a if/else (Connection vs #id)
			if(strInput.startsWith("Connection:")){
				
				// Split string 
				String[] definition = strInput.split("[:]");
				if (definition.length != 2) {
					System.out.println("Connection string malformed line "+line+" -> "+strInput);
					continue inputParsing;
				}
				
				// Parse connections
				
				String[] connections = definition[1].split("[,]");
				
				// Populate graph with connections
				graph = populateConnections(line, connections);				
				
			}
			else if(strInput.startsWith("#")){
				// This a command, must parse and execute proper method
				String[] command = strInput.split("[:]");
				
				// Validate string (only one ":" char is allowed by problem definition)
				if (command.length != 2) {
					System.out.println("Command string malformed line "+line+" -> "+strInput);
					continue inputParsing;
				}
				
				
				setRequest(command[0]);
				
				// GET command type for all allowed commands				
				if(command[1].trim().startsWith("Find all conenctions from")){
					// get from, to and budget to acomplish the question
					String from;
					String to;
					String budget;
					
					String[] tmp;
					tmp = command[1].trim().split("from");
					tmp = tmp[1].split("to");
					from = tmp[0].trim();
					
					tmp = command[1].trim().split("to");
					tmp = tmp[1].split("below");
					to = tmp[0].trim();
					
					tmp = command[1].trim().split("below");
					tmp = tmp[1].split("Euros");
					budget = tmp[0].trim();
					
					// Execute method

					findAndPrintAllConnections (from, to, budget, 100);
					
				}
				else if(command[1].trim().startsWith("What is the price of the connection")){
					String tripPath;
					
					String[] tmp;
					tmp = command[1].trim().split("What is the price of the connection");
					tmp = tmp[1].split("\\?");
					tripPath = tmp[0].trim();
						
					tmp = tripPath.trim().split("-");
					
					if (tmp.length < 2)
					{
						System.out.println("Command string malformed line "+line+" -> "+strInput);
						continue inputParsing;
					}
					
					findAndPrintPriceOfConnection (tmp);
					
					
					
					
				}
				else if(command[1].trim().startsWith("What is the cheapest connection from")){
					String from;
					String to;
					
					String[] tmp;
					tmp = command[1].trim().split("from");
					tmp = tmp[1].split("to");
					from = tmp[0].trim();
					
					tmp = command[1].trim().split("to");
					tmp = tmp[1].split("\\?");
					to = tmp[0].trim();
										
					findAndPrintCheapestConnection (from, to);
					
				}
				else if(command[1].trim().startsWith("How many different connections with")){
					String condition;
					String hops;
					String from;
					String to;
					
					
					String[] tmp;
					tmp = command[1].trim().split("with");
					tmp = tmp[1].trim().split("stop");
					tmp = tmp[0].trim().split("[ ]+");					
					condition = tmp[0].trim();
					hops = tmp[1].trim();
					
					
					
					tmp = command[1].trim().split("between");
					tmp = tmp[1].split("and");
					from = tmp[0].trim();			
					
					tmp = command[1].trim().split("and");
					tmp = tmp[1].split("\\?");
					to = tmp[0].trim();				
					
					findAndPrintConnectionsBetween (condition, hops, from, to, 100);
				}
				else{
					System.out.println(getRequest() +" Unknown or malformed string \""+ strInput+"\"");
				}
				
			}			
		}
		System.out.println();
	}


	private static Graph<String> populateConnections (int line, String[] connections){
		Graph<String> graph = new Graph<>();		
		for (int i = 0; i < connections.length; i++){					
			String[] Edge = connections[i].split("[-]");
			if (Edge.length != 3) {
				System.out.println("Not including Flight info malformed on line "+line+" -> "+connections[i]);
				continue;
			}
			else
			{
				// Populate graph
				graph.addEdge(new Edge<>(Edge[0].trim(),Edge[1].trim(),Float.parseFloat(Edge[2].trim())));
			}
		}
		
		return graph;
		
	}


	private static String printNodeList( List<String> nodeList){
		boolean first = true;
		String result = "";
		for (String str : nodeList){
			if (!first){
				result += "-";
			}
			first = false;
			result += str;
		}
		return result;
		
	}

	// Implemented Dijkstra algorithm generalized to find the K Shortest paths should be loopless
	// The maxNode is forced to avoid infinite solutions on connection with loop	 
	private static void findAndPrintAllConnections (String from, String to, String budget, int maxNode){
		int it = 0;
		boolean first = true;
		
		List<Path<String>> paths = new DefaultKShortestPathFinder<String>().findShortestPaths(from, to, graph, maxNode);
		
		for (Path<String> path : paths) {
			if (path.pathCost() <=  Integer.parseInt(budget)){
				if (first){
					System.out.print(getRequest()+" ");
				}
				else{
					System.out.print(", ");
				}
				first = false;
				System.out.print(printNodeList(path.getNodeList()) + "-" + path.pathCost());
			}
			else
			{
				// Its sorted so we may break now, following paths will exceed the budget.
				break;
			}
			it++;
		}
	}

	private static void findAndPrintPriceOfConnection (String[] TripPlan){
		String from;
		String to;
		int it;					
		double totalCost = 0;
		for (it = 0; it < (TripPlan.length-1); it++)
		{
			from = TripPlan[it];
			to = TripPlan[it+1];
						
			List<Path<String>> paths = new DefaultKShortestPathFinder<String>().findShortestPaths(from, to, graph, 1);
			if (paths.size() < 1)
			{
				System.out.println(getRequest()+" No such connection found!");
				return;
			}
			for (Path<String> path : paths) {
				totalCost+=	path.pathCost();
			}
		}
		
		System.out.println(getRequest() + " " + totalCost);
	}

	private static void findAndPrintCheapestConnection (String from, String to){
		List<Path<String>> paths = new DefaultKShortestPathFinder<String>().findShortestPaths(from, to, graph, 1);
					
		if (paths.size() < 1)
		{
			System.out.println(getRequest() +" No such connection found!");
			return;
		}
		for (Path<String> path : paths) {		
			if (path.getNodeList().size() < 1)
			{
				System.out.println(getRequest() +" You're already there!");
				return;
			}				
			System.out.println(getRequest() +" "+printNodeList(path.getNodeList()) + "-" + path.pathCost());
		}
	}
	
	// Implemented Dijkstra algorithm generalized to find the K Shortest paths should be loopless
	// The maxNode is forced to avoid infinite solutions on connection with loop 
	private static void findAndPrintConnectionsBetween (String condition, String hops, String from, String to, int maxNode){
		List<Path<String>> paths = new DefaultKShortestPathFinder<String>().findShortestPaths(from, to, graph, maxNode);
		int it = 0;
		int npaths = 0;
		for (Path<String> path : paths) {
			if (condition.equals("maximum"))
			{
				if (path.getNodeList().size() <= (Integer.parseInt(hops))+1)
				{
					npaths++;
				}
			}
			else if (condition.equals("exactly"))
			{
				if (path.getNodeList().size() == (Integer.parseInt(hops))+1)
				{
					npaths++;
				}
			}
			it++;
		}
		System.out.println(getRequest() + " " + npaths);
	}
}

	

