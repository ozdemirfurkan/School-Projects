/*
//  Created by Furkan Ozdemir
//
//  Resources: 
// 
//  "https://github.com/rizaozcelik/cmpe250-fall20/tree/main/PS11"
//  "https://konaeakira.github.io/posts/using-the-shortest-path-faster-algorithm-to-find-negative-cycles.html"
//
*/

#include <fstream>
#include <vector>
#include <stack>
#include <queue>
#include <unordered_map>

using namespace std;

class Vertex {
public:

    int from;
    unordered_map<int, int> adjacencyMap;

    Vertex(){
    }

    Vertex(int from) {
        this->from = from;
    }

    void addEdge(int to, int capacity) {
        adjacencyMap[to] = capacity;
    }
};

class AugmentingPath {
public:

    int amount;
    stack <int> path;

    AugmentingPath() {
        this->amount = -1;
    }

    AugmentingPath(int amount, stack<int> path) {
        this->amount = amount;
        this->path = path;
    }
};

AugmentingPath spfa(unordered_map<int, Vertex>& residualGraph, int source, int target){

    vector<int> distances(target + 1, 0);
    vector<int> predecessors(target + 1, -1);
    vector<bool> inQueue(target + 1, false);
    vector<bool> isVisited(target + 1, false);
    queue<int> q;
    q.push(source);
    inQueue[source] = true;
    isVisited[source] = true;

    while(!q.empty()){

        int currentVertex = q.front();
        q.pop();
        inQueue[currentVertex] = false;

        for(auto edge : residualGraph[currentVertex].adjacencyMap) {

            int to = edge.first;
            int weight = edge.second;

            if (!isVisited[to]) {

                isVisited[to] = true;
                distances[to] = distances[currentVertex] + weight;
                predecessors[to] = currentVertex;
                inQueue[to] = true;
                q.push(to);

            } else {

                if (distances[to] < distances[currentVertex] + weight) {

                    distances[to] = distances[currentVertex] + weight;
                    predecessors[to] = currentVertex;

                    if (!inQueue[to]) {

                        inQueue[to] = true;
                        q.push(to);
                    }
                }
            }
        }
    }

    if(distances[target] == 0){

        return AugmentingPath();
    }

    stack<int> path;
    int child = target;

    while(child!=source){

        path.push(child);
        int parent = predecessors[child];
        child = parent;

    }

    path.push(source);
    return AugmentingPath(distances[target], path);
}

void augment(unordered_map<int, Vertex>& residualGraph, AugmentingPath augmentingPath) {

    stack<int> path = augmentingPath.path;

    while (path.size() > 1) {

        int fromVertex = path.top();
        path.pop();
        int toVertex = path.top();
        residualGraph[toVertex].addEdge(fromVertex, -(residualGraph[fromVertex].adjacencyMap[toVertex]));
        residualGraph[fromVertex].adjacencyMap.erase(toVertex);

    }
}

int fordFulkerson(unordered_map<int, Vertex>& graph, int source, int target){

    unordered_map<int, Vertex> residualGraph = graph;
    int maxFlow = 0;
    bool isAugmentingPathLeft = true;

    while (isAugmentingPathLeft) {

        AugmentingPath augmentingPath = spfa(residualGraph, source, target);

        if (augmentingPath.amount > 0) {

            augment(residualGraph, augmentingPath);
            maxFlow = maxFlow + augmentingPath.amount;

        } else {

            isAugmentingPathLeft = false;

        }
    }

    return maxFlow;
}

int main(int argc, char const *argv[]) {

    ifstream infile;
    infile.open(argv[1]);
    ofstream outfile;
    outfile.open(argv[2]);
    int totalTestCases = 0;
    infile >> totalTestCases;

    for(int i=0; i<totalTestCases; i++){

        int n = 0;
        infile >> n;
        unordered_map<int, Vertex> graph;

        for(int i=0; i<2*n+2; i++){

            graph[i] = Vertex(i);
        }

        for(int i=1; i<n+1; i++){

            graph[0].addEdge(i, 0);
            graph[n+i].addEdge(2*n+1, 0);
        }

        for(int i=1; i<n+1; i++){

            for(int j=1; j<n+1; j++){

                int weight = 0;
                infile >> weight;
                graph[i].addEdge(j+n, weight);
            }
        }

        int maxFlow = fordFulkerson(graph, 0, 2*n+1);
        outfile << maxFlow << endl;
    }
    
    infile.close();
    outfile.close();
    return 0;
}