/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

// some parts of the code are taken from PS6 codes

#include "part_b.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>
#include <string.h>
#include <sys/stat.h>
#include <fcntl.h>

#define RUN_EXEC


void
partb_prog_1(char blackboxpath[200], char outputpath[200], char *host)
{
	CLIENT *clnt;
	int  *result_1;
	paths  part_b_1_arg;

#ifndef	DEBUG
	clnt = clnt_create (host, PARTB_PROG, PARTB_VERS, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
#endif	/* DEBUG */
    //paths are copied from args to variables
	strcpy(part_b_1_arg.path_1, blackboxpath);
	strcpy(part_b_1_arg.path_2, outputpath);
	result_1 = part_b_1(&part_b_1_arg, clnt);
	if (result_1 == (int *) NULL) {
		clnt_perror (clnt, "call failed");
	}
#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 /* DEBUG */
}


int
main (int argc, char *argv[])
{
	char *host;
    //variables for paths
	char blackboxpath[200];
	char outputpath[200];

	if (argc < 4) {
		printf ("usage: %s blackbox outputfile server_host\n", argv[0]);
		exit (1);
	}
	host = argv[3];
	strcpy(blackboxpath,argv[1]);
	strcpy(outputpath, argv[2]);
	
    // same process with part a
	char* path_1 = argv[1];
    char* path_2 = argv[2];
    char w_buf[100], r_buf[10000];
    int a,b;
    int p2c[2], c2p[2];
    pid_t   pid;


    scanf("%d %d", &a, &b);
    // pipes for bidirectional com 
    pipe(p2c);
    pipe(c2p); 
    pid = fork();

    if(pid == -1){
        fprintf(stderr, "fork() failed.\n");
        exit(-1);
    }

    if(pid > 0){
        // parent

        close(p2c[0]);  
        close(c2p[1]);
        // open the output file
        int fd = open(argv[2], (O_WRONLY | O_CREAT | O_APPEND) ,0777);
        // redirect stdout to output file
        dup2(fd, STDOUT_FILENO);
        sprintf(w_buf, "%d %d\n", a, b);
        write(p2c[1], w_buf, strlen(w_buf));
        read(c2p[0], r_buf, sizeof(r_buf));
        

        if(atoi(r_buf) != 0){

            printf("Success:\n%d\n", atoi(r_buf));

        }else{

            printf("Fail: \n%s", r_buf);
        }

        close(p2c[1]);
        close(c2p[0]);


    }else{

        /*
            child
        */

        close(p2c[1]);
        close(c2p[0]);


        //redirect stdın stdout and stderr to pipes
        dup2(p2c[0], STDIN_FILENO);
        dup2(c2p[1], STDOUT_FILENO);
        dup2(c2p[1], STDERR_FILENO);

    
        close(p2c[0]);
        close(c2p[1]);
        // exec blackbox
        execl(path_1, path_1, NULL);
        
        
    }


	partb_prog_1 (blackboxpath, outputpath, host);
	
exit (0);
}
