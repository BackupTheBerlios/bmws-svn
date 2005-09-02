//////////////////////////////////////////////////////////////////////
// String.cpp
//
// (C)2000 by Axel Sammet
//
// generische Stringklasse
//////////////////////////////////////////////////////////////////////

#include "string.h"
#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <string>
#include "excclass.h"

#define true 1
#define false 0

namespace util {

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

String::String() {
	ptr	   = new char[1];
	*ptr   = '\0';
	length = 0;
	sf = SF_IGNORECASE;
}

String::String(const char ch) {
	length = 1;
	ptr = new char[2];
	ptr[0] = ch;
	ptr[1] = '\0';
	sf = SF_IGNORECASE;
}

String::String(char * str) {
	length = strlen(str);
	ptr    = new char[length+1];
	memcpy(ptr,str,length+1);
	sf = SF_IGNORECASE;
}

String::String(const char * str) {
	length = strlen(str);
	ptr    = new char[length+1];
	memcpy(ptr,str,length+1);
	sf = SF_IGNORECASE;
}

String::String(const String & str) {
	length = str.length;
	sf     = str.sf;
	ptr    = new char[length+1];
	memcpy(ptr,str.ptr,str.length+1);

}

String::String(int i) {
	char	buf[80];
	sprintf(&buf[0],"%d",i);
	length = strlen(&buf[0]);
	ptr	= new char[length+1];
	memcpy(ptr,&buf[0],length+1);
	sf = SF_IGNORECASE;
}	

String::String(unsigned int i) {
	char	buf[80];
	sprintf(&buf[0],"%d",i);
	length = strlen(&buf[0]);
	ptr	= new char[length+1];
	memcpy(ptr,&buf[0],length+1);
	sf = SF_IGNORECASE;
}
	
String::String(long i) {
	char	buf[80];
	sprintf(&buf[0],"%d",i);
	length = strlen(&buf[0]);
	ptr	= new char[length+1];
	memcpy(ptr,&buf[0],length+1);
	sf = SF_IGNORECASE;
}	

String::String(double d) {
	char	buf[80];
	sprintf(&buf[0],"%f",d);
	length = strlen(&buf[0]);
	ptr	= new char[length+1];
	memcpy(ptr,&buf[0],length+1);
	sf = SF_IGNORECASE;
}

String::~String() {
	delete ptr;
}

//////////////////////////////////////////////////////////////////////////
// Operatoren
//////////////////////////////////////////////////////////////////////////

/*char&  String::operator [ ](long i) {
        if((i<0)||(i>length)) {
                String met = String("String::operator[")+String(i)+String("] length:")+String(length);
                throw OutOfBoundsException(met,"Out of range !!!");
        }
        return *(ptr+i);
}
*/
/*
istream &operator >> (istream &is,String &str) {
	char	buf[1024];
	is.getline(&buf[0],1024);
	str.length = strlen(&buf[0]);
	str.ptr    = new char[str.length+1];
	memcpy(str.ptr,&buf[0],str.length);
	str.ptr[str.length] = '\0';
	return is;
}

ostream &operator << (ostream &os,const String &str) {
	return os << str.ptr;
}
*/
String String::operator =(const String &s1) {
	delete ptr;
	length = s1.length;
	sf     = s1.sf;
	ptr    = new char[s1.length+1];
	memcpy(ptr,s1.ptr,length+1);
	return *this;
}

String String::operator =(const char *str) {
	delete ptr;
	length = strlen(str);
	sf     = SF_IGNORECASE;
	ptr    = new char[length+1];
	memcpy(ptr,str,length+1);
	return *this;
}

String String::operator +=(const char *str) {
	char*	temp;
	long slen = strlen(str);
	temp    = ptr;
	ptr     = new char[length+strlen(str)+1];
	memcpy(ptr,temp,length);
	memcpy(ptr+length,str,slen+1);
	length = length+slen;
	delete temp;
	return *this;
}

String String::operator +=(const String &str) {
	char*	old;
	old = ptr;
	ptr    = new char[length+str.length+1];
	memcpy(ptr,old,length);
	memcpy(ptr+length,str.ptr,str.length+1);
	length = length+str.length;
	delete old;
	return *this;
}
	

long String::operator ==(const String &s1) {
	char	*pt1,*pt2;
	for(pt1=s1.ptr,pt2=ptr;(*pt1 == *pt2);pt1++,pt2++) if (*pt1=='\0') return true;
	return false;
}

long String::operator ==(const char* s1) {
	const char	*pt1,*pt2;
	for(pt1=s1,pt2=ptr;(*pt1 == *pt2);pt1++,pt2++) if (*pt1=='\0') return true;
	return false;
}

long String::operator !=(const String &s1) {
	char	*pt1,*pt2;
	for(pt1=s1.ptr,pt2=ptr;(*pt1 == *pt2);pt1++,pt2++) if (*pt1=='\0') return false;
	return (pt1-ptr);
}

long String::operator != (const char *s1) {
        const char    *pt1,*pt2;
        for(pt1=s1,pt2=ptr;(*pt1 == *pt2);pt1++,pt2++) if (*pt1=='\0') return false;
        return (pt1-ptr);
}

String operator +(const String &s1,const String &s2) {
	String	temp;
	temp.length = s1.length+s2.length;
	temp.ptr    = new char[temp.length+1];
	memcpy(temp.ptr,s1.ptr,s1.length);
	memcpy(temp.ptr+s1.length,s2.ptr,s2.length+1);
	return temp;
} 

String operator+(const char *pt,const String &s2) {
	String	temp;
	long		len;
	len = strlen(pt);
	temp.length = len+s2.length;
	temp.ptr    = new char[temp.length+1];
	memcpy(temp.ptr,pt,len);
	memcpy(temp.ptr+len,s2.ptr,s2.length+1);
	return temp;
}

String operator+(const String &s1,const char *pt) {
	String	temp;
	long		len;
	len = strlen(pt);
	temp.length = s1.length+len;
	temp.ptr    = new char[temp.length+1];
	memcpy(temp.ptr,s1.ptr,s1.length);
	memcpy(temp.ptr+s1.length,pt,len+1);
	return temp;
}

long String::operator<(const String &s1) {
	long		i=0;
	char		ch1,ch2;
	do {
		if(i==length) return false;
		if(sf&SF_IGNORECASE) {
			if((GetAt(i)>=65)&&(GetAt(i)<=91))			ch1 = GetAt(i)+32;
			else										ch1 = GetAt(i);
			if((s1.GetAt(i)>=65)&&(s1.GetAt(i)<=91))	ch2 = s1.GetAt(i)+32;
			else										ch2 = s1.GetAt(i);
		}
		else {
			ch1 = GetAt(i);
			ch2 = s1.GetAt(i);
		}
		i++;
	} while(ch1==ch2);
	return (ch1<ch2);
}

long String::operator<=(const String &s1) {
	long		i=0;
	char		ch1,ch2;
	do {
		if(i==length) return true;
		if(sf&SF_IGNORECASE) {
			if((GetAt(i)>=65)&&(GetAt(i)<=91))			ch1 = GetAt(i)+32;
			else										ch1 = GetAt(i);
			if((s1.GetAt(i)>=65)&&(s1.GetAt(i)<=91))	ch2 = s1.GetAt(i)+32;
			else										ch2 = s1.GetAt(i);
		}
		else {
			ch1 = GetAt(i);
			ch2 = s1.GetAt(i);
		}
		i++;
	} while(ch1==ch2);
	return (ch1<=ch2);
}

long String::operator>(const String &s1) {
	long		i=0;
	char		ch1,ch2;
	do {
		if(i==length) return false;
		if(sf&SF_IGNORECASE) {
			if((GetAt(i)>=65)&&(GetAt(i)<=91))			ch1 = GetAt(i)+32;
			else										ch1 = GetAt(i);
			if((s1.GetAt(i)>=65)&&(s1.GetAt(i)<=91))	ch2 = s1.GetAt(i)+32;
			else										ch2 = s1.GetAt(i);
		}
		else {
			ch1 = GetAt(i);
			ch2 = s1.GetAt(i);
		}
		i++;
	} while(ch1==ch2);
	return (ch1>ch2);
}

long String::operator>=(const String &s1) {
	long		i=0;
	char		ch1,ch2;
	do {
		if(i==length) return true;
		if(sf&SF_IGNORECASE) {
			if((GetAt(i)>=65)&&(GetAt(i)<=91))			ch1 = GetAt(i)+32;
			else										ch1 = GetAt(i);
			if((s1.GetAt(i)>=65)&&(s1.GetAt(i)<=91))	ch2 = s1.GetAt(i)+32;
			else										ch2 = s1.GetAt(i);
		}
		else {
			ch1 = GetAt(i);
			ch2 = s1.GetAt(i);
		}
		i++;
	} while(ch1==ch2);
	return (ch1>=ch2);
}

char * String::CharPtr() const {
 return ptr;
}

//////////////////////////////////////////////////////////////////////////////
// Methoden
//////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////
String String::Insert(const String &Ins, long pos) {
	if((pos<0)||(pos>=length)) 
			throw OutOfBoundsException(
		   					"String::Insert('"+Ins+"',"+String(pos)+")",
								"Length of String is "+String(length)+". Out of range!!!"
						);
	String		temp;
	char		*InsPt,*OrgPt,*TempPt;
	temp.length = Ins.length+length;
	temp.ptr    = new char[temp.length+1];
	TempPt      = temp.ptr;
	OrgPt       = ptr;
	InsPt       = Ins.ptr;
	while(pos-->0)      *TempPt++ = *OrgPt++;
	while(*InsPt!='\0') *TempPt++ = *InsPt++;
	while(*OrgPt!='\0') *TempPt++ = *OrgPt++;
	*TempPt = '\0';
	return temp;
}

////////////////////////////////////////////////////////////////////////////////////
String String::Insert(char *InsPt, long pos) {
	if((pos<0)||(pos>=length)) 
			throw OutOfBoundsException(
		   					"String::Insert('"+String(InsPt)+"',"+String(pos)+")",
								"Length of String is "+String(length)+". Out of range!!!"
						);
	String		temp;
	char		*OrgPt,*TempPt;
	temp.length = strlen(InsPt)+length;
	temp.ptr    = new char[temp.length+1];
	TempPt      = temp.ptr;
	OrgPt       = ptr;
	while(pos-->0)      *TempPt++ = *OrgPt++;
	while(*InsPt!='\0') *TempPt++ = *InsPt++;
	while(*OrgPt!='\0') *TempPt++ = *OrgPt++;
	*TempPt = '\0';
	return		temp;
}


////////////////////////////////////////////////////////////////////////////////////
String String::Delete(long pos, long number) {
	if((pos<0)||(pos>=length)||(pos+number>length)||(number<=0))
			throw OutOfBoundsException(
		   					"String::Delete('"+String(pos)+"',"+String(number)+")",
								"Length of String is "+String(length)+". Out of range!!!"
						);
	String		temp;
	long			i;
	char		*tempPt,*cpPt;
	temp.length = length-number;
	temp.ptr    = new char[temp.length+1];
	tempPt      = temp.ptr;
	cpPt        = ptr;
	i           = pos;
	while(i-->0) *tempPt++ = *cpPt++;
	cpPt        = ptr+pos+number;
	while((*cpPt)!='\0') *tempPt++ = *cpPt++;
	*tempPt = '\0';
	return temp;
}

////////////////////////////////////////////////////////////////////////////////////
long String::Find(const String & search, long start) const {
	char		*pt,*tpt,*spt;
	if(length==0) return -1;
	if((start<0)||(start>=length))
			throw OutOfBoundsException(
							"String::Find('"+search+"',"+String(start)+")",
							"Length of String is "+String(length)+". Out of range !!!"
						);
	pt = ptr+start;
	while(*pt != '\0') {
		spt  = search.ptr;
		tpt  = pt;
		while(*tpt++ == *spt++) {
			if(*spt == '\0') return (pt-ptr);
			if(*tpt == '\0') return -1;
		}
		pt++;
	}
	return -1;
}

////////////////////////////////////////////////////////////////////////////////////
long String::Find(char * search,long start) const {
	char		*pt,*tpt,*spt;
	if(length==0) return -1;
	if((start<0)||(start>=length))
			throw OutOfBoundsException(
							"String::Find('"+String(search)+"',"+String(start)+")",
							"Length of String is "+String(length)+". Out of range !!!"
						);
	pt = ptr+start;
	while(*pt != '\0') {
		spt  = search;
		tpt  = pt;
		while(*tpt++ == *spt++) {
			if(*spt == '\0') return (pt-ptr);
			if(*tpt == '\0') return -1;
		}
		pt++;
	}
	return -1;
}

////////////////////////////////////////////////////////////////////////////////////
long String::ReverseFind(const String & search, long start) const {
	char		*pt,*tpt,*spt;
	if(start==0) start = GetLength();
	if((start<0)||(start>=length))
			throw OutOfBoundsException(
							"String::ReverseFind('"+search+"',"+String(start)+")",
							"Length of String is "+String(length)+". Out of range !!!"
						);
	pt = ptr+start;
	while(pt != ptr) {
		spt  = search.ptr;
		tpt  = pt;
		while(*tpt++ == *spt++) {
			if(*spt == '\0') return (pt-ptr);
			if(*tpt == '\0') return -1;
		}
		pt--;
	}
	return -1;
}

////////////////////////////////////////////////////////////////////////////////////
long String::ReverseFind(char * search,long start) const {
	char		*pt,*tpt,*spt;
	if(start==0) start = GetLength();
	if((start<0)||(start>=length))
			throw OutOfBoundsException(
							"String::ReverseFind('"+String(search)+"',"+String(start)+")",
							"Length of String is "+String(length)+". Out of range !!!"
						);
	pt = ptr+start;
	while(pt != ptr) {
		spt  = search;
		tpt  = pt;
		while(*tpt++ == *spt++) {
			if(*spt == '\0') return (pt-ptr);
			if(*tpt == '\0') return -1;
		}
		pt--;
	}
	return -1;
}

////////////////////////////////////////////////////////////////////////////////////
long String::FindOneOf(char * search,long start) const {
	char		*pt,*spt;
	if((start<0)||(start>=length))
			throw OutOfBoundsException(
							"String::FindOneOf('"+String(search)+"',"+String(start)+")",
							"Length of String is "+String(length)+". Out of range !!!"
						);
	pt = ptr+start;
	while(*pt != '\0') {
		for(spt=search;*spt!='\0';spt++) {
			if(*spt == *pt) return (pt-ptr);
		}
		pt++;
	}
	return -1;
}

////////////////////////////////////////////////////////////////////////////////////
long String::FindOneOf(const String& search,long start) const {
	char		*pt,*spt;
	if((start<0)||(start>=length))
			throw OutOfBoundsException(
							"String::FindOneOf('"+search+"',"+String(start)+")",
							"Length of String is "+String(length)+". Out of range !!!"
						);
	pt = ptr+start;
	while(*pt != '\0') {
		for(spt=search.ptr;*spt!='\0';spt++) {
			if(*spt == *pt) return (pt-ptr);
		}
		pt++;
	}
	return -1;
}

////////////////////////////////////////////////////////////////////////////////////
long String::ReverseFindOneOf(char * search,long start) const {
	char		*pt,*spt;
	if(start==0) start = GetLength();
	if((start<0)||(start>=length))
			throw OutOfBoundsException(
							"String::ReverseFindOneOf('"+String(search)+"',"+String(start)+")",
							"Length of String is "+String(length)+". Out of range !!!"
						);
	pt = ptr+start;
	while(pt != ptr) {
		for(spt=search;*spt!='\0';spt++) {
			if(*spt == *pt) return (pt-ptr);
		}
		pt--;
	}
	return -1;
}

////////////////////////////////////////////////////////////////////////////////////
long String::ReverseFindOneOf(const String& search,long start) const {
	char		*pt,*spt;
	if(start==0) start = GetLength();
	if((start<0)||(start>=length))
			throw OutOfBoundsException(
							"String::ReverseFindOneOf('"+search+"',"+String(start)+")",
							"Length of String is "+String(length)+". Out of range !!!"
						);
	pt = ptr+start;
	while(pt != ptr) {
		for(spt=search.ptr;*spt!='\0';spt++) {
			if(*spt == *pt) return (pt-ptr);
		}
		pt--;
	}
	return -1;
}

////////////////////////////////////////////////////////////////////////////////////
String String::Mid(long pos,long count) const {
	if((pos<0)||(count<0)||(pos+count>length))
			throw OutOfBoundsException(
							"String::Mid("+String(pos)+","+String(count)+")",
							"Length of String is "+String(length)+". Out of range !!!"
						);
	String	ret;
	ret.ptr = new char[count+1];
	ret.length = count;
	memcpy(ret.ptr,ptr+pos,count);
	*(ret.ptr+count) = '\0';
	return ret;
}

////////////////////////////////////////////////////////////////////////////////////
String String::LowerCase() {
	String ret = *this;
	for(long i=0;i<GetLength();i++) {
		if((GetAt(i)>=65)&&(GetAt(i)<=91)) ret.GetAt(i) = GetAt(i)+32;
	}
	return ret;
}

////////////////////////////////////////////////////////////////////////////////////
String String::UpperCase() {
	String ret = *this;
	for(long i=0;i<GetLength();i++) {
		if((GetAt(i)>=97)&&(GetAt(i)<=123)) ret.GetAt(i) = GetAt(i)-32;
	}
	return ret;
}

#define TAB 9

////////////////////////////////////////////////////////////////////////////////////
String String::TrimLeft() {
	String ret;
	long		i=0;
	while( *(ptr+i)==' ' || *(ptr+i)==TAB ) i++;
	ret.length = length-i;
	delete ret.ptr;
	ret.ptr = new char[ret.length+1];
	memcpy(ret.ptr,ptr+i,ret.length+1);
	*(ret.ptr+ret.length)='\0';
	return ret;
}

////////////////////////////////////////////////////////////////////////////////////
String String::TrimRight() {
	String ret;
	long		i=length-1;
	while( i>0 && (*(ptr+i)==' ' || *(ptr+i)==TAB) ) i--;
	if(i>=0) {
		ret.length = i+1;
		delete ret.ptr;
		ret.ptr = new char[ret.length+1];
		memcpy(ret.ptr,ptr,ret.length);
		*(ret.ptr+ret.length)='\0';
	}
	return ret;
}

////////////////////////////////////////////////////////////////////////////////////
String String::Trim() {
	String ret;
	ret = TrimLeft();
	ret = ret.TrimRight();
	return ret;
}

////////////////////////////////////////////////////////////////////////////////////
long String::Hash(long mod) {
	long	ret = 0;
	char*	p=ptr;
	while(*p) {
		ret <<= 1;
		ret ^= *(p++);
	}
	return ret%mod;
}

////////////////////////////////////////////////////////////////////////////////////
long String::MatchesWildcard(const String &wildcardexp) const {
	long searchfrom=0,searchto=0; // start and end position of matching text fragment
	long foundfrom=0,foundto=0;
	while( (searchfrom<wildcardexp.GetLength()) && (foundfrom<GetLength()) ) {
		// ueber alle '*' laufen -> Anfang eines Textsegments 
		while( (wildcardexp.GetAt(searchfrom) == '*') && (searchfrom<wildcardexp.GetLength() ) ) 
				searchfrom++;
		// ueber Text laufen bis zum naechsten '*' -> Ende eines Textsegmentes
		searchto = searchfrom;
		while( (wildcardexp.GetAt(searchto) != '*') && (searchto<wildcardexp.GetLength() ) ) 
				searchto++;
		// Textsegment im String suchen
		if(searchto-searchfrom>0) {
			String seg = wildcardexp.Mid(searchfrom,searchto-searchfrom); 
			if( (foundfrom=Find(seg,foundfrom)) == -1 ) return 0;
			// zwei Spezialfaelle
			// 1.) Segment steht am Anfang -> Segment muss auch im String am Anfang stehen
			if( (searchfrom == 0) && (foundfrom != 0) ) return 0;
			// 2.) Segment steht am Ende -> Segment muss auch im String am Ende stehen
			if( (searchto == wildcardexp.GetLength()) 
				  && (foundfrom+seg.GetLength() != GetLength()) ) return 0;
		}
		// weiter im Text
		foundfrom  += searchto-searchfrom;
		// weiter im Wildcardtext
		searchfrom = searchto+1;
	}
	return 1; 
} 


//long String::GetLength() {return length;}
}
