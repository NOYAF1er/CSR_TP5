import java.util.List;

/**
 * Classe Chef de rayon
 * 
 * Permet au chef de rayon de faire sa ronde en approvisionnant les rayons
 * et en se réapprovisionnant à l'entrepot
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 *
 */
public class ChefDeRayon extends Thread  {
	
	/** Liste des rayons du supermarché */
	List<Rayon> listeRayons;
	
	/** Nombre maximal d'exemplaire de produit possible de transporter */
	private final int QTE_MAX_EXEMPLAIRE_TRANSPORTABLE = 5;
	
	/**
	 * Constructeur
	 * 
	 * Définit ce thread comme étant un deamon thread afin de ne l'arreter que lorsque 
	 * tous les thread autre que les deamons threads sont arrêtés
	 * 
	 * Aussi définit
	 * la liste de rayon à approvisionner,
	 * 
	 * @param listeRayons
	 */
	public ChefDeRayon(List<Rayon> listeRayons){
		this.setDaemon(true);
		this.listeRayons = listeRayons;
	}
	
	/**
	 * Déroulement du thread
	 * Faire la ronde des rayons
	 */
	public void run(){
		try {
			this.parcourir();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Approvisionne le rayon définie, si nécessaire, dans les limites des exemplaires 
	 * en possession du chef de rayons et de la capacité du rayon
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void approvisionner(Rayon rayon) throws InterruptedException {
		Thread.sleep(200); // Simule le temps de marche entre les rayons et entre l'entrepot et le 1er rayon
		
		int stockDispoRayon = rayon.getStockDisponible();
		int besoin = rayon.getCapacite() - stockDispoRayon;
		
		if(besoin > 0){
			if(besoin > QTE_MAX_EXEMPLAIRE_TRANSPORTABLE){
				rayon.setStockDisponible(stockDispoRayon + QTE_MAX_EXEMPLAIRE_TRANSPORTABLE);
			}
			else{
				rayon.setStockDisponible(stockDispoRayon + besoin);
			}
			this.notifyAll();
		}
		System.out.println("Chef de rayon: Etat du rayon " + rayon.getProduit() + " après mon passage: " + rayon.getStockDisponible());
	}
	
	/**
	 * Parcour l'ensemble des rayons afin de les approvisionner
	 * une fois au dernier rayons, il se rend à l'entrepot pour se réapprovisionner avant de reprendre sa ronde
	 * 
	 * @throws InterruptedException
	 */
	public void parcourir() throws InterruptedException{
		for(int i = 0, t = listeRayons.size(); i < t; i = (i+1) % t) {
			approvisionner(listeRayons.get(i));
			if(i == t){
				Thread.sleep(500);//Simule le reapprovisionnement à l'entrepot
			}
		}
	}
	
}
