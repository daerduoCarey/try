#include<iostream>
#include<cstdio>
#include<cstdlib>
#include<algorithm>
#include<map>
#include<list>
#include<string>
#include<fstream>
#include<queue>
#include<vector>

#define MaxPOIPerNode 3
#define PI 3.1415926

using namespace std;

struct point
{
	double x,y;

	point() {}
	point(double xx, double yy)
	{
		x = xx;
		y = yy;
	}
	point(const point& obj)
	{
		x = obj.x;
		y = obj.y;
	}
};

struct rectangle
{
	struct point upperLeft,bottomRight;

	rectangle() {}
	rectangle(struct point x, struct point y)
	{
		upperLeft = x;
		bottomRight = y;
	}
	rectangle(const rectangle& obj)
	{
		upperLeft = obj.upperLeft;
		bottomRight = obj.bottomRight;
	}
};

struct circle
{
	struct point center;
	double radius;

	circle(struct point c, double r)
	{
		center = c;
		radius = r;
	}
};

struct POI
{
	string id;
	struct point p;
	int cat;

	POI(string i, int c, double x, double y)
	{
		id = i;
		cat = c;
		p.x = x;
		p.y = y;
	}
};

struct kdNode
{
	bool flag, isLeaf;
	POI* split;
	struct kdNode *left, *right;
	list<POI*> data;
	double minx, miny, maxx, maxy;
	
	kdNode(list<POI*> l)
	{
		data = l;
		isLeaf = true;
	}
	kdNode(bool f, POI* s, kdNode* l, kdNode* r)
	{
		flag = f;
		split = s;
		left = l ;
		right = r;
		isLeaf = false;
	}
};

struct combine
{
	double minx, miny, maxx, maxy;

	combine(double x, double y)
	{
		minx = maxx = x;
		miny = maxy = y;
	}
	combine(double x1, double x2, double y1, double y2)
	{
		minx = x1; maxx = x2;
		miny = y1; maxy = y2;
	}
};

class Search
{
	public:
		Search() {}
		~Search() {}

		list<POI*> KNNQuery(struct point p, int cat, int index);
		list<POI*> RangeQuery(struct circle range, int cat);
		list<POI*> RangeQuery(struct rectangle range, int cat);
		void IndexBuilding(string file_name);

		list<POI*> Scan(struct rectangle, int cat, string file);
		list<POI*> Scan(struct circle, int cat, string file);
		list<POI*> KNNScan(struct point p, int cat, int index, string file);

	private:
		ifstream fin;
		map<int, list<POI*>* > table;
		map<int, kdNode* > trees;
		
		int abs(int x);
		list<POI*> RangeQuery(struct rectangle range, kdNode* r, bool flag);
		kdNode* split(list<POI*> l, bool flag, combine *com);	//flag=true means to split x
		double dist(const point& p1, const point& p2);
		double Rad(double d);
		double dist_from_point_to_rect(const point& p, const kdNode* node);
		bool point_in_range(const struct point& p, const struct rectangle& range);
		bool point_in_range(const struct point& p, const struct circle& range);
		
		//KNN global data
		struct Pair
		{
			POI* poi;
			double dist;
			Pair(POI* p, double d)
			{
				poi = p;
				dist = d;
			}
			friend bool operator<(const Pair& p1, const Pair& p2)
			{
				return p1.dist<p2.dist;
			}
		};
		point knn_p;
		int knn_k;
		priority_queue<Pair> knn_q;
		void KNNQuery(kdNode* r);

		//for KNNScan
		struct InversePair
		{
			POI* poi;
			double dist;
			InversePair(POI* p, double d)
			{
				poi = p;
				dist = d;
			}
			friend bool operator<(const InversePair& p1, const InversePair& p2)
			{
				return p1.dist>p2.dist;
			}
		};

	//only for testing and debugging
	public:
		void test(int cat);
		void bianli(kdNode* r);
};
