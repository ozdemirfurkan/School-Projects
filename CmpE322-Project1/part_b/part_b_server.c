/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

// some parts of the code are taken from PS6 codes

#include "part_b.h"

int *
part_b_1_svc(paths *argp, struct svc_req *rqstp)
{
	static int  result;

	/*
	 * insert server code here
	 */

	printf("Part b server is called with %s and %s \n", argp->path_1, argp->path_2);
	
	result =1;

	return &result;
}
