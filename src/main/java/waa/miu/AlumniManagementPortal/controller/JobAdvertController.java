package waa.miu.AlumniManagementPortal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import waa.miu.AlumniManagementPortal.entity.JobAdvert;
import waa.miu.AlumniManagementPortal.service.JobAdvertService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin
public class JobAdvertController {

    private final JobAdvertService jobAdvertService;

    @GetMapping("/job-adverts")
    public List<JobAdvert> findAll(){
        return jobAdvertService.findAll();
    }

    @GetMapping("/job-adverts/{id}")
    public JobAdvert findById(@PathVariable Long id){
        return jobAdvertService.findById(id);
    }

    @PostMapping("/job-adverts")
    public JobAdvert create(@RequestBody JobAdvert jobAdvert){
        return jobAdvertService.create(jobAdvert);
    }

    @PutMapping("/job-adverts/{id}")
    public JobAdvert update(@PathVariable Long id, @RequestBody JobAdvert jobAdvert) {
        return jobAdvertService.update(id, jobAdvert);
    }

    @DeleteMapping("/job-adverts/{id}")
    public void delete(@PathVariable Long id){
        jobAdvertService.delete(id);
    }

    @GetMapping("/job-adverts/findTop10JobsByDateAdded")
    public List<JobAdvert> findTop10JobsByDateAdded(){
        return jobAdvertService.findTop10JobsByDateAdded();
    }

}
