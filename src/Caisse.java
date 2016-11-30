import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Caisse {
	/** Liste des produits présent sur le tapis */
	Queue<Produits> tapis;
	
	/** Etat du tapis (occupé/libre) */
	Boolean clientSuivant;
	
	/**
	 * Constructeur
	 * Instancie le tapis, lui définie sa taille et se tient prêt à recevoir un client
	 */
	public Caisse(){
		clientSuivant = true;
		tapis = new ArrayBlockingQueue<>(Supermarche.TAILLE_TAPIS);
	}
	
	/**
	 * Ajout un produit au tapis dès que possible
	 * @param produit Produit à ajouter
	 * @throws InterruptedException
	 */
	public synchronized void charger(Produits produit) throws InterruptedException{
		while(tapis.size() == Supermarche.TAILLE_TAPIS){
			this.wait();
		}
		tapis.add(produit);
		afficher("Depot");
	}
	
	/**
	 * Retire un produit du tapis dès que possible
	 * @throws InterruptedException
	 */
	public synchronized void retirer() throws InterruptedException{
		while(tapis.isEmpty()){
			this.wait();
		}
		tapis.poll();
		this.notifyAll();
		afficher("Retrait");
	}
	
	/**
	 * 
	 * @return L'état du tapis (occupé/libre)
	 */
	public Boolean getClientSuivant() {
		return clientSuivant;
	}
	
	/**
	 * Définit l'état du tapis (occupé/libre)
	 * @param clientSuivant
	 */
	public void setClientSuivant(Boolean clientSuivant) {
		this.clientSuivant = clientSuivant;
	}
	
	/**
	 * Affiche l'etat du stock de produit du rayon
	 */
	public void afficher(String action) {
		System.out.println(Thread.currentThread().getName() + " Tapis: " + action + " d'un produit");
	}

}
