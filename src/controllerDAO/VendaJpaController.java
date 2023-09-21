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
import model.Encomenda;
import model.Pedido;
import model.Venda;

/**
 *
 * @author helio
 */
public class VendaJpaController implements Serializable {

    public VendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venda venda) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente fkCliente = venda.getFkCliente();
            if (fkCliente != null) {
                fkCliente = em.getReference(fkCliente.getClass(), fkCliente.getIdCliente());
                venda.setFkCliente(fkCliente);
            }
            Encomenda fkEncomenda = venda.getFkEncomenda();
            if (fkEncomenda != null) {
                fkEncomenda = em.getReference(fkEncomenda.getClass(), fkEncomenda.getIdEncomenda());
                venda.setFkEncomenda(fkEncomenda);
            }
            Pedido fkPedido = venda.getFkPedido();
            if (fkPedido != null) {
                fkPedido = em.getReference(fkPedido.getClass(), fkPedido.getIdPedido());
                venda.setFkPedido(fkPedido);
            }
            em.persist(venda);
            if (fkCliente != null) {
                fkCliente.getVendaCollection().add(venda);
                fkCliente = em.merge(fkCliente);
            }
            if (fkEncomenda != null) {
                fkEncomenda.getVendaCollection().add(venda);
                fkEncomenda = em.merge(fkEncomenda);
            }
            if (fkPedido != null) {
                fkPedido.getVendaCollection().add(venda);
                fkPedido = em.merge(fkPedido);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venda venda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venda persistentVenda = em.find(Venda.class, venda.getIdVenda());
            Cliente fkClienteOld = persistentVenda.getFkCliente();
            Cliente fkClienteNew = venda.getFkCliente();
            Encomenda fkEncomendaOld = persistentVenda.getFkEncomenda();
            Encomenda fkEncomendaNew = venda.getFkEncomenda();
            Pedido fkPedidoOld = persistentVenda.getFkPedido();
            Pedido fkPedidoNew = venda.getFkPedido();
            if (fkClienteNew != null) {
                fkClienteNew = em.getReference(fkClienteNew.getClass(), fkClienteNew.getIdCliente());
                venda.setFkCliente(fkClienteNew);
            }
            if (fkEncomendaNew != null) {
                fkEncomendaNew = em.getReference(fkEncomendaNew.getClass(), fkEncomendaNew.getIdEncomenda());
                venda.setFkEncomenda(fkEncomendaNew);
            }
            if (fkPedidoNew != null) {
                fkPedidoNew = em.getReference(fkPedidoNew.getClass(), fkPedidoNew.getIdPedido());
                venda.setFkPedido(fkPedidoNew);
            }
            venda = em.merge(venda);
            if (fkClienteOld != null && !fkClienteOld.equals(fkClienteNew)) {
                fkClienteOld.getVendaCollection().remove(venda);
                fkClienteOld = em.merge(fkClienteOld);
            }
            if (fkClienteNew != null && !fkClienteNew.equals(fkClienteOld)) {
                fkClienteNew.getVendaCollection().add(venda);
                fkClienteNew = em.merge(fkClienteNew);
            }
            if (fkEncomendaOld != null && !fkEncomendaOld.equals(fkEncomendaNew)) {
                fkEncomendaOld.getVendaCollection().remove(venda);
                fkEncomendaOld = em.merge(fkEncomendaOld);
            }
            if (fkEncomendaNew != null && !fkEncomendaNew.equals(fkEncomendaOld)) {
                fkEncomendaNew.getVendaCollection().add(venda);
                fkEncomendaNew = em.merge(fkEncomendaNew);
            }
            if (fkPedidoOld != null && !fkPedidoOld.equals(fkPedidoNew)) {
                fkPedidoOld.getVendaCollection().remove(venda);
                fkPedidoOld = em.merge(fkPedidoOld);
            }
            if (fkPedidoNew != null && !fkPedidoNew.equals(fkPedidoOld)) {
                fkPedidoNew.getVendaCollection().add(venda);
                fkPedidoNew = em.merge(fkPedidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venda.getIdVenda();
                if (findVenda(id) == null) {
                    throw new NonexistentEntityException("The venda with id " + id + " no longer exists.");
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
            Venda venda;
            try {
                venda = em.getReference(Venda.class, id);
                venda.getIdVenda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venda with id " + id + " no longer exists.", enfe);
            }
            Cliente fkCliente = venda.getFkCliente();
            if (fkCliente != null) {
                fkCliente.getVendaCollection().remove(venda);
                fkCliente = em.merge(fkCliente);
            }
            Encomenda fkEncomenda = venda.getFkEncomenda();
            if (fkEncomenda != null) {
                fkEncomenda.getVendaCollection().remove(venda);
                fkEncomenda = em.merge(fkEncomenda);
            }
            Pedido fkPedido = venda.getFkPedido();
            if (fkPedido != null) {
                fkPedido.getVendaCollection().remove(venda);
                fkPedido = em.merge(fkPedido);
            }
            em.remove(venda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venda> findVendaEntities() {
        return findVendaEntities(true, -1, -1);
    }

    public List<Venda> findVendaEntities(int maxResults, int firstResult) {
        return findVendaEntities(false, maxResults, firstResult);
    }

    private List<Venda> findVendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venda.class));
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

    public Venda findVenda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venda.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venda> rt = cq.from(Venda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
