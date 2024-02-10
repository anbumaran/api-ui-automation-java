package com.asapp.api.controller;

import com.asapp.api.model.StudentDTO;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController {

    static ConcurrentMap<Long, StudentDTO> studentDTOConcurrentHashMap = new ConcurrentHashMap<>();

    static {
        List<StudentDTO> studentDTOS = new ArrayList<>();
        studentDTOS.add(new StudentDTO("Ramesh", 10, "Maths"));
        studentDTOS.add(new StudentDTO("Nitha", 20, "Chemistry"));
        studentDTOS.add(new StudentDTO("Sanjay", 30, "Science"));
        studentDTOS.add(new StudentDTO("Praveen", 40, "Biology"));
        studentDTOS.add(new StudentDTO("Aniha", 50, "Maths"));
        studentDTOS.forEach(s -> {
            studentDTOConcurrentHashMap.put(s.getStdId(), s);
        });
    }

    @GetMapping("/welcome")
    public String welcomeMessage() {
        return "Welcome to Spring World - Student";
    }

    @PostMapping("/addStudent")
    public StudentDTO addStudent(@RequestBody StudentDTO studentDTO) {
        studentDTOConcurrentHashMap.put(studentDTO.getStdId(), studentDTO);
        return studentDTO;
    }

    @PostMapping("/addStudents")
    public List<StudentDTO> addStudents(@RequestBody List<StudentDTO> studentDTOS) {
        studentDTOS.forEach(s -> {
            studentDTOConcurrentHashMap.put(s.getStdId(), s);
        });
        return studentDTOS;
    }

    @PostMapping("/studentAdd/stdName/{stdName}/stdId/{stdId}/dept/{dept}")
    public StudentDTO studentAddPathVar(
            @PathVariable("stdName") String stdName,
            @PathVariable("stdId") Long stdId,
            @PathVariable("dept") String dept) {
        StudentDTO studentDTO = new StudentDTO(stdName, stdId, dept);
        studentDTOConcurrentHashMap.put(stdId, studentDTO);
        return studentDTO;
    }

    @PutMapping("/updateStudent")
    public StudentDTO updatedStudent(@RequestBody StudentDTO studentDTO) {

        StudentDTO updatedStudentDTO = Optional.ofNullable(studentDTOConcurrentHashMap.get(studentDTO.getStdId()))
                .orElseThrow(() -> new ResourceNotFoundException("Student not exist with id: " + studentDTO.getDept()));

        updatedStudentDTO.setStdName(studentDTO.getStdName());
        updatedStudentDTO.setStdId(studentDTO.getStdId());
        updatedStudentDTO.setDept(studentDTO.getDept());

        studentDTOConcurrentHashMap.put(updatedStudentDTO.getStdId(), updatedStudentDTO);

        return studentDTO;

    }

    @GetMapping("/students")
    public List<StudentDTO> studentQueryParam(
            @RequestParam(required = false, name = "stdName") String stdName,
            @RequestParam(required = false, name = "stdId") Long stdId,
            @RequestParam(required = false, name = "dept") String dept) {

        List<StudentDTO> studentDTOList = null;

        if (stdName != null) {
            studentDTOList = studentDTOConcurrentHashMap.values().parallelStream()
                    .filter(s -> s.getStdName().equalsIgnoreCase(stdName)).collect(Collectors.toList());
        }

        if (stdId != null) {
            if (studentDTOList == null) {
                studentDTOList = studentDTOConcurrentHashMap.values().parallelStream()
                        .filter(s -> s.getStdId() == stdId).collect(Collectors.toList());
            } else {
                studentDTOList = studentDTOList.parallelStream()
                        .filter(s -> s.getStdId() == stdId).collect(Collectors.toList());
            }
        }

        if (dept != null) {
            if (studentDTOList == null) {
                studentDTOList = studentDTOConcurrentHashMap.values().parallelStream()
                        .filter(s -> s.getDept().equalsIgnoreCase(dept)).collect(Collectors.toList());
            } else {
                studentDTOList = studentDTOList.parallelStream()
                        .filter(s -> s.getDept().equalsIgnoreCase(dept)).collect(Collectors.toList());
            }
        }

        if (stdName == null && stdId == null && dept == null) {
            studentDTOList = getAllStudents();
        }

        return studentDTOList;

    }

    @GetMapping("/allStudents")
    public List<StudentDTO> getAllStudents() {
        return studentDTOConcurrentHashMap.values().parallelStream()
                .sorted(Comparator.comparing(StudentDTO::getStdId)).collect(Collectors.toList());
    }

    @PostMapping("/deleteStudent")
    public StudentDTO deleteStudent(@RequestBody StudentDTO studentDTO) {
        studentDTOConcurrentHashMap.remove(studentDTO.getStdId(), studentDTO);
        return studentDTO;
    }

    @PostMapping("/deleteStudents")
    public List<StudentDTO> deleteStudents(@RequestBody List<StudentDTO> studentDTOS) {
        studentDTOS.forEach(s -> {
            studentDTOConcurrentHashMap.remove(s.getStdId(), s);
        });
        return studentDTOS;
    }

}