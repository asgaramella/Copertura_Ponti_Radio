package it.polito.tdp.radio;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.radio.bean.Citta;
import it.polito.tdp.radio.bean.Model;
import it.polito.tdp.radio.bean.Ponte;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class RadioController {
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Citta> boxFirst;

    @FXML
    private ComboBox<Citta> boxSecond;

    @FXML
    private ComboBox<Citta> boxThird;

    @FXML
    private Button btnPonti;

    @FXML
    private Button btnCopertura;
    
    @FXML
    private TextArea txtResult;

    @FXML
    void doCerca(ActionEvent event) {
    	Citta c1=this.boxFirst.getValue();
    	if(c1==null){
    		txtResult.appendText("ERRORE: Inserire prima città!");
    		return;
    	}
    	Citta c2=this.boxSecond.getValue();
    	if(c2==null){
    		txtResult.appendText("ERRORE: Inserire seconda città!");
    		return;
    	}
    	
    	if(c1.equals(c2)){
    		txtResult.appendText("ERRORE: Inserire due città diverse!");
    		return;
    	}
    	
    	List<Ponte> ponti=model.getPontiForCities(c1, c2);
    	if(ponti.size()==0)
    		txtResult.appendText("Non esistono ponti tra le due città selezionate !\n");
    	else{
    		txtResult.appendText("Ponti che collegano le due città :\n");
    		for(Ponte p:ponti){
    			txtResult.appendText(p.toString()+"\n");
    		}
    	}

    }

    @FXML
    void doCoperturaOttima(ActionEvent event) {
    	
    	Citta c1=this.boxFirst.getValue();
    	if(c1==null){
    		txtResult.appendText("ERRORE: Inserire prima città!");
    		return;
    	}
    	Citta c2=this.boxSecond.getValue();
    	if(c2==null){
    		txtResult.appendText("ERRORE: Inserire seconda città!");
    		return;
    	}
    	Citta c3=this.boxThird.getValue();
    	if(c3==null){
    		txtResult.appendText("ERRORE: Inserire seconda città!");
    		return;
    	}
    	 txtResult.appendText(model.getOttimo(c1, c2, c3).toString());
    	

    }

    @FXML
    void initialize() {
        assert boxFirst != null : "fx:id=\"boxFirst\" was not injected: check your FXML file 'Radio.fxml'.";
        assert boxSecond != null : "fx:id=\"boxSecond\" was not injected: check your FXML file 'Radio.fxml'.";
        assert boxThird != null : "fx:id=\"boxThird\" was not injected: check your FXML file 'Radio.fxml'.";
        assert btnPonti != null : "fx:id=\"btnPonti\" was not injected: check your FXML file 'Radio.fxml'.";
        assert btnCopertura != null : "fx:id=\"btnCopertura\" was not injected: check your FXML file 'Radio.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Radio.fxml'.";
    }

	public void setModel(Model model) {
	 this.model=model;	
	 this.boxFirst.getItems().clear();
	 this.boxSecond.getItems().clear();
	 this.boxThird.getItems().clear();
	 this.boxFirst.getItems().addAll(model.getAllCities());
	 this.boxSecond.getItems().addAll(model.getAllCities());
	 this.boxThird.getItems().addAll(model.getAllCities());
	 


		
	}
}
