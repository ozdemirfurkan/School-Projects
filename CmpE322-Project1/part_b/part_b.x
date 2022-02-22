struct paths{
	char path_1[200];
	char path_2[200];
};

program PARTB_PROG{
	version PARTB_VERS{
		int part_b(paths)=1;
	}=1;
}=0x12345678;