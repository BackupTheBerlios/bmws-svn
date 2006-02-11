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
 * Class Accountserver.
 * 
 * @version $Revision$ $Date$
 */
public class Accountserver implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _basicAttributes
     */
    private de.mbws.server.configuration.BasicAttributes _basicAttributes;

    /**
     * Field _s2sport
     */
    private int _s2sport;

    /**
     * keeps track of state for field: _s2sport
     */
    private boolean _has_s2sport;

    /**
     * Field _eventControllers
     */
    private de.mbws.server.configuration.EventControllers _eventControllers;


      //----------------/
     //- Constructors -/
    //----------------/

    public Accountserver() 
     {
        super();
    } //-- de.mbws.server.configuration.Accountserver()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteS2sport
     * 
     */
    public void deleteS2sport()
    {
        this._has_s2sport= false;
    } //-- void deleteS2sport() 

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
     * Returns the value of field 's2sport'.
     * 
     * @return int
     * @return the value of field 's2sport'.
     */
    public int getS2sport()
    {
        return this._s2sport;
    } //-- int getS2sport() 

    /**
     * Method hasS2sport
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasS2sport()
    {
        return this._has_s2sport;
    } //-- boolean hasS2sport() 

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
     * Sets the value of field 's2sport'.
     * 
     * @param s2sport the value of field 's2sport'.
     */
    public void setS2sport(int s2sport)
    {
        this._s2sport = s2sport;
        this._has_s2sport = true;
    } //-- void setS2sport(int) 

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
        return (de.mbws.server.configuration.Accountserver) Unmarshaller.unmarshal(de.mbws.server.configuration.Accountserver.class, reader);
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
