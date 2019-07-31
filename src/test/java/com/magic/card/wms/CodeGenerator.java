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
		 String packageName = "com.magic.card.wms.baseset";
		 String[] tableNames = {
				 "wms_logistics_tracking_info"
		 };
		 generateByTables(packageName,tableNames);

	}
	
	private  void generateByTables(String packageName,  String[] tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://47.103.60.47:3306/WMS?useSSL=false&verifyServerCertificate=false&useUnicode=true&characterEncoding=utf8";
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
                .setTablePrefix("wms_")
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
