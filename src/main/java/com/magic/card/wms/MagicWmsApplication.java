package com.magic.card.wms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/***
 * 启动类
 * @author pengjie
 * @since 2019-06-10
 */
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.magic.card.wms"})
public class MagicWmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagicWmsApplication.class, args);
	}

}
