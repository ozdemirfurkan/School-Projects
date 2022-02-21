#include "Hacker.h"

Hacker::Hacker(int arrivalTime, int id){

	this->ID = id;
	this->totalValidCommits = 0;
	this->totalGifts = 0;
	this->totalCommit = 0;
	this->totalLineChange = 0;
	this->totalWaitingSticker = 0;
	this->totalWaitingHoodie = 0;
	this->start = 0;
	this->turnaround = 0;

}

Hacker::Hacker(const Hacker& hacker) {

	this->ID = hacker.ID;
	this->totalValidCommits = hacker.totalValidCommits;
	this->totalGifts = hacker.totalGifts;
	this->totalCommit = hacker.totalCommit;
	this->totalLineChange = hacker.totalLineChange;
	this->totalWaitingSticker = hacker.totalWaitingSticker;
	this->totalWaitingHoodie = hacker.totalWaitingHoodie;
	this->start = hacker.start;
	this->turnaround = hacker.turnaround;

}

Hacker& Hacker::operator=(const Hacker& hacker){

	if(this == &hacker){
		return *this;
	}

	this->ID = hacker.ID;
	this->totalValidCommits = hacker.totalValidCommits;
	this->totalGifts = hacker.totalGifts;
	this->totalCommit = hacker.totalCommit;
	this->totalLineChange = hacker.totalLineChange;
	this->totalWaitingSticker = hacker.totalWaitingSticker;
	this->totalWaitingHoodie = hacker.totalWaitingHoodie;
	this->start = hacker.start;
	this->turnaround = hacker.turnaround;

	return *this;

}

bool Hacker::operator<(const Hacker& other) {

	if(this->ID < other.ID){

		return true;
	}

	return false;
}

Hacker::~Hacker() {

}
