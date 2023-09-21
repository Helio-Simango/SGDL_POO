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
@Table(name = "telefone_funcionario")
@NamedQueries({
    @NamedQuery(name = "TelefoneFuncionario.findAll", query = "SELECT t FROM TelefoneFuncionario t"),
    @NamedQuery(name = "TelefoneFuncionario.findByIdTelefoneCli", query = "SELECT t FROM TelefoneFuncionario t WHERE t.idTelefoneCli = :idTelefoneCli"),
    @NamedQuery(name = "TelefoneFuncionario.findByContacto", query = "SELECT t FROM TelefoneFuncionario t WHERE t.contacto = :contacto")})
public class TelefoneFuncionario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_telefone_cli")
    private Integer idTelefoneCli;
    @Basic(optional = false)
    @Column(name = "contacto")
    private int contacto;
    @JoinColumn(name = "fkEmpregado", referencedColumnName = "idEmpregado")
    @ManyToOne
    private Empregado fkEmpregado;

    public TelefoneFuncionario() {
    }

    public TelefoneFuncionario(Integer idTelefoneCli) {
        this.idTelefoneCli = idTelefoneCli;
    }

    public TelefoneFuncionario(Integer idTelefoneCli, int contacto) {
        this.idTelefoneCli = idTelefoneCli;
        this.contacto = contacto;
    }

    public Integer getIdTelefoneCli() {
        return idTelefoneCli;
    }

    public void setIdTelefoneCli(Integer idTelefoneCli) {
        this.idTelefoneCli = idTelefoneCli;
    }

    public int getContacto() {
        return contacto;
    }

    public void setContacto(int contacto) {
        this.contacto = contacto;
    }

    public Empregado getFkEmpregado() {
        return fkEmpregado;
    }

    public void setFkEmpregado(Empregado fkEmpregado) {
        this.fkEmpregado = fkEmpregado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTelefoneCli != null ? idTelefoneCli.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TelefoneFuncionario)) {
            return false;
        }
        TelefoneFuncionario other = (TelefoneFuncionario) object;
        if ((this.idTelefoneCli == null && other.idTelefoneCli != null) || (this.idTelefoneCli != null && !this.idTelefoneCli.equals(other.idTelefoneCli))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TelefoneFuncionario[ idTelefoneCli=" + idTelefoneCli + " ]";
    }
    
}
