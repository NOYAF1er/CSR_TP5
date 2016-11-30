
public class EmployeDeCaisse extends Thread {
	
	Caisse caisse;
	
	public EmployeDeCaisse(Caisse caisse){
		this.setDaemon(true);
		this.caisse = caisse;
	}
	
	public void run(){
		try {
			recupererProduit();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void recupererProduit() throws InterruptedException{
		caisse.retirer();
	}
	
}
