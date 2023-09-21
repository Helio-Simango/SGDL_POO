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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author helio
 */
@Entity
@Table(name = "telefonecliente")
@NamedQueries({
    @NamedQuery(name = "Telefonecliente.findAll", query = "SELECT t FROM Telefonecliente t"),
    @NamedQuery(name = "Telefonecliente.findByIdTelefoneCliente", query = "SELECT t FROM Telefonecliente t WHERE t.idTelefoneCliente = :idTelefoneCliente"),
    @NamedQuery(name = "Telefonecliente.findByContacto", query = "SELECT t FROM Telefonecliente t WHERE t.contacto = :contacto")})
public class Telefonecliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTelefoneCliente")
    private Integer idTelefoneCliente;
    @Column(name = "contacto")
    private Integer contacto;
    @JoinColumn(name = "fkCliente", referencedColumnName = "idCliente")
    @ManyToOne
    private Cliente fkCliente;

    public Telefonecliente() {
    }

    public Telefonecliente(Integer idTelefoneCliente) {
        this.idTelefoneCliente = idTelefoneCliente;
    }

    public Integer getIdTelefoneCliente() {
        return idTelefoneCliente;
    }

    public void setIdTelefoneCliente(Integer idTelefoneCliente) {
        this.idTelefoneCliente = idTelefoneCliente;
    }

    public Integer getContacto() {
        return contacto;
    }

    public void setContacto(Integer contacto) {
        this.contacto = contacto;
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
        hash += (idTelefoneCliente != null ? idTelefoneCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Telefonecliente)) {
            return false;
        }
        Telefonecliente other = (Telefonecliente) object;
        if ((this.idTelefoneCliente == null && other.idTelefoneCliente != null) || (this.idTelefoneCliente != null && !this.idTelefoneCliente.equals(other.idTelefoneCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Telefonecliente[ idTelefoneCliente=" + idTelefoneCliente + " ]";
    }
    
}
