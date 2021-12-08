public class Nadador extends Atleta{

	private String estilo;
	
	public Nadador (String nome, String numero, String estilo) {
		super (nome, numero, "Nadador");
		this.estilo = estilo;
	}

	public String getEstilo(){
		return this.estilo;
	}

	public void setEstilo(String estilo){
		this.estilo = estilo;
	}

	public String toString (){
		return super.toString() + "Estilo: " + this.getEstilo();
	}
}
