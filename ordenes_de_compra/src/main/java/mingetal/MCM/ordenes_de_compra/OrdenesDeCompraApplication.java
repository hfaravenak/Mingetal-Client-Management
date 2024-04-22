package mingetal.MCM.ordenes_de_compra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OrdenesDeCompraApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdenesDeCompraApplication.class, args);
	}

}
