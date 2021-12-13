/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, December 2021
 */
package manydesktop;



/**
 * This class launches the Stream Deck plugin
 * @author JasonPercus
 * @version 1.0
 */
public final class ManyDesktop extends com.jasonpercus.plugincreator.PluginCreator {

    

//OVERRIDED
    /**
     * Returns the author of the plugin [Required]
     * @return Returns the author of the plugin
     */
    @Override
    public String author() {
        return "JasonPercus";
    }

    /**
     * Return a general description of what the plugin does. This string is displayed to the user in the Stream Deck store [Required]
     * @return Return a general description of what the plugin does. This string is displayed to the user in the Stream Deck store
     */
    @Override
    public String description() {
        return "Allows switching between multiple windows desktops";
    }

    /**
     * Return the name of the plugin. This string is displayed to the user in the Stream Deck store [Required]
     * @return Return the name of the plugin. This string is displayed to the user in the Stream Deck store
     */
    @Override
    public String name() {
        return "ManyDesktop";
    }

    /**
     * Return the version of the plugin which can only contain digits and periods. This is used for the software update mechanism [Required]
     * @return Return the version of the plugin which can only contain digits and periods. This is used for the software update mechanism
     */
    @Override
    public String version() {
        return "1.0";
    }
    
    /**
     * Returns the name of the folder where the plugin will be stored [Required]
     * @return Returns the name of the folder where the plugin will be stored
     */
    @Override
    public String folderName() {
        return "com.jasonpercus.manydesktop";
    }
    
    
    
//MAIN
    /**
     * Corresponds to the plugin startup method
     * @param args Corresponds to the arguments provided by Stream Deck
     */
    public static void main(String[] args) {
        //Plugin Init
        com.jasonpercus.plugincreator.PluginCreator.register(args);
    }
    
    
    
}