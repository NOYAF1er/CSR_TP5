/**
 * Classe File de chariot
 * 
 * Gère le stock disponible dans la file de chariot en permettant le destockage
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
		this.setStock(stockInit);
	}

	/**
	 * Définit le stock de chariot disponible
	 * 
	 * @param stock
	 *            Nouveau stock à définir
	 */
	public void setStock(int stock) {
		this.stockDisponible = stock;
	}

	/**
	 * Retourne le stock de chariot disponible
	 * 
	 * @return Stock de chariot
	 */
	public int getStock() {
		return stockDisponible;
	}

	/**
	 * Affiche l'etat du stock de la file de chariot
	 */
	public void afficher() {
		System.out.println(Thread.currentThread().getName() + ": Après action sur le file de stock, il contient "
				+ stockDisponible + " chariot(s).");
	}

	/**
	 * Rétire un chariot à la fois du stock dès que cela est possible
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void deStocker() throws InterruptedException {

		while (stockDisponible == 0) {
			this.wait();
		}
		this.stockDisponible--;
		this.afficher();// Affiche le niveau de stock du site
	}

	/**
	 * Ajoute un chariot à la fois du stock dès que cela est possible
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void stocker() throws InterruptedException {

		this.stockDisponible++;
		this.notifyAll();
		this.afficher();// Affiche le niveau de stock du site
	}

}
