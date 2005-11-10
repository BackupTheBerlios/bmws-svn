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

import java.util.ArrayList;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class EventControllers.
 * 
 * @version $Revision$ $Date$
 */
public class EventControllers implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _eventControllerList
     */
    private java.util.ArrayList _eventControllerList;


      //----------------/
     //- Constructors -/
    //----------------/

    public EventControllers() 
     {
        super();
        _eventControllerList = new ArrayList();
    } //-- de.mbws.server.configuration.EventControllers()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addEventController
     * 
     * 
     * 
     * @param vEventController
     */
    public void addEventController(de.mbws.server.configuration.EventController vEventController)
        throws java.lang.IndexOutOfBoundsException
    {
        _eventControllerList.add(vEventController);
    } //-- void addEventController(de.mbws.server.configuration.EventController) 

    /**
     * Method addEventController
     * 
     * 
     * 
     * @param index
     * @param vEventController
     */
    public void addEventController(int index, de.mbws.server.configuration.EventController vEventController)
        throws java.lang.IndexOutOfBoundsException
    {
        _eventControllerList.add(index, vEventController);
    } //-- void addEventController(int, de.mbws.server.configuration.EventController) 

    /**
     * Method clearEventController
     * 
     */
    public void clearEventController()
    {
        _eventControllerList.clear();
    } //-- void clearEventController() 

    /**
     * Method enumerateEventController
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateEventController()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_eventControllerList.iterator());
    } //-- java.util.Enumeration enumerateEventController() 

    /**
     * Method getEventController
     * 
     * 
     * 
     * @param index
     * @return EventController
     */
    public de.mbws.server.configuration.EventController getEventController(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _eventControllerList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (de.mbws.server.configuration.EventController) _eventControllerList.get(index);
    } //-- de.mbws.server.configuration.EventController getEventController(int) 

    /**
     * Method getEventController
     * 
     * 
     * 
     * @return EventController
     */
    public de.mbws.server.configuration.EventController[] getEventController()
    {
        int size = _eventControllerList.size();
        de.mbws.server.configuration.EventController[] mArray = new de.mbws.server.configuration.EventController[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (de.mbws.server.configuration.EventController) _eventControllerList.get(index);
        }
        return mArray;
    } //-- de.mbws.server.configuration.EventController[] getEventController() 

    /**
     * Method getEventControllerCount
     * 
     * 
     * 
     * @return int
     */
    public int getEventControllerCount()
    {
        return _eventControllerList.size();
    } //-- int getEventControllerCount() 

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
     * Method removeEventController
     * 
     * 
     * 
     * @param vEventController
     * @return boolean
     */
    public boolean removeEventController(de.mbws.server.configuration.EventController vEventController)
    {
        boolean removed = _eventControllerList.remove(vEventController);
        return removed;
    } //-- boolean removeEventController(de.mbws.server.configuration.EventController) 

    /**
     * Method setEventController
     * 
     * 
     * 
     * @param index
     * @param vEventController
     */
    public void setEventController(int index, de.mbws.server.configuration.EventController vEventController)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _eventControllerList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _eventControllerList.set(index, vEventController);
    } //-- void setEventController(int, de.mbws.server.configuration.EventController) 

    /**
     * Method setEventController
     * 
     * 
     * 
     * @param eventControllerArray
     */
    public void setEventController(de.mbws.server.configuration.EventController[] eventControllerArray)
    {
        //-- copy array
        _eventControllerList.clear();
        for (int i = 0; i < eventControllerArray.length; i++) {
            _eventControllerList.add(eventControllerArray[i]);
        }
    } //-- void setEventController(de.mbws.server.configuration.EventController) 

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
        return (de.mbws.server.configuration.EventControllers) Unmarshaller.unmarshal(de.mbws.server.configuration.EventControllers.class, reader);
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
