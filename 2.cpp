#include <iostream>
#include <fstream>
#include <string>
#include <cstring>

 #include <stdio.h>
#include <string.h>
#include <stdlib.h>


using namespace std;

int main(int argc, char const *argv[])
{

	ifstream in;
	in.open("in.txt");
	string strline="";
	ofstream temp1("temp1.txt");


	while( getline( in, strline ) )
        {	//int len=strlen(strline);
        	int len = strline.length();
        	
        	bool check=true;
        	if (len!=0)
        	{
        		
        		for (int i = 0; i < len; ++i)
        		{
        			if (strline[i]==';')
        				{
        					temp1<<";"<<endl;
        					check=false;
        				}else{
        					temp1<<strline[i];
        				}
        		}
        		if(check==true){
        		temp1<<endl;
        		}
        	}

        	}

        temp1.close();
        in.close();


        ifstream temp2;
        ofstream temp3;
        temp2.open("temp1.txt");
        temp3.open("temp2.txt");
        

    
	
		

		while(getline(temp2,strline)){
			bool check1=true;
			int len = strline.length();
			for(int i=0;i<len;i++){
				
				if(strline[i] == '{'){
					// fprintf(temp3, "{\n" ); 
					temp3<<"{"<<endl;
					check1=false;
				}
				else if(strline[i]== '}'){
					// fprintf(temp3, "}\n" );
					temp3<<"}"<<endl;
					check1=false;
				}else if(strline[i]==';'){
					temp3<<";"<<endl;
					check1=false;
				}
				else{
					temp3<<strline[i];
				}
			}
			if (check1==true)
			{
					temp3<<endl;
			}
			
		}
	
	temp3.close();
	temp2.close();



	// ifstream temp4("temp2.txt");
	// ofstream temp5("temp3.txt");

		
	// 	int tabcount=0;
	// 	while(getline(temp4,strline)){
	// 		bool check2=true;
	// 		int len = strline.length();
	// 		//cout << tabcount << endl;
	// 		if(strline[0] == '}'){
	// 			for(int j=0;j<tabcount-1;j++) temp5<<"\t";	
	// 		}
	// 		else
	// 			for(int j=0;j<tabcount;j++) temp5<<"\t";
	// 		for(int i=0;i<len;i++){
	// 			//check2=true;
	// 			if(strline[i]== '{'){
	// 				check2=false;
	// 				temp5<<"{" <<endl;
	// 				tabcount++;
	// 				for(int j =0 ;j<tabcount;j++) temp5<<"\t"; 
	// 			}
	// 			else if(strline[i] == '}'){
	// 				check2=false;
	// 				temp5<< "}"<<endl ;
	// 				tabcount--;
	// 			}
	// 			else{
	// 				temp5<<strline[i];
	// 			}
	// 		}
	// 		if (check2==true)
	// 		{
	// 			temp5<<endl;
	// 		}
			
	// 	}
	
	// temp5.close();
	// temp4.close();

	ifstream temp6("temp2.txt");
	ofstream out("out.txt");
	int count=0;

	while(getline(temp6,strline)){
		int len=strline.length();
		// out<<len<<"---";
		if (len!=0)
		{
			out<<count<<"\t"<<strline<<endl;
			count++;
		}

		
	}
	out.close();
	temp6.close();


	//cout<<count<<endl;



	int   a[count][count];

	for (int i = 0; i < count; ++i)
	{
		for (int j = 0; j < count; ++j)
		{
			a[i][j]=0;
		}
		
	}




	int ln=-1;
	// ifstream in1("out.txt");
	// bool main=false;
	char *line=NULL;
	FILE *fp;
	size_t length=0;
	fp=fopen("out.txt","r");
	ssize_t read;
	int arr[100];
	int row=0;
	
	while((read=getline(&line,&length,fp))!=-1){
		ln++;
		if (strstr(line,"{"))
		{	
			if (row!=0)
			{
				a[arr[row-1]][ln]=1;
			}
			arr[row++]=ln;
			if (strstr(line,"while"))
			{
				a[arr[row-1]][ln]=1;
			}
			
		}else if(strstr(line,"}")){
			row--;
		}else{
			a[arr[row-1]][ln]=1;
		}
	}


	cout<<"CONTROL FLOW LIST::"<<endl;
	for (int i = 0; i < count; ++i)
	{
		for (int j = 0; j < count; ++j)
		{
			cout<<a[i][j]<<" ";
		}
		cout<<endl;
	}

	fclose(fp);


	//data dependency;
	cout<<endl<<"DATA DEPENDENCY LIST"<<endl;

	int d[count][count];

	for (int i = 0; i < count; ++i)
	{
		for (int j = 0; j < count; ++j)
		{
			d[i][j]=0;
		}
	}

	fopen("out.txt","r");
	FILE *fp1;

	ln=-1;
	while((read=getline(&line,&length,fp))!=-1){
		 ln++;
		 if(strstr(line,"=")  && (strstr(line,"<")==NULL) && (strstr(line,">")==NULL)  && (strstr(line,"==")==NULL)){
		 	string s="";
		 	
		 	bool check3=false;
		 	int leng=strlen(line);
		 	bool space=false;
		 	
		 	for (int i = leng-1; i >=0; i--)
		 	{
		 		if(line[i]=='='){
		 			check3=true;
		 			i--;
		 		}
		 		if (line[i]=='\t')
		 		{
		 			break;
		 		}

		 		if (check3==true)
		 		{
		 			if (line[i]==' ')
		 			{	
		 				if (space==true)
		 				{
		 					break;
		 				}
		 				space=true;
		 				
		 			}else{
		 			s=line[i]+s;
		 			}
		 		}
		 	}

		 	fp1 =fopen("out.txt","r");
		 	char *line1=NULL;
		 	size_t length1=0;
			ssize_t read1;
			int ln1=-1;
		 	while((read1=getline(&line1,&length1,fp1))!=-1){
		 		ln1++;
		 		int leng1=strlen(line1);
		 		if(strstr(line1,"=")  && (strstr(line1,"<")==NULL) && (strstr(line1,">")==NULL)  && (strstr(line1,"==")==NULL)){
		 			string s1="";
		 			for (int i = leng1-1; i >= 0; i--)
		 			{
		 				if (line1[i]=='=')
		 				{
		 					break;
		 				}else{
		 					s1=line1[i]+s1;
		 				}
		 			}
		 			
		 			if (s1.find(s) != string::npos)
		 			{
		 				d[ln][ln1]=1;
		 			}
		 		}

		 	}
		 	fclose(fp1);



		 }


	}


for (int i = 0; i < count; ++i)
{
for (int j = 0; j < count; ++j)
{
	cout<<d[i][j]<<" ";
}
cout<<endl;
}






	return 0;
}