/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author helio
 */
@Entity
@Table(name = "venda")
@NamedQueries({
    @NamedQuery(name = "Venda.findAll", query = "SELECT v FROM Venda v"),
    @NamedQuery(name = "Venda.findByIdVenda", query = "SELECT v FROM Venda v WHERE v.idVenda = :idVenda"),
    @NamedQuery(name = "Venda.findBySituacao", query = "SELECT v FROM Venda v WHERE v.situacao = :situacao"),
    @NamedQuery(name = "Venda.findByDataVenda", query = "SELECT v FROM Venda v WHERE v.dataVenda = :dataVenda"),
    @NamedQuery(name = "Venda.findByValorTotalVenda", query = "SELECT v FROM Venda v WHERE v.valorTotalVenda = :valorTotalVenda")})
public class Venda implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montante")
    private double montante;
    @Column(name = "trocos")
    private double trocos;
    @Column(name = "tipoDepagamento")
    private String tipoDepagamento;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVenda")
    private Integer idVenda;
    @Basic(optional = false)
    @Column(name = "situacao")
    private String situacao;
    @Basic(optional = false)
    @Column(name = "dataVenda")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVenda;
    @Basic(optional = false)
    @Column(name = "valorTotalVenda")
    private double valorTotalVenda;
    @JoinColumn(name = "fkCliente", referencedColumnName = "idCliente")
    @ManyToOne
    private Cliente fkCliente;
    @JoinColumn(name = "fkEncomenda", referencedColumnName = "idEncomenda")
    @ManyToOne
    private Encomenda fkEncomenda;
    @JoinColumn(name = "fkPedido", referencedColumnName = "idPedido")
    @ManyToOne
    private Pedido fkPedido;

    public Venda() {
    }

    public Venda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public Venda(Integer idVenda, String situacao, Date dataVenda, int valorTotalVenda) {
        this.idVenda = idVenda;
        this.situacao = situacao;
        this.dataVenda = dataVenda;
        this.valorTotalVenda = valorTotalVenda;
    }

    public Integer getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public double getValorTotalVenda() {
        return valorTotalVenda;
    }

    public void setValorTotalVenda(double valorTotalVenda) {
        this.valorTotalVenda = valorTotalVenda;
    }

    public Cliente getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Cliente fkCliente) {
        this.fkCliente = fkCliente;
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
    
    public double getMontante() {
        return montante;
    }

    public void setMontante(double montante) {
        this.montante = montante;
    }

    public double getTrocos() {
        return trocos;
    }

    public void setTrocos(double trocos) {
        this.trocos = trocos;
    }

    public String getTipoDepagamento() {
        return tipoDepagamento;
    }

    public void setTipoDepagamento(String tipoDepagamento) {
        this.tipoDepagamento = tipoDepagamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVenda != null ? idVenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venda)) {
            return false;
        }
        Venda other = (Venda) object;
        if ((this.idVenda == null && other.idVenda != null) || (this.idVenda != null && !this.idVenda.equals(other.idVenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Venda[ idVenda=" + idVenda + " ]";
    }
    
}
