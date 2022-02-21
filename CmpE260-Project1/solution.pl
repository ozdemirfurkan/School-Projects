%furkan ozdemir
%2018400201
%compiling: yes
%complete: yes

%include the knowledge base
:- ['load.pro'].

%this predicate calculate difference between features and expects.
calculate_difference([], [], []).
calculate_difference([-1], _, []).
calculate_difference([H1|T1], [H2|T2], [HeadResult|TailResult]):-
	(H1 = -1,calculate_difference(T1, T2, [HeadResult|TailResult]), !);
	(HeadResult is (H1-H2)*(H1-H2), calculate_difference(T1, T2, TailResult)).

%this predicate calculate weighted difference between features and expects.
calculate_weighted_difference([], [], [], []).
calculate_weighted_difference([-1], _, _, []).
calculate_weighted_difference([H1|T1], [H2|T2], [H3|T3], [HeadResult|TailResult]):-
	(H1 = -1, calculate_weighted_difference(T1, T2, T3, [HeadResult|TailResult]), !);
	(HeadResult is H3*(H1-H2)*(H1-H2), calculate_weighted_difference(T1, T2, T3, TailResult)).
	
calculate_distance(List, Result):-
	sum_list(List, R1),
	Result is sqrt(R1).

%this predicate divide dashed list	
divide_dashed_list([], [], []).
divide_dashed_list([Head|Tail], [H1|T1], [H2|T2]):-
	H1-H2 = Head,
	divide_dashed_list(Tail, T1, T2).

%finds possible pairs for 3.6	
find_pairs(Name1, Name2, Distance):-
	glanian_distance(Name1,Name2, Distance),
	expects(Name1, GenderList, _),
	member(X, GenderList),
	glanian(Name2, X, _).
	
%finds possible pairs for 3.7	
find_weighted_pairs(Name1, Name2, Distance):-
	weighted_glanian_distance(Name1,Name2, Distance),
	expects(Name1, GenderList, _),
	member(X, GenderList),
	glanian(Name2, X, _).
	
% 3.1 glanian_distance(Name1, Name2, Distance) 5 points
glanian_distance(Name1, Name2, Distance):-
	expects(Name1, _, ExpectedFeatures),
	glanian(Name2, _, GlanianFeatures),
	calculate_difference(ExpectedFeatures, GlanianFeatures, ResultList),
	calculate_distance(ResultList, Distance).
	

% 3.2 weighted_glanian_distance(Name1, Name2, Distance) 10 points
weighted_glanian_distance(Name1, Name2, Distance):-
	expects(Name1, _, ExpectedFeatures),
	weight(Name1, WeightList),
	glanian(Name2, _, GlanianFeatures),
	calculate_weighted_difference(ExpectedFeatures, GlanianFeatures, WeightList, ResultList),
	calculate_distance(ResultList,Distance).

% 3.3 find_possible_cities(Name, CityList) 5 points
find_possible_cities(Name, [HeadResult|TailResult]):-
	city(X, List1, _), 
	member(Name, List1), !,
	likes(Name, _, List2),
	HeadResult = X,
	TailResult = List2.

% 3.4 merge_possible_cities(Name1, Name2, MergedCities) 5 points
merge_possible_cities(Name1, Name2, MergedCities):-
	find_possible_cities(Name1, List1),
	find_possible_cities(Name2, List2),
	union(List1, List2, MergedCities).

% 3.5 find_mutual_activities(Name1, Name2, MutualActivities) 5 points
find_mutual_activities(Name1, Name2, MutualActivities):-
	likes(Name1, List1, _),
	likes(Name2, List2, _),
	intersection(List1, List2, MutualActivities).

% 3.6 find_possible_targets(Name, Distances, TargetList) 10 points
find_possible_targets(Name1, Distances, TargetList):-
	findall(Distance-Name2, find_pairs(Name1, Name2, Distance), Pairs),
	keysort(Pairs, Sorted),
	divide_dashed_list(Sorted, Distances, TargetList).
		

% 3.7 find_weighted_targets(Name, Distances, TargetList) 15 points
find_weighted_targets(Name1, Distances, TargetList):-
	findall(Distance-Name2, find_weighted_pairs(Name1, Name2, Distance), Pairs),
	keysort(Pairs, Sorted),
	divide_dashed_list(Sorted, Distances, TargetList).

% this predicate is used for tolerance limits of glanians	
is_tolareted([],_).
is_tolareted([H1|T1], [H2|T2]):-
	(length(H2,Length),Length = 0,is_tolareted(T1,T2));
	H2 = [Head|[H|_]],
	H1>Head, H1<H,
	is_tolareted(T1,T2).
	
