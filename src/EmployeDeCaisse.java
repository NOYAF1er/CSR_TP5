
/**
 * Classe Employ� de caisse
 * <p>
 * Enregistre les produit pr�sent sur le tapis
 * </p>
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 *
 * @see Thread
 */
public class EmployeDeCaisse extends Thread {
	/** 
	 * Caisse sur lequel sera r�tir� les produits
	 * 
	 * @see EmployeDeCaisse#EmployeDeCaisse(Caisse)
	 * @see EmployeDeCaisse#recupererProduit()
	 */
	Caisse caisse;
	
	/**
	 * Constructeur
	 * <p>
	 * Fixe la caisse sur laquelle l'employ� travaillera et d�finit le 
	 * thread comme un deamon (le thread ne s'arreter de tourner que quand tous les threads
	 * non demon se serons arr�t�)
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
	 * File de d�roulement du thread
	 * <p>
	 * Recup�re les produit jusqu'� ce que le thread s'arr�te (deamon)
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
