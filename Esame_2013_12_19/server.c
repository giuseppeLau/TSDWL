/* 
 * File:   server.c
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
#define MYPORT 7777
#define MAXQ 512
#define MAXBUF 1024
#define NITER 10
#define DELAY 7

int iter = 0;

int main()
{
	int s, s1;
	struct sockaddr_in locAddr, farAddr;
	socklen_t farAddrL, ipAddrL; 
	int retcode;
	char buf[MAXBUF], list[MAXBUF];
	char v[10][MAXBUF] = { "", "", "", "", "", "", "", "", "", "" };
	char msg_ins[MAXBUF] = {"INSERITA\n"};
	char msg_pres[MAXBUF] = {"PRESENTE\n"};
	char msg_list[MAXBUF] = {"LIST"};
	int mkaddr(struct sockaddr_in * skaddr, char * ipaddr, u_int16_t port);

	if ( (s = socket(PF_INET, SOCK_STREAM, 0)) == -1 ) {
		perror("creating socket"); exit(-10);
	}
	
    mkaddr(&locAddr, LOCIP, htons(MYPORT));   
	ipAddrL = farAddrL = sizeof(struct sockaddr_in);
	if ( bind(s, (struct sockaddr *) &locAddr, ipAddrL) == -1 ) {
		perror("trying to bind"); exit(-1);
	}

	listen(s, MAXQ);

	while ((s1 = accept(s, (struct sockaddr *) &farAddr, &farAddrL)) != -1) {
		printf("Client from %s/%d connected, its address is %d bytes long\n", 
		       inet_ntoa(farAddr.sin_addr), 
		       ntohs(farAddr.sin_port), farAddrL);

		if ( (retcode = read(s1,buf,(int)sizeof(buf))) > 0) {   
			buf[retcode-1] = '\0';
			printf("Client sends: %s\n", buf);
			buf[retcode-1] = '\n';
		}

		char dest[10];
		strncpy(dest, buf, 10);

		int skip = 0;
		for(int i = 0; i < NITER; i++){ // se gia' presente skippa
			if((strncmp(dest, v[i], strlen(dest))) == 0 && skip == 0){
				write(s1,msg_pres,sizeof(msg_pres));
				skip = 1;
			}
		}		


		if((strncmp(buf, msg_list, strlen(msg_list))) == 0){ // controlla se hai scritto list
			for(int i = 0; i < iter; i++){
				if(v[i] != NULL){
					strcpy(list, v[i]);
					write(s1, list, sizeof(list));
					for (int j = 0; j < MAXBUF; ++j) { buf[j] = NULL; }	
				} else {
					skip = 1;
					break;	
				}
				skip = 1;
			}
		} else if(iter < NITER && skip == 0){ // inserimento normale 
			sprintf(v[iter], "%s\n", dest);
			write(s1,msg_ins,sizeof(msg_ins));
			iter++;
		} else if(iter == NITER && skip == 0){ // inserimento se il vettore e' pieno
			srand(time(NULL));	
			sprintf(v[rand()%10], "%s\n", dest);
			write(s1,msg_ins,sizeof(msg_ins));
		}

		for (int i = 0; i < MAXBUF; ++i) { buf[i] = NULL; }

		close(s1);
		for(int i = 0; i < NITER; i++){
			printf("\nContentuto di v[%d]: %s", i, v[i]);
		}
		printf("\n");
	} 
	exit(0);
}
