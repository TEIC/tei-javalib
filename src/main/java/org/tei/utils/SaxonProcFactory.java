package org.tei.utils;

import net.sf.saxon.TransformerFactoryImpl;
import net.sf.saxon.s9api.Processor;

import javax.xml.XMLConstants;
import javax.xml.transform.TransformerConfigurationException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Provides access to a Saxon Processor.
 *
 * @author Arno Mittelbach
 */
public class SaxonProcFactory {

    private static Processor processor = null;
    private static final Logger LOGGER = LogManager.getLogger(SaxonProcFactory.class);
    /**
     * Stores and reuses a Saxon processor.
     *
     * @return The Saxon processor.
     */
    public static Processor getProcessor() {
        if (null == processor) {
            final TransformerFactoryImpl transFactory = new net.sf.saxon.TransformerFactoryImpl();
            try {
                //REDHAT
                //https://www.blackhat.com/docs/us-15/materials/us-15-Wang-FileCry-The-New-Age-Of-XXE-java-wp.pdf
                transFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                //transFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
                //this would make all TEI stylesheets stop working
                //transFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

                //OWASP
                //https://cheatsheetseries.owasp.org/cheatsheets/XML_External_Entity_Prevention_Cheat_Sheet.html
                //these don't work on the factory though
                //transFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                //transFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
                //transFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                //transFactory.setFeature(
                //		FeatureKeys.XML_PARSER_FEATURE + "http://xml.org/sax/features/external-general-entities", false);
                processor = transFactory.getProcessor();
                processor.setConfigurationProperty(
                        "http://saxon.sf.net/feature/parserFeature?uri=http://xml.org/sax/features/external-general-entities", false);
                processor.setConfigurationProperty(
                        "http://saxon.sf.net/feature/parserFeature?uri=http://apache.org/xml/features/disallow-doctype-decl", true);
            } catch (TransformerConfigurationException e) {
                LOGGER.error("There is a Doctype Declaration present in the source document that cannot be processed due to security reasons. Please remove it from your file and try again." + e.getMessage());
            }
        }
        return processor;
    }


}
