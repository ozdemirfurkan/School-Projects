#include "Hacker.h"
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <stdlib.h> 
#include <iomanip>

using namespace std;

vector<Hacker> hackers;

struct StickerDesk{			
	int ID;
	float speed;
	float busy = 0;
	StickerDesk(int ID, float speed) : ID(ID), speed(speed){
	}
};

vector<StickerDesk> stickerDesks;

struct HoodieDesk{
	int ID;
	float speed;
	float busy = 0;
	HoodieDesk(int ID, float speed) : ID(ID), speed(speed){
	}
};

vector<HoodieDesk> hoodieDesks;

struct Commit{
	int hackerID, line;
	float time;
	Commit(int hackerID, int line, float time) : hackerID(hackerID), line(line), time(time){
	}
};

struct QueueAttempt{
	int hackerID;
	float time;
	QueueAttempt(int hackerID, float time) : hackerID(hackerID), time(time){
	}
};

struct HoodieQueueAttempt{
	int hackerID;
	float time;
	HoodieQueueAttempt(int hackerID, float time) : hackerID(hackerID), time(time){
	}
};

struct compareSticker{
	bool operator()(QueueAttempt const& q2, QueueAttempt const& q1){

		if(abs(q1.time - q2.time) < 0.00001){

			if(q1.hackerID < q2.hackerID){
				return true;
			}

		}else if(q1.time - q2.time < 0.00001){

			return true;
		}

		return false;
	}
};

struct compareHoodie{
	bool operator()(HoodieQueueAttempt const& q2, HoodieQueueAttempt const& q1){                             

		if(hackers[q1.hackerID - 1].totalValidCommits == hackers[q2.hackerID - 1].totalValidCommits){

			if(abs(q1.time - q2.time) < 0.00001){

				if(q1.hackerID < q2.hackerID){
					return true;
				}

			}else if(q1.time - q2.time < 0.00001){

				return true;
			}

		}else if(hackers[q2.hackerID - 1].totalValidCommits < hackers[q1.hackerID - 1].totalValidCommits){

			return true;
		}

		return false;
	}
};

struct compareHoodieAttempt{
	bool operator()(HoodieQueueAttempt const& q2, HoodieQueueAttempt const& q1){

		if(abs(q1.time - q2.time) < 0.00001){

			if(hackers[q2.hackerID - 1].totalValidCommits == hackers[q1.hackerID - 1].totalValidCommits){

				if(q1.hackerID < q2.hackerID){
					return true;
				}

			}else if(hackers[q2.hackerID - 1].totalValidCommits < hackers[q1.hackerID - 1].totalValidCommits){

				return true;
			}

		}else if(q1.time - q2.time < 0.00001){

			return true;
		}

		return false;
	}
};

priority_queue<QueueAttempt, vector<QueueAttempt>, compareSticker> queueAttempts;
priority_queue<HoodieQueueAttempt , vector<HoodieQueueAttempt>, compareHoodieAttempt> HoodieQueueAttempts;
priority_queue<QueueAttempt, vector<QueueAttempt>, compareSticker> stickerQueue;
priority_queue<HoodieQueueAttempt, vector<HoodieQueueAttempt>, compareHoodie> hoodieQueue;
priority_queue<float> hoodietime;
int maxLengthSticker = 0;
int maxLengthHoodie = 0;

void addCommit(Commit const& c1){

	hackers[c1.hackerID - 1].totalLineChange += c1.line;
	hackers[c1.hackerID - 1].totalCommit += 1;
	if(c1.line >= 20){
	hackers[c1.hackerID - 1].totalValidCommits += 1;
	}

}

void giveSticker(QueueAttempt const& q1){

	hackers[q1.hackerID - 1].totalGifts++;
	bool isAllFull = true;
	int x = 0;
	for(int i = 0; i < stickerDesks.size(); i++){

		if(stickerDesks[i].busy - q1.time < 0.00001){

			isAllFull = false;
			x = i;
			break;
		}
	}

	if(isAllFull){

		stickerQueue.push(q1);
		if(stickerQueue.size() > maxLengthSticker){
			maxLengthSticker = stickerQueue.size();
		}

	}else{

		stickerDesks[x].busy = q1.time + stickerDesks[x].speed;
		HoodieQueueAttempt hq1(q1.hackerID, q1.time + stickerDesks[x].speed);
		HoodieQueueAttempts.push(hq1);

	}
}

