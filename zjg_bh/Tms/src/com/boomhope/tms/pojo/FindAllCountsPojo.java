package com.boomhope.tms.pojo;
/**
 * 信息轮播接口pojo
 * @author WU CHAO
 *
 */
public class FindAllCountsPojo {

	private String machineCounts;//唐山网点数量
	private String machineTypeCounts;//终端设备种类
	private Long Counts;//终端设备种类
	private String code;
	private String machineInitialCounts;//终端运行状态数量-初始
	private String machineOperationCounts;//终端运行状态数量-投产
	private String machineOfflineCounts;//终端运行状态数量-下线
	
	private String machineNormalCounts;//终端启用状态数据量正常
	private String machineErrCounts;//终端启用状态数据量异常
	private String machineUnknownCounts;//终端启用状态数据量未知
	
	public FindAllCountsPojo(){
		
	}
	public FindAllCountsPojo(Object[] objects) {
		super();
		this.code = (String) objects[0];
		this.Counts = (Long) objects[1];
	}
	
	
	public String getMachineCounts() {
		return machineCounts;
	}
	public void setMachineCounts(String machineCounts) {
		this.machineCounts = machineCounts;
	}
	public String getMachineTypeCounts() {
		return machineTypeCounts;
	}
	public void setMachineTypeCounts(String machineTypeCounts) {
		this.machineTypeCounts = machineTypeCounts;
	}
	public Long getCounts() {
		return Counts;
	}
	public void setCounts(Long counts) {
		Counts = counts;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMachineInitialCounts() {
		return machineInitialCounts;
	}
	public void setMachineInitialCounts(String machineInitialCounts) {
		this.machineInitialCounts = machineInitialCounts;
	}
	public String getMachineOperationCounts() {
		return machineOperationCounts;
	}
	public void setMachineOperationCounts(String machineOperationCounts) {
		this.machineOperationCounts = machineOperationCounts;
	}
	public String getMachineOfflineCounts() {
		return machineOfflineCounts;
	}
	public void setMachineOfflineCounts(String machineOfflineCounts) {
		this.machineOfflineCounts = machineOfflineCounts;
	}
	public String getMachineNormalCounts() {
		return machineNormalCounts;
	}
	public void setMachineNormalCounts(String machineNormalCounts) {
		this.machineNormalCounts = machineNormalCounts;
	}
	public String getMachineErrCounts() {
		return machineErrCounts;
	}
	public void setMachineErrCounts(String machineErrCounts) {
		this.machineErrCounts = machineErrCounts;
	}
	public String getMachineUnknownCounts() {
		return machineUnknownCounts;
	}
	public void setMachineUnknownCounts(String machineUnknownCounts) {
		this.machineUnknownCounts = machineUnknownCounts;
	}
	
	
	
}
