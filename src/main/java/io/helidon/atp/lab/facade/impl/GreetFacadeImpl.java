package io.helidon.atp.lab.facade.impl;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.mybatis.cdi.Transactional;

import io.helidon.atp.lab.common.exception.DBOperationException;
import io.helidon.atp.lab.entity.Dept;
import io.helidon.atp.lab.entity.DeptExample;
import io.helidon.atp.lab.entity.Emp;
import io.helidon.atp.lab.entity.EmpExample;
import io.helidon.atp.lab.entity.Employee;
import io.helidon.atp.lab.entity.EmployeeExample;
import io.helidon.atp.lab.facade.GreetFacade;
import io.helidon.atp.lab.service.DeptService;
import io.helidon.atp.lab.service.EmpService;
import io.helidon.atp.lab.service.EmployeeService;
import io.helidon.atp.lab.service.ManagerService;

@RequestScoped
@Transactional(rollbackFor = DBOperationException.class)
public class GreetFacadeImpl implements GreetFacade {

	@Inject
	EmployeeService employeeService;

	@Inject
	ManagerService managerService;

	@Inject
	EmpService empService;

	@Inject
	DeptService deptService;

	@Override
	public List<Employee> listEmployees() {

		return employeeService.selectByExample(new EmployeeExample());
	}

	@Override
	public Employee selectEmployee(String employeeId) {

		EmployeeExample employeeExample = new EmployeeExample();
		io.helidon.atp.lab.entity.EmployeeExample.Criteria employeeCriteria = employeeExample.createCriteria();
		employeeCriteria.andEmployeeIdEqualTo(employeeId);
		return employeeService.selectByExample(employeeExample).get(0);
	}

	@Override
	public Employee insertEmployee(Employee employee) {

		int i = employeeService.insert(employee);
		if (i == 1) {
			return selectEmployee(employee.getEmployeeId());
		}
		return null;
	}

	@Override
	public Employee updateEmployee(Employee employee) {

		String employeeId = employee.getEmployeeId();
		Employee oldEmployee = selectEmployee(employeeId);

		if (oldEmployee != null) {

			EmployeeExample employeeExample = new EmployeeExample();
			io.helidon.atp.lab.entity.EmployeeExample.Criteria employeeCriteria = employeeExample.createCriteria();
			employeeCriteria.andEmployeeIdEqualTo(employeeId);
			int i = employeeService.updateByExample(employee, employeeExample);
			if (i == 1) {
				return selectEmployee(employee.getEmployeeId());
			}
		}

		return null;
	}

	@Override
	public int deleteEmployee(String employeeId) {

		int i = -1;
		Employee oldEmployee = selectEmployee(employeeId);

		if (oldEmployee != null) {

			EmployeeExample employeeExample = new EmployeeExample();
			io.helidon.atp.lab.entity.EmployeeExample.Criteria employeeCriteria = employeeExample.createCriteria();
			employeeCriteria.andEmployeeIdEqualTo(employeeId);
			i = employeeService.deleteByExample(employeeExample);
		}

		return i;
	}

	@Override
	public List<Emp> listEmp() {

		return empService.selectByExample(new EmpExample());
	}

	@Override
	public Emp selectEmp(Short empno) {

		EmpExample empExample = new EmpExample();
		EmpExample.Criteria empCriteria = empExample.createCriteria();
		empCriteria.andEmpnoEqualTo(empno);
		return empService.selectByExample(empExample).get(0);
	}

	@Override
	public Emp insertEmp(Emp emp) {

		int i = empService.insert(emp);
		return selectEmp(emp.getEmpno());
	}

	@Override
	public List<Dept> listDept() {

		return deptService.selectByExample(new DeptExample());
	}

	@Override
	public Dept selectDept(Short deptno) {

		DeptExample deptExample = new DeptExample();
		DeptExample.Criteria empCriteria = deptExample.createCriteria();
		empCriteria.andDeptnoEqualTo(deptno);
		return deptService.selectByExample(deptExample).get(0);
	}

	@Override
	public Dept insertDept(Dept dept) {

		int i = deptService.insert(dept);
		return selectDept(dept.getDeptno());
	}

}