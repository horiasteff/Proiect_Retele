package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
	
	

	public static void main(String[] args) {
		int port = Integer.parseInt(ResourceBundle.getBundle("settings").getString("port"));
		String hostname = ResourceBundle.getBundle("settings").getString("host");
		try (Socket socket = new Socket(hostname, port)) {
			System.out.println("Connected to server");
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			String user;
			List<String> clienti = new ArrayList<>();
			List<String> foldere = new ArrayList<>();
			List<String> foldereAlti = new ArrayList<>();
			//ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
			clienti.add("admin 12345");
			clienti.add("admin 00000");
			clienti.add("horia 12345");
			clienti.add("ari 12345");
			clienti.add("catinca 12345");
			
			try(Scanner scanner = new Scanner(System.in)) {
				System.out.println("Introdu userul si parola");
				while(true) {
					 // Initially send the username of the client.
					
					
					String command = scanner.nextLine();
		           
					
					user = command;
					
					
					writer.println(user);
					writer.flush();
					
//					Object object = objectInput.readObject();
//					 foldereAlti = (ArrayList<String>) object;
//					 System.out.println(foldereAlti);
					
						String response = reader.readLine();
						System.out.println(response);
						
//						for(int i =0;i<2;i++) {
//							response = reader.readLine();
//							System.out.println(response);
//						}
					
						command = scanner.nextLine();
						//System.out.println(response);
					if("incarca".equals(command.strip())) {
							Path path = Paths.get("/Users/horiamunteanu/Desktop/Diverse/Anul III/Semestrul II/Retele de calculatoare/EclipseProjects/Proiect_retele/src/fisiere");
							try (Stream<Path> subPath = Files.walk(path, 1)){
								List<String> subPathfolders = subPath.filter(Files::isRegularFile)
										.map(Objects::toString)
										.collect(Collectors.toList());
								
								for(int i = 0;i<subPathfolders.size();i++) {
									Path pathtemp = Paths.get(subPathfolders.get(i));
//									writer.println(pathtemp.getFileName().toString());
//									writer.flush();
									foldere.add(pathtemp.getFileName().toString());
									System.out.println(pathtemp.getFileName().toString());
								}
								
							}catch(IOException e) {
								e.printStackTrace();
							}
					}else if(command.contains("descarca")) {
						System.out.println("Am descarcat fisierul " + command.split(" ")[1]);
					}
					command = scanner.nextLine();
					while(true) {
						if(command.endsWith("txt")) {
							if(foldere.contains(command)) {
								writer.println(command);
								writer.flush();
							}
							
							
						}else if("incarca".equals(command.strip())) {
							Path path = Paths.get("/Users/horiamunteanu/Desktop/Diverse/Anul III/Semestrul II/Retele de calculatoare/EclipseProjects/Proiect_retele/src/fisiere");
							try (Stream<Path> subPath = Files.walk(path, 1)){
								List<String> subPathfolders = subPath.filter(Files::isRegularFile)
										.map(Objects::toString)
										.collect(Collectors.toList());
								
								for(int i = 0;i<subPathfolders.size();i++) {
									Path pathtemp = Paths.get(subPathfolders.get(i));
//									writer.println(pathtemp.getFileName().toString());
//									writer.flush();
									foldere.add(pathtemp.getFileName().toString());
									System.out.println(pathtemp.getFileName().toString());
								}
								
								if(command.endsWith("txt")) {
									if(foldere.contains(command)) {
										writer.println(command);
										writer.flush();
									}
									
								}
								
							}catch(IOException e) {
								e.printStackTrace();
							}
					}
						else if(command.equals("exit")){
							socket.close();
							
						}else {
							System.out.println("Fisier invalid");
						}
						 response = reader.readLine();
						 System.out.println(response);
						command = scanner.nextLine();
					}
					
//					for(int i = 0;i<clienti.size();i++) {
//						if(!(clienti.get(i).equals(response))) {
//							System.out.println(clienti.get(i));
//						}
//					}
					
					
					//System.out.println(response);	
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.exit(0);
		}
	}
	
}