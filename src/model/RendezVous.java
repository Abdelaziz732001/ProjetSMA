package model;

import agents.AgentPatient;
import agents.AgentMedecin;

public class RendezVous {
    private String date;
    private String heure;
    private AgentPatient patient;
    private AgentMedecin medecin;

    public RendezVous(String date, String heure, AgentPatient patient, AgentMedecin medecin) {
        this.date = date;
        this.heure = heure;
        this.patient = patient;
        this.medecin = medecin;
    }

    public void creerRendezVous() {
        System.out.println("Rendez-vous créé pour " + date + " à " + heure);
    }

    public void annulerRendezVous() {
        System.out.println("Rendez-vous annulé.");
    }
}
