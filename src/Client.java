import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Classe Client
 * 
 * Permet � un client de faire ses courses en magasin en lui permettant
 * d'emprunter et restituer un chariot dans une file de chariots
 * d'avoir une liste de courses contenant les produits et la quantit� souhait�
 * 
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 *
 */
public class Client extends Thread {
	/** Liste de courses contenant les produits et leurs quantit�s */
	Map<Produits, Integer> listeDeCourses;
	
	/** File de chariot du supermarch� */
	FileDeChariot fileDeChariot;
	
	/** Liste des rayons du supermarch� */
	List<Rayon> listeRayons;
	
	/** Caisse où se diriger pour le paiement */
	Caisse caisse;
	
	/** Les �tats du client */
	String etat;
	
	/**
	 * Constructeur
	 * D�finit 
	 * une liste de courses, 
	 * un file de chariot,
	 * une liste de rayon
	 * 
	 * @param fileDeChariot
	 * @param listeRayons
	 */
	public Client(FileDeChariot fileDeChariot, List<Rayon> listeRayon, Caisse caisse) {
		this.fileDeChariot = fileDeChariot;
		this.listeRayons = listeRayon;
		this.caisse = caisse;
		this.etat = "INITIALISATION";
		
		this.listeDeCourses = new HashMap<>();
		this.setListeDeCourses();
	}
	
	/**
	 * D�roulement du thread
	 * Emprunte un chariot
	 * Parcours tous les rayons pour recup�rer les produits de sa liste de courses
	 * Restitue le chariot
	 */
	public void run() {
		try {
			etat = "ATTENTE_CHARIOT";
			this.emprunterChariot();
			//Parcours des rayons
			etat = "EN_COURSE";
			for(Rayon rayon: listeRayons){
				etat = "ATTENTE_PRODUIT";
				prendreProduits(rayon);
				Thread.sleep(Supermarche.TPS_MARCHE_CLT); // Simule le temps de marche entre les rayons
			}
			
			//Passer à la caisse
			passerAlaCaisse();
			
			System.out.println(Thread.currentThread().getName() + " Reglement.");
			Thread.sleep(5000); //Simule le reglement
			
			//Restituer le chariot
			this.restituerChariot();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
//	System.out.println(Thread.currentThread().getName() + ": " + action + " sur le rayon " + produit
//	+ ", il contient " + stockDisponible + " produit(s)");

	/**
	 * D�finit une liste de courses de fa�on al�atoire
	 */
	public void setListeDeCourses(){
		Random rd = new Random();
		
		this.listeDeCourses.put(Produits.SUCRE, rd.nextInt(10));
		this.listeDeCourses.put(Produits.FARINE, rd.nextInt(10));
		this.listeDeCourses.put(Produits.BEURRE, rd.nextInt(10));
		this.listeDeCourses.put(Produits.LAIT, rd.nextInt(10));
	}
	
	/**
	 * D�finit une liste de courses � partir de celle indiqu�
	 * @param listeDeCourses Liste de courses
	 */
	public void setListeDeCourses(Map<Produits, Integer> listeDeCourses){
		this.listeDeCourses = listeDeCourses;
	}	

	/**
	 * Emprunte un chariot dans la file de chariot
	 * 
	 * @throws InterruptedException
	 */
	public void emprunterChariot() throws InterruptedException {
		fileDeChariot.deStocker();
	}

	/**
	 * Restitue un chariot dans la file de chariot
	 * 
	 * @throws InterruptedException
	 */
	public void restituerChariot() throws InterruptedException {
		fileDeChariot.stocker();
	}
	
	/**
	 * Retire dans un rayon donn�, la quantit� de produit figurant sur 
	 * la liste de courses
	 * 
	 * @param rayon Rayon sur lequel sera retir� le produit
	 * @throws InterruptedException
	 */
	public synchronized void prendreProduits(Rayon rayon) throws InterruptedException{
		Produits produitRayon = rayon.getProduit();
		int qteProduit = listeDeCourses.get(produitRayon);
		System.out.println(Thread.currentThread().getName() + " Liste Courses: " + produitRayon + " = " + qteProduit);
		while(qteProduit > 0){
			rayon.deStocker();
			qteProduit--;
		}	
	}
	
	/**
	 * Poser ses produits les uns après les autres sur le tapis de la caisse (dans les limites du tapis)
	 * puis liberer la caisse
	 * @throws InterruptedException
	 */
	public synchronized void passerAlaCaisse() throws InterruptedException{
		while(!caisse.clientSuivant){
			System.out.println(Thread.currentThread().getName() + " En attente à la caisse.");
			this.wait();
		}
		System.out.println(Thread.currentThread().getName() + " Passage en caisse.");
		
		caisse.setClientSuivant(false); //Signaler que la caisse est occupée
		for(Produits produit: listeDeCourses.keySet()){
			for(int i=0, qtProduit = listeDeCourses.get(produit); i < qtProduit; i++){
				caisse.charger(produit);
			}
		}
		caisse.setClientSuivant(true); //Signaler que la caisse est libérée
	}

}
