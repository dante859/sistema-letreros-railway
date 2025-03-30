package com.letreros.apirest.apirestletreros.Entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
// import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "letreros")
public class Letrero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLetrero")
    private Long idLetrero;

    @Column(name = "cliente")
    private String cliente;

    @Column(name = "telefono")
    private Integer telefono;

    @Column(name = "apellido")
    private String apellido;

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "fechaInicio")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    @Column(name = "fechaCaducada")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaCaducada;

    public Long getIdLetrero() {
        return idLetrero;
    }

    public void setIdLetrero(Long idLetrero) {
        this.idLetrero = idLetrero;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaCaducada() {
        return fechaCaducada;
    }

    public void setFechaCaducada(LocalDate fechaCaducada) {
        this.fechaCaducada = fechaCaducada;
    }

}
