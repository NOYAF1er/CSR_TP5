import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe principale de l'application
 * Elle instanciera l'ensemble des sources et des threads
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 *
 */
public class Supermarche {
	
	static final int RAYON_STOCK_INIT = 5;
	static final int RAYON_STOCK_MAX = 10;
	static final int TAILLE_TAPIS = 5;
	static final int NB_CHARIOTS = 6;
	static final int TPS_CR_PLEIN = 500;
	static final int TPS_CR_DEPLACEMENT = 200;
	static final int TPS_MARCHE_CLT = 300;
	static final int TPS_PLACEMENT_CLT = 20;
	
	public static void main(String[] args) {
		FileDeChariot fileDeChariot = new FileDeChariot(NB_CHARIOTS);
		
		Rayon rayonSucre = new Rayon("Sucre", RAYON_STOCK_INIT);
		Rayon rayonFarine = new Rayon("Farine", RAYON_STOCK_INIT);
		Rayon rayonBeurre = new Rayon("Beurre", RAYON_STOCK_INIT);
		Rayon rayonLait = new Rayon("Lait", RAYON_STOCK_INIT);
		ArrayList<Rayon> listeRayons = new ArrayList<>();
		
		listeRayons.add(rayonLait);
		listeRayons.add(rayonBeurre);
		listeRayons.add(rayonFarine);
		listeRayons.add(rayonSucre);
		
		ChefDeRayon chefDeRayon = new ChefDeRayon(listeRayons, RAYON_STOCK_MAX);
		chefDeRayon.start();
		
		Map<String, Integer> listeCourses1 = new HashMap<>();
		Map<String, Integer> listeCourses2 = new HashMap<>();
		Map<String, Integer> listeCourses3 = new HashMap<>();
		Map<String, Integer> listeCourses4 = new HashMap<>();
		Map<String, Integer> listeCourses5 = new HashMap<>();
		
		listeCourses1.put("Sucre", 4);
		listeCourses1.put("Farine", 2);
		listeCourses1.put("Beurre", 3);
		listeCourses1.put("Lait", 1);
		
		listeCourses2.put("Sucre", 3);
		listeCourses2.put("Farine", 3);
		listeCourses2.put("Beurre", 3);
		listeCourses2.put("Lait", 3);
		
		listeCourses3.put("Sucre", 0);
		listeCourses3.put("Farine", 0);
		listeCourses3.put("Beurre", 0);
		listeCourses3.put("Lait", 2);
		
		listeCourses4.put("Sucre", 5);
		listeCourses4.put("Farine", 5);
		listeCourses4.put("Beurre", 5);
		listeCourses4.put("Lait", 5);

		listeCourses5.put("Sucre", 5);
		listeCourses5.put("Farine", 1);
		listeCourses5.put("Beurre", 1);
		listeCourses5.put("Lait", 1);
				
		Client client1 = new Client(listeCourses1, fileDeChariot, listeRayons);
		Client client2 = new Client(listeCourses2, fileDeChariot, listeRayons);
		Client client3 = new Client(listeCourses3, fileDeChariot, listeRayons);
		Client client4 = new Client(listeCourses4, fileDeChariot, listeRayons);
		Client client5 = new Client(listeCourses5, fileDeChariot, listeRayons);
		
		client1.start();
		client2.start();
		client3.start();
		client4.start();
		client5.start();

	}

}
