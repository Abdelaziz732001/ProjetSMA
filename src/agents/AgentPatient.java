package agents;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;

public class AgentPatient extends Agent {
    private String nom = "Patient";
    private boolean demandeEnvoyee = false; // Pour éviter les demandes répétées

    @Override
    protected void setup() {
        System.out.println("AgentPatient " + getLocalName() + " démarré.");
        
        // Ajouter un comportement pour demander un rendez-vous
        addBehaviour(new DemandeRendezVousBehaviour());
    }

    private class DemandeRendezVousBehaviour extends Behaviour {
        private boolean rendezVousConfirme = false;

        @Override
        public void action() {
            if (!demandeEnvoyee) {
                System.out.println("AgentPatient prépare une demande de rendez-vous...");

                // Création et envoi de la demande de rendez-vous
                ACLMessage demande = new ACLMessage(ACLMessage.REQUEST);
                AID medecinAID = new AID("AgentMedecin", AID.ISLOCALNAME);
                demande.addReceiver(medecinAID);
                demande.setContent("Je souhaite un rendez-vous.");
                send(demande);

                System.out.println("AgentPatient a envoyé une demande au médecin.");
                demandeEnvoyee = true; // Éviter les demandes multiples
            } else {
                // Attente de réponse du médecin
                ACLMessage reponse = receive();
                if (reponse != null) {
                    switch (reponse.getPerformative()) {
                        case ACLMessage.CONFIRM:
                            System.out.println("AgentPatient a reçu une confirmation : " + reponse.getContent());
                            rendezVousConfirme = true;
                            break;
                        case ACLMessage.PROPOSE:
                            System.out.println("AgentPatient a reçu une proposition : " + reponse.getContent());
                            // L'agent peut ici traiter la proposition ou renvoyer une autre demande
                            demandeEnvoyee = false; // Relancer une demande si besoin
                            break;
                        default:
                            System.out.println("AgentPatient a reçu une réponse inattendue.");
                    }
                } else {
                    block(); // Attendre de nouveaux messages
                }
            }
        }

        @Override
        public boolean done() {
            return rendezVousConfirme; // Comportement terminé après confirmation
        }
    }
}
