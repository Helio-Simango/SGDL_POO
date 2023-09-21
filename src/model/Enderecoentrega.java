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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author helio
 */
@Entity
@Table(name = "enderecoentrega")
@NamedQueries({
    @NamedQuery(name = "Enderecoentrega.findAll", query = "SELECT e FROM Enderecoentrega e"),
    @NamedQuery(name = "Enderecoentrega.findByIdEnderecoEntrega", query = "SELECT e FROM Enderecoentrega e WHERE e.idEnderecoEntrega = :idEnderecoEntrega")})
public class Enderecoentrega implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEnderecoEntrega")
    private Integer idEnderecoEntrega;
    @Basic(optional = false)
    @Lob
    @Column(name = "endereco")
    private String endereco;
    @OneToMany(mappedBy = "fkEnderecoEntrega")
    private Collection<Cliente> clienteCollection;

    public Enderecoentrega() {
    }

    public Enderecoentrega(Integer idEnderecoEntrega) {
        this.idEnderecoEntrega = idEnderecoEntrega;
    }

    public Enderecoentrega(Integer idEnderecoEntrega, String endereco) {
        this.idEnderecoEntrega = idEnderecoEntrega;
        this.endereco = endereco;
    }

    public Integer getIdEnderecoEntrega() {
        return idEnderecoEntrega;
    }

    public void setIdEnderecoEntrega(Integer idEnderecoEntrega) {
        this.idEnderecoEntrega = idEnderecoEntrega;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Collection<Cliente> getClienteCollection() {
        return clienteCollection;
    }

    public void setClienteCollection(Collection<Cliente> clienteCollection) {
        this.clienteCollection = clienteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEnderecoEntrega != null ? idEnderecoEntrega.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Enderecoentrega)) {
            return false;
        }
        Enderecoentrega other = (Enderecoentrega) object;
        if ((this.idEnderecoEntrega == null && other.idEnderecoEntrega != null) || (this.idEnderecoEntrega != null && !this.idEnderecoEntrega.equals(other.idEnderecoEntrega))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Enderecoentrega[ idEnderecoEntrega=" + idEnderecoEntrega + " ]";
    }
    
}
