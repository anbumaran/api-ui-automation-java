package com.asapp.api.service;

import com.asapp.api.model.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> findAll();

    EmployeeDTO findById(long id);

    void save(EmployeeDTO EmployeeDTO);

    void saveAll(List<EmployeeDTO> EmployeeDTOS);

    void delete(long id);

}
