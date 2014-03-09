#include<iostream>
#include<cstdio>
#include<cstdlib>
using namespace std;
int d[3][3]={0},x[3][3]={0},ans[3][3]={0};
void multi()
{
	int tmp[3][3]={0};
	for(int i=1;i<=2;++i)
		for(int j=1;j<=2;++j)
			for(int k=1;k<=2;++k)
				tmp[i][j]=(tmp[i][j]+ans[i][k]*x[k][j])%7;
	for(int i=1;i<=2;++i)
		for(int j=1;j<=2;++j)
			ans[i][j]=tmp[i][j];
}
void multi2()
{
	int tmp[3][3]={0};
	for(int i=1;i<=2;++i)
		for(int j=1;j<=2;++j)
			for(int k=1;k<=2;++k)
				tmp[i][j]=(tmp[i][j]+x[i][k]*x[k][j])%7;
	for(int i=1;i<=2;++i)
		for(int j=1;j<=2;++j)
			x[i][j]=tmp[i][j];
}
void mul(int k)
{
	while(k){
		if(k%2) multi();
		multi2();
		k/=2;	
	}
}
int main()
{
	int a,b,n;
	scanf("%d%d%d",&a,&b,&n);
	a%=7;b%=7;
	d[1][2]=1;d[2][1]=b;d[2][2]=a;
	x[1][2]=1;x[2][1]=b;x[2][2]=a;
	ans[1][1]=ans[2][2]=1;
	mul(n-2);
	printf("%d\n",(ans[2][1]+ans[2][2])%7);
	return 0;
}
