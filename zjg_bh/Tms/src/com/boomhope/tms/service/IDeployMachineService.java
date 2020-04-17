package com.boomhope.tms.service;

import java.util.List;
import java.util.Map;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.boomhope.tms.entity.BaseMenu;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.DeployMachineView;
import com.boomhope.tms.entity.MacControlPeri;
import com.boomhope.tms.entity.MacMachinePic;
import com.boomhope.tms.entity.MacWarning;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Manu;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.Peripherals;
import com.boomhope.tms.entity.PeripheralsMachine;
import com.boomhope.tms.entity.StatisticsView;
import com.boomhope.tms.pojo.ControlPojo;
import com.boomhope.tms.pojo.DeployControlPojo;
import com.boomhope.tms.pojo.FindAllCountsPojo;
import com.boomhope.tms.pojo.UnitMachineStatusPojo;

public interface IDeployMachineService {
	/**
	 * 查询部署维护信息
	 * @param page
	 * @param map
	 * @return
	 */
	public List<DeployMachineView> findDeployMachineList(Page page,Map<String,String> map);
	
	/**
	 * 编辑部署维护信息
	 * @param deployMachine
	 */
	public void editDeployMachine(DeployMachine deployMachine);
	
	
	/***
	 * 根据id获取部署机器信息
	 * @param machineId
	 * @return
	 */
	public DeployMachine getDeployMachineById(String machineId);
	
	/**
	 * 保存部署维护信息
	 * @param deployMachine
	 */
	public void saveDeployMachine(DeployMachine deployMachine);
	
	/**
	 * 保存或更新部署维护信息
	 * @param deployMachine
	 */
	public void saveOrUpdateDeployMachine(DeployMachine deployMachine);
	
	/**
	 * 删除部署维护信息
	 * @param deployMachine
	 */
	public void delDeployMachine(String machineNo);
	
	
	/**
	 * 修改密码
	 * @param deployMachine
	 */
	public void editDeployMachine1(DeployMachine deployMachine);
	
	/**
	 * 修改状态
	 * @param deployMachine
	 */
	public void editDeployMachine2(DeployMachine deployMachine);

	/**
	 * 修改状态
	 * @param deployMachine
	 */
	public void editDeployMachineremark(DeployMachine deployMachine);
	/**
	 * 查询部署维护信息-不带分页
	 * @param map
	 * @return
	 */
	public List<DeployMachineView> findDeployMachineList(Map<String,String> map);

	/**
	 * 部署维护字段判重（机器编号和ip地址判断）
	 * @param machineCode
	 * @param ip
	 */
	public List<DeployMachine> findDeployMachineMC(String machineNo);
	public List<DeployMachine> findDeployMachineMC1(String ip);
	/**
	 * 按照机器类型来统计各个类型的数量
	 * @return
	 */
	public List<StatisticsView> queryDeployMachineType();
	/**
	 * 按照机器的上报状态来统计数量
	 * @return
	 */
	public List<StatisticsView> queryDeployMachineRepStatus();
	/**
	 * 按照机器的使用状态来统计数量
	 * @return
	 */
	public List<StatisticsView> queryDeployMachineStatus();
	
	/**
	 * 不分页查机构号
	 * @param map
	 * @return
	 */
	public List<DeployMachineView> DeployMachineLists(String unitCode);

	/**
	 * 按照机器类型来统计各个类型的数量(区县)
	 * @return
	 */
	public List<StatisticsView> queryDeployMachineType1(String districtCounty);
	
	/**
	 * 按照机器的上报状态来统计数量(区县)
	 * @return
	 */
	public List<StatisticsView> queryDeployMachineRepStatus1(String districtCounty);
	
	/**
	 * 按照机器的使用状态来统计数量(区县)
	 * @return
	 */
	public List<StatisticsView> queryDeployMachineStatus1(String districtCounty);
	
	/**
	 * 按照机器类型来统计各个类型的数量(支行)
	 * @return
	 */
	public List<StatisticsView> queryDeployMachineType2(String unitCode);
	
	/**
	 * 通过机构名称查询 机器名称和使用次数
	 * @return
	 */
	public List<StatisticsView> queryDeployMachineType22(Map<String, String> map);
	
	/**
	 * 通过业务名称查询 机构名称和使用次数
	 * @return
	 */
	public List<StatisticsView> queryDeployMachineType23(Map<String, String> map);
	
	/**
	 * 通过业务名称查询 机构名称和使用次数
	 * @return
	 */
	public List<StatisticsView> queryDeployMachineType24(Map<String, String> map);
	
	/**
	 * 按照机器的上报状态来统计数量(支行)
	 * @return
	 */
	public List<StatisticsView> queryDeployMachineRepStatus2(String unitCode);
	
