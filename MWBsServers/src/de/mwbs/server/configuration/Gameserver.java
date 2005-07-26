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

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class Gameserver.
 * 
 * @version $Revision$ $Date$
 */
public class Gameserver implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

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

    /**
     * Field _accountserverip
     */
    private java.lang.String _accountserverip;

    /**
     * Field _accountserverport
     */
    private int _accountserverport;

    /**
     * keeps track of state for field: _accountserverport
     */
    private boolean _has_accountserverport;


      //----------------/
     //- Constructors -/
    //----------------/

    public Gameserver() 
     {
        super();
    } //-- de.mwbs.server.configuration.Gameserver()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteAccountserverport
     * 
     */
    public void deleteAccountserverport()
    {
        this._has_accountserverport= false;
    } //-- void deleteAccountserverport() 

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
     * Returns the value of field 'accountserverip'.
     * 
     * @return String
     * @return the value of field 'accountserverip'.
     */
    public java.lang.String getAccountserverip()
    {
        return this._accountserverip;
    } //-- java.lang.String getAccountserverip() 

    /**
     * Returns the value of field 'accountserverport'.
     * 
     * @return int
     * @return the value of field 'accountserverport'.
     */
    public int getAccountserverport()
    {
        return this._accountserverport;
    } //-- int getAccountserverport() 

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
     * Method hasAccountserverport
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasAccountserverport()
    {
        return this._has_accountserverport;
    } //-- boolean hasAccountserverport() 

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
     * Sets the value of field 'accountserverip'.
     * 
     * @param accountserverip the value of field 'accountserverip'.
     */
    public void setAccountserverip(java.lang.String accountserverip)
    {
        this._accountserverip = accountserverip;
    } //-- void setAccountserverip(java.lang.String) 

    /**
     * Sets the value of field 'accountserverport'.
     * 
     * @param accountserverport the value of field
     * 'accountserverport'.
     */
    public void setAccountserverport(int accountserverport)
    {
        this._accountserverport = accountserverport;
        this._has_accountserverport = true;
    } //-- void setAccountserverport(int) 

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
        return (de.mwbs.server.configuration.Gameserver) Unmarshaller.unmarshal(de.mwbs.server.configuration.Gameserver.class, reader);
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
