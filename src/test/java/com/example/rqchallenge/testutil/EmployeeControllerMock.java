package com.example.rqchallenge.testutil;

import com.example.rqchallenge.employees.EmployeeController;
import com.example.rqchallenge.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeControllerMock extends EmployeeController {

    // better to have a map with random access in case it gets large, worth the overhead in
    // values().stream().toList() when querying all
    final Map<Integer, Employee> employeeMap = new HashMap<>();
    int nextId = 0;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {

        return new ResponseEntity<>(employeeMap.values().stream().toList(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {

        if (employeeMap.containsKey(Integer.valueOf(id))) {
            return new ResponseEntity<>(employeeMap.get(Integer.valueOf(id)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {

        final Employee newEmployee = Employee.fromMap(employeeInput);

        if (newEmployee.id() != null) {
            employeeMap.put(newEmployee.id(), newEmployee);
            nextId = newEmployee.id()+1;
            return new ResponseEntity<>(newEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> createEmployee(final String name, final String salary, final String age) {

        ResponseEntity<Employee> response = createEmployee(Map.of("id", nextId, "employee_name", name, "employee_salary", Integer.valueOf(salary), "employee_age", Integer.valueOf(age)));
        if (response.getStatusCode() == HttpStatus.OK) {
            nextId++;
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("failure", response.getStatusCode());
        }
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        final Integer intId = Integer.valueOf(id);
        if (employeeMap.containsKey(intId)) {
            final String name = employeeMap.get(intId).employee_name();
            employeeMap.remove(intId);
            return new ResponseEntity<>(name, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public void addAll(List<Map<String, Object>> testEmployees) {
        testEmployees.forEach(this::createEmployee);
    }

    public void reset() {
        employeeMap.clear();
        nextId=0;
    }
}
