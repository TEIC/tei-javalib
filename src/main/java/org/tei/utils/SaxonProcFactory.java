package org.tei.utils;

import net.sf.saxon.TransformerFactoryImpl;
import net.sf.saxon.s9api.Processor;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.transform.TransformerConfigurationException;

/**
 * Provides access to a Saxon Processor.
 * 
 * @author Arno Mittelbach
 *
 */
public class SaxonProcFactory {

	private static Processor processor;

	/**
	 * Stores and reuses a Saxon processor.
	 * @return The Saxon processor.
	 */
	public static Processor getProcessor() {
		if(null == processor){
			final TransformerFactoryImpl transFactory = new net.sf.saxon.TransformerFactoryImpl();
			try {
			transFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			transFactory.setFeature(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
			transFactory.setFeature(XMLInputFactory.SUPPORT_DTD, false);

			transFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			transFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
			transFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			transFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			}
			processor = transFactory.getProcessor();
		}
		return processor; 
	}


}
