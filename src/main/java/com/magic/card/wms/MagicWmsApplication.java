package com.magic.card.wms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/***
 * 启动类
 * @author pengjie
 * @since 2019-06-10
 */
@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
public class MagicWmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagicWmsApplication.class, args);
	}

}
