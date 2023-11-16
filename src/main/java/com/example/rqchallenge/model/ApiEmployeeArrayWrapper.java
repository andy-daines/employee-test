package com.example.rqchallenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiEmployeeArrayWrapper(String status, Employee[] data) {
}
