package org.onap.usecaseui.server.bean.sotn;

public class Pnf {
	
	private String pnfName;
	
	public Pnf( String pnfName) {
		this.pnfName = pnfName;
	}

	public Pnf() {
	}

	public String getPnfName() {
		return pnfName;
	}

	public void setPnfName(String pnfName) {
		this.pnfName = pnfName;
	}
	
}
