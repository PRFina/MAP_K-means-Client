import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;


import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import keyboardinput.Keyboard;
import protocol.MessageType;
import protocol.RequestMessage;
import protocol.ResponseMessage;


public class MainTest {

	/**
	 * @param args
	 */
	private ObjectOutputStream out;
	private ObjectInputStream in ; // stream con richieste del client
	
	
	public MainTest(String ip, int port) throws IOException{
		InetAddress addr = InetAddress.getByName(ip); //ip
		System.out.println("addr = " + addr);
		Socket socket = new Socket(addr, port); //Port
		System.out.println(socket);
		
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());	; // stream con richieste del client
	}
	
	private int menu(){
		int answer;
		System.out.println("Scegli una opzione");
		do{
			System.out.println("(1) Carica Cluster da File");
			System.out.println("(2) Carica Dati");
			System.out.print("Risposta:");
			answer=Keyboard.readInt();
		}
		while(answer<=0 || answer>2);
		return answer;
		
	}
	
	private String readActivity() throws  IOException, ClassNotFoundException{
		RequestMessage req = new RequestMessage(MessageType.READ);

		System.out.println("Insert file to load");
		String fileName = Keyboard.readString();
		req.addBodyField("file",fileName);

		out.writeObject(req);
		ResponseMessage resp = (ResponseMessage) in.readObject();

		out.flush();
		return resp.toString();
	}

	private String discoverActivity() throws IOException, ClassNotFoundException {
		RequestMessage req = new RequestMessage(MessageType.DISCOVER);

		System.out.println("Insert number of clusters:");
		String count = Integer.toString(Keyboard.readInt());
		req.addBodyField("clusters", count);

		System.out.println("Insert table name:");
		String table = Keyboard.readString();
		req.addBodyField("table", table);

		System.out.println("Insert file name:");
		String file = Keyboard.readString();
		req.addBodyField("file", file);

		out.writeObject(req);
		ResponseMessage resp = (ResponseMessage) in.readObject();
		out.flush();
		return resp.toString();


	}

	public static void main(String[] args) {
		String ip=args[0];
		int port=new Integer(args[1]).intValue();
		MainTest main=null;
		try{
			main=new MainTest(ip,port);
		}
		catch (IOException e){
			System.out.println(e);
			return;
		}
		
		
		do{
			int menuAnswer=main.menu();
			switch(menuAnswer) {

				case 1: { // read activity

					try {
						String result = main.readActivity();
						System.out.println(result); // DEBUG
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

					break;
				}
				case 2: {
					String result = null;
					try {
						result = main.discoverActivity();
						System.out.println(result); // DEBUG
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					break;
				}
				default:
					System.out.println("Opzione non valida!");
			}
			
			System.out.print("Vuoi scegliere una nuova operazione da menu?(y/n)");
			if(Keyboard.readChar()!='y')
				break;
		} while(true);
	}
}



