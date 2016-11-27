/**
 * Classe Rayon
 * 
 * Gère le stock de produit d'un rayon
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 *
 */
public class Rayon {

	/** Type de produit disponible dans le rayon */
	private String produit;

	/** Stock de produit disponible dans le rayon */
	private int stockDisponible;

	/**
	 * Constructeur Définit le type de produit proposé dans le rayon et le stock
	 * disponible
	 * 
	 * @param produit
	 *            Produit proposé dans le rayon
	 * @param stockInitiale
	 *            Stock initiale du rayon
	 */
	public Rayon(String produit, int stockInitiale) {
		this.produit = produit;
		this.stockDisponible = stockInitiale;
	}

	/**
	 * 
	 * @return Le type de produit disponible dans le rayon
	 */
	public String getProduit() {
		return this.produit;
	}

	/**
	 * Définit le stock de produit disponible
	 * 
	 * @param stock
	 *            Nouveau stock à définir
	 */
	public void setStockDisponible(int stock) {
		this.stockDisponible = stock;
	}

	/**
	 * Retourne le stock de produit disponible
	 * 
	 * @return Stock de produit disponible
	 */
	public int getStockDisponible() {
		return stockDisponible;
	}

	/**
	 * Affiche l'etat du stock de produit du rayon
	 */
	public void afficher(String action) {
		System.out.println(Thread.currentThread().getName() + ": Après " + action + " sur le rayon " + produit
				+ ", il contient " + stockDisponible + " produit(s)");
	}

	/**
	 * Rétire un produit à la fois du rayon dès que cela est possible
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void deStocker() throws InterruptedException {

		while (stockDisponible == 0) {
			this.wait();
		}
		this.stockDisponible--;
		this.afficher("retrait");// Affiche le niveau de stock
	}

	/**
	 * Ajoute un produit à la fois au rayon dès que cela est possible
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void stocker() throws InterruptedException {

		this.stockDisponible++;
		this.notifyAll();
		this.afficher("depot");// Affiche le niveau de stock
	}
}
