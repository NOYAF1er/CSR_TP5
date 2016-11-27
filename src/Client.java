import java.util.ArrayList;
import java.util.Map;

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
	Map<String, Integer> listeDeCourses;
	
	/** File de chariot du supermarché */
	FileDeChariot fileDeChariot;
	
	/** Liste des rayons du supermarché */
	ArrayList<Rayon> listeRayons;
	
	/**
	 * Constructeur
	 * Définit 
	 * une liste de courses, 
	 * un file de chariot,
	 * une liste de rayon
	 * 
	 * @param listeDeCourses
	 * @param fileDeChariot
	 * @param listeRayons
	 */
	public Client(Map<String, Integer> listeDeCourses, FileDeChariot fileDeChariot, ArrayList<Rayon> listeRayon) {
		this.listeDeCourses = listeDeCourses;
		this.fileDeChariot = fileDeChariot;
		this.listeRayons = listeRayon;
	}

	public void run() {
		try {
			this.emprunterChariot();
			//Parcours des rayons
			for(Rayon rayon: listeRayons){
				prendreProduits(rayon);
				Thread.sleep(300); // Simule le temps de marche entre les rayons
			}
			this.restituerChariot();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
		String produitRayon = rayon.getProduit();
		int qteProduit = listeDeCourses.get(produitRayon);
		System.out.println(Thread.currentThread().getName() + " Liste Courses: " + produitRayon + " = " + qteProduit);
		while(qteProduit > 0){
			System.out.println(Thread.currentThread().getName() + "_Client sur le point de prendre un produit sur le rayon "+ rayon.getProduit());
			rayon.deStocker();
			qteProduit--;
		}	
	}

}
