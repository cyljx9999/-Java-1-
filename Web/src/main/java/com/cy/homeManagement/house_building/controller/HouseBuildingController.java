package com.cy.homeManagement.house_building.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.CommonResult;
import com.cy.homeManagement.house_building.entity.BuildingParam;
import com.cy.homeManagement.house_building.entity.HouseBuilding;
import com.cy.homeManagement.house_building.service.HouseBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author cy
 * @program: WuYeManagementProgram_java
 * @description:
 * @date 2022-01-17 11:27:30
 */
@RestController
@RequestMapping("/api/houseBuild")
public class HouseBuildingController {
    @Autowired
    private HouseBuildingService houseBuildingService;


    /**
     * 栋数查询列表
     * @param param
     * @return
     */
    @GetMapping("/list")
    public CommonResult<IPage<HouseBuilding>> list(@Valid BuildingParam param){
        IPage<HouseBuilding> list = houseBuildingService.getList(param);
        return CommonResult.success("栋数列表查询成功",list);
    }

    /**
     * 给房屋单元查询列表
     * @return
     */
    @GetMapping("/unitList")
    public CommonResult<List<HouseBuilding>> unitList(){
        List<HouseBuilding> list = houseBuildingService.list();
        return CommonResult.success("栋数列表查询成功",list);
    }

    /**
     * 新增栋数
     * @param houseBuilding
     * @return
     */
    @PostMapping
    public CommonResult<String> addHouseBuilding(@RequestBody @Valid HouseBuilding houseBuilding){
        QueryWrapper<HouseBuilding> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(HouseBuilding::getBuildName,houseBuilding.getBuildName());
        HouseBuilding one = houseBuildingService.getOne(queryWrapper);
        if (one != null){
            return CommonResult.error("楼栋已存在!");
        }else {
            boolean saveStatus = houseBuildingService.save(houseBuilding);
            if(saveStatus){
                return CommonResult.success("新增楼栋成功!");
            }
            return CommonResult.error("新增楼栋失败!");
        }

    }

    /**
     * 编辑栋数
     * @param houseBuilding
     * @return
     */
    @PutMapping
    public CommonResult<String> editHouseBuilding(@RequestBody @Valid HouseBuilding houseBuilding){
        QueryWrapper<HouseBuilding> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(HouseBuilding::getBuildName,houseBuilding.getBuildName());
        HouseBuilding one = houseBuildingService.getOne(queryWrapper);
        if (one != null){
            return CommonResult.error("楼栋已存在!");
        }else {
            boolean editStatus = houseBuildingService.updateById(houseBuilding);
            if (editStatus) {
                return CommonResult.success("编辑楼栋成功!");
            }
            return CommonResult.error("编辑楼栋失败!");
        }
    }


    /**
     * 删除栋数
     * @param buildId
     * @return
     */
    @DeleteMapping("/{buildId}")
    public CommonResult<String> deleteHouseBuilding(@PathVariable("buildId") @Valid Long buildId){
        boolean removeStatus = houseBuildingService.removeById(buildId);
        if(removeStatus){
            return CommonResult.success("删除楼栋成功!");
        }
        return CommonResult.error("删除楼栋失败!");
    }
}
