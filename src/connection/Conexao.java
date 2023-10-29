/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author helio
 */
public class Conexao {
    public static  EntityManagerFactory getEmf(){
       return Persistence.createEntityManagerFactory("SGDL_POOPU");
    }
}
