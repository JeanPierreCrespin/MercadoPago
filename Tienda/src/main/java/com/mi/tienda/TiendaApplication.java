package com.mi.tienda;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class TiendaApplication {

	public static  void main(String[] args) {
		SpringApplication.run(TiendaApplication.class, args);
		MercadoPagoConfig.setAccessToken("PROD_ACCESS_TOKEN");

	}
	

}
