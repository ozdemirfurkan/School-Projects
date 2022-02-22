/*
*   Assignment 2 - Abstractor
*   A simple project that create a multithreaded scientific search engine in C++
*   that queries the paper abstracts and summarizes the most relevant ones.
*
*   How to compile: "make" or "g++ -pthread abstractor.cpp -o abstractor.out"
*   How to run: ./abstractor.out input_file_name.txt output_file_name.txt
*
*   @author Furkan Özdemir 2018400201
*
*   In this project I made benefit of some codes from PS8 and PS10
*/
#include <string>
#include <fstream>
#include <vector>
#include <sstream>
#include <pthread.h>
#include <queue>
#include <set>
#include <iomanip>

using namespace std;

int T, A, N; // T is number of threads, A is number of abstracts, N is number of returns
vector<string> query; // vector for words that are given in input file
vector<string> summary; // vector for summaries that can be represented in output file
vector<float> scores; // vector for scores that can be represented in output file
queue<string> abstract_queue; // queue for abstracts that are given in input file
queue<int> thread_queue; // queue for threads which finished their job and waiting for next one
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER; // mutex for synchronization
ofstream outfile; // output file

void *runner(void *param);
  
int main(int argc, char** argv)
{

	if (argc != 3) { /* if there is not two arguments */
		fprintf(stderr,"usage: ./abstractor.out <input_file_name.txt> <output_file_name.txt>\n");
		return -1;
	}

    string inputfile = argv[1]; // inputfile
    string outputfile = argv[2]; // outputfile

    ifstream infile(inputfile);
    outfile.open(outputfile);

    string firstline; // firstline in the input file that contains T, A and N
    string secondline; // second line in input file that contains words that we are looking for
    string line; // rest lines in input file which are abstract files
    getline(infile, firstline);
    getline(infile, secondline);

    /*
    * The code segment below, splits the firstline and change variables from string to integer with stoi().
    */
    T = stoi(firstline.substr(0, firstline.find(' ')));
    firstline = firstline.substr(firstline.find(' ') + 1);
    A = stoi(firstline.substr(0, firstline.find(' ')));
    firstline = firstline.substr(firstline.find(' ') + 1);
    N = stoi(firstline.substr(0, firstline.find(' ')));

    /*
    * With stringstream, I read the words in the second line one by one.
    */
    stringstream s(secondline);
    string word;
    while (s >> word) {
        query.push_back(word);
    }

    /*
    * Pushing the abstract files into abstract queue
    */
    while(getline(infile, line)){
    	abstract_queue.push(line);
    }

    infile.close(); // done with input file

    pthread_t threads[T]; // thread ids

    /*
    * Pushing threads into thread queue
    */
    for(int i = 0; i < T; i++){
    	thread_queue.push(i);
    }


    /*
    * In this while loop, program creates threads to execute runner function.
    * At each iteration if there is a thread waiting in the queue, the thread 
    * starts to run runner function with an abstract in the abstract queue.
    * When abstract queue becomes empty, program is done with all abstract files. 
    */
    while(!abstract_queue.empty() || thread_queue.size() != T){

    	if(!thread_queue.empty() && !abstract_queue.empty()){

    		int i = thread_queue.front();
    		pthread_create(&threads[i], NULL, runner, (void *)(intptr_t) i);
    		thread_queue.pop();	
    	}
    }

    /*
    * Before continuing, program waits for threads to finish their job and join main thread. 
    */
	for (int i = 0; i < T; i++){

		pthread_join(threads[i], NULL);
	}
    outfile << "###" << endl;


    vector<int> score_order; // vector for ordering the scores, the bigger the result the smaller the index
    
    /*
    * Creating a copy of scores vector in order not to lose original scores  
    */
    vector<float> scores_copy;
    for(int i = 0; i < A; i++){

    	scores_copy.push_back(scores[i]);
    }


    /*
    * The while loop orders scores from bigger to smaller
    */
    while(score_order.size() != N){
    	int max_index = 0;
    	for(int i = 1; i < A; i++){

    		if(scores_copy[i] > scores_copy[max_index]){

    			max_index = i;
    		}
    	}
    	scores_copy[max_index] = -1.0;
    	score_order.push_back(max_index);
    }

    /*
    * Writing results into output file
    */
    for(int i = 0; i < N; i++){

    	outfile << "Result " << i+1 << ":" << endl;
    	outfile << "File: " << summary[score_order[i]].substr(0, summary[score_order[i]].find(' ')) << endl;
    	outfile << "Score: " << fixed << setprecision(4) << scores[score_order[i]] << endl; 
    	outfile << "Summary: " << summary[score_order[i]].substr(summary[score_order[i]].find(' ') + 1) << endl;
    	outfile << "###" << endl;
    }

    outfile.close(); // done with output file
    return 0;
}

/*
* In this runner function, threads deal with their abstract file.
* They are comparing the words in the query vector with the text
* in the abstract file. Then, finds a score with Jaccard Simularity 
* Measurement.
*/
void *runner(void *param) {

	int id = (int)(intptr_t) param; // id of threads for naming them
	string myabstract;

    /*
    * Threads take their abstracts from abstract_queue and write the
    * output file which abstract they are reading. In order to ensure
    * synchronization, I used mutex.
    */
	pthread_mutex_lock(&mutex);
	if(!abstract_queue.empty()){
		myabstract = abstract_queue.front();
		outfile << "Thread " << (char)('A' + id) << " is calculating " << myabstract << endl;
		abstract_queue.pop();
	}
	pthread_mutex_unlock(&mutex);



	string abstract_path = "../abstracts/" + myabstract; // abstract path
	ifstream file(abstract_path); // abstract file
	set<string> words; // set for words in the abstract
	vector<string> sentences;  // vector for sentences in the abstract
	string word;
	string sentence = "";

    /*
    * In the loop program inserts words to words set one by one and if there is 
    * a dot, it pushes the sentence to sentences vector.
    */
	while (file >> word){
        words.insert(word);
        sentence = sentence + word + " ";

        if(word == "."){

        	sentences.push_back(sentence);
        	sentence = "";
        }
    }

    /*
    * In the for loop, I compared the words in the query with sentences.
    */
    sentence = myabstract + " ";
    for(int i = 0; i < sentences.size(); i++){

    	for(int j = 0; j < query.size(); j++){

    		string s1 = " " + query[j] + " ";
    		string s2 = " " + sentences[i] + " ";
    		if (s2.find(s1) != string::npos) {
   				
   				sentence += sentences[i];
   				break;
			}
    	}
    }
    

    /*
    * The code segment below, calculates Jaccard Similarity score.
    */
    int first_size = words.size(); 
    words.insert(query.begin(), query.end());
    int union_size = words.size();
    int intersection_size = query.size()-(union_size-first_size);
    float score = (float)intersection_size/(float)union_size;


	file.close(); // done with abstract file

    /*
    * Thread pushes the scores and sentences to global vectors.
    * In order to ensure synchronization I used mutex.
    */
	pthread_mutex_lock(&mutex);
	summary.push_back(sentence);
	scores.push_back(score);
	thread_queue.push(id);
	pthread_mutex_unlock(&mutex);
	pthread_exit(NULL);
}