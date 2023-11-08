/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllerDAO;

import controllerDAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Empregado;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Funcaoempregado;

/**
 *
 * @author helio
 */
public class FuncaoempregadoJpaController implements Serializable {

    public FuncaoempregadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcaoempregado funcaoempregado) {
        if (funcaoempregado.getEmpregadoCollection() == null) {
            funcaoempregado.setEmpregadoCollection(new ArrayList<Empregado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Empregado> attachedEmpregadoCollection = new ArrayList<Empregado>();
            for (Empregado empregadoCollectionEmpregadoToAttach : funcaoempregado.getEmpregadoCollection()) {
                empregadoCollectionEmpregadoToAttach = em.getReference(empregadoCollectionEmpregadoToAttach.getClass(), empregadoCollectionEmpregadoToAttach.getIdEmpregado());
                attachedEmpregadoCollection.add(empregadoCollectionEmpregadoToAttach);
            }
            funcaoempregado.setEmpregadoCollection(attachedEmpregadoCollection);
            em.persist(funcaoempregado);
            for (Empregado empregadoCollectionEmpregado : funcaoempregado.getEmpregadoCollection()) {
                Funcaoempregado oldFkFuncaoEmpregadoOfEmpregadoCollectionEmpregado = empregadoCollectionEmpregado.getFkFuncaoEmpregado();
                empregadoCollectionEmpregado.setFkFuncaoEmpregado(funcaoempregado);
                empregadoCollectionEmpregado = em.merge(empregadoCollectionEmpregado);
                if (oldFkFuncaoEmpregadoOfEmpregadoCollectionEmpregado != null) {
                    oldFkFuncaoEmpregadoOfEmpregadoCollectionEmpregado.getEmpregadoCollection().remove(empregadoCollectionEmpregado);
                    oldFkFuncaoEmpregadoOfEmpregadoCollectionEmpregado = em.merge(oldFkFuncaoEmpregadoOfEmpregadoCollectionEmpregado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcaoempregado funcaoempregado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcaoempregado persistentFuncaoempregado = em.find(Funcaoempregado.class, funcaoempregado.getIdFuncao());
            Collection<Empregado> empregadoCollectionOld = persistentFuncaoempregado.getEmpregadoCollection();
            Collection<Empregado> empregadoCollectionNew = funcaoempregado.getEmpregadoCollection();
            Collection<Empregado> attachedEmpregadoCollectionNew = new ArrayList<Empregado>();
            for (Empregado empregadoCollectionNewEmpregadoToAttach : empregadoCollectionNew) {
                empregadoCollectionNewEmpregadoToAttach = em.getReference(empregadoCollectionNewEmpregadoToAttach.getClass(), empregadoCollectionNewEmpregadoToAttach.getIdEmpregado());
                attachedEmpregadoCollectionNew.add(empregadoCollectionNewEmpregadoToAttach);
            }
            empregadoCollectionNew = attachedEmpregadoCollectionNew;
            funcaoempregado.setEmpregadoCollection(empregadoCollectionNew);
            funcaoempregado = em.merge(funcaoempregado);
            for (Empregado empregadoCollectionOldEmpregado : empregadoCollectionOld) {
                if (!empregadoCollectionNew.contains(empregadoCollectionOldEmpregado)) {
                    empregadoCollectionOldEmpregado.setFkFuncaoEmpregado(null);
                    empregadoCollectionOldEmpregado = em.merge(empregadoCollectionOldEmpregado);
                }
            }
            for (Empregado empregadoCollectionNewEmpregado : empregadoCollectionNew) {
                if (!empregadoCollectionOld.contains(empregadoCollectionNewEmpregado)) {
                    Funcaoempregado oldFkFuncaoEmpregadoOfEmpregadoCollectionNewEmpregado = empregadoCollectionNewEmpregado.getFkFuncaoEmpregado();
                    empregadoCollectionNewEmpregado.setFkFuncaoEmpregado(funcaoempregado);
                    empregadoCollectionNewEmpregado = em.merge(empregadoCollectionNewEmpregado);
                    if (oldFkFuncaoEmpregadoOfEmpregadoCollectionNewEmpregado != null && !oldFkFuncaoEmpregadoOfEmpregadoCollectionNewEmpregado.equals(funcaoempregado)) {
                        oldFkFuncaoEmpregadoOfEmpregadoCollectionNewEmpregado.getEmpregadoCollection().remove(empregadoCollectionNewEmpregado);
                        oldFkFuncaoEmpregadoOfEmpregadoCollectionNewEmpregado = em.merge(oldFkFuncaoEmpregadoOfEmpregadoCollectionNewEmpregado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = funcaoempregado.getIdFuncao();
                if (findFuncaoempregado(id) == null) {
                    throw new NonexistentEntityException("The funcaoempregado with id " + id + " no longer exists.");
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
            Funcaoempregado funcaoempregado;
            try {
                funcaoempregado = em.getReference(Funcaoempregado.class, id);
                funcaoempregado.getIdFuncao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcaoempregado with id " + id + " no longer exists.", enfe);
            }
            Collection<Empregado> empregadoCollection = funcaoempregado.getEmpregadoCollection();
            for (Empregado empregadoCollectionEmpregado : empregadoCollection) {
                empregadoCollectionEmpregado.setFkFuncaoEmpregado(null);
                empregadoCollectionEmpregado = em.merge(empregadoCollectionEmpregado);
            }
            em.remove(funcaoempregado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Funcaoempregado> findFuncaoempregadoEntities() {
        return findFuncaoempregadoEntities(true, -1, -1);
    }

    public List<Funcaoempregado> findFuncaoempregadoEntities(int maxResults, int firstResult) {
        return findFuncaoempregadoEntities(false, maxResults, firstResult);
    }

    private List<Funcaoempregado> findFuncaoempregadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcaoempregado.class));
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

    public Funcaoempregado findFuncaoempregado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcaoempregado.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncaoempregadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcaoempregado> rt = cq.from(Funcaoempregado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public void funcionarioEstado(){
        
    }
    
}
