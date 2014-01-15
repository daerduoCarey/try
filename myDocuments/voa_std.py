#This is a python file intending to update voa listening materials from 51voa.com everyday
#Author: mkch

import re,urllib2,os,time,sys,shutil
	
global save_path
global year,month,day

def check(x):
	p_data = re.compile('[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')
	m = p_data.match(x)
	if not m:
		return 0
	m = m.group()
	if x==m:
		return 1
	else:
		return 0

def get_fileName(x):
	global url_end
	p_tmp = re.compile('/[^/\.]+.html')
	name = p_tmp.search(x).group()
	url_end = name
	end = 1
	while name[len(name)-end] != '-':
		end = end + 1
	return name[1:len(name)-end]

def get_date():
	global year,month,day
	year = int(time.strftime('%Y',time.localtime(time.time())))
	month = int(time.strftime('%m',time.localtime(time.time())))
	day = int(time.strftime('%d',time.localtime(time.time())))
	return time.strftime('%Y%m%d',time.localtime(time.time()))

def procedure(x):
	global save_path,url_end
	file_name = get_fileName(x)
	print 'NOW to process the file: ' + file_name
	material = urllib2.urlopen("http://www.51voa.com/VOA_Standard_English"+url_end).read()
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
		#urllib.urlretrieve(target.group(), save_path+file_name+'.mp3')
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
		f_script.close()
		print 'Finished.'

def main():
	global save_path,year,month,day
	save_path = 'voa/'
	
	if len(sys.argv)==1:
		k = 0
		date = get_date()
		print 'No parameter been found, update today\'s materials...'
	else:
		date = sys.argv[1]
		if not check(date):
			print 'Invalid Parameter, error!'
			sys.exit(0)
		print 'Parameter been found, update '+date+'\'s materials...'
		if get_date()!=date:
			k = 1
		year = int(date[0:4])
		month = int(date[4:6])
		day = int(date[6:8])
	
	save_path = save_path+date+'/';
	url_start = 'http://www.51voa.com/'
	
	if not os.path.isdir("voa"):
		os.makedirs("voa")
	if not os.path.isdir(save_path):
		os.makedirs(save_path)
	else: 
		flag = ''
		while flag!='y' and flag !='n':
			flag = raw_input('You already download this day\'s materials. Cover them and download again?(y or n): ')
		if flag == 'y':
			shutil.rmtree(save_path)
			os.makedirs(save_path)
		else:
			print 'Job done!'
			sys.exit(0)

	content = urllib2.urlopen(url_start).read()
	p_re = '<a href=\"/VOA_Standard_English/.*?\('+str(year)+'-'+str(month)+'-'+str(day)+'\)'
	p = re.compile(p_re)
	m = p.findall(content)
	if (not m) or (len(m)==k):
		print 'There is no file to download available'
		shutil.rmtree(save_path)
	else:
		flag = ''
		while flag!='y' and flag!='n':
			flag = raw_input('There are '+str(len(m)-k)+' files available, download now?(y or n): ')
		if flag=='y':
			while k<len(m):
				procedure(m[k])
				k = k + 1
			print 'Job done!'
		else:
			print 'Job done!'
			shutil.rmtree(save_path)
			sys.exit()

if __name__ == '__main__':
	main()

	