	/**
	 * 按照机器的使用状态来统计数量(支行)
	 * @return
	 */
	public List<StatisticsView> queryDeployMachineStatus2(String unitCode);

	/**
	 * 终端查询
	 */
	public List<DeployControlPojo> findDeployMachineList1(Page pageInfo,
			Map<String, String> map);
	
	/**
	 * 根据编号查询当前机器发生次数
	 * */
	public Long findDeployMachineList3(String machineNo);
	
	/**
	 * 异常处理
	 * @param map
	 * @return
	 */
	public List<ControlPojo> findMacControlMachineList(String
			machineNo);
	/**
	 * 根据时间查询每个月这台机器的异常次数
	 * @param map
	 * @return
	 */
	public List<ControlPojo> findeStatusEveryMouth(Map<String, String> map);

	
	/**
	 * 网点地图查询异常报警数据
	 * @return
	 */
	public List<UnitMachineStatusPojo> findUnitMachineStatus();

	/***
	 * 根据型号查询此机器最小创建时间
	 * @param machineCode
	 * @return
	 */
	public String findUnitMachinePic(String machineCode);

	/***
	 * 根据型号和最小时间查询此机器图片
	 * @param machineCode
	 * @return
	 */
	public String findUnitMachinePic2(String machineCode, String creatdate);

	/**
	 * 根据机器型号查询该机器所有的外设编码
	 * @param machineCode
	 * @return
	 */
	public List<PeripheralsMachine> findMachineDict(String machineCode);
	
	/**
	 * 添加外设监控的机器编号和此机器对应的外设编码
	 * @param macControlPeri
	 */
	public void saveMacControlPeri(MacControlPeri macControlPeri);

	/**
	 * 出厂编号判重以及判断是否投产使用-查询
	 * @param macFacNum
	 * @return
	 */
	public List<DeployMachine> findDeployMachineMC3(String macFacNum);
	
	/**
	 * //根据机器出厂号码，查询本机器现如今都在那个地区哪个银行部署过
	 * @param macFacNum
	 * @return
	 */
	public List<DeployMachine> findDeployMachineMC4(String macFacNum);
	/**
	 * 判断此机器现在的状态
	 * 1.投产状态下，则其他银行有关本机器的备份机器不能再投产
	 * 2。初始、下线状态下，任何有本机器的银行都可投产
	 * @param deployMachine
	 * @return
	 */
	public List<DeployMachine> editDeployMachine22(DeployMachine deployMachine);

	/**
	 * 判断本机器现在的状态
	 * 1.投产状态下，则其不能将本机转部署到其他银行
	 * 2。初始、下线状态下，可以转投入其他银行
	 * 但是其他银行如果已经存在此机器则不能再次转部署成功
	 * @param deployMachine
	 * @return
	 */
	public List<DeployMachine> findDeployMachineMC33(String macFacNum);

	/**
	 * 编辑异常描述
	 * @param machineNo
	 * @param repDesc
	 * @param createDate
	 */
	public void updateMachineStatus1(String machineNo, String repDesc,
			String createDate);

	/**
	 * 根据编号查询最大时间
	 * @param machineNo
	 * @return
	 */
	public String findMaxCreateDate(String machineNo);
	
	/**
	 * 网点地图查询异常报警数据=全部数据（正常+异常-异常在前）
	 * @return
	 */
	public List<UnitMachineStatusPojo> findUnitMachineStatus1();

	/**
	 * 网点地图查询异常报警数据=正常机器的数据
	 * @return
	 */
	public List<UnitMachineStatusPojo> findUnitMachineStatus2();

	/**
	 * 网点地图查询异常报警数据=异常机器的数据
	 * @return
	 */
	public List<UnitMachineStatusPojo> findUnitMachineStatus3();

	/**
	 * 导出 - 部署信息
	 * @param map
	 * @return
	 */
	public List<DeployMachineView> findDeployMachineListExcel(
			Map<String, String> map);

	/**
	 * 查询唐山网点数量
	 * @return
	 */
	public Long findMachineCounts();
	
	/**
	 * 终端设备种类
	 * @return
	 */
	public  List<FindAllCountsPojo> findMachineTypeCounts();

	/**
	 * 终端运行状态数量-初始
	 * @return
	 */
	public Long findMachineInitialCounts();

	/**
	 * 终端运行状态数量-投产
	 * @return
	 */
	public Long findMachineOperationCounts();

	/**
	 * 终端运行状态数量-下线
	 * @return
	 */
	public Long findMachineOfflineCounts();

	/**
	 * 终端启用状态数据量-正常
	 * @return
	 */
	public Long findMachineNormalCounts();

	/**
	 * 终端启用状态数据量-异常
	 * @return
	 */
	public Long findMachineErrCounts();

	/**
	 * 终端启用状态数据量-未知
	 * @return
	 */
	public Long findMachineUnknownCounts();



}
