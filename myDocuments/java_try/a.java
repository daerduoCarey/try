import java.io.*;

class fileReader
{
	public static void readToBuffer(StringBuffer buffer,String file_path) throws IOException
	{
		InputStream in=new FileInputStream(file_path);
		String line;
		BufferedReader reader=new BufferedReader(new InputStreamReader(in));
		line=reader.readLine();
		while(line!=null)
		{
			buffer.append(line);
			buffer.append("\n");
			line=reader.readLine();
		}
		reader.close();
		in.close();
	}
	public static String readFile(String file_path) throws IOException
	{
		StringBuffer sb=new StringBuffer();
		fileReader.readToBuffer(sb,file_path);
		return sb.toString();
	}
}

public class a
{
	public static void main(String[] argv)
	{
		fileReader fr=new fileReader();
		try{
			String get=fr.readFile(new String("a.txt"));
			System.out.println(get);
		}
		catch(IOException e) {
			System.out.println("Open File Failed.");
		}
	}
}
