package pxt.etq.domain.estoque;

public enum TipoOperacao {
	
	RECEBIMENTO ("RECEBIMENTO"), LIBERACAO ("LIBERACAO"), VENDA ("VENDA");
	
	private String descricao;

	
	private TipoOperacao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
