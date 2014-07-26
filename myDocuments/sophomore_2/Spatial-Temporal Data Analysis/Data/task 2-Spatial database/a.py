#!/usr/bin/python

import csv

def main():
	reader = csv.reader(file("POI_jiaotong.txt",'rb'))
	k=0
	for l in reader:
		k+=1
		if(k>1):
			print "%s %d %f %f" %(l[0],int(l[9]),float(l[14]),float(l[15]))

main()
