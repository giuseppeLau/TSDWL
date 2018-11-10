/* 
 * File:   hit.c
 * Author: Giuseppe
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <ctype.h>
#include <limits.h>

#include <sys/syscall.h>
#include <sys/types.h>

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
int n = 0;
int t = 200;

void function_t(){
	
	int cycles = 0;
	int run = 1;
	
	while(run==1){

		pthread_mutex_lock(&mutex);

		if(cycles >= 10 && n%2==0){
			printf("n (%d) is even after %d cycles. Closing thread tE. \n", n, cycles);
			pthread_mutex_unlock(&mutex);
			run = 0;
			pthread_exit(NULL);
		} else if (cycles >= 1000){
			printf("Cycles has reached 1000. Closing thread tE. \n" );
			pthread_mutex_unlock(&mutex);
			run = 0;
			pthread_exit(NULL);	
		} else {
			usleep(t);
			printf("tE: waiting for %d microseconds... \n", t);
			srand(time(NULL));
			int random = rand() % 1000;
			n = n + random;
			cycles++;
		}
		pthread_mutex_unlock(&mutex);
	}
	printf("Thread tE has been closed. \n");
}

void function_n(){
	
	int cycles_n = 0;
	int run_n = 1;
	
	while(run_n==1){

		pthread_mutex_lock(&mutex);

		if(cycles_n >= 10 && n%2==1){
			printf("n (%d) is odd after %d cycles. Closing thread t0. \n", n, cycles_n);
			pthread_mutex_unlock(&mutex);
			run_n = 0;
			pthread_exit(NULL);
		} else if (cycles_n >= 1000){
			printf("Cycles has reached 1000. Closing thread t0. \n" );
			pthread_mutex_unlock(&mutex);
			run_n = 0;
			pthread_exit(NULL);	
		} else {
			usleep(n);
			printf("t0: waiting for %d microseconds... \n", n);
			srand(time(NULL));
			int random_n = rand() % 1000;
			n+= random_n;	
			cycles_n++;
		}
		pthread_mutex_unlock(&mutex); 
	}
	printf("Thread tO has been closed. \n");
}

int main(){

	pthread_t tO, tE;
	srand(time(NULL));
	pthread_create(&tO, NULL, function_n, NULL);
	pthread_create(&tE, NULL, function_t, NULL);
	pthread_join(tO, NULL);
	pthread_join(tE, NULL);
	printf("Parent thread closed. \n");

	return 0;
}
