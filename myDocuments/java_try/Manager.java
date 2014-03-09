/**
 * This is a class named <pre>Manager</pre> which herits <pre>Employee</pre>
 * @author mkch
 * @version 1.0
 */

public class Manager extends Employee
{
	public Manager()
	{
		super();
		bonus=0;
	}
	public Manager(String name,double salary,int y,int m,int d,double bonus)
	{
		super(name,salary,y,m,d);
		this.bonus=bonus;
	}
	public Manager(String name)
	{
		super(name);
		bonus=0;
	}
	
	public double getBonus()
	{
		return bonus;
	}
	public double getSalary()
	{
		return super.getSalary()+bonus;
	}
	
	private double bonus;
}

