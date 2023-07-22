package waa.miu.AlumniManagementPortal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import waa.miu.AlumniManagementPortal.dto.StudentDto;
import waa.miu.AlumniManagementPortal.entity.Student;
import waa.miu.AlumniManagementPortal.service.StudentService;

import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/students")
@RequiredArgsConstructor
@CrossOrigin
public class StudentController {


    private final StudentService studentService;

    @GetMapping("/students")
    public List<Student> findAll(){
        return studentService.findAll();
    }

    @GetMapping("/students/{id}")
    public Student findById(@PathVariable Long id){
        return studentService.findById(id);
    }

    @GetMapping("/students/filter")
    public List<Student> filterByType(@RequestParam Map<String, String> filterParams){
        return studentService.filterEntities(filterParams);
    }

    @PostMapping("/students")
    public Student create(@RequestBody StudentDto student){
        return studentService.create(student);
    }

    @PutMapping("/students/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("/students/{id}")
    public void delete(@PathVariable Long id){
        studentService.delete(id);
    }

    @GetMapping("/auth/studentsPerState")
    public List<Map<String, Integer>> getStudentsPerState(){
        return studentService.getStudentsPerState();
    }

    @GetMapping("/auth/studentsPerCity")
    public List<Map<String, Integer>> getStudentsPerCity(){
        return studentService.getStudentsPerCity();
    }
}
