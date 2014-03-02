import java.util.*;
import java.io.*;

public class lexical_analysis
{
	private static int tot=0;
	private static boolean isDigit(char x)
	{
		return (x<='9'&&x>='0');
	}
	private static boolean isLetter(char x)
	{
		return (x<='z'&&x>='a')||(x<='Z'&&x>='A')||(x=='_');
	}
	public static boolean isInteger(String x)
	{
		if(x.length()==0) return false;
		char[] input = x.toCharArray();
		int st=0;
		if(input[0]=='+'||input[0]=='-') 
		{
			st=1;
			if(x.length()==1) return false;
		}
		for(int i=st;i<x.length();++i) 
			if(!isDigit(input[i])) return false;
		return true;
	}
	public static boolean isFloat(String x)
	{
		int st=0;
		char[] input=x.toCharArray();
		if(x.length()==0) return false;
		if(input[0]=='+'||input[0]=='-') 
		{
			st=1;
			if(x.length()==1) return false;
		}
		int[][] a=new int[9][4];
		a[1][2]=a[2][2]=2;a[3][2]=a[4][2]=3;a[5][2]=a[7][2]=7;a[6][2]=a[8][2]=8;
		a[5][1]=6;a[2][3]=a[3][3]=5;
		a[2][0]=3;a[1][0]=4;
		int state=1;
		for(int i=st;i<x.length();++i)
		{
			char c=input[i];
			int tar=-1;
			
			if(c=='+'||c=='-') tar=1;
			else if(c=='e'||c=='E') tar=3;
			else if(isDigit(c)) tar=2;
			else if(c=='.') tar=0;
			
			if(tar==-1) return false;
			
			state=a[state][tar];
		}
		if(state==2||state==3||state==7||state==8) return true;
		return false;
	}
	public static boolean isIdentifier(String x)
	{
		if(x.length()==0) return false;
		char[] input=x.toCharArray();
		if(isLetter(input[0]))
		{
			for(int i=1;i<x.length();++i)
				if(!isLetter(input[i])&&!isDigit(input[i])) return false;
			return true;
		}
		else return false;
	}
	public static boolean isKeyword(String x)
	{
		String [] list={"alignas","alignof","and","and_eq","asm","auto","bitand","bitor","bool","break","case","catch","char","char16_t","char32_t","class","compl","const","constexpr","const_cast","continue","decltype","default","delete","do","double","dynamic_cast","else","enum","explici","export","extern","false","float","for","friend","goto","if","inline","int","long","mutable","namespace","new","noexcept","not","not_eq","nullptr","operator","or","or_eq","private","protected","public","register","reinterpret_cast","return","short","signed","sizeof","static","static_assert","static_cast","struct","switch","template","this","thread_local","throw","true","try","typedef","typeid","typename","union","unsigned","using","virtual","void","volatile","wchar_t","while","xor","xor_eq"};
		for(int i=0;i<list.length;++i)
			if(x.compareTo(list[i])==0) return true;
		return false;
	}
	public static boolean isOperator(String x)
	{
		String[] list={"+","-","*","/","=","==","!=","!","&","%","^","&","|","&&","||","<","<<",">",">>","++","--",">=","<=","/=","+=","-=","*=","%=","~",".","->","[","]","?",":",",","::",">>=","<<=","&=","|=","^="};
		for(int i=0;i<list.length;++i)
			if(x.compareTo(list[i])==0) return true;
		return false;

	}
	public static boolean isOpenParen(String x)
	{
		return (x.compareTo(new String("("))==0)||(x.compareTo(new String("{"))==0);
	}
	public static boolean isCloseParen(String x)
	{	
		return (x.compareTo(new String(")"))==0)||(x.compareTo(new String("}"))==0);
	}
	public static boolean isSemicolon(String x)
	{
		return (x.compareTo(new String(";"))==0);
	}
	public static boolean isWhite(String x)
	{
		char[] input=x.toCharArray();
		for(int i=0;i<x.length();++i)
			if(!(input[i]==' '||input[i]=='\t'||input[i]=='\n')) return false;
		return true;
	}
	private static void execute(String x)
	{
		++tot;

		if(isString(x)) System.out.print("<String, "+x+">");
		else if(isKeyword(x)) System.out.print("<Keyword, "+x+">");
		else if(isOperator(x)) System.out.print("<Operate, "+x+">");
		else if(isIdentifier(x)) System.out.print("<Identifier, "+x+">");	
		else if(isFloat(x)) System.out.print("<Number, "+x+">");
		else if(isSemicolon(x)) System.out.print("<Semicolon, "+x+">");
		else if(isWhite(x)) ;
		else if(isOpenParen(x)) System.out.print("<OpenParen, "+x+">");
		else if(isCloseParen(x)) System.out.print("<CloseParen, "+x+">");
	}
	public static boolean isString(String x)
	{
		if(x.length()<2) return false;
		char[] input=x.toCharArray();
		if((input[0]=='\"'&&input[x.length()-1]=='\"')||(input[0]=='\''&&input[x.length()-1]=='\'')) return true;
		else return false;
	}
	public static boolean isToken(String x)
	{
		return isString(x)||isFloat(x)||isIdentifier(x)||isKeyword(x)||isWhite(x)||isSemicolon(x)||isOpenParen(x)||isCloseParen(x)||isOperator(x);
	}
	public static void main(String[] argv)
	{
		String x=argv[0];
		char[] input=x.toCharArray();
		int st=0,end;
		while(st<x.length())
		{
			for(end=x.length()-1;end>=st;--end)
			{
				String y=x.substring(st,end+1);
				if(isToken(y)) break; 
			}
			if(end>=st) execute(x.substring(st,end+1));
			else 
			{
				System.out.println("Error at '"+input[st]+"'");
				return;
			}
			st=end+1;
		}
		System.out.println();
	}
	
}


