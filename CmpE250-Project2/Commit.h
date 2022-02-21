#include <string>
using namespace std;

#ifndef COMMIT_H
#define COMMIT_H


class Commit {

public:
	int hackerId;
	int line;
	float ctime;
	

    Commit(int hackerId, int line, float ctime) ;
};

#endif //COMMIT_H