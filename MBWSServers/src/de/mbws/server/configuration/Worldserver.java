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

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class Worldserver.
 * 
 * @version $Revision$ $Date$
 */
public class Worldserver implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _basicAttributes
     */
    private de.mbws.server.configuration.BasicAttributes _basicAttributes;

    /**
     * Field _eventControllers
     */
    private de.mbws.server.configuration.EventControllers _eventControllers;

    /**
     * Field _myclientip
     */
    private java.lang.String _myclientip;

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

    public Worldserver() 
     {
        super();
    } //-- de.mbws.server.configuration.Worldserver()


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
     * Returns the value of field 'basicAttributes'.
     * 
     * @return BasicAttributes
     * @return the value of field 'basicAttributes'.
     */
    public de.mbws.server.configuration.BasicAttributes getBasicAttributes()
    {
        return this._basicAttributes;
    } //-- de.mbws.server.configuration.BasicAttributes getBasicAttributes() 

    /**
     * Returns the value of field 'eventControllers'.
     * 
     * @return EventControllers
     * @return the value of field 'eventControllers'.
     */
    public de.mbws.server.configuration.EventControllers getEventControllers()
    {
        return this._eventControllers;
    } //-- de.mbws.server.configuration.EventControllers getEventControllers() 

    /**
     * Returns the value of field 'myclientip'.
     * 
     * @return String
     * @return the value of field 'myclientip'.
     */
    public java.lang.String getMyclientip()
    {
        return this._myclientip;
    } //-- java.lang.String getMyclientip() 

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
     * Sets the value of field 'basicAttributes'.
     * 
     * @param basicAttributes the value of field 'basicAttributes'.
     */
    public void setBasicAttributes(de.mbws.server.configuration.BasicAttributes basicAttributes)
    {
        this._basicAttributes = basicAttributes;
    } //-- void setBasicAttributes(de.mbws.server.configuration.BasicAttributes) 

    /**
     * Sets the value of field 'eventControllers'.
     * 
     * @param eventControllers the value of field 'eventControllers'
     */
    public void setEventControllers(de.mbws.server.configuration.EventControllers eventControllers)
    {
        this._eventControllers = eventControllers;
    } //-- void setEventControllers(de.mbws.server.configuration.EventControllers) 

    /**
     * Sets the value of field 'myclientip'.
     * 
     * @param myclientip the value of field 'myclientip'.
     */
    public void setMyclientip(java.lang.String myclientip)
    {
        this._myclientip = myclientip;
    } //-- void setMyclientip(java.lang.String) 

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
        return (de.mbws.server.configuration.Worldserver) Unmarshaller.unmarshal(de.mbws.server.configuration.Worldserver.class, reader);
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
