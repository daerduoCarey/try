import java.util.*;

public class EnumTest
{
	public static void main(String[] argv)
	{
		Size s=Enum.valueOf(Size.class,"SMALL");
		System.out.println(s.getAbbr());
	}
}

enum Size
{
	SMALL("S"),MEDIUM("M"),LARGE("L"),EXTRA_LARGE("XL");

	private Size(String abbr) {this.abbr=abbr;}
	public String getAbbr() {return abbr;}

	private String abbr;
}
