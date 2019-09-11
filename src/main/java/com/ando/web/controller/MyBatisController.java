package com.ando.web.controller;

import com.ando.web.bean.Department;
import com.ando.web.bean.Employee;
import com.ando.web.mapper.DepartmentMapper;
import com.ando.web.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *{@link  com.ando.web.bean.Department }
 *{@link  com.ando.web.bean.Employee }
 */
@RestController
public class MyBatisController {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @GetMapping("/dept/all")
    public List<Department> getDepartments() {
        return departmentMapper.getDeptAll();
    }

    @GetMapping("/dept/{id}")
    public Department getDepartment(@PathVariable("id") Integer id) {
        return departmentMapper.getDeptById(id);
    }

    @GetMapping("/dept")
    public Department insertDept(Department department) {
        departmentMapper.insertDept(department);
        return department;
    }

    @GetMapping("/employee/{id}")
    public Employee getEmp(@PathVariable("id") Integer id) {
        return employeeMapper.getEmpById(id);
    }


}