% conditions for 3.8
conditions_of_eight(Name, Distance, Activity, City, Target):-
	glanian(Target, Gender, Features),
	not(old_relation([Name, Target])),
	expects(Name, ExpectedGenders, _),
	member(Gender, ExpectedGenders),
	dislikes(Name, DislikedActivities, DislikedCities, Limits),
	((find_possible_cities(Name,C),member(City,C),city(City, _, CityActivities),member(Activity,CityActivities),not(member(Activity,DislikedActivities)));
	(likes(Name, LikedActivities, _),city(City, _, Activities),intersection(LikedActivities,Activities,S),length(S,Length),Length>0),member(Activity,S),not(member(Activity,DislikedActivities))),
	not(member(City,DislikedCities)),
	is_tolareted(Features,Limits),
	weighted_glanian_distance(Name,Target,Distance),
	likes(Target, LikedActivities2, _),
	intersection(LikedActivities2,DislikedActivities,I),
	length(I,L),L<3,
	merge_possible_cities(Name,Target,MergedCities),
	member(City,MergedCities).	
	
	
% 3.8 find_my_best_target(Name, Distances, Activities, Cities, Targets) 20 points
find_my_best_target(Name, Distances, Activities, Cities, Targets):-
	findall(Distance-Activity-City-Target, conditions_of_eight(Name, Distance, Activity, City, Target), Pairs),
	list_to_set(Pairs,PairSet),
	keysort(PairSet,Sorted),
	divide_dashed_list(Sorted,Sorted2,Targets),
	divide_dashed_list(Sorted2,Sorted3,Cities),
	divide_dashed_list(Sorted3,Distances,Activities).
	
% conditions for 3.9
conditions_of_nine(Name, Distance, Activity, City, Target):-
	glanian(Target, TargetGender, TargetFeatures),
	glanian(Name, NameGender, NameFeatures),
	not(old_relation([Name, Target])),
	expects(Name, NameExpectedGenders, _),
	member(TargetGender, NameExpectedGenders),
	expects(Target, TargetExpectedGenders, _),
	member(NameGender, TargetExpectedGenders),
	dislikes(Name, NameDislikedActivities, NameDislikedCities, NameLimits),
	dislikes(Target, TargetDislikedActivities, TargetDislikedCities, TargetLimits),
	((find_possible_cities(Name,C),member(City,C),city(City, _, CityActivities),member(Activity,CityActivities),not(member(Activity,NameDislikedActivities)));
	(likes(Name, NameLikedActivities1, _),city(City, _, Activities),intersection(NameLikedActivities1,Activities,S),length(S,Length),Length>0),member(Activity,S),not(member(Activity,NameDislikedActivities))),
	((find_possible_cities(Target,C1),member(City,C1),city(City, _, CityActivities1),member(Activity,CityActivities1),not(member(Activity,TargetDislikedActivities)));
	(likes(Target, TargetLikedActivities1, _),city(City, _, Activities),intersection(TargetLikedActivities1,Activities,S),length(S,Length),Length>0),member(Activity,S),not(member(Activity,TargetDislikedActivities))),
	not(member(City,NameDislikedCities)),
	not(member(City,TargetDislikedCities)),
	is_tolareted(TargetFeatures,NameLimits),
	is_tolareted(NameFeatures,TargetLimits),
	weighted_glanian_distance(Name,Target,Distance1),
	weighted_glanian_distance(Target, Name, Distance2),
	Distance is (Distance1+Distance2)/2,
	likes(Target, LikedActivities2, _),
	intersection(LikedActivities2, NameDislikedActivities,I1),
	length(I1,L1),L1<3,
	likes(Name, NameLikedActivities2, _),
	intersection(NameLikedActivities2, TargetDislikedActivities,I2),
	length(I2,L2),L2<3,
	merge_possible_cities(Name,Target,MergedCities),
	member(City,MergedCities).
	

% 3.9 find_my_best_match(Name, Distances, Activities, Cities, Targets) 25 points
find_my_best_match(Name, Distances, Activities, Cities, Targets):-
	findall(Distance-Activity-City-Target, conditions_of_nine(Name, Distance, Activity, City, Target), Pairs),
	list_to_set(Pairs,PairSet),
	keysort(PairSet,Sorted),
	divide_dashed_list(Sorted,Sorted2,Targets),
	divide_dashed_list(Sorted2,Sorted3,Cities),
	divide_dashed_list(Sorted3,Distances,Activities).