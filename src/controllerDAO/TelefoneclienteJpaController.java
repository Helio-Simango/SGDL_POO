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
import model.Cliente;
import model.Telefonecliente;

/**
 *
 * @author helio
 */
public class TelefoneclienteJpaController implements Serializable {

    public TelefoneclienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Telefonecliente telefonecliente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente fkCliente = telefonecliente.getFkCliente();
            if (fkCliente != null) {
                fkCliente = em.getReference(fkCliente.getClass(), fkCliente.getIdCliente());
                telefonecliente.setFkCliente(fkCliente);
            }
            em.persist(telefonecliente);
            if (fkCliente != null) {
                fkCliente.getTelefoneclienteCollection().add(telefonecliente);
                fkCliente = em.merge(fkCliente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Telefonecliente telefonecliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telefonecliente persistentTelefonecliente = em.find(Telefonecliente.class, telefonecliente.getIdTelefoneCliente());
            Cliente fkClienteOld = persistentTelefonecliente.getFkCliente();
            Cliente fkClienteNew = telefonecliente.getFkCliente();
            if (fkClienteNew != null) {
                fkClienteNew = em.getReference(fkClienteNew.getClass(), fkClienteNew.getIdCliente());
                telefonecliente.setFkCliente(fkClienteNew);
            }
            telefonecliente = em.merge(telefonecliente);
            if (fkClienteOld != null && !fkClienteOld.equals(fkClienteNew)) {
                fkClienteOld.getTelefoneclienteCollection().remove(telefonecliente);
                fkClienteOld = em.merge(fkClienteOld);
            }
            if (fkClienteNew != null && !fkClienteNew.equals(fkClienteOld)) {
                fkClienteNew.getTelefoneclienteCollection().add(telefonecliente);
                fkClienteNew = em.merge(fkClienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telefonecliente.getIdTelefoneCliente();
                if (findTelefonecliente(id) == null) {
                    throw new NonexistentEntityException("The telefonecliente with id " + id + " no longer exists.");
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
            Telefonecliente telefonecliente;
            try {
                telefonecliente = em.getReference(Telefonecliente.class, id);
                telefonecliente.getIdTelefoneCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telefonecliente with id " + id + " no longer exists.", enfe);
            }
            Cliente fkCliente = telefonecliente.getFkCliente();
            if (fkCliente != null) {
                fkCliente.getTelefoneclienteCollection().remove(telefonecliente);
                fkCliente = em.merge(fkCliente);
            }
            em.remove(telefonecliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Telefonecliente> findTelefoneclienteEntities() {
        return findTelefoneclienteEntities(true, -1, -1);
    }

    public List<Telefonecliente> findTelefoneclienteEntities(int maxResults, int firstResult) {
        return findTelefoneclienteEntities(false, maxResults, firstResult);
    }

    private List<Telefonecliente> findTelefoneclienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Telefonecliente.class));
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

    public Telefonecliente findTelefonecliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Telefonecliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelefoneclienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Telefonecliente> rt = cq.from(Telefonecliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
