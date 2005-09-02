////////////////////////////////////////////////////////////////////////
// excclass.h
//
// (C)2000 by Axel Sammet
//
// Basisklasse für abgeleitete Exceptions
//
////////////////////////////////////////////////////////////////////////
#ifndef EXCCLASS_H
#define EXCCLASS_H

#include "string.h"

namespace util {

class Exception {
public:
	String		method;
	String		message;

	Exception() {
		method="Exception";
		message="Oups, an exception occurred";
	}
	Exception(String met,String mes) {
		method  = met; 
		message = mes;
	}
	virtual String ToString() {
		return String("exception in method ")+method
					+String("\n           ")+message+String("\n");
	}
	
	operator	PTRCHAR() {return ToString();};    // will not be called
};


class OutOfMemoryException : public Exception {
public:
	OutOfMemoryException(String met, String mes) {
		method  = met; 
		message = mes;
	}; 
};


class OutOfBoundsException : public Exception {
public:
	OutOfBoundsException(String met, String mes) {
		method  = met; 
		message = mes;
	}; 
};

	
class IOException : public Exception {
public:
	IOException(String met, String mes) {
		method  = met; 
		message = mes;
	}; 
};
	

#define ExceptionString(exc) (String("Exception caught in ")+__FILE__+\
						String(" line: ")+String(__LINE__)+String(", method ") \
						+exc.method+String("\n")+exc.message+String("\n"))
}
	
#endif


