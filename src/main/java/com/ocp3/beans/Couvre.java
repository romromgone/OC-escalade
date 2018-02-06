package com.ocp3.beans;

public class Couvre {
	private Long idTopo;
	private Long idSite;
	
	private Topo topo;
	private Site site;
	
	
	public Long getIdTopo() {
		return idTopo;
	}
	public void setIdTopo(Long idTopo) {
		this.idTopo = idTopo;
	}
	
	public Long getIdSite() {
		return idSite;
	}
	public void setIdSite(Long idSite) {
		this.idSite = idSite;
	}
	
	
	public Topo getTopo() {
		return topo;
	}
	public void setTopo(Topo topo) {
		this.topo = topo;
	}
	
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}

}
