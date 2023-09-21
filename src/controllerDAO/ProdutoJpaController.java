/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllerDAO;

import controllerDAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Itemcardapio;
import model.Produto;

/**
 *
 * @author helio
 */
public class ProdutoJpaController implements Serializable {

    public ProdutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Produto produto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Itemcardapio fkItemCardapio = produto.getFkItemCardapio();
            if (fkItemCardapio != null) {
                fkItemCardapio = em.getReference(fkItemCardapio.getClass(), fkItemCardapio.getIdItemCardapio());
                produto.setFkItemCardapio(fkItemCardapio);
            }
            em.persist(produto);
            if (fkItemCardapio != null) {
                fkItemCardapio.getProdutoCollection().add(produto);
                fkItemCardapio = em.merge(fkItemCardapio);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produto produto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto persistentProduto = em.find(Produto.class, produto.getIdProduto());
            Itemcardapio fkItemCardapioOld = persistentProduto.getFkItemCardapio();
            Itemcardapio fkItemCardapioNew = produto.getFkItemCardapio();
            if (fkItemCardapioNew != null) {
                fkItemCardapioNew = em.getReference(fkItemCardapioNew.getClass(), fkItemCardapioNew.getIdItemCardapio());
                produto.setFkItemCardapio(fkItemCardapioNew);
            }
            produto = em.merge(produto);
            if (fkItemCardapioOld != null && !fkItemCardapioOld.equals(fkItemCardapioNew)) {
                fkItemCardapioOld.getProdutoCollection().remove(produto);
                fkItemCardapioOld = em.merge(fkItemCardapioOld);
            }
            if (fkItemCardapioNew != null && !fkItemCardapioNew.equals(fkItemCardapioOld)) {
                fkItemCardapioNew.getProdutoCollection().add(produto);
                fkItemCardapioNew = em.merge(fkItemCardapioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = produto.getIdProduto();
                if (findProduto(id) == null) {
                    throw new NonexistentEntityException("The produto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto produto;
            try {
                produto = em.getReference(Produto.class, id);
                produto.getIdProduto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produto with id " + id + " no longer exists.", enfe);
            }
            Itemcardapio fkItemCardapio = produto.getFkItemCardapio();
            if (fkItemCardapio != null) {
                fkItemCardapio.getProdutoCollection().remove(produto);
                fkItemCardapio = em.merge(fkItemCardapio);
            }
            em.remove(produto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Produto> findProdutoEntities() {
        return findProdutoEntities(true, -1, -1);
    }

    public List<Produto> findProdutoEntities(int maxResults, int firstResult) {
        return findProdutoEntities(false, maxResults, firstResult);
    }

    private List<Produto> findProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Produto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Produto findProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Produto> rt = cq.from(Produto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
