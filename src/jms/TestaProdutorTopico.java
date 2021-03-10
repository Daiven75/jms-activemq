package jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;

import br.com.lucasilva.modelo.Pedido;
import br.com.lucasilva.modelo.PedidoFactory;

public class TestaProdutorTopico {

	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topico = (Topic) context.lookup("loja");
		
		MessageProducer producer = session.createProducer(topico);
		
		Pedido pedido = new PedidoFactory().geraPedidoComValores(); 

//		StringWriter writter = new StringWriter();
//		JAXB.marshal(pedido, writter);
//		String xml = writter.toString();
//		System.out.println(xml);
		
		Message message = session.createObjectMessage(pedido);
//		message.setBooleanProperty("eBook", false);
		producer.send(message);

		session.close();
        connection.close();
        context.close();
	}
}
