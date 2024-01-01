'''
find the three wires we need to disconnect that splits the components into two groups
too many edges to try deleting possible sets of three edges
part 2 is just probably drop more edges
graph is undirected
strongly connected components question???
adding an edge can either reduce the number of compoenents, orr keep them the same
union find, keep adding until we get 2 compoenents
once we get to two components, we need to check if adding
'''
from collections import defaultdict
lines = open("input.txt","r")

graph = defaultdict(set)
for l in lines:
    l = l.strip('\n')
    node = l.split(": ")[0]
    neighs = l.split(": ")[1]
    #edge is both ways
    for neigh in neighs:
        graph[node].add(neigh)
        graph[neigh].add(node)

#check number of edges
print(len(graph)*2)

