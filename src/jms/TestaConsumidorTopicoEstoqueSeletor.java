package jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

import br.com.lucasilva.modelo.Pedido;

public class TestaConsumidorTopicoEstoqueSeletor {

	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = connectionFactory.createConnection();
		connection.setClientID("estoque");
		
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Topic topico = (Topic) context.lookup("loja");
		
		MessageConsumer consumer = session.createDurableSubscriber(topico, "algumaCoisa", "eBook=false", false);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				
				ObjectMessage objectMessage = (ObjectMessage) message;
				
				try {
					TextMessage textMessage = (TextMessage) message;
					Pedido pedido = (Pedido) objectMessage.getObject();
					System.out.println(objectMessage.getObject());
					System.out.println("------------------------------------------");
					System.out.println();
					System.out.println();
					System.out.println(pedido.toString());
					System.out.println("------------------------------------------");
					System.out.println();
					System.out.println();
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			
		});
		
		new Scanner(System.in).nextLine(); 

        connection.close();
        context.close();
	}
}
