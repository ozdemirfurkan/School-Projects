#include <fstream>
#include <queue>
#include <list>
#include <vector>

using namespace std;

struct Vertex{
	queue<int> edges;
	int inDegree = 0;
	int outDegree = 0;
};


int main(int argc, char const *argv[]) {

	ifstream infile;
	infile.open(argv[1]);
	ofstream outfile;
	outfile.open(argv[2]);

	int numOfVertex = 0;
	int numOfEdges = 0;
	infile >> numOfVertex;
	vector<Vertex> graph;
	graph.resize(numOfVertex);

	for(int i=0; i<numOfVertex; i++){

		int ID, outDegree;
		infile >> ID >> outDegree;
		numOfEdges += outDegree;
		graph[i].outDegree = outDegree;

		for(int j=0; j<outDegree; j++){

			int nextID;
			infile >> nextID;
			graph[nextID].inDegree++;
			graph[i].edges.push(nextID);

		}
	}

	list<int> path;
	int startVertexID;
	infile >> startVertexID;
	path.push_back(startVertexID);
	infile.close();

	bool isEulerian = true;

	for(int i=0; i<numOfVertex; i++){

		if(graph[i].inDegree != graph[i].outDegree){

			isEulerian = false;
			outfile << "no path";
			break;

		}
	}

	list<int> :: iterator it;
	list<int> :: iterator it2;
	it = path.begin();
	it2 = it;

	if(isEulerian){

		while(path.size() < numOfEdges + 1){

			list<int> tour;
			int currentVertex = *it;

			while(!graph[currentVertex].edges.empty()){

				int tourVertex = graph[currentVertex].edges.front();
				graph[currentVertex].edges.pop();
				tour.push_back(tourVertex);
				currentVertex = tourVertex;

			}

			++it2;
        	path.insert(it2, tour.begin(), tour.end());
        	++it;
        	it2 = it;
		}

	}

	if(isEulerian){

		for (auto const& i: path) {

        	outfile << i << " ";

    	}
	}

	outfile.close();
	return 0;
}