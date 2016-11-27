/**
 * Classe Rayon
 * 
 * G�re le stock de produit d'un rayon
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
	 * Constructeur: D�finit le type de produit propos� dans le rayon, le stock
	 * disponible et la quantit� maximale d'exemplaire
	 * 
	 * @param produit
	 *            Produit propos� dans le rayon
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
	 * @return La quantit� maximal d'exemplaire possible sur ce rayon
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
	 * D�finit le stock de produit disponible
	 * 
	 * @param stock
	 *            Nouveau stock � d�finir
	 */
	public synchronized void setStockDisponible(int stock) {
		this.stockDisponible = (stock > capacite) ? capacite : stock;
		this.notifyAll();
	}

	/**
	 * Ajoute un produit � la fois au rayon d�s que cela est possible
	 */
	public synchronized void stocker() {
		if (stockDisponible < capacite) {
			this.stockDisponible++;
			this.notifyAll();
			this.afficher("depot");// Affiche le niveau de stock
		}
	}
	
	/**
	 * R�tire un produit � la fois du rayon d�s que cela est possible
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
		System.out.println(Thread.currentThread().getName() + ": Apr�s " + action + " sur le rayon " + produit
				+ ", il contient " + stockDisponible + " produit(s)");
	}
	
}
