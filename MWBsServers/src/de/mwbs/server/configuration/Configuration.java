/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.7</a>, using an XML
 * Schema.
 * $Id$
 */

package de.mwbs.server.configuration;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.ArrayList;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Configuration of the MWB System
 *  
 * 
 * @version $Revision$ $Date$
 */
public class Configuration implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _accountserver
     */
    private de.mwbs.server.configuration.Accountserver _accountserver;

    /**
     * Field _gameserver
     */
    private de.mwbs.server.configuration.Gameserver _gameserver;

    /**
     * Field _zoneserverList
     */
    private java.util.ArrayList _zoneserverList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Configuration() 
     {
        super();
        _zoneserverList = new ArrayList();
    } //-- de.mwbs.server.configuration.Configuration()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addZoneserver
     * 
     * 
     * 
     * @param vZoneserver
     */
    public void addZoneserver(de.mwbs.server.configuration.Zoneserver vZoneserver)
        throws java.lang.IndexOutOfBoundsException
    {
        _zoneserverList.add(vZoneserver);
    } //-- void addZoneserver(de.mwbs.server.configuration.Zoneserver) 

    /**
     * Method addZoneserver
     * 
     * 
     * 
     * @param index
     * @param vZoneserver
     */
    public void addZoneserver(int index, de.mwbs.server.configuration.Zoneserver vZoneserver)
        throws java.lang.IndexOutOfBoundsException
    {
        _zoneserverList.add(index, vZoneserver);
    } //-- void addZoneserver(int, de.mwbs.server.configuration.Zoneserver) 

    /**
     * Method clearZoneserver
     * 
     */
    public void clearZoneserver()
    {
        _zoneserverList.clear();
    } //-- void clearZoneserver() 

    /**
     * Method enumerateZoneserver
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateZoneserver()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_zoneserverList.iterator());
    } //-- java.util.Enumeration enumerateZoneserver() 

    /**
     * Returns the value of field 'accountserver'.
     * 
     * @return Accountserver
     * @return the value of field 'accountserver'.
     */
    public de.mwbs.server.configuration.Accountserver getAccountserver()
    {
        return this._accountserver;
    } //-- de.mwbs.server.configuration.Accountserver getAccountserver() 

    /**
     * Returns the value of field 'gameserver'.
     * 
     * @return Gameserver
     * @return the value of field 'gameserver'.
     */
    public de.mwbs.server.configuration.Gameserver getGameserver()
    {
        return this._gameserver;
    } //-- de.mwbs.server.configuration.Gameserver getGameserver() 

    /**
     * Method getZoneserver
     * 
     * 
     * 
     * @param index
     * @return Zoneserver
     */
    public de.mwbs.server.configuration.Zoneserver getZoneserver(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _zoneserverList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (de.mwbs.server.configuration.Zoneserver) _zoneserverList.get(index);
    } //-- de.mwbs.server.configuration.Zoneserver getZoneserver(int) 

    /**
     * Method getZoneserver
     * 
     * 
     * 
     * @return Zoneserver
     */
    public de.mwbs.server.configuration.Zoneserver[] getZoneserver()
    {
        int size = _zoneserverList.size();
        de.mwbs.server.configuration.Zoneserver[] mArray = new de.mwbs.server.configuration.Zoneserver[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (de.mwbs.server.configuration.Zoneserver) _zoneserverList.get(index);
        }
        return mArray;
    } //-- de.mwbs.server.configuration.Zoneserver[] getZoneserver() 

    /**
     * Method getZoneserverCount
     * 
     * 
     * 
     * @return int
     */
    public int getZoneserverCount()
    {
        return _zoneserverList.size();
    } //-- int getZoneserverCount() 

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
     * Method removeZoneserver
     * 
     * 
     * 
     * @param vZoneserver
     * @return boolean
     */
    public boolean removeZoneserver(de.mwbs.server.configuration.Zoneserver vZoneserver)
    {
        boolean removed = _zoneserverList.remove(vZoneserver);
        return removed;
    } //-- boolean removeZoneserver(de.mwbs.server.configuration.Zoneserver) 

    /**
     * Sets the value of field 'accountserver'.
     * 
     * @param accountserver the value of field 'accountserver'.
     */
    public void setAccountserver(de.mwbs.server.configuration.Accountserver accountserver)
    {
        this._accountserver = accountserver;
    } //-- void setAccountserver(de.mwbs.server.configuration.Accountserver) 

    /**
     * Sets the value of field 'gameserver'.
     * 
     * @param gameserver the value of field 'gameserver'.
     */
    public void setGameserver(de.mwbs.server.configuration.Gameserver gameserver)
    {
        this._gameserver = gameserver;
    } //-- void setGameserver(de.mwbs.server.configuration.Gameserver) 

    /**
     * Method setZoneserver
     * 
     * 
     * 
     * @param index
     * @param vZoneserver
     */
    public void setZoneserver(int index, de.mwbs.server.configuration.Zoneserver vZoneserver)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _zoneserverList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _zoneserverList.set(index, vZoneserver);
    } //-- void setZoneserver(int, de.mwbs.server.configuration.Zoneserver) 

    /**
     * Method setZoneserver
     * 
     * 
     * 
     * @param zoneserverArray
     */
    public void setZoneserver(de.mwbs.server.configuration.Zoneserver[] zoneserverArray)
    {
        //-- copy array
        _zoneserverList.clear();
        for (int i = 0; i < zoneserverArray.length; i++) {
            _zoneserverList.add(zoneserverArray[i]);
        }
    } //-- void setZoneserver(de.mwbs.server.configuration.Zoneserver) 

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
        return (de.mwbs.server.configuration.Configuration) Unmarshaller.unmarshal(de.mwbs.server.configuration.Configuration.class, reader);
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
