package com.magic.card.wms.config;

import com.magic.card.wms.baseset.service.ICommodityInfoService;
import com.magic.card.wms.baseset.service.ILogisticsTrackingInfoService;
import com.magic.card.wms.baseset.service.IPickingBillService;
import com.magic.card.wms.warehousing.service.IPurchaseBillService;

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
    @Autowired
    private IPurchaseBillService purchaseBillService;
    @Autowired
    private ILogisticsTrackingInfoService logisticsTrackingInfoService;
    @Autowired
    private ICommodityInfoService commodityInfoService;

//    @Scheduled(cron = "0 0/30 * * * *")
    public void generatorPickingBill() {
        pickingBillService.timingGenerator();
    }

    /**
     * 食品预警任务
     **/
    //@Scheduled(cron = "* */2 * * * *")
    public void runFoodWarning() {
        log.info("==>runFoodWarning start..");
        try {
        	 purchaseBillService.FoodWarningTask();
             log.info("==>runFoodWarning end..");
		} catch (Exception e) {
			log.error("runFoodWarning error:{}",e);
		}
       
    }
    /**
     * 滞销品预警
     **/
    //@Scheduled(cron = "* * */1 * * *")
    public void runUnsalableGoodsWarning() {
    	log.info("==>runUnsalableGoodsWarning start..");
        try {
        	commodityInfoService.selectUnsalableGood();
             log.info("==>runUnsalableGoodsWarning end..");
		} catch (Exception e) {
			log.error("runUnsalableGoodsWarning error:{}",e);
		}
    }

    /**
     * 定时批量查询物流跟踪信息
     **/
    //@Scheduled(cron = "* */2 * * * *")
    public void runLogisticsTrackingInfo() {
    	log.info("==>runLogisticsTrackingInfo start..");
        try {
        	logisticsTrackingInfoService.runLogisticsInfo();
             log.info("==>runLogisticsTrackingInfo end..");
		} catch (Exception e) {
			log.error("runLogisticsTrackingInfo error:{}",e);
		}
    }
    /**
     * 物流信息预警
     **/
    //@Scheduled(cron = "* */2 * * * *")
    public void runLogisticsTrackingInfoWarning() {
    	log.info("==>runLogisticsTrackingInfoWarning start..");
        try {
        	logisticsTrackingInfoService.runLogisticsInfoWarning();
             log.info("==>runLogisticsTrackingInfoWarning end..");
		} catch (Exception e) {
			log.error("runLogisticsTrackingInfoWarning error:{}",e);
		}
    }

    /**
         * 订单超时预警 当日16：00之前订单，已锁定订单在2H之内没有后续系统操作，进行预警。16：00之后订单，超12H没有生成拣货单的订单进行预警
     **/
    //@Scheduled(cron = "* */2 * * * *")
    public void runOrderTimeOutWarning() {
    	log.info("==>runOrderTimeOutWarning start..");
        try {
        	logisticsTrackingInfoService.runLogisticsInfo();
             log.info("==>runOrderTimeOutWarning end..");
		} catch (Exception e) {
			log.error("runOrderTimeOutWarning error:{}",e);
		}
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
