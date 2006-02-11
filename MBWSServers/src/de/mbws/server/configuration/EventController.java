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
 * Class EventController.
 * 
 * @version $Revision$ $Date$
 */
public class EventController implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _type
     */
    private int _type;

    /**
     * keeps track of state for field: _type
     */
    private boolean _has_type;

    /**
     * Field _clazz
     */
    private java.lang.String _clazz;


      //----------------/
     //- Constructors -/
    //----------------/

    public EventController() 
     {
        super();
    } //-- de.mbws.server.configuration.EventController()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteType
     * 
     */
    public void deleteType()
    {
        this._has_type= false;
    } //-- void deleteType() 

    /**
     * Returns the value of field 'clazz'.
     * 
     * @return String
     * @return the value of field 'clazz'.
     */
    public java.lang.String getClazz()
    {
        return this._clazz;
    } //-- java.lang.String getClazz() 

    /**
     * Returns the value of field 'type'.
     * 
     * @return int
     * @return the value of field 'type'.
     */
    public int getType()
    {
        return this._type;
    } //-- int getType() 

    /**
     * Method hasType
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasType()
    {
        return this._has_type;
    } //-- boolean hasType() 

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
     * Sets the value of field 'clazz'.
     * 
     * @param clazz the value of field 'clazz'.
     */
    public void setClazz(java.lang.String clazz)
    {
        this._clazz = clazz;
    } //-- void setClazz(java.lang.String) 

    /**
     * Sets the value of field 'type'.
     * 
     * @param type the value of field 'type'.
     */
    public void setType(int type)
    {
        this._type = type;
        this._has_type = true;
    } //-- void setType(int) 

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
        return (de.mbws.server.configuration.EventController) Unmarshaller.unmarshal(de.mbws.server.configuration.EventController.class, reader);
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