void giveSticker(float time){

	if(stickerQueue.empty()){

		return;

	}else{

		QueueAttempt q1 = stickerQueue.top();
		int x = 0;
		for(int i = 0; i < stickerDesks.size(); i++){

			if(stickerDesks[i].busy - time < 0.00001){

				x = i;
				break;
			}
		}
		stickerDesks[x].busy = time + stickerDesks[x].speed;
		HoodieQueueAttempt hq1(q1.hackerID, time + stickerDesks[x].speed);
		HoodieQueueAttempts.push(hq1);
		hackers[q1.hackerID - 1].totalWaitingSticker += time - q1.time;
		stickerQueue.pop();
	}

}

void giveHoodie(HoodieQueueAttempt const& hq1){

	HoodieQueueAttempt hq2 = hq1;
	giveSticker(hq1.time);
	bool isAllFull = true;
	int x = 0;
	for(int i = 0; i < hoodieDesks.size(); i++){

		if(hoodieDesks[i].busy - hq2.time < 0.00001){

			isAllFull = false;
			x = i;
			break;
		}
	}

	if(isAllFull){

		hoodieQueue.push(hq2);
		if(hoodieQueue.size() > maxLengthHoodie){
			maxLengthHoodie = hoodieQueue.size();
		}

	}else{

		hoodieDesks[x].busy = hq2.time + hoodieDesks[x].speed;
		hackers[hq2.hackerID - 1].turnaround += ((hq2.time + hoodieDesks[x].speed) - hackers[hq2.hackerID - 1].start);
		hoodietime.push(-(hq2.time + hoodieDesks[x].speed));
	}
}

void giveHoodie(float time){

	if(hoodieQueue.empty()){

		return;

	}else{

		HoodieQueueAttempt hq1 = hoodieQueue.top();
		int x = 0;
		for(int i = 0; i < hoodieDesks.size(); i++){

			if(hoodieDesks[i].busy - time < 0.00001){

				x = i;
				break;
			}
		}
		hoodieDesks[x].busy = time + hoodieDesks[x].speed;
		hoodietime.push(-(time + hoodieDesks[x].speed));
		hackers[hq1.hackerID - 1].totalWaitingHoodie += time - hq1.time;
		hackers[hq1.hackerID - 1].turnaround += ((time + hoodieDesks[x].speed) - hackers[hq1.hackerID - 1].start);
		hoodieQueue.pop();
	}
}

