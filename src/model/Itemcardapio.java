/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author helio
 */
@Entity
@Table(name = "itemcardapio")
@NamedQueries({
    @NamedQuery(name = "Itemcardapio.findAll", query = "SELECT i FROM Itemcardapio i"),
    @NamedQuery(name = "Itemcardapio.findByIdItemCardapio", query = "SELECT i FROM Itemcardapio i WHERE i.idItemCardapio = :idItemCardapio"),
    @NamedQuery(name = "Itemcardapio.findByNomeItem", query = "SELECT i FROM Itemcardapio i WHERE i.nomeItem = :nomeItem"),
    @NamedQuery(name = "Itemcardapio.findByPrecoUnitarioCardapio", query = "SELECT i FROM Itemcardapio i WHERE i.precoUnitarioCardapio = :precoUnitarioCardapio")})
public class Itemcardapio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idItemCardapio")
    private Integer idItemCardapio;
    @Basic(optional = false)
    @Column(name = "nomeItem")
    private String nomeItem;
    @Basic(optional = false)
    @Column(name = "precoUnitarioCardapio")
    private double precoUnitarioCardapio;
    @OneToMany(mappedBy = "fkItemCardapio")
    private Collection<Produto> produtoCollection;
    @JoinColumn(name = "fkEncomenda", referencedColumnName = "idEncomenda")
    @ManyToOne
    private Encomenda fkEncomenda;
    @JoinColumn(name = "fkPedido", referencedColumnName = "idPedido")
    @ManyToOne
    private Pedido fkPedido;

    public Itemcardapio() {
    }

    public Itemcardapio(Integer idItemCardapio) {
        this.idItemCardapio = idItemCardapio;
    }

    public Itemcardapio(Integer idItemCardapio, String nomeItem, int precoUnitarioCardapio) {
        this.idItemCardapio = idItemCardapio;
        this.nomeItem = nomeItem;
        this.precoUnitarioCardapio = precoUnitarioCardapio;
    }

    public Integer getIdItemCardapio() {
        return idItemCardapio;
    }

    public void setIdItemCardapio(Integer idItemCardapio) {
        this.idItemCardapio = idItemCardapio;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public double getPrecoUnitarioCardapio() {
        return precoUnitarioCardapio;
    }

    public void setPrecoUnitarioCardapio(double precoUnitarioCardapio) {
        this.precoUnitarioCardapio = precoUnitarioCardapio;
    }

    public Collection<Produto> getProdutoCollection() {
        return produtoCollection;
    }

    public void setProdutoCollection(Collection<Produto> produtoCollection) {
        this.produtoCollection = produtoCollection;
    }

    public Encomenda getFkEncomenda() {
        return fkEncomenda;
    }

    public void setFkEncomenda(Encomenda fkEncomenda) {
        this.fkEncomenda = fkEncomenda;
    }

    public Pedido getFkPedido() {
        return fkPedido;
    }

    public void setFkPedido(Pedido fkPedido) {
        this.fkPedido = fkPedido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idItemCardapio != null ? idItemCardapio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Itemcardapio)) {
            return false;
        }
        Itemcardapio other = (Itemcardapio) object;
        if ((this.idItemCardapio == null && other.idItemCardapio != null) || (this.idItemCardapio != null && !this.idItemCardapio.equals(other.idItemCardapio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Itemcardapio[ idItemCardapio=" + idItemCardapio + " ]";
    }
    
}
