package com.example.rqchallenge.employees;

import com.example.rqchallenge.model.ApiEmployeeArrayWrapper;
import com.example.rqchallenge.model.ApiEmployeeWrapper;
import com.example.rqchallenge.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeControllerRest extends EmployeeController {

    final static Logger log = LoggerFactory.getLogger(EmployeeControllerRest.class);
    final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {

        ResponseEntity<ApiEmployeeArrayWrapper> employees = restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employees", ApiEmployeeArrayWrapper.class);

        if (employees.getBody() != null) {
            return new ResponseEntity<>(Arrays.stream(employees.getBody().data()).toList(), employees.getStatusCode());
        } else {
            return new ResponseEntity<>(null, employees.getStatusCode());
        }
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {

        ResponseEntity<ApiEmployeeWrapper> employee = restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employee/" + id, ApiEmployeeWrapper.class);

        if (employee.getBody() != null) {
            return new ResponseEntity<>(employee.getBody().data(), employee.getStatusCode());
        } else {
            return new ResponseEntity<>(null, employee.getStatusCode());
        }
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        log.info("createEmployee: {}", employeeInput);
        return restTemplate.postForEntity("https://dummy.restapiexample.com/api/v1/create", null, Employee.class, employeeInput);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        log.info("deleteEmployeeById: {}", id);
        return restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/delete/" + id,String.class);
    }
}
