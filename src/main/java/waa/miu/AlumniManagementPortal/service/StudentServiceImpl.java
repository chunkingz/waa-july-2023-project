package waa.miu.AlumniManagementPortal.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.apache.tika.mime.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import waa.miu.AlumniManagementPortal.entity.Student;
import waa.miu.AlumniManagementPortal.repository.StudentRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

    private final StudentRepo studentRepo;

    @Override
    public List<Student> findAll() {
        return studentRepo.findAll();
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
                default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid filter: "+key);
            }
        }
        return students;
    }

    @Override
    public Student create(Student student) {
        String studentCVPath = processCV(student);
        student.setCv(studentCVPath);
        return studentRepo.save(student);
    }

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
        if(!(existingStudent.getCv().equals(student.getCv()))){
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
        existingStudent.setJobAdverts(student.getJobAdverts());
        existingStudent.setCurrentWorkPlace(student.getCurrentWorkPlace());
        existingStudent.setIsCurrentlyEmployed(student.getIsCurrentlyEmployed());
        existingStudent.setIsDeleted(student.getIsDeleted());
        return studentRepo.save(existingStudent);
    }

    @Override
    public void delete(Long id) {
        studentRepo.deleteById(id);
    }
}
