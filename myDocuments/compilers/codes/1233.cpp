#include<cstdio>
#include<algorithm>
#include<iostream>
using namespace std;
struct node
{
	int no,totn;
	node *next;
	node(int non=0,int tott=0,node *ne=NULL) {no=non;totn=tott;next=ne;}
}p[11];
int n,m,s,l,ans=0,tot=0,d[11]={0},add=1;
void dfs(int k)
{
	if(tot==l) 
	{
		ans+=add;
	}
	node *q=p[k].next;
	while(q)
	{
		if(!d[q->no])
		{
			++tot;d[q->no]=1;
			add*=q->totn;
			dfs(q->no);
			add/=q->totn;
			--tot;d[q->no]=0;
		}
		q=q->next;
	}
	return;
}
int main()
{
	freopen("1233.in","r",stdin);
	node *q;
	scanf("%d%d%d%d",&n,&m,&s,&l);
	for(int i=1;i<=m;++i)
	{
		int x,y;
		scanf("%d%d",&x,&y);
		q=p[x].next;
		int f=0;
		while(q)
		{
			if(q->no==y) {f=1;++q->totn;break;}
			q=q->next;
		}
		if(!f)
		{
			q=new node(y,1,p[x].next);
			p[x].next=q;
		}
	}
	d[s]=1;
	dfs(s);
	printf("%d\n",ans);
	return 0;
}
