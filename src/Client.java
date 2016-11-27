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
	public Client(FileDeChariot fileDeChariot, List<Rayon> listeRayon) {
		this.fileDeChariot = fileDeChariot;
		this.listeRayons = listeRayon;
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
				Thread.sleep(300); // Simule le temps de marche entre les rayons
			}
			this.restituerChariot();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
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
	 * Retire dans un rayon donn�, la quantit� de produit figurant sur 
	 * la liste de courses
	 * 
	 * @param rayon Rayon sur lequel sera retir� le produit
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
