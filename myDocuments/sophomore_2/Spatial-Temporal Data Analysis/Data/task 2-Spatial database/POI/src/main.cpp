#include<iostream>
#include<cstdio>
#include<string>
#include "POI.h"

using namespace std;

const int CATEGORY =200300;

int main(int args, char** argv)
{
	Search engine;
	engine.IndexBuilding("input");
	engine.test(CATEGORY);
	
	cout<<"------------------Rectangle RangeQuery--------------------"<<endl;
	list<POI*> res = engine.RangeQuery(rectangle(point(121.44,31.02), point(121.46,31.04)),CATEGORY);
	for(list<POI*>::iterator ite=res.begin();ite!=res.end();++ite)
		cout<<(*ite)->id<<endl;
	cout<<res.size()<<endl;
	
	cout<<"----------------------Circle RangeQuery------------------"<<endl;
	res = engine.RangeQuery(circle(point(121.411,31.042), 2000),CATEGORY);
	for(list<POI*>::iterator ite=res.begin();ite!=res.end();++ite)
		cout<<(*ite)->id<<endl;
	cout<<res.size()<<endl;


	cout<<"-------------------KNN Query-----------------------------"<<endl;
	res = engine.KNNQuery(point(121.411,31.042), CATEGORY, 2);
	cout<<"\nRank:"<<endl;
	int rank=0;
	for(list<POI*>::iterator ite=res.begin();ite!=res.end();++ite)
		cout<<++rank<<": "<<(*ite)->id<<endl;
	cout<<res.size()<<endl;
}
