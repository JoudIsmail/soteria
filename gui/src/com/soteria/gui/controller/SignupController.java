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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignupController implements Initializable {
    double x = 0;
    double y = 0;

    public Image image;

    {
        try {
            image = new Image(new File("D:\\soteria\\gui\\src\\com\\soteria\\gui\\style\\icons\\iconfinder_sign-error_299045.png").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private Button btnusimg,btnnicimg,btnpasimg,btnconimg,btnemimg;
    @FXML
    private ImageView nickic, useic, passic, conic, emic;
    @FXML
    private TextField usernametxt, txtmail, txtnick;
    @FXML
    private PasswordField ptxt, confirmtxt;

//TODO passwords need to be encrypted!!


    @FXML
    public void handleButtonClose() {//close the screen
        System.exit(0);
    }

    @FXML
    public void handleButtonMini(Event e) { //minimizing the screen
        ((Stage) ((Button) e.getSource()).getScene().getWindow()).setIconified(true);
    }

    @FXML
    public void handleButtonBack(Event e) {
        closeStage(e);
        loadLoginWindow();
    }

    @FXML
    public void dragged(MouseEvent event) {
        Customize.onDrag(event);
    }

    @FXML
    public void pressed(MouseEvent event) {
        Customize.onPress(event);
    }

    public void closeStage(Event event) {
        Customize.closeStage(event);
    }

    void loadLoginWindow() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/com/soteria/gui/fxml/login.fxml"));
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            //TODO need to create mainwindow to locate
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    @FXML
    public void handleButtonCreate() throws SQLException {
        if(Validation.checkSignup(txtnick,usernametxt,ptxt,confirmtxt,txtmail)){
            Validation.insert(txtnick,usernametxt,ptxt,confirmtxt,txtmail);
            return;
        }else{

        if (!Validation.isPasswordFieldNotEmpty(ptxt)) {
            Customize.showError(image,"Field is required!",passic,btnpasimg);
        }else{
            Customize.restoreError(btnpasimg);
        }


        if (!Validation.isPasswordFieldNotEmpty(confirmtxt)) {
            Customize.showError(image,"Field is required!",conic,btnconimg);
        }else{
            Customize.restoreError(btnconimg);
        }


        if (!Validation.isTextFieldNotEmpty(txtnick)) {
            Customize.showError(image,"Field is required!",nickic,btnnicimg);
        }else{
            Customize.restoreError(btnnicimg);
        }


        if (!Validation.isTextFieldNotEmpty(txtmail)) {
            Customize.showError(image,"Field is required!",emic,btnemimg);
        }else{
            Customize.restoreError(btnemimg);
        }


        if (!Validation.isTextFieldNotEmpty(usernametxt)) {
            Customize.showError(image,"Field is required!",useic,btnusimg);
        }
        else{
            Customize.restoreError(btnusimg);
        }


        if(Validation.isTextFieldNotEmpty(txtmail)){
        if(!Validation.mailvalidation(txtmail)){
            Customize.showError(image,"Invalid Email!",emic,btnemimg);
        }else{
            Customize.restoreError(btnemimg);
        }}}
    }

    @FXML
    public void validateconfirm(KeyEvent e) {
        Validation.validation(ptxt, confirmtxt);
        //e.getCode();
    }

    @FXML
    public void validatepasscon(MouseEvent event) {
        Validation.validation(ptxt, confirmtxt);
        //event.getSource();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionClass.getConnection();
        LoginController.checkConnection();
    }
}
