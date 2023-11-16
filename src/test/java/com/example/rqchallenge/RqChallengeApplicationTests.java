package com.example.rqchallenge;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.testutil.EmployeeControllerMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RqChallengeApplicationTests {

    // test data produced easily from find/replace reformatted output of url:
    // curl https://dummy.restapiexample.com/api/v1/employees
    
    final List<Map<String, Object>> testEmployees = List.of(
            Map.of("id",1,"employee_name","Tiger Nixon","employee_salary",320800,"employee_age",61,"profile_image",""),
            Map.of("id",2,"employee_name","Garrett Winters","employee_salary",170750,"employee_age",63,"profile_image",""),
            Map.of("id",3,"employee_name","Ashton Cox","employee_salary",86000,"employee_age",66,"profile_image",""),
            Map.of("id",4,"employee_name","Cedric Kelly","employee_salary",433060,"employee_age",22,"profile_image",""),
            Map.of("id",5,"employee_name","Airi Satou","employee_salary",162700,"employee_age",33,"profile_image",""),
            Map.of("id",6,"employee_name","Brielle Williamson","employee_salary",372000,"employee_age",61,"profile_image",""),
            Map.of("id",7,"employee_name","Herrod Chandler","employee_salary",137500,"employee_age",59,"profile_image",""),
            Map.of("id",8,"employee_name","Rhona Davidson","employee_salary",327900,"employee_age",55,"profile_image",""),
            Map.of("id",9,"employee_name","Colleen Hurst","employee_salary",205500,"employee_age",39,"profile_image",""),
            Map.of("id",10,"employee_name","Sonya Frost","employee_salary",103600,"employee_age",23,"profile_image",""),
            Map.of("id",11,"employee_name","Jena Gaines","employee_salary",90560,"employee_age",30,"profile_image",""),
            Map.of("id",12,"employee_name","Quinn Flynn","employee_salary",342000,"employee_age",22,"profile_image",""),
            Map.of("id",13,"employee_name","Charde Marshall","employee_salary",470600,"employee_age",36,"profile_image",""),
            Map.of("id",14,"employee_name","Haley Kennedy","employee_salary",313500,"employee_age",43,"profile_image",""),
            Map.of("id",15,"employee_name","Tatyana Fitzpatrick","employee_salary",385750,"employee_age",19,"profile_image",""),
            Map.of("id",16,"employee_name","Michael Silva","employee_salary",198500,"employee_age",66,"profile_image",""),
            Map.of("id",17,"employee_name","Paul Byrd","employee_salary",725000,"employee_age",64,"profile_image",""),
            Map.of("id",18,"employee_name","Gloria Little","employee_salary",237500,"employee_age",59,"profile_image",""),
            Map.of("id",19,"employee_name","Bradley Greer","employee_salary",132000,"employee_age",41,"profile_image",""),
            Map.of("id",20,"employee_name","Dai Rios","employee_salary",217500,"employee_age",35,"profile_image",""),
            Map.of("id",21,"employee_name","Jenette Caldwell","employee_salary",345000,"employee_age",30,"profile_image",""),
            Map.of("id",22,"employee_name","Yuri Berry","employee_salary",675000,"employee_age",40,"profile_image",""),
            Map.of("id",23,"employee_name","Caesar Vance","employee_salary",106450,"employee_age",21,"profile_image",""),
            Map.of("id",24,"employee_name","Doris Wilder","employee_salary",85600,"employee_age",23,"profile_image","")
    );
    static final Map<String, Object> TEST_EMPLOYEE_NEW = Map.of("id",99,"employee_name","TestName","employee_salary",12345,"employee_age",20,"profile_image","");

    final static EmployeeControllerMock mockedEndpoint = new EmployeeControllerMock();

    @BeforeEach
    void setup() {
        mockedEndpoint.reset();
        mockedEndpoint.addAll(testEmployees);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testGetAllEmployees() throws IOException {

        assertEmployeeCount(testEmployees.size());
    }

    @Test
    void testDeleteEmployee() throws IOException {
        ResponseEntity<String> deletedName = mockedEndpoint.deleteEmployeeById("19");
        assertNotNull(deletedName.getBody());
        assertEquals(testEmployees.get(18).get("employee_name"), deletedName.getBody());
        assertEmployeeCount(testEmployees.size()-1);
    }

    @Test
    void testGetEmployeesByName() {
        ResponseEntity<List<Employee>> employees = mockedEndpoint.getEmployeesByNameSearch("ell");
        assertNotNull(employees.getBody());
        assertEquals(3, employees.getBody().size());
        // with time we could test these out by id to verify exactly but given it found three it's probably worked fine
    }

    @Test
    void testGetEmployeeById() {
        ResponseEntity<Employee> employee = mockedEndpoint.getEmployeeById("19");
        assertNotNull(employee.getBody());
        assertEquals(testEmployees.get(18).get("employee_name"), employee.getBody().employee_name());
    }

    @Test
    void testHighestSalary() {
        ResponseEntity<Integer> salary = mockedEndpoint.getHighestSalaryOfEmployees();
        assertNotNull(salary.getBody());
        assertEquals(725000, salary.getBody());
    }

    @Test
    void testGetTopTenSalaries() {
        final Set<String> names = Set.of(
                "Paul Byrd",
                "Yuri Berry",
                "Charde Marshall",
                "Cedric Kelly",
                "Tatyana Fitzpatrick",
                "Brielle Williamson",
                "Jenette Caldwell",
                "Quinn Flynn",
                "Rhona Davidson",
                "Tiger Nixon");

        ResponseEntity<List<String>> employeeNames = mockedEndpoint.getTopTenHighestEarningEmployeeNames();
        assertNotNull(employeeNames.getBody());
        assertEquals(10, employeeNames.getBody().size());
        assertTrue(employeeNames.getBody().containsAll(names), names + " differs from " + employeeNames.getBody());
    }

    @Test
    void testCreateEmployeeStrings() throws IOException {
        // only testing the mocked endpoint because actual published REST interface needs a map with id
        ResponseEntity<String> status = mockedEndpoint.createEmployee("employee_name", "1234", "43");
        assertNotNull(status.getBody());
        assertEquals("success", status.getBody());
        assertEmployeeCount(testEmployees.size()+1);
    }

    @Test
    void testCreateEmployeeMap() throws IOException {
        // this endpoint is documented to get back employee except actual endpoint doesn't allow creation so
        // assumption is this returned the employee sent.  Also duplicated don't seem to matter so using existing
        // for speed.

        ResponseEntity<Employee> status = mockedEndpoint.createEmployee(TEST_EMPLOYEE_NEW);
        assertNotNull(status.getBody());
        assertEquals(TEST_EMPLOYEE_NEW.get("employee_name"), status.getBody().employee_name());
        assertEquals(TEST_EMPLOYEE_NEW.get("employee_age"), status.getBody().employee_age());
        assertEquals(TEST_EMPLOYEE_NEW.get("employee_salary"), status.getBody().employee_salary());
        assertEquals(TEST_EMPLOYEE_NEW.get("id"), status.getBody().id());
        assertEmployeeCount(testEmployees.size()+1);
    }

    private void assertEmployeeCount(final int count) throws IOException {
        ResponseEntity<List<Employee>> allEmployees = mockedEndpoint.getAllEmployees();
        assertNotNull(allEmployees.getBody());
        assertEquals(count, allEmployees.getBody().size());
    }
}
