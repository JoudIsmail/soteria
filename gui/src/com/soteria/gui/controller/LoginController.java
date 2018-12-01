package com.soteria.gui.controller;

import com.soteria.logic.connectivity.ConnectionClass;
import com.soteria.logic.customization.Customize;
import com.soteria.logic.validation.Validation;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {
    private double x=0;
    private double y=0;
    @FXML
    private StackPane rootpane;
    @FXML
    private VBox rootbox;
    @FXML
    private TextField txtuser;
    @FXML
    private PasswordField txtpass;
    @FXML
    private Label errorLabel;
    @FXML
    public void handleButtonClose(){//close the screen
        System.exit(0);
    }
    @FXML
    public void handleButtonMini(Event e){ //minimizing the screen
        ((Stage)((Button)e.getSource()).getScene().getWindow()).setIconified(true);
    }
    @FXML
    public void handleButtonLogin() throws SQLException {//Button to login
        /*PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=ConnectionClass.getConnection();
        try{
            String username= txtuser.getText();
            String password= txtpass.getText();
            String query="SELECT * FROM login WHERE user_name=? and password=?";
            ps=conn.prepareStatement(query);
            ps.setString(1,username);
            ps.setString(2,password);
            rs=ps.executeQuery();
            if(rs.next()){
                //MyAlert myAlert= new MyAlert();
                //myAlert.errorAlert("OK",rootpane,"Check your Internet Connection",rootbox);
                Alert alertinfo= new Alert(Alert.AlertType.INFORMATION);
                alertinfo.setHeaderText(null);
                alertinfo.setContentText("Connected");
                alertinfo.showAndWait();
            }else{

                txtuser.getStyleClass().add("wrong-credentials");
                txtpass.getStyleClass().add("wrong-credentials");
                errorLabel.setText("Invalid username or password");
                errorLabel.getStyleClass().add(".wrong-pass-label");

            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            if(ps!=null){
                ps.close();}
            if(rs!=null){
                rs.close();
            }
        }*/
        if(Validation.checkLogin(txtuser,txtpass)){
            System.out.println("Connected!");
           // loadWindow("/com/soteria/gui/fxml/main.fxml");
        }else{
            txtuser.getStyleClass().add("wrong-credentials");
            txtpass.getStyleClass().add("wrong-credentials");
            errorLabel.setText("Invalid username or password");
            errorLabel.getStyleClass().add(".wrong-pass-label");
        }
    }
    @FXML
    public void dragged(MouseEvent event) {
        Customize.onDrag(event);
    }
    @FXML
    public void pressed(MouseEvent event) {
        Customize.onPress(event);
    }
    @FXML
    public void handleLinkCreate(Event e){
        closeStage(e);
        loadWindow("/com/soteria/gui/fxml/signup.fxml");
    }
    public void closeStage(Event event){
        Customize.closeStage(event);
    }
    public void loadWindow(String loc){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage= new Stage(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            //TODO need to create mainwindow to locate
             Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE,null,e);
            System.out.println("something went wrong!!");
            e.printStackTrace();
        }
    }

    public static void checkConnection(){
        if(ConnectionClass.isConnected()){
            System.out.println("Connected");
        }else{
            System.out.println("Not Connected");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionClass.getConnection();
        checkConnection();
    }
}
