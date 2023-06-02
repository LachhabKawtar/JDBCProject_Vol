package com.example.demo.entities;

import java.util.Date;

public class Vol {
	private Integer id;
	private Date dateA;
	private Date dateD;
	private String aeroportA;
	private String aeroportD;
	private Integer nbrEscales;

	public Vol()
	{

	}
	public Vol(Integer id,Date dateA, Date dateD, String aeroportA, String aeroportD, int nbrEscales) {
		this.id=id;
		this.dateA = dateA;
		this.dateD = dateD;
		this.aeroportA = aeroportA;
		this.aeroportD = aeroportD;
		this.nbrEscales = nbrEscales;
	}
    

	public Date getDateA() {
		return dateA;
	}
	public void setDateA(Date dateA) {
		this.dateA = dateA;
	}
	public Date getDateD() {
		return dateD;
	}
	public void setDateD(Date dateD) {
		this.dateD = dateD;
	}
	public String getAeroportA() {
		return aeroportA;
	}
	public void setAeroportA(String aeroportA) {
		this.aeroportA = aeroportA;
	}
	public String getAeroportD() {
		return aeroportD;
	}
	public void setAeroportD(String aeroportD) {
		this.aeroportD = aeroportD;
	}
	public int getNbrEscales() {
		return nbrEscales;
	}
	public void setNbrEscales(int nbrEscales) {
		this.nbrEscales = nbrEscales;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Vol [dateA=" + dateA + ", dateD=" + dateD + ", aeroportA=" + aeroportA + ", aeroportD=" + aeroportD+ ", nbrEscales=" + nbrEscales + "]";
	}
}
