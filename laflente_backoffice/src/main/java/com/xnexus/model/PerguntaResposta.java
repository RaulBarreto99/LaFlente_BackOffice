package com.xnexus.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class PerguntaResposta implements Serializable{

private static final long serialVersionUID = 1L;

	public PerguntaResposta( String pergunta, String resposta, long codigoProduto) {
		this.pergunta = pergunta;
		this.resposta = resposta;
		this.codigoProduto = codigoProduto;
	}

	public PerguntaResposta( ) {

	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String pergunta;
	private String resposta;
	private long codigoProduto;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPergunta() {
		return pergunta;
	}
	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}
	public String getResposta() {
		return resposta;
	}
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	public long getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(long codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

}
