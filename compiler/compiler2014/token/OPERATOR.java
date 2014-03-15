package token;

public class OPERATOR extends Token
{
	public OPERATOR(String op)
	{
		this.op = op;
	}
	public String get()
	{
		return op;
	}
	public String getId()
	{
		return "OPERATOR";
	}
	public String toString()
	{
		return "<"+getId()+", "+get()+">";
	}

	private String op;
}

