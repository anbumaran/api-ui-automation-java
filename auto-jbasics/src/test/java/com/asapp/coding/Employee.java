
package com.asapp.coding;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
public class Employee implements Serializable, Cloneable {

    String empName;
    int empId;
    String dept;

    public String getEmpName() {
        return empName;
    }

    public Employee setEmpName(String empName) {
        this.empName = empName;
        return this;
    }

    public int getEmpId() {
        return empId;
    }

    public Employee setEmpId(int empId) {
        this.empId = empId;
        return this;
    }

    public String getDept() {
        return dept;
    }

    public Employee setDept(String dept) {
        this.dept = dept;
        return this;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empName='" + empName + '\'' +
                ", empId=" + empId +
                ", dept='" + dept + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return getEmpId() == employee.getEmpId() && Objects.equals(getEmpName(), employee.getEmpName()) && Objects.equals(getDept(), employee.getDept());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmpName(), getEmpId(), getDept());
    }

    public Employee() {

    }

    public Employee(String empName, int empId, String dept) {
        this.empName = empName;
        this.empId = empId;
        this.dept = dept;
    }

    @Override
    public Employee clone() {
        try {
            return (Employee) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static class Builder {

        String empName;
        int empId;
        String dept;

        public Builder setEmpName(String empName) {
            this.empName = empName;
            return this;
        }

        public Builder setEmpId(int empId) {
            this.empId = empId;
            return this;
        }

        public Builder setDept(String dept) {
            this.dept = dept;
            return this;
        }

        public Employee build() {
            Employee employee = new Employee();
            employee.setEmpName(empName);
            employee.setEmpId(empId);
            if (dept == null || dept.isBlank()) {
                employee.setDept("Training");
            } else {
                employee.setDept(dept);
            }
            return employee;
        }

    }

    public static int compareByEmpId(Employee a, Employee b) {
        return a.getEmpId() - b.getEmpId();
    }

    public static int compareByEmpName(Employee a, Employee b) {
        return a.getEmpName().compareTo(b.getEmpName());
    }

    public static int compareByDept(Employee a, Employee b) {
        return a.getDept().compareTo(b.getDept());
    }

}
