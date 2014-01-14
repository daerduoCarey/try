#This is a python file intending to update voa listening materials from 51voa.com everyday
#Author: mkch

import re,urllib2,os
	
global save_path

def get_fileName(x):
	p_tmp = re.compile('/[^/\.]+.html')
	name = p_tmp.search(x).group()
	end = 1
	while name[len(name)-end] != '-':
		end = end + 1
	return name[1:len(name)-end]

def procedure(x):
	global save_path
	file_name = get_fileName(x)
	print 'NOW to process the file: ' + file_name
	material = urllib2.urlopen("http://www.51voa.com"+x).read()
	if not material:
		print 'Failed to open the webpage'
	else:
		print 'Open the webpage successfully'
	p_mp3 = re.compile('http://down.51voa.com/[0-9]+/[a-z0-9-]*\.mp3')
	target = p_mp3.search(material)
	if not target:
		print 'There is nothing to download on this page'
	else:
		print 'Downloading ...'
		f = urllib2.urlopen(target.group())  
		data = f.read()  
		with open(save_path+file_name+'.mp3', 'wb') as code:  
			    code.write(data)
		print 'Finished.'
	print 'Now to download the script of this file'
	p_script = re.compile('<div id=\"content\">[\s\S]+<div id=\"Bottom_VOA\">')
	file_script = p_script.search(material)
	if not file_script:
		print 'There is no script in this file'
	else:
		print 'Script Downloading ...'
		f_script = open(save_path+file_name+'.doc', 'wb')
		f_script.write(file_script.group())
		f.close()
		print 'Finished.'

def main():
	global save_path
	save_path = 'voa/'
	date = raw_input('Please tell me today\' s date: (XXXX-XX-XX)')
	datep = re.compile('\d+')
	dates = datep.findall(date)
	save_path = save_path+dates[0]+dates[1]+dates[2]+'/';
	url_start = 'http://www.51voa.com/VOA_Standard_'
	local_path = '/home/mkch/voa/'
	content = urllib2.urlopen(url_start+'1'+'.html').read()
	if not os.path.isdir("voa"):
		os.makedirs("voa")
	if not os.path.isdir(save_path):
		os.makedirs(save_path)
	p = re.compile('/VOA_Standard_English/[a-z0-9-]*\.html')
	m = p.findall(content)
	if not m:
		print 'nothing'
	else:
		k = 0
		f = 1
		while m[k] and f:
			procedure(m[k])
			k = k + 1
			if not k%1:
				flag = raw_input('Want to download more 10 pieces?(y or n) : ')
				if flag=='n':
					f=0
	

main()
	
