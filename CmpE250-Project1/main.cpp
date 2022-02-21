#include "Character.h"
#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

int findName(vector<Character> &v, string name){

	for(int i=0; i<5; i++){

		if(v[i].name == name){

			return i;

		}
	}
	return 0;
}

int findHobbit(vector<Character> &v){

	for(int i=0; i<5; i++){

		if(v[i].type == "Hobbit"){

			return i;

		}
	}
	return 0;
}

bool isGameOver(vector<Character> &v){

	int count = 0;

	for(int i=0; i<5; i++){

		if(v[i].isAlive == false){

			if(v[i].type == "Hobbit"){

				return true;

			}else{

				count++;

			}
		}
	}

	if(count == 4){

		return true;

	}

	return false;
}

int main(int argc, char* argv[]) {

	ifstream infile;
	infile.open(argv[1]);
	ofstream outfile;
	outfile.open(argv[2]);
	int nMaxRounds;
	infile >> nMaxRounds;
	string name, type;
	int attack, defense, health;

	vector<Character> community1;
	infile >> name >> type >> attack >> defense >> health;
	Character m1(name,type,attack,defense,health,nMaxRounds);
	m1.healthHistory[0] = health;
	community1.push_back(m1);
	infile >> name >> type >> attack >> defense >> health;
	Character m2(name,type,attack,defense,health,nMaxRounds);
	m2.healthHistory[0] = health;
	community1.push_back(m2);
	infile >> name >> type >> attack >> defense >> health;
	Character m3(name,type,attack,defense,health,nMaxRounds);
	m3.healthHistory[0] = health;
	community1.push_back(m3);
	infile >> name >> type >> attack >> defense >> health;
	Character m4(name,type,attack,defense,health,nMaxRounds);
	m4.healthHistory[0] = health;
	community1.push_back(m4);
	infile >> name >> type >> attack >> defense >> health;
	Character m5(name,type,attack,defense,health,nMaxRounds);
	m5.healthHistory[0] = health;
	community1.push_back(m5);
	sort(community1.begin(), community1.end());

	vector<Character> community2;
	infile >> name >> type >> attack >> defense >> health;
	Character n1(name,type,attack,defense,health,nMaxRounds);
	n1.healthHistory[0] = health;
	community2.push_back(n1);
	infile >> name >> type >> attack >> defense >> health;
	Character n2(name,type,attack,defense,health,nMaxRounds);
	n2.healthHistory[0] = health;
	community2.push_back(n2);
	infile >> name >> type >> attack >> defense >> health;
	Character n3(name,type,attack,defense,health,nMaxRounds);
	n3.healthHistory[0] = health;
	community2.push_back(n3);
	infile >> name >> type >> attack >> defense >> health;
	Character n4(name,type,attack,defense,health,nMaxRounds);
	n4.healthHistory[0] = health;
	community2.push_back(n4);
	infile >> name >> type >> attack >> defense >> health;
	Character n5(name,type,attack,defense,health,nMaxRounds);
	n5.healthHistory[0] = health;
	community2.push_back(n5);
	sort(community2.begin(), community2.end());

	int *initialHealths1 = new int[5];
	int *initialHealths2 = new int[5];
	for(int i=0; i<5; i++){

		initialHealths1[i] = community1[i].remainingHealth;
		initialHealths2[i] = community2[i].remainingHealth;

	}
	int theRound = 1;
	string lastKill1 = "nobody";
	string lastKill2 = "nobody";

	for(int i=0; i<nMaxRounds; i++){

		string name1, name2, isSpecial;
		infile >> name1 >> name2 >> isSpecial;

		for(int i=0; i<5; i++){

			community1[i].healthHistory[theRound] = community1[i].healthHistory[(theRound)-1];
			community2[i].healthHistory[theRound] = community2[i].healthHistory[(theRound)-1];

		}


		if(theRound % 2 == 1){

			if(isSpecial == "NO-SPECIAL"){

				int x = 0;

				if(community1[findName(community1,name1)].isAlive == false){

					if(findName(community1,name1) < 4 && community1[findName(community1,name1)+1].isAlive == true){

						x = 1;

					}else if(findName(community1,name1) < 3 && community1[findName(community1,name1)+2].isAlive == true){

						x = 2;

					}else if(findName(community1,name1) < 2 && community1[findName(community1,name1)+3].isAlive == true){

						x = 3;

					}else if(findName(community1,name1) > 0 && community1[findName(community1,name1)-1].isAlive == true){

						x = -1;

					}else if(findName(community1,name1) > 1 && community1[findName(community1,name1)-2].isAlive == true){

						x = -2;

					}else if(findName(community1,name1) > 2 && community1[findName(community1,name1)-3].isAlive == true){

						x = -3;

					}
				}

				int y = 0;

				if(community2[findName(community2,name2)].isAlive == false){

					if(findName(community2,name2) < 4 && community2[findName(community2,name2)+1].isAlive == true){

						y = 1;

					}else if(findName(community2,name2) < 3 && community2[findName(community2,name2)+2].isAlive == true){

						y = 2;

					}else if(findName(community2,name2) < 2 && community2[findName(community2,name2)+3].isAlive == true){

						y = 3;

					}else if(findName(community2,name2) > 0 && community2[findName(community2,name2)-1].isAlive == true){

						y = -1;

					}else if(findName(community2,name2) > 1 && community2[findName(community2,name2)-2].isAlive == true){

						y = -2;

					}else if(findName(community2,name2) > 2 && community2[findName(community2,name2)-3].isAlive == true){

						y = -3;

					}
				}

				int damage =(community1[findName(community1,name1)+x].attack) - (community2[findName(community2,name2)+y].defense);

				if(damage > 0){

					if(((community2[findName(community2,name2)+y].remainingHealth) - damage) > 0){

						(community2[findName(community2,name2)+y].remainingHealth)-=damage;
						community2[findName(community2,name2)+y].healthHistory[theRound] = community2[findName(community2,name2)+y].remainingHealth;

					}else{

						(community2[findName(community2,name2)+y].remainingHealth) = 0;
						community2[findName(community2,name2)+y].isAlive = false;
						lastKill2 = community2[findName(community2,name2)+y].name;
						community2[findName(community2,name2)+y].healthHistory[theRound] = 0;
						if(isGameOver(community2)){

							outfile << "Community-1" << endl;
							theRound++;
							break;

						}

					}

				}

			}else{//somebody tried to use special

				int x = 0;

				if(community1[findName(community1,name1)].isAlive == false){

					if(findName(community1,name1) < 4 && community1[findName(community1,name1)+1].isAlive == true){

						x = 1;

					}else if(findName(community1,name1) < 3 && community1[findName(community1,name1)+2].isAlive == true){

						x = 2;

					}else if(findName(community1,name1) < 2 && community1[findName(community1,name1)+3].isAlive == true){

						x = 3;

					}else if(findName(community1,name1) > 0 && community1[findName(community1,name1)-1].isAlive == true){

						x = -1;

					}else if(findName(community1,name1) > 1 && community1[findName(community1,name1)-2].isAlive == true){

						x = -2;

					}else if(findName(community1,name1) > 2 && community1[findName(community1,name1)-3].isAlive == true){

						x = -3;

					}
				}

				int y = 0;

				if(community2[findName(community2,name2)].isAlive == false){

					if(findName(community2,name2) < 4 && community2[findName(community2,name2)+1].isAlive == true){

						y = 1;

					}else if(findName(community2,name2) < 3 && community2[findName(community2,name2)+2].isAlive == true){

						y = 2;

					}else if(findName(community2,name2) < 2 && community2[findName(community2,name2)+3].isAlive == true){

						y = 3;

					}else if(findName(community2,name2) > 0 && community2[findName(community2,name2)-1].isAlive == true){

						y = -1;

					}else if(findName(community2,name2) > 1 && community2[findName(community2,name2)-2].isAlive == true){

						y = -2;

					}else if(findName(community2,name2) > 2 && community2[findName(community2,name2)-3].isAlive == true){

						y = -3;

					}
				}

				if(community1[findName(community1,name1)+x].type == "Dwarfs" && community1[findName(community1,name1)+x].nRoundsSinceSpecial >= 20){

					(community2[findName(community2,name2)+y].remainingHealth)-=((community1[findName(community1,name1)+x].attack) - (community2[findName(community2,name2)+y].defense));
					community1[findName(community1,name1)+x].nRoundsSinceSpecial = -1;

				}else if(community1[findName(community1,name1)+x].type == "Elves" && community1[findName(community1,name1)+x].nRoundsSinceSpecial >= 10){

					community1[findHobbit(community1)].remainingHealth += ((community1[findName(community1,name1)+x].remainingHealth)-((community1[findName(community1,name1)+x].remainingHealth)/2));
					community1[findName(community1,name1)+x].remainingHealth = (community1[findName(community1,name1)+x].remainingHealth)/2;
					community1[findHobbit(community1)].healthHistory[theRound] = community1[findHobbit(community1)].remainingHealth;
					community1[findName(community1,name1)+x].healthHistory[theRound] = community1[findName(community1,name1)+x].remainingHealth;
					community1[findName(community1,name1)+x].nRoundsSinceSpecial = -1;

				}else if(community1[findName(community1,name1)+x].type == "Wizards" && community1[findName(community1,name1)+x].nRoundsSinceSpecial >= 50){

					if(lastKill1 != "nobody"){

						if(community1[findName(community1,lastKill1)].isAlive == false){

							community1[findName(community1,lastKill1)].nRoundsSinceSpecial = 0;

						}
						community1[findName(community1,lastKill1)].isAlive = true;
						community1[findName(community1,lastKill1)].remainingHealth = initialHealths1[findName(community1,lastKill1)];
						community1[findName(community1,lastKill1)].healthHistory[theRound] = community1[findName(community1,lastKill1)].remainingHealth;
						community1[findName(community1,name1)+x].nRoundsSinceSpecial = -1;

					}

				}

				int damage =(community1[findName(community1,name1)+x].attack) - (community2[findName(community2,name2)+y].defense);

				if(damage > 0){

					if(((community2[findName(community2,name2)+y].remainingHealth) - damage) > 0){

						(community2[findName(community2,name2)+y].remainingHealth)-=damage;
						community2[findName(community2,name2)+y].healthHistory[theRound] = community2[findName(community2,name2)+y].remainingHealth;

					}else{

						(community2[findName(community2,name2)+y].remainingHealth) = 0;
						community2[findName(community2,name2)+y].isAlive = false;
						lastKill2 = community2[findName(community2,name2)+y].name;
						community2[findName(community2,name2)+y].healthHistory[theRound] = 0;
						if(isGameOver(community2)){

							outfile << "Community-1" << endl;
							theRound++;
							break;

						}

					}

				}
			}

		}else{//theRound % 2 == 0

			if(isSpecial == "NO-SPECIAL"){

				int x = 0;

				if(community2[findName(community2,name1)].isAlive == false){

					if(findName(community2,name1) < 4 && community2[findName(community2,name1)+1].isAlive == true){

						x = 1;

					}else if(findName(community2,name1) < 3 && community2[findName(community2,name1)+2].isAlive == true){

						x = 2;

					}else if(findName(community2,name1) < 2 && community2[findName(community2,name1)+3].isAlive == true){

						x = 3;

					}else if(findName(community2,name1) > 0 && community2[findName(community2,name1)-1].isAlive == true){

						x = -1;

					}else if(findName(community2,name1) > 1 && community2[findName(community2,name1)-2].isAlive == true){

						x = -2;

					}else if(findName(community2,name1) > 2 && community2[findName(community2,name1)-3].isAlive == true){

						x = -3;

					}
				}

				int y = 0;

				if(community1[findName(community1,name2)].isAlive == false){

					if(findName(community1,name2) < 4 && community1[findName(community1,name2)+1].isAlive == true){

						y = 1;

					}else if(findName(community1,name2) < 3 && community1[findName(community1,name2)+2].isAlive == true){

						y = 2;

					}else if(findName(community1,name2) < 2 && community1[findName(community1,name2)+3].isAlive == true){

						y = 3;

					}else if(findName(community1,name2) > 0 && community1[findName(community1,name2)-1].isAlive == true){

						y = -1;

					}else if(findName(community1,name2) > 1 && community1[findName(community1,name2)-2].isAlive == true){

						y = -2;

					}else if(findName(community1,name2) > 2 && community1[findName(community1,name2)-3].isAlive == true){

						y = -3;

					}
				}

				int damage =(community2[findName(community2,name1)+x].attack) - (community1[findName(community1,name2)+y].defense);

				if(damage > 0){

					if(((community1[findName(community1,name2)+y].remainingHealth) - damage) > 0){

						(community1[findName(community1,name2)+y].remainingHealth)-=damage;
						community1[findName(community1,name2)+y].healthHistory[theRound] = community1[findName(community1,name2)+y].remainingHealth;

					}else{

						(community1[findName(community1,name2)+y].remainingHealth) = 0;
						community1[findName(community1,name2)+y].isAlive = false;
						lastKill1 = community1[findName(community1,name2)+y].name;
						community1[findName(community1,name2)+y].healthHistory[theRound] = 0;
						if(isGameOver(community1)){

							outfile << "Community-2" << endl;
							theRound++;
							break;

						}

					}

				}

			}else{//somebody tried to use special

				int x = 0;

				if(community2[findName(community2,name1)].isAlive == false){

					if(findName(community2,name1) < 4 && community2[findName(community2,name1)+1].isAlive == true){

						x = 1;

					}else if(findName(community2,name1) < 3 && community2[findName(community2,name1)+2].isAlive == true){

						x = 2;

					}else if(findName(community2,name1) < 2 && community2[findName(community2,name1)+3].isAlive == true){

						x = 3;

					}else if(findName(community2,name1) > 0 && community2[findName(community2,name1)-1].isAlive == true){

						x = -1;

					}else if(findName(community2,name1) > 1 && community2[findName(community2,name1)-2].isAlive == true){

						x = -2;

					}else if(findName(community2,name1) > 2 && community2[findName(community2,name1)-3].isAlive == true){

						x = -3;

					}
				}

				int y = 0;

				if(community1[findName(community1,name2)].isAlive == false){

					if(findName(community1,name2) < 4 && community1[findName(community1,name2)+1].isAlive == true){

						y = 1;

					}else if(findName(community1,name2) < 3 && community1[findName(community1,name2)+2].isAlive == true){

						y = 2;

					}else if(findName(community1,name2) < 2 && community1[findName(community1,name2)+3].isAlive == true){

						y = 3;

					}else if(findName(community1,name2) > 0 && community1[findName(community1,name2)-1].isAlive == true){

						y = -1;

					}else if(findName(community1,name2) > 1 && community1[findName(community1,name2)-2].isAlive == true){

						y = -2;

					}else if(findName(community1,name2) > 2 && community1[findName(community1,name2)-3].isAlive == true){

						y = -3;

					}
				}

				if(community2[findName(community2,name1)+x].type == "Dwarfs" && community2[findName(community2,name1)+x].nRoundsSinceSpecial >= 20){

					(community1[findName(community1,name2)+y].remainingHealth)-=((community2[findName(community2,name1)+x].attack) - (community1[findName(community1,name2)+y].defense));
					community2[findName(community2,name1)+x].nRoundsSinceSpecial = -1;

				}else if(community2[findName(community2,name1)+x].type == "Elves" && community2[findName(community2,name1)+x].nRoundsSinceSpecial >= 10){

					community2[findHobbit(community2)].remainingHealth += ((community2[findName(community2,name1)+x].remainingHealth)-((community2[findName(community2,name1)+x].remainingHealth)/2));
					community2[findName(community2,name1)+x].remainingHealth = (community2[findName(community2,name1)+x].remainingHealth)/2;
					community2[findHobbit(community2)].healthHistory[theRound] = community2[findHobbit(community2)].remainingHealth;
					community2[findName(community2,name1)+x].healthHistory[theRound] = community2[findName(community2,name1)+x].remainingHealth;
					community2[findName(community2,name1)+x].nRoundsSinceSpecial = -1;

				}else if(community2[findName(community2,name1)+x].type == "Wizards" && community2[findName(community2,name1)+x].nRoundsSinceSpecial >= 50){

					if(lastKill2 != "nobody"){

						if(community2[findName(community2,lastKill2)].isAlive == false){
							
							community2[findName(community2,lastKill2)].nRoundsSinceSpecial = 0;

						}
						community2[findName(community2,lastKill2)].isAlive = true;
						community2[findName(community2,lastKill2)].remainingHealth = initialHealths2[findName(community2,lastKill2)];
						community2[findName(community2,lastKill2)].healthHistory[theRound] = community2[findName(community2,lastKill2)].remainingHealth;
						community2[findName(community2,name1)+x].nRoundsSinceSpecial = -1;

					}

				}

				int damage =(community2[findName(community2,name1)+x].attack) - (community1[findName(community1,name2)+y].defense);

				if(damage > 0){

					if(((community1[findName(community1,name2)+y].remainingHealth) - damage) > 0){

						(community1[findName(community1,name2)+y].remainingHealth)-=damage;
						community1[findName(community1,name2)+y].healthHistory[theRound] = community1[findName(community1,name2)+y].remainingHealth;

					}else{

						(community1[findName(community1,name2)+y].remainingHealth) = 0;
						community1[findName(community1,name2)+y].isAlive = false;
						lastKill1 = community1[findName(community1,name2)+y].name;
						community1[findName(community1,name2)+y].healthHistory[theRound] = 0;
						if(isGameOver(community1)){

							outfile << "Community-2" << endl;
							theRound++;
							break;

						}

					}

				}

			}

		}

		for(int i=0; i<5; i++){

			community1[i].nRoundsSinceSpecial++;
			community2[i].nRoundsSinceSpecial++;

		}

		theRound++;

	}

	if(!isGameOver(community1) && !isGameOver(community2)){

		outfile << "Draw" << endl;

	}

	outfile << theRound-1 << endl;

	int theDeathCount = 0;

	for(int i=0; i<5; i++){

		if(community1[i].isAlive == false){

			theDeathCount++;

		}

		if(community2[i].isAlive == false){

			theDeathCount++;

		}

	}

	outfile << theDeathCount << endl;

	outfile << m1.name << " ";

	for(int i=0; i<theRound; i++){

		outfile << (community1[findName(community1,m1.name)]).healthHistory[i] << " ";

	}

	outfile << endl;

	outfile << m2.name << " ";

	for(int i=0; i<theRound; i++){

		outfile << (community1[findName(community1,m2.name)]).healthHistory[i] << " ";

	}

	outfile << endl;

	outfile << m3.name << " ";

	for(int i=0; i<theRound; i++){

		outfile << (community1[findName(community1,m3.name)]).healthHistory[i] << " ";

	}

	outfile << endl;

	outfile << m4.name << " ";

	for(int i=0; i<theRound; i++){

		outfile << (community1[findName(community1,m4.name)]).healthHistory[i] << " ";

	}

	outfile << endl;

	outfile << m5.name << " ";

	for(int i=0; i<theRound; i++){

		outfile << (community1[findName(community1,m5.name)]).healthHistory[i] << " ";

	}

	outfile << endl;

	outfile << n1.name << " ";

	for(int i=0; i<theRound; i++){

		outfile << (community2[findName(community2,n1.name)]).healthHistory[i] << " ";

	}

	outfile << endl;

	outfile << n2.name << " ";

	for(int i=0; i<theRound; i++){

		outfile << (community2[findName(community2,n2.name)]).healthHistory[i] << " ";

	}

	outfile << endl;

	outfile << n3.name << " ";

	for(int i=0; i<theRound; i++){

		outfile << (community2[findName(community2,n3.name)]).healthHistory[i] << " ";

	}

	outfile << endl;

	outfile << n4.name << " ";

	for(int i=0; i<theRound; i++){

		outfile << (community2[findName(community2,n4.name)]).healthHistory[i] << " ";

	}

	outfile << endl;

	outfile << n5.name << " ";

	for(int i=0; i<theRound; i++){

		outfile << (community2[findName(community2,n5.name)]).healthHistory[i] << " ";

	}

	outfile << endl;

	delete[] initialHealths1;
	delete[] initialHealths2;

	infile.close();
	outfile.close();
    return 0;
}