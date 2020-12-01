# Project-Assignment2
Assignment2

- My assignment 2 code runs by creating a graph and running Dijkstra's algorithm on that graph to find the shortest path from one destination to another
- The main program, FindPath.java has 1 function, route(starting_city, ending_city, attractionsList), returns a List<HashMap<Vertex, String>> which contains the shortest path
- In the main, it asks you to enter a starting city, ending city, and attractions you want to visit 
- It then calls the route function with the user input and returns the shortest path with the distance in miles
- The second program Graph.java has 2 objects of Edge and Vertex.
- Vertex stores the name of the vertex you are at
- Edge stores the weight and destination vertex of the current Vertex
- Both are stored in a HashMap<Vertex, LinkedList<Edge>> adjacencys which is an adjacency list storing the vertex and its edges and their weights
- The class MakeGraph uses the addEdge and its helper function checkEdge to add all the edges of the given vertex
- The class utilizes findVertex to find if a specific vertex already exists in the adjacency list
- ShortestPath is the function which utilizes Dijkstra's algorithm, and has 2 helper functions smallestDistance and findMinDistance
- ShortestPath adds vertexes that have been known to the settled list and adds that vertexes edges to the unsettled list
- The function iterates through those edges and finds the edge with the smallest weight to the source vertex and stores that in the vertex list shortestPath, which is defined
- in the vertex definition
- ShortestPath then returns the final vertex which and in the main program we iterate through and print the vertexes in that vertex's shortestPath list
