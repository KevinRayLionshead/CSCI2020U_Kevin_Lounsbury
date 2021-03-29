package sample;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class HttpClient {

	public static void main(String[] args) {
		BufferedReader in;
		PrintWriter out;

		//if (args.length <= 1) {
		//	System.out.println("Usage: java HttpClient <host> <uri> [<port>=80]");
		//	System.exit(0);
		//}

		String hostname = "localhost";
		String uri = "images/social/amazon.png";

		int port = 8080;
		if (args.length > 2) {
			port = Integer.parseInt(args[2]);
		}

		try {
			while (true) {

				Scanner scanner = new Scanner(System.in);
				System.out.println("Enter Command: ");
				String userInput = scanner.nextLine();
				StringTokenizer tokenizer = new StringTokenizer(userInput);
				String command = tokenizer.nextToken();
				String filename = "";
				if (tokenizer.hasMoreElements()) {
					filename = tokenizer.nextToken();
				}

				System.out.println("Connecting to server");
				Socket socket = new Socket(hostname, port);

				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream());


				// send the request
				System.out.println("Request:");
				System.out.print(command + " " + filename + " HTTP/1.1\r\n");
				System.out.print("Host: " + hostname + "\r\n\r\n");
				out.print(command + " " + filename + " HTTP/1.1\r\n");
				out.print("Host: " + hostname + "\r\n\r\n");
				out.flush();

				if (command.equalsIgnoreCase("DIR") || command.equalsIgnoreCase("DOWNLOAD")) {

					// read and print the response
					System.out.println("Response:");
					String line;
					while ((line = in.readLine()) != null) {
						System.out.println(line);
					}
				} else if (command.equalsIgnoreCase("UPLOAD")) {
					File file = new File(filename);

					if (!file.exists()) {
						System.out.println("404, Not Found. The file '" + file.getName() + "' could not be located.");

					} else {
						byte[] content = new byte[(int) file.length()];
						FileInputStream fileIn = new FileInputStream(file);
						fileIn.read(content);
						fileIn.close();
						out.print(content);
						System.out.println("File Sent!");
					}
				} else {
					System.out.println("Invalid command");
				}

				// close everything
				System.out.println("Disconnecting from server");
				in.close();
				out.close();
				socket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}