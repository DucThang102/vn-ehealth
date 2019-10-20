package test;

import java.util.List;import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import vn.ehealth.emr.EmrChamSoc;
import vn.ehealth.emr.EmrQuaTrinhChamSoc;
import vn.ehealth.emr.repository.EmrChamSocRepository;
import vn.ehealth.emr.service.EmrHoSoBenhAnService;

@SpringBootApplication
@EnableMongoRepositories("vn.ehealth.emr.repository")
@EntityScan("vn.ehealth.emr")
@ComponentScan("vn.ehealth.emr.service")
public class CmdApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CmdApp.class, args);
    }
    
    @Autowired EmrChamSocRepository emrChamSocRepository;
    @Autowired EmrHoSoBenhAnService emrHoSoBenhAnService;
    
    @Override
    public void run(String... args) throws Exception {
        var hsba = emrHoSoBenhAnService.getEmrHoSoBenhAnById(new ObjectId("5da544b74ece1e17ec0c0807"));
        hsba.ifPresent(x -> {
            System.out.println(x.mayte);
        });
    }
    
    public void run1(String... args) throws Exception {
       
        var cs = new EmrChamSoc();
        
        cs.sotochamsoc = "01";
        
        var qt1 = new EmrQuaTrinhChamSoc();
        qt1.thuchienylenh = "Y lenh 1";
        
        var qt2 = new EmrQuaTrinhChamSoc();
        qt2.thuchienylenh = "Y lenh 2";
        
        cs.emrQuaTrinhChamSocs = List.of(qt1, qt2);
        cs = emrChamSocRepository.save(cs);
        
    }

}
