package com.lak.hogwartartifactsonline;

import com.lak.hogwartartifactsonline.Artifact.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HogwartArtifactsOnlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(HogwartArtifactsOnlineApplication.class, args);
	}
	@Bean
	public IdWorker idWorker(){
		return  new IdWorker(1,1);
	}

}
