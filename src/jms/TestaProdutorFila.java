package jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TestaProdutorFila {

	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = connectionFactory.createConnection("admin", "senha");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");
		
		MessageProducer producer = session.createProducer(fila);
		
		Message message = session.createTextMessage("<pedido><id>58</id></pedido>");
		producer.send(message);
		
//		for (int i = 0; i < 2001; i++) {
//			Message message = session.createTextMessage("<pedido><id>" + i + "</id></pedido>");
//			producer.send(message);
//		}
		
//		new Scanner(System.in).nextLine(); 

		session.close();
        connection.close();
        context.close();
	}
}
