package uts.edu.co.veterinaria_seguridad.model;

import jakarta.persistence.*;

@Entity
@Table(name = "directorio_medico")
public class DirectorioMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private Integer idMedico;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 100)
    private String especialidad;

    @Column(name = "tarjeta_profesional", length = 50)
    private String tarjetaProfesional;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public DirectorioMedico() {}

    public DirectorioMedico(Integer idMedico, String nombre, String especialidad, String tarjetaProfesional, Usuario usuario) {
        this.idMedico = idMedico;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.tarjetaProfesional = tarjetaProfesional;
        this.usuario = usuario;
    }

    public Integer getIdMedico() { return idMedico; }
    public void setIdMedico(Integer idMedico) { this.idMedico = idMedico; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getTarjetaProfesional() { return tarjetaProfesional; }
    public void setTarjetaProfesional(String tarjetaProfesional) { this.tarjetaProfesional = tarjetaProfesional; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}