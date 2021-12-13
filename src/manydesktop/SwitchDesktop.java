/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, December 2021
 */
package manydesktop;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jasonpercus.plugincreator.Action;
import com.jasonpercus.plugincreator.models.Target;
import com.jasonpercus.plugincreator.models.events.KeyUp;
import com.jasonpercus.util.File;



/**
 * This class represents the action that switches to another desktop
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings("SleepWhileInLoop")
public class SwitchDesktop extends Action {
    
    
    
//ATTRIBUTS
    /**
     * Correspond à la position de départ
     */
    private static int position = 0;
    
    /**
     * Correspond au robot Java
     */
    private java.awt.Robot robot;

    
    
//CONSTRUCTOR
    /**
     * Crée une action qui switch les écrans
     */
    public SwitchDesktop() {
        super();
        try {
            this.robot = new java.awt.Robot();
        } catch (java.awt.AWTException ex) {
            getLogger().log(ex);
        }
    }
    
    
    

    
    
//OVERRIDED
    /**
     * Returns the name of the action
     * @return Returns the name of the action
     */
    @Override
    public String name() {
        return "SwitchDesktop";
    }

    /**
     * Returns the tooltip of the action
     * @return Returns the tooltip of the action
     */
    @Override
    public String tooltip() {
        return "Switch to another desk";
    }

    /**
     * Returns the uuid of the action
     * @return Returns the uuid of the action
     */
    @Override
    public String uuid() {
        return "com.jasonpercus.manydesktop.switchdesktop";
    }

    
    
//EVENTS
    /**
     * When the button is up from a Stream Deck action
     * @param actions Corresponds to the list of saved Actions
     * @param event Corresponds to the recovered event
     */
    @Override
    public void keyUp(java.util.HashMap<String, com.jasonpercus.plugincreator.Action> actions, KeyUp event) {
        new Thread(() -> {
            try{
                int number = getNumber(event);
                if(number != position && this.robot != null){
                    int diff = number - position;
                    getLogger().log("diff: "+diff);
                    if(diff > 0){
                        this.robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
                        this.robot.keyPress(java.awt.event.KeyEvent.VK_WINDOWS);
                        for(int i=0;i<Math.abs(diff);i++){
                            this.robot.keyPress(java.awt.event.KeyEvent.VK_RIGHT);
                            Thread.sleep(30);
                            this.robot.keyRelease(java.awt.event.KeyEvent.VK_RIGHT);
                            Thread.sleep(20);
                        }
                        this.robot.keyRelease(java.awt.event.KeyEvent.VK_WINDOWS);
                        this.robot.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
                    }else{
                        this.robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
                        this.robot.keyPress(java.awt.event.KeyEvent.VK_WINDOWS);
                        for(int i=0;i<Math.abs(diff);i++){
                            this.robot.keyPress(java.awt.event.KeyEvent.VK_LEFT);
                            Thread.sleep(30);
                            this.robot.keyRelease(java.awt.event.KeyEvent.VK_LEFT);
                            Thread.sleep(20);
                        }
                        this.robot.keyRelease(java.awt.event.KeyEvent.VK_WINDOWS);
                        this.robot.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
                    }
                    position = number;
                    for(java.util.Map.Entry<String, Action> a : actions.entrySet()){
                        Action action = a.getValue();
                        if(action.uuid().equals(uuid())){
                            action.setImage(new File("images/notSelected.png"), Target.BOTH);
                        }
                    }
                    setImage(new File("images/selected.png"), Target.BOTH);
                }
            }catch(NumberFormatException | NullPointerException | InterruptedException | java.io.FileNotFoundException ex){
                getLogger().log(ex);
            }
        }).start();
    }
    
    
    
//METHODE PRIVATE
    /**
     * Renvoie le nombre envoyé par le Stream Deck
     * @param event Correspond à l'évênement contenant le nombre
     * @return Retourne le nombre envoyé par le Stream Deck
     * @throws NumberFormatException S'il ne s'agit pas d'un nombre
     * @throws NullPointerException Si le contenu envoyé est null
     */
    private int getNumber(KeyUp event) throws NumberFormatException, NullPointerException {
        Gson gson = new GsonBuilder().create();
        Model model = gson.fromJson(event.payload.settings, Model.class);
        if(model != null && model.settingsModel != null && model.settingsModel.NumberValue != null && !model.settingsModel.NumberValue.isEmpty()){
            try{
                return Integer.parseInt(model.settingsModel.NumberValue);
            }catch(NumberFormatException ex){
                throw ex;
            }
        }else{
            throw new NullPointerException("event.payload.settings model is null !");
        }
    }
    
    
    
//CLASS
    /**
     * Cette classe représente un modèle d'évênement reçu
     * @author JasonPercus
     * @version 1.0
     */
    private class Model {
    
        /**
         * Correspond au sous model reçu
         */
        public SettingModel settingsModel;
    
    }
    
    /**
     * Cette classe représente le sous modèle d'évênement reçu
     * @author JasonPercus
     * @version 1.0
     */
    private class SettingModel {
        
        public String NumberValue;
        
    }
    
    
    
}