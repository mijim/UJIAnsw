package model;

import java.io.Serializable;
import java.util.Date;

public class Respuesta implements Serializable{
	private static final long serialVersionUID = -6263169882789901878L;
	private int idRespuesta;
	private int idPregunta;
	private int idUsuario;
	private Date fecha;
	private String txtRespuesta; 
	private int valoracion;

	public Respuesta(int id_respuesta, int id_pregunta, int id_usuario, Date fecha, String txt_respuesta, int valoracion) {
		this.idRespuesta=id_respuesta;
		this.idPregunta=id_pregunta;
		this.idUsuario=id_usuario;
		this.fecha=fecha;
		this.txtRespuesta=txt_respuesta;
		this.valoracion=valoracion;
	}

	public int getIdRespuesta() { return idRespuesta; }
	public void setIdRespuesta(int id_respuesta) { this.idRespuesta = id_respuesta; }

	public int getIdPregunta() { return idPregunta; }
	public void setIdPregunta(int id_pregunta) { this.idPregunta = id_pregunta; }

	public int getIdUsuario() { return idUsuario; }
	public void setIdUsuario(int id_usuario) { this.idUsuario = id_usuario; }

	public Date getFecha() { return fecha; }
	public void setFecha(Date fecha) { this.fecha = fecha; }

	public String getTxtRespuesta() { return txtRespuesta; }
	public void setTxtRespuesta(String txt_respuesta) { this.txtRespuesta = txt_respuesta; }

	public int getValoracion() { return valoracion; }
	public void setValoracion(int valoracion) { this.valoracion = valoracion; }
}