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
import model.Funcaoempregado;
import model.Pedido;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Empregado;
import model.TelefoneFuncionario;

/**
 *
 * @author helio
 */
public class EmpregadoJpaController implements Serializable {

    public EmpregadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empregado empregado) {
        if (empregado.getPedidoCollection() == null) {
            empregado.setPedidoCollection(new ArrayList<Pedido>());
        }
        if (empregado.getTelefoneFuncionarioCollection() == null) {
            empregado.setTelefoneFuncionarioCollection(new ArrayList<TelefoneFuncionario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcaoempregado fkFuncaoEmpregado = empregado.getFkFuncaoEmpregado();
            if (fkFuncaoEmpregado != null) {
                fkFuncaoEmpregado = em.getReference(fkFuncaoEmpregado.getClass(), fkFuncaoEmpregado.getIdFuncao());
                empregado.setFkFuncaoEmpregado(fkFuncaoEmpregado);
            }
            Collection<Pedido> attachedPedidoCollection = new ArrayList<Pedido>();
            for (Pedido pedidoCollectionPedidoToAttach : empregado.getPedidoCollection()) {
                pedidoCollectionPedidoToAttach = em.getReference(pedidoCollectionPedidoToAttach.getClass(), pedidoCollectionPedidoToAttach.getIdPedido());
                attachedPedidoCollection.add(pedidoCollectionPedidoToAttach);
            }
            empregado.setPedidoCollection(attachedPedidoCollection);
            Collection<TelefoneFuncionario> attachedTelefoneFuncionarioCollection = new ArrayList<TelefoneFuncionario>();
            for (TelefoneFuncionario telefoneFuncionarioCollectionTelefoneFuncionarioToAttach : empregado.getTelefoneFuncionarioCollection()) {
                telefoneFuncionarioCollectionTelefoneFuncionarioToAttach = em.getReference(telefoneFuncionarioCollectionTelefoneFuncionarioToAttach.getClass(), telefoneFuncionarioCollectionTelefoneFuncionarioToAttach.getIdTelefoneCli());
                attachedTelefoneFuncionarioCollection.add(telefoneFuncionarioCollectionTelefoneFuncionarioToAttach);
            }
            empregado.setTelefoneFuncionarioCollection(attachedTelefoneFuncionarioCollection);
            em.persist(empregado);
            if (fkFuncaoEmpregado != null) {
                fkFuncaoEmpregado.getEmpregadoCollection().add(empregado);
                fkFuncaoEmpregado = em.merge(fkFuncaoEmpregado);
            }
            for (Pedido pedidoCollectionPedido : empregado.getPedidoCollection()) {
                Empregado oldFkEmpregadoOfPedidoCollectionPedido = pedidoCollectionPedido.getFkEmpregado();
                pedidoCollectionPedido.setFkEmpregado(empregado);
                pedidoCollectionPedido = em.merge(pedidoCollectionPedido);
                if (oldFkEmpregadoOfPedidoCollectionPedido != null) {
                    oldFkEmpregadoOfPedidoCollectionPedido.getPedidoCollection().remove(pedidoCollectionPedido);
                    oldFkEmpregadoOfPedidoCollectionPedido = em.merge(oldFkEmpregadoOfPedidoCollectionPedido);
                }
            }
            for (TelefoneFuncionario telefoneFuncionarioCollectionTelefoneFuncionario : empregado.getTelefoneFuncionarioCollection()) {
                Empregado oldFkEmpregadoOfTelefoneFuncionarioCollectionTelefoneFuncionario = telefoneFuncionarioCollectionTelefoneFuncionario.getFkEmpregado();
                telefoneFuncionarioCollectionTelefoneFuncionario.setFkEmpregado(empregado);
                telefoneFuncionarioCollectionTelefoneFuncionario = em.merge(telefoneFuncionarioCollectionTelefoneFuncionario);
                if (oldFkEmpregadoOfTelefoneFuncionarioCollectionTelefoneFuncionario != null) {
                    oldFkEmpregadoOfTelefoneFuncionarioCollectionTelefoneFuncionario.getTelefoneFuncionarioCollection().remove(telefoneFuncionarioCollectionTelefoneFuncionario);
                    oldFkEmpregadoOfTelefoneFuncionarioCollectionTelefoneFuncionario = em.merge(oldFkEmpregadoOfTelefoneFuncionarioCollectionTelefoneFuncionario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empregado empregado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empregado persistentEmpregado = em.find(Empregado.class, empregado.getIdEmpregado());
            Funcaoempregado fkFuncaoEmpregadoOld = persistentEmpregado.getFkFuncaoEmpregado();
            Funcaoempregado fkFuncaoEmpregadoNew = empregado.getFkFuncaoEmpregado();
            Collection<Pedido> pedidoCollectionOld = persistentEmpregado.getPedidoCollection();
            Collection<Pedido> pedidoCollectionNew = empregado.getPedidoCollection();
            Collection<TelefoneFuncionario> telefoneFuncionarioCollectionOld = persistentEmpregado.getTelefoneFuncionarioCollection();
            Collection<TelefoneFuncionario> telefoneFuncionarioCollectionNew = empregado.getTelefoneFuncionarioCollection();
            if (fkFuncaoEmpregadoNew != null) {
                fkFuncaoEmpregadoNew = em.getReference(fkFuncaoEmpregadoNew.getClass(), fkFuncaoEmpregadoNew.getIdFuncao());
                empregado.setFkFuncaoEmpregado(fkFuncaoEmpregadoNew);
            }
            Collection<Pedido> attachedPedidoCollectionNew = new ArrayList<Pedido>();
            for (Pedido pedidoCollectionNewPedidoToAttach : pedidoCollectionNew) {
                pedidoCollectionNewPedidoToAttach = em.getReference(pedidoCollectionNewPedidoToAttach.getClass(), pedidoCollectionNewPedidoToAttach.getIdPedido());
                attachedPedidoCollectionNew.add(pedidoCollectionNewPedidoToAttach);
            }
            pedidoCollectionNew = attachedPedidoCollectionNew;
            empregado.setPedidoCollection(pedidoCollectionNew);
            Collection<TelefoneFuncionario> attachedTelefoneFuncionarioCollectionNew = new ArrayList<TelefoneFuncionario>();
            for (TelefoneFuncionario telefoneFuncionarioCollectionNewTelefoneFuncionarioToAttach : telefoneFuncionarioCollectionNew) {
                telefoneFuncionarioCollectionNewTelefoneFuncionarioToAttach = em.getReference(telefoneFuncionarioCollectionNewTelefoneFuncionarioToAttach.getClass(), telefoneFuncionarioCollectionNewTelefoneFuncionarioToAttach.getIdTelefoneCli());
                attachedTelefoneFuncionarioCollectionNew.add(telefoneFuncionarioCollectionNewTelefoneFuncionarioToAttach);
            }
            telefoneFuncionarioCollectionNew = attachedTelefoneFuncionarioCollectionNew;
            empregado.setTelefoneFuncionarioCollection(telefoneFuncionarioCollectionNew);
            empregado = em.merge(empregado);
            if (fkFuncaoEmpregadoOld != null && !fkFuncaoEmpregadoOld.equals(fkFuncaoEmpregadoNew)) {
                fkFuncaoEmpregadoOld.getEmpregadoCollection().remove(empregado);
                fkFuncaoEmpregadoOld = em.merge(fkFuncaoEmpregadoOld);
            }
            if (fkFuncaoEmpregadoNew != null && !fkFuncaoEmpregadoNew.equals(fkFuncaoEmpregadoOld)) {
                fkFuncaoEmpregadoNew.getEmpregadoCollection().add(empregado);
                fkFuncaoEmpregadoNew = em.merge(fkFuncaoEmpregadoNew);
            }
            for (Pedido pedidoCollectionOldPedido : pedidoCollectionOld) {
                if (!pedidoCollectionNew.contains(pedidoCollectionOldPedido)) {
                    pedidoCollectionOldPedido.setFkEmpregado(null);
                    pedidoCollectionOldPedido = em.merge(pedidoCollectionOldPedido);
                }
            }
            for (Pedido pedidoCollectionNewPedido : pedidoCollectionNew) {
                if (!pedidoCollectionOld.contains(pedidoCollectionNewPedido)) {
                    Empregado oldFkEmpregadoOfPedidoCollectionNewPedido = pedidoCollectionNewPedido.getFkEmpregado();
                    pedidoCollectionNewPedido.setFkEmpregado(empregado);
                    pedidoCollectionNewPedido = em.merge(pedidoCollectionNewPedido);
                    if (oldFkEmpregadoOfPedidoCollectionNewPedido != null && !oldFkEmpregadoOfPedidoCollectionNewPedido.equals(empregado)) {
                        oldFkEmpregadoOfPedidoCollectionNewPedido.getPedidoCollection().remove(pedidoCollectionNewPedido);
                        oldFkEmpregadoOfPedidoCollectionNewPedido = em.merge(oldFkEmpregadoOfPedidoCollectionNewPedido);
                    }
                }
            }
            for (TelefoneFuncionario telefoneFuncionarioCollectionOldTelefoneFuncionario : telefoneFuncionarioCollectionOld) {
                if (!telefoneFuncionarioCollectionNew.contains(telefoneFuncionarioCollectionOldTelefoneFuncionario)) {
                    telefoneFuncionarioCollectionOldTelefoneFuncionario.setFkEmpregado(null);
                    telefoneFuncionarioCollectionOldTelefoneFuncionario = em.merge(telefoneFuncionarioCollectionOldTelefoneFuncionario);
                }
            }
            for (TelefoneFuncionario telefoneFuncionarioCollectionNewTelefoneFuncionario : telefoneFuncionarioCollectionNew) {
                if (!telefoneFuncionarioCollectionOld.contains(telefoneFuncionarioCollectionNewTelefoneFuncionario)) {
                    Empregado oldFkEmpregadoOfTelefoneFuncionarioCollectionNewTelefoneFuncionario = telefoneFuncionarioCollectionNewTelefoneFuncionario.getFkEmpregado();
                    telefoneFuncionarioCollectionNewTelefoneFuncionario.setFkEmpregado(empregado);
                    telefoneFuncionarioCollectionNewTelefoneFuncionario = em.merge(telefoneFuncionarioCollectionNewTelefoneFuncionario);
                    if (oldFkEmpregadoOfTelefoneFuncionarioCollectionNewTelefoneFuncionario != null && !oldFkEmpregadoOfTelefoneFuncionarioCollectionNewTelefoneFuncionario.equals(empregado)) {
                        oldFkEmpregadoOfTelefoneFuncionarioCollectionNewTelefoneFuncionario.getTelefoneFuncionarioCollection().remove(telefoneFuncionarioCollectionNewTelefoneFuncionario);
                        oldFkEmpregadoOfTelefoneFuncionarioCollectionNewTelefoneFuncionario = em.merge(oldFkEmpregadoOfTelefoneFuncionarioCollectionNewTelefoneFuncionario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empregado.getIdEmpregado();
                if (findEmpregado(id) == null) {
                    throw new NonexistentEntityException("The empregado with id " + id + " no longer exists.");
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
            Empregado empregado;
            try {
                empregado = em.getReference(Empregado.class, id);
                empregado.getIdEmpregado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empregado with id " + id + " no longer exists.", enfe);
            }
            Funcaoempregado fkFuncaoEmpregado = empregado.getFkFuncaoEmpregado();
            if (fkFuncaoEmpregado != null) {
                fkFuncaoEmpregado.getEmpregadoCollection().remove(empregado);
                fkFuncaoEmpregado = em.merge(fkFuncaoEmpregado);
            }
            Collection<Pedido> pedidoCollection = empregado.getPedidoCollection();
            for (Pedido pedidoCollectionPedido : pedidoCollection) {
                pedidoCollectionPedido.setFkEmpregado(null);
                pedidoCollectionPedido = em.merge(pedidoCollectionPedido);
            }
            Collection<TelefoneFuncionario> telefoneFuncionarioCollection = empregado.getTelefoneFuncionarioCollection();
            for (TelefoneFuncionario telefoneFuncionarioCollectionTelefoneFuncionario : telefoneFuncionarioCollection) {
                telefoneFuncionarioCollectionTelefoneFuncionario.setFkEmpregado(null);
                telefoneFuncionarioCollectionTelefoneFuncionario = em.merge(telefoneFuncionarioCollectionTelefoneFuncionario);
            }
            em.remove(empregado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empregado> findEmpregadoEntities() {
        return findEmpregadoEntities(true, -1, -1);
    }

    public List<Empregado> findEmpregadoEntities(int maxResults, int firstResult) {
        return findEmpregadoEntities(false, maxResults, firstResult);
    }

    private List<Empregado> findEmpregadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empregado.class));
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

    public Empregado findEmpregado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empregado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpregadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empregado> rt = cq.from(Empregado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
