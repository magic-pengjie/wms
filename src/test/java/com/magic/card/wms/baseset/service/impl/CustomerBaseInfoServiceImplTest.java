package com.magic.card.wms.baseset.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.MagicWmsApplicationTests;
import com.magic.card.wms.baseset.model.dto.CustomerBaseInfoDTO;
import com.magic.card.wms.baseset.service.ICustomerBaseInfoService;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.Constants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * com.magic.card.wms.baseset.service.impl
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/18/018 11:04
 * @since : 1.0.0
 */
@Slf4j
public class CustomerBaseInfoServiceImplTest extends MagicWmsApplicationTests {

    @Resource
    private ICustomerBaseInfoService customerBaseInfoService;

    @Test
    public void loadGrid() {
        Page<Map> page = new Page<>(1, 2);
        HashMap<String, String> map = new HashMap<>();
        map.put("address", "%上海%");
        log.info(" LoadGrid 加载数据： {}", JSON.toJSONString(customerBaseInfoService.loadGrid(new LoadGrid())));
    }

    @Test
    public void addCustomerBaseInfo() {
        CustomerBaseInfoDTO customerBaseInfoDTO = new CustomerBaseInfoDTO();
        customerBaseInfoDTO.setCustomerCode("www.baidu.com");
        customerBaseInfoDTO.setCustomerName("百度（上海）信息科技有限公司");
        customerBaseInfoDTO.setBrandId("120025336");
        customerBaseInfoDTO.setContactPerson("Mr.Zhang");
        customerBaseInfoDTO.setAddress("上海市闵行区快乐大厦603室");
        customerBaseInfoDTO.setRemark("百度（上海）信息科技有限公司 \n 地址：上海市闵行区快乐大厦603室");

        customerBaseInfoService.add(customerBaseInfoDTO, Constants.DEFAULT_USER);
    }

    @Test
    public void updateCustomerBaseInfo() {
    }

    @Test
    public void deleteCustomer() {
    }
}