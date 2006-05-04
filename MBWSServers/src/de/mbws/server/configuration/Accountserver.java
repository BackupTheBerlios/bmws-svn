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
