package com.gauss.shell;
public class hostinfo{
	public  String host;
	public  String name;
	public  String password;
	public  String port;
	public  String sql_dst_path;
	@Override
	public String toString() {
		return "hostinfo [host=" + host + ", name=" + name + ", password=" + password + ", port=" + port
				+ ", sql_dst_path=" + sql_dst_path + "]";
	}
	public String getHost() {
		return host;
	}
	public String getPassword() {
		return password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getSql_dst_path() {
		return sql_dst_path;
	}
	public void setSql_dst_path(String sql_dst_path) {
		this.sql_dst_path = sql_dst_path;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}