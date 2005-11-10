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
 * Class BasicAttributes.
 * 
 * @version $Revision$ $Date$
 */
public class BasicAttributes implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _c2sport
     */
    private int _c2sport;

    /**
     * keeps track of state for field: _c2sport
     */
    private boolean _has_c2sport;

    /**
     * Field _queueworkersize
     */
    private int _queueworkersize;

    /**
     * keeps track of state for field: _queueworkersize
     */
    private boolean _has_queueworkersize;

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

    public BasicAttributes() 
     {
        super();
    } //-- de.mbws.server.configuration.BasicAttributes()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteC2sport
     * 
     */
    public void deleteC2sport()
    {
        this._has_c2sport= false;
    } //-- void deleteC2sport() 

    /**
     * Method deleteQueueworkersize
     * 
     */
    public void deleteQueueworkersize()
    {
        this._has_queueworkersize= false;
    } //-- void deleteQueueworkersize() 

    /**
     * Method deleteStartup
     * 
     */
    public void deleteStartup()
    {
        this._has_startup= false;
    } //-- void deleteStartup() 

    /**
     * Returns the value of field 'c2sport'.
     * 
     * @return int
     * @return the value of field 'c2sport'.
     */
    public int getC2sport()
    {
        return this._c2sport;
    } //-- int getC2sport() 

    /**
     * Returns the value of field 'queueworkersize'.
     * 
     * @return int
     * @return the value of field 'queueworkersize'.
     */
    public int getQueueworkersize()
    {
        return this._queueworkersize;
    } //-- int getQueueworkersize() 

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
     * Method hasC2sport
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasC2sport()
    {
        return this._has_c2sport;
    } //-- boolean hasC2sport() 

    /**
     * Method hasQueueworkersize
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasQueueworkersize()
    {
        return this._has_queueworkersize;
    } //-- boolean hasQueueworkersize() 

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
     * Sets the value of field 'c2sport'.
     * 
     * @param c2sport the value of field 'c2sport'.
     */
    public void setC2sport(int c2sport)
    {
        this._c2sport = c2sport;
        this._has_c2sport = true;
    } //-- void setC2sport(int) 

    /**
     * Sets the value of field 'queueworkersize'.
     * 
     * @param queueworkersize the value of field 'queueworkersize'.
     */
    public void setQueueworkersize(int queueworkersize)
    {
        this._queueworkersize = queueworkersize;
        this._has_queueworkersize = true;
    } //-- void setQueueworkersize(int) 

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
        return (de.mbws.server.configuration.BasicAttributes) Unmarshaller.unmarshal(de.mbws.server.configuration.BasicAttributes.class, reader);
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
