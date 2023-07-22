package waa.miu.AlumniManagementPortal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import waa.miu.AlumniManagementPortal.entity.JobAdvert;
import waa.miu.AlumniManagementPortal.repository.AddressRepo;
import waa.miu.AlumniManagementPortal.repository.JobAdvertRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobAdvertServiceImpl implements JobAdvertService{

    private final JobAdvertRepo jobAdvertRepo;
    private final AddressRepo addressRepo;

    @Override
    public List<JobAdvert> findAll() {
        return jobAdvertRepo.findAll();
    }

    @Override
    public JobAdvert findById(Long id) {
        return jobAdvertRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Job advert with id "+id+" not found"));
    }

    @Override
    public JobAdvert create(JobAdvert jobAdvert) {
        addressRepo.save(jobAdvert.getAddress());
        JobAdvert jobAdvert1=new JobAdvert();
        jobAdvert1.setCompanyName(jobAdvert.getCompanyName());
        jobAdvert1.setJobTitle(jobAdvert.getJobTitle());
        jobAdvert1.setExpectedSalary(jobAdvert.getExpectedSalary());
        jobAdvert1.setJobDescription(jobAdvert.getJobDescription());



        jobAdvertRepo.save(jobAdvert1);
        return jobAdvert1;

    }

    @Override
    public JobAdvert update(Long id, JobAdvert jobAdvert) {
        JobAdvert existingJobAdvert = findById(id);
        existingJobAdvert.setJobTitle(jobAdvert.getJobTitle());
        existingJobAdvert.setExpectedSalary(jobAdvert.getExpectedSalary());
        existingJobAdvert.setJobDescription(jobAdvert.getJobDescription());
        existingJobAdvert.setCompanyName(jobAdvert.getCompanyName());
//        existingJobAdvert.setStudent(jobAdvert.getStudent());
//        existingJobAdvert.setAddress(jobAdvert.getAddress());
//        existingJobAdvert.setApplicants(jobAdvert.getApplicants());
        return jobAdvertRepo.save(existingJobAdvert);
    }

    @Override
    public void delete(Long id) {
        jobAdvertRepo.deleteById(id);
    }

    @Override
    public List<JobAdvert> findTop10JobsByDateAdded(){
        return jobAdvertRepo.findTop10ByOrderByDateAddedDesc();
    }

}
