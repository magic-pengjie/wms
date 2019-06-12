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
        String dbUrl = "jdbc:h2:D:/work/soft/h2/wms";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.H2)
                .setUrl(dbUrl)
                .setUsername("pengjie")
                .setPassword("123456")
                .setDriverName("org.h2.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                //.setTablePrefix()
                .setRestControllerStyle(true)
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        config.setActiveRecord(false)
                .setAuthor("pengjie")
                .setOutputDir("D:\\work\\projects\\workspace")
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setServiceName("%Service")
                .setFileOverride(true);
        
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setService("service")
                                .setServiceImpl("serviceImp")
                                .setEntity("model.po")
                ).execute();
    }

}
