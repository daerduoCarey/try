#include "POI.h"
#include<cmath>

using namespace std;

bool cmpx(const POI* obj1, const POI* obj2)
{
	if(obj1->p.x<obj2->p.x) return true;
	else return false;
}

bool cmpy(const POI* obj1, const POI* obj2)
{
	if(obj1->p.y<obj2->p.y) return true;
	else return false;
}

bool Search::point_in_range(const struct point& p, const struct rectangle& range)
{
	if(p.x>=range.upperLeft.x&&p.x<=range.bottomRight.x)
	{
		if(p.y>=range.upperLeft.y&&p.y<=range.bottomRight.y)
			return true;
	}
	return false;
}

bool Search::point_in_range(const struct point& p, const struct circle& range)
{
	if(dist(p, range.center)<=range.radius)
		return true;
	return false;
}

double Search::dist_from_point_to_rect(const point& p, const kdNode* node)
{
	double minx = node->minx, maxx = node->maxx;
	double miny = node->miny, maxy = node->maxy;

	if(p.x<minx)
	{
		if(p.y<miny) return dist(point(minx, miny), p);
		else if(p.y>maxy) return dist(point(minx, maxy), p);
		else return dist(point(minx, p.y), p);
	}
	else if(p.x>maxx)
	{
		if(p.y<miny) return dist(point(maxx, miny), p);
		else if(p.y>maxy) return dist(point(maxx, maxy), p);
		else return dist(point(maxx, p.y), p);
	}
	else
	{
		if(p.y<miny) return dist(point(p.x, miny), p);
		else if(p.y>maxy) return dist(point(p.x, maxy), p);
		else return 0;
	}
}

double Search::Rad(double d)
{
	return d * PI / 180.0;
}

double Search::dist(const point& p1, const point& p2)
{
	double lat1 = p1.y, lat2 = p2.y;
	double lon1 = p1.x, lon2 = p2.x;

	double radLat1 = Rad(lat1);
	double radLat2 = Rad(lat2);
        double delta_lon = Rad(lon2 - lon1);
        double top_1 = cos(radLat2) * sin(delta_lon);
        double top_2 = cos(radLat1) * sin(radLat2) - sin(radLat1) * cos(radLat2) * cos(delta_lon);
        double top = sqrt(top_1 * top_1 + top_2 * top_2);
        double bottom = sin(radLat1) * sin(radLat2) + cos(radLat1) * cos(radLat2) * cos(delta_lon);
        double delta_sigma = atan2(top, bottom);
        double distance = delta_sigma * 6378137.0;
        return distance;
}

void Search::IndexBuilding(string file_name)
{
	fin.open(file_name.c_str());

	string id;
	int cat;
	double x,y;

	//new table mapping each category to a distinct kd-tree
	table = map<int, list<POI*>* >();
	map<int, combine*> record;

	//classify all POIs
	while(fin>>id>>cat>>x>>y)
	{
		POI* poi = new POI(id, cat, x, y);

		map<int, list<POI*>* >::iterator ite;
		ite = table.find(cat);
		if(ite==table.end())
		{
			list<POI*>* l = new list<POI*>();
			l->push_back(poi);
			table.insert(pair<int, list<POI*>* >(cat, l));

			record.insert(pair<int, combine*>(cat, new combine(x,y)));
		}
		else
		{
			list<POI*>* l = ite->second;
			l->push_back(poi);
			combine* com = record.find(cat)->second;
			
			if(x>com->maxx) com->maxx = x;
			if(x<com->minx) com->minx = x;
			if(y<com->miny) com->miny = y;
			if(y>com->maxy) com->maxy = y;
		}
	}

	fin.close();

	//set up all kd-tree's
	trees = map<int, kdNode* >();

	map<int, list<POI*>* >::iterator ite;
	
	for(ite=table.begin();ite!=table.end();++ite)
	{
		list<POI*>* l = ite->second;

		combine *com = record.find(ite->first)->second;
		kdNode* root = split(*l, true, com);
		root->minx = com->minx;
		root->maxx = com->maxx;
		root->miny = com->miny;
		root->maxy = com->maxy;
		
		trees.insert(pair<int, kdNode*>(ite->first, root));
	}
}

int Search::abs(int x)
{
	if(x<0) return -x;
	return x;
}

