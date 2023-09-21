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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author helio
 */
@Entity
@Table(name = "funcaoempregado")
@NamedQueries({
    @NamedQuery(name = "Funcaoempregado.findAll", query = "SELECT f FROM Funcaoempregado f"),
    @NamedQuery(name = "Funcaoempregado.findByIdFuncao", query = "SELECT f FROM Funcaoempregado f WHERE f.idFuncao = :idFuncao"),
    @NamedQuery(name = "Funcaoempregado.findByEspecialidade", query = "SELECT f FROM Funcaoempregado f WHERE f.especialidade = :especialidade"),
    @NamedQuery(name = "Funcaoempregado.findByDescricao", query = "SELECT f FROM Funcaoempregado f WHERE f.descricao = :descricao")})
public class Funcaoempregado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFuncao")
    private Integer idFuncao;
    @Basic(optional = false)
    @Column(name = "especialidade")
    private String especialidade;
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(mappedBy = "fkFuncaoEmpregado")
    private Collection<Empregado> empregadoCollection;

    public Funcaoempregado() {
    }

    public Funcaoempregado(Integer idFuncao) {
        this.idFuncao = idFuncao;
    }

    public Funcaoempregado(Integer idFuncao, String especialidade) {
        this.idFuncao = idFuncao;
        this.especialidade = especialidade;
    }

    public Integer getIdFuncao() {
        return idFuncao;
    }

    public void setIdFuncao(Integer idFuncao) {
        this.idFuncao = idFuncao;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Collection<Empregado> getEmpregadoCollection() {
        return empregadoCollection;
    }

    public void setEmpregadoCollection(Collection<Empregado> empregadoCollection) {
        this.empregadoCollection = empregadoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFuncao != null ? idFuncao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcaoempregado)) {
            return false;
        }
        Funcaoempregado other = (Funcaoempregado) object;
        if ((this.idFuncao == null && other.idFuncao != null) || (this.idFuncao != null && !this.idFuncao.equals(other.idFuncao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Funcaoempregado[ idFuncao=" + idFuncao + " ]";
    }
    
}
