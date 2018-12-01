package com.soteria.logic.validation;

import com.soteria.logic.connectivity.ConnectionClass;
import com.soteria.logic.decoration.MyGraphicValidationDecoration;
import javafx.scene.control.Control;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static ValidationSupport validationSupport;
    private static PreparedStatement ps=null;
    private static ResultSet rs=null;
    private static Connection conn= null;

//TODO encrypt passwords and save encrypted passwords in database

    public static boolean isTextFieldNotEmpty(TextField textField){
        boolean b=false;
        if(textField.getText().length()!=0|| !textField.getText().isEmpty())
            b=true;
        return b;
    }
    public static boolean isPasswordFieldNotEmpty(PasswordField passwordField){
        boolean b=false;
        if(passwordField.getText().length()!=0|| !passwordField.getText().isEmpty())
            b=true;
        return b;
    }
    public static boolean passwordvalidation(PasswordField passwordField){
        String regex= "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$!%^&*])(?=.*[a-zA-Z]).{8,15}$";
        Pattern pattern= Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher=pattern.matcher(passwordField.getText());
        //if(matcher.find()&& matcher.group().equals(password.getText()))
        if(matcher.matches()){
            return true;
        }
        return false;
    }
    public static boolean usernamevalidation(TextField textField) throws SQLException {
        conn=ConnectionClass.getConnection();
        try{
            String username= textField.getText();
            if(username.length()<=6){
                System.out.println("name is too short");
                return false;
            }
            String query="SELECT * FROM login WHERE user_name=?";
            ps=conn.prepareStatement(query);
            ps.setString(1,username);
            rs=ps.executeQuery();
            if(!rs.next()){
                System.out.println("name is available");
                return true;
            }else{
                System.out.println("name is used!");
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(ps!=null){
                ps.close();
            }if(rs!=null){
                rs.close();
            }
        }return false;
    }
    public static boolean checkSignup(TextField nickname,TextField username,PasswordField password,PasswordField confirm,TextField email) throws SQLException {
        boolean b= false;
        if(isTextFieldNotEmpty(nickname)&&isTextFieldNotEmpty(username)&&isPasswordFieldNotEmpty(password)&&isPasswordFieldNotEmpty(confirm)&&isTextFieldNotEmpty(email)&&passmatch(password,confirm)){
            if(usernamevalidation(username)&&passwordvalidation(password)&&mailvalidation(email)){
                b=true;
            }
        }
        return b;
    }
    public static boolean checkLogin(TextField textField, PasswordField passwordField) throws SQLException {
        boolean a=false;
        conn= ConnectionClass.getConnection();

        try{
            String username= textField.getText();
            String password= passwordField.getText();
            String query="SELECT * FROM login WHERE user_name=? and password=?";
            ps=conn.prepareStatement(query);
            ps.setString(1,username);
            ps.setString(2,password);
            rs=ps.executeQuery();
            if(rs.next()){
                a=true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            if(ps!=null){
                ps.close();}
            if(rs!=null){
                rs.close();
            }
        }return a;
    }
    public static void insert(TextField nickname,TextField username, PasswordField passwordField, PasswordField confirm, TextField email){
        String query="INSERT INTO login(name,password,confirm,user_name,email)VALUES(?,?,?,?,?)";
        try{
            String nick= nickname.getText();
            String user=username.getText();
            String pass= passwordField.getText();
            String conf=confirm.getText();
            String mail=email.getText();
            conn=ConnectionClass.getConnection();
            ps=conn.prepareStatement(query);
            ps.setString(1, nick);
            ps.setString(2,pass);
            ps.setString(3,conf);
            ps.setString(4,user);
            ps.setString(5,mail);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        //ps=conn.prepareStatement()
    }

    public static boolean mailvalidation(TextField textField){
        String regex="[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
        Pattern pattern= Pattern.compile(regex,Pattern.MULTILINE);
        Matcher matcher= pattern.matcher(textField.getText());
        if(matcher.matches()){
            System.out.println("Email is valid");
            return true;
        }
        System.out.println("Email is invalid");
        return false;
    }
    public static boolean passmatch(PasswordField passwordField, PasswordField confirmField){
        boolean p=false;
        String password=passwordField.getText();
        String confirmPassword=confirmField.getText();
        if(confirmPassword.length()!= 0 || password.length()!=0){
        if(password.equals(confirmPassword)){
            p=true;
        }}
        return p;
    }

    //unused method for now, till finding a way to implement it correctly
    public static void validation(PasswordField passwordField, PasswordField confirmField) {
        validationSupport = new ValidationSupport();
        validationSupport.setValidationDecorator(new MyGraphicValidationDecoration());
        boolean password = validationSupport.registerValidator(passwordField, false, (Control control, String content) -> checkMatch(control,passwordField,confirmField));
        boolean confirm = validationSupport.registerValidator(confirmField, false, (Control control, String content) -> checkMatch(control,passwordField,confirmField));
    }
    private static ValidationResult checkMatch(Control control, PasswordField passwordField, PasswordField confirmField) {
        String content = "Passwords don't match";
        return ValidationResult.fromMessageIf(control, content, Severity.ERROR, !passwordField.getText().equals(confirmField.getText()));
    }
}