kdNode* Search::split(list<POI*> l, bool flag, combine *com)
{
	if(l.size()<=MaxPOIPerNode) 
		return new kdNode(l);
	else
	{
		if(flag) l.sort(cmpx);
		else l.sort(cmpy);
		
		int k=0;
		list<POI*> newList;
		while(abs(l.size()-k)>1)
		{
			newList.push_back(l.front());
			l.pop_front();
			++k;
		}
	
		POI* split_poi = l.front();
		l.pop_front();
	
		double minx, miny, maxx, maxy;
		minx = com->minx; maxx = com->maxx; 
		miny = com->miny; maxy = com->maxy;
		if(flag) maxx = split_poi->p.x;
		else maxy = split_poi->p.y;
		kdNode* le = split(newList, !flag, new combine(minx, maxx, miny, maxy));
		le->minx = minx; le->maxx = maxx;
		le->miny = miny; le->maxy = maxy;

		minx = com->minx; maxx = com->maxx; 
		miny = com->miny; maxy = com->maxy;
		if(flag) minx = split_poi->p.x;
		else miny = split_poi->p.y;
		kdNode* r = split(l, !flag, new combine(minx, maxx, miny, maxy));
		r->minx = minx;	r->maxx = maxx;
		r->miny = miny; r->maxy = maxy;
		
		return new kdNode(flag, split_poi, le, r);
	}
}

list<POI*> Search::RangeQuery(struct rectangle range, int cat)
{
	map<int, kdNode*>::iterator ite = trees.find(cat);
	if(ite==trees.end()) 
	{
		list<POI*> res;
		return res;
	}
	
	kdNode* root = ite->second;
	return RangeQuery(range, root, true);
}

list<POI*> Search::RangeQuery(struct rectangle range, kdNode* r, bool flag)
{
	list<POI*> res;
	if(r->isLeaf)
	{
		list<POI*> l = r->data;
		for(list<POI*>::iterator i=l.begin();i!=l.end();++i)
		{
			if(point_in_range((*i)->p, range))
				res.push_back(*i);
		}
	}
	else
	{
		struct point upperLeft = range.upperLeft;
		struct point bottomRight = range.bottomRight;
		if(flag)
		{
			if(r->split->p.x>upperLeft.x&&r->split->p.x<bottomRight.x)
			{
				res = RangeQuery(rectangle(upperLeft, point(r->split->p.x, bottomRight.y)), r->left, false);
				list<POI*> tmp = RangeQuery(rectangle(point(r->split->p.x, upperLeft.y), bottomRight), r->right, false);
				for(list<POI*>::iterator ite=tmp.begin();ite!=tmp.end();++ite)
					res.push_back(*ite);
			}
			else if(r->split->p.x<=upperLeft.x)
			{
				res = RangeQuery(range, r->right, false);
			}
			else
			{
				res = RangeQuery(range, r->left, false);
			}
		}
		else
		{
			if(r->split->p.y>upperLeft.y&&r->split->p.y<bottomRight.y)
			{
				res = RangeQuery(rectangle(upperLeft, point(bottomRight.x, r->split->p.y)), r->left, true);
				list<POI*> tmp = RangeQuery(rectangle(point(upperLeft.x, r->split->p.y), bottomRight), r->right, true);
				for(list<POI*>::iterator ite=tmp.begin();ite!=tmp.end();++ite)
					res.push_back(*ite);
			}
			else if(r->split->p.y<=upperLeft.y)
			{
				res = RangeQuery(range, r->right, true);
			}
			else
			{
				res = RangeQuery(range, r->left, true);
			}
		}
		if(point_in_range(r->split->p, range))
			res.push_back(r->split);
	}
	return res;
}

list<POI*> Search::RangeQuery(struct circle range, int cat)
{
	list<POI*> poss = RangeQuery(rectangle(point(range.center.x-range.radius, range.center.y-range.radius), point(range.center.x+range.radius, range.center.y+range.radius)), cat);
	list<POI*> res;
	for(list<POI*>::iterator ite=poss.begin();ite!=poss.end();++ite)
	{
		if(dist(range.center, (*ite)->p) < range.radius)
		{
			res.push_back(*ite);
		}
	}
	return res;
}

list<POI*> Search::KNNQuery(struct point p, int cat, int index)
{
	knn_q = priority_queue<Pair>();
	knn_k = index; 
	knn_p = p;

	map<int, kdNode*>::iterator ite = trees.find(cat);
	list<POI*> res;
	if(ite==trees.end()) return res;
	kdNode* root = ite->second;
	KNNQuery(root);
	int k=knn_k;
	while(k>0&&!knn_q.empty())
	{
		res.push_back(knn_q.top().poi);
		cout<<knn_q.top().poi->id<<": "<<knn_q.top().dist<<endl;
		knn_q.pop();
		--k;
	}
	res.reverse();
	return res;
}

