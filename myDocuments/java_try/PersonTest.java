import java.util.*;
import java.io.*;

/**
 * This program demonstrates abstract classes.
 * @version 1.0
 * @author mkch
 */

public class PersonTest
{
	public static void main(String[] args) throws IOException
	{
		Person[] p = new Person[3];
		p[0] = new Student("mkch","5120309052");
		p[1] = new Employee("zzy",1000);
		p[2] = new Employee("lzb",2000);
		for(Person i:p)
		{
			System.out.println(i.getDescription());
		}
	}
}

abstract class Person
{
	public Person(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return name;
	}
	public abstract String getDescription();

	private String name;
}

class Employee extends Person
{
	public Employee(String name,double salary)
	{
		super(name);
		this.salary=salary;	
	}
	public String getDescription()
	{
		return super.getName()+" is an employee.\nWhose salary is "+getSalary();
	}
	public double getSalary()
	{
		return salary;
	}

	private double salary;
}

class Student extends Person
{
	public Student(String name,String number)
	{
		super(name);
		this.number=number;
	}
	public String getNumber()
	{	
		return number;
	}
	public String getDescription()
	{
		return super.getName()+" is a student.\nWhose number is "+getNumber();
	}

	private String number;
}
