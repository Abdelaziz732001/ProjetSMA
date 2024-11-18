package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AgentMedecin extends Agent {
    private String nom = "Médecin";
    private boolean disponible = true;  // Simuler la disponibilité

    @Override
    protected void setup() {
        System.out.println("AgentMedecin " + getLocalName() + " démarré.");
        
        // Ajouter un comportement cyclique pour gérer les demandes
        addBehaviour(new VerifierDisponibiliteBehaviour());
    }

    private class VerifierDisponibiliteBehaviour extends CyclicBehaviour {
        @Override
        public void action() {
            // Attendre un message de l'AgentPatient
            MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage message = receive(template);

            if (message != null) {
                System.out.println("AgentMedecin reçoit la demande de rendez-vous...");

                ACLMessage reponse = message.createReply();

                if (disponible) {
                    // Si le médecin est disponible, confirmer le rendez-vous
                    reponse.setPerformative(ACLMessage.CONFIRM);
                    reponse.setContent("Rendez-vous confirmé");
                    send(reponse);
                    System.out.println("AgentMedecin confirme le rendez-vous.");
                } else {
                    // Si le médecin n'est pas disponible, proposer une autre date
                    reponse.setPerformative(ACLMessage.PROPOSE);
                    reponse.setContent("Proposition nouvelle date: 10h00 demain");
                    send(reponse);
                    System.out.println("AgentMedecin propose une nouvelle date.");
                }
            } else {
                block(); // Si aucun message, l'agent attend
            }
        }
    }
}
