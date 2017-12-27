package model;

import java.io.Serializable;

public class Usuario implements Serializable{
	private static final long serialVersionUID = 667766289398214039L;
	private int idUsuario;
	private String nombre;
	private String pass;
	private String email;

	public Usuario(int id_usuario, String nombre, String email, String pass) { 
		this.idUsuario=id_usuario;
		this.nombre=nombre;
		this.pass=pass;
		this.email=email;
	}
	public Usuario(String nombre, String email, String pass) { 
		this.nombre=nombre;
		this.pass=pass;
		this.email=email;
	}
	public int getIdUsuario() { return idUsuario; }
	public void setIdUsuario(int id_usuario) {	this.idUsuario = id_usuario; }

	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }

	public String getPass() { return pass; }
	public void setPass(String pass) { this.pass = pass; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
}