package com.scri.workshopspring;

import com.scri.workshopspring.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Workshopspring implements CommandLineRunner {




    public static void main(String[] args) {
        SpringApplication.run(Workshopspring.class, args);
    }

    @Override
    public void run(String... args) throws Exception{

    }

}
