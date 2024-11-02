package myWordlePackage;

import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import myWordlePackage.Connect;
import myWordlePackage.Connect;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ntsak
 */
public class WordleLogic {
    
    Connect db = new Connect();
    
    private String password;
    private String[]  wordList = new String[50];
    
    
    public String Login( String uN, String uP)
    {
               
        String sql = "SELECT Password FROM tbluserInfo WHERE Username = '" + uN + "'";
        String Message = "";
    try {
        ResultSet rs = db.query(sql);  
        
        
        if (rs.next()) {
            password = rs.getString("Password");
            if(password.equalsIgnoreCase(uP)) 
            {
                Message = "Correct";
            }else{
                Message = "Incorrect";
            } 
            
        } else {
            Message = "UserNotFound";
        }            
        
    } catch (SQLException ex) {
        Logger.getLogger(WordleLogic.class.getName()).log(Level.SEVERE, null, ex);
    }
       return Message; 
    }
    
    public void Register(String uN, String uP)
    {
        String sql = "INSERT INTO tblUserInfo (Username, Password) \nVALUES('" + uN +"', '"+uP +"');";
        int rowsAffected = db.update(sql);
        System.out.println(rowsAffected + "\n"+sql);
        
    }
    public boolean contains3Numbers(String input)
    {
        int numDigits = 0;
        for(int i= 0; i<input.length(); i++)
        {
            char userLetter = input.charAt(i);
            if(Character.isDigit(userLetter))
            {
               numDigits++;
            }
        }
        if(numDigits>=3)
        {
            return true;
        }
       
        return false;
    }
    
    public boolean containsNumberandSpecial(String uP)
    {
        int numDigit2 = 0;
        int numSpecial = 0;
        for(int i = 0; i<uP.length(); i++)
        {
            char passLetter = uP.charAt(i);
            if(Character.isDigit(passLetter))
            {
                numDigit2++;
            }else if(!Character.isLetterOrDigit(passLetter))
            {
                numSpecial++;
            }
        }
        
        if(numSpecial>0 && numDigit2>0)
        {
            return true;
        }
        
        return false;
    }
    
    
    public String readWord()
    {
        try{
            Scanner scFile = new Scanner(new File("WordleWordList.txt"));
            
            int wordCount = 0;
            while(scFile.hasNext())
            {
                wordList[wordCount] =scFile.nextLine();
                wordCount++;
            }
            
        }catch(FileNotFoundException ex)
        {
            System.out.println("File not found");
        }
        
        String randomWord = wordList[(int)( 0 + Math.random()*wordList.length)];
        return randomWord;
    }
    
    public void inputScore(String uN, int Sc){
            
            String sqlScoreNUser = "INSERT INTO tblHighscore (Username, Score) \nVALUES('" + uN +"', "+Sc +");";
            int rowsAffected = db.update(sqlScoreNUser);
          
    }
    
    
    
    
    
    
    
     
    
    
    
    
    
    
    public static void main(String[] args) {
        new WordleLogic();
        
        
    }
}
