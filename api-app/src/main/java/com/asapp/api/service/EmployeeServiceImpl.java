package com.asapp.api.service;

import com.asapp.api.model.EmployeeDTO;
import com.asapp.api.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll();
    }

    public EmployeeDTO findById(long id) {
        if (employeeRepository.findById(id).isPresent()) {
            return employeeRepository.findById(id).get();
        } else {
            throw new IllegalArgumentException(id + "Employee ID is not available");
        }
    }

    public void save(EmployeeDTO EmployeeDTO) {
        employeeRepository.save(EmployeeDTO);
    }

    public void saveAll(List<EmployeeDTO> EmployeeDTOS) {
        employeeRepository.saveAll(EmployeeDTOS);
    }

    public void delete(long id) {
        employeeRepository.deleteById(id);
    }

}