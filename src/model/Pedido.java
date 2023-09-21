/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author helio
 */
@Entity
@Table(name = "pedido")
@NamedQueries({
    @NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p"),
    @NamedQuery(name = "Pedido.findByIdPedido", query = "SELECT p FROM Pedido p WHERE p.idPedido = :idPedido"),
    @NamedQuery(name = "Pedido.findByDataPedido", query = "SELECT p FROM Pedido p WHERE p.dataPedido = :dataPedido"),
    @NamedQuery(name = "Pedido.findBySituacaoPedido", query = "SELECT p FROM Pedido p WHERE p.situacaoPedido = :situacaoPedido")})
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPedido")
    private Integer idPedido;
    @Basic(optional = false)
    @Column(name = "dataPedido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPedido;
    @Basic(optional = false)
    @Column(name = "situacaoPedido")
    private String situacaoPedido;
    @OneToMany(mappedBy = "fkPedido")
    private Collection<Venda> vendaCollection;
    @JoinColumn(name = "fkCliente", referencedColumnName = "idCliente")
    @ManyToOne
    private Cliente fkCliente;
    @JoinColumn(name = "fkEmpregado", referencedColumnName = "idEmpregado")
    @ManyToOne
    private Empregado fkEmpregado;
    @OneToMany(mappedBy = "fkPedido")
    private Collection<Itemcardapio> itemcardapioCollection;

    public Pedido() {
    }

    public Pedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Pedido(Integer idPedido, Date dataPedido, String situacaoPedido) {
        this.idPedido = idPedido;
        this.dataPedido = dataPedido;
        this.situacaoPedido = situacaoPedido;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getSituacaoPedido() {
        return situacaoPedido;
    }

    public void setSituacaoPedido(String situacaoPedido) {
        this.situacaoPedido = situacaoPedido;
    }

    public Collection<Venda> getVendaCollection() {
        return vendaCollection;
    }

    public void setVendaCollection(Collection<Venda> vendaCollection) {
        this.vendaCollection = vendaCollection;
    }

    public Cliente getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Cliente fkCliente) {
        this.fkCliente = fkCliente;
    }

    public Empregado getFkEmpregado() {
        return fkEmpregado;
    }

    public void setFkEmpregado(Empregado fkEmpregado) {
        this.fkEmpregado = fkEmpregado;
    }

    public Collection<Itemcardapio> getItemcardapioCollection() {
        return itemcardapioCollection;
    }

    public void setItemcardapioCollection(Collection<Itemcardapio> itemcardapioCollection) {
        this.itemcardapioCollection = itemcardapioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPedido != null ? idPedido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.idPedido == null && other.idPedido != null) || (this.idPedido != null && !this.idPedido.equals(other.idPedido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Pedido[ idPedido=" + idPedido + " ]";
    }
    
}
