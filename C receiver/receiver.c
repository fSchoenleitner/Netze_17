
#include<stdio.h>
#include<winsock2.h>
#include<time.h>
#include<stdlib.h>
#include<windows.h>
 
#pragma comment(lib,"ws2_32.lib") //Winsock Library
 
//compilieren mit option -lws2_32
 
int main(int argc, char *argv[]){
    SOCKET s;
    struct sockaddr_in addr;
    int slen , recv_len, buflen=atoi(argv[2]), port=atoi(argv[1]);
    char buf[buflen];
    WSADATA wsa;
	SYSTEMTIME end_time, start_time;
	long end_millis, start_millis, rec_millis, rec_duration, transm_duration;
	char *millis;
	printf("port: %d  buflen: %d\n", port, buflen);
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

	int i=1;
    while(i){
        printf("Waiting...");
        fflush(stdout);
         
        //clear the buffer by filling null, it might have previously received data
        memset(buf,'\0', buflen);
         
        //try to receive some data, this is a blocking call
        GetSystemTime(&start_time);
		if ((recv_len = recv(s, buf, buflen, 0)) == SOCKET_ERROR){
            printf("recvfrom() failed with error code : %d" , WSAGetLastError());
            exit(EXIT_FAILURE);
        }
		GetSystemTime(&end_time);
		start_millis=(start_time.wSecond * 1000) + start_time.wMilliseconds;
		end_millis=(end_time.wSecond * 1000) + end_time.wMilliseconds;
		rec_duration=end_millis-start_millis;
         
        //print details of the client/peer and the data received
		i=strcmp(buf, "EndOfTransmission");
		if(i){
			millis=strstr(buf, "at ");
			*millis=*(millis+4);
			transm_duration=atoi(millis);
			printf("Received packet from %s:%d in time %d\n", inet_ntoa(addr.sin_addr), ntohs(addr.sin_port), transm_duration);
			printf("Packet: %s\n" , buf);
		}else{
			printf("Transmission terminated!\n");
		}
		
    }
 
    closesocket(s);
    WSACleanup();
     
    return 0;
}