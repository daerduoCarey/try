import java.util.*;

public class Employee
{
	public Employee(String n)
	{
		this();
		name=n;
	}
	public Employee(String n,double s,int y,int m,int d)
	{
		name=n;
		salary=s;
		GregorianCalendar calendar=new GregorianCalendar(y,m-1,d);
		hireDay=calendar.getTime();
	}
	public Employee()
	{
		name="Unnamed";
		salary=0;
		GregorianCalendar calendar=new GregorianCalendar(0,0,0);
		hireDay=calendar.getTime();
	}

	public String getName()
	{
		return name;
	}
	public Date getHireDay()
	{
		return (Date) hireDay.clone();
	}
	public double getSalary()
	{
		return salary;
	}
	public void raiseSalary(double byPercent)
	{
		salary=salary*(1+byPercent/100);
	}

	private String name;
	private double salary;
	private Date hireDay;
}
