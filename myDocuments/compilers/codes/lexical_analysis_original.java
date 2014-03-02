public class lexical_analysis
{
	private static int tot = 0;
	private static boolean isInteger(String x)
	{
		int[][] a=new int[4][12];
		a[1][10]=a[1][11]=1;
		for(int i=0;i<10;++i)
			a[1][i]=a[2][i]=a[3][i]=3;
		char[] input = x.toCharArray();
		char cur;
		int state = 1;
		for(int i=0;i<x.length();++i)
		{
			cur = input[i];
			if(cur>='0'&&cur<='9')
				state = a[state][cur-'0'];
			else if(cur=='+')
				state = a[state][10];
			else if(cur=='-')
				state = a[state][11];
			else 
				return false;
			if(state==0) return false;
		}
		if(state==3) return true;
		return false;
	}
	private static boolean isString(String x)
	{
		int[][] a=new int[2][26*2];
		for(int i=0;i<26*2;++i)
			a[1][i]=a[0][i]=1;
		char[] input = x.toCharArray();
		char cur;
		int state = 0;
		for(int i=0;i<x.length();++i)
		{
			cur = input[i];
			if(cur>='a'&&cur<='z')
				state = a[state][cur-'a'];
			else if(cur>='A'&&cur<='Z')
				state = a[state][cur-'A'+26];
			else 
				return false;
		}
		if(state==1) return true;
		return false;
	}
	public static boolean isNumber(char c)
	{
		if(c>='0'&&c<='9')
			return true;
		else return false;
	}
	public static boolean isFloat(String s)
	{
		if(s.length()==0)return false;
		int pos=0;
		if(s.charAt(0)=='+'||s.charAt(0)=='-')pos++;
		if(pos==s.length())return false;

		if(s.charAt(pos)!='.'&&(!isNumber(s.charAt(pos))))return false;
		if(s.charAt(pos)=='.')
		{
			pos++;
			if(pos>=s.length())return false;

			for(;pos<s.length()&&isNumber(s.charAt(pos));++pos);

			if(pos==s.length())return true;
			if(s.charAt(pos)!='e'&&s.charAt(pos)!='E')return false;
			pos++;
			return isInteger(s.substring(pos, s.length()));
		}
		else{

			for(;pos<s.length()&&isNumber(s.charAt(pos));++pos);

			if(pos==s.length())return true;
			if(s.charAt(pos)=='.')
			{
				pos++;
				if(pos>=s.length())return false;

				for(;pos<s.length()&&isNumber(s.charAt(pos));++pos);

				if(pos==s.length())return true;
				if(s.charAt(pos)!='e'&&s.charAt(pos)!='E')return false;
				pos++;
				return isInteger(s.substring(pos, s.length()));
			}
			if(s.charAt(pos)=='e'||s.charAt(pos)=='E')
			{
				pos++;
				return isInteger(s.substring(pos, s.length()));
			}
			return false;
		}


	}
	public static void execute(String x)
	{
		++tot;
		System.out.print("token "+tot+": <"+x+", ");
		if(isFloat(x)) System.out.print("Float");
		else if(isInteger(x)) System.out.print("Integer");
		else System.out.print("String");
		System.out.println(">");
	}
	public static void main(String[] argv)
	{
		String text = argv[0];
		char[] input = text.toCharArray();
		int length = text.length();
		int st = 0;
		while(st<length)
		{
			int end;
			for(end=length-1;end>=st;--end)
			{
				String x = text.substring(st,end+1);
				if(isFloat(x)||isInteger(x)||isString(x)) break;
			}
			execute(text.substring(st,end+1));
			st = end + 1;
		}
	}
}

