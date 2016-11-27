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
	private Produits produit;

	/** Stock de produit disponible dans le rayon */
	private int stockDisponible;

	/** Stock maximum de d'exemplaire dans le rayon */
	private int capacite;

	/**
	 * Constructeur: Définit le type de produit proposé dans le rayon, le stock
	 * disponible et la quantité maximale d'exemplaire
	 * 
	 * @param produit
	 *            Produit proposé dans le rayon
	 * @param stockInitiale
	 *            Stock initiale du rayon
	 * @param capacite
	 *            Stock maximun du rayon
	 * 
	 */
	public Rayon(Produits produit, int stockInitiale, int capacite) {
		this.produit = produit;
		this.stockDisponible = stockInitiale;
		this.capacite = capacite;
	}

	/**
	 * @return Le type de produit disponible dans le rayon
	 */
	public Produits getProduit() {
		return this.produit;
	}

	/**
	 * @return La quantité maximal d'exemplaire possible sur ce rayon
	 */
	public int getCapacite() {
		return this.capacite;
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
	 * Définit le stock de produit disponible
	 * 
	 * @param stock
	 *            Nouveau stock à définir
	 */
	public synchronized void setStockDisponible(int stock) {
		this.stockDisponible = (stock > capacite) ? capacite : stock;
		this.notifyAll();
	}

	/**
	 * Ajoute un produit à la fois au rayon dès que cela est possible
	 */
	public synchronized void stocker() {
		if (stockDisponible < capacite) {
			this.stockDisponible++;
			this.notifyAll();
			this.afficher("depot");// Affiche le niveau de stock
		}
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
	 * Affiche l'etat du stock de produit du rayon
	 */
	public void afficher(String action) {
		System.out.println(Thread.currentThread().getName() + ": Après " + action + " sur le rayon " + produit
				+ ", il contient " + stockDisponible + " produit(s)");
	}
	
}
