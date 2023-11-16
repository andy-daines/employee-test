package com.example.rqchallenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Employee (Integer id, String employee_name, Integer employee_salary, Integer employee_age, String profile_image) {

    static public Employee fromMap(final Map<String, Object> fields) {

        Integer id = null;
        Integer salary = null;
        Integer age = null;
        String name = null;
        String image = null;

        for (Map.Entry<String, Object> value : fields.entrySet()) {
            switch (value.getKey()) {
                case "id":
                    id = (Integer)value.getValue();
                    break;
                case "employee_salary":
                    salary = (Integer)value.getValue();
                    break;
                case "employee_age":
                    age = (Integer)value.getValue();
                    break;
                case "employee_name":
                    name = (String)value.getValue();
                    break;
                case "profile_image":
                    image = (String)value.getValue();
                    break;
            }
        }

        return new Employee(id, name, salary, age, image);
    }
}
