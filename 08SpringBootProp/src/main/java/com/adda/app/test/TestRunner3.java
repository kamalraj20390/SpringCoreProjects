package com.adda.app.test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.adda.app.bean.DbCon;
import com.adda.app.entity.Department;
import com.adda.app.entity.Employee;
import com.adda.app.entity.Product;
import com.adda.app.repo.DepartmentRepository;
import com.adda.app.repo.EmployeeRepository;
import com.adda.app.repo.ProductRepository;
//@Component
public class TestRunner3 implements CommandLineRunner{
	@Autowired
	private DbCon con;
	@Autowired
	private EmployeeRepository erepo;
	@Autowired
	private DepartmentRepository drepo;

	@Override
	public void run(String... args) throws Exception {
		Department d1=new Department(1001, "DEV", "ADMIN1");
		Department d2=new Department(1002, "PROD", "ADMIN2");
		Department d3=new Department(1003, "QA", "ADMIN3");
		Department d4=new Department(1004, "BA", "ADMIN4");
		drepo.saveAll(Arrays.asList(d1,d2,d3,d4));
		Employee e1=new Employee(101, "Mayank", 10000.0, d1);
		Employee e2=new Employee(102, "Rachana", 12000.0, d2);
		Employee e3=new Employee(103, "Misthi", 12000.0, d3);
		Employee e4=new Employee(104, "Vikash", 11000.0, d4);
		Employee e5=new Employee(105, "Diksha", 13000.0, d1);
		Employee e6=new Employee(106, "Simmi", 15000.0, d2);
		Employee e7=new Employee(107, "Piyush", 14000.0, d3);
		Employee e8=new Employee(108, "Rahul", 19000.0, d4);
		Employee e9=new Employee(109, "Pooja", 14000.0, d1);
		Employee e10=new Employee(110, "Gopal", 16000.0, d2);
		erepo.saveAll(Arrays.asList(e1,e2,e3,e4,e5,e6,e7,e8,e9,e10));
		List<Object[]> list1=erepo.getAllEnamesAndDeptNames();
		for (Object[] objects : list1) {
			System.out.println(objects[0]+"-------------"+objects[1]);
		}
	}


}
