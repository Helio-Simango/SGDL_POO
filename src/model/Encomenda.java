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
@Table(name = "encomenda")
@NamedQueries({
    @NamedQuery(name = "Encomenda.findAll", query = "SELECT e FROM Encomenda e"),
    @NamedQuery(name = "Encomenda.findByIdEncomenda", query = "SELECT e FROM Encomenda e WHERE e.idEncomenda = :idEncomenda"),
    @NamedQuery(name = "Encomenda.findBySituacao", query = "SELECT e FROM Encomenda e WHERE e.situacao = :situacao"),
    @NamedQuery(name = "Encomenda.findByCustoEntrega", query = "SELECT e FROM Encomenda e WHERE e.custoEntrega = :custoEntrega")})
public class Encomenda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEncomenda")
    private Integer idEncomenda;
    @Basic(optional = false)
    @Column(name = "situacao")
    private String situacao;
    @Basic(optional = false)
    @Column(name = "custoEntrega")
    private double custoEntrega;
    @OneToMany(mappedBy = "fkEncomenda")
    private Collection<Venda> vendaCollection;
    @OneToMany(mappedBy = "fkEncomenda")
    private Collection<Itemcardapio> itemcardapioCollection;
    @JoinColumn(name = "fkCliente", referencedColumnName = "idCliente")
    @ManyToOne
    private Cliente fkCliente;

    public Encomenda() {
    }

    public Encomenda(Integer idEncomenda) {
        this.idEncomenda = idEncomenda;
    }

    public Encomenda(Integer idEncomenda, String situacao, int custoEntrega) {
        this.idEncomenda = idEncomenda;
        this.situacao = situacao;
        this.custoEntrega = custoEntrega;
    }

    public Integer getIdEncomenda() {
        return idEncomenda;
    }

    public void setIdEncomenda(Integer idEncomenda) {
        this.idEncomenda = idEncomenda;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public double getCustoEntrega() {
        return custoEntrega;
    }

    public void setCustoEntrega(double custoEntrega) {
        this.custoEntrega = custoEntrega;
    }

    public Collection<Venda> getVendaCollection() {
        return vendaCollection;
    }

    public void setVendaCollection(Collection<Venda> vendaCollection) {
        this.vendaCollection = vendaCollection;
    }

    public Collection<Itemcardapio> getItemcardapioCollection() {
        return itemcardapioCollection;
    }

    public void setItemcardapioCollection(Collection<Itemcardapio> itemcardapioCollection) {
        this.itemcardapioCollection = itemcardapioCollection;
    }

    public Cliente getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Cliente fkCliente) {
        this.fkCliente = fkCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEncomenda != null ? idEncomenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Encomenda)) {
            return false;
        }
        Encomenda other = (Encomenda) object;
        if ((this.idEncomenda == null && other.idEncomenda != null) || (this.idEncomenda != null && !this.idEncomenda.equals(other.idEncomenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Encomenda[ idEncomenda=" + idEncomenda + " ]";
    }
    
}
