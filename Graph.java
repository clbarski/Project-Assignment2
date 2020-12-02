import java.util.*;

public interface Graph 
{
	//Class edges which holds the destinations and weights of the edges of a vertex
	public class Edges
	{
		Vertex destination;
		int weight;
		
		Edges(Vertex destination, int weight)
		{
			this.destination = destination;
			this.weight = weight;
		}
		
		Vertex getDest()
		{
			return destination;
		}
		
		int getWeight()
		{
			return weight;
		}
	}
	
	//Class vertex which stores the city name, its distance for calculating shortest path, and a linkedList shortestPath
	//Which holds the information for the shortest path to that vertex
	public class Vertex
	{
		String name;
		int distance = Integer.MAX_VALUE;
		LinkedList<Vertex> shortestPath = new LinkedList<>();
		
		Vertex(String name)
		{
			this.name = name;
		}
		
		String getName()
		{
			return name;
		}
		
		void setDist(int distance)
		{
			this.distance = distance;
		}
		
		int getDist()
		{
			return distance;
		}
		
		void setShortestPath(LinkedList<Vertex> shortestPath)
		{
			this.shortestPath = shortestPath;
		}
		
		LinkedList<Vertex> getShortestPath()
		{
			return shortestPath;
		}
	}
	
	//Class makegraph which constructs the graph using different functions
	public class MakeGraph
	{
		//Adjacent list of vertexes and their edges
		public HashMap<Vertex, LinkedList<Edges>> adjacents;
		
		public MakeGraph()
		{
			adjacents = new HashMap<>();
		}
		
		//addEdge helper function which checks for current edges and adds the new edge and weight
		public void edgeCheck(Vertex source, Vertex destination, int weight)
		{
			LinkedList<Edges> temp = adjacents.get(source);
			Edges edge = new Edges(destination, weight);
			
			//This if statement checks for duplicates
			if(temp != null)
			{
				temp.remove(destination);
			}
			else
			{
				//Create a new linkedlist temp if the vertex has no edges yet
				temp = new LinkedList<>();
			}
			
			//Add the new edge object to temp then put that in the adjacent list hashmap
			temp.add(edge);
			adjacents.put(source, temp);
		}
		
		//Function add edge to add the edges of each vertex
		public void addEdge(Vertex source, Vertex destination, int weight)
		{
			//If the source vertex does not exist yet, put that in the adjacent list
			if(!adjacents.keySet().contains(source))
			{
				adjacents.put(source, null);
			}
			//If the destination vertex does not exist yet, put that in the adjacent list
			if(!adjacents.keySet().contains(destination))
			{
				adjacents.put(destination, null);
			}
			
			//This is an undirected graph so add edges and weights both ways
			edgeCheck(source, destination, weight);
			edgeCheck(destination, source, weight);
			
		}
		
		//Function which finds if the vertex already exists and returns that vertex if it exists
		public Vertex findVertex(String names)
		{
			//Iterate through the adjacent list to find if the vertex already exists
			for(Vertex vertex : adjacents.keySet())
			{
				//Compare the content of the vertex name and the given name
				if(vertex.getName().equals(names))
				{
					return vertex;
				}
			}
			return null;
		}
		
		//Helper function to print the whole graph
		public void printGraph()
		{
			for(Vertex vertex : adjacents.keySet())
			{
				System.out.print("The "+vertex.name+" has edges of: ");
				if(adjacents.get(vertex) != null)
				{
					for(Edges neighbor : adjacents.get(vertex))
					{
						System.out.print(neighbor.getDest().getName()+" ");
					}
					System.out.println();
				}
				else
				{
					System.out.println("none");
				}
			}
		}
		
		//Function shortestPath which utilizes Dijkstra's algorithm and returns the shortest path to a given vertex
		public Vertex shortestPath(String starting, String ending)
		{
			//Set the source and target to the given starting and ending destinations
			Vertex source = findVertex(starting);
			Vertex target = findVertex(ending);
			source.setDist(0);
			
			//Create settled and unsettled sets to keep track of which vertex we have been to
			Set<Vertex> settled = new HashSet<>();
			Set<Vertex> unsettled = new HashSet<>();
			
			unsettled.add(source);
			
			//Loop while we have not seen every vertex
			while(unsettled.size() != 0)
			{
				//Set the current to the smallest distance vertex by calling the smallestDistance helper function
				Vertex current = smallestDistance(unsettled);
				unsettled.remove(current);
				
				//Iterate through the edges of the current vertex
				for(Edges neighbor : adjacents.get(current))
				{
					Vertex adjacentVertex = neighbor.getDest();
					int edgeWeight = neighbor.getWeight();
					
					//If we have not settled the current edge
					if(!settled.contains(adjacentVertex))
					{
						//Call findMinDistance to find the vertex with the smallest edge weight 
						findMinDistance(adjacentVertex, edgeWeight, current);
						unsettled.add(adjacentVertex);
					}
					//If statement to check if we have found the target destination, if we have, return that vertex
					if(current == target)
					{
						return current;			
					}
				}
				//Add the current vertex to the settled list
				settled.add(current);
				
				
			}
			
			return null;
		}
		
		//Smallest distance helper function to return the vertex with the smallest distance from the unsettled list
		public Vertex smallestDistance(Set<Vertex> unsettled)
		{
			Vertex smallestDistVertex = null;
			int smallDistance = Integer.MAX_VALUE;
			for(Vertex vertex:unsettled)
			{
				int vertexDistance = vertex.getDist();
				if(vertexDistance < smallDistance)
				{
					smallDistance = vertexDistance;
					smallestDistVertex = vertex;
				}
			}
			
			return smallestDistVertex;
		}
		
		//Find min distance helper function to set the ShortestPath list to the vertex with the smallest edge weight
		public void findMinDistance(Vertex adjacentVertex, int edgeWeight, Vertex current)
		{
			int sourceDistance = current.getDist();
			//This if statement compares the current edge weight of the current shortest path to the weight of the edge we are checking
			if((sourceDistance + edgeWeight) < adjacentVertex.getDist())
			{
				adjacentVertex.setDist(sourceDistance+edgeWeight);
				LinkedList<Vertex> shortestPath = new LinkedList<>(current.getShortestPath());
				shortestPath.add(current);
				adjacentVertex.setShortestPath(shortestPath);
			}
		}
		
		
	}
	
	
	
	
}
