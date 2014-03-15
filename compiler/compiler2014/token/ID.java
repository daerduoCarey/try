package token;

public class ID extends Token
{
	public ID(String name)
	{
		this.name = name;
	}
	public String get()
	{
		return ""+name;
	}
	public String getId()
	{
		return "IDENTIFIER";
	}
	public String toString()
	{
		return "<"+getId()+", "+get()+">";
	}

	private String name;
}

