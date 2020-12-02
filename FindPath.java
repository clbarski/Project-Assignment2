import java.io.*;
import java.util.*;

//Implement Graph class
public class FindPath implements Graph
{
	//Function to return a List of type HashMap<Vertex, String> containing the final destinations
	public static List<HashMap<Vertex, String>> route(String starting_city, String ending_city, List<String> attractions)
	{
		//Set the file destination to where it is on your computer
		File roadsCSV = new File("C:/Users/littl/eclipse-workspace/Assignment2/src/roads.csv");
		File attractionsCSV = new File("C:/Users/littl/eclipse-workspace/Assignment2/src/attractions.csv");
		//Initializing the list we will be returning
		List<HashMap<Vertex, String>> finalList = new ArrayList<HashMap<Vertex, String>>();
		
		try
		{
			//Create a new buffered reader to read through the roads.csv file
			BufferedReader br = new BufferedReader(new FileReader(roadsCSV));
			String line, source, destination, sWeight, attraction;
			int weight;
			//Create a new variable graph to manipulate and pass the roads.csv file through
			MakeGraph graph = new MakeGraph();
			
			//Read through each line of the roads.csv file
			//We iterate through each line and break that line up with the comma delimiter, then add those to the graph
			while((line = br.readLine()) != null)
			{
				//Create a string list roads with a comma delimiter
				String[] roads = line.split(",");
				
				//Set the source to the first item in roads and set it to lower case
				source = roads[0]; source = source.toLowerCase();
				//Set the destination to the second item in roads and set it to lower case
				destination = roads[1]; destination = destination.toLowerCase();
				//Set sWeight to the 3rd item in roads and change it to an integer
				sWeight = roads[2]; weight = Integer.parseInt(sWeight);
				
				//If/else statements to fill our graph with all the vertexes and their edges
				//If the vertex exists already
				if(graph.findVertex(source) != null)
				{
					//Set v1 to the same vertex object
					Vertex v1 = graph.findVertex(source);
					
					//If the destination is already a vertex
					if(graph.findVertex(destination) != null)
					{
						//Set v2 to the same vertex object and add the edges
						Vertex v2 = graph.findVertex(destination);
						graph.addEdge(v1, v2, weight);
					}
					//If the destination is not a vertex already
					else
					{
						//Create a new vertex object and add the edges
						Vertex v2 = new Vertex(destination);
						graph.addEdge(v1, v2, weight);
					}
				}
				//If the vertex does not exist
				else 
				{
					//Check if the destination is already a vertex
					if(graph.findVertex(destination) != null)
					{
						//If it is, set the destination vertex to v2 and create a new vertex object v1 and add the edges
						Vertex v1 = new Vertex(source);
						Vertex v2 = graph.findVertex(destination);
						graph.addEdge(v1, v2, weight);
					}
					//If the destination is not a vertex
					else
					{
						//Create two new vertex v1 and v2 and add their edges
						Vertex v1 = new Vertex(source);
						Vertex v2 = new Vertex(destination);
						graph.addEdge(v1, v2, weight);
					}
				}
					
			}
			br.close();
			
			//List of attractions and their city
			HashMap<String, String> attractionsList = new HashMap<String, String>();
			br = new BufferedReader(new FileReader(attractionsCSV));
			
			//Iterate through each line of the file and split each line with a comma delimiter
			while((line = br.readLine()) != null)
			{
				String[] StringAttractions = line.split(",");
				
				//Add the attraction and city source to the hashmap
				attraction = StringAttractions[0]; attraction = attraction.toLowerCase();
				source = StringAttractions[1]; source = source.toLowerCase();
				
				attractionsList.put(attraction, source);
				
			}
			br.close();
			
			//This is the hashmap we will be returning in the list
			HashMap<Vertex, String> returnThis = new HashMap<Vertex, String>();
			String attractionLocation = null;
			Vertex currentLowest = null;
			
			//This while loop takes the starting destination and finds the shortest path to one of the attractions
			//It then sets that attraction to the starting city and does the same for each other attraction
			//We remove each visited attraction from the attractions list
			while(attractions.size() > 0)
			{
				int count = 0;
				currentLowest = null;
				Vertex current = null;
				for(int i = 0; i<attractions.size();i++)
				{
					attractionLocation = attractionsList.get(attractions.get(i));
					if(currentLowest == null)
					{
						currentLowest = graph.shortestPath(starting_city, attractionLocation);
						count = i;
						starting_city = attractionLocation;
					}
					else
					{
						current = graph.shortestPath(starting_city, attractionLocation);
						if(currentLowest.getDist() > current.getDist())
						{
							count = i;
							currentLowest = current;
							starting_city = attractionLocation;
						}
						
					}
				}
				attractions.remove(count);
				//Add the current lowest destination and the current location to the hashmap
				returnThis.put(currentLowest, attractionLocation);
			}
			
			//At the end of the loop, find the lowest distance between the current attraction and the ending city
			currentLowest = graph.shortestPath(attractionLocation, ending_city);
			returnThis.put(currentLowest, ending_city);
			finalList.add(returnThis);
			
		}
		
		//Exception catches
		catch (EOFException e)
	    {
	        e.printStackTrace();
	    } 
		catch (NumberFormatException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//Return the finalList
		return finalList;
	}
	
	public static void main(String[] args)
	{
		List<String> attractions = new ArrayList<String>();
		attractions.add("freedom trail");
		//attractions.add("oz museum");
		List<HashMap<Vertex, String>> finalList = route("boston ma", "orlando fl", attractions);
		
		//Iterate through the returned final list and print the shortest path
		for(int i = 0; i<finalList.size(); i++)
		{
			HashMap<Vertex, String> check = finalList.get(i);
			for(Vertex vertex : check.keySet())
			{
				List<Vertex> shortestPath = vertex.getShortestPath();
				System.out.println("From: ");
				for(int j = 0; j<shortestPath.size(); j++)
				{
					System.out.println(shortestPath.get(j).getName()+" to:");
				}
				System.out.println(check.get(vertex));
				System.out.print("is: "+vertex.getDist()+" miles.");
				System.out.println("\n");
			}
		}
		
	}
}