void Search::KNNQuery(kdNode* r)
{
	cout<<"!!!!"<<r->minx<<" "<<r->maxx<<"; "<<r->miny<<" "<<r->maxy<<endl;
	if(r->isLeaf)
	{
		for(list<POI*>::iterator ite=r->data.begin();ite!=r->data.end();++ite)
		{
			double new_dist = dist((*ite)->p, knn_p);
			knn_q.push(Pair(*ite, new_dist));
			if(knn_q.size()>knn_k) knn_q.pop();
		}
	}
	else
	{
		double new_dist = dist(knn_p, r->split->p);
		//cout<<"("<<knn_p.x<<", "<<knn_p.y<<")  ";
		//cout<<"("<<r->split->p.x<<", "<<r->split->p.y<<")   ";
		//cout<<r->split->id<<": "<<new_dist<<" "<<(knn_q.empty()? -1:knn_q.top().dist)<<endl;
		knn_q.push(Pair(r->split, new_dist));
		if(knn_q.size()>knn_k) knn_q.pop();
		
		double left_dist = dist_from_point_to_rect(knn_p, r->left);
		double right_dist = dist_from_point_to_rect(knn_p, r->right);
		
		double max_dist = knn_q.top().dist;
		if(left_dist<right_dist)
		{
			if(left_dist<max_dist||knn_q.size()<knn_k) KNNQuery(r->left);
			max_dist = knn_q.top().dist;
			if(right_dist<max_dist||knn_q.size()<knn_k) KNNQuery(r->right);
		}
		else
		{
			if(right_dist<max_dist||knn_q.size()<knn_k) KNNQuery(r->right);
			max_dist = knn_q.top().dist;
			if(left_dist<max_dist||knn_q.size()<knn_k) KNNQuery(r->left);
		}
	}
}

void Search::test(int cat)
{
	map<int, list<POI*>* >::iterator ite = table.find(cat);
	if(ite==table.end())
	{
		cout<<"Sorry, not found!"<<endl;
	}
	else
	{
		cout<<"OK, here they are: "<<endl;
		list<POI*>* l = ite->second;
		cout<<l->size()<<endl;
		for(list<POI*>::iterator i=l->begin();i!=l->end();++i)
		{
			cout<<"\t"<<(*i)->id<<"\t"<<(*i)->p.x<<"\t"<<(*i)->p.y<<endl;
		}
		map<int, kdNode*>::iterator it = trees.find(cat);
		cout<<"Following is the tree!"<<endl;
		kdNode* root = it->second;
		bianli(root);
	}
}

void Search::bianli(kdNode* r)
{
	cout<<"{";
	if(r->isLeaf) 
	{
		for(list<POI*>::iterator ite=r->data.begin();ite!=r->data.end();++ite)
			cout<<(*ite)->id<<" ";
		cout<<"}"<<endl;
	}
	else 
	{
		cout<<r->split->id<<":";
		bianli(r->left);
		bianli(r->right);
		cout<<"}";
	}
}

list<POI*> Search::Scan(struct rectangle range, int cat, string file)
{
	ifstream fin2(file.c_str());

	list<POI*> l;

	string id;
	int c;
	double x, y;

	while(fin2>>id>>c>>x>>y)
	{
		if(c==cat&&point_in_range(point(x,y), range))
		{
			POI* poi = new POI(id, cat, x, y);
			l.push_back(poi);
		}
	}

	fin2.close();

	return l;
}

list<POI*> Search::Scan(struct circle range, int cat, string file)
{
	ifstream fin2(file.c_str());

	list<POI*> l;

	string id;
	int c;
	double x, y;

	while(fin2>>id>>c>>x>>y)
	{
		if(c==cat&&point_in_range(point(x,y), range))
		{
			POI* poi = new POI(id, cat, x, y);
			l.push_back(poi);
		}
	}

	fin2.close();

	return l;
}

list<POI*> Search::KNNScan(struct point p, int cat, int index, string file)
{
	ifstream fin2(file.c_str());

	list<POI*> l;

	string id;
	int c;
	double x, y;

	priority_queue<InversePair> q;

	while(fin2>>id>>c>>x>>y)
	{
		if(c==cat)
		{
			POI* poi = new POI(id, c, x, y);
			q.push(InversePair(poi, dist(p, point(x, y))));
		}
	}

	while(index>0&&!q.empty())
	{
		l.push_back(q.top().poi);
		q.pop();
		--index;
	}

	fin2.close();

	return l;
}
