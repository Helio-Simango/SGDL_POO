/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author helio
 */
@Entity
@Table(name = "categoriaproduto")
@NamedQueries({
    @NamedQuery(name = "Categoriaproduto.findAll", query = "SELECT c FROM Categoriaproduto c"),
    @NamedQuery(name = "Categoriaproduto.findByIdCategoriaProduto", query = "SELECT c FROM Categoriaproduto c WHERE c.idCategoriaProduto = :idCategoriaProduto"),
    @NamedQuery(name = "Categoriaproduto.findByCategoria", query = "SELECT c FROM Categoriaproduto c WHERE c.categoria = :categoria"),
    @NamedQuery(name = "Categoriaproduto.findByDescricao", query = "SELECT c FROM Categoriaproduto c WHERE c.descricao = :descricao")})
public class Categoriaproduto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCategoriaProduto")
    private Integer idCategoriaProduto;
    
    @Basic(optional = false)
    @Column(name = "categoria")
    private String categoria;
    
    @Column(name = "descricao")
    private String descricao;

    public Categoriaproduto() {
    }

    public Categoriaproduto(Integer idCategoriaProduto) {
        this.idCategoriaProduto = idCategoriaProduto;
    }

    public Categoriaproduto(Integer idCategoriaProduto, String categoria) {
        this.idCategoriaProduto = idCategoriaProduto;
        this.categoria = categoria;
    }

    public Integer getIdCategoriaProduto() {
        return idCategoriaProduto;
    }

    public void setIdCategoriaProduto(Integer idCategoriaProduto) {
        this.idCategoriaProduto = idCategoriaProduto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoriaProduto != null ? idCategoriaProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoriaproduto)) {
            return false;
        }
        Categoriaproduto other = (Categoriaproduto) object;
        if ((this.idCategoriaProduto == null && other.idCategoriaProduto != null) || (this.idCategoriaProduto != null && !this.idCategoriaProduto.equals(other.idCategoriaProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Categoriaproduto[ idCategoriaProduto=" + idCategoriaProduto + " ]";
    }
    
}
