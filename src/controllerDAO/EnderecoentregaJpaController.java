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
import model.Cliente;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Enderecoentrega;

/**
 *
 * @author helio
 */
public class EnderecoentregaJpaController implements Serializable {

    public EnderecoentregaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Enderecoentrega enderecoentrega) {
        if (enderecoentrega.getClienteCollection() == null) {
            enderecoentrega.setClienteCollection(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Cliente> attachedClienteCollection = new ArrayList<Cliente>();
            for (Cliente clienteCollectionClienteToAttach : enderecoentrega.getClienteCollection()) {
                clienteCollectionClienteToAttach = em.getReference(clienteCollectionClienteToAttach.getClass(), clienteCollectionClienteToAttach.getIdCliente());
                attachedClienteCollection.add(clienteCollectionClienteToAttach);
            }
            enderecoentrega.setClienteCollection(attachedClienteCollection);
            em.persist(enderecoentrega);
            for (Cliente clienteCollectionCliente : enderecoentrega.getClienteCollection()) {
                Enderecoentrega oldFkEnderecoEntregaOfClienteCollectionCliente = clienteCollectionCliente.getFkEnderecoEntrega();
                clienteCollectionCliente.setFkEnderecoEntrega(enderecoentrega);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
                if (oldFkEnderecoEntregaOfClienteCollectionCliente != null) {
                    oldFkEnderecoEntregaOfClienteCollectionCliente.getClienteCollection().remove(clienteCollectionCliente);
                    oldFkEnderecoEntregaOfClienteCollectionCliente = em.merge(oldFkEnderecoEntregaOfClienteCollectionCliente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Enderecoentrega enderecoentrega) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Enderecoentrega persistentEnderecoentrega = em.find(Enderecoentrega.class, enderecoentrega.getIdEnderecoEntrega());
            Collection<Cliente> clienteCollectionOld = persistentEnderecoentrega.getClienteCollection();
            Collection<Cliente> clienteCollectionNew = enderecoentrega.getClienteCollection();
            Collection<Cliente> attachedClienteCollectionNew = new ArrayList<Cliente>();
            for (Cliente clienteCollectionNewClienteToAttach : clienteCollectionNew) {
                clienteCollectionNewClienteToAttach = em.getReference(clienteCollectionNewClienteToAttach.getClass(), clienteCollectionNewClienteToAttach.getIdCliente());
                attachedClienteCollectionNew.add(clienteCollectionNewClienteToAttach);
            }
            clienteCollectionNew = attachedClienteCollectionNew;
            enderecoentrega.setClienteCollection(clienteCollectionNew);
            enderecoentrega = em.merge(enderecoentrega);
            for (Cliente clienteCollectionOldCliente : clienteCollectionOld) {
                if (!clienteCollectionNew.contains(clienteCollectionOldCliente)) {
                    clienteCollectionOldCliente.setFkEnderecoEntrega(null);
                    clienteCollectionOldCliente = em.merge(clienteCollectionOldCliente);
                }
            }
            for (Cliente clienteCollectionNewCliente : clienteCollectionNew) {
                if (!clienteCollectionOld.contains(clienteCollectionNewCliente)) {
                    Enderecoentrega oldFkEnderecoEntregaOfClienteCollectionNewCliente = clienteCollectionNewCliente.getFkEnderecoEntrega();
                    clienteCollectionNewCliente.setFkEnderecoEntrega(enderecoentrega);
                    clienteCollectionNewCliente = em.merge(clienteCollectionNewCliente);
                    if (oldFkEnderecoEntregaOfClienteCollectionNewCliente != null && !oldFkEnderecoEntregaOfClienteCollectionNewCliente.equals(enderecoentrega)) {
                        oldFkEnderecoEntregaOfClienteCollectionNewCliente.getClienteCollection().remove(clienteCollectionNewCliente);
                        oldFkEnderecoEntregaOfClienteCollectionNewCliente = em.merge(oldFkEnderecoEntregaOfClienteCollectionNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = enderecoentrega.getIdEnderecoEntrega();
                if (findEnderecoentrega(id) == null) {
                    throw new NonexistentEntityException("The enderecoentrega with id " + id + " no longer exists.");
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
            Enderecoentrega enderecoentrega;
            try {
                enderecoentrega = em.getReference(Enderecoentrega.class, id);
                enderecoentrega.getIdEnderecoEntrega();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The enderecoentrega with id " + id + " no longer exists.", enfe);
            }
            Collection<Cliente> clienteCollection = enderecoentrega.getClienteCollection();
            for (Cliente clienteCollectionCliente : clienteCollection) {
                clienteCollectionCliente.setFkEnderecoEntrega(null);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
            }
            em.remove(enderecoentrega);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Enderecoentrega> findEnderecoentregaEntities() {
        return findEnderecoentregaEntities(true, -1, -1);
    }

    public List<Enderecoentrega> findEnderecoentregaEntities(int maxResults, int firstResult) {
        return findEnderecoentregaEntities(false, maxResults, firstResult);
    }

    private List<Enderecoentrega> findEnderecoentregaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Enderecoentrega.class));
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

    public Enderecoentrega findEnderecoentrega(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Enderecoentrega.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnderecoentregaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Enderecoentrega> rt = cq.from(Enderecoentrega.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
