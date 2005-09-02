//////////////////////////////////////////////////////////////////////
// String.h
//
// (C)2000 by Axel Sammet
//
// generische Stringklasse
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_STRING_H__469C3AD6_1AF1_11D2_94AB_0004ACEE0A95__INCLUDED_)
#define AFX_STRING_H__469C3AD6_1AF1_11D2_94AB_0004ACEE0A95__INCLUDED_

#if _MSC_VER >= 1000
#pragma once
#endif // _MSC_VER >= 1000

#include <iostream>

namespace util {

/////////////////////////////////////////////////////////////////////
// class String

enum SortFlags {
	SF_IGNORECASE = 0x0000000000000001
};


typedef char* PTRCHAR;
typedef const char* CPCHAR;

class String  {
protected:
	char		*ptr;
	long		length;
	SortFlags	sf;

public:
	// Konstruktoren, Destruktor
	String();
	String(const char ch);
	String(const String &str);
	String(char *str);
	String(const char *str);
	String(int);
	String(unsigned int);
	String(long);
	String(double);
	virtual ~String();

	// Methoden
	// Suchbefehle geben -1, wenn nichts gefunden wird
	long		Find(char *search,long start =0) const;
	long		Find(const String &search,long start = 0) const;
	long		ReverseFind(char *search,long start=0) const;								// default starts from end
	long		ReverseFind(const String &search,long start=0) const;
	long		FindOneOf(const String &search,long start = 0) const;
	long		FindOneOf(char *search,long start = 0) const;
	long		ReverseFindOneOf(const String &search,long start=0) const;
	long		ReverseFindOneOf(char *search,long start=0) const;
	long		MatchesWildcard(const String &wildcardexp) const;
	String		Delete(long pos,long count);
	String		Insert(char *,long pos);
	String		Insert(const String &,long pos);
	long		GetLength() const {return length;};
	long		Size() const {return length;};
	char*		CharPtr() const;
	char&		GetAt(long i) const {return *(ptr+i);};
	String		Mid(long pos,long count) const;
	String		LowerCase();
	String		UpperCase();
	String		TrimLeft();
	String		TrimRight();
	String		Trim();
	long		Hash(long mod);
//	void		SetCompareOptions();
 
	// Operatoren
	long		operator!=(const String &);
	long		operator!=(const char * );
	long		operator==(const String &);
	long		operator==(const char * );
	String		operator=(const char *);
	String		operator=(const String&);
	String		operator+=(const String &s);
	String		operator+=(const char *str);
	long		operator>(const String &);
	long		operator<(const String &);
	long		operator>=(const String &);
	long		operator<=(const String &);
	char&		operator [ ](long i);
	operator	PTRCHAR() {return ptr;};
//	operator CPCHAR() {return ptr;};

	// Freunde
	friend String operator + (const String &,const String &);
	friend String operator + (const char *,const String &);
	friend String operator + (const String &,const char *);
//	friend ostream &operator << (ostream &os,const String &str);
//	friend istream &operator >> (istream &is,String &str);

};

}
#endif // !defined(AFX_STRING_H__469C3AD6_1AF1_11D2_94AB_0004ACEE0A95__INCLUDED_)
