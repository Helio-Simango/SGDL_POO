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
import model.Empregado;
import model.TelefoneFuncionario;

/**
 *
 * @author helio
 */
public class TelefoneFuncionarioJpaController implements Serializable {

    public TelefoneFuncionarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TelefoneFuncionario telefoneFuncionario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empregado fkEmpregado = telefoneFuncionario.getFkEmpregado();
            if (fkEmpregado != null) {
                fkEmpregado = em.getReference(fkEmpregado.getClass(), fkEmpregado.getIdEmpregado());
                telefoneFuncionario.setFkEmpregado(fkEmpregado);
            }
            em.persist(telefoneFuncionario);
            if (fkEmpregado != null) {
                fkEmpregado.getTelefoneFuncionarioCollection().add(telefoneFuncionario);
                fkEmpregado = em.merge(fkEmpregado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TelefoneFuncionario telefoneFuncionario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TelefoneFuncionario persistentTelefoneFuncionario = em.find(TelefoneFuncionario.class, telefoneFuncionario.getIdTelefoneCli());
            Empregado fkEmpregadoOld = persistentTelefoneFuncionario.getFkEmpregado();
            Empregado fkEmpregadoNew = telefoneFuncionario.getFkEmpregado();
            if (fkEmpregadoNew != null) {
                fkEmpregadoNew = em.getReference(fkEmpregadoNew.getClass(), fkEmpregadoNew.getIdEmpregado());
                telefoneFuncionario.setFkEmpregado(fkEmpregadoNew);
            }
            telefoneFuncionario = em.merge(telefoneFuncionario);
            if (fkEmpregadoOld != null && !fkEmpregadoOld.equals(fkEmpregadoNew)) {
                fkEmpregadoOld.getTelefoneFuncionarioCollection().remove(telefoneFuncionario);
                fkEmpregadoOld = em.merge(fkEmpregadoOld);
            }
            if (fkEmpregadoNew != null && !fkEmpregadoNew.equals(fkEmpregadoOld)) {
                fkEmpregadoNew.getTelefoneFuncionarioCollection().add(telefoneFuncionario);
                fkEmpregadoNew = em.merge(fkEmpregadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telefoneFuncionario.getIdTelefoneCli();
                if (findTelefoneFuncionario(id) == null) {
                    throw new NonexistentEntityException("The telefoneFuncionario with id " + id + " no longer exists.");
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
            TelefoneFuncionario telefoneFuncionario;
            try {
                telefoneFuncionario = em.getReference(TelefoneFuncionario.class, id);
                telefoneFuncionario.getIdTelefoneCli();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telefoneFuncionario with id " + id + " no longer exists.", enfe);
            }
            Empregado fkEmpregado = telefoneFuncionario.getFkEmpregado();
            if (fkEmpregado != null) {
                fkEmpregado.getTelefoneFuncionarioCollection().remove(telefoneFuncionario);
                fkEmpregado = em.merge(fkEmpregado);
            }
            em.remove(telefoneFuncionario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TelefoneFuncionario> findTelefoneFuncionarioEntities() {
        return findTelefoneFuncionarioEntities(true, -1, -1);
    }

    public List<TelefoneFuncionario> findTelefoneFuncionarioEntities(int maxResults, int firstResult) {
        return findTelefoneFuncionarioEntities(false, maxResults, firstResult);
    }

    private List<TelefoneFuncionario> findTelefoneFuncionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TelefoneFuncionario.class));
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

    public TelefoneFuncionario findTelefoneFuncionario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TelefoneFuncionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelefoneFuncionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TelefoneFuncionario> rt = cq.from(TelefoneFuncionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
