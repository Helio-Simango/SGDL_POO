/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testes;

import controllerDAO.CategoriaprodutoJpaController;
import java.util.List;
import javax.swing.JOptionPane;
import model.Categoriaproduto;

/**
 *
 * @author helio
 */
public class teste {
    public static void main(String[] args) {
        
        CategoriaprodutoJpaController categoriaProdutoDAO = new
                        CategoriaprodutoJpaController(connection.ConnectionFactory.getEmf());
        
        // Instancia da clsse categoria
        Categoriaproduto categoria = new Categoriaproduto();
        
        categoria.setCategoria("Comidas ");
        categoria.setDescricao("Podem conter bebidas jkskdksndjshdbhbsdsdsd !!");
        try {
           // categoriaProdutoDAO.create(categoria);
            JOptionPane.showMessageDialog(null,"Categoria Salva com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao Salvar a Categoria"+ e.getMessage());
        }
        
        
        
        
        // Select 
        List<Categoriaproduto> categoriaProduto =  categoriaProdutoDAO.findCategoriaprodutoEntities();
        
        for(Categoriaproduto cat: categoriaProduto){
            System.out.println("Nome Da Categoria=  "+cat.getCategoria() + "  Decriao  ="+ cat.getDescricao());
        }
    }
}
