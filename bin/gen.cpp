#include <bits/stdc++.h>
using namespace std;

int main(int argc, char** argv)
{
	int count = stoi(argv[1]);

	for (int i = 0; i < count; ++i)
	{
		printf("insert into temp values(%d,'%d');\n",i,i );
	}
	return 0;
}