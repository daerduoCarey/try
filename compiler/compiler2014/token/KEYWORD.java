package token;

public class KEYWORD extends Token
{
	public KEYWORD(String word)
	{
		this.word = word;
	}
	public String get()
	{
		return word;
	}
	public String getId()
	{
		return "KEYWORD";
	}
	public String toString()
	{
		return "<"+getId()+", "+get()+">";
	}

	private String word;
}

