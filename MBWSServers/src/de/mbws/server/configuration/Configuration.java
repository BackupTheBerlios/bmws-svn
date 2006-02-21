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
 * Configuration of MBWS
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
    private de.mbws.server.configuration.Accountserver _accountserver;

    /**
     * Field _worldserver
     */
    private de.mbws.server.configuration.Worldserver _worldserver;


      //----------------/
     //- Constructors -/
    //----------------/

    public Configuration() 
     {
        super();
    } //-- de.mbws.server.configuration.Configuration()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'accountserver'.
     * 
     * @return Accountserver
     * @return the value of field 'accountserver'.
     */
    public de.mbws.server.configuration.Accountserver getAccountserver()
    {
        return this._accountserver;
    } //-- de.mbws.server.configuration.Accountserver getAccountserver() 

    /**
     * Returns the value of field 'worldserver'.
     * 
     * @return Worldserver
     * @return the value of field 'worldserver'.
     */
    public de.mbws.server.configuration.Worldserver getWorldserver()
    {
        return this._worldserver;
    } //-- de.mbws.server.configuration.Worldserver getWorldserver() 

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
     * Sets the value of field 'accountserver'.
     * 
     * @param accountserver the value of field 'accountserver'.
     */
    public void setAccountserver(de.mbws.server.configuration.Accountserver accountserver)
    {
        this._accountserver = accountserver;
    } //-- void setAccountserver(de.mbws.server.configuration.Accountserver) 

    /**
     * Sets the value of field 'worldserver'.
     * 
     * @param worldserver the value of field 'worldserver'.
     */
    public void setWorldserver(de.mbws.server.configuration.Worldserver worldserver)
    {
        this._worldserver = worldserver;
    } //-- void setWorldserver(de.mbws.server.configuration.Worldserver) 

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
        return (de.mbws.server.configuration.Configuration) Unmarshaller.unmarshal(de.mbws.server.configuration.Configuration.class, reader);
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
