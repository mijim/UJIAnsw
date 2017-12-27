package model;

import java.io.Serializable;
import java.util.Date;

public class Pregunta implements Serializable{
	private static final long serialVersionUID = 6143418671025949534L;
	private int idPregunta;
	private int idUsuario;
	private Date fecha;
	private String titulo;
	private String subtitulo;
	private int valoracion;
	private int nRespuestas;

	public Pregunta(int id_pregunta, int id_usuario, Date fecha, String titulo, String subtitulo, int valoracion, int n_respuestas) {
		this.idPregunta=id_pregunta; 
		this.idUsuario=id_usuario;
		this.fecha=fecha;
		this.titulo=titulo;
		this.subtitulo=subtitulo;
		this.valoracion=valoracion;
		this.nRespuestas=n_respuestas;
	}
	public Pregunta( int id_usuario, Date fecha, String titulo, String subtitulo, int valoracion, int n_respuestas) {
		this.idUsuario=id_usuario;
		this.fecha=fecha;
		this.titulo=titulo;
		this.subtitulo=subtitulo;
		this.valoracion=valoracion;
		this.nRespuestas=n_respuestas;
	}
	
	public int getIdPregunta() { return idPregunta; }
	public void setIdPregunta(int id_pregunta) { this.idPregunta = id_pregunta; }

	public int getIdUsuario() { return idUsuario; }
	public void setIdUsuario(int id_usuario) { this.idUsuario = id_usuario; }

	public Date getFecha() { return fecha; }
	public void setFecha(Date fecha) { this.fecha = fecha; }

	public String getTitulo() { return titulo; }
	public void setTitulo(String titulo) { this.titulo = titulo; }

	public String getSubtitulo() { return subtitulo; }
	public void setSubtitulo(String subtitulo) { this.subtitulo = subtitulo; }

	public int getValoracion() { return valoracion; }
	public void setValoracion(int valoracion) { this.valoracion = valoracion; }

	public int getNRespuestas() { return nRespuestas; }
	public void setNRespuestas(int n_respuestas) { this.nRespuestas = n_respuestas; }
}