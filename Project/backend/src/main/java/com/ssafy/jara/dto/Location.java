package com.ssafy.jara.dto;

import java.util.Date;

public class Location {
	
	private int no;
	private String name;
	private double x;
	private double y;
	private String nx;
	private String ny;
	private String PTY;
	private String SKY;
	private String T1H;
	private Date updated_at;
	
	public Location() {}
	
	public Location(int no, String name, double x, double y, String nx, String ny, String PTY, String SKY, String T1H, Date updated_at) {
		super();
		this.no = no;
		this.name = name;
		this.x = x;
		this.y = y;
		this.nx = nx;
		this.ny = ny;
		this.PTY = PTY;
		this.SKY = SKY;
		this.T1H = T1H;
		this.updated_at = updated_at;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getNx() {
		return nx;
	}

	public void setNx(String nx) {
		this.nx = nx;
	}

	public String getNy() {
		return ny;
	}

	public void setNy(String ny) {
		this.ny = ny;
	}

	public String getPTY() {
		return PTY;
	}

	public void setPTY(String pTY) {
		PTY = pTY;
	}

	public String getSKY() {
		return SKY;
	}

	public void setSKY(String sKY) {
		SKY = sKY;
	}

	public String getT1H() {
		return T1H;
	}

	public void setT1H(String t1h) {
		T1H = t1h;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

}
