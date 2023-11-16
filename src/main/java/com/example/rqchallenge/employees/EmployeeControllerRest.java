package com.example.rqchallenge.employees;

import com.example.rqchallenge.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeControllerRest extends EmployeeController {

    final static Logger log = LoggerFactory.getLogger(EmployeeControllerRest.class);
    final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        ResponseEntity<List<Employee>> ret = restTemplate.exchange("https://dummy.restapiexample.com/api/v1/employees",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {});
        log.info("getAllEmployees returned: {}", ret.getBody());
        return ret;
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        ResponseEntity<Employee> ret = restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employee/" + id, Employee.class);
        log.info("getEmployeeById: {} returned: {}", id, ret.getBody());
        return ret;
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
