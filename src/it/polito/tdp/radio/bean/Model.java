package it.polito.tdp.radio.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.radio.db.RadioDAO;

public class Model {
	private List<Citta> citta;
	private Map<Integer,Citta> mapCitta;
	private RadioDAO dao;
	private Set<Ponte> ponti;
	private List<Ponte> best;
	private int min;
	private Citta c1;
	private Citta c2;
	private Citta c3;
	
	public Model() {
		super();
		dao=new RadioDAO();
		
	}
	
	
	public List<Citta> getAllCities(){
		if(citta==null){
			citta=dao.getAllCitta();
			mapCitta=new HashMap<>();
			for(Citta ctemp:citta){
				mapCitta.put(ctemp.getIdCitta(),ctemp);
			}
		}
		
		return citta;
	}
	
	
	public List<Ponte> getPontiForCities(Citta c1,Citta c2){
		List<Ponte> ponti=dao.getAllPontiForCities(c1, c2);
		
		return ponti;
	}
	
	public List<Ponte> getOttimo(Citta c1,Citta c2,Citta c3){
		ponti=new HashSet<>();
		min=Integer.MAX_VALUE;
		this.c1=mapCitta.get(c1.getIdCitta());
		this.c2=mapCitta.get(c2.getIdCitta());
		this.c3=mapCitta.get(c3.getIdCitta());
	    this.c1.getPonti().addAll(dao.getPontiForCitta(c1));
		this.c2.getPonti().addAll(dao.getPontiForCitta(c2));
		this.c3.getPonti().addAll(dao.getPontiForCitta(c3));
		
		ponti.addAll(this.c1.getPonti());
		ponti.addAll(this.c2.getPonti());
		ponti.addAll(this.c3.getPonti());
		List<Ponte> parziale=new ArrayList<Ponte>();
		best=new ArrayList<>();
		
		recursive(0,parziale);
		
		
		return best;
		
	}


	private void recursive(int livello, List<Ponte> parziale) {
		
		if(this.getCoperturaTot(parziale)){
			
			if(parziale.size()<min){
				
				best.clear();
				best.addAll(parziale);
				min=parziale.size();
			}
			
		}
		
		for(Ponte ptemp:ponti){
			
			if(parziale.isEmpty()||ptemp.compareTo(parziale.get(parziale.size()-1))>0){
				parziale.add(ptemp);
				
				recursive(livello+1,parziale);
				
				parziale.remove(ptemp);
			}
			
		}
	}


	private boolean getCoperturaTot(List<Ponte> parziale) {
		boolean coperta1=false;
		boolean coperta2=false;
		boolean coperta3=false;
		
		for(Ponte ptemp:parziale){
			if(c1.getPonti().contains(ptemp))
				coperta1=true;
			
			if(c2.getPonti().contains(ptemp))
				coperta2=true;
			
			if(c3.getPonti().contains(ptemp))
				coperta3=true;
			
		}
		return coperta1 && coperta2 && coperta3;
	}
	
	
	
	
	
	
	
	
	
	

}
