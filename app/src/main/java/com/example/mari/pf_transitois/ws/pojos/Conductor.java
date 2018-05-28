package com.example.mari.pf_transitois.ws.pojos;

import java.util.Date;

/**
 * Created by Mari on 04/05/2018.
 */

public class Conductor {
        private String nombreC;
        private String apellidoPaternoC;
        private String apellidoMaternoC;
        private Date fechaNacimiento;
        private int numeroLicencia;
        private String telefono;
        private int idConductor;
        private String clave;

    public Conductor(int i) {
        idConductor = i;
    }

    public String getNombreC() {
            return nombreC;
        }

        public void setNombreC(String nombreC) {
            this.nombreC = nombreC;
        }

        public String getApellidoPaternoC() {
            return apellidoPaternoC;
        }

        public void setApellidoPaternoC(String apellidoPaternoC) {
            this.apellidoPaternoC = apellidoPaternoC;
        }

        public String getApellidoMaternoC() {
            return apellidoMaternoC;
        }

        public void setApellidoMaternoC(String apellidoMaternoC) {
            this.apellidoMaternoC = apellidoMaternoC;
        }

        public Date getFechaNacimiento() {
            return fechaNacimiento;
        }

        public void setFechaNacimiento(Date fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }

        public int getNumeroLicencia() {
            return numeroLicencia;
        }

        public void setNumeroLicencia(int numeroLicencia) {
            this.numeroLicencia = numeroLicencia;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public int getIdConductor() {
            return idConductor;
        }

        public void setIdConductor(int idConductor) {
            this.idConductor = idConductor;
        }

        public String getClave() {
            return clave;
        }

        public void setClave(String clave) {
            this.clave = clave;
        }

}
