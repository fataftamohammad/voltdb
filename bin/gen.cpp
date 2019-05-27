#include <bits/stdc++.h>
using namespace std;

int main(int argc, char** argv)
{

	for (int i = 1; i < argc; ++i)
	{
		//insert
		if(argv[i][0]=='-' && argv[i][1] == 'i')
		{
			int count = stoi(argv[++i]);
			for (int i = 0; i < count; ++i)
				printf("insert into temp values(%d,'%d');\n",i,i );
		}
		//update
		if(argv[i][0]=='-' && argv[i][1] == 'u')
		{
			int count = stoi(argv[++i]);
			for (int i = 0; i < count; ++i)
				printf("update temp set name='x';\n");
		}

	}
	


	return 0;
}