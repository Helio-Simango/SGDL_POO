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
import model.Venda;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Encomenda;
import model.Itemcardapio;

/**
 *
 * @author helio
 */
public class EncomendaJpaController implements Serializable {

    public EncomendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Encomenda encomenda) {
        if (encomenda.getVendaCollection() == null) {
            encomenda.setVendaCollection(new ArrayList<Venda>());
        }
        if (encomenda.getItemcardapioCollection() == null) {
            encomenda.setItemcardapioCollection(new ArrayList<Itemcardapio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente fkCliente = encomenda.getFkCliente();
            if (fkCliente != null) {
                fkCliente = em.getReference(fkCliente.getClass(), fkCliente.getIdCliente());
                encomenda.setFkCliente(fkCliente);
            }
            Collection<Venda> attachedVendaCollection = new ArrayList<Venda>();
            for (Venda vendaCollectionVendaToAttach : encomenda.getVendaCollection()) {
                vendaCollectionVendaToAttach = em.getReference(vendaCollectionVendaToAttach.getClass(), vendaCollectionVendaToAttach.getIdVenda());
                attachedVendaCollection.add(vendaCollectionVendaToAttach);
            }
            encomenda.setVendaCollection(attachedVendaCollection);
            Collection<Itemcardapio> attachedItemcardapioCollection = new ArrayList<Itemcardapio>();
            for (Itemcardapio itemcardapioCollectionItemcardapioToAttach : encomenda.getItemcardapioCollection()) {
                itemcardapioCollectionItemcardapioToAttach = em.getReference(itemcardapioCollectionItemcardapioToAttach.getClass(), itemcardapioCollectionItemcardapioToAttach.getIdItemCardapio());
                attachedItemcardapioCollection.add(itemcardapioCollectionItemcardapioToAttach);
            }
            encomenda.setItemcardapioCollection(attachedItemcardapioCollection);
            em.persist(encomenda);
            if (fkCliente != null) {
                fkCliente.getEncomendaCollection().add(encomenda);
                fkCliente = em.merge(fkCliente);
            }
            for (Venda vendaCollectionVenda : encomenda.getVendaCollection()) {
                Encomenda oldFkEncomendaOfVendaCollectionVenda = vendaCollectionVenda.getFkEncomenda();
                vendaCollectionVenda.setFkEncomenda(encomenda);
                vendaCollectionVenda = em.merge(vendaCollectionVenda);
                if (oldFkEncomendaOfVendaCollectionVenda != null) {
                    oldFkEncomendaOfVendaCollectionVenda.getVendaCollection().remove(vendaCollectionVenda);
                    oldFkEncomendaOfVendaCollectionVenda = em.merge(oldFkEncomendaOfVendaCollectionVenda);
                }
            }
            for (Itemcardapio itemcardapioCollectionItemcardapio : encomenda.getItemcardapioCollection()) {
                Encomenda oldFkEncomendaOfItemcardapioCollectionItemcardapio = itemcardapioCollectionItemcardapio.getFkEncomenda();
                itemcardapioCollectionItemcardapio.setFkEncomenda(encomenda);
                itemcardapioCollectionItemcardapio = em.merge(itemcardapioCollectionItemcardapio);
                if (oldFkEncomendaOfItemcardapioCollectionItemcardapio != null) {
                    oldFkEncomendaOfItemcardapioCollectionItemcardapio.getItemcardapioCollection().remove(itemcardapioCollectionItemcardapio);
                    oldFkEncomendaOfItemcardapioCollectionItemcardapio = em.merge(oldFkEncomendaOfItemcardapioCollectionItemcardapio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Encomenda encomenda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Encomenda persistentEncomenda = em.find(Encomenda.class, encomenda.getIdEncomenda());
            Cliente fkClienteOld = persistentEncomenda.getFkCliente();
            Cliente fkClienteNew = encomenda.getFkCliente();
            Collection<Venda> vendaCollectionOld = persistentEncomenda.getVendaCollection();
            Collection<Venda> vendaCollectionNew = encomenda.getVendaCollection();
            Collection<Itemcardapio> itemcardapioCollectionOld = persistentEncomenda.getItemcardapioCollection();
            Collection<Itemcardapio> itemcardapioCollectionNew = encomenda.getItemcardapioCollection();
            if (fkClienteNew != null) {
                fkClienteNew = em.getReference(fkClienteNew.getClass(), fkClienteNew.getIdCliente());
                encomenda.setFkCliente(fkClienteNew);
            }
            Collection<Venda> attachedVendaCollectionNew = new ArrayList<Venda>();
            for (Venda vendaCollectionNewVendaToAttach : vendaCollectionNew) {
                vendaCollectionNewVendaToAttach = em.getReference(vendaCollectionNewVendaToAttach.getClass(), vendaCollectionNewVendaToAttach.getIdVenda());
                attachedVendaCollectionNew.add(vendaCollectionNewVendaToAttach);
            }
            vendaCollectionNew = attachedVendaCollectionNew;
            encomenda.setVendaCollection(vendaCollectionNew);
            Collection<Itemcardapio> attachedItemcardapioCollectionNew = new ArrayList<Itemcardapio>();
            for (Itemcardapio itemcardapioCollectionNewItemcardapioToAttach : itemcardapioCollectionNew) {
                itemcardapioCollectionNewItemcardapioToAttach = em.getReference(itemcardapioCollectionNewItemcardapioToAttach.getClass(), itemcardapioCollectionNewItemcardapioToAttach.getIdItemCardapio());
                attachedItemcardapioCollectionNew.add(itemcardapioCollectionNewItemcardapioToAttach);
            }
            itemcardapioCollectionNew = attachedItemcardapioCollectionNew;
            encomenda.setItemcardapioCollection(itemcardapioCollectionNew);
            encomenda = em.merge(encomenda);
            if (fkClienteOld != null && !fkClienteOld.equals(fkClienteNew)) {
                fkClienteOld.getEncomendaCollection().remove(encomenda);
                fkClienteOld = em.merge(fkClienteOld);
            }
            if (fkClienteNew != null && !fkClienteNew.equals(fkClienteOld)) {
                fkClienteNew.getEncomendaCollection().add(encomenda);
                fkClienteNew = em.merge(fkClienteNew);
            }
            for (Venda vendaCollectionOldVenda : vendaCollectionOld) {
                if (!vendaCollectionNew.contains(vendaCollectionOldVenda)) {
                    vendaCollectionOldVenda.setFkEncomenda(null);
                    vendaCollectionOldVenda = em.merge(vendaCollectionOldVenda);
                }
            }
            for (Venda vendaCollectionNewVenda : vendaCollectionNew) {
                if (!vendaCollectionOld.contains(vendaCollectionNewVenda)) {
                    Encomenda oldFkEncomendaOfVendaCollectionNewVenda = vendaCollectionNewVenda.getFkEncomenda();
                    vendaCollectionNewVenda.setFkEncomenda(encomenda);
                    vendaCollectionNewVenda = em.merge(vendaCollectionNewVenda);
                    if (oldFkEncomendaOfVendaCollectionNewVenda != null && !oldFkEncomendaOfVendaCollectionNewVenda.equals(encomenda)) {
                        oldFkEncomendaOfVendaCollectionNewVenda.getVendaCollection().remove(vendaCollectionNewVenda);
                        oldFkEncomendaOfVendaCollectionNewVenda = em.merge(oldFkEncomendaOfVendaCollectionNewVenda);
                    }
                }
            }
            for (Itemcardapio itemcardapioCollectionOldItemcardapio : itemcardapioCollectionOld) {
                if (!itemcardapioCollectionNew.contains(itemcardapioCollectionOldItemcardapio)) {
                    itemcardapioCollectionOldItemcardapio.setFkEncomenda(null);
                    itemcardapioCollectionOldItemcardapio = em.merge(itemcardapioCollectionOldItemcardapio);
                }
            }
            for (Itemcardapio itemcardapioCollectionNewItemcardapio : itemcardapioCollectionNew) {
                if (!itemcardapioCollectionOld.contains(itemcardapioCollectionNewItemcardapio)) {
                    Encomenda oldFkEncomendaOfItemcardapioCollectionNewItemcardapio = itemcardapioCollectionNewItemcardapio.getFkEncomenda();
                    itemcardapioCollectionNewItemcardapio.setFkEncomenda(encomenda);
                    itemcardapioCollectionNewItemcardapio = em.merge(itemcardapioCollectionNewItemcardapio);
                    if (oldFkEncomendaOfItemcardapioCollectionNewItemcardapio != null && !oldFkEncomendaOfItemcardapioCollectionNewItemcardapio.equals(encomenda)) {
                        oldFkEncomendaOfItemcardapioCollectionNewItemcardapio.getItemcardapioCollection().remove(itemcardapioCollectionNewItemcardapio);
                        oldFkEncomendaOfItemcardapioCollectionNewItemcardapio = em.merge(oldFkEncomendaOfItemcardapioCollectionNewItemcardapio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = encomenda.getIdEncomenda();
                if (findEncomenda(id) == null) {
                    throw new NonexistentEntityException("The encomenda with id " + id + " no longer exists.");
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
            Encomenda encomenda;
            try {
                encomenda = em.getReference(Encomenda.class, id);
                encomenda.getIdEncomenda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The encomenda with id " + id + " no longer exists.", enfe);
            }
            Cliente fkCliente = encomenda.getFkCliente();
            if (fkCliente != null) {
                fkCliente.getEncomendaCollection().remove(encomenda);
                fkCliente = em.merge(fkCliente);
            }
            Collection<Venda> vendaCollection = encomenda.getVendaCollection();
            for (Venda vendaCollectionVenda : vendaCollection) {
                vendaCollectionVenda.setFkEncomenda(null);
                vendaCollectionVenda = em.merge(vendaCollectionVenda);
            }
            Collection<Itemcardapio> itemcardapioCollection = encomenda.getItemcardapioCollection();
            for (Itemcardapio itemcardapioCollectionItemcardapio : itemcardapioCollection) {
                itemcardapioCollectionItemcardapio.setFkEncomenda(null);
                itemcardapioCollectionItemcardapio = em.merge(itemcardapioCollectionItemcardapio);
            }
            em.remove(encomenda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Encomenda> findEncomendaEntities() {
        return findEncomendaEntities(true, -1, -1);
    }

    public List<Encomenda> findEncomendaEntities(int maxResults, int firstResult) {
        return findEncomendaEntities(false, maxResults, firstResult);
    }

    private List<Encomenda> findEncomendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Encomenda.class));
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

    public Encomenda findEncomenda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Encomenda.class, id);
        } finally {
            em.close();
        }
    }

    public int getEncomendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Encomenda> rt = cq.from(Encomenda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
