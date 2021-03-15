# ====================================================================================
#      Assignment 03 - Kosaraju's Algorithm
# ====================================================================================
# by: Colby Sawyer- sawyerc17@students.ecu.edu
# B01204512
# ====================================================================================
# ====================================================================================

class DirectedGraph:
    # ====================================================================================
    # Constructor for Directed Graph
    # ====================================================================================
    # Described basic DirectedGraph Structure
    # num_verts holding number of vertices
    # graph holding dictionary of lists to represent an adjacency list
    # ====================================================================================
    def __init__(self, num_verts):
        self.num_verts = num_verts
        default_graph = {}
        for x in range(num_verts):
            default_graph.setdefault(x, [])
        self.graph = default_graph

    # ====================================================================================
    # add_edge
    # ====================================================================================
    # add_edge adds an edge to the DirectedGraph
    # u --> v
    # ====================================================================================
    def add_edge(self, x, y):
        self.graph[x].append(y)
        #print(self.graph)

    # ====================================================================================
    # dfs
    # ====================================================================================
    # dfs runs the Depth First Search Algorithm and fills the stack
    # as it goes.
    # ====================================================================================
    def dfs(self, v, visited, stack):
        visited.append(v)
        for x in self.graph[v]:
            if x not in visited:
                self.dfs(x, visited, stack)
        stack.append(v)

    # ====================================================================================
    # invert_graph
    # ====================================================================================
    # invert_graph inverts the DirectedGraph self and
    # returns a DirectedGraph
    # ====================================================================================
    def invert_graph(self):
        graph_invert = DirectedGraph(self.num_verts)
        for x in self.graph:
            for y in self.graph[x]:
                graph_invert.add_edge(y, x)
        return graph_invert

    # ====================================================================================
    # performKosaraju
    # ====================================================================================
    # performKosaraju follows Kosaraju's Algoritmn to find and return a
    # list of SCCs.
    # Kosaraju's Algorithm steps:
    #   Step 1: Run DFS on DirectedGraph (self in this case)
    #   Step 2: Invert graph and store as g_t
    #   Step 3: Run DFS on DirectedGraph g_t to find each SCC
    # returns SCCs as list
    # ====================================================================================
    def perform_kosaraju(self):
        # Declare lists
        stack = []
        sccList = []
        visited = [] # Visited must be empty before DFS

        # Step 1
        for x in range(self.num_verts):
            if x not in visited:
                self.dfs(x, visited, stack)

        # Step 2
        # Compute inverse graph and store as g_t
        g_t = self.invert_graph()
        visited.clear() # Visited must be empty before DFS

        # Step 3
        while stack:
            scc = []
            x = stack.pop()
            if x not in visited:
                g_t.dfs(x, visited, scc)
                sccList.append(scc)

        return sccList

# ====================================================================================
# Main
# ====================================================================================
def main():
    # Collect information from the user for the DirectedGraph dimensions
    print("Welcome to the Koraraju's Algorithm Machine")

    # Retrieve size of DirectedGraph
    numVerts = int(input("\n# of vertices: "))
    numEdges = int(input("\n# of edges: "))
    graph = DirectedGraph(numVerts)

    # Retrieve Each Edge from stdin
    print("\nPlease Enter each Edge: (Seperate w/Space) ")
    print("ex. x y  == (x --> y)")
    for x in range(numEdges):
        edge = input("\nEdge " + str(x) + ": ")
        verts = edge.split()
        vertexA = verts[0]
        # DEBUG print("VertexA: " + vertexA)
        vertexB = verts[1]
        # DEBUG print("VertexB: " + vertexB)
        graph.add_edge(int(vertexA), int(vertexB))
    # DEBUG print("\nThe given graph has " + str(numEdges) + " edges")

    # Use Kosaraju's Algorithm to find a list of SCCs
    sccList = graph.perform_kosaraju()

    # Print each SCC
    print("\nThe given graph has " + str(len(sccList)) + " Strongly Connected Components:")
    for x in range(len(sccList)):
        print("Connected Component #" + str(x) + ":    " + str(sccList[x]))


if __name__ == "__main__":
    main()
