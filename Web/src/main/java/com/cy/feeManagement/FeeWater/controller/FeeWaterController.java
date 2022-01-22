package com.cy.feeManagement.FeeWater.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.CommonResult;
import com.cy.feeManagement.FeeWater.entity.FeeWater;
import com.cy.feeManagement.FeeWater.entity.FeeWaterParam;
import com.cy.feeManagement.FeeWater.service.FeeWaterService;
import com.cy.valid.feeAddOrEdit;
import com.cy.valid.feePay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author cy
 * @program: WuYeManagementProgram_java
 * @description:
 * @date 2022-01-21 15:28:17
 */
@RestController
@RequestMapping("/api/feeWater")
public class FeeWaterController {
    @Autowired
    private FeeWaterService feeWaterService;


    /**
     * 新增水费
     * @param feeWater
     * @return
     */
    @PostMapping
    public CommonResult<String> add(@RequestBody @Validated(value = {feeAddOrEdit.class}) FeeWater feeWater){
        int saveFeePowerStatus = feeWaterService.saveFeePower(feeWater);
        if (saveFeePowerStatus == 1){
            return CommonResult.success("新增水费成功!");
        }else if (saveFeePowerStatus == -1) {
            return CommonResult.error("该房屋尚未被使用，不能添加水费!");
        }else if (saveFeePowerStatus == 2){
            return CommonResult.error("该房屋本月已添加电费，不能重复添加!");
        }else {
            return CommonResult.error("新增水费失败!");
        }
    }


    /**
     * 编辑水费
     * @param feeWater
     * @return
     */
    @PutMapping
    public CommonResult<String> edit(@RequestBody @Validated(value = {feeAddOrEdit.class})  FeeWater feeWater){
        boolean updateFeePowerStatus = feeWaterService.updateFeePower(feeWater);
        if (updateFeePowerStatus) {
            return CommonResult.success("编辑水费成功!");
        }else {
            return CommonResult.error("编辑水费失败!");
        }
    }


    /**
     * 删除水费
     * @param waterId
     * @return
     */
    @DeleteMapping("/{waterId}")
    public CommonResult<String> delete(@PathVariable("waterId") @Valid  Long waterId){
        //如果已经缴费，就不能删除
        QueryWrapper<FeeWater> query = new QueryWrapper<>();
        query.lambda().eq(FeeWater::getWaterId,waterId).eq(FeeWater::getPayWaterStatus,"1");
        FeeWater one = feeWaterService.getOne(query);
        if(one != null){
            return CommonResult.error("已缴费，不能删除!");
        }
        //删除操作
        boolean removeByIdStatus = feeWaterService.removeById(waterId);
        if(removeByIdStatus){
            return CommonResult.success("删除水费成功!");
        }
        return CommonResult.error("删除水费失败!");
    }

    /**
     * 水费列表查询
     */
    @GetMapping("/list")
    public CommonResult<IPage<FeeWater>> getFeeWaterList(@Valid FeeWaterParam param){
        IPage<FeeWater> list = feeWaterService.getFeeWaterList(param);
        return CommonResult.success("水费列表查询成功",list);
    }

    /**
     * 缴费
     */
    @PostMapping("/payWater")
    public CommonResult<String> feeWater(@RequestBody @Validated(value = {feePay.class}) FeeWater feeWater){
        feeWater.setPayWaterTime((new Date()));


        QueryWrapper<FeeWater> feeWaterQueryWrapper = new QueryWrapper<>();
        feeWaterQueryWrapper.lambda().isNull(FeeWater::getPayWaterTime);
        FeeWater one = feeWaterService.getOne(feeWaterQueryWrapper);
        if (one != null) {
            boolean updateByIdStatus = feeWaterService.updateById(feeWater);
            if(updateByIdStatus){
                return CommonResult.success("缴费成功!");
            }
            return CommonResult.error("缴费失败!");
        }else {
            return CommonResult.error("已缴费，无需重复缴费!");
        }
    }
}