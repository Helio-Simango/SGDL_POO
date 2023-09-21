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
import model.Encomenda;
import model.Pedido;
import model.Produto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Itemcardapio;

/**
 *
 * @author helio
 */
public class ItemcardapioJpaController implements Serializable {

    public ItemcardapioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Itemcardapio itemcardapio) {
        if (itemcardapio.getProdutoCollection() == null) {
            itemcardapio.setProdutoCollection(new ArrayList<Produto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Encomenda fkEncomenda = itemcardapio.getFkEncomenda();
            if (fkEncomenda != null) {
                fkEncomenda = em.getReference(fkEncomenda.getClass(), fkEncomenda.getIdEncomenda());
                itemcardapio.setFkEncomenda(fkEncomenda);
            }
            Pedido fkPedido = itemcardapio.getFkPedido();
            if (fkPedido != null) {
                fkPedido = em.getReference(fkPedido.getClass(), fkPedido.getIdPedido());
                itemcardapio.setFkPedido(fkPedido);
            }
            Collection<Produto> attachedProdutoCollection = new ArrayList<Produto>();
            for (Produto produtoCollectionProdutoToAttach : itemcardapio.getProdutoCollection()) {
                produtoCollectionProdutoToAttach = em.getReference(produtoCollectionProdutoToAttach.getClass(), produtoCollectionProdutoToAttach.getIdProduto());
                attachedProdutoCollection.add(produtoCollectionProdutoToAttach);
            }
            itemcardapio.setProdutoCollection(attachedProdutoCollection);
            em.persist(itemcardapio);
            if (fkEncomenda != null) {
                fkEncomenda.getItemcardapioCollection().add(itemcardapio);
                fkEncomenda = em.merge(fkEncomenda);
            }
            if (fkPedido != null) {
                fkPedido.getItemcardapioCollection().add(itemcardapio);
                fkPedido = em.merge(fkPedido);
            }
            for (Produto produtoCollectionProduto : itemcardapio.getProdutoCollection()) {
                Itemcardapio oldFkItemCardapioOfProdutoCollectionProduto = produtoCollectionProduto.getFkItemCardapio();
                produtoCollectionProduto.setFkItemCardapio(itemcardapio);
                produtoCollectionProduto = em.merge(produtoCollectionProduto);
                if (oldFkItemCardapioOfProdutoCollectionProduto != null) {
                    oldFkItemCardapioOfProdutoCollectionProduto.getProdutoCollection().remove(produtoCollectionProduto);
                    oldFkItemCardapioOfProdutoCollectionProduto = em.merge(oldFkItemCardapioOfProdutoCollectionProduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Itemcardapio itemcardapio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Itemcardapio persistentItemcardapio = em.find(Itemcardapio.class, itemcardapio.getIdItemCardapio());
            Encomenda fkEncomendaOld = persistentItemcardapio.getFkEncomenda();
            Encomenda fkEncomendaNew = itemcardapio.getFkEncomenda();
            Pedido fkPedidoOld = persistentItemcardapio.getFkPedido();
            Pedido fkPedidoNew = itemcardapio.getFkPedido();
            Collection<Produto> produtoCollectionOld = persistentItemcardapio.getProdutoCollection();
            Collection<Produto> produtoCollectionNew = itemcardapio.getProdutoCollection();
            if (fkEncomendaNew != null) {
                fkEncomendaNew = em.getReference(fkEncomendaNew.getClass(), fkEncomendaNew.getIdEncomenda());
                itemcardapio.setFkEncomenda(fkEncomendaNew);
            }
            if (fkPedidoNew != null) {
                fkPedidoNew = em.getReference(fkPedidoNew.getClass(), fkPedidoNew.getIdPedido());
                itemcardapio.setFkPedido(fkPedidoNew);
            }
            Collection<Produto> attachedProdutoCollectionNew = new ArrayList<Produto>();
            for (Produto produtoCollectionNewProdutoToAttach : produtoCollectionNew) {
                produtoCollectionNewProdutoToAttach = em.getReference(produtoCollectionNewProdutoToAttach.getClass(), produtoCollectionNewProdutoToAttach.getIdProduto());
                attachedProdutoCollectionNew.add(produtoCollectionNewProdutoToAttach);
            }
            produtoCollectionNew = attachedProdutoCollectionNew;
            itemcardapio.setProdutoCollection(produtoCollectionNew);
            itemcardapio = em.merge(itemcardapio);
            if (fkEncomendaOld != null && !fkEncomendaOld.equals(fkEncomendaNew)) {
                fkEncomendaOld.getItemcardapioCollection().remove(itemcardapio);
                fkEncomendaOld = em.merge(fkEncomendaOld);
            }
            if (fkEncomendaNew != null && !fkEncomendaNew.equals(fkEncomendaOld)) {
                fkEncomendaNew.getItemcardapioCollection().add(itemcardapio);
                fkEncomendaNew = em.merge(fkEncomendaNew);
            }
            if (fkPedidoOld != null && !fkPedidoOld.equals(fkPedidoNew)) {
                fkPedidoOld.getItemcardapioCollection().remove(itemcardapio);
                fkPedidoOld = em.merge(fkPedidoOld);
            }
            if (fkPedidoNew != null && !fkPedidoNew.equals(fkPedidoOld)) {
                fkPedidoNew.getItemcardapioCollection().add(itemcardapio);
                fkPedidoNew = em.merge(fkPedidoNew);
            }
            for (Produto produtoCollectionOldProduto : produtoCollectionOld) {
                if (!produtoCollectionNew.contains(produtoCollectionOldProduto)) {
                    produtoCollectionOldProduto.setFkItemCardapio(null);
                    produtoCollectionOldProduto = em.merge(produtoCollectionOldProduto);
                }
            }
            for (Produto produtoCollectionNewProduto : produtoCollectionNew) {
                if (!produtoCollectionOld.contains(produtoCollectionNewProduto)) {
                    Itemcardapio oldFkItemCardapioOfProdutoCollectionNewProduto = produtoCollectionNewProduto.getFkItemCardapio();
                    produtoCollectionNewProduto.setFkItemCardapio(itemcardapio);
                    produtoCollectionNewProduto = em.merge(produtoCollectionNewProduto);
                    if (oldFkItemCardapioOfProdutoCollectionNewProduto != null && !oldFkItemCardapioOfProdutoCollectionNewProduto.equals(itemcardapio)) {
                        oldFkItemCardapioOfProdutoCollectionNewProduto.getProdutoCollection().remove(produtoCollectionNewProduto);
                        oldFkItemCardapioOfProdutoCollectionNewProduto = em.merge(oldFkItemCardapioOfProdutoCollectionNewProduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = itemcardapio.getIdItemCardapio();
                if (findItemcardapio(id) == null) {
                    throw new NonexistentEntityException("The itemcardapio with id " + id + " no longer exists.");
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
            Itemcardapio itemcardapio;
            try {
                itemcardapio = em.getReference(Itemcardapio.class, id);
                itemcardapio.getIdItemCardapio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemcardapio with id " + id + " no longer exists.", enfe);
            }
            Encomenda fkEncomenda = itemcardapio.getFkEncomenda();
            if (fkEncomenda != null) {
                fkEncomenda.getItemcardapioCollection().remove(itemcardapio);
                fkEncomenda = em.merge(fkEncomenda);
            }
            Pedido fkPedido = itemcardapio.getFkPedido();
            if (fkPedido != null) {
                fkPedido.getItemcardapioCollection().remove(itemcardapio);
                fkPedido = em.merge(fkPedido);
            }
            Collection<Produto> produtoCollection = itemcardapio.getProdutoCollection();
            for (Produto produtoCollectionProduto : produtoCollection) {
                produtoCollectionProduto.setFkItemCardapio(null);
                produtoCollectionProduto = em.merge(produtoCollectionProduto);
            }
            em.remove(itemcardapio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Itemcardapio> findItemcardapioEntities() {
        return findItemcardapioEntities(true, -1, -1);
    }

    public List<Itemcardapio> findItemcardapioEntities(int maxResults, int firstResult) {
        return findItemcardapioEntities(false, maxResults, firstResult);
    }

    private List<Itemcardapio> findItemcardapioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Itemcardapio.class));
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

    public Itemcardapio findItemcardapio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Itemcardapio.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemcardapioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Itemcardapio> rt = cq.from(Itemcardapio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
