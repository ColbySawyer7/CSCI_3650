# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.
def reverseGraph(graph):
    reverseGraph = {}
    for vertA, values in graph.items():
        #print(values)
        for vertB in values:
            #print("VertB: " + vertB + "\tVertA: " + vertA)
            reverseGraph.setdefault(vertB, []).append(vertA)
    return reverseGraph

def performDFSPostOrder(visited, graph, node,stack):
    if node not in visited:
        stack.push(node)
        visited.add(node)
        for neighbour in graph[node]:
            performDFSPostOrder(visited, graph, neighbour,stack)

def performKosaraju(graph):
    sccList = []
    # Perform DFS in post-oder transversal
    visited = set()
    stack = []
    performDFSPostOrder(visited, graph, "1", stack)
    print(stack)
    # invert graph
    # Locate SCC
    return sccList

def main():
    # Collect information from the user for the graph dimensions
    print("Welcome to the Koraraju's Algorithm Machine")
    directedGraph = {}
    numVerts = int(input("\n# of vertices: "))
    numEdges = int(input("\n# of edges: "))
    edges = ["Null"] * numEdges
    print(edges)
    print("NumEdges = " + str(numEdges) + "\tNumVerts =" + str(numVerts))
    for x in range(numEdges):
        edge = input("\nEdge " + str(x) + ": ")
        verts = edge.split()
        vertexA = verts[0]
        #DEBUG print("VertexA: " + vertexA)
        vertexB = verts[1]
        # DEBUG print("VertexB: " + vertexB)
        directedGraph.setdefault(vertexA, []).append(vertexB)
        edges[x] = edge

    print("\nThe given graph has " + str(numEdges) + " edges")
    print(edges)
    print(directedGraph)

    #performKosaraju(directedGraph)
    print("\nREVERSAL")
    reverse = reverseGraph(directedGraph)
    print(reverse)
    #   OUTPUT
    # print("The given graph has" + str(numSCC) + "Strongly Connected Components")
    # for x in range(len(sccList)):
    #   print("Connected Component #" + str(x) + ":\t" + str(sccList[x]);

if __name__ == "__main__":
    main()
