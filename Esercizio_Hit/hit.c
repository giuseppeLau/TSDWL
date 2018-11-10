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
#include <time.h>
#include <unistd.h>
#include <string.h>

#include <sys/syscall.h>
#include <sys/types.h>

pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;
int x = 0;

void function_tA(){

	int hit_a = 0;
	int running_a = 1;

	while(running_a){

		int n_a = rand()%200;
		printf("Thread tA sleeping for %d ms...\n", n_a);
		usleep(n_a);
		pthread_mutex_lock(&lock);

		if(x > 500){
			printf("x has surpassed 500. Thread tA hit x %d times.\n", hit_a);
			pthread_mutex_unlock(&lock);
			running_a = 0;
			pthread_exit(NULL);	
		} else {
			hit_a++;
			x++;
			printf("Thread tA just hit x. Current tA hits: %d.\n", hit_a);
		}
		pthread_mutex_unlock(&lock);
	}
	printf("Closing thread tA...\n");
}

void function_tB(){

	int hit_b = 0;
	int running_b = 1;

	while(running_b){

		int n_b = rand()%200;
		printf("Thread tA sleeping for %d ms...\n", n_b);
		usleep(n_b);
		pthread_mutex_lock(&lock);

		if(x > 500){
			printf("x has surpassed 500. Thread tB hit x %d times.\n", hit_b);
			pthread_mutex_unlock(&lock);
			running_b = 0;
			pthread_exit(NULL);	
		} else {
			hit_b++;
			x++;
			printf("Thread tB just hit x. Current tB hits: %d.\n", hit_b);
		}
		pthread_mutex_unlock(&lock);
	}
	printf("Closing thread tB...\n");
}

int main(){

	pthread_t tA, tB;
	srand(time(0));
	pthread_create(&tA, NULL, &function_tA, NULL);
	pthread_create(&tB, NULL, &function_tB, NULL);
	pthread_join(tA, NULL);
	pthread_join(tB, NULL);
	printf("Exiting parent thread...\n");

	return 0;
}
