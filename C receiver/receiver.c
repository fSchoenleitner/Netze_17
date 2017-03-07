#include <stdio.h>
#include <winsock2.h>
#include <windows.h>

int startWinsocket(void){
	WSADATA wsa;
	return WSAStartup(MAKEWORD(2,0),&wsa);
}

int PASCAL recvfrom (
	SOCKET s,                   
	char FAR* buf,              
	int len,                    
	int flags,                  
	struct sockaddr FAR* from,  
	int FAR* fromlen            
);

int main (int argc, int* argv){
	long rc=startWinsocket();
	if(rc!=0){
		printf("starting winsocket failed with error code %l \n", rc);
		return 1;
	}else{
		printf("winsocket started\n");
	}
	SOCKET s=socket(AF_INET,SOCK_DGRAM,0);
	if(s==INVALID_SOCKET){
		printf("UDP Socket konnte nicht erstellt werden\n");
		return 1;
	}else{
		printf("UDP socket started\n");
	}
	char buf[256];
	SOCKADDR_IN addr;
	int addrLen=sizeof(SOCKADDR_IN);
	
	int i=0;
	int j=0;
	char c*;
	while(i<1000){
		rc=recvfrom(s, buf, 256, 0, &addr, &addrLen);
		if(rc==SOCKET_ERROR){
			printf("Beim Empfangen ist ein Fehler aufgetreten");
			return 1;
		}
		for(j=0; j<rc; j++){
			c*=buf[j];
			printf(c*);
		}
		
	}
}