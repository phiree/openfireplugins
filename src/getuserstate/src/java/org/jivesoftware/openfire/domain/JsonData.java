package org.jivesoftware.openfire.domain;

public class JsonData {
	
	private String protocol_CODE;
	private User ReqData;
	private Long stamp_TIMES;
	public JsonData() {
		
	}
	public JsonData(String protocol_CODE, User reqData, long l) {
		super();
		this.protocol_CODE = protocol_CODE;
		ReqData = reqData;
		this.stamp_TIMES = l;
	}
	public String getProtocol_CODE() {
		return protocol_CODE;
	}
	public void setProtocol_CODE(String protocol_CODE) {
		this.protocol_CODE = protocol_CODE;
	}
	public User getReqData() {
		return ReqData;
	}
	public void setReqData(User ReqData) {
		ReqData = ReqData;
	}
	public Long getStamp_TIMES() {
		return stamp_TIMES;
	}
	public void setStamp_TIMES(Long stamp_TIMES) {
		this.stamp_TIMES = stamp_TIMES;
	}
	@Override
	public String toString() {
		return "JsonData [protocol_CODE=" + protocol_CODE + ", ReqData="
				+ ReqData + ", stamp_TIMES=" + stamp_TIMES + "]";
	}
	
	

}
