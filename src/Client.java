import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Classe Client
 * 
 * Permet à un client de faire ses courses en magasin en lui permettant
 * d'emprunter et restituer un chariot dans une file de chariots
 * d'avoir une liste de courses contenant les produits et la quantité souhaité
 * 
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 *
 */
public class Client extends Thread {
	/** Liste de courses contenant les produits et leurs quantités */
	Map<Produits, Integer> listeDeCourses;
	
	/** File de chariot du supermarché */
	FileDeChariot fileDeChariot;
	
	/** Liste des rayons du supermarché */
	List<Rayon> listeRayons;
	
	/** Les états du client */
	String etat;
	
	/**
	 * Constructeur
	 * Définit 
	 * une liste de courses, 
	 * un file de chariot,
	 * une liste de rayon
	 * 
	 * @param fileDeChariot
	 * @param listeRayons
	 */
	public Client(FileDeChariot fileDeChariot, List<Rayon> listeRayon) {
		this.fileDeChariot = fileDeChariot;
		this.listeRayons = listeRayon;
		this.etat = "INITIALISATION";
		
		this.listeDeCourses = new HashMap<>();
		this.setListeDeCourses();
		
	}
	
	/**
	 * Déroulement du thread
	 * Emprunte un chariot
	 * Parcours tous les rayons pour recupérer les produits de sa liste de courses
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
				Thread.sleep(300); // Simule le temps de marche entre les rayons
			}
			this.restituerChariot();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Définit une liste de courses de façon aléatoire
	 */
	public void setListeDeCourses(){
		Random rd = new Random();
		
		this.listeDeCourses.put(Produits.SUCRE, rd.nextInt(10));
		this.listeDeCourses.put(Produits.FARINE, rd.nextInt(10));
		this.listeDeCourses.put(Produits.BEURRE, rd.nextInt(10));
		this.listeDeCourses.put(Produits.LAIT, rd.nextInt(10));
	}
	
	/**
	 * Définit une liste de courses à partir de celle indiqué
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
		System.out.println(Thread.currentThread().getName() + "_Client sur le point d'emprunter un chariot ");
		fileDeChariot.deStocker();
	}

	/**
	 * Restitue un chariot dans la file de chariot
	 * 
	 * @throws InterruptedException
	 */
	public void restituerChariot() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + "_Client sur le point de restituer un chariot ");
		fileDeChariot.stocker();
	}
	
	/**
	 * Retire dans un rayon donné, la quantité de produit figurant sur 
	 * la liste de courses
	 * 
	 * @param rayon Rayon sur lequel sera retiré le produit
	 * @throws InterruptedException
	 */
	public void prendreProduits(Rayon rayon) throws InterruptedException{
		Produits produitRayon = rayon.getProduit();
		int qteProduit = listeDeCourses.get(produitRayon);
		System.out.println(Thread.currentThread().getName() + " Liste Courses: " + produitRayon + " = " + qteProduit);
		while(qteProduit > 0){
			System.out.println(Thread.currentThread().getName() + "_Client sur le point de prendre un produit sur le rayon "+ rayon.getProduit());
			rayon.deStocker();
			qteProduit--;
		}	
	}

}
