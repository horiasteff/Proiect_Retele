package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientHandler implements Runnable {

	private Socket socket;
	private BufferedReader reader;
    private BufferedWriter bufferedWriter;
	private PrintWriter writer;
	private static List<String> clienti;
	private static List<String> fisiere = new ArrayList<>();
	private static List<String> clientiAutentificati= new ArrayList<>();
	private ObjectOutputStream objectOutput;
	 public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	 private String clientUsername;
	
	public ClientHandler(Socket socket) throws IOException {
		this.socket = socket;
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.writer = new PrintWriter(socket.getOutputStream());
		clienti = new ArrayList<>();
		//clientiAutentificati= new ArrayList<>();
        this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		//fisiere = new ArrayList<>();
		//fisiereCopie = new ArrayList<>();
		//objectOutput = new ObjectOutputStream(socket.getOutputStream());
         
		
	}

	@Override
	public void run() {
		while (!socket.isClosed()) {
			clienti.add("admin 12345");
			clienti.add("admin 00000");
			clienti.add("horia 12345");
			clienti.add("ari 12345");
			clienti.add("catinca 12345");
			
		
			try{
				String command = reader.readLine();
				this.clientUsername = command;
				
				
				if ("exit".equals(command.strip())) {
					clientiAutentificati.remove(clientUsername);
//				for (String client : clientiAutentificati) {
//					System.out.println(client);
//				}
					socket.close();
				} else if(clienti.contains(command.strip())) {
					for(String c : clienti) {
						if(c.equals(command.strip())) {
							//String command2 = reader.readLine();
							System.out.println("Bine ai venit");
							//System.out.println(command2);
							 clientHandlers.add(this);
					          //broadcastMessage("SERVER: " + clientUsername + " has entered the chat!");

							clientiAutentificati.add(command);
							for (String client : clientiAutentificati) {
								System.out.println(client);
							}
							
						String toate="";
						
						for(int i =0;i<fisiere.size();i++) {
							//toate.concat(fisiere.get(i) + " ");
							toate = toate  +" "+fisiere.get(i);
							}
						writer.println(toate);
						writer.flush();
						//writer.flush();
							
//							writer.println(toate);
//							writer.flush();
						}
					}
				}else if(command.endsWith(".txt")){
					//System.out.println("User invalid");
//					String toate="";
					fisiere.add(command);
					
					System.out.println("Am adaugat fisierul");
//					for(int i =0;i<fisiere.size();i++) {
//						//toate.concat(fisiere.get(i) + " ");
//						toate = toate  +" "+fisiere.get(i);
//						}
//					writer.println(toate);
//					writer.flush();
					
				}else {
					writer.println("user invalid");
					writer.flush();
					socket.close();
				}
				
				
				
			} catch (Exception e) {
				writer.println(e.getMessage());
				writer.flush();
			}	
			
			
		}		
	}
	
	public void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                // You don't want to broadcast the message to the user who sent it.
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                // Gracefully close everything.
                closeEverything(socket, reader, bufferedWriter);
            }
        }
    }
	public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUsername + " has left the chat!");
    }
	
	  public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
	        // Note you only need to close the outer wrapper as the underlying streams are closed when you close the wrapper.
	        // Note you want to close the outermost wrapper so that everything gets flushed.
	        // Note that closing a socket will also close the socket's InputStream and OutputStream.
	        // Closing the input stream closes the socket. You need to use shutdownInput() on socket to just close the input stream.
	        // Closing the socket will also close the socket's input stream and output stream.
	        // Close the socket after closing the streams.

	        // The client disconnected or an error occurred so remove them from the list so no message is broadcasted.
	        removeClientHandler();
	        try {
	            if (bufferedReader != null) {
	                bufferedReader.close();
	            }
	            if (bufferedWriter != null) {
	                bufferedWriter.close();
	            }
	            if (socket != null) {
	                socket.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
}