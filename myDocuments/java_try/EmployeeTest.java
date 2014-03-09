import java.util.*;

public class EmployeeTest
{
	public static void main(String[] argv)
	{
		Employee[] staff=new Employee[3];
		staff[0]=new Employee("mkch",100,2000,1,1);
		staff[1]=new Employee();
		staff[2]=new Employee("bob");
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
