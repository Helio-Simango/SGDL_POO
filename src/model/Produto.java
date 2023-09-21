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
@Table(name = "produto")
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p"),
    @NamedQuery(name = "Produto.findByIdProduto", query = "SELECT p FROM Produto p WHERE p.idProduto = :idProduto"),
    @NamedQuery(name = "Produto.findByIdCategoriaProduto", query = "SELECT p FROM Produto p WHERE p.idCategoriaProduto = :idCategoriaProduto"),
    @NamedQuery(name = "Produto.findByNomeProduto", query = "SELECT p FROM Produto p WHERE p.nomeProduto = :nomeProduto"),
    @NamedQuery(name = "Produto.findByQuantidadeProduto", query = "SELECT p FROM Produto p WHERE p.quantidadeProduto = :quantidadeProduto"),
    @NamedQuery(name = "Produto.findByPrecoUnitarioProduto", query = "SELECT p FROM Produto p WHERE p.precoUnitarioProduto = :precoUnitarioProduto"),
    @NamedQuery(name = "Produto.findByPrecoCompraProduto", query = "SELECT p FROM Produto p WHERE p.precoCompraProduto = :precoCompraProduto"),
    @NamedQuery(name = "Produto.findByDataCompraProduto", query = "SELECT p FROM Produto p WHERE p.dataCompraProduto = :dataCompraProduto"),
    @NamedQuery(name = "Produto.findByDataValidadeProduto", query = "SELECT p FROM Produto p WHERE p.dataValidadeProduto = :dataValidadeProduto")})
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProduto")
    private Integer idProduto;
    @Column(name = "idCategoriaProduto")
    private Integer idCategoriaProduto;
    @Basic(optional = false)
    @Column(name = "nomeProduto")
    private String nomeProduto;
    @Basic(optional = false)
    @Column(name = "quantidadeProduto")
    private int quantidadeProduto;
    @Basic(optional = false)
    @Column(name = "precoUnitarioProduto")
    private double precoUnitarioProduto;
    @Basic(optional = false)
    @Column(name = "precoCompraProduto")
    private double precoCompraProduto;
    @Basic(optional = false)
    @Column(name = "dataCompraProduto")
    @Temporal(TemporalType.DATE)
    private Date dataCompraProduto;
    @Basic(optional = false)
    @Column(name = "dataValidadeProduto")
    @Temporal(TemporalType.DATE)
    private Date dataValidadeProduto;
    @JoinColumn(name = "fkItemCardapio", referencedColumnName = "idItemCardapio")
    @ManyToOne
    private Itemcardapio fkItemCardapio;

    public Produto() {
    }

    public Produto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Produto(Integer idProduto, String nomeProduto, int quantidadeProduto, int precoUnitarioProduto, int precoCompraProduto, Date dataCompraProduto, Date dataValidadeProduto) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.quantidadeProduto = quantidadeProduto;
        this.precoUnitarioProduto = precoUnitarioProduto;
        this.precoCompraProduto = precoCompraProduto;
        this.dataCompraProduto = dataCompraProduto;
        this.dataValidadeProduto = dataValidadeProduto;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getIdCategoriaProduto() {
        return idCategoriaProduto;
    }

    public void setIdCategoriaProduto(Integer idCategoriaProduto) {
        this.idCategoriaProduto = idCategoriaProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(int quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }

    public double getPrecoUnitarioProduto() {
        return precoUnitarioProduto;
    }

    public void setPrecoUnitarioProduto(double precoUnitarioProduto) {
        this.precoUnitarioProduto = precoUnitarioProduto;
    }

    public double getPrecoCompraProduto() {
        return precoCompraProduto;
    }

    public void setPrecoCompraProduto(double precoCompraProduto) {
        this.precoCompraProduto = precoCompraProduto;
    }

    public Date getDataCompraProduto() {
        return dataCompraProduto;
    }

    public void setDataCompraProduto(Date dataCompraProduto) {
        this.dataCompraProduto = dataCompraProduto;
    }

    public Date getDataValidadeProduto() {
        return dataValidadeProduto;
    }

    public void setDataValidadeProduto(Date dataValidadeProduto) {
        this.dataValidadeProduto = dataValidadeProduto;
    }

    public Itemcardapio getFkItemCardapio() {
        return fkItemCardapio;
    }

    public void setFkItemCardapio(Itemcardapio fkItemCardapio) {
        this.fkItemCardapio = fkItemCardapio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProduto != null ? idProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.idProduto == null && other.idProduto != null) || (this.idProduto != null && !this.idProduto.equals(other.idProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Produto[ idProduto=" + idProduto + " ]";
    }
    
}
