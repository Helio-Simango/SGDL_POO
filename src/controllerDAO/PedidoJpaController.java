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
import model.Empregado;
import model.Venda;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Itemcardapio;
import model.Pedido;

/**
 *
 * @author helio
 */
public class PedidoJpaController implements Serializable {

    public PedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedido pedido) {
        if (pedido.getVendaCollection() == null) {
            pedido.setVendaCollection(new ArrayList<Venda>());
        }
        if (pedido.getItemcardapioCollection() == null) {
            pedido.setItemcardapioCollection(new ArrayList<Itemcardapio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente fkCliente = pedido.getFkCliente();
            if (fkCliente != null) {
                fkCliente = em.getReference(fkCliente.getClass(), fkCliente.getIdCliente());
                pedido.setFkCliente(fkCliente);
            }
            Empregado fkEmpregado = pedido.getFkEmpregado();
            if (fkEmpregado != null) {
                fkEmpregado = em.getReference(fkEmpregado.getClass(), fkEmpregado.getIdEmpregado());
                pedido.setFkEmpregado(fkEmpregado);
            }
            Collection<Venda> attachedVendaCollection = new ArrayList<Venda>();
            for (Venda vendaCollectionVendaToAttach : pedido.getVendaCollection()) {
                vendaCollectionVendaToAttach = em.getReference(vendaCollectionVendaToAttach.getClass(), vendaCollectionVendaToAttach.getIdVenda());
                attachedVendaCollection.add(vendaCollectionVendaToAttach);
            }
            pedido.setVendaCollection(attachedVendaCollection);
            Collection<Itemcardapio> attachedItemcardapioCollection = new ArrayList<Itemcardapio>();
            for (Itemcardapio itemcardapioCollectionItemcardapioToAttach : pedido.getItemcardapioCollection()) {
                itemcardapioCollectionItemcardapioToAttach = em.getReference(itemcardapioCollectionItemcardapioToAttach.getClass(), itemcardapioCollectionItemcardapioToAttach.getIdItemCardapio());
                attachedItemcardapioCollection.add(itemcardapioCollectionItemcardapioToAttach);
            }
            pedido.setItemcardapioCollection(attachedItemcardapioCollection);
            em.persist(pedido);
            if (fkCliente != null) {
                fkCliente.getPedidoCollection().add(pedido);
                fkCliente = em.merge(fkCliente);
            }
            if (fkEmpregado != null) {
                fkEmpregado.getPedidoCollection().add(pedido);
                fkEmpregado = em.merge(fkEmpregado);
            }
            for (Venda vendaCollectionVenda : pedido.getVendaCollection()) {
                Pedido oldFkPedidoOfVendaCollectionVenda = vendaCollectionVenda.getFkPedido();
                vendaCollectionVenda.setFkPedido(pedido);
                vendaCollectionVenda = em.merge(vendaCollectionVenda);
                if (oldFkPedidoOfVendaCollectionVenda != null) {
                    oldFkPedidoOfVendaCollectionVenda.getVendaCollection().remove(vendaCollectionVenda);
                    oldFkPedidoOfVendaCollectionVenda = em.merge(oldFkPedidoOfVendaCollectionVenda);
                }
            }
            for (Itemcardapio itemcardapioCollectionItemcardapio : pedido.getItemcardapioCollection()) {
                Pedido oldFkPedidoOfItemcardapioCollectionItemcardapio = itemcardapioCollectionItemcardapio.getFkPedido();
                itemcardapioCollectionItemcardapio.setFkPedido(pedido);
                itemcardapioCollectionItemcardapio = em.merge(itemcardapioCollectionItemcardapio);
                if (oldFkPedidoOfItemcardapioCollectionItemcardapio != null) {
                    oldFkPedidoOfItemcardapioCollectionItemcardapio.getItemcardapioCollection().remove(itemcardapioCollectionItemcardapio);
                    oldFkPedidoOfItemcardapioCollectionItemcardapio = em.merge(oldFkPedidoOfItemcardapioCollectionItemcardapio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedido pedido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido persistentPedido = em.find(Pedido.class, pedido.getIdPedido());
            Cliente fkClienteOld = persistentPedido.getFkCliente();
            Cliente fkClienteNew = pedido.getFkCliente();
            Empregado fkEmpregadoOld = persistentPedido.getFkEmpregado();
            Empregado fkEmpregadoNew = pedido.getFkEmpregado();
            Collection<Venda> vendaCollectionOld = persistentPedido.getVendaCollection();
            Collection<Venda> vendaCollectionNew = pedido.getVendaCollection();
            Collection<Itemcardapio> itemcardapioCollectionOld = persistentPedido.getItemcardapioCollection();
            Collection<Itemcardapio> itemcardapioCollectionNew = pedido.getItemcardapioCollection();
            if (fkClienteNew != null) {
                fkClienteNew = em.getReference(fkClienteNew.getClass(), fkClienteNew.getIdCliente());
                pedido.setFkCliente(fkClienteNew);
            }
            if (fkEmpregadoNew != null) {
                fkEmpregadoNew = em.getReference(fkEmpregadoNew.getClass(), fkEmpregadoNew.getIdEmpregado());
                pedido.setFkEmpregado(fkEmpregadoNew);
            }
            Collection<Venda> attachedVendaCollectionNew = new ArrayList<Venda>();
            for (Venda vendaCollectionNewVendaToAttach : vendaCollectionNew) {
                vendaCollectionNewVendaToAttach = em.getReference(vendaCollectionNewVendaToAttach.getClass(), vendaCollectionNewVendaToAttach.getIdVenda());
                attachedVendaCollectionNew.add(vendaCollectionNewVendaToAttach);
            }
            vendaCollectionNew = attachedVendaCollectionNew;
            pedido.setVendaCollection(vendaCollectionNew);
            Collection<Itemcardapio> attachedItemcardapioCollectionNew = new ArrayList<Itemcardapio>();
            for (Itemcardapio itemcardapioCollectionNewItemcardapioToAttach : itemcardapioCollectionNew) {
                itemcardapioCollectionNewItemcardapioToAttach = em.getReference(itemcardapioCollectionNewItemcardapioToAttach.getClass(), itemcardapioCollectionNewItemcardapioToAttach.getIdItemCardapio());
                attachedItemcardapioCollectionNew.add(itemcardapioCollectionNewItemcardapioToAttach);
            }
            itemcardapioCollectionNew = attachedItemcardapioCollectionNew;
            pedido.setItemcardapioCollection(itemcardapioCollectionNew);
            pedido = em.merge(pedido);
            if (fkClienteOld != null && !fkClienteOld.equals(fkClienteNew)) {
                fkClienteOld.getPedidoCollection().remove(pedido);
                fkClienteOld = em.merge(fkClienteOld);
            }
            if (fkClienteNew != null && !fkClienteNew.equals(fkClienteOld)) {
                fkClienteNew.getPedidoCollection().add(pedido);
                fkClienteNew = em.merge(fkClienteNew);
            }
            if (fkEmpregadoOld != null && !fkEmpregadoOld.equals(fkEmpregadoNew)) {
                fkEmpregadoOld.getPedidoCollection().remove(pedido);
                fkEmpregadoOld = em.merge(fkEmpregadoOld);
            }
            if (fkEmpregadoNew != null && !fkEmpregadoNew.equals(fkEmpregadoOld)) {
                fkEmpregadoNew.getPedidoCollection().add(pedido);
                fkEmpregadoNew = em.merge(fkEmpregadoNew);
            }
            for (Venda vendaCollectionOldVenda : vendaCollectionOld) {
                if (!vendaCollectionNew.contains(vendaCollectionOldVenda)) {
                    vendaCollectionOldVenda.setFkPedido(null);
                    vendaCollectionOldVenda = em.merge(vendaCollectionOldVenda);
                }
            }
            for (Venda vendaCollectionNewVenda : vendaCollectionNew) {
                if (!vendaCollectionOld.contains(vendaCollectionNewVenda)) {
                    Pedido oldFkPedidoOfVendaCollectionNewVenda = vendaCollectionNewVenda.getFkPedido();
                    vendaCollectionNewVenda.setFkPedido(pedido);
                    vendaCollectionNewVenda = em.merge(vendaCollectionNewVenda);
                    if (oldFkPedidoOfVendaCollectionNewVenda != null && !oldFkPedidoOfVendaCollectionNewVenda.equals(pedido)) {
                        oldFkPedidoOfVendaCollectionNewVenda.getVendaCollection().remove(vendaCollectionNewVenda);
                        oldFkPedidoOfVendaCollectionNewVenda = em.merge(oldFkPedidoOfVendaCollectionNewVenda);
                    }
                }
            }
            for (Itemcardapio itemcardapioCollectionOldItemcardapio : itemcardapioCollectionOld) {
                if (!itemcardapioCollectionNew.contains(itemcardapioCollectionOldItemcardapio)) {
                    itemcardapioCollectionOldItemcardapio.setFkPedido(null);
                    itemcardapioCollectionOldItemcardapio = em.merge(itemcardapioCollectionOldItemcardapio);
                }
            }
            for (Itemcardapio itemcardapioCollectionNewItemcardapio : itemcardapioCollectionNew) {
                if (!itemcardapioCollectionOld.contains(itemcardapioCollectionNewItemcardapio)) {
                    Pedido oldFkPedidoOfItemcardapioCollectionNewItemcardapio = itemcardapioCollectionNewItemcardapio.getFkPedido();
                    itemcardapioCollectionNewItemcardapio.setFkPedido(pedido);
                    itemcardapioCollectionNewItemcardapio = em.merge(itemcardapioCollectionNewItemcardapio);
                    if (oldFkPedidoOfItemcardapioCollectionNewItemcardapio != null && !oldFkPedidoOfItemcardapioCollectionNewItemcardapio.equals(pedido)) {
                        oldFkPedidoOfItemcardapioCollectionNewItemcardapio.getItemcardapioCollection().remove(itemcardapioCollectionNewItemcardapio);
                        oldFkPedidoOfItemcardapioCollectionNewItemcardapio = em.merge(oldFkPedidoOfItemcardapioCollectionNewItemcardapio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pedido.getIdPedido();
                if (findPedido(id) == null) {
                    throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.");
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
            Pedido pedido;
            try {
                pedido = em.getReference(Pedido.class, id);
                pedido.getIdPedido();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.", enfe);
            }
            Cliente fkCliente = pedido.getFkCliente();
            if (fkCliente != null) {
                fkCliente.getPedidoCollection().remove(pedido);
                fkCliente = em.merge(fkCliente);
            }
            Empregado fkEmpregado = pedido.getFkEmpregado();
            if (fkEmpregado != null) {
                fkEmpregado.getPedidoCollection().remove(pedido);
                fkEmpregado = em.merge(fkEmpregado);
            }
            Collection<Venda> vendaCollection = pedido.getVendaCollection();
            for (Venda vendaCollectionVenda : vendaCollection) {
                vendaCollectionVenda.setFkPedido(null);
                vendaCollectionVenda = em.merge(vendaCollectionVenda);
            }
            Collection<Itemcardapio> itemcardapioCollection = pedido.getItemcardapioCollection();
            for (Itemcardapio itemcardapioCollectionItemcardapio : itemcardapioCollection) {
                itemcardapioCollectionItemcardapio.setFkPedido(null);
                itemcardapioCollectionItemcardapio = em.merge(itemcardapioCollectionItemcardapio);
            }
            em.remove(pedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedido> findPedidoEntities() {
        return findPedidoEntities(true, -1, -1);
    }

    public List<Pedido> findPedidoEntities(int maxResults, int firstResult) {
        return findPedidoEntities(false, maxResults, firstResult);
    }

    private List<Pedido> findPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
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

    public Pedido findPedido(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedido> rt = cq.from(Pedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
