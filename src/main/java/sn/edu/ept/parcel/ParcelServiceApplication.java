package sn.edu.ept.parcel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ParcelServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParcelServiceApplication.class, args);
	}
}
