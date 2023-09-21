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
import model.Enderecoentrega;
import model.Venda;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Cliente;
import model.Pedido;
import model.Telefonecliente;
import model.Encomenda;

/**
 *
 * @author helio
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getVendaCollection() == null) {
            cliente.setVendaCollection(new ArrayList<Venda>());
        }
        if (cliente.getPedidoCollection() == null) {
            cliente.setPedidoCollection(new ArrayList<Pedido>());
        }
        if (cliente.getTelefoneclienteCollection() == null) {
            cliente.setTelefoneclienteCollection(new ArrayList<Telefonecliente>());
        }
        if (cliente.getEncomendaCollection() == null) {
            cliente.setEncomendaCollection(new ArrayList<Encomenda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Enderecoentrega fkEnderecoEntrega = cliente.getFkEnderecoEntrega();
            if (fkEnderecoEntrega != null) {
                fkEnderecoEntrega = em.getReference(fkEnderecoEntrega.getClass(), fkEnderecoEntrega.getIdEnderecoEntrega());
                cliente.setFkEnderecoEntrega(fkEnderecoEntrega);
            }
            Collection<Venda> attachedVendaCollection = new ArrayList<Venda>();
            for (Venda vendaCollectionVendaToAttach : cliente.getVendaCollection()) {
                vendaCollectionVendaToAttach = em.getReference(vendaCollectionVendaToAttach.getClass(), vendaCollectionVendaToAttach.getIdVenda());
                attachedVendaCollection.add(vendaCollectionVendaToAttach);
            }
            cliente.setVendaCollection(attachedVendaCollection);
            Collection<Pedido> attachedPedidoCollection = new ArrayList<Pedido>();
            for (Pedido pedidoCollectionPedidoToAttach : cliente.getPedidoCollection()) {
                pedidoCollectionPedidoToAttach = em.getReference(pedidoCollectionPedidoToAttach.getClass(), pedidoCollectionPedidoToAttach.getIdPedido());
                attachedPedidoCollection.add(pedidoCollectionPedidoToAttach);
            }
            cliente.setPedidoCollection(attachedPedidoCollection);
            Collection<Telefonecliente> attachedTelefoneclienteCollection = new ArrayList<Telefonecliente>();
            for (Telefonecliente telefoneclienteCollectionTelefoneclienteToAttach : cliente.getTelefoneclienteCollection()) {
                telefoneclienteCollectionTelefoneclienteToAttach = em.getReference(telefoneclienteCollectionTelefoneclienteToAttach.getClass(), telefoneclienteCollectionTelefoneclienteToAttach.getIdTelefoneCliente());
                attachedTelefoneclienteCollection.add(telefoneclienteCollectionTelefoneclienteToAttach);
            }
            cliente.setTelefoneclienteCollection(attachedTelefoneclienteCollection);
            Collection<Encomenda> attachedEncomendaCollection = new ArrayList<Encomenda>();
            for (Encomenda encomendaCollectionEncomendaToAttach : cliente.getEncomendaCollection()) {
                encomendaCollectionEncomendaToAttach = em.getReference(encomendaCollectionEncomendaToAttach.getClass(), encomendaCollectionEncomendaToAttach.getIdEncomenda());
                attachedEncomendaCollection.add(encomendaCollectionEncomendaToAttach);
            }
            cliente.setEncomendaCollection(attachedEncomendaCollection);
            em.persist(cliente);
            if (fkEnderecoEntrega != null) {
                fkEnderecoEntrega.getClienteCollection().add(cliente);
                fkEnderecoEntrega = em.merge(fkEnderecoEntrega);
            }
            for (Venda vendaCollectionVenda : cliente.getVendaCollection()) {
                Cliente oldFkClienteOfVendaCollectionVenda = vendaCollectionVenda.getFkCliente();
                vendaCollectionVenda.setFkCliente(cliente);
                vendaCollectionVenda = em.merge(vendaCollectionVenda);
                if (oldFkClienteOfVendaCollectionVenda != null) {
                    oldFkClienteOfVendaCollectionVenda.getVendaCollection().remove(vendaCollectionVenda);
                    oldFkClienteOfVendaCollectionVenda = em.merge(oldFkClienteOfVendaCollectionVenda);
                }
            }
            for (Pedido pedidoCollectionPedido : cliente.getPedidoCollection()) {
                Cliente oldFkClienteOfPedidoCollectionPedido = pedidoCollectionPedido.getFkCliente();
                pedidoCollectionPedido.setFkCliente(cliente);
                pedidoCollectionPedido = em.merge(pedidoCollectionPedido);
                if (oldFkClienteOfPedidoCollectionPedido != null) {
                    oldFkClienteOfPedidoCollectionPedido.getPedidoCollection().remove(pedidoCollectionPedido);
                    oldFkClienteOfPedidoCollectionPedido = em.merge(oldFkClienteOfPedidoCollectionPedido);
                }
            }
            for (Telefonecliente telefoneclienteCollectionTelefonecliente : cliente.getTelefoneclienteCollection()) {
                Cliente oldFkClienteOfTelefoneclienteCollectionTelefonecliente = telefoneclienteCollectionTelefonecliente.getFkCliente();
                telefoneclienteCollectionTelefonecliente.setFkCliente(cliente);
                telefoneclienteCollectionTelefonecliente = em.merge(telefoneclienteCollectionTelefonecliente);
                if (oldFkClienteOfTelefoneclienteCollectionTelefonecliente != null) {
                    oldFkClienteOfTelefoneclienteCollectionTelefonecliente.getTelefoneclienteCollection().remove(telefoneclienteCollectionTelefonecliente);
                    oldFkClienteOfTelefoneclienteCollectionTelefonecliente = em.merge(oldFkClienteOfTelefoneclienteCollectionTelefonecliente);
                }
            }
            for (Encomenda encomendaCollectionEncomenda : cliente.getEncomendaCollection()) {
                Cliente oldFkClienteOfEncomendaCollectionEncomenda = encomendaCollectionEncomenda.getFkCliente();
                encomendaCollectionEncomenda.setFkCliente(cliente);
                encomendaCollectionEncomenda = em.merge(encomendaCollectionEncomenda);
                if (oldFkClienteOfEncomendaCollectionEncomenda != null) {
                    oldFkClienteOfEncomendaCollectionEncomenda.getEncomendaCollection().remove(encomendaCollectionEncomenda);
                    oldFkClienteOfEncomendaCollectionEncomenda = em.merge(oldFkClienteOfEncomendaCollectionEncomenda);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdCliente());
            Enderecoentrega fkEnderecoEntregaOld = persistentCliente.getFkEnderecoEntrega();
            Enderecoentrega fkEnderecoEntregaNew = cliente.getFkEnderecoEntrega();
            Collection<Venda> vendaCollectionOld = persistentCliente.getVendaCollection();
            Collection<Venda> vendaCollectionNew = cliente.getVendaCollection();
            Collection<Pedido> pedidoCollectionOld = persistentCliente.getPedidoCollection();
            Collection<Pedido> pedidoCollectionNew = cliente.getPedidoCollection();
            Collection<Telefonecliente> telefoneclienteCollectionOld = persistentCliente.getTelefoneclienteCollection();
            Collection<Telefonecliente> telefoneclienteCollectionNew = cliente.getTelefoneclienteCollection();
            Collection<Encomenda> encomendaCollectionOld = persistentCliente.getEncomendaCollection();
            Collection<Encomenda> encomendaCollectionNew = cliente.getEncomendaCollection();
            if (fkEnderecoEntregaNew != null) {
                fkEnderecoEntregaNew = em.getReference(fkEnderecoEntregaNew.getClass(), fkEnderecoEntregaNew.getIdEnderecoEntrega());
                cliente.setFkEnderecoEntrega(fkEnderecoEntregaNew);
            }
            Collection<Venda> attachedVendaCollectionNew = new ArrayList<Venda>();
            for (Venda vendaCollectionNewVendaToAttach : vendaCollectionNew) {
                vendaCollectionNewVendaToAttach = em.getReference(vendaCollectionNewVendaToAttach.getClass(), vendaCollectionNewVendaToAttach.getIdVenda());
                attachedVendaCollectionNew.add(vendaCollectionNewVendaToAttach);
            }
            vendaCollectionNew = attachedVendaCollectionNew;
            cliente.setVendaCollection(vendaCollectionNew);
            Collection<Pedido> attachedPedidoCollectionNew = new ArrayList<Pedido>();
            for (Pedido pedidoCollectionNewPedidoToAttach : pedidoCollectionNew) {
                pedidoCollectionNewPedidoToAttach = em.getReference(pedidoCollectionNewPedidoToAttach.getClass(), pedidoCollectionNewPedidoToAttach.getIdPedido());
                attachedPedidoCollectionNew.add(pedidoCollectionNewPedidoToAttach);
            }
            pedidoCollectionNew = attachedPedidoCollectionNew;
            cliente.setPedidoCollection(pedidoCollectionNew);
            Collection<Telefonecliente> attachedTelefoneclienteCollectionNew = new ArrayList<Telefonecliente>();
            for (Telefonecliente telefoneclienteCollectionNewTelefoneclienteToAttach : telefoneclienteCollectionNew) {
                telefoneclienteCollectionNewTelefoneclienteToAttach = em.getReference(telefoneclienteCollectionNewTelefoneclienteToAttach.getClass(), telefoneclienteCollectionNewTelefoneclienteToAttach.getIdTelefoneCliente());
                attachedTelefoneclienteCollectionNew.add(telefoneclienteCollectionNewTelefoneclienteToAttach);
            }
            telefoneclienteCollectionNew = attachedTelefoneclienteCollectionNew;
            cliente.setTelefoneclienteCollection(telefoneclienteCollectionNew);
            Collection<Encomenda> attachedEncomendaCollectionNew = new ArrayList<Encomenda>();
            for (Encomenda encomendaCollectionNewEncomendaToAttach : encomendaCollectionNew) {
                encomendaCollectionNewEncomendaToAttach = em.getReference(encomendaCollectionNewEncomendaToAttach.getClass(), encomendaCollectionNewEncomendaToAttach.getIdEncomenda());
                attachedEncomendaCollectionNew.add(encomendaCollectionNewEncomendaToAttach);
            }
            encomendaCollectionNew = attachedEncomendaCollectionNew;
            cliente.setEncomendaCollection(encomendaCollectionNew);
            cliente = em.merge(cliente);
            if (fkEnderecoEntregaOld != null && !fkEnderecoEntregaOld.equals(fkEnderecoEntregaNew)) {
                fkEnderecoEntregaOld.getClienteCollection().remove(cliente);
                fkEnderecoEntregaOld = em.merge(fkEnderecoEntregaOld);
            }
            if (fkEnderecoEntregaNew != null && !fkEnderecoEntregaNew.equals(fkEnderecoEntregaOld)) {
                fkEnderecoEntregaNew.getClienteCollection().add(cliente);
                fkEnderecoEntregaNew = em.merge(fkEnderecoEntregaNew);
            }
            for (Venda vendaCollectionOldVenda : vendaCollectionOld) {
                if (!vendaCollectionNew.contains(vendaCollectionOldVenda)) {
                    vendaCollectionOldVenda.setFkCliente(null);
                    vendaCollectionOldVenda = em.merge(vendaCollectionOldVenda);
                }
            }
            for (Venda vendaCollectionNewVenda : vendaCollectionNew) {
                if (!vendaCollectionOld.contains(vendaCollectionNewVenda)) {
                    Cliente oldFkClienteOfVendaCollectionNewVenda = vendaCollectionNewVenda.getFkCliente();
                    vendaCollectionNewVenda.setFkCliente(cliente);
                    vendaCollectionNewVenda = em.merge(vendaCollectionNewVenda);
                    if (oldFkClienteOfVendaCollectionNewVenda != null && !oldFkClienteOfVendaCollectionNewVenda.equals(cliente)) {
                        oldFkClienteOfVendaCollectionNewVenda.getVendaCollection().remove(vendaCollectionNewVenda);
                        oldFkClienteOfVendaCollectionNewVenda = em.merge(oldFkClienteOfVendaCollectionNewVenda);
                    }
                }
            }
            for (Pedido pedidoCollectionOldPedido : pedidoCollectionOld) {
                if (!pedidoCollectionNew.contains(pedidoCollectionOldPedido)) {
                    pedidoCollectionOldPedido.setFkCliente(null);
                    pedidoCollectionOldPedido = em.merge(pedidoCollectionOldPedido);
                }
            }
            for (Pedido pedidoCollectionNewPedido : pedidoCollectionNew) {
                if (!pedidoCollectionOld.contains(pedidoCollectionNewPedido)) {
                    Cliente oldFkClienteOfPedidoCollectionNewPedido = pedidoCollectionNewPedido.getFkCliente();
                    pedidoCollectionNewPedido.setFkCliente(cliente);
                    pedidoCollectionNewPedido = em.merge(pedidoCollectionNewPedido);
                    if (oldFkClienteOfPedidoCollectionNewPedido != null && !oldFkClienteOfPedidoCollectionNewPedido.equals(cliente)) {
                        oldFkClienteOfPedidoCollectionNewPedido.getPedidoCollection().remove(pedidoCollectionNewPedido);
                        oldFkClienteOfPedidoCollectionNewPedido = em.merge(oldFkClienteOfPedidoCollectionNewPedido);
                    }
                }
            }
            for (Telefonecliente telefoneclienteCollectionOldTelefonecliente : telefoneclienteCollectionOld) {
                if (!telefoneclienteCollectionNew.contains(telefoneclienteCollectionOldTelefonecliente)) {
                    telefoneclienteCollectionOldTelefonecliente.setFkCliente(null);
                    telefoneclienteCollectionOldTelefonecliente = em.merge(telefoneclienteCollectionOldTelefonecliente);
                }
            }
            for (Telefonecliente telefoneclienteCollectionNewTelefonecliente : telefoneclienteCollectionNew) {
                if (!telefoneclienteCollectionOld.contains(telefoneclienteCollectionNewTelefonecliente)) {
                    Cliente oldFkClienteOfTelefoneclienteCollectionNewTelefonecliente = telefoneclienteCollectionNewTelefonecliente.getFkCliente();
                    telefoneclienteCollectionNewTelefonecliente.setFkCliente(cliente);
                    telefoneclienteCollectionNewTelefonecliente = em.merge(telefoneclienteCollectionNewTelefonecliente);
                    if (oldFkClienteOfTelefoneclienteCollectionNewTelefonecliente != null && !oldFkClienteOfTelefoneclienteCollectionNewTelefonecliente.equals(cliente)) {
                        oldFkClienteOfTelefoneclienteCollectionNewTelefonecliente.getTelefoneclienteCollection().remove(telefoneclienteCollectionNewTelefonecliente);
                        oldFkClienteOfTelefoneclienteCollectionNewTelefonecliente = em.merge(oldFkClienteOfTelefoneclienteCollectionNewTelefonecliente);
                    }
                }
            }
            for (Encomenda encomendaCollectionOldEncomenda : encomendaCollectionOld) {
                if (!encomendaCollectionNew.contains(encomendaCollectionOldEncomenda)) {
                    encomendaCollectionOldEncomenda.setFkCliente(null);
                    encomendaCollectionOldEncomenda = em.merge(encomendaCollectionOldEncomenda);
                }
            }
            for (Encomenda encomendaCollectionNewEncomenda : encomendaCollectionNew) {
                if (!encomendaCollectionOld.contains(encomendaCollectionNewEncomenda)) {
                    Cliente oldFkClienteOfEncomendaCollectionNewEncomenda = encomendaCollectionNewEncomenda.getFkCliente();
                    encomendaCollectionNewEncomenda.setFkCliente(cliente);
                    encomendaCollectionNewEncomenda = em.merge(encomendaCollectionNewEncomenda);
                    if (oldFkClienteOfEncomendaCollectionNewEncomenda != null && !oldFkClienteOfEncomendaCollectionNewEncomenda.equals(cliente)) {
                        oldFkClienteOfEncomendaCollectionNewEncomenda.getEncomendaCollection().remove(encomendaCollectionNewEncomenda);
                        oldFkClienteOfEncomendaCollectionNewEncomenda = em.merge(oldFkClienteOfEncomendaCollectionNewEncomenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getIdCliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            Enderecoentrega fkEnderecoEntrega = cliente.getFkEnderecoEntrega();
            if (fkEnderecoEntrega != null) {
                fkEnderecoEntrega.getClienteCollection().remove(cliente);
                fkEnderecoEntrega = em.merge(fkEnderecoEntrega);
            }
            Collection<Venda> vendaCollection = cliente.getVendaCollection();
            for (Venda vendaCollectionVenda : vendaCollection) {
                vendaCollectionVenda.setFkCliente(null);
                vendaCollectionVenda = em.merge(vendaCollectionVenda);
            }
            Collection<Pedido> pedidoCollection = cliente.getPedidoCollection();
            for (Pedido pedidoCollectionPedido : pedidoCollection) {
                pedidoCollectionPedido.setFkCliente(null);
                pedidoCollectionPedido = em.merge(pedidoCollectionPedido);
            }
            Collection<Telefonecliente> telefoneclienteCollection = cliente.getTelefoneclienteCollection();
            for (Telefonecliente telefoneclienteCollectionTelefonecliente : telefoneclienteCollection) {
                telefoneclienteCollectionTelefonecliente.setFkCliente(null);
                telefoneclienteCollectionTelefonecliente = em.merge(telefoneclienteCollectionTelefonecliente);
            }
            Collection<Encomenda> encomendaCollection = cliente.getEncomendaCollection();
            for (Encomenda encomendaCollectionEncomenda : encomendaCollection) {
                encomendaCollectionEncomenda.setFkCliente(null);
                encomendaCollectionEncomenda = em.merge(encomendaCollectionEncomenda);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
