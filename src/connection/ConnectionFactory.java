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
public class ConnectionFactory {
    
    /** metedo "static" pode ser "chamado" sem a necessidade
     * de instanciar a classe.
     *
     *
     * O metedo returna EntityManagerFactory para poder 
     * Atribuir as Classses controller e depois gravar os dados
    */
    public static  EntityManagerFactory getEmf(){
       return Persistence.createEntityManagerFactory("SGDL_POOPU");
    }
}
