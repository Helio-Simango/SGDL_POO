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
import model.Categoriaproduto;

/**
 *
 * @author helio
 */
public class CategoriaprodutoJpaController implements Serializable {

    public CategoriaprodutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoriaproduto categoriaproduto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(categoriaproduto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoriaproduto categoriaproduto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            categoriaproduto = em.merge(categoriaproduto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoriaproduto.getIdCategoriaProduto();
                if (findCategoriaproduto(id) == null) {
                    throw new NonexistentEntityException("The categoriaproduto with id " + id + " no longer exists.");
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
            Categoriaproduto categoriaproduto;
            try {
                categoriaproduto = em.getReference(Categoriaproduto.class, id);
                categoriaproduto.getIdCategoriaProduto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaproduto with id " + id + " no longer exists.", enfe);
            }
            em.remove(categoriaproduto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoriaproduto> findCategoriaprodutoEntities() {
        return findCategoriaprodutoEntities(true, -1, -1);
    }

    public List<Categoriaproduto> findCategoriaprodutoEntities(int maxResults, int firstResult) {
        return findCategoriaprodutoEntities(false, maxResults, firstResult);
    }

    private List<Categoriaproduto> findCategoriaprodutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoriaproduto.class));
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

    public Categoriaproduto findCategoriaproduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoriaproduto.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaprodutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoriaproduto> rt = cq.from(Categoriaproduto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
