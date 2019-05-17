package io.helidon.atp.lab.facade;

import java.util.List;

import io.helidon.atp.lab.entity.Dept;
import io.helidon.atp.lab.entity.Emp;
import io.helidon.atp.lab.entity.Employee;

public interface GreetFacade {

	List<Employee> listEmployees();

	Employee selectEmployee(String employeeId);

	Employee insertEmployee(Employee employee);

	Employee updateEmployee(Employee employee);

	int deleteEmployee(String employeeId);

	List<Emp> listEmp();

	Emp selectEmp(Short empno);

	Emp insertEmp(Emp emp);

	List<Dept> listDept();

	Dept selectDept(Short deptno);

	Dept insertDept(Dept dept);

}
