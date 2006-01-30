/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.7</a>, using an XML
 * Schema.
 * $Id$
 */

package de.mbws.common.data.race;

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
public class Races implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _raceList
     */
    private java.util.ArrayList _raceList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Races() 
     {
        super();
        _raceList = new ArrayList();
    } //-- de.mbws.common.data.race.Races()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addRace
     * 
     * 
     * 
     * @param vRace
     */
    public void addRace(de.mbws.common.data.race.Race vRace)
        throws java.lang.IndexOutOfBoundsException
    {
        _raceList.add(vRace);
    } //-- void addRace(de.mbws.common.data.race.Race) 

    /**
     * Method addRace
     * 
     * 
     * 
     * @param index
     * @param vRace
     */
    public void addRace(int index, de.mbws.common.data.race.Race vRace)
        throws java.lang.IndexOutOfBoundsException
    {
        _raceList.add(index, vRace);
    } //-- void addRace(int, de.mbws.common.data.race.Race) 

    /**
     * Method clearRace
     * 
     */
    public void clearRace()
    {
        _raceList.clear();
    } //-- void clearRace() 

    /**
     * Method enumerateRace
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateRace()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_raceList.iterator());
    } //-- java.util.Enumeration enumerateRace() 

    /**
     * Method getRace
     * 
     * 
     * 
     * @param index
     * @return Race
     */
    public de.mbws.common.data.race.Race getRace(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _raceList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (de.mbws.common.data.race.Race) _raceList.get(index);
    } //-- de.mbws.common.data.race.Race getRace(int) 

    /**
     * Method getRace
     * 
     * 
     * 
     * @return Race
     */
    public de.mbws.common.data.race.Race[] getRace()
    {
        int size = _raceList.size();
        de.mbws.common.data.race.Race[] mArray = new de.mbws.common.data.race.Race[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (de.mbws.common.data.race.Race) _raceList.get(index);
        }
        return mArray;
    } //-- de.mbws.common.data.race.Race[] getRace() 

    /**
     * Method getRaceCount
     * 
     * 
     * 
     * @return int
     */
    public int getRaceCount()
    {
        return _raceList.size();
    } //-- int getRaceCount() 

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
     * Method removeRace
     * 
     * 
     * 
     * @param vRace
     * @return boolean
     */
    public boolean removeRace(de.mbws.common.data.race.Race vRace)
    {
        boolean removed = _raceList.remove(vRace);
        return removed;
    } //-- boolean removeRace(de.mbws.common.data.race.Race) 

    /**
     * Method setRace
     * 
     * 
     * 
     * @param index
     * @param vRace
     */
    public void setRace(int index, de.mbws.common.data.race.Race vRace)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _raceList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _raceList.set(index, vRace);
    } //-- void setRace(int, de.mbws.common.data.race.Race) 

    /**
     * Method setRace
     * 
     * 
     * 
     * @param raceArray
     */
    public void setRace(de.mbws.common.data.race.Race[] raceArray)
    {
        //-- copy array
        _raceList.clear();
        for (int i = 0; i < raceArray.length; i++) {
            _raceList.add(raceArray[i]);
        }
    } //-- void setRace(de.mbws.common.data.race.Race) 

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
        return (de.mbws.common.data.race.Races) Unmarshaller.unmarshal(de.mbws.common.data.race.Races.class, reader);
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
