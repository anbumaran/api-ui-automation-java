package com.asapp.api.controller;

import com.asapp.api.model.EmployeeDTO;
import com.asapp.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.asapp.api.Constants.RECORD_DELETE_FAIL;
import static com.asapp.api.Constants.RECORD_DELETE_SUCCESS;
import static com.asapp.api.Constants.RECORD_NOT_FOUND;
import static com.asapp.api.Constants.RECORD_SAVE_FAIL;
import static com.asapp.api.Constants.RECORD_SAVE_SUCCESS;
import static com.asapp.api.Constants.RECORD_UPDATE_FAIL;
import static com.asapp.api.Constants.RECORD_UPDATE_SUCCESS;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/welcome")
    public String welcomeMessage() {
        return "Welcome to Spring World - Employee";
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDTO employeeDTO) {

        try {
            employeeService.save(employeeDTO);
            return new ResponseEntity<>(RECORD_SAVE_SUCCESS, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RECORD_SAVE_FAIL, HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PostMapping("/addEmployees")
    public ResponseEntity<?> addEmployees(@RequestBody List<EmployeeDTO> employeeDTOS) {

        try {
            employeeService.saveAll(employeeDTOS);
            return new ResponseEntity<>(RECORD_SAVE_SUCCESS, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RECORD_SAVE_FAIL, HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PostMapping("/addEmployee/empName/{empName}/empId/{empId}/dept/{dept}")
    public ResponseEntity<?> addEmployee(
            @PathVariable("empName") String empName,
            @PathVariable("empId") int empId,
            @PathVariable("dept") String dept) {

        try {
            EmployeeDTO employeeDTO = new EmployeeDTO(empName, empId, dept);
            employeeService.save(employeeDTO);
            return new ResponseEntity<>(RECORD_SAVE_SUCCESS, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RECORD_SAVE_FAIL, HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PutMapping("/updateEmployee/{empId}")
    public ResponseEntity<?> updatedEmployee(
            @PathVariable(value = "empId") long empId,
            @RequestBody EmployeeDTO employeeDTO) {

        try {

            employeeService.delete(empId);
            employeeService.save(employeeDTO);
            return new ResponseEntity<>(RECORD_UPDATE_SUCCESS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(RECORD_UPDATE_FAIL, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/employees")
    public ResponseEntity<?> getEmployee(
            @RequestParam(required = false, name = "empName") String empName,
            @RequestParam(required = false, name = "empId") Integer empId,
            @RequestParam(required = false, name = "dept") String dept) {

        List<EmployeeDTO> employeeDTOList = null;

        try {

            List<EmployeeDTO> employeeDTOS = employeeService.findAll();

            if (empName != null) {
                employeeDTOList = employeeDTOS.parallelStream()
                        .filter(s -> s.getEmpName().equalsIgnoreCase(empName)).collect(Collectors.toList());
            }

            if (empId != null) {
                if (employeeDTOList == null) {
                    employeeDTOList = employeeDTOS.parallelStream()
                            .filter(s -> s.getEmpId() == empId).collect(Collectors.toList());
                } else {
                    employeeDTOList = employeeDTOList.parallelStream()
                            .filter(s -> s.getEmpId() == empId).collect(Collectors.toList());
                }
            }

            if (dept != null) {
                if (employeeDTOList == null) {
                    employeeDTOList = employeeDTOS.parallelStream()
                            .filter(s -> s.getDept().equalsIgnoreCase(dept)).collect(Collectors.toList());
                } else {
                    employeeDTOList = employeeDTOList.parallelStream()
                            .filter(s -> s.getDept().equalsIgnoreCase(dept)).collect(Collectors.toList());
                }
            }

            if (empName == null && empId == null && dept == null) {
                employeeDTOList = employeeDTOS;
            }

            employeeDTOList.sort(Comparator.comparing(EmployeeDTO::getEmpId));

            return new ResponseEntity<>(employeeDTOList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(RECORD_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/allEmployees")
    public ResponseEntity<?> getEmployee() {

        try {
            List<EmployeeDTO> employeeDTOS = employeeService.findAll();
            employeeDTOS.sort(Comparator.comparing(EmployeeDTO::getEmpId));
            return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(RECORD_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<?> getEmployee(@PathVariable("empId") long empId) {

        try {
            EmployeeDTO employeeDTO = employeeService.findById(empId);
            return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(RECORD_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/deleteEmployee/{empId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("empId") long empId) {

        try {
            employeeService.delete(empId);
            return new ResponseEntity<>(RECORD_DELETE_SUCCESS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(RECORD_DELETE_FAIL, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/deleteAllEmployees")
    public ResponseEntity<?> deleteAllEmployees() {

        List<EmployeeDTO> employeeDTOS = employeeService.findAll();
        try {
            employeeDTOS.forEach(each -> employeeService.delete(each.getEmpId()));
            return new ResponseEntity<>(RECORD_DELETE_SUCCESS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(RECORD_DELETE_FAIL, HttpStatus.NOT_FOUND);
        }

    }

}
