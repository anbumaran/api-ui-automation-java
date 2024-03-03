package com.asapp.jbasics;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.Objects;

@XmlRootElement
public class Employees {

    @XmlAttribute(name = "employees")
    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employees)) return false;
        Employees that = (Employees) o;
        return getEmployees().equals(that.getEmployees());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmployees());
    }

    @Override
    public String toString() {
        return "EmployeeList{" +
                "employees=" + employees +
                '}';
    }

}