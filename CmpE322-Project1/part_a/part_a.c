//    Some parts of the code included from PS6 example of iocapture

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>
#include <string.h>
#include <sys/stat.h>
#include <fcntl.h>

#define RUN_EXEC

int main(int argc, char** argv){
    // take the paths from arguments
    char* path_1 = argv[1];
    char* path_2 = argv[2];
    // write and read
    char w_buf[100], r_buf[10000];
    int a,b;
    // fd for bidirectional pipe
    int p2c[2], c2p[2];
    pid_t   pid;


    scanf("%d %d", &a, &b);
    pipe(p2c);
    pipe(c2p); 
    pid = fork();

    if(pid == -1){
        fprintf(stderr, "fork() failed.\n");
        exit(-1);
    }

    if(pid > 0){
        /*
            parent
        */

        close(p2c[0]);  
        close(c2p[1]);
        //open the output file
        int fd = open(path_2, (O_WRONLY | O_CREAT | O_APPEND) ,0777);
        //redirect stdout to output file
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



    return 0;

}
