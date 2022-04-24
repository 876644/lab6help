package com.company;

import com.company.Commands.clear;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Main {

    //public static PriorityQueue<StudyGroup> collection = new PriorityQueue<>();//Создала коллекцию
    public static DatagramSocket datagramSocket;//  получать ответы от клиента
    public static DatagramPacket inputPacket;
    public static byte[] receivingDataBuffer = new byte[16384];
    public static byte[] sendingDataBuffer = new byte[16384];
    public static clear object;
    public static clear newClearObject;

    public static void main(String[] args) {

        connection();
        read();
        newClearObject = object;

        sent();

    }

    public static void connection() {

        int port = 21055;
        //byte[] length = new byte[16384];
        try {
            datagramSocket = new DatagramSocket(port);// буферы для хранения отправляемых и получаемых данных.
            //DatagramPacket inputPacketLength = new DatagramPacket(length,length.length);
            //System.out.println("Waiting for a client to connect...length...");
            //datagramSocket.receive(inputPacketLength);
            //count = ByteBuffer.wrap(length).getInt();
            //System.out.println(count);
            receivingDataBuffer = new byte[16384];

            inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);//экземпляр UDP-пакета для хранения клиентских данных с использованием буфера для полученных данных
            System.out.println("Waiting for a client to connect...");
            datagramSocket.receive(inputPacket);// получаем данные от клиента и сохраните их в datagramPacket
            System.out.println("Был получен сериализованный объект. ");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void read() {

            //datagramSocket.receive(inputPacket); // получаем данные от клиента и сохраните их в datagramPacket
            //received = inputPacket.getData();// Выводим на экран отправленные клиентом данные
            //System.out.println(received);
        try {
            object = (clear) deserialize(receivingDataBuffer);
            System.out.println("Полученное сообщение от клиента: " + object.nameOfCommand);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static Object deserialize(byte[] array) throws IOException, ClassNotFoundException{

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(array);
        ObjectInput objectInput = new ObjectInputStream(byteArrayInputStream);
        return objectInput.readObject();
    }
    public static byte[] serialize(Object obj) throws IOException{

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//данные записываются в массив байтов
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);//Записывает указанный объект в ObjectOutputStream
        objectOutputStream.flush();//Сбрасывает поток
        return byteArrayOutputStream.toByteArray();

    }

    public static void sent() {
        try {
            InetAddress host = inputPacket.getAddress(); // Получение IP-адрес и порт клиента
            int senderPort = inputPacket.getPort();

            sendingDataBuffer = serialize(newClearObject);
            //  новый UDP-пакет с данными, чтобы отправить их клиенту
            DatagramPacket outputPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, host, senderPort);

            datagramSocket.send(outputPacket);// Отправляем пакет клиенту

            datagramSocket.close();//закрытие соединение сокетов
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /* public static int countOfMass(byte[] bytes){
        int count=0;
        for(int i=0;i<=1024;i++){
            if(bytes[i]!=0){
                count+=1;
            }
        }
        return count;
    }*/
}
