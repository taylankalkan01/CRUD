package com.example.CRUD.service.impl;

import com.example.CRUD.entities.Employee;
import com.example.CRUD.exception.ResourceNotFoundException;
import com.example.CRUD.repos.EmployeeRepository;
import com.example.CRUD.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {
        /* Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()){
            return employee.get();
        }else{
            throw new ResourceNotFoundException("Employee","Id",id);
        }*/
        return employeeRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Employee","Id",id));
    }

    @Override
    public Employee updateEmployee(Employee employee, long id) {
        //check employee with given id is exist in db
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee","Id",id));

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());
        //save existing employees to db
        employeeRepository.save(existingEmployee);
        return existingEmployee;


    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Employee","Id",id));
        employeeRepository.deleteById(id);
    }


}
