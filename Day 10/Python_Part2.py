import sys
sys.setrecursionlimit(100000)
from collections import defaultdict, deque
with open("input.txt", "r") as f:
    lines = f.readlines()

'''
part 2 is the same, we have the cells that are part of the loop, 
we just need to flood fill the ones that are inside the main looop
we can either use shoelace formula or expand so we can cover cells during flood fill
'''

grid = []
for line in lines:
    line = line.strip()
    if line == "": continue
    grid.append(line)

start = None
start_adj = []
adj = defaultdict(list)
for i, row in enumerate(grid):
    for j, cell in enumerate(row):
        neighbors = []
        if cell == "|":
            neighbors = [(i-1, j), (i+1, j)]
        elif cell == "-":
            neighbors = [(i, j-1), (i, j+1)]
        elif cell == "L":
            neighbors = [(i-1, j), (i, j+1)]
        elif cell == "J":
            neighbors = [(i-1, j), (i, j-1)]
        elif cell == "7":
            neighbors = [(i+1, j), (i, j-1)]
        elif cell == "F":
            neighbors = [(i+1, j), (i, j+1)]
        elif cell == "S":
            start = (i, j)
        for x, y in neighbors:
            if x >= 0 and x < len(grid) and y >= 0 and y < len(row):
                adj[(i, j)].append((x, y))

adj_start = []
for vert in adj:
    for vert2 in adj[vert]:
        if vert2 == start:
            adj_start.append(vert)
adj[start] = adj_start

INF = 1000000000
dst = defaultdict(lambda: INF)
bfs_q = deque()
bfs_q.append(start)
dst[start] = 0
ans = (0, start)
while len(bfs_q) > 0:
    curcell = bfs_q.popleft()
    for nxt in adj[curcell]:
        if dst[nxt] == INF:
            dst[nxt] = dst[curcell] + 1
            ans = max(ans, (dst[nxt], nxt))
            bfs_q.append(nxt)

#print(len(dst)/2) #distnace is all just the length of the whole loop / 2
#loop_cells = list(dst.keys())

#for part 2, find area, then subtract len of loop - 1

seen = set()
ordered_points = []

def dfs(curr_cell,seen,ordered_points,adj):
    #mark
    print(curr_cell)
    x,y = curr_cell
    seen.add(curr_cell)
    ordered_points.append(curr_cell)
    for neigh_cell in adj[curr_cell]:
        neigh_x,neigh_y = neigh_cell
        if neigh_cell not in seen:
            dfs(neigh_cell,seen,ordered_points,adj)

dfs(start,seen,ordered_points,adj)
num_points =len(ordered_points)
#print(num_points//2)



def calculate_polygon_area(points):
    n = len(points)
    area = 0.0

    for i in range(n):
        current = points[i]
        next_point = points[(i + 1) % n]

        area += (current[0] * next_point[1] - next_point[0] * current[1])

    return abs(area) / 2.0

area = calculate_polygon_area(ordered_points)
print(area - (len(ordered_points)//2 - 1))



