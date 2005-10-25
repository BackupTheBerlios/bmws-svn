/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.7</a>, using an XML
 * Schema.
 * $Id$
 */

package de.mbws.server.configuration;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class Zoneserver.
 * 
 * @version $Revision$ $Date$
 */
public class Zoneserver implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _gameserverip
     */
    private java.lang.String _gameserverip;

    /**
     * Field _gameserverport
     */
    private int _gameserverport;

    /**
     * keeps track of state for field: _gameserverport
     */
    private boolean _has_gameserverport;

    /**
     * Field _mapid
     */
    private int _mapid;

    /**
     * keeps track of state for field: _mapid
     */
    private boolean _has_mapid;

    /**
     * Field _port
     */
    private int _port;

    /**
     * keeps track of state for field: _port
     */
    private boolean _has_port;

    /**
     * Field _startup
     */
    private boolean _startup;

    /**
     * keeps track of state for field: _startup
     */
    private boolean _has_startup;


      //----------------/
     //- Constructors -/
    //----------------/

    public Zoneserver() 
     {
        super();
    } //-- de.mbws.server.configuration.Zoneserver()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteGameserverport
     * 
     */
    public void deleteGameserverport()
    {
        this._has_gameserverport= false;
    } //-- void deleteGameserverport() 

    /**
     * Method deleteMapid
     * 
     */
    public void deleteMapid()
    {
        this._has_mapid= false;
    } //-- void deleteMapid() 

    /**
     * Method deletePort
     * 
     */
    public void deletePort()
    {
        this._has_port= false;
    } //-- void deletePort() 

    /**
     * Method deleteStartup
     * 
     */
    public void deleteStartup()
    {
        this._has_startup= false;
    } //-- void deleteStartup() 

    /**
     * Returns the value of field 'gameserverip'.
     * 
     * @return String
     * @return the value of field 'gameserverip'.
     */
    public java.lang.String getGameserverip()
    {
        return this._gameserverip;
    } //-- java.lang.String getGameserverip() 

    /**
     * Returns the value of field 'gameserverport'.
     * 
     * @return int
     * @return the value of field 'gameserverport'.
     */
    public int getGameserverport()
    {
        return this._gameserverport;
    } //-- int getGameserverport() 

    /**
     * Returns the value of field 'mapid'.
     * 
     * @return int
     * @return the value of field 'mapid'.
     */
    public int getMapid()
    {
        return this._mapid;
    } //-- int getMapid() 

    /**
     * Returns the value of field 'name'.
     * 
     * @return String
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName() 

    /**
     * Returns the value of field 'port'.
     * 
     * @return int
     * @return the value of field 'port'.
     */
    public int getPort()
    {
        return this._port;
    } //-- int getPort() 

    /**
     * Returns the value of field 'startup'.
     * 
     * @return boolean
     * @return the value of field 'startup'.
     */
    public boolean getStartup()
    {
        return this._startup;
    } //-- boolean getStartup() 

    /**
     * Method hasGameserverport
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasGameserverport()
    {
        return this._has_gameserverport;
    } //-- boolean hasGameserverport() 

    /**
     * Method hasMapid
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasMapid()
    {
        return this._has_mapid;
    } //-- boolean hasMapid() 

    /**
     * Method hasPort
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasPort()
    {
        return this._has_port;
    } //-- boolean hasPort() 

    /**
     * Method hasStartup
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasStartup()
    {
        return this._has_startup;
    } //-- boolean hasStartup() 

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
     * Sets the value of field 'gameserverip'.
     * 
     * @param gameserverip the value of field 'gameserverip'.
     */
    public void setGameserverip(java.lang.String gameserverip)
    {
        this._gameserverip = gameserverip;
    } //-- void setGameserverip(java.lang.String) 

    /**
     * Sets the value of field 'gameserverport'.
     * 
     * @param gameserverport the value of field 'gameserverport'.
     */
    public void setGameserverport(int gameserverport)
    {
        this._gameserverport = gameserverport;
        this._has_gameserverport = true;
    } //-- void setGameserverport(int) 

    /**
     * Sets the value of field 'mapid'.
     * 
     * @param mapid the value of field 'mapid'.
     */
    public void setMapid(int mapid)
    {
        this._mapid = mapid;
        this._has_mapid = true;
    } //-- void setMapid(int) 

    /**
     * Sets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
    } //-- void setName(java.lang.String) 

    /**
     * Sets the value of field 'port'.
     * 
     * @param port the value of field 'port'.
     */
    public void setPort(int port)
    {
        this._port = port;
        this._has_port = true;
    } //-- void setPort(int) 

    /**
     * Sets the value of field 'startup'.
     * 
     * @param startup the value of field 'startup'.
     */
    public void setStartup(boolean startup)
    {
        this._startup = startup;
        this._has_startup = true;
    } //-- void setStartup(boolean) 

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
        return (de.mbws.server.configuration.Zoneserver) Unmarshaller.unmarshal(de.mbws.server.configuration.Zoneserver.class, reader);
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
