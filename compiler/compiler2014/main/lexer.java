package main;

import java.util.*;
import java.io.*;

import token.*;
import fileReader.*;

/*
 * Class <I>lexer</I> to lexical analysis a <I>C</I> file
 * @author mkch
 * @version 1.0
 */

public class lexer
{
	private static boolean isDigit(char x)
	{
		return (x<='9'&&x>='0');
	}
	private static boolean isLetter(char x)
	{
		return (x<='z'&&x>='a')||(x<='Z'&&x>='A')||(x=='_');
	}
	private static boolean isInteger(String x)
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
	private static boolean isFloat(String x)
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
	private static boolean isIdentifier(String x)
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
	private static boolean isKeyword(String x)
	{
		String [] list={"include","alignas","alignof","and","and_eq","asm","auto","bitand","bitor","bool","break","case","catch","char","char16_t","char32_t","class","compl","const","constexpr","const_cast","continue","decltype","default","delete","do","double","dynamic_cast","else","enum","explici","export","extern","false","float","for","friend","goto","if","inline","int","long","mutable","namespace","new","noexcept","not","not_eq","nullptr","operator","or","or_eq","private","protected","public","register","reinterpret_cast","return","short","signed","sizeof","static","static_assert","static_cast","struct","switch","template","this","thread_local","throw","true","try","typedef","typeid","typename","union","unsigned","using","virtual","void","volatile","wchar_t","while","xor","xor_eq"};
		for(int i=0;i<list.length;++i)
			if(x.compareTo(list[i])==0) return true;
		return false;
	}
	private static boolean isOperator(String x)
	{
		String[] list={"+","-","*","/","=","==","!=","!","&","%","^","&","|","&&","||","<","<<",">",">>","++","--",">=","<=","/=","+=","-=","*=","%=","~",".","->","[","]","?",":",",","::",">>=","<<=","&=","|=","^=","#"};
		for(int i=0;i<list.length;++i)
			if(x.compareTo(list[i])==0) return true;
		return false;

	}
	private static boolean isOpenParen(String x)
	{
		return (x.compareTo(new String("("))==0)||(x.compareTo(new String("{"))==0);
	}
	private static boolean isCloseParen(String x)
	{	
		return (x.compareTo(new String(")"))==0)||(x.compareTo(new String("}"))==0);
	}
	private static boolean isSemicolon(String x)
	{
		return (x.compareTo(new String(";"))==0);
	}
	private static boolean isWhite(String x)
	{
		char[] input=x.toCharArray();
		for(int i=0;i<x.length();++i)
			if(!(input[i]==' '||input[i]=='\t'||input[i]=='\n')) return false;
		return true;
	}
	private static boolean __isBlock(char x)
	{
		return x==' '||x=='\t'||x=='\n'||x==';'||x=='('||x==')'||x=='{'||x=='}';
	}
	private void execute(String x)
	{	
		if(isString(x)) tokenList.add(new QUOTATION(x));
		else if(isKeyword(x)) tokenList.add(new KEYWORD(x));
		else if(isOperator(x)) tokenList.add(new OPERATOR(x));
		else if(isInteger(x)) tokenList.add(new INT(x));
		else if(isIdentifier(x)) tokenList.add(new ID(x));	
		else if(isFloat(x)) tokenList.add(new FLOAT(x));
		else if(isSemicolon(x)) tokenList.add(new SEMICOLON());
		else if(isWhite(x)) tokenList.add(new WHITE());
		else if(isOpenParen(x)) tokenList.add(new OPENPAREN(x));
		else if(isCloseParen(x)) tokenList.add(new CLOSEPAREN(x));
	}
	private static boolean isString(String x)
	{
		if(x.length()<2) return false;
		char[] input=x.toCharArray();
		if(input[0]=='\"'&&input[x.length()-1]=='\"')
		{
			for(int i=1;i<x.length()-1;++i)
				if(input[i]=='\"') return false;
			return true;
		}
		if(input[0]=='\''&&input[x.length()-1]=='\'')
		{
			for(int i=1;i<x.length()-1;++i)
				if(input[i]=='\'') return false;
			return true;
		}
		return false;
	}
	private static boolean isToken(String x)
	{
		return isString(x)||isFloat(x)||isIdentifier(x)||isKeyword(x)||isWhite(x)||isSemicolon(x)||isOpenParen(x)||isCloseParen(x)||isOperator(x);
	}
	/*
	 * Constructor of lexer
	 */
	public lexer(String file_name) throws IOException
	{
		fileName = file_name;
		inFile = new fileReader();
		fileContent = inFile.readFile(file_name);
	}
	/*
	 * run lexical analysis
	 */
	public boolean run()
	{
		tokenList = new ArrayList<Token>();
		char[] input=fileContent.toCharArray();
		int st=0,end;
		while(st<input.length)
		{
			int mark=st+1;
			if(__isBlock(input[st]))
				while(mark<input.length&&__isBlock(input[mark])) ++mark;
			else
				while(mark<input.length&&!__isBlock(input[mark])) ++mark;
			for(end=mark-1;end>=st;--end)
			{
				String y=fileContent.substring(st,end+1);
				if(isToken(y)) break; 
			}
			if(end>=st) execute(fileContent.substring(st,end+1));
			else 
			{
				System.out.println("Error at '"+input[st]+"'");
				return false;
			}
			st=end+1;
		}
		return true;
	}
	/*
	 * output .lex file to <I>file_name</I>
	 */
	public boolean output(String file_name)
	{
		try{
			PrintWriter out = new PrintWriter(file_name);
			for(Token token:tokenList)
				out.println(token.toString());
			out.close();
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	/*
	 * output .lex file automatically
	 */
	public boolean output()
	{
		return output(fileName+".lex");
	}

	private String fileName;
	private String fileContent;
	private fileReader inFile;
	private ArrayList<Token> tokenList;
}

