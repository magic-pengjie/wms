package com.magic.card.wms.baseset.controller;

import java.util.List;

import javax.validation.Valid;

import com.magic.card.wms.baseset.model.dto.BatchBindStorehouseDTO;
import com.magic.card.wms.baseset.model.xml.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magic.card.wms.baseset.model.dto.StorehouseConfigDTO;
import com.magic.card.wms.baseset.model.vo.StorehouseConfigVO;
import com.magic.card.wms.baseset.service.IStorehouseConfigService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * com.magic.card.wms.baseset.controller
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/20/020 14:45
 * @since : 1.0.0
 */
@Slf4j
@Api(value = "库位配置前端控制器", description = "库位配置前端控制器")
@RestController
@RequestMapping(value = "storehouseConfig", headers = "accept=application/json")
public class StorehouseConfigController {
    @Autowired
    private IStorehouseConfigService storehouseConfigService;

    @ApiOperation(value = "加载库位数据（可分页查询）")
    @PostMapping("loadGrid")
    public ResponseData loadGrid( @ApiParam("搜索信息")@RequestBody LoadGrid loadGrid) {
        return ResponseData.ok(storehouseConfigService.loadGrid(loadGrid));
    }

    @ApiOperation(value = "新增库位配置数据基本信息")
    @PostMapping("add")
    public ResponseData add(@RequestBody @Valid StorehouseConfigDTO dto, BindingResult result) {
        storehouseConfigService.add(dto, Constants.DEFAULT_USER);
        return ResponseData.ok();
    }

    @ApiOperation(value = "修改库位配置数据基本信息")
    @PostMapping("update")
    public ResponseData update(@RequestBody @Valid StorehouseConfigDTO dto, BindingResult result) {
        storehouseConfigService.update(dto, Constants.DEFAULT_USER);
        return ResponseData.ok();
    }

    @ApiOperation(value = "删除库位配置基本信息(物理删除)")
    @GetMapping("delete")
    public ResponseData delete(@RequestParam Long id) {
        storehouseConfigService.delete(id);
        return ResponseData.ok(storehouseConfigService.deleteById(id));
    }
    
    @ApiOperation(value = "推荐库位")
    @GetMapping("/recommendStore")
    public ResponseData recommendStore(@RequestParam(required = true) String customerId,
    									@RequestParam String commodityId) {
    	 List<StorehouseConfigVO> result = null;
    	try {
    		result = storehouseConfigService.recommendStore(customerId, commodityId);
		} catch (OperationException o) {
			log.error("库位查询失败OperationException:{}", o);
			return ResponseData.error(o.getErrCode(), o.getErrMsg());
		} catch (Exception e) {
			log.error("库位查询失败Exception:{}", e);
			return ResponseData.error(ResultEnum.query_error);
		}
        
        return ResponseData.ok(result);
    }

    @ApiOperation("批量绑定客户")
    @PostMapping("batchBind")
    public ResponseData batchBind(@RequestBody BatchBindStorehouseDTO batchBindStorehouseDTO, BindingResult bindingResult) {
        storehouseConfigService.batchBind(batchBindStorehouseDTO);
        return ResponseData.ok();
    }
}
