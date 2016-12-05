
/**
 * Classe Employé de caisse
 * <p>
 * Enregistre les produit présent sur le tapis
 * </p>
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 *
 * @see Thread
 */
public class EmployeDeCaisse extends Thread {
	/** 
	 * Caisse sur lequel sera rétiré les produits
	 * 
	 * @see EmployeDeCaisse#EmployeDeCaisse(Caisse)
	 * @see EmployeDeCaisse#recupererProduit()
	 */
	Caisse caisse;
	
	/**
	 * Constructeur
	 * <p>
	 * Fixe la caisse sur laquelle l'employé travaillera et définit le 
	 * thread comme un deamon (le thread ne s'arreter de tourner que quand tous les threads
	 * non demon se serons arrêté)
	 * </p>
	 * 
	 * @param caisse
	 * @see Caisse
	 */
	public EmployeDeCaisse(Caisse caisse){
		this.setDaemon(true);
		this.caisse = caisse;
	}
	
	/**
	 * File de déroulement du thread
	 * <p>
	 * Recupère les produit jusqu'à ce que le thread s'arrête (deamon)
	 * </p>
	 */
	public void run(){
		try {
			while(true){
				recupererProduit();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retire un produit de la caisse
	 * 
	 * @throws InterruptedException
	 */
	public void recupererProduit() throws InterruptedException{
		caisse.retirer();
	}
	
}
