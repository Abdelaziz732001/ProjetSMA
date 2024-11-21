package model;

import agents.AgentPatient;
import agents.AgentMedecin;

public class RendezVous {
    private String date;
    private String heure;
    private AgentPatient patient;
    private AgentMedecin medecin;

    // Constructeur
    public RendezVous(String date, String heure, AgentPatient patient, AgentMedecin medecin) {
        this.date = date;
        this.heure = heure;
        this.patient = patient;
        this.medecin = medecin;
    }

    // Méthode pour créer un rendez-vous
    public void creerRendezVous() {
        System.out.println("Rendez-vous créé : Date = " + date + ", Heure = " + heure);
    }

    // Méthode pour annuler un rendez-vous
    public void annulerRendezVous() {
        System.out.println("Rendez-vous annulé : Patient = " + patient.getLocalName() + ", Médecin = " + medecin.getLocalName());
    }

    // Getters et setters (si nécessaire)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public AgentPatient getPatient() {
        return patient;
    }

    public void setPatient(AgentPatient patient) {
        this.patient = patient;
    }

    public AgentMedecin getMedecin() {
        return medecin;
    }

    public void setMedecin(AgentMedecin medecin) {
        this.medecin = medecin;
    }
}
