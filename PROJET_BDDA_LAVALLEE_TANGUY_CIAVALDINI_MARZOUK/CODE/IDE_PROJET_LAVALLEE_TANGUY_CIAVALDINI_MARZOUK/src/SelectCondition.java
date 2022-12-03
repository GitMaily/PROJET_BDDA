
public class SelectCondition {

	private int indice; 
	private static String[] comparateur = {"=","<",">","<=",">="};  
	private String valcomparaison;
	private String comp;
	
	public SelectCondition(int indice, String comp, String valcomparaison) {
		this.setIndice(indice);
		this.comp=comp;
		this.valcomparaison= valcomparaison;
	}
	
	public boolean VerifCondition(String ValRecord)
	{
		switch(comp) {
		case "=": return valcomparaison.equals(ValRecord);
		case "<": return ValRecord.compareTo(valcomparaison)<0;
		case ">": return ValRecord.compareTo(valcomparaison)>0;
		case "<=": return ValRecord.compareTo(valcomparaison)<=0;
		case ">=": return ValRecord.compareTo(valcomparaison)>=0;
		default: System.out.println("comparateur incorrect");
				return false;
		}
	}
	public String toString() {
		return "Values:"+valcomparaison+"  operateur: "+comp+"  indice: "+indice; 
	}

	public int getIndice() {
		return indice;
	}
	public void setIndice(int indice) {
		this.indice = indice;
	}

	public static String[] getComparateur() {
		return comparateur;
	}
	
	public String getValcomparaison() {
		return valcomparaison;
	}
	public void setValcomparaison(String valcomparaison) {
		this.valcomparaison = valcomparaison;
	}

	public String getComp() {
		return valcomparaison;
	}
	public void setComp(String comp) {
		this.comp = comp;
	}
	
	
}
