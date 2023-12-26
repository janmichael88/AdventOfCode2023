import sys
from collections import defaultdict
sys.setrecursionlimit(1000000)
ans = res = 0

with open("input.txt") as f:
    s = f.read().strip()


# grid input
g = [list(r) for r in s.split("\n")]
n,m = len(g),len(g[0])

def adj(cur, part=2):
    cx,cy = cur
    adjs = {
            "^":[(-1,0)],
            ">":[(0,1)],
            "v":[(1,0)],
            "<":[(0,-1)],
            ".":[(1,0),(-1,0),(0,1),(0,-1)]
        }
    adjs = adjs[g[cx][cy]]
    if part == 1 and g[cx][cy] in "^>v<":
        adjs = {
            "^":[(-1,0)],
            ">":[(0,1)],
            "v":[(1,0)],
            "<":[(0,-1)]
        }[g[cx][cy]]
    for dx,dy in adjs:
        nx,ny = cx+dx,cy+dy
        if nx in range(n) and ny in range(m) and g[nx][ny] != "#":
            yield (nx,ny)

best = 0
def dfs(cur, path, pathset):
    global best
    if cur == (n-1,m-2):
        best = max(best, len(path))
    for a in adj(cur, part=1):
        if a not in pathset:
            path.append(a)
            pathset.add(a)
            dfs(a, path, pathset)
            pathset.remove(a)
            path.pop(-1)

dfs((0,1), [], set())
print(best)

v = set()
gd = defaultdict(list)

##edges = 0
##vert = 0
for i in range(n):
    for j in range(m):
        if g[i][j] != "#":
            n_adj = len(list(adj((i,j))))
            if n_adj > 2:
                v.add((i,j))
v.add((0,1))
v.add((n-1,m-2))


for x,y in v:
    q = []
    q.append((x,y))
    seen = {(x,y)}
    dist = 0
    while len(q) > 0:
        nq = []
        dist += 1
        for c in q:
            for a in adj(c):
                if a not in seen:
                    if a in v:
                        gd[x,y].append((dist, a))
                        seen.add(a)
                    else:
                        seen.add(a)
                        nq.append(a)
        q = nq


best = 0
def dfs(cur, pathset, totaldist):
    global best
    if cur == (n-1,m-2):
        if totaldist > best:
            best = max(best, totaldist)
    for da,a in gd[cur]:
        if a not in pathset:
            pathset.add(a)
            dfs(a,pathset, totaldist + da)
            pathset.remove(a)

dfs((0,1), set(), 0)
print(best)