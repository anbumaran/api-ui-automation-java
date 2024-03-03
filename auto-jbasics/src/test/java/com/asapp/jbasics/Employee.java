package com.asapp.jbasics;


import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
class Employee implements Serializable, Cloneable {

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

}