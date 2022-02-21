#include <string>
using namespace std;

#ifndef HACKER_H
#define HACKER_H


class Hacker {

public:
	int ID;
	int totalValidCommits;
	int totalGifts;
	int totalCommit;
	int totalLineChange;
	float totalWaitingSticker;
	float totalWaitingHoodie;
	float start;
	float turnaround;

    Hacker(int arrivalTime, int id);
    Hacker(const Hacker& hacker);
    Hacker& operator=(const Hacker& hacker);
    bool operator<(const Hacker& other);
    ~Hacker();
};

#endif //HACKER_H