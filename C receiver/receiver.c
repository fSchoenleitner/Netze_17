#include<stdio.h>
#include<winsock2.h>
 
#pragma comment(lib,"ws2_32.lib") //Winsock Library
 
//compilieren mit option -lws2_32
 
int main(int argc, char *argv[]){
    SOCKET s;
    struct sockaddr_in addr;
    int slen , recv_len;
	int buflen=atoi(argv[2]);
    char buf[buflen];
	int port=atoi(argv[1]);
	int numbOfPacket=1000;
    WSADATA wsa;
	printf("port: %d  buflen %d  number: %d\n", port, buflen, numbOfPacket);
    slen = sizeof(addr) ;
     
    //Initialise winsock
    printf("Initialising Winsock...\n");
    if (WSAStartup(MAKEWORD(2,2),&wsa) != 0){
        printf("Failed. Error Code : %d",WSAGetLastError());
        exit(EXIT_FAILURE);
    }
     
    //Create a socket
    if((s = socket(AF_INET , SOCK_DGRAM , 0 )) == INVALID_SOCKET){
        printf("Could not create socket : %d" , WSAGetLastError());
    }
    printf("Socket created\n");
     
    //Prepare the addr's sockaddr_in structure
    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = INADDR_ANY;
    addr.sin_port = htons( port );
     
    //Bind socket to receiver
    if( bind(s ,(struct sockaddr *)&addr , sizeof(addr)) == SOCKET_ERROR){
        printf("Bind failed with error code : %d" , WSAGetLastError());
        exit(EXIT_FAILURE);
    }
    printf("Bind done\n");

	int i=0;
    while(i<numbOfPacket){
        printf("Waiting...");
        fflush(stdout);
         
        //clear the buffer by filling null, it might have previously received data
        memset(buf,'\0', buflen);
         
        //try to receive some data, this is a blocking call
        if ((recv_len = recv(s, buf, buflen, 0)) == SOCKET_ERROR){
            printf("recvfrom() failed with error code : %d" , WSAGetLastError());
            exit(EXIT_FAILURE);
        }
         
        //print details of the client/peer and the data received
        printf("Received packet from %s:%d\n", inet_ntoa(addr.sin_addr), ntohs(addr.sin_port));
        printf("Packet: %s\n" , buf);
		i++;
    }
 
    closesocket(s);
    WSACleanup();
     
    return 0;
}