package com.example.rqchallenge.employees;

import com.example.rqchallenge.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class EmployeeController implements IEmployeeController {

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        try {
            final List<Employee> ret = getAllEmployees().getBody().stream().filter(e -> e.employee_name().contains(searchString)).toList();
            return new ResponseEntity<>(ret, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {

        final Comparator<Employee> salaryComp = Comparator.comparing(Employee::employee_salary);

        try {
            final ResponseEntity<List<Employee>> response = getAllEmployees();
            if (response.getBody() != null) {
                final Employee highest = response.getBody().stream().max(salaryComp).get();
                return new ResponseEntity<>(highest.employee_salary(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, response.getStatusCode());
            }
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        // note: Readme suggests employees but the interface provided and the function employee_name suggests names so going
        // with names
        final Comparator<Employee> salaryComp = Comparator.comparing(Employee::employee_salary);

        try {
            final ResponseEntity<List<Employee>> response = getAllEmployees();
            if (response.getBody() != null) {
                final List<String> employeeNames = new ArrayList<>();
                response.getBody()
                        .stream()
                        .sorted(salaryComp.reversed())
                        .limit(10)
                        .forEach(e -> employeeNames.add(e.employee_name()));

                return new ResponseEntity<>(employeeNames, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, response.getStatusCode());
            }
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // TODO: The spec was a little confusing we are implementing a create here with no id
    public ResponseEntity<String> createEmployee(final String name, final String salary, final String age) {

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
