/**
 * Classe File de chariot
 * 
 * G�re le stock disponible dans la file de chariot en permettant le destockage
 * et le stockage de chariot
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 *
 */
public class FileDeChariot {

	/** Stock de chariot disponible */
	private int stockDisponible;

	/**
	 * Constructeur
	 * 
	 * @param stockInit
	 *            Stock initial
	 */
	public FileDeChariot(int stockInit) {
		this.setStockDisponible(stockInit);
	}

	/**
	 * D�finit le stock de chariot disponible
	 * 
	 * @param stock
	 *            Nouveau stock � d�finir
	 */
	public void setStockDisponible(int stock) {
		this.stockDisponible = stock;
	}

	/**
	 * Retourne le stock de chariot disponible
	 * 
	 * @return Stock de chariot
	 */
	public int getStockDisponible() {
		return stockDisponible;
	}

	/**
	 * R�tire un chariot � la fois du stock d�s que cela est possible
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void deStocker() throws InterruptedException {
		while (stockDisponible == 0) {
			this.afficher("En attente");// Affiche le niveau de stock
			this.wait();
		}
		this.stockDisponible--;
		this.afficher("Emprunt");// Affiche le niveau de stock
	}

	/**
	 * Ajoute un chariot � la fois du stock d�s que cela est possible
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void stocker() throws InterruptedException {
		this.stockDisponible++;
		this.notifyAll();
		this.afficher("Restitution");// Affiche le niveau de stock
	}
	
	/**
	 * Affiche l'etat du stock de la file de chariot
	 */
	public void afficher(String action) {
		System.out.println(Thread.currentThread().getName() + ": "+ action + " sur la file de chariots, stock disponible: "
				+ stockDisponible + " chariot(s).");
	}

}
