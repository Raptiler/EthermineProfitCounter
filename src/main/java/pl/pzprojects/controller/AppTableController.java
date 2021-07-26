package pl.pzprojects.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import pl.pzprojects.dto.CurrentStatsDto;
import pl.pzprojects.dto.CurrentStatsModuleDto;
import pl.pzprojects.dto.PayoutsEthermineDataDto;
import pl.pzprojects.dto.PayoutsEthermineDto;
import pl.pzprojects.factory.TextFieldsFactory;
import pl.pzprojects.rest.CurrentStatsRestController;
import pl.pzprojects.rest.PayoutsRestController;
import pl.pzprojects.table.EtherumTableModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AppTableController implements Initializable {

    @FXML
    private BorderPane appTableBorderPane;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField ethPriceTextField;

    @FXML
    private Button calculateButton;

    @FXML
    private TableView<EtherumTableModel> appTableView;

    @FXML
    private Label totalEthLabel;

    @FXML
    private Label totalUsdLabel;

    private ObservableList<EtherumTableModel> data;
    private Double ethPrice = 0.0;
    private String address;
    private CurrentStatsDto currentStatsDto;
    private PayoutsEthermineDto dto;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addressTextField.setText(TextFieldsFactory.firstTextFieldValue);
        initializeTableView();
        initializeCalculateButton();
    }

    private void initializeCalculateButton() {
        calculateButton.setOnAction(x->{
            setLabels(0.0);
            Thread thread = new Thread(()->{
                if(address.equals(addressTextField.getText())){
                    data.clear();
                    loadChangedPriceTableView();
                }
                else
                {
                    data.clear();
                    loadPayoutsTableViewData();
                }
                appTableView.setItems(data);
            });
            thread.start();
        });
    }

    public AppTableController() {
        data = FXCollections.observableArrayList();
    }


    public void setControllersValues(String address){
        addressTextField.setText(address);
    }

    private TableColumn addColumnsDouble(String title,String name, int size){
        TableColumn column = new TableColumn(title);
        column.setMinWidth(size);
        column.setCellValueFactory(new PropertyValueFactory<EtherumTableModel,Double>(name));
        return column;
    }

    private TableColumn addColumnsString(String title,String name, int size){
        TableColumn column = new TableColumn(title);
        column.setMinWidth(size);
        column.setCellValueFactory(new PropertyValueFactory<EtherumTableModel,String>(name));
        return column;
    }

    private void initializeTableView(){
        appTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn amount = addColumnsDouble("Amount in ETH","amount",100);
        TableColumn paidOn = addColumnsString("Withdraw Date","paidOn",100);
        TableColumn amountInDolars = addColumnsDouble("Amount in dollars","amountInDolars",90);
        appTableView.getColumns().addAll(paidOn, amount, amountInDolars);
        loadPayoutsTableViewData();
        appTableView.setItems(data);
        appTableView.setStyle("-fx-alignment: center;");
    }
    //System.out.println(dto.getData().stream().mapToDouble(x->x.getAmount()).sum());
    //System.out.println(dto.getData().stream().map(EtherumTableModel::of).collect(Collectors.toList()));
    //System.out.println(dto.getData());

    //dto.getData().forEach(i -> { System.out.println(i.getAmount()); });
    private void setLabels(Double eth)
    {
        eth = Math.round(eth / 1000000000000000000L * 1000000.00)/1000000.00;
        totalEthLabel.setText("Total ETH: "+eth.toString() + " ETH");
        Double price = Math.round(eth*ethPrice * 100.00)/100.00;
        totalUsdLabel.setText("Total USD: "+ price.toString() + " $");
    }
    private void loadPayoutsTableViewData()
    {
            Thread thread = new Thread(() -> {
                PayoutsRestController payoutsRestController = new PayoutsRestController(addressTextField.getText());
                try {
                    dto = payoutsRestController.getPayouts();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dto.getData().forEach(x -> System.out.println(x.getAmount()));
                CurrentStatsRestController currentStatsRestController = new CurrentStatsRestController(addressTextField.getText());
                currentStatsDto = currentStatsRestController.getCurrentStats();
                if(!dto.getData().isEmpty() && !currentStatsDto.getData().equals("NO DATA")) {
                    CurrentStatsModuleDto currentStatsModuleDto = currentStatsDto.getData();
                    ethPrice = currentStatsModuleDto.getUsdPerMin() / currentStatsModuleDto.getCoinsPerMin();
                    ethPriceTextField.setText(ethPrice.toString());
                    address = addressTextField.getText();
                    dto.getData().add(PayoutsEthermineDataDto.of(currentStatsModuleDto));
                    data.addAll(dto.getData().stream().map(x -> EtherumTableModel.of(x, ethPrice)).collect(Collectors.toList()));
                    double sum = dto.getData().stream().mapToDouble(x -> x.getAmount()).sum();
                    Platform.runLater(()->{
                        setLabels(sum);
                        appTableView.setItems(data);
                    });
                }
            });
            thread.start();
    }
    private void loadChangedPriceTableView()
    {
        Thread thread = new Thread(() -> {
            ethPrice = Double.valueOf(ethPriceTextField.getText());
            ethPriceTextField.setText(String.valueOf(Math.round(ethPrice*100.00)/100.00));
            address = addressTextField.getText();
            data.addAll(dto.getData().stream().map(x -> EtherumTableModel.of(x,ethPrice)).collect(Collectors.toList()));
            double sum = dto.getData().stream().mapToDouble(x -> x.getAmount()).sum();
            Platform.runLater(()->{
                setLabels(sum);
                appTableView.setItems(data);
            });
        });
        thread.start();
    }
}