int main(int argc, char* argv[]) {

	ifstream infile;
	infile.open(argv[1]);
	ofstream outfile;
	outfile.open(argv[2]);

	int numberOfHackers;
	infile >> numberOfHackers;

	for(int i=0; i<numberOfHackers; i++){  // take hackers

		float arrivalTime;
		infile >> arrivalTime;
		Hacker h(arrivalTime , i+1);
		hackers.push_back(h);

	}

	int numberOfCommits;
	queue<Commit> commits;
	infile >> numberOfCommits;

	for(int i=0; i<numberOfCommits; i++){  // take commits

		int hackerID, line;
		float time;
		infile >> hackerID >> line >> time;
		Commit c(hackerID, line, time);
		commits.push(c);

	}

	int numberOfQueueAttempts;
	infile >> numberOfQueueAttempts;

	for(int i=0; i<numberOfQueueAttempts; i++){  // take queue attempts

		int hackerID;
		float time;
		infile >> hackerID >> time;
		QueueAttempt qA(hackerID, time);
		queueAttempts.push(qA);

	}

	int numberOfStickerDesks;
	infile >> numberOfStickerDesks;

	for(int i=0; i<numberOfStickerDesks; i++){  // take sticker desks

		float speed;
		infile >> speed;
		StickerDesk sD(i+1, speed);
		stickerDesks.push_back(sD);

	}

	int numberOfHoodieDesks;
	infile >> numberOfHoodieDesks;

	for(int i=0; i<numberOfHoodieDesks; i++){  // take hoodie desks

		float speed;
		infile >> speed;
		HoodieDesk hD(i+1, speed);
		hoodieDesks.push_back(hD);

	}

	int invalid1 = 0;
	int invalid2 = 0;
	float lastEvent = 0;
	float x1= 2560000;
	float x2= 2560000;
	float x3= 2560000;
	float x4= 2560000;
	while(!queueAttempts.empty() || !commits.empty() || !HoodieQueueAttempts.empty() || !hoodietime.empty()){

		if(!commits.empty()){
			x1 = commits.front().time;
		}

		if(!hoodietime.empty()){
			x2 = -(hoodietime.top());
		}

		if(!HoodieQueueAttempts.empty()){
			x3 = HoodieQueueAttempts.top().time;
		}

		if(!queueAttempts.empty()){
			x4 = queueAttempts.top().time;
		}

		if(x1 - x2 < 0.00001 && x1 - x3 < 0.00001 && x1 - x4 < 0.00001 && x1 != 2560000){

			addCommit(commits.front());
			lastEvent = x1;
			commits.pop();

		}else if(x2 - x3 < 0.00001 && x2 - x4 < 0.00001 && x2 != 2560000){

			lastEvent = x2;
			giveHoodie(x2);
			hoodietime.pop();

		}else if(x3 - x4 < 0.00001 && x3 != 2560000){

			giveHoodie(HoodieQueueAttempts.top());
			HoodieQueueAttempts.pop();

		}else{

			lastEvent = x4;

			if(hackers[queueAttempts.top().hackerID -1].totalValidCommits < 3){

				invalid1++;
				queueAttempts.pop();

			}else if(hackers[queueAttempts.top().hackerID -1].totalGifts > 2){

				invalid2++;
				queueAttempts.pop();

			}else{

				hackers[queueAttempts.top().hackerID -1].start = x4;
				giveSticker(queueAttempts.top());
				queueAttempts.pop();

			}
		}

		x1= 2560000;
		x2= 2560000;
		x3= 2560000;
		x4= 2560000;
	}

	int totalGifts = 0;
	for(int i=0; i<numberOfHackers; i++){

		totalGifts += hackers[i].totalGifts;

	}
	float averageGifts = (float) totalGifts/(float) numberOfHackers;

	float totalWaitingSticker = 0;
	float totalWaitingHoodie = 0;
	float totalLineChange = 0;
	float totalTurnaround = 0;
	for(int i=0; i<numberOfHackers; i++){

		totalWaitingSticker += hackers[i].totalWaitingSticker;
		totalWaitingHoodie += hackers[i].totalWaitingHoodie;
		totalLineChange += hackers[i].totalLineChange;
		totalTurnaround += hackers[i].turnaround;

	}
	float averageWaitingSticker = totalWaitingSticker/totalGifts;
	float averageWaitingHoodie = totalWaitingHoodie/totalGifts;
	float averageCommit = (float) numberOfCommits/ (float) numberOfHackers;
	float averageLineChange = totalLineChange/numberOfCommits;

	int mostID = 1;
	float mostWaiting = hackers[0].totalWaitingSticker + hackers[0].totalWaitingHoodie;
	for(int i=0; i<numberOfHackers; i++){

		if((hackers[i].totalWaitingHoodie + hackers[i].totalWaitingSticker) - mostWaiting > 0.00001){

			mostID = i+1;
			mostWaiting = hackers[i].totalWaitingHoodie + hackers[i].totalWaitingSticker;

		}
	}

	int leastID = -1;
	float leastWaiting = 2560000;
	for(int i=0; i<numberOfHackers; i++){

		if(hackers[i].totalGifts == 3){

			if((hackers[i].totalWaitingSticker + hackers[i].totalWaitingHoodie) - leastWaiting < -0.00001){

				leastWaiting = hackers[i].totalWaitingSticker + hackers[i].totalWaitingHoodie;
				leastID = i+1;
			}
		}
	}

	outfile << fixed;
	outfile << setprecision(3);
	outfile << maxLengthSticker << endl;
	outfile << maxLengthHoodie << endl;
	outfile << averageGifts << endl;
	outfile << averageWaitingSticker << endl;
	outfile << averageWaitingHoodie << endl;
	outfile << averageCommit << endl;
	outfile << averageLineChange << endl;
	outfile << totalTurnaround/totalGifts << endl;
	outfile << invalid1 << endl;
	outfile << invalid2 << endl;
	outfile << mostID << " " << mostWaiting << endl;
	if(leastID == -1){

		outfile << leastID << " " << -1.000 << endl;
	}else{

		outfile << leastID << " " << leastWaiting << endl;
	}
	outfile << lastEvent << endl;

	infile.close();
	outfile.close();

    return 0;
}