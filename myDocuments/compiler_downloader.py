import re,urllib2,urllib,os,time,sys,shutil

global save_path,output

def get_file_name(x):
	p = re.compile('recoded_videos.+?\.mp4')
	m = p.findall(x)
	if not m:
		print "error"
	else:
		m = m[0]
		return m[15:len(m)]

def procedure(x):
	print x
	p_num = re.compile('[0-9]+')
	num = p_num.findall(x)
	num = num[0]
	global save_path,output
	content = urllib2.urlopen(x).read()
	p = re.compile('http://.*?\.mp4')
	m = p.findall(content)
	if not m:
		print 'There is no files to download.'
	elif len(m)==1:
		print 'Process: ' + m[0]
		print 'Downloading...'
		file_name = get_file_name(m[0])
		print 'Finished.'
		output.write('wget -O '+save_path+file_name+' '+m[0]+'\n')
		output.write('wget -O '+save_path+file_name+'.ch.src https://class.coursera.org/compilers/lecture/subtitles?q='+str(num)+'_zh-cn\n')
		output.write('wget -O '+save_path+file_name+'.eg.src https://class.coursera.org/compilers/lecture/subtitles?q='+str(num)+'_en\n')
	else:
		print 'More than 1 files. Must be an error.'

def main():
	global save_path,output
	k_st=31
	k_end=32
	output=open('compiler_shell2','w')
	if not os.path.isdir("compilers"):
		os.makedirs("compilers")
	save_path='compilers/'
	url = 'https://class.coursera.org/compilers/lecture'
	url_o = urllib2.urlopen(url)
	if not url_o:
		print 'Not successfully open the URL'
		exit()
	else:
		content = url_o.read()
	p_re = 'https://.*?view\?lecture_id=[0-9]+'
	p = re.compile(p_re)
	m = p.findall(content)
	if not m:
		print 'There is no videos to download.'
		shutil.rmtree(save_path)
	else:
		for i in range(len(m)):
			print m[i]
		flag = ''
		while flag!='y' and flag!='n':
			flag = raw_input('There are '+str(len(m))+' files to download. NOW BEGIN?')
		if flag=='n':
			print 'Job Done.'
		else:
			print 'Beginning...'
			k=k_st-1
			while k<min(k_end,len(m)):
				procedure(m[k])
				k = k + 1
			print 'Job Done.'
	output.close()

main()

