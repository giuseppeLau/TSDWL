/* 
 * File:   serverB.c
 * Author: Giuseppe
 *
 */

#include <sys/types.h>
#include <sys/socket.h> 
#include <netinet/in.h>
#include <arpa/inet.h>		
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define LOCIP "127.0.0.1"
#define MYPORT 3333
#define MAXBUF 1024
#define MAXQ 512

int main() {

	int s, s1, retcode;
	struct sockaddr_in locAddr, farAddr;
	socklen_t ipAddrL, farAddrL;
	char buf[MAXBUF];

	int mkaddr(struct sockaddr_in * skaddr, char* ipaddr, u_int16_t port);

	if ((s = socket(PF_INET, SOCK_STREAM, 0)) == -1) {
		perror("errore nella creazione della socket.");
		exit(-10);
	}

	mkaddr(&locAddr, LOCIP, htons(MYPORT));
	ipAddrL = farAddrL = sizeof(struct sockaddr_in);

	if(bind(s, (struct sockaddr*) &locAddr, ipAddrL) == -1){
		perror("errore durante la bind");
		exit(-10);
	}
	
	listen(s, MAXQ);

	while((s1 = accept(s, (struct sockaddr* ) & farAddr, &farAddrL)) != 1){

		if ( (retcode = read(s1,buf,(int)sizeof(buf))) > 0) {   
			buf[retcode-1] = '\0';
			printf("Client sends: %s\n", buf);
			buf[retcode-1] = '\n';
		}

		write(s1, buf, strlen(buf));

		close(s1);
	}
	exit(0);
}
