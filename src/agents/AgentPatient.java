package agents;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;

public class AgentPatient extends Agent {
    private String nom = "Patient";
    private boolean rendezVousDemande = false; // Indicateur pour éviter la demande infinie

    @Override
    protected void setup() {
        System.out.println("AgentPatient " + getLocalName() + " démarré.");
        
        // Ajouter un comportement pour demander un rendez-vous
        addBehaviour(new DemandeRendezVousBehaviour());
    }

    private class DemandeRendezVousBehaviour extends Behaviour {
        private boolean reponseRecue = false;

        @Override
        public void action() {
            if (!reponseRecue && !rendezVousDemande) {
                // Demander un rendez-vous seulement si aucune demande n'a encore été faite
                System.out.println("AgentPatient demande un rendez-vous...");
                
                // Créer un message pour demander un rendez-vous au médecin
                ACLMessage demande = new ACLMessage(ACLMessage.REQUEST);
                AID medecinAID = new AID("medecin", AID.ISLOCALNAME);
                demande.addReceiver(medecinAID);
                demande.setContent("Demande de rendez-vous");
                send(demande);
                
                rendezVousDemande = true; // Marquer que la demande a été faite
            } else {
                // Attendre la réponse de l'AgentMedecin
                ACLMessage reponse = receive();
                if (reponse != null) {
                    if (reponse.getPerformative() == ACLMessage.CONFIRM) {
                        System.out.println("AgentPatient a reçu la confirmation du rendez-vous.");
                        reponseRecue = true;
                    } else if (reponse.getPerformative() == ACLMessage.PROPOSE) {
                        System.out.println("AgentMedecin propose une nouvelle date : " + reponse.getContent());
                        // L'AgentPatient reprogramme le rendez-vous ici
                        // Remettre à false pour redemander le rendez-vous
                        rendezVousDemande = false;
                    }
                } else {
                    block();  // Si aucune réponse, l'agent attend
                }
            }
        }

        @Override
        public boolean done() {
            return reponseRecue;  // Ce comportement est terminé quand la réponse est reçue
        }
    }
}
