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
			afficher("En attente de dépot");
			this.wait();
		}
		tapis.add(produit);
		this.notifyAll();
		afficher("Depot");
	}
	
	/**
	 * Retire un produit du tapis dès que possible
	 * @throws InterruptedException
	 */
	public synchronized void retirer() throws InterruptedException{
		while(tapis.isEmpty()){
			afficher("En attente de retrait");
			this.wait();
		}
		tapis.poll();
		this.notifyAll();
		afficher("Retrait");
	}
	
	/**
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void clientSuivant() throws InterruptedException{
		while(!this.getClientSuivant()){
			System.out.println(Thread.currentThread().getName() + " En attente à la caisse.");
			this.wait();
		}
		System.out.println(Thread.currentThread().getName() + " Passage en caisse.");		
		this.setClientSuivant(false); //Signaler que la caisse est occupée
	}
	
	/**
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void reglementClient() throws InterruptedException{
		while(this.isOccuped()){
			System.out.println(Thread.currentThread().getName() + " En attente de règlement.");
			this.wait();
		}
		System.out.println(Thread.currentThread().getName() + " Reglement en cours...");
		Thread.sleep(2000); //Simule le reglement
		this.setClientSuivant(true); //Signaler que la caisse est libérée
		System.out.println(Thread.currentThread().getName() + " Reglement effectué !");
		this.notifyAll();
	}
	
	/**
	 * L'état d'occupation du tapis
	 * @return true: si occupé | false sinon
	 */
	public synchronized Boolean isOccuped(){
		return (tapis.size() == 0) ? false : true;
	}
	
	/**
	 * 
	 * @return L'état du tapis (occupé/libre)
	 */
	public synchronized Boolean getClientSuivant() {
		return clientSuivant;
	}
	
	/**
	 * Définit l'état du tapis (occupé/libre)
	 * @param clientSuivant
	 */
	public synchronized void setClientSuivant(Boolean clientSuivant) {
		this.clientSuivant = clientSuivant;
	}
	
	/**
	 * Affiche l'etat du tapis
	 */
	public synchronized void afficher(String action) {
		//System.out.println(Thread.currentThread().getName() + " Tapis: " + action + " d'un produit (produits sur le tapis: "+ tapis.size() +")");
	}

}
