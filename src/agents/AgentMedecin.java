package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AgentMedecin extends Agent {
    private String nom = "Médecin";
    private boolean disponible = true; // Simule la disponibilité du médecin

    @Override
    protected void setup() {
        System.out.println("AgentMedecin " + getLocalName() + " démarré.");

        // Ajouter un comportement pour gérer les demandes de rendez-vous
        addBehaviour(new GestionDemandeRendezVous());
    }

    private class GestionDemandeRendezVous extends CyclicBehaviour {
        @Override
        public void action() {
            // Filtrer les messages de type REQUEST
            MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage message = receive(template);

            if (message != null) {
                System.out.println("AgentMedecin a reçu une demande de rendez-vous de " + message.getSender().getLocalName());

                ACLMessage reponse = message.createReply();
                if (disponible) {
                    // Si disponible, confirmer le rendez-vous
                    reponse.setPerformative(ACLMessage.CONFIRM);
                    reponse.setContent("Rendez-vous confirmé pour demain à 14h.");
                    disponible = false; // Marquer le médecin comme non disponible
                    System.out.println("AgentMedecin confirme le rendez-vous.");
                } else {
                    // Sinon, proposer une autre date
                    reponse.setPerformative(ACLMessage.PROPOSE);
                    reponse.setContent("Proposition : 10h00 le lendemain.");
                    System.out.println("AgentMedecin propose une autre date.");
                }
                send(reponse);
            } else {
                block(); // Si aucun message reçu, attendre
            }
        }
    }
}
