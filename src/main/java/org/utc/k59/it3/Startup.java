/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.utc.k59.it3;

import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 *
 * @author JewCat
 */
public class Startup {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("user.timezone", "Asia/Ho_Chi_Minh");
        Logger.getGlobal().info(LocalDateTime.now().toString());

        // DbSeeder.seedAll();
    }
    
}
