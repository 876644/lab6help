package com.company;

import com.company.Commands.clear;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
    public static clear clearObject;
    public static clear newClearObject;
    static DatagramChannel channel;
    public static void main(String[] args) throws IOException {

        int port = 21055; //порт сервера, к которому собирается потключится клиентский сокет

        try {
/*
            DatagramSocket datagramSocket = new DatagramSocket();//создаем экземпляр клиентского сокета
            channel = DatagramChannel.open();
            channel.configureBlocking(false);

            byte[] sendingDataBuffer = new byte[106384];
            byte[] receivingDataBuffer = new byte[16384];

            System.out.println("Введите команду:");
            Scanner scanner = new Scanner(System.in);
            String sentence = scanner.nextLine();
            clearObject = new clear(sentence);

            sendingDataBuffer = serialize(clearObject);

            newClearObject = (clear)deserialize(sendingDataBuffer);
            System.out.println("Объект был дессериализован.");
            InetAddress IAddress = InetAddress.getByName("localhost");
            // Создание UDP-пакет
            DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer,sendingDataBuffer.length,IAddress, port);


            datagramSocket.send(sendingPacket);// Отправка UDP-пакет серверу
            System.out.println("Объект был отправлен.");
            // Получите ответ от сервера, т.е. предложение из заглавных букв
            DatagramPacket receivingPacket = new DatagramPacket(receivingDataBuffer,receivingDataBuffer.length);
            datagramSocket.receive(receivingPacket);

            // Выведите на экране полученные данные
            byte[] receivedData = receivingPacket.getData();
            newClearObject = (clear) deserialize(receivedData);
            System.out.println("Сообщение от сервера: "+ newClearObject.nameOfCommand);

            datagramSocket.close();//закрытие соединения с сервером через сокет
            channel.close();
            */

            channel = DatagramChannel.open();
            channel.configureBlocking(false);

            byte[] sendingDataBuffer;

            System.out.println("Введите команду:");
            Scanner scanner = new Scanner(System.in);
            String sentence = scanner.nextLine();
            clearObject = new clear(sentence);

            sendingDataBuffer = serialize(clearObject);

            ByteBuffer byteBuffer = ByteBuffer.wrap(sendingDataBuffer);
            System.out.println(byteBuffer.remaining());
            InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost",port);
            
            channel.send(byteBuffer,inetSocketAddress);
            System.out.println("Объект был отправлен.");
            
            ByteBuffer newByteBuffer = ByteBuffer.allocate(91);
            
            channel.receive(newByteBuffer);

            while (byteBuffer.hasRemaining()){
                channel.read(newByteBuffer);
            }

            byte[] receivingDataBuffer = new byte[newByteBuffer.remaining()];
            System.out.println(newByteBuffer.remaining());
            newByteBuffer.get(receivingDataBuffer);
            /*for (int i=0;i<=receivingDataBuffer.length-1;i++){
                System.out.println(receivingDataBuffer[i]);
            }*/
            newByteBuffer.flip();
            newClearObject = (clear) deserialize(receivingDataBuffer);
            System.out.println("Сообщение от сервера: "+ newClearObject.nameOfCommand);

            channel.close();

        } catch (SocketException | UnknownHostException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static byte[] serialize(Object obj) throws IOException{

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//данные записываются в массив байтов
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);//Записывает указанный объект в ObjectOutputStream
        objectOutputStream.flush();//Сбрасывает поток
        return byteArrayOutputStream.toByteArray();

    }
    public static Object deserialize(byte[] array) throws IOException, ClassNotFoundException{

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(array);
        ObjectInput objectInput = new ObjectInputStream(byteArrayInputStream);
        return objectInput.readObject();
    }
}
