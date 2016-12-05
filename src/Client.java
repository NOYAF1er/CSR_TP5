import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Classe Client
 * <p>
 * Recup�re un chariot dans une file de chariot d�s que possible puis parcours
 * l'ensemble des rayons afin de recup�rer les produits correspondants � sa liste
 * de courses enfin passe � la caisse afin de faire enregistrer ses produits et
 * effectuer le reglement
 * </p>
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 * 
 *@see Thread
 *
 */
public class Client extends Thread {
	/** 
	 * Liste de courses contenant les produits et leurs quantit�s 
	 * 
	 * @see Client#Client(FileDeChariot, List, Caisse)
	 * @see Client#setListeDeCourses()
	 * @see Client#setListeDeCourses(Map)
	 * @see Client#prendreProduits(Rayon)
	 * @see Client#passerAlaCaisse()
	 */
	Map<Produits, Integer> listeDeCourses;
	
	/** 
	 * File de chariot du supermarch�
	 * 
	 * @see Client#Client(FileDeChariot, List, Caisse)
	 * @see Client#emprunterChariot()
	 * @see Client#restituerChariot()
	 *  
	 */
	FileDeChariot fileDeChariot;
	
	/** 
	 * Liste des rayons du supermarch�
	 * 
	 *  @see Client#Client(FileDeChariot, List, Caisse)
	 *  @see Client#run()
	 */
	List<Rayon> listeRayons;
	
	/** 
	 * Caisse du supermarch� 
	 * 
	 * @see Client#Client(FileDeChariot, List, Caisse)
	 * @see Client#passerAlaCaisse()
	 */
	Caisse caisse;
	
	/**
	 * Constructeur
	 * 
	 * <ul>
	 * Fixe:
	 * 	<li>la liste de course</li>
	 * 	<li>la file de chariot</li>
	 * 	<li>la liste de rayon</li>
	 * 	<li>la caisse</li>
	 * </ul>
	 * 
	 * @param fileDeChariot
	 * @param listeRayons
	 * @param caisse
	 * 
	 * @see FileDeChariot
	 * @see List
	 * @see Rayon
	 * @see Caisse
	 */
	public Client(FileDeChariot fileDeChariot, List<Rayon> listeRayon, Caisse caisse) {
		this.fileDeChariot = fileDeChariot;
		this.listeRayons = listeRayon;
		this.caisse = caisse;
		
		this.listeDeCourses = new HashMap<>();
		this.setListeDeCourses();
	}
	
	/** 
	 * File de d�roulement du thread
	 * <ol>
		 * <li>Emprunte un chariot</li>
		 * <li>Parcours tous les rayons pour recup�rer les produits de sa liste de courses</li>
		 * <li>Passe � la caisse afin de faire enregistrer ses produits et effectuer le r�glement</li>
		 * <li>Restitue le chariot</li>
	 * </ol>
	 */
	public void run() {
		try {
			//Emprunt d'un chariot
			this.emprunterChariot();
			
			//Parcours des rayons
			for(Rayon rayon: listeRayons){
				prendreProduits(rayon);
				Thread.sleep(Supermarche.TPS_MARCHE_CLT); // Simule le temps de marche entre les rayons
			}
			
			//Passage en caisse
			passerAlaCaisse();
			
			//Restitution du chariot
			this.restituerChariot();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * D�finit une liste de courses de fa�on al�atoire
	 * 
	 * @see Client#Client(FileDeChariot, List, Caisse)
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
	 * 
	 * @param listeDeCourses Liste de courses
	 * @see Map
	 * @see Produits
	 * @see Integer
	 */
	public void setListeDeCourses(Map<Produits, Integer> listeDeCourses){
		this.listeDeCourses = listeDeCourses;
	}	

	/**
	 * Emprunte un chariot dans la file de chariot
	 * 
	 * @throws InterruptedException
	 * @see Client#run()
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
	public void prendreProduits(Rayon rayon) throws InterruptedException{
		Produits produitRayon = rayon.getProduit();
		int qteProduit = listeDeCourses.get(produitRayon);
		//System.out.println(Thread.currentThread().getName() + " Liste Courses: " + produitRayon + " = " + qteProduit);
		
		while(qteProduit > 0){
			rayon.deStocker();
			qteProduit--;
		}	
	}
	
	/**
	 * Poser ses produits les uns après les autres sur le tapis de la caisse (dans les limites du tapis)
	 * après avoir vérifié que la caisse soit libre
	 * puis liberer la caisse
	 * @throws InterruptedException
	 */
	public void passerAlaCaisse() throws InterruptedException{
		caisse.clientSuivant(); //Vérifie que la caisse est libre
		for(Produits produit: listeDeCourses.keySet()){
			for(int i=0, qtProduit = listeDeCourses.get(produit); i < qtProduit; i++){
				caisse.charger(produit);
			}
		}
		caisse.reglementClient();
	}

}
