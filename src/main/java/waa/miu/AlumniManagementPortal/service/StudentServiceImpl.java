package waa.miu.AlumniManagementPortal.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.apache.tika.mime.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import waa.miu.AlumniManagementPortal.dto.StudentDto;
import waa.miu.AlumniManagementPortal.entity.Address;
import waa.miu.AlumniManagementPortal.entity.Student;
import waa.miu.AlumniManagementPortal.repository.StudentRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

    private final StudentRepo studentRepo;
    private final AddressService addressService;

    @Override
    public List<Student> findAll() {
        return studentRepo.findAllActiveStudents();
    }

    @Override
    public Student findById(Long id) {
        return studentRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id "+id+" not found"));
    }

    @Override
    public List<Student> filterEntities(Map<String, String> filterParams) {

        List<Student> students = new ArrayList<>();

        for (Map.Entry<String, String> entry : filterParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "state" -> students = studentRepo.findByAddressState(value);
                case "city" -> students = studentRepo.findByAddressCity(value);
                case "major" -> students = studentRepo.findAllByMajorMajorName(value);
                case "name" -> students = studentRepo.findAllByFirstName(value);
                case "studentId" -> students = studentRepo.findByStudentId(value);
                default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid filter: "+key);
            }
        }
        return students;
    }

    @Override
    public Student create(StudentDto studentDto) {
        Address newAddress = addressService.createAddress(studentDto.getAddress());
        Student newStudent = new Student();
        newStudent.setFirstName(studentDto.getFirstName());
        newStudent.setLastName(studentDto.getLastName());
        newStudent.setEmail(studentDto.getEmail());
//        newStudent.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        newStudent.setPassword(studentDto.getPassword());
        newStudent.setPhone(studentDto.getPhone());
        newStudent.setStudentId(studentDto.getStudentId());
        newStudent.setCv(studentDto.getCv());
        newStudent.setIsCurrentlyEmployed(studentDto.getIsCurrentlyEmployed());
        newStudent.setIsDeleted(studentDto.getIsDeleted());
        newStudent.setAddress(newAddress);
        newStudent.setRole(studentDto.getRole());
        return studentRepo.save(newStudent);
    }

//    @Override
//    public Student create(Student student) {
//        if (student.getCv() != null && !student.getCv().isEmpty()) {
//            String studentCVPath = processCV(student);
//            student.setCv(studentCVPath);
//        }
//        return studentRepo.save(student);
//    }

    private @NotNull String processCV(@NotNull Student student) {
        String base64value = student.getCv();
        byte[] fileBytes = Base64.getDecoder().decode(base64value);
        Tika tika = new Tika();
        MediaType mediaType = MediaType.parse(tika.detect(fileBytes));
        String fileExtension = mediaType.getSubtype();
        if (fileExtension.equals("x-tika-ooxml")) fileExtension = "docx";
        String fileName = student.getFirstName() + "-" + student.getLastName() + "-CV." + fileExtension;
        String filePath = Paths.get("uploads", fileName).toString();
        try{
            Path path = Path.of(filePath);
            Files.write(path, fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePath;
    }

    @Override
    public Student update(Long id, Student student) {
        Student existingStudent = findById(id);
        String studentCVPath = existingStudent.getCv();
//        if(student.getCv() != null)
        if(student.getCv() != null && !Objects.equals(existingStudent.getCv(), student.getCv())){
            studentCVPath = processCV(student);
        }
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setCv(studentCVPath);
        existingStudent.setPassword(student.getPassword());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setPhone(student.getPhone());
        existingStudent.setAddress(student.getAddress());
        existingStudent.setMajor(student.getMajor());
        existingStudent.setStudentId(student.getStudentId());
//        existingStudent.setJobAdverts(student.getJobAdverts());
//        existingStudent.setAppliedJobAdverts(student.getAppliedJobAdverts());
        existingStudent.setCurrentWorkPlace(student.getCurrentWorkPlace());
        existingStudent.setIsCurrentlyEmployed(student.getIsCurrentlyEmployed());
        existingStudent.setIsDeleted(student.getIsDeleted());
        return studentRepo.save(existingStudent);
    }

    @Override
    public void delete(Long id) {
//        studentRepo.deleteById(id);
        Student existingStudent = findById(id);
        existingStudent.setIsDeleted(existingStudent.getIsDeleted());
        studentRepo.save(existingStudent);
    }

    @Override
    public List<Map<String, Integer>> getStudentsPerState() {
        Map<String, Integer> map = new HashMap<>();
        List<Student> studentList = findAll();

        for (Student s : studentList) {
            String state = s.getAddress().getState();
            map.put(state, map.getOrDefault(state, 0) + 1);
        }

        List<Map<String, Integer>> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            Map<String, Integer> stateCountMap = new HashMap<>();
            stateCountMap.put(entry.getKey(), entry.getValue());
            result.add(stateCountMap);
        }

        return result;
    }

    @Override
    public List<Map<String, Integer>> getStudentsPerCity() {
        Map<String, Integer> map = new HashMap<>();
        List<Student> studentList = findAll();

        for (Student s : studentList) {
            String city = s.getAddress().getCity();
            map.put(city, map.getOrDefault(city, 0) + 1);
        }

        List<Map<String, Integer>> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            Map<String, Integer> cityCountMap = new HashMap<>();
            cityCountMap.put(entry.getKey(), entry.getValue());
            result.add(cityCountMap);
        }

        return result;
    }
}
