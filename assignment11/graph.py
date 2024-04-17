import networkx as nx

# Create a directed graph
G = nx.DiGraph()

# Add edges as seen in the provided image
# edges = [('a', 'b'), ('f', 'g'), ('g', 'a'), ('g', 'c'), ('g', 'k'), ('k', 'h'),
#          ('f', 'l'), ('f', 'b'), ('k', 'l'), ('l', 'h'), ('h', 'c'), ('h', 'd'),
#          ('d', 'c'), ('l', 'p'), ]
edges = [
    ('a', 'b'), ('d', 'c'), ('e', 'i'), ('f', 'b'), ('f', 'g'), ('f', 'l'),
    ('g', 'a'), ('g', 'c'), ('g', 'k'), ('h', 'c'), ('h', 'd'),
    ('i', 'n'), ('j', 'k'), ('j', 'm'), ('j', 'n'), ('k', 'h'),
    ('k', 'l'), ('l', 'h'), ('l', 'p'), ('m', 'i'), ('n', 'o'),
    ('o', 'k'), ('o', 'l')
]

# Add edges to the graph
G.add_edges_from(edges)

# Check if the graph is a DAG (Directed Acyclic Graph)
is_dag = nx.is_directed_acyclic_graph(G)

# If it is a DAG, perform a topological sort
topological_sort = None
post = None
pre = None
if is_dag:
    pre = list(nx.dfs_preorder_nodes(G))
    post = list(nx.dfs_postorder_nodes(G))
    topological_sort = list(nx.all_topological_sorts(G))

if __name__ == '__main__':
    print(is_dag)
    print(pre)
    print(post)
    print(['j', 'm', 'f', 'g', 'e', 'i', 'n', 'o', 'k', 'l', 'p', 'h', 'd', 'c', 'a', 'b'] in topological_sort)
