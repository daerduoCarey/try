import java.util.*;

public class ManagerTest
{
	public static void main(String[] argv)
	{
		Manager[] staff=new Manager[3];
		staff[0]=new Manager("mkch",100,2000,1,1,1000000);
		staff[1]=new Manager();
		staff[2]=new Manager("bob");
		for(Employee i:staff)
		{
			i.raiseSalary(5);
		}
		for(Employee i:staff)
		{
			System.out.println("Name: "+i.getName()+"   Salary: "+i.getSalary()+"   Hire day: "+i.getHireDay());
		}
	}
}
