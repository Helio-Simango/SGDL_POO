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
@Table(name = "empregado")
@NamedQueries({
    @NamedQuery(name = "Empregado.findAll", query = "SELECT e FROM Empregado e"),
    @NamedQuery(name = "Empregado.findByIdEmpregado", query = "SELECT e FROM Empregado e WHERE e.idEmpregado = :idEmpregado"),
    @NamedQuery(name = "Empregado.findByPrimeiroNome", query = "SELECT e FROM Empregado e WHERE e.primeiroNome = :primeiroNome"),
    @NamedQuery(name = "Empregado.findByApelido", query = "SELECT e FROM Empregado e WHERE e.apelido = :apelido"),
    @NamedQuery(name = "Empregado.findByEmail", query = "SELECT e FROM Empregado e WHERE e.email = :email"),
    @NamedQuery(name = "Empregado.findBySenha", query = "SELECT e FROM Empregado e WHERE e.senha = :senha"),
    @NamedQuery(name = "Empregado.findByDataNascimento", query = "SELECT e FROM Empregado e WHERE e.dataNascimento = :dataNascimento"),
    @NamedQuery(name = "Empregado.findBySexo", query = "SELECT e FROM Empregado e WHERE e.sexo = :sexo"),
    @NamedQuery(name = "Empregado.findByDataCadastro", query = "SELECT e FROM Empregado e WHERE e.dataCadastro = :dataCadastro"),
    @NamedQuery(name = "Empregado.findByEstado", query = "SELECT e FROM Empregado e WHERE e.estado = :estado"),
    @NamedQuery(name = "Empregado.findByEnderecoResidencia", query = "SELECT e FROM Empregado e WHERE e.enderecoResidencia = :enderecoResidencia")})
public class Empregado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEmpregado")
    private Integer idEmpregado;
    @Basic(optional = false)
    @Column(name = "primeiroNome")
    private String primeiroNome;
    @Basic(optional = false)
    @Column(name = "apelido")
    private String apelido;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "senha")
    private String senha;
    @Column(name = "dataNascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    @Basic(optional = false)
    @Column(name = "sexo")
    private Character sexo;
    @Basic(optional = false)
    @Column(name = "dataCadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Column(name = "enderecoResidencia")
    private String enderecoResidencia;
    @Column(name = "funcao")
    private String funcao;
    @Column(name = "nivelDeAcesso")
    private Integer nivelDeAcesso;
    @Column(name = "Nuit")
    private String nuit;
    @Column(name = "BilheteIdentidade")
    private String bilheteIdentidade;
    @JoinColumn(name = "fkFuncaoEmpregado", referencedColumnName = "idFuncao")
    @ManyToOne
    private Funcaoempregado fkFuncaoEmpregado;
    @OneToMany(mappedBy = "fkEmpregado")
    private Collection<Pedido> pedidoCollection;
    @OneToMany(mappedBy = "fkEmpregado")
    private Collection<TelefoneFuncionario> telefoneFuncionarioCollection;

    public Empregado() {
    }

    public Empregado(Integer idEmpregado) {
        this.idEmpregado = idEmpregado;
    }

    public Empregado(Integer idEmpregado, String primeiroNome, String apelido, String email, String senha, Character sexo, Date dataCadastro, String estado) {
        this.idEmpregado = idEmpregado;
        this.primeiroNome = primeiroNome;
        this.apelido = apelido;
        this.email = email;
        this.senha = senha;
        this.sexo = sexo;
        this.dataCadastro = dataCadastro;
        this.estado = estado;
    }

    public Integer getIdEmpregado() {
        return idEmpregado;
    }

    public void setIdEmpregado(Integer idEmpregado) {
        this.idEmpregado = idEmpregado;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEnderecoResidencia() {
        return enderecoResidencia;
    }

    public void setEnderecoResidencia(String enderecoResidencia) {
        this.enderecoResidencia = enderecoResidencia;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public Integer getNivelDeAcesso() {
        return nivelDeAcesso;
    }

    public void setNivelDeAcesso(Integer nivelDeAcesso) {
        this.nivelDeAcesso = nivelDeAcesso;
    }
    
    public Funcaoempregado getFkFuncaoEmpregado() {
        return fkFuncaoEmpregado;
    }

    public String getNuit() {
        return nuit;
    }

    public void setNuit(String nuit) {
        this.nuit = nuit;
    }

    public String getBilheteIdentidade() {
        return bilheteIdentidade;
    }

    public void setBilheteIdentidade(String bilheteIdentidade) {
        this.bilheteIdentidade = bilheteIdentidade;
    }
    
    

    public void setFkFuncaoEmpregado(Funcaoempregado fkFuncaoEmpregado) {
        this.fkFuncaoEmpregado = fkFuncaoEmpregado;
    }

    public Collection<Pedido> getPedidoCollection() {
        return pedidoCollection;
    }

    public void setPedidoCollection(Collection<Pedido> pedidoCollection) {
        this.pedidoCollection = pedidoCollection;
    }

    public Collection<TelefoneFuncionario> getTelefoneFuncionarioCollection() {
        return telefoneFuncionarioCollection;
    }

    public void setTelefoneFuncionarioCollection(Collection<TelefoneFuncionario> telefoneFuncionarioCollection) {
        this.telefoneFuncionarioCollection = telefoneFuncionarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpregado != null ? idEmpregado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empregado)) {
            return false;
        }
        Empregado other = (Empregado) object;
        if ((this.idEmpregado == null && other.idEmpregado != null) || (this.idEmpregado != null && !this.idEmpregado.equals(other.idEmpregado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Empregado[ idEmpregado=" + idEmpregado + " primeiroNome" + primeiroNome+" ]";
    }
    
}
