/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.7</a>, using an XML
 * Schema.
 * $Id$
 */

package de.mbws.common.data.xml;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.ArrayList;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Races
 *  
 * 
 * @version $Revision$ $Date$
 */
public class RacesDocument implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _raceElementList
     */
    private java.util.ArrayList _raceElementList;


      //----------------/
     //- Constructors -/
    //----------------/

    public RacesDocument() 
     {
        super();
        _raceElementList = new ArrayList();
    } //-- de.mbws.common.data.xml.RacesDocument()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addRaceElement
     * 
     * 
     * 
     * @param vRaceElement
     */
    public void addRaceElement(de.mbws.common.data.xml.RaceElement vRaceElement)
        throws java.lang.IndexOutOfBoundsException
    {
        _raceElementList.add(vRaceElement);
    } //-- void addRaceElement(de.mbws.common.data.xml.RaceElement) 

    /**
     * Method addRaceElement
     * 
     * 
     * 
     * @param index
     * @param vRaceElement
     */
    public void addRaceElement(int index, de.mbws.common.data.xml.RaceElement vRaceElement)
        throws java.lang.IndexOutOfBoundsException
    {
        _raceElementList.add(index, vRaceElement);
    } //-- void addRaceElement(int, de.mbws.common.data.xml.RaceElement) 

    /**
     * Method clearRaceElement
     * 
     */
    public void clearRaceElement()
    {
        _raceElementList.clear();
    } //-- void clearRaceElement() 

    /**
     * Method enumerateRaceElement
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateRaceElement()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_raceElementList.iterator());
    } //-- java.util.Enumeration enumerateRaceElement() 

    /**
     * Method getRaceElement
     * 
     * 
     * 
     * @param index
     * @return RaceElement
     */
    public de.mbws.common.data.xml.RaceElement getRaceElement(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _raceElementList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (de.mbws.common.data.xml.RaceElement) _raceElementList.get(index);
    } //-- de.mbws.common.data.xml.RaceElement getRaceElement(int) 

    /**
     * Method getRaceElement
     * 
     * 
     * 
     * @return RaceElement
     */
    public de.mbws.common.data.xml.RaceElement[] getRaceElement()
    {
        int size = _raceElementList.size();
        de.mbws.common.data.xml.RaceElement[] mArray = new de.mbws.common.data.xml.RaceElement[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (de.mbws.common.data.xml.RaceElement) _raceElementList.get(index);
        }
        return mArray;
    } //-- de.mbws.common.data.xml.RaceElement[] getRaceElement() 

    /**
     * Method getRaceElementCount
     * 
     * 
     * 
     * @return int
     */
    public int getRaceElementCount()
    {
        return _raceElementList.size();
    } //-- int getRaceElementCount() 

    /**
     * Method isValid
     * 
     * 
     * 
     * @return boolean
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * Method marshal
     * 
     * 
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * 
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Method removeRaceElement
     * 
     * 
     * 
     * @param vRaceElement
     * @return boolean
     */
    public boolean removeRaceElement(de.mbws.common.data.xml.RaceElement vRaceElement)
    {
        boolean removed = _raceElementList.remove(vRaceElement);
        return removed;
    } //-- boolean removeRaceElement(de.mbws.common.data.xml.RaceElement) 

    /**
     * Method setRaceElement
     * 
     * 
     * 
     * @param index
     * @param vRaceElement
     */
    public void setRaceElement(int index, de.mbws.common.data.xml.RaceElement vRaceElement)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _raceElementList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _raceElementList.set(index, vRaceElement);
    } //-- void setRaceElement(int, de.mbws.common.data.xml.RaceElement) 

    /**
     * Method setRaceElement
     * 
     * 
     * 
     * @param raceElementArray
     */
    public void setRaceElement(de.mbws.common.data.xml.RaceElement[] raceElementArray)
    {
        //-- copy array
        _raceElementList.clear();
        for (int i = 0; i < raceElementArray.length; i++) {
            _raceElementList.add(raceElementArray[i]);
        }
    } //-- void setRaceElement(de.mbws.common.data.xml.RaceElement) 

    /**
     * Method unmarshal
     * 
     * 
     * 
     * @param reader
     * @return Object
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (de.mbws.common.data.xml.RacesDocument) Unmarshaller.unmarshal(de.mbws.common.data.xml.RacesDocument.class, reader);
    } //-- java.lang.Object unmarshal(java.io.Reader) 

    /**
     * Method validate
     * 
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
