package com.magic.card.wms;

import org.junit.Test;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator extends MagicWmsApplicationTests{

	@Test
	public void start() {
		 String packageName = "com.magic.card.wms.user";
		 String[] tableNames = {
				 "WMS_USER"
		 };
		 generateByTables(packageName,tableNames);

	}
	
	private  void generateByTables(String packageName,  String[] tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/wms?useUnicode=true&characterEncoding=utf8";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("wmsdata")
                .setPassword("wmsdata")
                .setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix("WMS_")
                .setRestControllerStyle(true)
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        config.setActiveRecord(false)
                .setAuthor("pengjie")
                .setOutputDir("D:\\work\\projects\\workspace")
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                //.setServiceName("%Service")
                .setFileOverride(true);
        
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setService("service")
                                .setServiceImpl("service.impl")
                                .setEntity("model.po")
                ).execute();
    }

}
