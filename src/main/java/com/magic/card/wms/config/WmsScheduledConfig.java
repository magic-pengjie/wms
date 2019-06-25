package com.magic.card.wms.config;

import com.magic.card.wms.baseset.service.IPickingBillService;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * com.magic.card.wms.config
 * WMS 定时任务执行配置
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/25/025 13:50
 * @since : 1.0.0
 */
@Slf4j
@Configuration
@EnableScheduling
public class WmsScheduledConfig {
    @Autowired
    private IPickingBillService pickingBillService;

//    @Scheduled(cron = "0 0/30 * * * *")
    public void generatorPickingBill() {
        pickingBillService.timingGenerator();
    }

//    @Scheduled(cron = "*/5 * * * * *")
    public void run1() {
        execute("run1");
    }

//    @Scheduled(cron = "*/5 * * * * *")
    public void run2() {
        execute("run2");
    }

//    @Scheduled(cron = "*/5 * * * * *")
    public void run3() {
        execute("run3");
    }

//    @Synchronized
    public void execute(String taskName) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("{} ----- {}", taskName,System.currentTimeMillis());
    }

}
